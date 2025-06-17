package uk.gov.hmcts.ecm.common.client;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestClientResponseException;
import org.springframework.web.client.RestTemplate;
import uk.gov.hmcts.ecm.common.helpers.ESHelper;
import uk.gov.hmcts.ecm.common.model.helper.TribunalOffice;
import uk.gov.hmcts.ecm.common.model.labels.LabelCaseSearchResult;
import uk.gov.hmcts.ecm.common.model.labels.LabelPayloadEvent;
import uk.gov.hmcts.ecm.common.model.reference.ReferenceSubmitEvent;
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
import uk.gov.hmcts.ecm.common.model.schedule.NotificationSchedulePayloadEvent;
import uk.gov.hmcts.ecm.common.model.schedule.NotificationScheduleSearchCasesResult;
import uk.gov.hmcts.ecm.common.model.schedule.ScheduleCaseSearchResult;
import uk.gov.hmcts.ecm.common.model.schedule.SchedulePayloadEvent;
import uk.gov.hmcts.ecm.common.service.UserService;
import uk.gov.hmcts.et.common.model.bulk.BulkCaseSearchResult;
import uk.gov.hmcts.et.common.model.bulk.BulkData;
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
import uk.gov.hmcts.et.common.model.generic.GenericRequest;
import uk.gov.hmcts.et.common.model.multiples.MultipleCaseSearchResult;
import uk.gov.hmcts.et.common.model.multiples.MultipleData;
import uk.gov.hmcts.et.common.model.multiples.MultipleRequest;
import uk.gov.hmcts.et.common.model.multiples.SubmitMultipleEvent;
import uk.gov.hmcts.reform.authorisation.generators.AuthTokenGenerator;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;
import javax.naming.NameNotFoundException;

import static uk.gov.hmcts.ecm.common.model.helper.Constants.ALL_VENUES;
import static uk.gov.hmcts.ecm.common.model.helper.Constants.ENGLANDWALES_CASE_TYPE_ID;
import static uk.gov.hmcts.ecm.common.model.helper.Constants.MANUALLY_CREATED_POSITION;
import static uk.gov.hmcts.ecm.common.model.helper.Constants.OLD_DATE_TIME_PATTERN;
import static uk.gov.hmcts.ecm.common.model.helper.Constants.SCOTLAND_CASE_TYPE_ID;
import static uk.gov.hmcts.ecm.common.model.helper.Constants.SERVICE_AUTHORIZATION;

@Slf4j
public class CcdClient {

    public static final String EXPERIMENTAL = "experimental";
    private RestTemplate restTemplate;
    private UserService userService;
    private CcdClientConfig ccdClientConfig;
    private CaseDataBuilder caseDataBuilder;
    private EcmCaseDataBuilder ecmCaseDataBuilder;
    private AuthTokenGenerator authTokenGenerator;

    static final String CREATION_EVENT_SUMMARY = "Case created automatically";
    static final String UPDATE_EVENT_SUMMARY = "Case updated by bulk";
    static final String UPDATE_BULK_EVENT_SUMMARY = "Bulk case updated by bulk";

    static final String UPDATE_CHANGE_ORG_SUMMARY = "Change of organisation completed";
    private static final int MAX_RETRIES = 7;

    public CcdClient(RestTemplate restTemplate, UserService userService, CaseDataBuilder caseDataBuilder,
                     CcdClientConfig ccdClientConfig, AuthTokenGenerator authTokenGenerator) {
        this.restTemplate = restTemplate;
        this.userService = userService;
        this.ccdClientConfig = ccdClientConfig;
        this.authTokenGenerator = authTokenGenerator;
        this.caseDataBuilder = caseDataBuilder;
    }

    public CcdClient(RestTemplate restTemplate, UserService userService, CaseDataBuilder caseDataBuilder,
                     CcdClientConfig ccdClientConfig, AuthTokenGenerator authTokenGenerator,
                     EcmCaseDataBuilder ecmCaseDataBuilder) {
        this(restTemplate, userService, caseDataBuilder, ccdClientConfig, authTokenGenerator);
        this.ecmCaseDataBuilder = ecmCaseDataBuilder;
    }

    public CCDRequest startCaseCreation(String authToken, CaseDetails caseDetails) throws IOException {
        String uri = ccdClientConfig.buildStartCaseCreationUrl(userService.getUserDetails(authToken).getUid(),
                caseDetails.getJurisdiction(),
                caseDetails.getCaseTypeId());
        return restTemplate.exchange(uri, HttpMethod.GET, getRequest(authToken), CCDRequest.class).getBody();
    }

    public <T> CCDRequest startGenericTypeCaseCreation(String authToken, GenericTypeCaseDetails<T> caseDetails)
            throws IOException {
        String uri = ccdClientConfig.buildStartCaseCreationUrl(userService.getUserDetails(authToken).getUid(),
                caseDetails.getJurisdiction(),
                caseDetails.getCaseTypeId());
        return restTemplate.exchange(uri, HttpMethod.GET, getRequest(authToken), CCDRequest.class).getBody();
    }

    public CCDRequest startCaseCreationTransfer(String authToken, CaseDetails caseDetails) throws IOException {
        String uri = ccdClientConfig.buildStartCaseCreationTransferUrl(userService.getUserDetails(authToken).getUid(),
                caseDetails.getJurisdiction(),
                caseDetails.getCaseTypeId());
        return restTemplate.exchange(uri, HttpMethod.GET, getRequest(authToken), CCDRequest.class).getBody();
    }

    public uk.gov.hmcts.ecm.common.model.ccd.CCDRequest startEcmCaseCreationTransfer(String authToken,
                                                   uk.gov.hmcts.ecm.common.model.ccd.CaseDetails caseDetails)
        throws IOException {
        String uri = ccdClientConfig.buildStartCaseCreationTransferUrl(userService.getUserDetails(authToken).getUid(),
            caseDetails.getJurisdiction(),
            caseDetails.getCaseTypeId());
        return restTemplate.exchange(uri, HttpMethod.GET, getRequest(authToken),
            uk.gov.hmcts.ecm.common.model.ccd.CCDRequest.class).getBody();
    }

