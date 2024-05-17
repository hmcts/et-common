package uk.gov.hmcts.ecm.common.client;

import lombok.extern.slf4j.Slf4j;

import static uk.gov.hmcts.ecm.common.model.helper.Constants.AMEND_MULTIPLE_EVENT_TRIGGER_ID;
import static uk.gov.hmcts.ecm.common.model.helper.Constants.AMEND_SINGLE_EVENT_TRIGGER_ID;
import static uk.gov.hmcts.ecm.common.model.helper.Constants.CASE_TRANSFER_EVENT_TRIGGER_ID;
import static uk.gov.hmcts.ecm.common.model.helper.Constants.CASE_TRANSFER_SAME_COUNTRY_ECC_LINKED_CASE_EVENT_TRIGGER_ID;
import static uk.gov.hmcts.ecm.common.model.helper.Constants.CREATE_MULTIPLE_EVENT_TRIGGER_ID;
import static uk.gov.hmcts.ecm.common.model.helper.Constants.CREATION_EVENT_TRIGGER_ID;
import static uk.gov.hmcts.ecm.common.model.helper.Constants.CREATION_TRANSFER_EVENT_TRIGGER_ID;
import static uk.gov.hmcts.ecm.common.model.helper.Constants.DISPOSE_EVENT_TRIGGER_ID;
import static uk.gov.hmcts.ecm.common.model.helper.Constants.PRE_ACCEPT_CASE_TRIGGER_ID_BULK;
import static uk.gov.hmcts.ecm.common.model.helper.Constants.RETURN_TRANSFER_EVENT_TRIGGER_ID;
import static uk.gov.hmcts.ecm.common.model.helper.Constants.UPDATE_BULK_EVENT_TRIGGER_ID;
import static uk.gov.hmcts.ecm.common.model.helper.Constants.UPDATE_EVENT_TRIGGER_ID;
import static uk.gov.hmcts.ecm.common.model.helper.Constants.UPDATE_EVENT_TRIGGER_ID_BULK;
import static uk.gov.hmcts.ecm.common.model.helper.Constants.UPDATE_REPRESENTATION_EVENT_TRIGGER_ID;

@Slf4j
public class CcdClientConfig {

    private static final String FORMAT = "Format: {}";
    private static final String URL_DETAILS = "Looking cases by: uid: {}, jid: {}, ctid: {}, param: {}";

    private static final String START_CASE_CREATION_URL_CASEWORKER_FORMAT =
            "%s/caseworkers/%s/jurisdictions/%s/case-types/%s/event-triggers/%s/token?ignore-warning=true";
    private static final String SUBMIT_CASE_CREATION_URL_CASEWORKER_FORMAT =
            "%s/caseworkers/%s/jurisdictions/%s/case-types/%s/cases";
    private static final String RETRIEVE_CASE_URL_CASEWORKER_FORMAT =
            "%s/caseworkers/%s/jurisdictions/%s/case-types/%s/cases/%s";
    private static final String RETRIEVE_CASES_URL_CASEWORKER_FORMAT =
            "%s/caseworkers/%s/jurisdictions/%s/case-types/%s/cases?%s";
    private static final String START_EVENT_FOR_CASE_URL_CASEWORKER_FORMAT =
            "%s/caseworkers/%s/jurisdictions/%s/case-types/%s/cases/%s/event-triggers/%s/token";
    private static final String SUBMIT_EVENT_FOR_URL_CASEWORKER_FORMAT =
            "%s/caseworkers/%s/jurisdictions/%s/case-types/%s/cases/%s/events";
    private static final String PAGINATION_METADATA_FORMAT =
            "%s/caseworkers/%s/jurisdictions/%s/case-types/%s/cases/pagination_metadata";
    private static final String SEARCH_CASES_FORMAT = "%s/searchCases?%s";

    private static final String RETRIEVE_CASE_EVENTS = "%s/cases/%s/events";

    private static final String CASE_USERS_RETRIEVE = "%s/case-users?case_ids=%s";

    private static final String CASE_USERS_REVOKE = "%s/case-users";

