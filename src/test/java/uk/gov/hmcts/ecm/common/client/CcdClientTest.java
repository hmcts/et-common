package uk.gov.hmcts.ecm.common.client;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;
import uk.gov.hmcts.ecm.common.idam.models.UserDetails;
import uk.gov.hmcts.ecm.common.model.helper.TribunalOffice;
import uk.gov.hmcts.ecm.common.model.labels.LabelCaseSearchResult;
import uk.gov.hmcts.ecm.common.model.labels.LabelPayloadEvent;
import uk.gov.hmcts.ecm.common.model.reports.casesawaitingjudgment.CasesAwaitingJudgmentSearchResult;
import uk.gov.hmcts.ecm.common.model.reports.casesawaitingjudgment.CasesAwaitingJudgmentSubmitEvent;
import uk.gov.hmcts.ecm.common.model.reports.claimsbyhearingvenue.ClaimsByHearingVenueSearchResult;
import uk.gov.hmcts.ecm.common.model.reports.claimsbyhearingvenue.ClaimsByHearingVenueSubmitEvent;
import uk.gov.hmcts.ecm.common.model.reports.eccreport.EccReportSearchResult;
import uk.gov.hmcts.ecm.common.model.reports.eccreport.EccReportSubmitEvent;
import uk.gov.hmcts.ecm.common.model.reports.hearingsbyhearingtype.HearingsByHearingTypeSearchResult;
import uk.gov.hmcts.ecm.common.model.reports.hearingsbyhearingtype.HearingsByHearingTypeSubmitEvent;
import uk.gov.hmcts.ecm.common.model.reports.hearingstojudgments.HearingsToJudgmentsSearchResult;
import uk.gov.hmcts.ecm.common.model.reports.hearingstojudgments.HearingsToJudgmentsSubmitEvent;
import uk.gov.hmcts.ecm.common.model.reports.respondentsreport.RespondentsReportSearchResult;
import uk.gov.hmcts.ecm.common.model.reports.respondentsreport.RespondentsReportSubmitEvent;
import uk.gov.hmcts.ecm.common.model.reports.sessiondays.SessionDaysSearchResult;
import uk.gov.hmcts.ecm.common.model.reports.sessiondays.SessionDaysSubmitEvent;
import uk.gov.hmcts.ecm.common.model.schedule.ScheduleCaseSearchResult;
import uk.gov.hmcts.ecm.common.model.schedule.SchedulePayloadEvent;
import uk.gov.hmcts.ecm.common.service.UserService;
import uk.gov.hmcts.et.common.model.bulk.BulkCaseSearchResult;
import uk.gov.hmcts.et.common.model.bulk.BulkData;
import uk.gov.hmcts.et.common.model.bulk.BulkDetails;
import uk.gov.hmcts.et.common.model.bulk.SubmitBulkEvent;
import uk.gov.hmcts.et.common.model.ccd.AuditEventsResponse;
import uk.gov.hmcts.et.common.model.ccd.CCDRequest;
import uk.gov.hmcts.et.common.model.ccd.CaseData;
import uk.gov.hmcts.et.common.model.ccd.CaseDataContent;
import uk.gov.hmcts.et.common.model.ccd.CaseDetails;
import uk.gov.hmcts.et.common.model.ccd.CaseSearchResult;
import uk.gov.hmcts.et.common.model.ccd.CaseUserAssignmentData;
import uk.gov.hmcts.et.common.model.ccd.GenericTypeCaseDetails;
import uk.gov.hmcts.et.common.model.ccd.PaginatedSearchMetadata;
import uk.gov.hmcts.et.common.model.ccd.SubmitEvent;
import uk.gov.hmcts.et.common.model.multiples.MultipleCaseSearchResult;
import uk.gov.hmcts.et.common.model.multiples.MultipleData;
import uk.gov.hmcts.et.common.model.multiples.MultipleDetails;
import uk.gov.hmcts.et.common.model.multiples.SubmitMultipleEvent;
import uk.gov.hmcts.reform.authorisation.generators.AuthTokenGenerator;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.endsWith;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;
import static uk.gov.hmcts.ecm.common.client.CcdClient.UPDATE_EVENT_SUMMARY;
import static uk.gov.hmcts.ecm.common.helpers.ESHelper.LISTING_VENUE_FIELD_NAME;
import static uk.gov.hmcts.ecm.common.model.helper.Constants.ALL_VENUES;
import static uk.gov.hmcts.ecm.common.model.helper.Constants.BROUGHT_FORWARD_REPORT;
import static uk.gov.hmcts.ecm.common.model.helper.Constants.CASES_COMPLETED_REPORT;
import static uk.gov.hmcts.ecm.common.model.helper.Constants.CASE_SOURCE_LOCAL_REPORT;
import static uk.gov.hmcts.ecm.common.model.helper.Constants.ENGLANDWALES_CASE_TYPE_ID;
import static uk.gov.hmcts.ecm.common.model.helper.Constants.LIVE_CASELOAD_REPORT;
import static uk.gov.hmcts.ecm.common.model.helper.Constants.MANUALLY_CREATED_POSITION;
import static uk.gov.hmcts.ecm.common.model.helper.Constants.TIME_TO_FIRST_HEARING_REPORT;