    public uk.gov.hmcts.ecm.common.model.ccd.CCDRequest startEventForEcmCase(String authToken, String caseTypeId,
                                                                             String jurisdiction, String cid,
                                        String eventId) throws IOException {
        HttpEntity<String> request = new HttpEntity<>(buildHeaders(authToken));
        String uri = ccdClientConfig.buildStartEventUrlForCaseWorker(userService.getUserDetails(authToken).getUid(),
                jurisdiction,
                caseTypeId, cid, eventId);
        return restTemplate.exchange(uri, HttpMethod.GET, request,
                uk.gov.hmcts.ecm.common.model.ccd.CCDRequest.class).getBody();
    }

    public uk.gov.hmcts.ecm.common.model.ccd.SubmitEvent submitEventForEcmCase(String authToken,
                                                           uk.gov.hmcts.ecm.common.model.ccd.CaseDetails caseDetails,
                                                           uk.gov.hmcts.ecm.common.model.ccd.CCDRequest req)
            throws IOException {
        uk.gov.hmcts.ecm.common.model.ccd.CaseDataContent ecmCaseDataContent = ecmCaseDataBuilder
                .buildCaseDataContent(caseDetails.getCaseData(), req, null);
        HttpEntity<uk.gov.hmcts.ecm.common.model.ccd.CaseDataContent> request = new HttpEntity<>(ecmCaseDataContent,
                buildHeaders(authToken));
        String uri = ccdClientConfig.buildSubmitEventForCaseUrl(userService.getUserDetails(authToken).getUid(),
                caseDetails.getJurisdiction(), caseDetails.getCaseTypeId(), caseDetails.getCaseId());
        return restTemplate.exchange(uri, HttpMethod.POST, request,
                uk.gov.hmcts.ecm.common.model.ccd.SubmitEvent.class).getBody();
    }

    public CCDRequest startCaseTransfer(String authToken, String caseTypeId, String jurisdiction, String cid)
            throws IOException {
        String uri = ccdClientConfig.buildStartCaseTransferUrl(userService.getUserDetails(authToken).getUid(),
                jurisdiction, caseTypeId, cid);
        return restTemplate.exchange(uri, HttpMethod.GET, getRequest(authToken), CCDRequest.class).getBody();
    }

    public CCDRequest startCaseTransferSameCountryEccLinkedCase(String authToken, String caseTypeId,
                                                                String jurisdiction, String cid) throws IOException {
        String uid = userService.getUserDetails(authToken).getUid();
        String uri = ccdClientConfig.buildStartCaseTransferSameCountryLinkedCaseUrl(
                uid, jurisdiction, caseTypeId, cid);
        return restTemplate.exchange(uri, HttpMethod.GET, getRequest(authToken), CCDRequest.class).getBody();
    }

    public CCDRequest returnCaseCreationTransfer(String authToken, String caseTypeId, String jurisdiction, String cid)
            throws IOException {
        String uri = ccdClientConfig.buildReturnCaseCreationTransferUrl(userService.getUserDetails(authToken).getUid(),
                jurisdiction, caseTypeId, cid);
        return restTemplate.exchange(uri, HttpMethod.GET,  getRequest(authToken), CCDRequest.class).getBody();
    }

    private HttpEntity<String>  getRequest(String authToken) throws IOException {
        return new HttpEntity<>(buildHeaders(authToken));
    }

    public CCDRequest startCaseMultipleCreation(String authToken, String caseTypeId, String jurisdiction)
            throws IOException {
        HttpEntity<String> request =
                new HttpEntity<>(buildHeaders(authToken));
        String uri = ccdClientConfig.buildStartCaseMultipleCreationUrl(userService.getUserDetails(authToken).getUid(),
                jurisdiction, caseTypeId);
        return restTemplate.exchange(uri, HttpMethod.GET, request, CCDRequest.class).getBody();
    }

    public SubmitEvent submitCaseCreation(String authToken, CaseDetails caseDetails, CCDRequest req)
            throws IOException {
        return submitCaseCreation(authToken, caseDetails, req, CREATION_EVENT_SUMMARY);
    }

    public SubmitEvent submitCaseCreation(String authToken, CaseDetails caseDetails, CCDRequest req,
                                          String eventSummary) throws IOException {
        var caseDataContent = caseDataBuilder
            .buildCaseDataContent(caseDetails.getCaseData(), req, eventSummary);
        HttpEntity<CaseDataContent> request = new HttpEntity<>(caseDataContent, buildHeaders(authToken));
        String uri = ccdClientConfig.buildSubmitCaseCreationUrl(userService.getUserDetails(authToken).getUid(),
                caseDetails.getJurisdiction(), caseDetails.getCaseTypeId());
        return restTemplate.exchange(uri, HttpMethod.POST, request, SubmitEvent.class).getBody();
    }

    public <T> SubmitEvent submitGenericTypeCaseCreation(String authToken, GenericTypeCaseDetails<T> caseDetails,
                                                     CCDRequest req, String eventSummary, String eventDescription)
            throws IOException {
        var caseDataContent = caseDataBuilder
                .buildGenericCaseDataContent(caseDetails.getCaseData(), req, eventSummary, eventDescription);
        HttpEntity<CaseDataContent> request = new HttpEntity<>(caseDataContent, buildHeaders(authToken));
        String uri = ccdClientConfig.buildSubmitCaseCreationUrl(userService.getUserDetails(authToken).getUid(),
                caseDetails.getJurisdiction(), caseDetails.getCaseTypeId());
        return restTemplate.exchange(uri, HttpMethod.POST, request, SubmitEvent.class).getBody();
    }

    public uk.gov.hmcts.ecm.common.model.ccd.SubmitEvent submitEcmCaseCreation(String authToken,
                                             uk.gov.hmcts.ecm.common.model.ccd.CaseDetails caseDetails,
                                             uk.gov.hmcts.ecm.common.model.ccd.CCDRequest req)
        throws IOException {
        uk.gov.hmcts.ecm.common.model.ccd.CaseDataContent ecmCaseDataContent = ecmCaseDataBuilder
            .buildCaseDataContent(caseDetails.getCaseData(), req, CREATION_EVENT_SUMMARY);
        HttpEntity<uk.gov.hmcts.ecm.common.model.ccd.CaseDataContent> request = new HttpEntity<>(ecmCaseDataContent,
            buildHeaders(authToken));
        String uri = ccdClientConfig.buildSubmitCaseCreationUrl(userService.getUserDetails(authToken).getUid(),
            caseDetails.getJurisdiction(), caseDetails.getCaseTypeId());
        return restTemplate.exchange(uri, HttpMethod.POST, request,
                uk.gov.hmcts.ecm.common.model.ccd.SubmitEvent.class).getBody();
    }