    private static final String SET_SUPPLEMENTARY_DATA = "%s/cases/%s/supplementary-data";
    private static final String ADD_LEGAL_REP_TO_MULTIPLE =
            "%s/caseworkers/%s/jurisdictions/%s/case-types/%s/cases/%s/users";
    private static final String REMOVE_LEGAL_REP_FROM_MULTIPLE =
            "%s/caseworkers/%s/jurisdictions/%s/case-types/%s/cases/%s/users/%s";
    private final String ccdDataStoreApiBaseUrl;

    public CcdClientConfig(String ccdDataStoreApiBaseUrl) {
        this.ccdDataStoreApiBaseUrl = ccdDataStoreApiBaseUrl;
    }

    String buildStartCaseCreationUrl(String uid, String jid, String ctid) {
        return String.format(START_CASE_CREATION_URL_CASEWORKER_FORMAT, ccdDataStoreApiBaseUrl, uid, jid, ctid,
                CREATION_EVENT_TRIGGER_ID);
    }

    String buildStartCaseCreationTransferUrl(String uid, String jid, String ctid) {
        return String.format(START_CASE_CREATION_URL_CASEWORKER_FORMAT, ccdDataStoreApiBaseUrl, uid, jid, ctid,
                CREATION_TRANSFER_EVENT_TRIGGER_ID);
    }

    String buildStartCaseTransferUrl(String uid, String jid, String ctid, String cid) {
        return String.format(START_EVENT_FOR_CASE_URL_CASEWORKER_FORMAT, ccdDataStoreApiBaseUrl, uid, jid, ctid, cid,
                CASE_TRANSFER_EVENT_TRIGGER_ID);
    }

    String buildStartCaseTransferSameCountryLinkedCaseUrl(String uid, String jid, String ctid, String cid) {
        return String.format(START_EVENT_FOR_CASE_URL_CASEWORKER_FORMAT, ccdDataStoreApiBaseUrl, uid, jid, ctid, cid,
                CASE_TRANSFER_SAME_COUNTRY_ECC_LINKED_CASE_EVENT_TRIGGER_ID
        );
    }

    String buildReturnCaseCreationTransferUrl(String uid, String jid, String ctid, String cid) {
        return String.format(START_EVENT_FOR_CASE_URL_CASEWORKER_FORMAT, ccdDataStoreApiBaseUrl, uid, jid, ctid, cid,
                RETURN_TRANSFER_EVENT_TRIGGER_ID);
    }

    String buildStartCaseMultipleCreationUrl(String uid, String jid, String ctid) {
        return String.format(START_CASE_CREATION_URL_CASEWORKER_FORMAT, ccdDataStoreApiBaseUrl, uid, jid, ctid,
                CREATE_MULTIPLE_EVENT_TRIGGER_ID);
    }

    String buildSubmitCaseCreationUrl(String uid, String jid, String ctid) {
        return String.format(SUBMIT_CASE_CREATION_URL_CASEWORKER_FORMAT, ccdDataStoreApiBaseUrl, uid, jid, ctid);
    }

    String buildRetrieveCaseUrl(String uid, String jid, String ctid, String cid) {
        return String.format(RETRIEVE_CASE_URL_CASEWORKER_FORMAT, ccdDataStoreApiBaseUrl, uid, jid, ctid, cid);
    }

    String buildRetrieveCasesUrl(String uid, String jid, String ctid, String page) {
        String param = "page=" + page;
        String url = String.format(RETRIEVE_CASES_URL_CASEWORKER_FORMAT, ccdDataStoreApiBaseUrl, uid, jid, ctid, param);
        log.info(URL_DETAILS, uid, jid, ctid, param);
        log.info(FORMAT, url);
        return url;
    }

    String buildRetrieveCasesUrlElasticSearch(String ctid) {
        String format = String.format(SEARCH_CASES_FORMAT, ccdDataStoreApiBaseUrl, "ctid=" + ctid);
        log.info(FORMAT, format);
        return format;
    }

    String buildStartEventForCaseUrlAPIRole(String uid, String jid, String ctid, String cid) {
        return String.format(START_EVENT_FOR_CASE_URL_CASEWORKER_FORMAT,
                ccdDataStoreApiBaseUrl, uid, jid, ctid, cid, AMEND_SINGLE_EVENT_TRIGGER_ID);
    }

    String buildStartEventForCaseUrl(String uid, String jid, String ctid, String cid) {
        return String.format(START_EVENT_FOR_CASE_URL_CASEWORKER_FORMAT,
                ccdDataStoreApiBaseUrl, uid, jid, ctid, cid, UPDATE_EVENT_TRIGGER_ID);
    }