@ExtendWith(MockitoExtension.class)
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
    private EcmCaseDataBuilder ecmCaseDataBuilder;
    @Mock
    private AuthTokenGenerator authTokenGenerator;

    private UserDetails userDetails;
    private CaseDetails caseDetails;
    private GenericTypeCaseDetails<CaseData> genericTypeCaseDetails;
    private BulkDetails bulkDetails;
    private MultipleDetails multipleDetails;
    private CaseData caseData;
    private uk.gov.hmcts.ecm.common.model.ccd.CaseData ecmCaseData;
    private BulkData bulkData;
    private MultipleData multipleData;
    private CCDRequest ccdRequest;
    private uk.gov.hmcts.ecm.common.model.ccd.CCDRequest ecmCcdRequest;
    private final String uri = "http://example.com";
    static final String CASE_CREATION_BY_GENERIC_CASE_DATA_SUMMARY
            = "Case with generic case data creation content summary";
    static final String CASE_CREATION_BY_GENERIC_CASE_DATA_DESCRIPTION
            = "Case with generic case data creation content description";

    @BeforeEach
    void setUp() {
        ccdClient = new CcdClient(restTemplate, userService, caseDataBuilder, ccdClientConfig, authTokenGenerator);

        ccdRequest = new CCDRequest();
        ccdRequest.setEventId("1111");
        ccdRequest.setToken("Token");
        userDetails = getUserDetails();

        caseDetails = new CaseDetails();
        caseDetails.setJurisdiction("TRIBUNALS");
        caseDetails.setCaseTypeId(ENGLANDWALES_CASE_TYPE_ID);
        caseData = new CaseData();
        caseData.setManagingOffice(TribunalOffice.LEEDS.getOfficeName());
        caseDetails.setCaseData(caseData);
        genericTypeCaseDetails = new GenericTypeCaseDetails<>();
        genericTypeCaseDetails.setJurisdiction("TRIBUNALS");
        genericTypeCaseDetails.setCaseTypeId(ENGLANDWALES_CASE_TYPE_ID);
        genericTypeCaseDetails.setCaseData(caseData);

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

        ecmCcdRequest = new uk.gov.hmcts.ecm.common.model.ccd.CCDRequest();
        ecmCcdRequest.setEventId("2222");
        ecmCcdRequest.setToken("Token");

        ecmCaseData = new uk.gov.hmcts.ecm.common.model.ccd.CaseData();
        ecmCaseData.setManagingOffice(TribunalOffice.LEEDS.getOfficeName());
    }

    private HttpHeaders creatBuildHeaders() {
        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.AUTHORIZATION, "authToken");
        headers.add("ServiceAuthorization", null);
        headers.add(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_UTF8_VALUE);
        return headers;
    }

    @Test
    void startCaseCreation() throws IOException {
        HttpEntity<Object> httpEntity = new HttpEntity<>(creatBuildHeaders());
        ResponseEntity<CCDRequest> responseEntity = new ResponseEntity<>(HttpStatus.OK);
        when(userService.getUserDetails(anyString())).thenReturn(userDetails);
        when(ccdClientConfig.buildStartCaseCreationUrl(any(), any(), any())).thenReturn(uri);
        when(restTemplate.exchange(eq(uri), eq(HttpMethod.GET), eq(httpEntity), eq(CCDRequest.class)))
                .thenReturn(responseEntity);
        ccdClient.startCaseCreation("authToken", caseDetails);
        verify(restTemplate).exchange(eq(uri), eq(HttpMethod.GET), eq(httpEntity), eq(CCDRequest.class));
        verifyNoMoreInteractions(restTemplate);
    }

    @Test
    void startGenericTypeCaseCreation() throws IOException {
        HttpEntity<Object> httpEntity = new HttpEntity<>(creatBuildHeaders());
        ResponseEntity<CCDRequest> responseEntity = new ResponseEntity<>(HttpStatus.OK);
        when(userService.getUserDetails(anyString())).thenReturn(userDetails);
        when(ccdClientConfig.buildStartCaseCreationUrl(any(), any(), any())).thenReturn(uri);
        when(restTemplate.exchange(eq(uri), eq(HttpMethod.GET), eq(httpEntity), eq(CCDRequest.class)))
                .thenReturn(responseEntity);
        ccdClient.startGenericTypeCaseCreation("authToken", genericTypeCaseDetails);
        verify(restTemplate).exchange(eq(uri), eq(HttpMethod.GET), eq(httpEntity), eq(CCDRequest.class));
        verifyNoMoreInteractions(restTemplate);
    }

    @Test
    void startCaseCreationAccepted() throws IOException {
        HttpEntity<Object> httpEntity = new HttpEntity<>(creatBuildHeaders());
        ResponseEntity<CCDRequest> responseEntity = new ResponseEntity<>(HttpStatus.OK);
        when(userService.getUserDetails(anyString())).thenReturn(userDetails);
        when(ccdClientConfig.buildStartCaseCreationTransferUrl(any(), any(), any())).thenReturn(uri);
        when(restTemplate.exchange(eq(uri), eq(HttpMethod.GET), eq(httpEntity), eq(CCDRequest.class)))
                .thenReturn(responseEntity);
        ccdClient.startCaseCreationTransfer("authToken", caseDetails);
        verify(restTemplate).exchange(eq(uri), eq(HttpMethod.GET), eq(httpEntity), eq(CCDRequest.class));
        verifyNoMoreInteractions(restTemplate);
    }

    @Test
    void startEcmCaseCreationTransferAccepted() throws IOException {
        ResponseEntity<CCDRequest> responseEntity =
            new ResponseEntity<>(HttpStatus.OK);
        when(userService.getUserDetails(anyString())).thenReturn(userDetails);
        when(ccdClientConfig.buildStartCaseCreationTransferUrl(any(), any(), any())).thenReturn(uri);
        when(restTemplate.exchange(eq(uri), eq(HttpMethod.GET), any(), eq(CCDRequest.class)))
            .thenReturn(responseEntity);
        ccdClient.startCaseCreationTransfer("authToken", caseDetails);
        verify(restTemplate).exchange(eq(uri), eq(HttpMethod.GET), any(),
            eq(CCDRequest.class));
        verifyNoMoreInteractions(restTemplate);
    }

    @Test
    void startCaseTransfer() throws IOException {
        HttpEntity<Object> httpEntity = new HttpEntity<>(creatBuildHeaders());
        ResponseEntity<CCDRequest> responseEntity = new ResponseEntity<>(HttpStatus.OK);
        when(userService.getUserDetails(anyString())).thenReturn(userDetails);
        when(ccdClientConfig.buildStartCaseTransferUrl(any(), any(), any(), any())).thenReturn(uri);
        when(restTemplate.exchange(eq(uri), eq(HttpMethod.GET), eq(httpEntity), eq(CCDRequest.class)))
                .thenReturn(responseEntity);
        ccdClient.startCaseTransfer("authToken", caseDetails.getCaseTypeId(),
                caseDetails.getJurisdiction(), caseDetails.getCaseId());
        verify(restTemplate).exchange(eq(uri), eq(HttpMethod.GET), eq(httpEntity), eq(CCDRequest.class));
        verifyNoMoreInteractions(restTemplate);
    }

    @Test
    void startCaseTransferSameCountryEccLinkedCase() throws IOException {
        var caseTypeId = ENGLANDWALES_CASE_TYPE_ID;
        var jurisdiction = "EMPLOYMENT";
        var caseId = "123";
        var httpEntity = new HttpEntity<>(creatBuildHeaders());
        var responseEntity = mock(ResponseEntity.class);
        when(responseEntity.getBody()).thenReturn(new CCDRequest());
        when(userService.getUserDetails("authToken")).thenReturn(userDetails);
        when(ccdClientConfig.buildStartCaseTransferSameCountryLinkedCaseUrl(userDetails.getUid(), jurisdiction,
                caseTypeId, caseId)).thenReturn(uri);
        when(restTemplate.exchange(eq(uri), eq(HttpMethod.GET), eq(httpEntity), eq(CCDRequest.class)))
                .thenReturn(responseEntity);

        var ccdRequest = ccdClient.startCaseTransferSameCountryEccLinkedCase(
                "authToken",
                caseTypeId,
                jurisdiction,
                caseId);

        assertNotNull(ccdRequest);
    }

    @Test
    void returnCaseCreation() throws IOException {
        HttpEntity<Object> httpEntity = new HttpEntity<>(creatBuildHeaders());
        ResponseEntity<CCDRequest> responseEntity = new ResponseEntity<>(HttpStatus.OK);
        when(userService.getUserDetails(anyString())).thenReturn(userDetails);
        when(ccdClientConfig.buildReturnCaseCreationTransferUrl(any(), any(), any(), any())).thenReturn(uri);
        when(restTemplate.exchange(eq(uri), eq(HttpMethod.GET), eq(httpEntity), eq(CCDRequest.class)))
                .thenReturn(responseEntity);
        ccdClient.returnCaseCreationTransfer("authToken",  caseDetails.getCaseTypeId(),
                caseDetails.getJurisdiction(), caseDetails.getCaseId());
        verify(restTemplate).exchange(eq(uri), eq(HttpMethod.GET), eq(httpEntity), eq(CCDRequest.class));
        verifyNoMoreInteractions(restTemplate);
    }

    @Test
    void startCaseMultipleCreation() throws IOException {
        HttpEntity<Object> httpEntity = new HttpEntity<>(creatBuildHeaders());
        ResponseEntity<CCDRequest> responseEntity = new ResponseEntity<>(HttpStatus.OK);
        when(userService.getUserDetails(anyString())).thenReturn(userDetails);
        when(ccdClientConfig.buildStartCaseMultipleCreationUrl(any(), any(), any())).thenReturn(uri);
        when(restTemplate.exchange(eq(uri), eq(HttpMethod.GET), eq(httpEntity), eq(CCDRequest.class)))
                .thenReturn(responseEntity);
        ccdClient.startCaseMultipleCreation("authToken",  caseDetails.getCaseTypeId(),
                caseDetails.getJurisdiction());
        verify(restTemplate).exchange(eq(uri), eq(HttpMethod.GET), eq(httpEntity), eq(CCDRequest.class));
        verifyNoMoreInteractions(restTemplate);
    }

    @Test
    void submitCaseCreation() throws IOException {
        HttpEntity<CaseDataContent> httpEntity = new HttpEntity<>(CaseDataContent.builder().build(),
                creatBuildHeaders());
        ResponseEntity<SubmitEvent> responseEntity = new ResponseEntity<>(HttpStatus.OK);
        when(caseDataBuilder.buildCaseDataContent(eq(caseData), eq(ccdRequest), anyString()))
                .thenReturn(CaseDataContent.builder().build());
        when(userService.getUserDetails(anyString())).thenReturn(userDetails);
        when(ccdClientConfig.buildSubmitCaseCreationUrl(any(), any(), any())).thenReturn(uri);
        when(restTemplate.exchange(eq(uri), eq(HttpMethod.POST), eq(httpEntity), eq(SubmitEvent.class)))
                .thenReturn(responseEntity);
        ccdClient.submitCaseCreation("authToken", caseDetails, ccdRequest);
        verify(restTemplate).exchange(eq(uri), eq(HttpMethod.POST), eq(httpEntity), eq(SubmitEvent.class));
        verifyNoMoreInteractions(restTemplate);
    }

    @Test
    void submitGenericTypeCaseCreation() throws IOException {
        HttpEntity<CaseDataContent> httpEntity = new HttpEntity<>(CaseDataContent.builder().build(),
                creatBuildHeaders());
        ResponseEntity<SubmitEvent> responseEntity = new ResponseEntity<>(HttpStatus.OK);
        when(caseDataBuilder.buildGenericCaseDataContent(eq(caseData), eq(ccdRequest), anyString(), anyString()))
                .thenReturn(CaseDataContent.builder().build());
        when(userService.getUserDetails(anyString())).thenReturn(userDetails);
        when(ccdClientConfig.buildSubmitCaseCreationUrl(any(), any(), any())).thenReturn(uri);
        when(restTemplate.exchange(eq(uri), eq(HttpMethod.POST), eq(httpEntity), eq(SubmitEvent.class)))
                .thenReturn(responseEntity);
        ccdClient.submitGenericTypeCaseCreation("authToken", genericTypeCaseDetails, ccdRequest,
                CASE_CREATION_BY_GENERIC_CASE_DATA_SUMMARY, CASE_CREATION_BY_GENERIC_CASE_DATA_DESCRIPTION);
        verify(restTemplate).exchange(eq(uri), eq(HttpMethod.POST), eq(httpEntity), eq(SubmitEvent.class));
        verifyNoMoreInteractions(restTemplate);
    }

    @Test
    void submitCaseCreationWithEventSummary() throws IOException {
        HttpEntity<CaseDataContent> httpEntity = new HttpEntity<>(CaseDataContent.builder().build(),
                creatBuildHeaders());
        ResponseEntity<SubmitEvent> responseEntity = new ResponseEntity<>(HttpStatus.OK);
        when(caseDataBuilder.buildCaseDataContent(eq(caseData), eq(ccdRequest), anyString()))
                .thenReturn(CaseDataContent.builder().build());
        when(userService.getUserDetails(anyString())).thenReturn(userDetails);
        when(ccdClientConfig.buildSubmitCaseCreationUrl(any(), any(), any())).thenReturn(uri);
        when(restTemplate.exchange(eq(uri), eq(HttpMethod.POST), eq(httpEntity), eq(SubmitEvent.class)))
                .thenReturn(responseEntity);

        ccdClient.submitCaseCreation("authToken", caseDetails, ccdRequest, "Test Event Summary");

        verify(restTemplate).exchange(eq(uri), eq(HttpMethod.POST), eq(httpEntity), eq(SubmitEvent.class));
        verifyNoMoreInteractions(restTemplate);
    }

    @Test
    void submitEcmCaseCreation() throws IOException {
        HttpEntity<uk.gov.hmcts.ecm.common.model.ccd.CaseDataContent> httpEntity =
            new HttpEntity<>(uk.gov.hmcts.ecm.common.model.ccd.CaseDataContent.builder().build(),
            creatBuildHeaders());
        ResponseEntity<SubmitEvent> responseEntity = new ResponseEntity<>(HttpStatus.OK);

        when(userService.getUserDetails(anyString())).thenReturn(userDetails);
        when(ccdClientConfig.buildSubmitCaseCreationUrl(any(), any(), any())).thenReturn(uri);
        when(restTemplate.exchange(endsWith(uri), eq(HttpMethod.POST), any(), eq(SubmitEvent.class)))
            .thenReturn(responseEntity);

        ccdClient.submitCaseCreation("authToken", caseDetails, ccdRequest, "Test Event Summary");

        verify(restTemplate).exchange(eq(uri), eq(HttpMethod.POST), any(), eq(SubmitEvent.class));
        verifyNoMoreInteractions(restTemplate);
    }

    @Test
    void retrieveCase() throws IOException {
        HttpEntity<Object> httpEntity = new HttpEntity<>(creatBuildHeaders());
        ResponseEntity<SubmitEvent> responseEntity = new ResponseEntity<>(HttpStatus.OK);
        when(userService.getUserDetails(anyString())).thenReturn(userDetails);
        when(ccdClientConfig.buildRetrieveCaseUrl(any(), any(), any(), any())).thenReturn(uri);
        when(restTemplate.exchange(eq(uri), eq(HttpMethod.GET), eq(httpEntity), eq(SubmitEvent.class)))
                .thenReturn(responseEntity);
        ccdClient.retrieveCase("authToken", caseDetails.getCaseTypeId(), caseDetails.getJurisdiction(),
                "111111");
        verify(restTemplate).exchange(eq(uri), eq(HttpMethod.GET), eq(httpEntity), eq(SubmitEvent.class));
        verifyNoMoreInteractions(restTemplate);
    }

    @Test
    void retrieveCases() throws IOException {
        PaginatedSearchMetadata metadata = new PaginatedSearchMetadata();
        metadata.setTotalPagesCount(1);
        ResponseEntity<PaginatedSearchMetadata> paginatedSearchMetadata = new ResponseEntity<>(metadata,
                HttpStatus.OK);
        when(userService.getUserDetails(anyString())).thenReturn(userDetails);
        when(ccdClientConfig.buildRetrieveCasesUrl(any(), any(), any(), any())).thenReturn(uri);
        when(ccdClientConfig.buildPaginationMetadataCaseUrl(any(), any(), any())).thenReturn(uri);
        HttpEntity<Object> httpEntity = new HttpEntity<>(creatBuildHeaders());
        List<SubmitEvent> submitEvents = new ArrayList<>(Arrays.asList(new SubmitEvent(), new SubmitEvent()));
        ResponseEntity<List<SubmitEvent>> responseEntity = new ResponseEntity<>(submitEvents, HttpStatus.OK);
        when(restTemplate.exchange(eq(uri), eq(HttpMethod.GET), eq(httpEntity),
                eq(new ParameterizedTypeReference<List<SubmitEvent>>(){}))).thenReturn(responseEntity);
        when(restTemplate.exchange(eq(uri), eq(HttpMethod.GET), eq(httpEntity), eq(PaginatedSearchMetadata.class)))
                .thenReturn(paginatedSearchMetadata);
        ccdClient.retrieveCases("authToken", caseDetails.getCaseTypeId(), caseDetails.getJurisdiction());
        verify(restTemplate).exchange(eq(uri), eq(HttpMethod.GET), eq(httpEntity),
                eq(new ParameterizedTypeReference<List<SubmitEvent>>(){}));
    }

    @Test
    void retrieveCasesElasticSearchForCreationManuallyCreated() throws IOException {
        String jsonQuery = "{\"size\":10000,\"query\":{\"terms\":{\"data.ethosCaseReference.keyword\":["
                + "\"2420117/2019\",\"2420118/2019\"],\"boost\":1.0}}}";
        HttpEntity<String> httpEntity = new HttpEntity<>(jsonQuery, creatBuildHeaders());
        CaseSearchResult caseSearchResult = new CaseSearchResult(2L, Arrays.asList(new SubmitEvent(),
                new SubmitEvent()));
        ResponseEntity<CaseSearchResult> responseEntity = new ResponseEntity<>(caseSearchResult, HttpStatus.OK);
        when(ccdClientConfig.buildRetrieveCasesUrlElasticSearch(any())).thenReturn(uri);
        when(restTemplate.exchange(eq(uri), eq(HttpMethod.POST), eq(httpEntity),
                eq(CaseSearchResult.class))).thenReturn(responseEntity);
        ccdClient.retrieveCasesElasticSearchForCreation("authToken",
                caseDetails.getCaseTypeId(),
                new ArrayList<>(Arrays.asList("2420117/2019", "2420118/2019")), MANUALLY_CREATED_POSITION);
        verify(restTemplate).exchange(eq(uri), eq(HttpMethod.POST), eq(httpEntity), eq(CaseSearchResult.class));
        verifyNoMoreInteractions(restTemplate);
    }

    @Test
    void retrieveCasesElasticSearchForCreationETOnline() throws IOException {
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
        String jsonQuery = "{\"size\":10000,\"query\":{\"terms\":{\"data.ethosCaseReference.keyword\":["
                + "\"2420117/2019\",\"2420118/2019\"],\"boost\":1.0}}}";
        HttpEntity<String> httpEntity = new HttpEntity<>(jsonQuery, creatBuildHeaders());
        when(restTemplate.exchange(eq(uri), eq(HttpMethod.POST), eq(httpEntity),
                eq(CaseSearchResult.class))).thenReturn(responseEntity);
        ccdClient.retrieveCasesElasticSearchForCreation("authToken",
                caseDetails.getCaseTypeId(),
                new ArrayList<>(Arrays.asList("2420117/2019", "2420118/2019")), "ET1 Online");
        verify(restTemplate).exchange(eq(uri), eq(HttpMethod.POST), eq(httpEntity), eq(CaseSearchResult.class));
        verifyNoMoreInteractions(restTemplate);
    }

    @Test
    void retrieveCasesElasticSearchSchedule() throws IOException {
        String jsonQuery = "{\"size\":5000,\"query\":{\"terms\":{\"data.ethosCaseReference.keyword\":["
                + "\"2420117/2019\",\"2420118/2019\"],\"boost\":1.0}},"
                + "\"_source\":[\"data.claimantIndType.*\",\"data.claimantType.claimant_addressUK.*\",\"data"
                + ".claimant_Company\",\"data.positionType\",\"data.ethosCaseReference\","
                + "\"data.respondentCollection.*\"]}";
        HttpEntity<String> httpEntity = new HttpEntity<>(jsonQuery, creatBuildHeaders());
        ScheduleCaseSearchResult scheduleCaseSearchResult =
                new ScheduleCaseSearchResult(2L, Arrays.asList(new SchedulePayloadEvent(),
                        new SchedulePayloadEvent()));
        ResponseEntity<ScheduleCaseSearchResult> responseEntity = new ResponseEntity<>(scheduleCaseSearchResult,
                HttpStatus.OK);
        when(ccdClientConfig.buildRetrieveCasesUrlElasticSearch(any())).thenReturn(uri);
        when(restTemplate.exchange(eq(uri), eq(HttpMethod.POST), eq(httpEntity),
                eq(ScheduleCaseSearchResult.class))).thenReturn(responseEntity);
        ccdClient.retrieveCasesElasticSearchSchedule("authToken", caseDetails.getCaseTypeId(),
                new ArrayList<>(Arrays.asList("2420117/2019", "2420118/2019")));
        verify(restTemplate).exchange(eq(uri), eq(HttpMethod.POST), eq(httpEntity),
                eq(ScheduleCaseSearchResult.class));
        verifyNoMoreInteractions(restTemplate);
    }

    @Test
    void retrieveCasesElasticSearchLabels() throws IOException {
        String jsonQuery = "{\"size\":5000,\"query\":{\"terms\":{\"data.ethosCaseReference.keyword\":["
                + "\"2420117/2019\",\"2420118/2019\"],\"boost\":1.0}},"
                + "\"_source\":[\"data.claimantIndType.*\",\"data.claimantType.*\",\"data.claimant_TypeOfClaimant\","
                + "\"data.claimant_Company\",\"data.representativeClaimantType.*\","
                + "\"data.claimantRepresentedQuestion\",\"data.respondentCollection.*\","
                + "\"data.repCollection.*\",\"data.ethosCaseReference\"]}";
        HttpEntity<String> httpEntity = new HttpEntity<>(jsonQuery, creatBuildHeaders());
        LabelCaseSearchResult labelCaseSearchResult =
                new LabelCaseSearchResult(2L, Arrays.asList(new LabelPayloadEvent(),
                        new LabelPayloadEvent()));
        ResponseEntity<LabelCaseSearchResult> responseEntity =
                new ResponseEntity<>(labelCaseSearchResult, HttpStatus.OK);
        when(ccdClientConfig.buildRetrieveCasesUrlElasticSearch(any())).thenReturn(uri);
        when(restTemplate.exchange(eq(uri), eq(HttpMethod.POST), eq(httpEntity),
                eq(LabelCaseSearchResult.class))).thenReturn(responseEntity);
        ccdClient.retrieveCasesElasticSearchLabels("authToken",
                caseDetails.getCaseTypeId(), new ArrayList<>(Arrays.asList("2420117/2019", "2420118/2019")));
        verify(restTemplate).exchange(eq(uri), eq(HttpMethod.POST), eq(httpEntity), eq(LabelCaseSearchResult.class));
        verifyNoMoreInteractions(restTemplate);
    }

    @Test
    void retrieveBulkCasesElasticSearch() throws IOException {
        String jsonQuery = "{\"size\":10000,\"query\":{\"terms\":{"
                + "\"data.multipleReference.keyword\":[\"2400001/2020\"],\"boost\":1.0}}}";
        HttpEntity<String> httpEntity = new HttpEntity<>(jsonQuery, creatBuildHeaders());
        BulkCaseSearchResult bulkCaseSearchResult = new BulkCaseSearchResult(2L,
                Arrays.asList(new SubmitBulkEvent(), new SubmitBulkEvent()));
        ResponseEntity<BulkCaseSearchResult> responseEntity = new ResponseEntity<>(bulkCaseSearchResult, HttpStatus.OK);
        when(ccdClientConfig.buildRetrieveCasesUrlElasticSearch(any())).thenReturn(uri);
        when(restTemplate.exchange(eq(uri), eq(HttpMethod.POST), eq(httpEntity),
                eq(BulkCaseSearchResult.class))).thenReturn(responseEntity);
        ccdClient.retrieveBulkCasesElasticSearch("authToken",
                caseDetails.getCaseTypeId(), "2400001/2020");
        verify(restTemplate).exchange(eq(uri), eq(HttpMethod.POST), eq(httpEntity), eq(BulkCaseSearchResult.class));
        verifyNoMoreInteractions(restTemplate);
    }

    @Test
    void retrieveMultipleCasesElasticSearch() throws IOException {
        String jsonQuery = "{\"size\":10000,\"query\":{\"terms\""
                + ":{\"data.multipleReference.keyword\":[\"2400001/2020\"],\"boost\":1.0}}}";
        HttpEntity<String> httpEntity = new HttpEntity<>(jsonQuery, creatBuildHeaders());
        MultipleCaseSearchResult multipleCaseSearchResult =
                new MultipleCaseSearchResult(2L, Arrays.asList(new SubmitMultipleEvent(),
                        new SubmitMultipleEvent()));
        ResponseEntity<MultipleCaseSearchResult> responseEntity =
                new ResponseEntity<>(multipleCaseSearchResult, HttpStatus.OK);
        when(ccdClientConfig.buildRetrieveCasesUrlElasticSearch(any())).thenReturn(uri);
        when(restTemplate.exchange(eq(uri), eq(HttpMethod.POST), eq(httpEntity),
                eq(MultipleCaseSearchResult.class))).thenReturn(responseEntity);
        ccdClient.retrieveMultipleCasesElasticSearch("authToken",
                caseDetails.getCaseTypeId(), "2400001/2020");
        verify(restTemplate).exchange(eq(uri), eq(HttpMethod.POST), eq(httpEntity),
                eq(MultipleCaseSearchResult.class));
        verifyNoMoreInteractions(restTemplate);
    }

    @Test
    void retrieveCasesVenueAndRangeDateElasticSearch() throws IOException {
        CaseSearchResult caseSearchResult = new CaseSearchResult(2L,
            Arrays.asList(new SubmitEvent(), new SubmitEvent()));
        ResponseEntity<CaseSearchResult> responseEntity = new ResponseEntity<>(caseSearchResult, HttpStatus.OK);
        when(ccdClientConfig.buildRetrieveCasesUrlElasticSearch(any())).thenReturn(uri);
        when(restTemplate.exchange(eq(uri), eq(HttpMethod.POST), any(),
            eq(CaseSearchResult.class))).thenReturn(responseEntity);
        ccdClient.retrieveCasesVenueAndDateElasticSearch("authToken",
            caseDetails.getCaseTypeId(), "2019-09-23",
            "2019-09-24", "Manchester", LISTING_VENUE_FIELD_NAME, "Manchester");
        verify(restTemplate).exchange(eq(uri), eq(HttpMethod.POST), any(), eq(CaseSearchResult.class));
        verifyNoMoreInteractions(restTemplate);
    }

    @Test
    void retrieveCasesVenueAndSingleDateElasticSearch() throws IOException {
        CaseSearchResult caseSearchResult = new CaseSearchResult(2L,
                Arrays.asList(new SubmitEvent(), new SubmitEvent()));
        ResponseEntity<CaseSearchResult> responseEntity = new ResponseEntity<>(caseSearchResult, HttpStatus.OK);
        when(ccdClientConfig.buildRetrieveCasesUrlElasticSearch(any())).thenReturn(uri);
        when(restTemplate.exchange(eq(uri), eq(HttpMethod.POST), any(),
                eq(CaseSearchResult.class))).thenReturn(responseEntity);
        ccdClient.retrieveCasesVenueAndDateElasticSearch("authToken",
                caseDetails.getCaseTypeId(), "2019-09-23",
                "2019-09-24", "Manchester", LISTING_VENUE_FIELD_NAME, "Manchester");
        verify(restTemplate).exchange(eq(uri), eq(HttpMethod.POST), any(), eq(CaseSearchResult.class));
        verifyNoMoreInteractions(restTemplate);
    }

    @Test
    void retrieveCasesAllVenuesAndSingleDateElasticSearch() throws IOException {
        CaseSearchResult caseSearchResult = new CaseSearchResult(2L, Arrays.asList(new SubmitEvent(),
                new SubmitEvent()));
        ResponseEntity<CaseSearchResult> responseEntity = new ResponseEntity<>(caseSearchResult, HttpStatus.OK);
        when(ccdClientConfig.buildRetrieveCasesUrlElasticSearch(any())).thenReturn(uri);
        when(restTemplate.exchange(eq(uri), eq(HttpMethod.POST), any(),
                eq(CaseSearchResult.class))).thenReturn(responseEntity);
        ccdClient.retrieveCasesVenueAndDateElasticSearch("authToken",
                caseDetails.getCaseTypeId(), "2019-09-23",
                "2019-09-23", ALL_VENUES, ALL_VENUES, "Manchester");
        verify(restTemplate).exchange(eq(uri), eq(HttpMethod.POST), any(), eq(CaseSearchResult.class));
        verifyNoMoreInteractions(restTemplate);
    }

    @Test
    void retrieveCasesGenericReportElasticSearchWithTribunalOffice() throws IOException {
        String jsonQuery = "{\"size\":10000,\"query\":{\"bool\":{\"must\":[{\"match\":{\"data.managingOffice\""
                + ":{\"query\":\"Leeds\",\"operator\":\"OR\",\"prefix_length\":0,\"max_expansions\":50,"
                + "\"fuzzy_transpositions\":true,\"lenient\":false,\"zero_terms_query\":\"NONE\","
                + "\"auto_generate_synonyms_phrase_query\":true,\"boost\":1.0}}}],\"filter\":[{\"range\""
                + ":{\"data.bfActions.value.bfDate\":{\"from\":\"2019-09-23T00:00:00.000\",\"to\":\""
                + "2019-09-24T23:59:59.000\",\"include_lower\":true,\"include_upper\":true,\"boost\":1.0}}}],"
                + "\"adjust_pure_negative\":true,\"boost\":1.0}}}";
        HttpEntity<String> httpEntity = new HttpEntity<>(jsonQuery, creatBuildHeaders());
        CaseSearchResult caseSearchResult = new CaseSearchResult(2L,
                Arrays.asList(new SubmitEvent(), new SubmitEvent()));
        ResponseEntity<CaseSearchResult> responseEntity = new ResponseEntity<>(caseSearchResult, HttpStatus.OK);
        when(ccdClientConfig.buildRetrieveCasesUrlElasticSearch(any())).thenReturn(uri);
        when(restTemplate.exchange(eq(uri), eq(HttpMethod.POST), eq(httpEntity),
                eq(CaseSearchResult.class))).thenReturn(responseEntity);
        ccdClient.retrieveCasesGenericReportElasticSearch("authToken", caseDetails.getCaseTypeId(),
                TribunalOffice.valueOfOfficeName(caseDetails.getCaseData().getManagingOffice()), "2019-09-23",
                "2019-09-24", BROUGHT_FORWARD_REPORT);
        verify(restTemplate).exchange(eq(uri), eq(HttpMethod.POST), eq(httpEntity), eq(CaseSearchResult.class));
        verifyNoMoreInteractions(restTemplate);
    }

    @Test
    void testRetrieveCasesGenericReportElasticSearchNoTribunalOffice() throws IOException {
        String jsonQuery = "{\"size\":10000,\"query\":{\"bool\":{\"filter\":[{\"range\":"
                + "{\"data.bfActions.value.bfDate\":{\"from\":\"2019-09-23T00:00:00.000\","
                +  "\"to\":\"2019-09-24T23:59:59.000\","
                + "\"include_lower\":true,\"include_upper\":true,\"boost\":1.0}}}],"
                + "\"adjust_pure_negative\":true,\"boost\":"
                + "1.0}}}";
        HttpEntity<String> httpEntity = new HttpEntity<>(jsonQuery, creatBuildHeaders());
        CaseSearchResult caseSearchResult = new CaseSearchResult(2L,
                Arrays.asList(new SubmitEvent(), new SubmitEvent()));
        ResponseEntity<CaseSearchResult> responseEntity = new ResponseEntity<>(caseSearchResult, HttpStatus.OK);
        when(ccdClientConfig.buildRetrieveCasesUrlElasticSearch(any())).thenReturn(uri);
        when(restTemplate.exchange(eq(uri), eq(HttpMethod.POST), eq(httpEntity),
                eq(CaseSearchResult.class))).thenReturn(responseEntity);
        ccdClient.retrieveCasesGenericReportElasticSearch("authToken", caseDetails.getCaseTypeId(), null, "2019-09-23",
                "2019-09-24", BROUGHT_FORWARD_REPORT);
        verify(restTemplate).exchange(eq(uri), eq(HttpMethod.POST), eq(httpEntity), eq(CaseSearchResult.class));
        verifyNoMoreInteractions(restTemplate);
    }

    @Test
    void retrieveCasesGenericReportElasticSearchCasesCompleted() throws IOException {

        String jsonQuery = "{\"size\":10000,\"query\":{\"bool\":{\"must\":[{\"match\":"
                + "{\"data.managingOffice\":{\"query\":\"Leeds\",\"operator\":\"OR\",\"prefix_length\""
                + ":0,\"max_expansions\":50,\"fuzzy_transpositions\":true,\"lenient\":false,"
                + "\"zero_terms_query\":\"NONE\",\"auto_generate_synonyms_phrase_query\":true,"
                + "\"boost\":1.0}}}],\"filter\":[{\"range\":{\"data.hearingCollection.value."
                + "hearingDateCollection.value.listedDate\":{\"from\":\"2019-09-24T00:00:00.000\","
                + "\"to\":\"2019-09-24T23:59:59.000\",\"include_lower\":true,\"include_upper\":true,"
                + "\"boost\":1.0}}}],\"adjust_pure_negative\":true,\"boost\":1.0}}}";
        HttpEntity<String> httpEntity = new HttpEntity<>(jsonQuery, creatBuildHeaders());
        CaseSearchResult caseSearchResult = new CaseSearchResult(2L,
                Arrays.asList(new SubmitEvent(), new SubmitEvent()));
        ResponseEntity<CaseSearchResult> responseEntity = new ResponseEntity<>(caseSearchResult, HttpStatus.OK);
        when(ccdClientConfig.buildRetrieveCasesUrlElasticSearch(any())).thenReturn(uri);
        when(restTemplate.exchange(eq(uri), eq(HttpMethod.POST), eq(httpEntity),
                eq(CaseSearchResult.class))).thenReturn(responseEntity);
        ccdClient.retrieveCasesGenericReportElasticSearch("authToken", caseDetails.getCaseTypeId(),
               TribunalOffice.valueOfOfficeName(caseDetails.getCaseData().getManagingOffice()), "2019-09-24",
                "2019-09-24", CASES_COMPLETED_REPORT);
        verify(restTemplate).exchange(eq(uri), eq(HttpMethod.POST), eq(httpEntity), eq(CaseSearchResult.class));
        verifyNoMoreInteractions(restTemplate);
    }

    @Test
    void retrieveCasesGenericReportElasticSearchCasesTimeToFirstHearing() throws IOException {
        String jsonQuery = "{\"size\":10000,\"query\":{\"bool\":{\"must\":[{\"match\":"
                + "{\"data.managingOffice\":{\"query\":\"Leeds\",\"operator\":\"OR\","
                + "\"prefix_length\":0,\"max_expansions\":50,\"fuzzy_transpositions\""
                + ":true,\"lenient\":false,\"zero_terms_query\":\"NONE\","
                + "\"auto_generate_synonyms_phrase_query\":true,\"boost\":1.0}}}],"
                + "\"filter\":[{\"range\":{\"data.hearingCollection."
                + "value.hearingDateCollection.value.listedDate\":"
                + "{\"from\":\"2019-09-24T00:00:00.000\",\"to\":\"2019-09-24T23:59:59.000\""
                + ",\"include_lower\":true,\"include_upper\":true,\""
                + "boost\":1.0}}}],\"adjust_pure_negative\":true,\"boost\":1.0}}}";
        HttpEntity<String> httpEntity = new HttpEntity<>(jsonQuery, creatBuildHeaders());
        CaseSearchResult caseSearchResult = new CaseSearchResult(2L,
                Arrays.asList(new SubmitEvent(), new SubmitEvent()));
        ResponseEntity<CaseSearchResult> responseEntity = new ResponseEntity<>(caseSearchResult, HttpStatus.OK);
        when(ccdClientConfig.buildRetrieveCasesUrlElasticSearch(any())).thenReturn(uri);
        when(restTemplate.exchange(eq(uri), eq(HttpMethod.POST), eq(httpEntity),
                eq(CaseSearchResult.class))).thenReturn(responseEntity);
        ccdClient.retrieveCasesGenericReportElasticSearch("authToken", caseDetails.getCaseTypeId(),
                TribunalOffice.valueOfOfficeName(caseDetails.getCaseData().getManagingOffice()), "2019-09-24",
                "2019-09-24", TIME_TO_FIRST_HEARING_REPORT);
        verify(restTemplate).exchange(eq(uri), eq(HttpMethod.POST), eq(httpEntity), eq(CaseSearchResult.class));
        verifyNoMoreInteractions(restTemplate);
    }

    @Test
    void retrieveCasesGenericReportElasticSearchCasesLocalReportCaseSource() throws IOException {
        String jsonQuery = "{\"size\":10000,\"query\":{\"bool\":{\"must\":[{\"match\":{\"data.managingOffice\":"
                + "{\"query\":\"Leeds\",\"operator\":\"OR\",\"prefix_length\":0,\"max_expansions\":50"
                + ",\"fuzzy_transpositions\":true,\"lenient\":false,\"zero_terms_query\":\"NONE\","
                + "\"auto_generate_synonyms_phrase_query\":true,\"boost\":1.0}}}],\"filter\":[{\"range\":"
                + "{\"data.receiptDate\":{\"from\":\"2019-09-24T00:00:00.000\",\"to\":\"2019-09-24T23:59:59.000\","
                + "\"include_lower\":true,\"include_upper\":true,\"boost\":1.0}}}],\"adjust_pure_negative\":true"
                + ",\"boost\":1.0}}}";

        HttpEntity<String> httpEntity = new HttpEntity<>(jsonQuery, creatBuildHeaders());
        CaseSearchResult caseSearchResult = new CaseSearchResult(2L,
                Arrays.asList(new SubmitEvent(), new SubmitEvent()));
        ResponseEntity<CaseSearchResult> responseEntity = new ResponseEntity<>(caseSearchResult, HttpStatus.OK);
        when(ccdClientConfig.buildRetrieveCasesUrlElasticSearch(any())).thenReturn(uri);
        when(restTemplate.exchange(eq(uri), eq(HttpMethod.POST), eq(httpEntity),
                eq(CaseSearchResult.class))).thenReturn(responseEntity);
        ccdClient.retrieveCasesGenericReportElasticSearch("authToken",
                caseDetails.getCaseTypeId(),
                TribunalOffice.valueOfOfficeName(caseDetails.getCaseData().getManagingOffice()),
                "2019-09-24", "2019-09-24", CASE_SOURCE_LOCAL_REPORT);
        verify(restTemplate).exchange(eq(uri), eq(HttpMethod.POST), eq(httpEntity), eq(CaseSearchResult.class));
        verifyNoMoreInteractions(restTemplate);
    }

    @Test
    void retrieveCasesGenericReportElasticSearchLiveCaseload() throws IOException {
        String jsonQuery = "{\"size\":10000,\"query\":{\"bool\":{\"must\":[{\"match\":"
                + "{\"data.managingOffice\":{\"query\":\"Leeds\",\"operator\":\"OR\""
                + ",\"prefix_length\":0,\"max_expansions\":50,"
                + "\"fuzzy_transpositions\":true,\"lenient\":false,\"zero_terms_query\""
                + ":\"NONE\",\"auto_generate_synonyms_phrase_query\":true,"
                + "\"boost\":1.0}}}],\"filter\":[{\"range\":"
                + "{\"data.preAcceptCase.dateAccepted\":{\"from\":\"2019-09-24T00:00:00.000\","
                + "\"to\":\"2019-09-24T23:59:59.000\",\"include_lower\":true,\"include_upper\":true,"
                + "\"boost\":1.0}}}],\"adjust_pure_negative\":true,\"boost\":1.0}}}";
        HttpEntity<String> httpEntity = new HttpEntity<>(jsonQuery, creatBuildHeaders());
        CaseSearchResult caseSearchResult = new CaseSearchResult(2L,
                Arrays.asList(new SubmitEvent(), new SubmitEvent()));
        ResponseEntity<CaseSearchResult> responseEntity = new ResponseEntity<>(caseSearchResult, HttpStatus.OK);
        when(ccdClientConfig.buildRetrieveCasesUrlElasticSearch(any())).thenReturn(uri);
        when(restTemplate.exchange(eq(uri), eq(HttpMethod.POST), eq(httpEntity),
                eq(CaseSearchResult.class))).thenReturn(responseEntity);
        ccdClient.retrieveCasesGenericReportElasticSearch("authToken", caseDetails.getCaseTypeId(),
                TribunalOffice.valueOfOfficeName(caseDetails.getCaseData().getManagingOffice()), "2019-09-24",
                "2019-09-24", LIVE_CASELOAD_REPORT);
        verify(restTemplate).exchange(eq(uri), eq(HttpMethod.POST), eq(httpEntity), eq(CaseSearchResult.class));
        verifyNoMoreInteractions(restTemplate);
    }

    @Test
    void retrieveBulkCases() throws IOException {
        PaginatedSearchMetadata metadata = new PaginatedSearchMetadata();
        metadata.setTotalPagesCount(1);
        ResponseEntity<PaginatedSearchMetadata> paginatedSearchMetadata = new ResponseEntity<>(metadata, HttpStatus.OK);
        when(userService.getUserDetails(anyString())).thenReturn(userDetails);
        when(ccdClientConfig.buildRetrieveCasesUrl(any(), any(), any(), any())).thenReturn(uri);
        when(ccdClientConfig.buildPaginationMetadataCaseUrl(any(), any(), any())).thenReturn(uri);
        HttpEntity<Object> httpEntity = new HttpEntity<>(creatBuildHeaders());
        List<SubmitBulkEvent> submitBulkEvents =
                new ArrayList<>(Arrays.asList(new SubmitBulkEvent(), new SubmitBulkEvent()));
        ResponseEntity<List<SubmitBulkEvent>> responseEntity = new ResponseEntity<>(submitBulkEvents, HttpStatus.OK);
        when(restTemplate.exchange(eq(uri), eq(HttpMethod.GET), eq(httpEntity),
                eq(new ParameterizedTypeReference<List<SubmitBulkEvent>>(){}))).thenReturn(responseEntity);
        when(restTemplate.exchange(eq(uri), eq(HttpMethod.GET), eq(httpEntity),
                eq(PaginatedSearchMetadata.class))).thenReturn(paginatedSearchMetadata);
        ccdClient.retrieveBulkCases("authToken", bulkDetails.getCaseTypeId(), bulkDetails.getJurisdiction());
        verify(restTemplate).exchange(eq(uri), eq(HttpMethod.GET), eq(httpEntity),
                eq(new ParameterizedTypeReference<List<SubmitBulkEvent>>(){}));
    }

    @Test
    void startEventForCase() throws IOException {
        HttpEntity<Object> httpEntity = new HttpEntity<>(creatBuildHeaders());
        ResponseEntity<CCDRequest> responseEntity = new ResponseEntity<>(HttpStatus.OK);
        when(userService.getUserDetails(anyString())).thenReturn(userDetails);
        when(ccdClientConfig.buildStartEventForCaseUrl(any(), any(), any(), any())).thenReturn(uri);
        when(restTemplate.exchange(eq(uri), eq(HttpMethod.GET), eq(httpEntity),
                eq(CCDRequest.class))).thenReturn(responseEntity);
        ccdClient.startEventForCase("authToken", caseDetails.getCaseTypeId(),
                caseDetails.getJurisdiction(), "1111");
        verify(restTemplate).exchange(eq(uri), eq(HttpMethod.GET), eq(httpEntity), eq(CCDRequest.class));
        verifyNoMoreInteractions(restTemplate);
    }

    @Test
    void startEventForCaseAPIRole() throws IOException {
        HttpEntity<Object> httpEntity = new HttpEntity<>(creatBuildHeaders());
        ResponseEntity<CCDRequest> responseEntity = new ResponseEntity<>(HttpStatus.OK);
        when(userService.getUserDetails(anyString())).thenReturn(userDetails);
        when(ccdClientConfig.buildStartEventForCaseUrlAPIRole(any(), any(), any(), any())).thenReturn(uri);
        when(restTemplate.exchange(eq(uri), eq(HttpMethod.GET), eq(httpEntity),
                eq(CCDRequest.class))).thenReturn(responseEntity);
        ccdClient.startEventForCaseAPIRole("authToken", caseDetails.getCaseTypeId(),
                caseDetails.getJurisdiction(), "1111");
        verify(restTemplate).exchange(eq(uri), eq(HttpMethod.GET), eq(httpEntity), eq(CCDRequest.class));
        verifyNoMoreInteractions(restTemplate);
    }

    @Test
    void startEventForCaseBulkSingle() throws IOException {
        HttpEntity<Object> httpEntity = new HttpEntity<>(creatBuildHeaders());
        ResponseEntity<CCDRequest> responseEntity = new ResponseEntity<>(HttpStatus.OK);
        when(userService.getUserDetails(anyString())).thenReturn(userDetails);
        when(ccdClientConfig.buildStartEventForCaseUrlBulkSingle(any(), any(), any(), any())).thenReturn(uri);
        when(restTemplate.exchange(eq(uri), eq(HttpMethod.GET), eq(httpEntity),
                eq(CCDRequest.class))).thenReturn(responseEntity);
        ccdClient.startEventForCaseBulkSingle("authToken", caseDetails.getCaseTypeId(),
                caseDetails.getJurisdiction(), "1111");
        verify(restTemplate).exchange(eq(uri), eq(HttpMethod.GET), eq(httpEntity), eq(CCDRequest.class));
        verifyNoMoreInteractions(restTemplate);
    }

    @Test
    void startEventForCasePreAcceptBulkSingle() throws IOException {
        HttpEntity<Object> httpEntity = new HttpEntity<>(creatBuildHeaders());
        ResponseEntity<CCDRequest> responseEntity = new ResponseEntity<>(HttpStatus.OK);
        when(userService.getUserDetails(anyString())).thenReturn(userDetails);
        when(ccdClientConfig.buildStartEventForCaseUrlPreAcceptBulkSingle(any(), any(), any(), any())).thenReturn(uri);
        when(restTemplate.exchange(eq(uri), eq(HttpMethod.GET), eq(httpEntity),
                eq(CCDRequest.class))).thenReturn(responseEntity);
        ccdClient.startEventForCasePreAcceptBulkSingle("authToken",
                caseDetails.getCaseTypeId(), caseDetails.getJurisdiction(), "1111");
        verify(restTemplate).exchange(eq(uri), eq(HttpMethod.GET), eq(httpEntity), eq(CCDRequest.class));
        verifyNoMoreInteractions(restTemplate);
    }

    @Test
    void startBulkEventForCase() throws IOException {
        HttpEntity<Object> httpEntity = new HttpEntity<>(creatBuildHeaders());
        ResponseEntity<CCDRequest> responseEntity = new ResponseEntity<>(HttpStatus.OK);
        when(userService.getUserDetails(anyString())).thenReturn(userDetails);
        when(ccdClientConfig.buildStartEventForBulkCaseUrl(any(), any(), any(), any())).thenReturn(uri);
        when(restTemplate.exchange(eq(uri), eq(HttpMethod.GET), eq(httpEntity),
                eq(CCDRequest.class))).thenReturn(responseEntity);
        ccdClient.startBulkEventForCase("authToken", bulkDetails.getCaseTypeId(),
                bulkDetails.getJurisdiction(), "1111");
        verify(restTemplate).exchange(eq(uri), eq(HttpMethod.GET), eq(httpEntity), eq(CCDRequest.class));
        verifyNoMoreInteractions(restTemplate);
    }

    @Test
    void startBulkAmendEventForCase() throws IOException {
        HttpEntity<Object> httpEntity = new HttpEntity<>(creatBuildHeaders());
        ResponseEntity<CCDRequest> responseEntity = new ResponseEntity<>(HttpStatus.OK);
        when(userService.getUserDetails(anyString())).thenReturn(userDetails);
        when(ccdClientConfig.buildStartEventForBulkAmendCaseUrl(any(), any(), any(), any())).thenReturn(uri);
        when(restTemplate.exchange(eq(uri), eq(HttpMethod.GET), eq(httpEntity),
                eq(CCDRequest.class))).thenReturn(responseEntity);
        ccdClient.startBulkAmendEventForCase("authToken", bulkDetails.getCaseTypeId(),
                bulkDetails.getJurisdiction(), "1111");
        verify(restTemplate).exchange(eq(uri), eq(HttpMethod.GET), eq(httpEntity), eq(CCDRequest.class));
        verifyNoMoreInteractions(restTemplate);
    }

    @Test
    void submitEventForCase() throws IOException {
        HttpEntity<CaseDataContent> httpEntity = new HttpEntity<>(CaseDataContent.builder().build(),
                creatBuildHeaders());
        ResponseEntity<SubmitEvent> responseEntity = new ResponseEntity<>(HttpStatus.OK);
        when(caseDataBuilder.buildCaseDataContent(caseData, ccdRequest, UPDATE_EVENT_SUMMARY, null))
                .thenReturn(CaseDataContent.builder().build());
        when(userService.getUserDetails(anyString())).thenReturn(userDetails);
        when(ccdClientConfig.buildSubmitEventForCaseUrl(any(), any(), any(), any())).thenReturn(uri);
        when(restTemplate.exchange(eq(uri), eq(HttpMethod.POST), eq(httpEntity),
                eq(SubmitEvent.class))).thenReturn(responseEntity);
        ccdClient.submitEventForCase("authToken", caseData, caseDetails.getCaseTypeId(),
                caseDetails.getJurisdiction(), ccdRequest, "111111");
        verify(restTemplate).exchange(eq(uri), eq(HttpMethod.POST), eq(httpEntity), eq(SubmitEvent.class));
        verifyNoMoreInteractions(restTemplate);
    }

    @Test
    void submitEventForCaseWithCcdSubmitEventParams() throws IOException {
        var eventSummary = "My Event";
        var eventDescription = "Event Description";
        HttpEntity<CaseDataContent> httpEntity = new HttpEntity<>(CaseDataContent.builder().build(),
                creatBuildHeaders());
        ResponseEntity<SubmitEvent> responseEntity = new ResponseEntity<>(HttpStatus.OK);
        when(caseDataBuilder.buildCaseDataContent(caseData, ccdRequest, eventSummary, eventDescription))
                .thenReturn(CaseDataContent.builder().build());
        when(userService.getUserDetails(anyString())).thenReturn(userDetails);
        when(ccdClientConfig.buildSubmitEventForCaseUrl(any(), any(), any(), any())).thenReturn(uri);
        when(restTemplate.exchange(eq(uri), eq(HttpMethod.POST), eq(httpEntity),
                eq(SubmitEvent.class))).thenReturn(responseEntity);

        var params = CcdSubmitEventParams.builder()
                .authToken("authToken")
                .ccdRequest(ccdRequest)
                .caseData(caseData)
                .eventSummary(eventSummary)
                .eventDescription(eventDescription)
                .jurisdiction(caseDetails.getJurisdiction())
                .caseId("111111")
                .build();

        ccdClient.submitEventForCase(params);
        verify(restTemplate).exchange(eq(uri), eq(HttpMethod.POST), eq(httpEntity), eq(SubmitEvent.class));
        verifyNoMoreInteractions(restTemplate);
    }

    @Test
    void submitBulkEventForCase() throws IOException {
        HttpEntity<CaseDataContent> httpEntity = new HttpEntity<>(CaseDataContent.builder().build(),
                creatBuildHeaders());
        ResponseEntity<SubmitBulkEvent> responseEntity = new ResponseEntity<>(HttpStatus.OK);
        when(caseDataBuilder.buildBulkDataContent(eq(bulkData), eq(ccdRequest),
                anyString())).thenReturn(CaseDataContent.builder().build());
        when(userService.getUserDetails(anyString())).thenReturn(userDetails);
        when(ccdClientConfig.buildSubmitEventForCaseUrl(any(), any(), any(), any())).thenReturn(uri);
        when(restTemplate.exchange(eq(uri), eq(HttpMethod.POST), eq(httpEntity),
                eq(SubmitBulkEvent.class))).thenReturn(responseEntity);
        ccdClient.submitBulkEventForCase("authToken", bulkData, bulkDetails.getCaseTypeId(),
                bulkDetails.getJurisdiction(), ccdRequest, "111111");
        verify(restTemplate).exchange(eq(uri), eq(HttpMethod.POST), eq(httpEntity), eq(SubmitBulkEvent.class));
        verifyNoMoreInteractions(restTemplate);
    }

    @Test
    void submitMultipleEventForCase() throws IOException {
        HttpEntity<CaseDataContent> httpEntity = new HttpEntity<>(CaseDataContent.builder().build(),
                creatBuildHeaders());
        ResponseEntity<SubmitMultipleEvent> responseEntity = new ResponseEntity<>(HttpStatus.OK);
        when(caseDataBuilder.buildMultipleDataContent(eq(multipleData), eq(ccdRequest),
                anyString())).thenReturn(CaseDataContent.builder().build());
        when(userService.getUserDetails(anyString())).thenReturn(userDetails);
        when(ccdClientConfig.buildSubmitEventForCaseUrl(any(), any(), any(), any())).thenReturn(uri);
        when(restTemplate.exchange(eq(uri), eq(HttpMethod.POST), eq(httpEntity), eq(SubmitMultipleEvent.class)))
                .thenReturn(responseEntity);
        ccdClient.submitMultipleEventForCase("authToken", multipleData, multipleDetails.getCaseTypeId(),
                multipleDetails.getJurisdiction(), ccdRequest, "111111");
        verify(restTemplate).exchange(eq(uri), eq(HttpMethod.POST), eq(httpEntity), eq(SubmitMultipleEvent.class));
        verifyNoMoreInteractions(restTemplate);
    }

    @Test
    void submitMultipleCreation() throws IOException {
        HttpEntity<CaseDataContent> httpEntity = new HttpEntity<>(CaseDataContent.builder().build(),
                creatBuildHeaders());
        ResponseEntity<SubmitMultipleEvent> responseEntity = new ResponseEntity<>(HttpStatus.OK);
        when(caseDataBuilder.buildMultipleDataContent(eq(multipleData), eq(ccdRequest), anyString()))
                .thenReturn(CaseDataContent.builder().build());
        when(userService.getUserDetails(anyString())).thenReturn(userDetails);
        when(ccdClientConfig.buildSubmitCaseCreationUrl(any(), any(), any())).thenReturn(uri);
        when(restTemplate.exchange(eq(uri), eq(HttpMethod.POST), eq(httpEntity), eq(SubmitMultipleEvent.class)))
                .thenReturn(responseEntity);
        ccdClient.submitMultipleCreation("authToken", multipleData, multipleDetails.getCaseTypeId(),
                multipleDetails.getJurisdiction(), ccdRequest);
        verify(restTemplate).exchange(eq(uri), eq(HttpMethod.POST), eq(httpEntity), eq(SubmitMultipleEvent.class));
        verifyNoMoreInteractions(restTemplate);
    }

    @Test
    void buildHeaders() throws IOException {
        when(authTokenGenerator.generate()).thenReturn("authString");
        HttpHeaders httpHeaders = ccdClient.buildHeaders("authString");
        assertEquals("[Authorization:\"authString\", ServiceAuthorization:\"authString\", "
                + "Content-Type:\"application/json;charset=UTF-8\"]", httpHeaders.toString());
    }

    @Test
    void retrieveMultipleCasesElasticSearchWithRetries() throws IOException {
        SubmitMultipleEvent submitMultipleEvent = new SubmitMultipleEvent();
        MultipleData multipleData = new MultipleData();
        multipleData.setMultipleReference("2400001/2020");
        submitMultipleEvent.setCaseData(multipleData);
        MultipleCaseSearchResult multipleCaseSearchResult =
                new MultipleCaseSearchResult(1L, Collections.singletonList(submitMultipleEvent));
        ResponseEntity<MultipleCaseSearchResult> responseEntity = new ResponseEntity<>(multipleCaseSearchResult,
                HttpStatus.OK);
        when(ccdClientConfig.buildRetrieveCasesUrlElasticSearch(any())).thenReturn(uri);
        String jsonQuery = "{\"size\":10000,\"query\":{\"terms\":{\"data.multipleReference.keyword\""
                + ":[\"2400001/2020\"],\"boost\":1.0}}}";
        HttpEntity<String> httpEntity = new HttpEntity<>(jsonQuery, creatBuildHeaders());
        when(restTemplate.exchange(eq(uri), eq(HttpMethod.POST), eq(httpEntity), eq(MultipleCaseSearchResult.class)))
                .thenReturn(responseEntity);
        ccdClient.retrieveMultipleCasesElasticSearchWithRetries("authToken",
                caseDetails.getCaseTypeId(), "2400001/2020");
        verify(restTemplate).exchange(eq(uri), eq(HttpMethod.POST), eq(httpEntity),
                eq(MultipleCaseSearchResult.class));
        verifyNoMoreInteractions(restTemplate);
    }

    @Test
    void testExecuteElasticSearch() throws IOException {
        var elasticSearchQuery = "{\"size\":10000,\"query\":{\"bool\":{\"must_not\":[{\"match\":{\"state\""
                + ":{\"query\":\"Closed\",\"operator\":\"OR\",\"prefix_length\":0,\"max_expansions\""
                + ":50,\"fuzzy_transpositions\":true,\"lenient\":false,\"zero_terms_query\":\"NONE\","
                + "\"auto_generate_synonyms_phrase_query\":true,\"boost\":1.0}}}],"
                + "\"adjust_pure_negative\":true,\"boost\":1.0}}}";
        var httpEntity = new HttpEntity<>(elasticSearchQuery, creatBuildHeaders());
        CaseSearchResult caseSearchResult = new CaseSearchResult(2L,
                Arrays.asList(new SubmitEvent(), new SubmitEvent()));
        ResponseEntity<CaseSearchResult> responseEntity = new ResponseEntity<>(caseSearchResult, HttpStatus.OK);
        when(ccdClientConfig.buildRetrieveCasesUrlElasticSearch(any())).thenReturn(uri);
        when(restTemplate.exchange(eq(uri), eq(HttpMethod.POST), eq(httpEntity),
                eq(CaseSearchResult.class))).thenReturn(responseEntity);
        ccdClient.executeElasticSearch("authToken", caseDetails.getCaseTypeId(), elasticSearchQuery);
        verify(restTemplate).exchange(eq(uri), eq(HttpMethod.POST), eq(httpEntity), eq(CaseSearchResult.class));
        verifyNoMoreInteractions(restTemplate);
    }

    @Test
    void testCasesAwaitingJudgmentSearch() throws IOException {
        var elasticSearchQuery = "{\"size\":10000,\"query\": {\"match_all\":{} }}";
        var httpEntity = new HttpEntity<>(elasticSearchQuery, creatBuildHeaders());
        var searchResult = new CasesAwaitingJudgmentSearchResult(2L,
                Arrays.asList(new CasesAwaitingJudgmentSubmitEvent(), new CasesAwaitingJudgmentSubmitEvent()));
        var responseEntity = new ResponseEntity<>(searchResult, HttpStatus.OK);
        when(ccdClientConfig.buildRetrieveCasesUrlElasticSearch(any())).thenReturn(uri);
        when(restTemplate.exchange(eq(uri), eq(HttpMethod.POST), eq(httpEntity),
                eq(CasesAwaitingJudgmentSearchResult.class))).thenReturn(responseEntity);
        var results = ccdClient.casesAwaitingJudgmentSearch("authToken",
                caseDetails.getCaseTypeId(), elasticSearchQuery);
        assertEquals(2, results.size());
        verify(restTemplate).exchange(eq(uri), eq(HttpMethod.POST), eq(httpEntity),
                eq(CasesAwaitingJudgmentSearchResult.class));
        verifyNoMoreInteractions(restTemplate);
    }

    @Test
    void testHearingsToJudgmentSearch() throws IOException {
        var elasticSearchQuery = "{\"size\":10000,\"query\": {\"match_all\":{} }}";
        var httpEntity = new HttpEntity<>(elasticSearchQuery, creatBuildHeaders());
        var searchResult = new HearingsToJudgmentsSearchResult(2L,
                Arrays.asList(new HearingsToJudgmentsSubmitEvent(), new HearingsToJudgmentsSubmitEvent()));
        var responseEntity = new ResponseEntity<>(searchResult, HttpStatus.OK);
        when(ccdClientConfig.buildRetrieveCasesUrlElasticSearch(any())).thenReturn(uri);
        when(restTemplate.exchange(uri, HttpMethod.POST, httpEntity, HearingsToJudgmentsSearchResult.class))
                .thenReturn(responseEntity);
        var results = ccdClient.hearingsToJudgementsSearch("authToken",
                caseDetails.getCaseTypeId(), elasticSearchQuery);
        assertEquals(2, results.size());
        verify(restTemplate).exchange(uri, HttpMethod.POST, httpEntity, HearingsToJudgmentsSearchResult.class);
        verifyNoMoreInteractions(restTemplate);
    }

    @Test
    void testRespondentsReportSearch() throws IOException {
        var elasticSearchQuery = "{\"size\":10000,\"query\": {\"match_all\":{} }}";
        var httpEntity = new HttpEntity<>(elasticSearchQuery, creatBuildHeaders());
        var searchResult = new RespondentsReportSearchResult(2L,
                Arrays.asList(new RespondentsReportSubmitEvent(), new RespondentsReportSubmitEvent()));
        var responseEntity = new ResponseEntity<>(searchResult, HttpStatus.OK);
        when(ccdClientConfig.buildRetrieveCasesUrlElasticSearch(any())).thenReturn(uri);
        when(restTemplate.exchange(uri, HttpMethod.POST, httpEntity, RespondentsReportSearchResult.class))
                .thenReturn(responseEntity);
        var results = ccdClient.respondentsReportSearch("authToken",
                caseDetails.getCaseTypeId(), elasticSearchQuery);
        assertEquals(2, results.size());
        verify(restTemplate).exchange(uri, HttpMethod.POST, httpEntity, RespondentsReportSearchResult.class);
        verifyNoMoreInteractions(restTemplate);
    }

    @Test
    void testSessionDaysSearch() throws IOException {
        var elasticSearchQuery = "{\"size\":10000,\"query\": {\"match_all\":{} }}";
        var httpEntity = new HttpEntity<>(elasticSearchQuery, creatBuildHeaders());
        var searchResult = new SessionDaysSearchResult(2L,
                Arrays.asList(new SessionDaysSubmitEvent(), new SessionDaysSubmitEvent()));
        var responseEntity = new ResponseEntity<>(searchResult, HttpStatus.OK);
        when(ccdClientConfig.buildRetrieveCasesUrlElasticSearch(any())).thenReturn(uri);
        when(restTemplate.exchange(uri, HttpMethod.POST, httpEntity, SessionDaysSearchResult.class))
                .thenReturn(responseEntity);
        var results = ccdClient.sessionDaysSearch("authToken",
                caseDetails.getCaseTypeId(), elasticSearchQuery);
        assertEquals(2, results.size());
        verify(restTemplate).exchange(uri, HttpMethod.POST, httpEntity, SessionDaysSearchResult.class);
        verifyNoMoreInteractions(restTemplate);
    }

    @Test
    void testClaimsByHearingVenueSearch() throws IOException {
        var elasticSearchQuery = "{\"size\":10000,\"query\": {\"match_all\":{} }}";
        var httpEntity = new HttpEntity<>(elasticSearchQuery, creatBuildHeaders());
        var searchResult = new ClaimsByHearingVenueSearchResult(2L,
            Arrays.asList(new ClaimsByHearingVenueSubmitEvent(), new ClaimsByHearingVenueSubmitEvent()));
        var responseEntity = new ResponseEntity<>(searchResult, HttpStatus.OK);
        when(ccdClientConfig.buildRetrieveCasesUrlElasticSearch(any())).thenReturn(uri);
        when(restTemplate.exchange(uri, HttpMethod.POST, httpEntity, ClaimsByHearingVenueSearchResult.class))
            .thenReturn(responseEntity);
        var results = ccdClient.claimsByHearingVenueSearch("authToken",
            caseDetails.getCaseTypeId(), elasticSearchQuery);
        assertEquals(2, results.size());
        verify(restTemplate).exchange(uri, HttpMethod.POST, httpEntity, ClaimsByHearingVenueSearchResult.class);
        verifyNoMoreInteractions(restTemplate);
    }

    @Test
    void testEccReportSearch() throws IOException {
        var elasticSearchQuery = "{\"size\":10000,\"query\": {\"match_all\":{} }}";
        var httpEntity = new HttpEntity<>(elasticSearchQuery, creatBuildHeaders());
        var searchResult = new EccReportSearchResult(2L,
                Arrays.asList(new EccReportSubmitEvent(), new EccReportSubmitEvent()));
        var responseEntity = new ResponseEntity<>(searchResult, HttpStatus.OK);
        when(ccdClientConfig.buildRetrieveCasesUrlElasticSearch(any())).thenReturn(uri);
        when(restTemplate.exchange(uri, HttpMethod.POST, httpEntity, EccReportSearchResult.class))
                .thenReturn(responseEntity);
        var results = ccdClient.eccReportSearch("authToken",
                caseDetails.getCaseTypeId(), elasticSearchQuery);
        assertEquals(2, results.size());
        verify(restTemplate).exchange(uri, HttpMethod.POST, httpEntity, EccReportSearchResult.class);
        verifyNoMoreInteractions(restTemplate);
    }

    @Test
    void testHearingsByHearingTypeReportSearch() throws IOException {
        var elasticSearchQuery = "{\"size\":10000,\"query\": {\"match_all\":{} }}";
        var httpEntity = new HttpEntity<>(elasticSearchQuery, creatBuildHeaders());
        var searchResult = new HearingsByHearingTypeSearchResult(2L,
                Arrays.asList(new HearingsByHearingTypeSubmitEvent(), new HearingsByHearingTypeSubmitEvent()));
        var responseEntity = new ResponseEntity<>(searchResult, HttpStatus.OK);
        when(ccdClientConfig.buildRetrieveCasesUrlElasticSearch(any())).thenReturn(uri);
        when(restTemplate.exchange(uri, HttpMethod.POST, httpEntity, HearingsByHearingTypeSearchResult.class))
                .thenReturn(responseEntity);
        var results = ccdClient.hearingsByHearingTypeSearch("authToken",
                caseDetails.getCaseTypeId(), elasticSearchQuery);
        assertEquals(2, results.size());
        verify(restTemplate).exchange(uri, HttpMethod.POST, httpEntity, HearingsByHearingTypeSearchResult.class);
        verifyNoMoreInteractions(restTemplate);
    }

    @Test
    void testRunElasticSearch() throws IOException {
        var elasticSearchQuery = "{\"size\":10000,\"query\": {\"match_all\":{} }}";
        var httpEntity = new HttpEntity<>(elasticSearchQuery, creatBuildHeaders());
        var searchResult = new CaseSearchResult(2L, Arrays.asList(new SubmitEvent(), new SubmitEvent()));
        var responseEntity = new ResponseEntity<>(searchResult, HttpStatus.OK);
        when(ccdClientConfig.buildRetrieveCasesUrlElasticSearch(any())).thenReturn(uri);
        when(restTemplate.exchange(uri, HttpMethod.POST, httpEntity, CaseSearchResult.class))
                .thenReturn(responseEntity);

        var results = ccdClient.runElasticSearch("authToken", caseDetails.getCaseTypeId(), elasticSearchQuery,
                CaseSearchResult.class);

        assertEquals(2, results.getCases().size());
        verify(restTemplate).exchange(uri, HttpMethod.POST, httpEntity, CaseSearchResult.class);
        verifyNoMoreInteractions(restTemplate);
    }

    @Test
    void startDisposeEventForCase() throws IOException {
        HttpEntity<Object> httpEntity = new HttpEntity<>(creatBuildHeaders());
        ResponseEntity<CCDRequest> responseEntity = new ResponseEntity<>(HttpStatus.OK);
        when(userService.getUserDetails(anyString())).thenReturn(userDetails);
        when(ccdClientConfig.buildStartDisposeEventForCaseUrl(any(), any(), any(), any())).thenReturn(uri);
        when(restTemplate.exchange(eq(uri), eq(HttpMethod.GET), eq(httpEntity),
                eq(CCDRequest.class))).thenReturn(responseEntity);
        ccdClient.startDisposeEventForCase("authToken", caseDetails.getCaseTypeId(),
                caseDetails.getJurisdiction(), "1111");
        verify(restTemplate).exchange(eq(uri), eq(HttpMethod.GET), eq(httpEntity), eq(CCDRequest.class));
        verifyNoMoreInteractions(restTemplate);
    }

    @Test
    void retrieveCaseEvents() throws IOException {
        ResponseEntity<AuditEventsResponse> responseEntity = new ResponseEntity<>(HttpStatus.OK);
        when(ccdClientConfig.buildCaseEventsUrl(any())).thenReturn(uri);
        when(restTemplate.exchange(eq(uri), eq(HttpMethod.GET), any(),
            eq(AuditEventsResponse.class))).thenReturn(responseEntity);
        ccdClient.retrieveCaseEvents("authToken", caseDetails.getCaseId());
        verify(restTemplate).exchange(eq(uri), eq(HttpMethod.GET), any(), eq(AuditEventsResponse.class));
        verifyNoMoreInteractions(restTemplate);
    }

    @Test
    void submitUpdateRepEvent() throws IOException {
        HttpEntity<CaseDataContent> httpEntity = new HttpEntity<>(CaseDataContent.builder().build(),
            creatBuildHeaders());
        ResponseEntity<SubmitEvent> responseEntity = new ResponseEntity<>(HttpStatus.OK);

        Map<String, Object> changeOrganisationRequest = new ConcurrentHashMap<>();

        when(caseDataBuilder.buildChangeOrganisationDataContent(eq(changeOrganisationRequest), eq(ccdRequest),
            anyString())).thenReturn(CaseDataContent.builder().build());
        when(userService.getUserDetails(anyString())).thenReturn(userDetails);
        when(ccdClientConfig.buildSubmitEventForCaseUrl(any(), any(), any(), any())).thenReturn(uri);
        when(restTemplate.exchange(eq(uri), eq(HttpMethod.POST), eq(httpEntity), eq(SubmitEvent.class)))
            .thenReturn(responseEntity);

        ccdClient.submitUpdateRepEvent("authToken", changeOrganisationRequest, caseDetails.getCaseTypeId(),
            caseDetails.getJurisdiction(), ccdRequest, "111111");

        verify(restTemplate).exchange(eq(uri), eq(HttpMethod.POST), eq(httpEntity), eq(SubmitEvent.class));
        verifyNoMoreInteractions(restTemplate);
    }

    @Test
    void startEventForUpdateRep() throws IOException {
        HttpEntity<Object> httpEntity = new HttpEntity<>(creatBuildHeaders());
        ResponseEntity<CCDRequest> responseEntity = new ResponseEntity<>(HttpStatus.OK);
        when(userService.getUserDetails(anyString())).thenReturn(userDetails);
        when(ccdClientConfig.buildStartUpdateRepEventForCaseUrl(any(), any(), any(), any())).thenReturn(uri);
        when(restTemplate.exchange(eq(uri), eq(HttpMethod.GET), eq(httpEntity),
            eq(CCDRequest.class))).thenReturn(responseEntity);
        ccdClient.startEventForUpdateRep("authToken", caseDetails.getCaseTypeId(),
            caseDetails.getJurisdiction(), "1111");
        verify(restTemplate).exchange(eq(uri), eq(HttpMethod.GET), eq(httpEntity), eq(CCDRequest.class));
        verifyNoMoreInteractions(restTemplate);
    }

    @Test
    void retrieveCaseAssignments() throws IOException {
        ResponseEntity<CaseUserAssignmentData> responseEntity = new ResponseEntity<>(HttpStatus.OK);
        when(ccdClientConfig.buildUrlForCaseAccessRetrieval(anyString())).thenReturn(uri);
        when(restTemplate.exchange(eq(uri), eq(HttpMethod.GET), any(),
            eq(CaseUserAssignmentData.class))).thenReturn(responseEntity);

        ccdClient.retrieveCaseAssignments("authToken", "1111");
        verify(restTemplate).exchange(eq(uri), eq(HttpMethod.GET), any(), eq(CaseUserAssignmentData.class));
        verifyNoMoreInteractions(restTemplate);
    }

    @Test
    void revokeCaseAssignments() throws IOException {
        CaseUserAssignmentData caseUserAssignmentData = CaseUserAssignmentData.builder().build();
        ResponseEntity<String> responseEntity = new ResponseEntity<>(HttpStatus.OK);
        when(ccdClientConfig.buildUrlForCaseAccessRevocation()).thenReturn(uri);
        when(restTemplate.exchange(eq(uri), eq(HttpMethod.DELETE), any(),
            eq(String.class))).thenReturn(responseEntity);

        ccdClient.revokeCaseAssignments("authToken", caseUserAssignmentData);
        verify(restTemplate).exchange(eq(uri), eq(HttpMethod.DELETE), any(), eq(String.class));
        verifyNoMoreInteractions(restTemplate);
    }

    @Test
    void setSupplementaryData() throws IOException {
        when(ccdClientConfig.buildUrlForSupplementaryApi(any())).thenReturn(uri);
        ResponseEntity<Object> responseEntity = new ResponseEntity<>(HttpStatus.OK);
        when(restTemplate.exchange(eq(uri), eq(HttpMethod.POST), any(), eq(Object.class))).thenReturn(responseEntity);
        ccdClient.setSupplementaryData("authToken", Map.of("HMCTSServiceID", "BHA1"), caseDetails.getCaseId());
        verify(restTemplate).exchange(eq(uri), eq(HttpMethod.POST), any(), eq(Object.class));
        verifyNoMoreInteractions(restTemplate);
    }

    @Test
    void addUserToMultiple() throws IOException {
        when(userService.getUserDetails(anyString())).thenReturn(userDetails);
        when(ccdClientConfig.addLegalRepToMultiCaseUrl(any(), any(), any(), any())).thenReturn(uri);
        ResponseEntity<Object> responseEntity = new ResponseEntity<>(HttpStatus.OK);
        when(restTemplate.exchange(eq(uri), eq(HttpMethod.POST), any(), eq(Object.class))).thenReturn(responseEntity);

        ccdClient.addUserToMultiple(
                "authToken",
                caseDetails.getJurisdiction(),
                caseDetails.getCaseTypeId(),
                "6000001",
                Map.of("id", "123")
        );

        verify(restTemplate).exchange(eq(uri), eq(HttpMethod.POST), any(), eq(Object.class));
        verifyNoMoreInteractions(restTemplate);
    }

    @Test
    void removeUserFromMultiple() throws IOException {
        when(userService.getUserDetails(anyString())).thenReturn(userDetails);
        when(ccdClientConfig.removeLegalRepFromMultiCaseUrl(any(), any(), any(), any(), any())).thenReturn(uri);
        ResponseEntity<Object> responseEntity = new ResponseEntity<>(HttpStatus.OK);
        when(restTemplate.exchange(eq(uri), eq(HttpMethod.DELETE), any(), eq(Object.class))).thenReturn(responseEntity);

        ccdClient.removeUserFromMultiple(
                "authToken",
                caseDetails.getJurisdiction(),
                caseDetails.getCaseTypeId(),
                "6000001",
                "123"
        );

        verify(restTemplate).exchange(eq(uri), eq(HttpMethod.DELETE), any(), eq(Object.class));
        verifyNoMoreInteractions(restTemplate);
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
}