    public SubmitEvent retrieveCase(String authToken, String caseTypeId, String jurisdiction, String cid)
            throws IOException {
        HttpEntity<CCDRequest> request =
                new HttpEntity<>(buildHeaders(authToken));
        String uri = ccdClientConfig.buildRetrieveCaseUrl(userService.getUserDetails(authToken).getUid(), jurisdiction,
                caseTypeId, cid);
        return restTemplate.exchange(uri, HttpMethod.GET, request, SubmitEvent.class).getBody();
    }

    public String retrieveTransferredCaseReference(String authToken, String caseTypeId,
                                                   String jurisdiction, String cid)
            throws IOException {
        HttpEntity<uk.gov.hmcts.ecm.common.model.ccd.CCDRequest> request = new HttpEntity<>(buildHeaders(authToken));
        String uri = ccdClientConfig.buildRetrieveCaseUrl(userService.getUserDetails(authToken).getUid(),
                jurisdiction, caseTypeId, cid);

        String targetCaseEthosReference = null;
        if (ENGLANDWALES_CASE_TYPE_ID.equals(caseTypeId) || SCOTLAND_CASE_TYPE_ID.equals(caseTypeId)) {
            uk.gov.hmcts.et.common.model.ccd.SubmitEvent reformCase = restTemplate.exchange(uri, HttpMethod.GET,
                    request, uk.gov.hmcts.et.common.model.ccd.SubmitEvent.class).getBody();
            if (reformCase != null && reformCase.getCaseData() != null) {
                targetCaseEthosReference = reformCase.getCaseData().getEthosCaseReference();
            }
        } else {
            uk.gov.hmcts.ecm.common.model.ccd.SubmitEvent ecmCase = restTemplate.exchange(uri, HttpMethod.GET,
                    request, uk.gov.hmcts.ecm.common.model.ccd.SubmitEvent.class).getBody();
            if (ecmCase != null && ecmCase.getCaseData() != null) {
                targetCaseEthosReference = ecmCase.getCaseData().getEthosCaseReference();
            }
        }
        return targetCaseEthosReference;
    }

    public List<SubmitEvent> executeElasticSearch(String authToken, String caseTypeId, String query)
            throws IOException {
        var submitEvents = new ArrayList<SubmitEvent>();
        var searchResult = runElasticSearch(authToken, caseTypeId, query,
                CaseSearchResult.class);
        if (searchResult != null && !CollectionUtils.isEmpty(searchResult.getCases())) {
            submitEvents.addAll(searchResult.getCases());
        }

        return submitEvents;
    }

    public List<CasesAwaitingJudgmentSubmitEvent> casesAwaitingJudgmentSearch(String authToken, String caseTypeId,
                                                                              String query) throws IOException {
        var submitEvents = new ArrayList<CasesAwaitingJudgmentSubmitEvent>();
        var searchResult = runElasticSearch(authToken, caseTypeId, query, CasesAwaitingJudgmentSearchResult.class);

        if (searchResult != null && !CollectionUtils.isEmpty(searchResult.getCases())) {
            submitEvents.addAll(searchResult.getCases());
        }

        return submitEvents;
    }

    public List<HearingsToJudgmentsSubmitEvent> hearingsToJudgementsSearch(String authToken, String caseTypeId,
                                                                           String query) throws IOException {
        var submitEvents = new ArrayList<HearingsToJudgmentsSubmitEvent>();
        var searchResult = runElasticSearch(authToken, caseTypeId, query, HearingsToJudgmentsSearchResult.class);

        if (searchResult != null && !CollectionUtils.isEmpty(searchResult.getCases())) {
            submitEvents.addAll(searchResult.getCases());
        }

        return submitEvents;
    }

    public List<RespondentsReportSubmitEvent> respondentsReportSearch(String authToken, String caseTypeId,
                                                                      String query) throws IOException {
        var submitEvents = new ArrayList<RespondentsReportSubmitEvent>();
        var searchResult = runElasticSearch(authToken, caseTypeId, query,
                RespondentsReportSearchResult.class);
        if (searchResult != null && !CollectionUtils.isEmpty(searchResult.getCases())) {
            submitEvents.addAll(searchResult.getCases());
        }

        return submitEvents;
    }

    public List<SessionDaysSubmitEvent> sessionDaysSearch(String authToken, String caseTypeId,
                                                          String query) throws IOException {
        var submitEvents = new ArrayList<SessionDaysSubmitEvent>();
        var searchResult = runElasticSearch(authToken, caseTypeId, query,
                SessionDaysSearchResult.class);
        if (searchResult != null && !CollectionUtils.isEmpty(searchResult.getCases())) {
            submitEvents.addAll(searchResult.getCases());
        }

        return submitEvents;
    }

    public List<ClaimsByHearingVenueSubmitEvent> claimsByHearingVenueSearch(String authToken, String caseTypeId,
                                                                            String query) throws IOException {
        var submitEvents = new ArrayList<ClaimsByHearingVenueSubmitEvent>();
        var searchResult = runElasticSearch(authToken, caseTypeId, query,
            ClaimsByHearingVenueSearchResult.class);
        if (searchResult != null && !CollectionUtils.isEmpty(searchResult.getCases())) {
            submitEvents.addAll(searchResult.getCases());
        }

        return submitEvents;
    }

    public List<EccReportSubmitEvent> eccReportSearch(String authToken, String caseTypeId,
                                                      String query) throws IOException {
        var submitEvents = new ArrayList<EccReportSubmitEvent>();
        var searchResult = runElasticSearch(authToken, caseTypeId, query,
                EccReportSearchResult.class);
        if (searchResult != null && !CollectionUtils.isEmpty(searchResult.getCases())) {
            submitEvents.addAll(searchResult.getCases());
        }

        return submitEvents;
    }

    public List<HearingsByHearingTypeSubmitEvent> hearingsByHearingTypeSearch(String authToken, String caseTypeId,
                                                                              String query) throws IOException {
        var submitEvents = new ArrayList<HearingsByHearingTypeSubmitEvent>();
        var searchResult = runElasticSearch(authToken, caseTypeId, query,
                HearingsByHearingTypeSearchResult.class);
        if (searchResult != null && !CollectionUtils.isEmpty(searchResult.getCases())) {
            submitEvents.addAll(searchResult.getCases());
        }

        return submitEvents;
    }

