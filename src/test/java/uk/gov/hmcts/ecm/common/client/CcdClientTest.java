package uk.gov.hmcts.ecm.common.client;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;
import uk.gov.hmcts.ecm.common.idam.models.UserDetails;
import uk.gov.hmcts.ecm.common.model.bulk.BulkCaseSearchResult;
import uk.gov.hmcts.ecm.common.model.bulk.BulkData;
import uk.gov.hmcts.ecm.common.model.bulk.BulkDetails;
import uk.gov.hmcts.ecm.common.model.bulk.SubmitBulkEvent;
import uk.gov.hmcts.ecm.common.model.ccd.*;
import uk.gov.hmcts.ecm.common.model.multiples.MultipleCaseSearchResult;
import uk.gov.hmcts.ecm.common.model.multiples.MultipleData;
import uk.gov.hmcts.ecm.common.model.multiples.MultipleDetails;
import uk.gov.hmcts.ecm.common.model.multiples.SubmitMultipleEvent;
import uk.gov.hmcts.ecm.common.service.UserService;
import uk.gov.hmcts.reform.authorisation.generators.AuthTokenGenerator;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;
import static uk.gov.hmcts.ecm.common.helpers.ESHelper.LISTING_VENUE_FIELD_NAME;
import static uk.gov.hmcts.ecm.common.model.helper.Constants.ALL_VENUES;
import static uk.gov.hmcts.ecm.common.model.helper.Constants.BROUGHT_FORWARD_REPORT;
import static uk.gov.hmcts.ecm.common.model.helper.Constants.MANUALLY_CREATED_POSITION;

@RunWith(MockitoJUnitRunner.class)
public class CcdClientTest {

    @InjectMocks
    private CcdClient ccdClient;
    @Mock
    private CcdClientConfig ccdClientConfig;
    @Mock
    private RestTemplate restTemplate;
    @Mock
    private UserService userService;
    @Mock
    private CaseDataBuilder caseDataBuilder;
    @Mock
    private AuthTokenGenerator authTokenGenerator;

    private UserDetails userDetails;
    private CaseDetails caseDetails;
    private BulkDetails bulkDetails;
    private MultipleDetails multipleDetails;
    private CaseData caseData;
    private BulkData bulkData;
    private MultipleData multipleData;
    private CCDRequest ccdRequest;
    private String uri = "http://example.com";

    @Before
    public void setUp() {

        ccdClient = new CcdClient(restTemplate, userService, caseDataBuilder, ccdClientConfig, authTokenGenerator);

        ccdRequest = new CCDRequest();
        ccdRequest.setEventId("1111");
        ccdRequest.setToken("Token");
        userDetails = getUserDetails();

        caseDetails = new CaseDetails();
        caseDetails.setJurisdiction("TRIBUNALS");
        caseDetails.setCaseTypeId("Type1");
        caseData = new CaseData();
        caseDetails.setCaseData(caseData);

        bulkDetails = new BulkDetails();
        bulkDetails.setJurisdiction("TRIBUNALS");
        bulkDetails.setCaseTypeId("Type1");
        bulkData = new BulkData();
        bulkDetails.setCaseData(bulkData);

        multipleDetails = new MultipleDetails();
        multipleDetails.setJurisdiction("TRIBUNALS");
        multipleDetails.setCaseTypeId("Type1");
        multipleData = new MultipleData();
        multipleDetails.setCaseData(multipleData);
    }