    String buildStartEventForCaseUrlBulkSingle(String uid, String jid, String ctid, String cid) {
        return String.format(START_EVENT_FOR_CASE_URL_CASEWORKER_FORMAT, ccdDataStoreApiBaseUrl, uid, jid,
                ctid, cid, UPDATE_EVENT_TRIGGER_ID_BULK);
    }

    String buildStartEventForCaseUrlPreAcceptBulkSingle(String uid, String jid, String ctid, String cid) {
        return String.format(START_EVENT_FOR_CASE_URL_CASEWORKER_FORMAT, ccdDataStoreApiBaseUrl, uid, jid,
                ctid, cid, PRE_ACCEPT_CASE_TRIGGER_ID_BULK);
    }

    String buildStartEventForBulkCaseUrl(String uid, String jid, String ctid, String cid) {
        return String.format(START_EVENT_FOR_CASE_URL_CASEWORKER_FORMAT, ccdDataStoreApiBaseUrl, uid, jid,
                ctid, cid, UPDATE_BULK_EVENT_TRIGGER_ID);
    }

    String buildStartEventForBulkAmendCaseUrl(String uid, String jid, String ctid, String cid) {
        return String.format(START_EVENT_FOR_CASE_URL_CASEWORKER_FORMAT, ccdDataStoreApiBaseUrl, uid, jid,
                ctid, cid, AMEND_MULTIPLE_EVENT_TRIGGER_ID);
    }

    String buildSubmitEventForCaseUrl(String uid, String jid, String ctid, String cid) {
        return String.format(SUBMIT_EVENT_FOR_URL_CASEWORKER_FORMAT, ccdDataStoreApiBaseUrl, uid, jid, ctid, cid);
    }

    String buildStartDisposeEventForCaseUrl(String uid, String jid, String ctid, String cid) {
        return String.format(START_EVENT_FOR_CASE_URL_CASEWORKER_FORMAT,
                ccdDataStoreApiBaseUrl, uid, jid, ctid, cid, DISPOSE_EVENT_TRIGGER_ID);
    }

    String buildStartUpdateRepEventForCaseUrl(String uid, String jid, String ctid, String cid) {
        return String.format(START_EVENT_FOR_CASE_URL_CASEWORKER_FORMAT,
            ccdDataStoreApiBaseUrl, uid, jid, ctid, cid, UPDATE_REPRESENTATION_EVENT_TRIGGER_ID);
    }

    String buildPaginationMetadataCaseUrl(String uid, String jid, String ctid) {
        return String.format(PAGINATION_METADATA_FORMAT, ccdDataStoreApiBaseUrl, uid, jid, ctid);
    }

    String buildCaseEventsUrl(String cid) {
        return String.format(RETRIEVE_CASE_EVENTS, ccdDataStoreApiBaseUrl, cid);
    }

    String buildUrlForCaseAccessRetrieval(String cid) {
        return String.format(CASE_USERS_RETRIEVE, ccdDataStoreApiBaseUrl, cid);
    }

    String buildUrlForCaseAccessRevocation() {
        return String.format(CASE_USERS_REVOKE, ccdDataStoreApiBaseUrl);
    }

    String buildUrlForSupplementaryApi(String caseId) {
        return String.format(SET_SUPPLEMENTARY_DATA, ccdDataStoreApiBaseUrl, caseId);
    }

    String buildStartEventUrlForCaseWorker(String uid, String jid, String ctid, String cid, String eventId) {
        return String.format(START_EVENT_FOR_CASE_URL_CASEWORKER_FORMAT, ccdDataStoreApiBaseUrl, uid, jid, ctid, cid,
                eventId);
    }

    String addLegalRepToMultiCaseUrl(String adminUid, String jid, String ctid, String mid) {
        return String.format(ADD_LEGAL_REP_TO_MULTIPLE, ccdDataStoreApiBaseUrl, adminUid, jid, ctid, mid);
    }

    String removeLegalRepFromMultiCaseUrl(String adminUid, String jid, String ctid, String mid, String lrUid) {
        return String.format(REMOVE_LEGAL_REP_FROM_MULTIPLE, ccdDataStoreApiBaseUrl, adminUid, jid, ctid, mid, lrUid);
    }
}