    public <T> T runElasticSearch(String authToken, String caseTypeId, String query, Class<T> searchResultTypeClass)
            throws IOException {
        var request = new HttpEntity<>(query, buildHeaders(authToken));
        var url = ccdClientConfig.buildRetrieveCasesUrlElasticSearch(caseTypeId);

        return restTemplate.exchange(url, HttpMethod.POST, request, searchResultTypeClass).getBody();
    }

    private PaginatedSearchMetadata searchMetadata(String authToken, String caseTypeId, String jurisdiction)
            throws IOException {
        HttpEntity<CCDRequest> request =
                new HttpEntity<>(buildHeaders(authToken));
        String uri = ccdClientConfig.buildPaginationMetadataCaseUrl(userService.getUserDetails(authToken).getUid(),
                jurisdiction,
                caseTypeId);
        return restTemplate.exchange(uri, HttpMethod.GET, request, PaginatedSearchMetadata.class).getBody();
    }

    private String getURI(String authToken, String caseTypeId, String jurisdiction, String page) {
        return ccdClientConfig.buildRetrieveCasesUrl(userService.getUserDetails(authToken).getUid(), jurisdiction,
                caseTypeId, page);
    }

    private int getTotalPagesCount(String authToken, String caseTypeId, String jurisdiction) throws IOException {
        PaginatedSearchMetadata paginatedSearchMetadata = searchMetadata(authToken, caseTypeId, jurisdiction);
        return paginatedSearchMetadata.getTotalPagesCount();
    }

    public List<SubmitEvent> retrieveCases(String authToken, String caseTypeId, String jurisdiction)
            throws IOException {
        List<SubmitEvent> submitEvents = new ArrayList<>();
        for (int page = 1; page <= getTotalPagesCount(authToken, caseTypeId, jurisdiction); page++) {
            List<SubmitEvent> submitEventAux = restTemplate.exchange(ccdClientConfig.buildRetrieveCasesUrl(
                    userService.getUserDetails(authToken).getUid(), jurisdiction,
                    caseTypeId, String.valueOf(page)), HttpMethod.GET, new HttpEntity<>(buildHeaders(authToken)),
                    new ParameterizedTypeReference<List<SubmitEvent>>() {
                    }).getBody();
            if (submitEventAux != null) {
                submitEvents.addAll(submitEventAux);
            }
        }
        return submitEvents;
    }

    public List<ReferenceSubmitEvent> retrieveReferenceDataCases(String authToken,
                                                                 String caseTypeId, String jurisdiction)
            throws IOException {
        List<ReferenceSubmitEvent> referenceSubmitEvents = new ArrayList<>();
        for (int page = 1; page <= getTotalPagesCount(authToken, caseTypeId, jurisdiction); page++) {
            List<ReferenceSubmitEvent> submitEventAux = restTemplate.exchange(
                    ccdClientConfig.buildRetrieveCasesUrl(userService.getUserDetails(authToken).getUid(), jurisdiction,
                    caseTypeId, String.valueOf(page)), HttpMethod.GET,
                    new HttpEntity<>(buildHeaders(authToken)),
                    new ParameterizedTypeReference<List<ReferenceSubmitEvent>>() {
                    }).getBody();
            if (submitEventAux != null) {
                referenceSubmitEvents.addAll(submitEventAux);
            }
        }
        return referenceSubmitEvents;
    }

    private String getListingQuery(String from, String to, String venue, String mapping, String managingOffice) {
        if (ALL_VENUES.equals(venue)) {
            return ESHelper.getListingRangeDateSearchQuery(from, to, managingOffice);
        } else {
            return ESHelper.getListingVenueAndRangeDateSearchQuery(from, to, venue, mapping, managingOffice);
        }
    }

    public List<SubmitEvent> retrieveCasesVenueAndDateElasticSearch(String authToken, String caseTypeId,
                                                                    String dateToSearchFrom, String dateToSearchTo,
                                                                    String venueToSearch, String venueToSearchMapping,
                                                                    String managingOffice)
            throws IOException {
        return buildAndGetElasticSearchRequest(authToken, caseTypeId,
                getListingQuery(dateToSearchFrom, dateToSearchTo, venueToSearch, venueToSearchMapping, managingOffice));
    }

    public List<SubmitEvent> retrieveCasesGenericReportElasticSearch(String authToken, String caseTypeId,
                                                                     TribunalOffice tribunalOffice,
                                                                     String dateToSearchFrom, String dateToSearchTo,
                                                                    String reportType) throws IOException {
        String from = LocalDate.parse(dateToSearchFrom).atStartOfDay().format(OLD_DATE_TIME_PATTERN);
        String to;
        if (dateToSearchTo.equals(dateToSearchFrom)) {
            to = LocalDate.parse(dateToSearchFrom).atStartOfDay().plusDays(1).minusSeconds(1)
                    .format(OLD_DATE_TIME_PATTERN);
        } else {
            to = LocalDate.parse(dateToSearchTo).atStartOfDay().plusDays(1).minusSeconds(1)
                    .format(OLD_DATE_TIME_PATTERN);
        }

        log.info("Report: {} Office: {} {} - {}", reportType, tribunalOffice, from, to);
        return buildAndGetElasticSearchRequest(authToken, caseTypeId,
                getReportRangeDateQuery(from, to, reportType, tribunalOffice));
    }

    private String getReportRangeDateQuery(String from, String to, String reportType, TribunalOffice tribunalOffice) {
        if (tribunalOffice != null) {
            return ESHelper.getReportRangeDateSearchQuery(from, to, reportType, tribunalOffice.getOfficeName());
        } else {
            return ESHelper.getReportRangeDateSearchQuery(from, to, reportType);
        }
    }

    public List<SubmitEvent> buildAndGetElasticSearchRequest(String authToken, String caseTypeId, String query)
            throws IOException {
        List<SubmitEvent> submitEvents = new ArrayList<>();
        HttpEntity<String> request = new HttpEntity<>(query, buildHeaders(authToken));
        String url = ccdClientConfig.buildRetrieveCasesUrlElasticSearch(caseTypeId);
        CaseSearchResult caseSearchResult = restTemplate.exchange(url, HttpMethod.POST, request,
                CaseSearchResult.class).getBody();
        if (caseSearchResult != null && caseSearchResult.getCases() != null) {
            submitEvents.addAll(caseSearchResult.getCases());
        }
        return submitEvents;
    }