    private HttpHeaders creatBuildHeaders() {
        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.AUTHORIZATION, "authToken");
        headers.add("ServiceAuthorization", null);
        headers.add(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_UTF8_VALUE);
        return headers;
    }

    @Test
    public void startCaseCreation() throws IOException {
        HttpEntity<Object> httpEntity = new HttpEntity<>(creatBuildHeaders());
        ResponseEntity<CCDRequest> responseEntity = new ResponseEntity<>(HttpStatus.OK);
        when(userService.getUserDetails(anyString())).thenReturn(userDetails);
        when(ccdClientConfig.buildStartCaseCreationUrl(any(), any(), any())).thenReturn(uri);
        when(restTemplate.exchange(eq(uri), eq(HttpMethod.GET), eq(httpEntity), eq(CCDRequest.class))).thenReturn(responseEntity);
        ccdClient.startCaseCreation("authToken", caseDetails);
        verify(restTemplate).exchange(eq(uri), eq(HttpMethod.GET), eq(httpEntity), eq(CCDRequest.class));
        verifyNoMoreInteractions(restTemplate);
    }

    @Test
    public void submitCaseCreation() throws IOException {
        HttpEntity<CaseDataContent> httpEntity = new HttpEntity<>(CaseDataContent.builder().build(), creatBuildHeaders());
        ResponseEntity<SubmitEvent> responseEntity = new ResponseEntity<>(HttpStatus.OK);
        when(caseDataBuilder.buildCaseDataContent(eq(caseData), eq(ccdRequest), anyString())).thenReturn(CaseDataContent.builder().build());
        when(userService.getUserDetails(anyString())).thenReturn(userDetails);
        when(ccdClientConfig.buildSubmitCaseCreationUrl(any(), any(), any())).thenReturn(uri);
        when(restTemplate.exchange(eq(uri), eq(HttpMethod.POST), eq(httpEntity), eq(SubmitEvent.class))).thenReturn(responseEntity);
        ccdClient.submitCaseCreation("authToken", caseDetails, ccdRequest);
        verify(restTemplate).exchange(eq(uri), eq(HttpMethod.POST), eq(httpEntity), eq(SubmitEvent.class));
        verifyNoMoreInteractions(restTemplate);
    }

    @Test
    public void retrieveCase() throws IOException {
        HttpEntity<Object> httpEntity = new HttpEntity<>(creatBuildHeaders());
        ResponseEntity<SubmitEvent> responseEntity = new ResponseEntity<>(HttpStatus.OK);
        when(userService.getUserDetails(anyString())).thenReturn(userDetails);
        when(ccdClientConfig.buildRetrieveCaseUrl(any(), any(), any(), any())).thenReturn(uri);
        when(restTemplate.exchange(eq(uri), eq(HttpMethod.GET), eq(httpEntity), eq(SubmitEvent.class))).thenReturn(responseEntity);
        ccdClient.retrieveCase("authToken", caseDetails.getCaseTypeId(), caseDetails.getJurisdiction(), "111111");
        verify(restTemplate).exchange(eq(uri), eq(HttpMethod.GET), eq(httpEntity), eq(SubmitEvent.class));
        verifyNoMoreInteractions(restTemplate);
    }

    @Test
    public void retrieveCases() throws IOException {
        HttpEntity<Object> httpEntity = new HttpEntity<>(creatBuildHeaders());
        List<SubmitEvent> submitEvents = new ArrayList<>(Arrays.asList(new SubmitEvent(), new SubmitEvent()));
        ResponseEntity<List<SubmitEvent>> responseEntity = new ResponseEntity<>(submitEvents, HttpStatus.OK);
        PaginatedSearchMetadata metadata = new PaginatedSearchMetadata();
        metadata.setTotalPagesCount(1);
        ResponseEntity<PaginatedSearchMetadata> paginatedSearchMetadata = new ResponseEntity<>(metadata, HttpStatus.OK);
        when(userService.getUserDetails(anyString())).thenReturn(userDetails);
        when(ccdClientConfig.buildRetrieveCasesUrl(any(), any(), any(), any())).thenReturn(uri);
        when(ccdClientConfig.buildPaginationMetadataCaseUrl(any(), any(), any())).thenReturn(uri);
        when(restTemplate.exchange(eq(uri), eq(HttpMethod.GET), eq(httpEntity), eq(new ParameterizedTypeReference<List<SubmitEvent>>(){}))).thenReturn(responseEntity);
        when(restTemplate.exchange(eq(uri), eq(HttpMethod.GET), eq(httpEntity), eq(PaginatedSearchMetadata.class))).thenReturn(paginatedSearchMetadata);
        ccdClient.retrieveCases("authToken", caseDetails.getCaseTypeId(), caseDetails.getJurisdiction());
        verify(restTemplate).exchange(eq(uri), eq(HttpMethod.GET), eq(httpEntity), eq(new ParameterizedTypeReference<List<SubmitEvent>>(){}));
    }

    @Test
    public void retrieveCasesElasticSearchForCreationManuallyCreated() throws IOException {
        String jsonQuery = "{\"size\":10000,\"query\":{\"terms\":{\"data.ethosCaseReference.keyword\":[\"2420117/2019\",\"2420118/2019\"],\"boost\":1.0}}}";
//        String jsonQuery = "{\"size\":10000,\"query\":{\"terms\":{\"data.ethosCaseReference.keyword\":[\"2420117/2019\",\"2420118/2019\"],\"boost\":1.0}}," +
//                "\"_source\":{\"includes\":[\"data\"],\"excludes\":[]}}";
        HttpEntity<String> httpEntity = new HttpEntity<>(jsonQuery,creatBuildHeaders());
        CaseSearchResult caseSearchResult = new CaseSearchResult(2L, Arrays.asList(new SubmitEvent(), new SubmitEvent()));
        ResponseEntity<CaseSearchResult> responseEntity = new ResponseEntity<>(caseSearchResult, HttpStatus.OK);
        when(ccdClientConfig.buildRetrieveCasesUrlElasticSearch(any())).thenReturn(uri);
        when(restTemplate.exchange(eq(uri), eq(HttpMethod.POST), eq(httpEntity), eq(CaseSearchResult.class))).thenReturn(responseEntity);
        ccdClient.retrieveCasesElasticSearchForCreation("authToken", caseDetails.getCaseTypeId(), new ArrayList<>(Arrays.asList("2420117/2019", "2420118/2019")), MANUALLY_CREATED_POSITION);
        verify(restTemplate).exchange(eq(uri), eq(HttpMethod.POST), eq(httpEntity), eq(CaseSearchResult.class));
        verifyNoMoreInteractions(restTemplate);
    }

    @Test
    public void retrieveCasesElasticSearchForCreationETOnline() throws IOException {
        String jsonQuery = "{\"size\":10000,\"query\":{\"terms\":{\"data.ethosCaseReference.keyword\":[\"2420117/2019\",\"2420118/2019\"],\"boost\":1.0}}}";
//        String jsonQuery = "{\"size\":10000,\"query\":{\"terms\":{\"data.ethosCaseReference.keyword\":[\"2420117/2019\",\"2420118/2019\"],\"boost\":1.0}}," +
//                "\"_source\":{\"includes\":[\"data\"],\"excludes\":[]}}";
        HttpEntity<String> httpEntity = new HttpEntity<>(jsonQuery, creatBuildHeaders());
        SubmitEvent submitEvent = new SubmitEvent();
        CaseData caseData = new CaseData();
        caseData.setEthosCaseReference("2420117/2019");
        submitEvent.setCaseData(caseData);
        SubmitEvent submitEvent1 = new SubmitEvent();
        CaseData caseData1 = new CaseData();
        caseData1.setEthosCaseReference("2420118/2019");
        submitEvent1.setCaseData(caseData1);
        CaseSearchResult caseSearchResult = new CaseSearchResult(2L, Arrays.asList(submitEvent, submitEvent1));
        ResponseEntity<CaseSearchResult> responseEntity = new ResponseEntity<>(caseSearchResult, HttpStatus.OK);
        when(ccdClientConfig.buildRetrieveCasesUrlElasticSearch(any())).thenReturn(uri);
        when(restTemplate.exchange(eq(uri), eq(HttpMethod.POST), eq(httpEntity), eq(CaseSearchResult.class))).thenReturn(responseEntity);
        ccdClient.retrieveCasesElasticSearchForCreation("authToken", caseDetails.getCaseTypeId(), new ArrayList<>(Arrays.asList("2420117/2019", "2420118/2019")), "ET1 Online");
        verify(restTemplate).exchange(eq(uri), eq(HttpMethod.POST), eq(httpEntity), eq(CaseSearchResult.class));
        verifyNoMoreInteractions(restTemplate);
    }

    @Test
    public void retrieveBulkCasesElasticSearch() throws IOException {
        String jsonQuery = "{\"size\":10000,\"query\":{\"terms\":{\"data.multipleReference.keyword\":[\"2400001/2020\"],\"boost\":1.0}}}";
        HttpEntity<String> httpEntity = new HttpEntity<>(jsonQuery, creatBuildHeaders());
        BulkCaseSearchResult bulkCaseSearchResult = new BulkCaseSearchResult(2L, Arrays.asList(new SubmitBulkEvent(), new SubmitBulkEvent()));
        ResponseEntity<BulkCaseSearchResult> responseEntity = new ResponseEntity<>(bulkCaseSearchResult, HttpStatus.OK);
        when(ccdClientConfig.buildRetrieveCasesUrlElasticSearch(any())).thenReturn(uri);
        when(restTemplate.exchange(eq(uri), eq(HttpMethod.POST), eq(httpEntity), eq(BulkCaseSearchResult.class))).thenReturn(responseEntity);
        ccdClient.retrieveBulkCasesElasticSearch("authToken", caseDetails.getCaseTypeId(), "2400001/2020");
        verify(restTemplate).exchange(eq(uri), eq(HttpMethod.POST), eq(httpEntity), eq(BulkCaseSearchResult.class));
        verifyNoMoreInteractions(restTemplate);
    }

    @Test
    public void retrieveMultipleCasesElasticSearch() throws IOException {
        String jsonQuery = "{\"size\":10000,\"query\":{\"terms\":{\"data.multipleReference.keyword\":[\"2400001/2020\"],\"boost\":1.0}}}";
        HttpEntity<String> httpEntity = new HttpEntity<>(jsonQuery, creatBuildHeaders());
        MultipleCaseSearchResult multipleCaseSearchResult =
                new MultipleCaseSearchResult(2L, Arrays.asList(new SubmitMultipleEvent(), new SubmitMultipleEvent()));
        ResponseEntity<MultipleCaseSearchResult> responseEntity = new ResponseEntity<>(multipleCaseSearchResult, HttpStatus.OK);
        when(ccdClientConfig.buildRetrieveCasesUrlElasticSearch(any())).thenReturn(uri);
        when(restTemplate.exchange(eq(uri), eq(HttpMethod.POST), eq(httpEntity), eq(MultipleCaseSearchResult.class))).thenReturn(responseEntity);
        ccdClient.retrieveMultipleCasesElasticSearch("authToken", caseDetails.getCaseTypeId(), "2400001/2020");
        verify(restTemplate).exchange(eq(uri), eq(HttpMethod.POST), eq(httpEntity), eq(MultipleCaseSearchResult.class));
        verifyNoMoreInteractions(restTemplate);
    }

    @Test
    public void retrieveCasesVenueAndRangeDateElasticSearch() throws IOException {
        String jsonQuery = "{\"size\":10000,\"query\":{\"bool\":{\"filter\":[{\"term\":{\"data.hearingCollection.value.hearingDateCollection.value" +
                ".hearingVenueDay.keyword\":{\"value\":\"Manchester\",\"boost\":1.0}}},{\"range\":{\"data.hearingCollection.value.hearingDateCollection.value" +
                ".listedDate\":{\"from\":\"2019-09-23T00:00:00.000\",\"to\":\"2019-09-24T00:00:00.000\",\"include_lower\":true,\"include_upper\":true,\"boost\":1" +
                ".0}}}],\"adjust_pure_negative\":true,\"boost\":1.0}}}";
        HttpEntity<String> httpEntity = new HttpEntity<>(jsonQuery, creatBuildHeaders());
        CaseSearchResult caseSearchResult = new CaseSearchResult(2L, Arrays.asList(new SubmitEvent(), new SubmitEvent()));
        ResponseEntity<CaseSearchResult> responseEntity = new ResponseEntity<>(caseSearchResult, HttpStatus.OK);
        when(ccdClientConfig.buildRetrieveCasesUrlElasticSearch(any())).thenReturn(uri);
        when(restTemplate.exchange(eq(uri), eq(HttpMethod.POST), eq(httpEntity), eq(CaseSearchResult.class))).thenReturn(responseEntity);
        ccdClient.retrieveCasesVenueAndDateElasticSearch("authToken", caseDetails.getCaseTypeId(), "2019-09-23",
                "2019-09-24", "Manchester", LISTING_VENUE_FIELD_NAME);
        verify(restTemplate).exchange(eq(uri), eq(HttpMethod.POST), eq(httpEntity), eq(CaseSearchResult.class));
        verifyNoMoreInteractions(restTemplate);
    }

    @Test
    public void retrieveCasesVenueAndSingleDateElasticSearch() throws IOException {
        String jsonQuery = "{\"size\":10000,\"query\":{\"bool\":{\"filter\":[{\"term\":{\"data.hearingCollection.value.hearingDateCollection.value" +
                ".hearingVenueDay.keyword\":{\"value\":\"Manchester\",\"boost\":1.0}}},{\"range\":{\"data.hearingCollection.value.hearingDateCollection.value" +
                ".listedDate\":{\"from\":\"2019-09-23T00:00:00.000\",\"to\":\"2019-09-24T00:00:00" +
                ".000\",\"include_lower\":true,\"include_upper\":true,\"boost\":1.0}}}],\"adjust_pure_negative\":true,\"boost\":1.0}}}";
        HttpEntity<String> httpEntity = new HttpEntity<>(jsonQuery, creatBuildHeaders());
        CaseSearchResult caseSearchResult = new CaseSearchResult(2L, Arrays.asList(new SubmitEvent(), new SubmitEvent()));
        ResponseEntity<CaseSearchResult> responseEntity = new ResponseEntity<>(caseSearchResult, HttpStatus.OK);
        when(ccdClientConfig.buildRetrieveCasesUrlElasticSearch(any())).thenReturn(uri);
        when(restTemplate.exchange(eq(uri), eq(HttpMethod.POST), eq(httpEntity), eq(CaseSearchResult.class))).thenReturn(responseEntity);
        ccdClient.retrieveCasesVenueAndDateElasticSearch("authToken", caseDetails.getCaseTypeId(), "2019-09-23",
                "2019-09-24", "Manchester", LISTING_VENUE_FIELD_NAME);
        verify(restTemplate).exchange(eq(uri), eq(HttpMethod.POST), eq(httpEntity), eq(CaseSearchResult.class));
        verifyNoMoreInteractions(restTemplate);
    }

    @Test
    public void retrieveCasesAllVenuesAndSingleDateElasticSearch() throws IOException {
        String jsonQuery = "{\"size\":10000,\"query\":{\"bool\":{\"filter\":[{\"range\":{\"data.hearingCollection.value.hearingDateCollection.value" +
                ".listedDate\":{\"from\":\"2019-09-23T00:00:00.000\",\"to\":\"2019-09-23T23:59:59.000\",\"include_lower\":true,\"include_upper\":true,\"boost\":1" +
                ".0}}}],\"adjust_pure_negative\":true,\"boost\":1.0}}}";
        HttpEntity<String> httpEntity = new HttpEntity<>(jsonQuery, creatBuildHeaders());
        CaseSearchResult caseSearchResult = new CaseSearchResult(2L, Arrays.asList(new SubmitEvent(), new SubmitEvent()));
        ResponseEntity<CaseSearchResult> responseEntity = new ResponseEntity<>(caseSearchResult, HttpStatus.OK);
        when(ccdClientConfig.buildRetrieveCasesUrlElasticSearch(any())).thenReturn(uri);
        when(restTemplate.exchange(eq(uri), eq(HttpMethod.POST), eq(httpEntity), eq(CaseSearchResult.class))).thenReturn(responseEntity);
        ccdClient.retrieveCasesVenueAndDateElasticSearch("authToken", caseDetails.getCaseTypeId(), "2019-09-23",
                "2019-09-23", ALL_VENUES, ALL_VENUES);
        verify(restTemplate).exchange(eq(uri), eq(HttpMethod.POST), eq(httpEntity), eq(CaseSearchResult.class));
        verifyNoMoreInteractions(restTemplate);
    }

    @Test
    public void retrieveCasesGenericReportElasticSearch() throws IOException {
        String jsonQuery = "{\"size\":10000,\"query\":{\"bool\":{\"filter\":[{\"range\":{\"data.broughtForwardCollection.value" +
                ".broughtForwardDate\":{\"from\":\"2019-09-23T00:00:00.000\",\"to\":\"2019-09-24T00:00:00.000\",\"include_lower\":true,\"include_upper\":true,\"boost\":1" +
                ".0}}}],\"adjust_pure_negative\":true,\"boost\":1.0}}}";
        HttpEntity<String> httpEntity = new HttpEntity<>(jsonQuery, creatBuildHeaders());
        CaseSearchResult caseSearchResult = new CaseSearchResult(2L, Arrays.asList(new SubmitEvent(), new SubmitEvent()));
        ResponseEntity<CaseSearchResult> responseEntity = new ResponseEntity<>(caseSearchResult, HttpStatus.OK);
        when(ccdClientConfig.buildRetrieveCasesUrlElasticSearch(any())).thenReturn(uri);
        when(restTemplate.exchange(eq(uri), eq(HttpMethod.POST), eq(httpEntity), eq(CaseSearchResult.class))).thenReturn(responseEntity);
        ccdClient.retrieveCasesGenericReportElasticSearch("authToken", caseDetails.getCaseTypeId(), "2019-09-23",
                "2019-09-24", BROUGHT_FORWARD_REPORT);
        verify(restTemplate).exchange(eq(uri), eq(HttpMethod.POST), eq(httpEntity), eq(CaseSearchResult.class));
        verifyNoMoreInteractions(restTemplate);
    }

    @Test
    public void retrieveBulkCases() throws IOException {
        HttpEntity<Object> httpEntity = new HttpEntity<>(creatBuildHeaders());
        List<SubmitBulkEvent> submitBulkEvents = new ArrayList<>(Arrays.asList(new SubmitBulkEvent(), new SubmitBulkEvent()));
        ResponseEntity<List<SubmitBulkEvent>> responseEntity = new ResponseEntity<>(submitBulkEvents, HttpStatus.OK);
        PaginatedSearchMetadata metadata = new PaginatedSearchMetadata();
        metadata.setTotalPagesCount(1);
        ResponseEntity<PaginatedSearchMetadata> paginatedSearchMetadata = new ResponseEntity<>(metadata, HttpStatus.OK);
        when(userService.getUserDetails(anyString())).thenReturn(userDetails);
        when(ccdClientConfig.buildRetrieveCasesUrl(any(), any(), any(), any())).thenReturn(uri);
        when(ccdClientConfig.buildPaginationMetadataCaseUrl(any(), any(), any())).thenReturn(uri);
        when(restTemplate.exchange(eq(uri), eq(HttpMethod.GET), eq(httpEntity), eq(new ParameterizedTypeReference<List<SubmitBulkEvent>>(){}))).thenReturn(responseEntity);
        when(restTemplate.exchange(eq(uri), eq(HttpMethod.GET), eq(httpEntity), eq(PaginatedSearchMetadata.class))).thenReturn(paginatedSearchMetadata);
        ccdClient.retrieveBulkCases("authToken", bulkDetails.getCaseTypeId(), bulkDetails.getJurisdiction());
        verify(restTemplate).exchange(eq(uri), eq(HttpMethod.GET), eq(httpEntity), eq(new ParameterizedTypeReference<List<SubmitBulkEvent>>(){}));
    }

    @Test
    public void startEventForCase() throws IOException {
        HttpEntity<Object> httpEntity = new HttpEntity<>(creatBuildHeaders());
        ResponseEntity<CCDRequest> responseEntity = new ResponseEntity<>(HttpStatus.OK);
        when(userService.getUserDetails(anyString())).thenReturn(userDetails);
        when(ccdClientConfig.buildStartEventForCaseUrl(any(), any(), any(), any())).thenReturn(uri);
        when(restTemplate.exchange(eq(uri), eq(HttpMethod.GET), eq(httpEntity), eq(CCDRequest.class))).thenReturn(responseEntity);
        ccdClient.startEventForCase("authToken", caseDetails.getCaseTypeId(), caseDetails.getJurisdiction(), "1111");
        verify(restTemplate).exchange(eq(uri), eq(HttpMethod.GET), eq(httpEntity), eq(CCDRequest.class));
        verifyNoMoreInteractions(restTemplate);
    }

    @Test
    public void startEventForCaseBulkSingle() throws IOException {
        HttpEntity<Object> httpEntity = new HttpEntity<>(creatBuildHeaders());
        ResponseEntity<CCDRequest> responseEntity = new ResponseEntity<>(HttpStatus.OK);
        when(userService.getUserDetails(anyString())).thenReturn(userDetails);
        when(ccdClientConfig.buildStartEventForCaseUrlBulkSingle(any(), any(), any(), any())).thenReturn(uri);
        when(restTemplate.exchange(eq(uri), eq(HttpMethod.GET), eq(httpEntity), eq(CCDRequest.class))).thenReturn(responseEntity);
        ccdClient.startEventForCaseBulkSingle("authToken", caseDetails.getCaseTypeId(), caseDetails.getJurisdiction(), "1111");
        verify(restTemplate).exchange(eq(uri), eq(HttpMethod.GET), eq(httpEntity), eq(CCDRequest.class));
        verifyNoMoreInteractions(restTemplate);
    }

    @Test
    public void startEventForCasePreAcceptBulkSingle() throws IOException {
        HttpEntity<Object> httpEntity = new HttpEntity<>(creatBuildHeaders());
        ResponseEntity<CCDRequest> responseEntity = new ResponseEntity<>(HttpStatus.OK);
        when(userService.getUserDetails(anyString())).thenReturn(userDetails);
        when(ccdClientConfig.buildStartEventForCaseUrlPreAcceptBulkSingle(any(), any(), any(), any())).thenReturn(uri);
        when(restTemplate.exchange(eq(uri), eq(HttpMethod.GET), eq(httpEntity), eq(CCDRequest.class))).thenReturn(responseEntity);
        ccdClient.startEventForCasePreAcceptBulkSingle("authToken", caseDetails.getCaseTypeId(), caseDetails.getJurisdiction(), "1111");
        verify(restTemplate).exchange(eq(uri), eq(HttpMethod.GET), eq(httpEntity), eq(CCDRequest.class));
        verifyNoMoreInteractions(restTemplate);
    }

    @Test
    public void startBulkEventForCase() throws IOException {
        HttpEntity<Object> httpEntity = new HttpEntity<>(creatBuildHeaders());
        ResponseEntity<CCDRequest> responseEntity = new ResponseEntity<>(HttpStatus.OK);
        when(userService.getUserDetails(anyString())).thenReturn(userDetails);
        when(ccdClientConfig.buildStartEventForBulkCaseUrl(any(), any(), any(), any())).thenReturn(uri);
        when(restTemplate.exchange(eq(uri), eq(HttpMethod.GET), eq(httpEntity), eq(CCDRequest.class))).thenReturn(responseEntity);
        ccdClient.startBulkEventForCase("authToken", bulkDetails.getCaseTypeId(), bulkDetails.getJurisdiction(), "1111");
        verify(restTemplate).exchange(eq(uri), eq(HttpMethod.GET), eq(httpEntity), eq(CCDRequest.class));
        verifyNoMoreInteractions(restTemplate);
    }

    @Test
    public void startBulkAmendEventForCase() throws IOException {
        HttpEntity<Object> httpEntity = new HttpEntity<>(creatBuildHeaders());
        ResponseEntity<CCDRequest> responseEntity = new ResponseEntity<>(HttpStatus.OK);
        when(userService.getUserDetails(anyString())).thenReturn(userDetails);
        when(ccdClientConfig.buildStartEventForBulkAmendCaseUrl(any(), any(), any(), any())).thenReturn(uri);
        when(restTemplate.exchange(eq(uri), eq(HttpMethod.GET), eq(httpEntity), eq(CCDRequest.class))).thenReturn(responseEntity);
        ccdClient.startBulkAmendEventForCase("authToken", bulkDetails.getCaseTypeId(), bulkDetails.getJurisdiction(), "1111");
        verify(restTemplate).exchange(eq(uri), eq(HttpMethod.GET), eq(httpEntity), eq(CCDRequest.class));
        verifyNoMoreInteractions(restTemplate);
    }

    @Test
    public void submitEventForCase() throws IOException {
        HttpEntity<CaseDataContent> httpEntity = new HttpEntity<>(CaseDataContent.builder().build(), creatBuildHeaders());
        ResponseEntity<SubmitEvent> responseEntity = new ResponseEntity<>(HttpStatus.OK);
        when(caseDataBuilder.buildCaseDataContent(eq(caseData), eq(ccdRequest), anyString())).thenReturn(CaseDataContent.builder().build());
        when(userService.getUserDetails(anyString())).thenReturn(userDetails);
        when(ccdClientConfig.buildSubmitEventForCaseUrl(any(), any(), any(), any())).thenReturn(uri);
        when(restTemplate.exchange(eq(uri), eq(HttpMethod.POST), eq(httpEntity), eq(SubmitEvent.class))).thenReturn(responseEntity);
        ccdClient.submitEventForCase("authToken", caseData, caseDetails.getCaseTypeId(), caseDetails.getJurisdiction(), ccdRequest, "111111");
        verify(restTemplate).exchange(eq(uri), eq(HttpMethod.POST), eq(httpEntity), eq(SubmitEvent.class));
        verifyNoMoreInteractions(restTemplate);
    }

    @Test
    public void submitBulkEventForCase() throws IOException {
        HttpEntity<CaseDataContent> httpEntity = new HttpEntity<>(CaseDataContent.builder().build(), creatBuildHeaders());
        ResponseEntity<SubmitBulkEvent> responseEntity = new ResponseEntity<>(HttpStatus.OK);
        when(caseDataBuilder.buildBulkDataContent(eq(bulkData), eq(ccdRequest), anyString())).thenReturn(CaseDataContent.builder().build());
        when(userService.getUserDetails(anyString())).thenReturn(userDetails);
        when(ccdClientConfig.buildSubmitEventForCaseUrl(any(), any(), any(), any())).thenReturn(uri);
        when(restTemplate.exchange(eq(uri), eq(HttpMethod.POST), eq(httpEntity), eq(SubmitBulkEvent.class))).thenReturn(responseEntity);
        ccdClient.submitBulkEventForCase("authToken", bulkData, bulkDetails.getCaseTypeId(), bulkDetails.getJurisdiction(), ccdRequest, "111111");
        verify(restTemplate).exchange(eq(uri), eq(HttpMethod.POST), eq(httpEntity), eq(SubmitBulkEvent.class));
        verifyNoMoreInteractions(restTemplate);
    }

    @Test
    public void submitMultipleEventForCase() throws IOException {
        HttpEntity<CaseDataContent> httpEntity = new HttpEntity<>(CaseDataContent.builder().build(), creatBuildHeaders());
        ResponseEntity<SubmitMultipleEvent> responseEntity = new ResponseEntity<>(HttpStatus.OK);
        when(caseDataBuilder.buildMultipleDataContent(eq(multipleData), eq(ccdRequest), anyString())).thenReturn(CaseDataContent.builder().build());
        when(userService.getUserDetails(anyString())).thenReturn(userDetails);
        when(ccdClientConfig.buildSubmitEventForCaseUrl(any(), any(), any(), any())).thenReturn(uri);
        when(restTemplate.exchange(eq(uri), eq(HttpMethod.POST), eq(httpEntity), eq(SubmitMultipleEvent.class))).thenReturn(responseEntity);
        ccdClient.submitMultipleEventForCase("authToken", multipleData, multipleDetails.getCaseTypeId(), multipleDetails.getJurisdiction(), ccdRequest, "111111");
        verify(restTemplate).exchange(eq(uri), eq(HttpMethod.POST), eq(httpEntity), eq(SubmitMultipleEvent.class));
        verifyNoMoreInteractions(restTemplate);
    }

    @Test
    public void buildHeaders() throws IOException {
        when(authTokenGenerator.generate()).thenReturn("authString");
        HttpHeaders httpHeaders = ccdClient.buildHeaders("authString");
        assertEquals("[Authorization:\"authString\", ServiceAuthorization:\"authString\", " +
                "Content-Type:\"application/json;charset=UTF-8\"]", httpHeaders.toString());
    }

    public static UserDetails getUserDetails() {
        UserDetails userDetails = new UserDetails();
        userDetails.setUid("id");
        userDetails.setEmail("mail@mail.com");
        userDetails.setFirstName("Mike");
        userDetails.setLastName("Jordan");
        userDetails.setRoles(Collections.singletonList("role"));
        return userDetails;
    }

    @Test
    public void retrieveMultipleCasesElasticSearchWithRetries() throws IOException {
        String jsonQuery = "{\"size\":10000,\"query\":{\"terms\":{\"data.multipleReference.keyword\":[\"2400001/2020\"],\"boost\":1.0}}}";
        HttpEntity<String> httpEntity = new HttpEntity<>(jsonQuery, creatBuildHeaders());
        SubmitMultipleEvent submitMultipleEvent = new SubmitMultipleEvent();
        MultipleData multipleData = new MultipleData();
        multipleData.setMultipleReference("2400001/2020");
        submitMultipleEvent.setCaseData(multipleData);
        MultipleCaseSearchResult multipleCaseSearchResult =
                new MultipleCaseSearchResult(1L, Collections.singletonList(submitMultipleEvent));
        ResponseEntity<MultipleCaseSearchResult> responseEntity = new ResponseEntity<>(multipleCaseSearchResult, HttpStatus.OK);
        when(ccdClientConfig.buildRetrieveCasesUrlElasticSearch(any())).thenReturn(uri);
        when(restTemplate.exchange(eq(uri), eq(HttpMethod.POST), eq(httpEntity), eq(MultipleCaseSearchResult.class))).thenReturn(responseEntity);
        ccdClient.retrieveMultipleCasesElasticSearchWithRetries("authToken", caseDetails.getCaseTypeId(), "2400001/2020");
        verify(restTemplate).exchange(eq(uri), eq(HttpMethod.POST), eq(httpEntity), eq(MultipleCaseSearchResult.class));
        verifyNoMoreInteractions(restTemplate);
    }

}