    private List<SchedulePayloadEvent> buildAndGetElasticSearchScheduleRequest(String authToken, String caseTypeId,
                                                                               String query) throws IOException {
        List<SchedulePayloadEvent> schedulePayloadEvents = new ArrayList<>();
        HttpEntity<String> request = new HttpEntity<>(query, buildHeaders(authToken));
        String url = ccdClientConfig.buildRetrieveCasesUrlElasticSearch(caseTypeId);
        ScheduleCaseSearchResult scheduleCaseSearchResult =
                restTemplate.exchange(url, HttpMethod.POST, request, ScheduleCaseSearchResult.class).getBody();
        if (scheduleCaseSearchResult != null && scheduleCaseSearchResult.getCases() != null) {
            schedulePayloadEvents.addAll(scheduleCaseSearchResult.getCases());
        }
        return schedulePayloadEvents;
    }

    private List<NotificationSchedulePayloadEvent> buildAndGetElasticNotificationSearchScheduleRequest(
            String authToken,
            String caseTypeId,
            String query) throws IOException {
        List<NotificationSchedulePayloadEvent> schedulePayloadEvents = new ArrayList<>();
        HttpEntity<String> request = new HttpEntity<>(query, buildHeaders(authToken));
        String url = ccdClientConfig.buildRetrieveCasesUrlElasticSearch(caseTypeId);
        NotificationScheduleSearchCasesResult scheduleCaseSearchResult =
                restTemplate.exchange(url,
                        HttpMethod.POST,
                        request,
                        NotificationScheduleSearchCasesResult.class).getBody();
        if (scheduleCaseSearchResult != null && scheduleCaseSearchResult.cases() != null) {
            schedulePayloadEvents.addAll(scheduleCaseSearchResult.cases());
        }
        return schedulePayloadEvents;
    }

    private List<LabelPayloadEvent> buildAndGetElasticSearchLabelRequest(String authToken, String caseTypeId,
                                                                         String query) throws IOException {
        List<LabelPayloadEvent> labelPayloadEvents = new ArrayList<>();
        HttpEntity<String> request = new HttpEntity<>(query, buildHeaders(authToken));
        String url = ccdClientConfig.buildRetrieveCasesUrlElasticSearch(caseTypeId);
        LabelCaseSearchResult labelCaseSearchResult =
                restTemplate.exchange(url, HttpMethod.POST, request, LabelCaseSearchResult.class).getBody();
        if (labelCaseSearchResult != null && labelCaseSearchResult.getCases() != null) {
            labelPayloadEvents.addAll(labelCaseSearchResult.getCases());
        }
        return labelPayloadEvents;
    }

    public List<SubmitMultipleEvent> buildAndGetElasticSearchRequestWithRetriesMultiples(String authToken,
                                                                                         String caseTypeId,
                                                                                         String query)
            throws IOException {
        HttpEntity<String> request = new HttpEntity<>(query, buildHeaders(authToken));
        String url = ccdClientConfig.buildRetrieveCasesUrlElasticSearch(caseTypeId);
        MultipleCaseSearchResult multipleCaseSearchResult;
        int retries = 1;
        do {
            log.info("Retry: " + retries);
            multipleCaseSearchResult = restTemplate.exchange(url, HttpMethod.POST, request,
                    MultipleCaseSearchResult.class).getBody();
            try {
                if (multipleCaseSearchResult != null) {
                    log.info("Checking size found: " + multipleCaseSearchResult.getTotal());
                }
                TimeUnit.SECONDS.sleep(5);
                retries++;
                if (retries == MAX_RETRIES) {
                    break;
                }
            } catch (InterruptedException e) {
                log.error("Error sleeping the thread");
                Thread.currentThread().interrupt();
            }
        } while (multipleCaseSearchResult == null || multipleCaseSearchResult.getTotal() != 1);

        return multipleCaseSearchResult != null
                ? new ArrayList<>(multipleCaseSearchResult.getCases())
                : new ArrayList<>();

    }

    private List<SubmitEvent> buildAndGetElasticSearchRequestWithRetries(String authToken, String caseTypeId,
                                                                         String query, int size, List<String> caseIds)
            throws IOException {
        HttpEntity<String> request = new HttpEntity<>(query, buildHeaders(authToken));
        String url = ccdClientConfig.buildRetrieveCasesUrlElasticSearch(caseTypeId);
        CaseSearchResult caseSearchResult;
        int retries = 1;
        do {
            log.info("Retry: " + retries);
            caseSearchResult = restTemplate.exchange(url, HttpMethod.POST, request, CaseSearchResult.class).getBody();
            try {
                if (caseSearchResult != null) {
                    log.info("Checking size found: " + caseSearchResult.getTotal());
                }
                TimeUnit.SECONDS.sleep(5);
                retries++;
                if (retries == MAX_RETRIES) {
                    break;
                }
            } catch (InterruptedException e) {
                log.error("Error sleeping the thread");
                Thread.currentThread().interrupt();
            }
        } while (caseSearchResult == null || caseSearchResult.getTotal() != size);

        if (caseSearchResult != null) {
            generateCasesNotFound(caseIds, caseSearchResult);
        }
        return caseSearchResult != null
                ? new ArrayList<>(caseSearchResult.getCases())
                : new ArrayList<>();
    }

    public static void generateCasesNotFound(List<String> caseIds, CaseSearchResult caseSearchResult) {
        List<String> casesFound = caseSearchResult.getCases()
                .stream()
                .map(caseSearched -> caseSearched.getCaseData().getEthosCaseReference())
                .collect(Collectors.toList());
        log.info("Cases not found: " + new ArrayList<>(CollectionUtils.subtract(caseIds, casesFound)).toString());
    }

    public List<SubmitEvent> retrieveCasesElasticSearchForCreation(String authToken, String caseTypeId,
                                                                   List<String> caseIds,
                                                                   String multipleSource) throws IOException {
        if (multipleSource.equals(MANUALLY_CREATED_POSITION)) {
            return retrieveCasesElasticSearch(authToken, caseTypeId, caseIds);
        } else {
            return retrieveCasesElasticSearchWithRetries(authToken, caseTypeId, caseIds);
        }
    }

    private List<SubmitEvent> retrieveCasesElasticSearchWithRetries(String authToken, String caseTypeId,
                                                                    List<String> caseIds) throws IOException {
        String query = ESHelper.getSearchQuery(caseIds);
        log.info("QUERY WITH RETRIES: " + query);
        return buildAndGetElasticSearchRequestWithRetries(authToken, caseTypeId, query, caseIds.size(), caseIds);
    }

    public List<SubmitEvent> retrieveCasesElasticSearch(String authToken, String caseTypeId,
                                                        List<String> caseIds) throws IOException {
        String query = ESHelper.getSearchQuery(caseIds);
        log.info("QUERY: " + query);
        return buildAndGetElasticSearchRequest(authToken, caseTypeId, query);
    }

    public List<SchedulePayloadEvent> retrieveCasesElasticSearchSchedule(String authToken, String caseTypeId,
                                                                         List<String> caseIds) throws IOException {
        String query = ESHelper.getSearchQuerySchedule(caseIds);
        log.info("QUERY Schedule: " + query);
        return buildAndGetElasticSearchScheduleRequest(authToken, caseTypeId, query);
    }

    public List<NotificationSchedulePayloadEvent> retrieveCasesElasticNotificationSearchSchedule(
            String authToken,
            String caseTypeId,
            List<String> caseIds) throws IOException {
        String query = ESHelper.getNotificationSearchQuerySchedule(caseIds);
        log.info("QUERY Schedule: " + query);
        return buildAndGetElasticNotificationSearchScheduleRequest(authToken, caseTypeId, query);
    }

    public List<LabelPayloadEvent> retrieveCasesElasticSearchLabels(String authToken, String caseTypeId,
                                                                    List<String> caseIds) throws IOException {
        String query = ESHelper.getSearchQueryLabels(caseIds);
        log.info("QUERY Labels: " + query);
        return buildAndGetElasticSearchLabelRequest(authToken, caseTypeId, query);
    }

    public List<SubmitEvent> retrieveTransferredCaseElasticSearch(String authToken, String caseTypeId,
                                                                  String currentCaseId) throws IOException {
        String query = ESHelper.getTransferredCaseSearchQueryLabel(currentCaseId);
        log.info("QUERY Labels: " + query);
        return this.buildAndGetElasticSearchRequest(authToken, caseTypeId, query);
    }

    public List<SubmitBulkEvent> retrieveBulkCases(String authToken, String caseTypeId, String jurisdiction)
            throws IOException {
        List<SubmitBulkEvent> submitBulkEvents = new ArrayList<>();
        int totalNumberPages = getTotalPagesCount(authToken, caseTypeId, jurisdiction);
        for (int page = 1; page <= totalNumberPages; page++) {
            List<SubmitBulkEvent> submitBulkEventAux = restTemplate.exchange(getURI(authToken, caseTypeId, jurisdiction,
                            String.valueOf(page)), HttpMethod.GET,
                    new HttpEntity<>(buildHeaders(authToken)),
                    new ParameterizedTypeReference<List<SubmitBulkEvent>>(){}).getBody();
            if (submitBulkEventAux != null) {
                submitBulkEvents.addAll(submitBulkEventAux);
            }
        }
        return submitBulkEvents;
    }

    public List<SubmitBulkEvent> retrieveBulkCasesElasticSearch(String authToken, String caseTypeId,
                                                                String multipleReference) throws IOException {
        List<SubmitBulkEvent> submitBulkEvents = new ArrayList<>();
        log.info("QUERY: " + ESHelper.getBulkSearchQuery(multipleReference));
        HttpEntity<String> request =
                new HttpEntity<>(ESHelper.getBulkSearchQuery(multipleReference), buildHeaders(authToken));
        String url = ccdClientConfig.buildRetrieveCasesUrlElasticSearch(caseTypeId);
        BulkCaseSearchResult bulkCaseSearchResult = restTemplate.exchange(url, HttpMethod.POST, request,
                BulkCaseSearchResult.class).getBody();
        if (bulkCaseSearchResult != null && bulkCaseSearchResult.getCases() != null) {
            submitBulkEvents.addAll(bulkCaseSearchResult.getCases());
        }
        return submitBulkEvents;
    }

    public List<SubmitMultipleEvent> retrieveMultipleCasesElasticSearchWithRetries(String authToken, String caseTypeId,
                                                                                   String multipleReference)
            throws IOException {
        log.info("QUERY WITH RETRIES: " + ESHelper.getBulkSearchQuery(multipleReference));
        return buildAndGetElasticSearchRequestWithRetriesMultiples(authToken, caseTypeId,
                ESHelper.getBulkSearchQuery(multipleReference));
    }

    public List<SubmitMultipleEvent> retrieveMultipleCasesElasticSearch(String authToken, String caseTypeId,
                                                                        String multipleReference)
            throws IOException {
        List<SubmitMultipleEvent> submitMultipleEvents = new ArrayList<>();
        log.info("QUERY: " + ESHelper.getBulkSearchQuery(multipleReference));
        HttpEntity<String> request =
                new HttpEntity<>(ESHelper.getBulkSearchQuery(multipleReference), buildHeaders(authToken));
        String url = ccdClientConfig.buildRetrieveCasesUrlElasticSearch(caseTypeId);
        MultipleCaseSearchResult multipleCaseSearchResult = restTemplate.exchange(
                url, HttpMethod.POST, request, MultipleCaseSearchResult.class).getBody();
        if (multipleCaseSearchResult != null && multipleCaseSearchResult.getCases() != null) {
            submitMultipleEvents.addAll(multipleCaseSearchResult.getCases());
        }
        return submitMultipleEvents;
    }

    public CCDRequest startEventForCase(String authToken, String caseTypeId, String jurisdiction, String cid)
            throws IOException {
        HttpEntity<String> request =
                new HttpEntity<>(buildHeaders(authToken));
        String uri = ccdClientConfig.buildStartEventForCaseUrl(userService.getUserDetails(authToken).getUid(),
                jurisdiction,
                caseTypeId, cid);
        return restTemplate.exchange(uri, HttpMethod.GET, request, CCDRequest.class).getBody();
    }

    public CCDRequest startEventForCase(String authToken, String caseTypeId, String jurisdiction, String cid,
                                        String eventId) throws IOException {
        HttpEntity<String> request =
                new HttpEntity<>(buildHeaders(authToken));
        String uri = ccdClientConfig.buildStartEventUrlForCaseWorker(userService.getUserDetails(authToken).getUid(),
                jurisdiction,
                caseTypeId, cid, eventId);
        return restTemplate.exchange(uri, HttpMethod.GET, request, CCDRequest.class).getBody();
    }

    public CCDRequest startEventForCaseAPIRole(String authToken, String caseTypeId, String jurisdiction, String cid)
            throws IOException {
        HttpEntity<String> request =
                new HttpEntity<>(buildHeaders(authToken));
        String uri = ccdClientConfig.buildStartEventForCaseUrlAPIRole(userService.getUserDetails(authToken).getUid(),
                jurisdiction,
                caseTypeId, cid);
        return restTemplate.exchange(uri, HttpMethod.GET, request, CCDRequest.class).getBody();
    }

    public CCDRequest startEventForCaseBulkSingle(String authToken, String caseTypeId, String jurisdiction, String cid)
            throws IOException {
        HttpEntity<String> request =
                new HttpEntity<>(buildHeaders(authToken));
        String uri = ccdClientConfig.buildStartEventForCaseUrlBulkSingle(userService.getUserDetails(authToken).getUid(),
                jurisdiction,
                caseTypeId, cid);
        return restTemplate.exchange(uri, HttpMethod.GET, request, CCDRequest.class).getBody();
    }

    public CCDRequest startEventForCasePreAcceptBulkSingle(String authToken, String caseTypeId, String jurisdiction,
                                                           String cid) throws IOException {
        HttpEntity<String> request =
                new HttpEntity<>(buildHeaders(authToken));
        String uri = ccdClientConfig.buildStartEventForCaseUrlPreAcceptBulkSingle(
                userService.getUserDetails(authToken).getUid(), jurisdiction, caseTypeId, cid);
        return restTemplate.exchange(uri, HttpMethod.GET, request, CCDRequest.class).getBody();
    }

    public CCDRequest startBulkEventForCase(String authToken, String caseTypeId, String jurisdiction, String cid)
            throws IOException {
        HttpEntity<String> request =
                new HttpEntity<>(buildHeaders(authToken));
        String uri = ccdClientConfig.buildStartEventForBulkCaseUrl(userService.getUserDetails(authToken).getUid(),
                jurisdiction, caseTypeId, cid);
        return restTemplate.exchange(uri, HttpMethod.GET, request, CCDRequest.class).getBody();
    }

    public CCDRequest startBulkAmendEventForCase(String authToken, String caseTypeId, String jurisdiction, String cid)
            throws IOException {
        HttpEntity<String> request =
                new HttpEntity<>(buildHeaders(authToken));
        String uri = ccdClientConfig.buildStartEventForBulkAmendCaseUrl(userService.getUserDetails(authToken).getUid(),
                jurisdiction, caseTypeId, cid);
        return restTemplate.exchange(uri, HttpMethod.GET, request, CCDRequest.class).getBody();
    }

    public MultipleRequest startBulkAmendEventForMultiple(String authToken, String caseTypeId, String jur, String cid)
        throws IOException {
        HttpEntity<String> request = new HttpEntity<>(buildHeaders(authToken));
        String uri = ccdClientConfig.buildStartEventForBulkAmendCaseUrl(userService.getUserDetails(authToken).getUid(),
            jur, caseTypeId, cid);
        return restTemplate.exchange(uri, HttpMethod.GET, request, MultipleRequest.class).getBody();
    }

    public CCDRequest startDisposeEventForCase(String authToken, String caseTypeId, String jurisdiction, String cid)
            throws IOException {
        HttpEntity<String> request =
                new HttpEntity<>(buildHeaders(authToken));
        String uri = ccdClientConfig.buildStartDisposeEventForCaseUrl(userService.getUserDetails(authToken).getUid(),
                jurisdiction,
                caseTypeId, cid);
        return restTemplate.exchange(uri, HttpMethod.GET, request, CCDRequest.class).getBody();
    }

    public SubmitEvent submitEventForCase(String authToken, CaseData caseData, String caseTypeId, String jurisdiction,
                                          CCDRequest req, String cid) throws IOException {
        var params = CcdSubmitEventParams.builder()
                .authToken(authToken)
                .ccdRequest(req)
                .caseId(cid)
                .caseData(caseData)
                .caseTypeId(caseTypeId)
                .jurisdiction(jurisdiction)
                .eventSummary(UPDATE_EVENT_SUMMARY)
                .build();

        return submitEventForCase(params);
    }

    public SubmitEvent submitEventForCase(CcdSubmitEventParams params) throws IOException {
        var request = new HttpEntity<>(caseDataBuilder.buildCaseDataContent(params.getCaseData(),
                params.getCcdRequest(), params.getEventSummary(), params.getEventDescription()),
                buildHeaders(params.getAuthToken()));
        var uri = ccdClientConfig.buildSubmitEventForCaseUrl(userService.getUserDetails(params.getAuthToken()).getUid(),
                params.getJurisdiction(), params.getCaseTypeId(), params.getCaseId());
        return restTemplate.exchange(uri, HttpMethod.POST, request, SubmitEvent.class).getBody();
    }

    public SubmitBulkEvent submitBulkEventForCase(String authToken, BulkData bulkData, String caseTypeId,
                                                  String jurisdiction, CCDRequest req, String cid)
            throws IOException {
        HttpEntity<CaseDataContent> request = new HttpEntity<>(caseDataBuilder.buildBulkDataContent(bulkData, req,
                UPDATE_BULK_EVENT_SUMMARY), buildHeaders(authToken));
        String uri = ccdClientConfig.buildSubmitEventForCaseUrl(userService.getUserDetails(authToken).getUid(),
                jurisdiction, caseTypeId, cid);
        return restTemplate.exchange(uri, HttpMethod.POST, request, SubmitBulkEvent.class).getBody();
    }

    public SubmitMultipleEvent submitMultipleEventForCase(String authToken, MultipleData multipleData,
                                                          String caseTypeId, String jurisdiction, GenericRequest req,
                                                          String cid) throws IOException {
        HttpEntity<CaseDataContent> request =
                new HttpEntity<>(caseDataBuilder.buildMultipleDataContent(multipleData, req, UPDATE_BULK_EVENT_SUMMARY),
                        buildHeaders(authToken));
        String uri = ccdClientConfig.buildSubmitEventForCaseUrl(userService.getUserDetails(authToken).getUid(),
                jurisdiction, caseTypeId, cid);
        return restTemplate.exchange(uri, HttpMethod.POST, request, SubmitMultipleEvent.class).getBody();
    }

    public SubmitMultipleEvent submitMultipleCreation(String authToken, MultipleData multipleData,
                                                      String caseTypeId, String jurisdiction,
                                                      CCDRequest req) throws IOException {
        HttpEntity<CaseDataContent> request =
                new HttpEntity<>(caseDataBuilder.buildMultipleDataContent(multipleData, req, CREATION_EVENT_SUMMARY),
                        buildHeaders(authToken));
        String uri = ccdClientConfig.buildSubmitCaseCreationUrl(userService.getUserDetails(authToken).getUid(),
                jurisdiction, caseTypeId);
        return restTemplate.exchange(uri, HttpMethod.POST, request, SubmitMultipleEvent.class).getBody();
    }

    public SubmitMultipleEvent getMultipleByName(String token, String ctid, String multipleName)
        throws IOException, NameNotFoundException {

        String requestBody = ESHelper.getBulkSearchQueryByName(multipleName);
        HttpEntity<String> request = new HttpEntity<>(requestBody, buildHeaders(token));
        ResponseEntity<MultipleCaseSearchResult> response;

        try {
            String url = ccdClientConfig.buildRetrieveCasesUrlElasticSearch(ctid);
            response = restTemplate.exchange(url, HttpMethod.POST, request, MultipleCaseSearchResult.class);
        } catch (RestClientResponseException exception) {
            log.error("Error from ccd - {}", exception.getMessage());
            throw exception;
        }

        MultipleCaseSearchResult resultBody = response.getBody();

        if (resultBody != null && CollectionUtils.isNotEmpty(resultBody.getCases())) {
            return resultBody.getCases().getFirst();
        }

        throw new NameNotFoundException("Multiple with name: " + multipleName + " not found.");
    }

    public AuditEventsResponse retrieveCaseEvents(String authToken, String cid) throws IOException {
        String uri = ccdClientConfig.buildCaseEventsUrl(cid);

        HttpHeaders httpHeaders = buildHeaders(authToken);
        httpHeaders.add(EXPERIMENTAL, "true");

        HttpEntity<String> request = new HttpEntity<>(httpHeaders);

        return restTemplate.exchange(uri, HttpMethod.GET, request, AuditEventsResponse.class).getBody();
    }

    public SubmitEvent submitUpdateRepEvent(String authToken, Map<String, Object> changeOrganisationRequest,
                                            String caseTypeId, String jurisdiction, CCDRequest req,
                                            String cid) throws IOException {
        HttpEntity<CaseDataContent> request =
                new HttpEntity<>(caseDataBuilder.buildChangeOrganisationDataContent(changeOrganisationRequest, req,
                        UPDATE_CHANGE_ORG_SUMMARY), buildHeaders(authToken));
        String uri = ccdClientConfig.buildSubmitEventForCaseUrl(userService.getUserDetails(authToken).getUid(),
                jurisdiction, caseTypeId, cid);
        return restTemplate.exchange(uri, HttpMethod.POST, request, SubmitEvent.class).getBody();
    }

    public CaseUserAssignmentData retrieveCaseAssignments(String authToken, String cid) throws IOException {
        String uri = ccdClientConfig.buildUrlForCaseAccessRetrieval(cid);
        HttpHeaders httpHeaders = buildHeaders(authToken);
        httpHeaders.add(EXPERIMENTAL, "true");

        HttpEntity<String> request = new HttpEntity<>(httpHeaders);

        return restTemplate.exchange(uri, HttpMethod.GET, request, CaseUserAssignmentData.class).getBody();
    }

    public String revokeCaseAssignments(String authToken, CaseUserAssignmentData caseUserAssignmentData)
            throws IOException {
        String uri = ccdClientConfig.buildUrlForCaseAccessRevocation();

        HttpHeaders httpHeaders = buildHeaders(authToken);
        httpHeaders.add(EXPERIMENTAL, "true");

        HttpEntity<CaseUserAssignmentData> request = new HttpEntity<>(caseUserAssignmentData, httpHeaders);

        return restTemplate.exchange(uri, HttpMethod.DELETE, request, String.class).getBody();
    }

    public CCDRequest startEventForUpdateRep(String authToken, String caseTypeId, String jurisdiction,
                                             String cid) throws IOException {
        HttpEntity<String> request =
                new HttpEntity<>(buildHeaders(authToken));
        String uri = ccdClientConfig.buildStartUpdateRepEventForCaseUrl(
                userService.getUserDetails(authToken).getUid(), jurisdiction, caseTypeId, cid);
        return restTemplate.exchange(uri, HttpMethod.GET, request, CCDRequest.class).getBody();
    }

    public ResponseEntity<Object> setSupplementaryData(String authToken, Map<String, Object> payload, String caseId)
            throws IOException {
        HttpEntity<Map<String, Object>> request = new HttpEntity<>(payload, buildHeaders(authToken));
        String uri = ccdClientConfig.buildUrlForSupplementaryApi(caseId);
        return restTemplate.exchange(uri, HttpMethod.POST, request, Object.class);
    }

    public ResponseEntity<Object> addUserToMultiple(
            String authToken,
            String jurisdiction,
            String caseTypeId,
            String multiCid,
            Map<String, String> payload) throws IOException {
        HttpEntity<Map<String, String>> request = new HttpEntity<>(payload, buildHeaders(authToken));
        String uri = ccdClientConfig.addLegalRepToMultiCaseUrl(
                userService.getUserDetails(authToken).getUid(),
                jurisdiction,
                caseTypeId,
                multiCid);
        return restTemplate.exchange(uri, HttpMethod.POST, request, Object.class);
    }

    public ResponseEntity<Object> removeUserFromMultiple(
            String authToken,
            String jurisdiction,
            String caseTypeId,
            String multiCid,
            String lrUid) throws IOException {
        HttpEntity<String> request = new HttpEntity<>(buildHeaders(authToken));
        String uri = ccdClientConfig.removeLegalRepFromMultiCaseUrl(
                userService.getUserDetails(authToken).getUid(),
                jurisdiction,
                caseTypeId,
                multiCid,
                lrUid);
        return restTemplate.exchange(uri, HttpMethod.DELETE, request, Object.class);
    }

    public HttpHeaders buildHeaders(String authToken) throws IOException {
        if (!authToken.matches("[a-zA-Z0-9._\\s\\S]+$")) {
            throw new IOException("authToken regex exception");
        }
        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.AUTHORIZATION, authToken);
        headers.add(SERVICE_AUTHORIZATION, authTokenGenerator.generate());
        headers.add(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE);
        return headers;
    }

}
