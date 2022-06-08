package uk.gov.hmcts.et.common.client;

import lombok.extern.slf4j.Slf4j;
import uk.gov.hmcts.et.common.model.helper.Constants;

@Slf4j
public class CcdClientConfig {

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

    private final String ccdDataStoreApiBaseUrl;

    public CcdClientConfig(String ccdDataStoreApiBaseUrl) {
        this.ccdDataStoreApiBaseUrl = ccdDataStoreApiBaseUrl;
    }

    String buildStartCaseCreationUrl(String uid, String jid, String ctid) {
        return String.format(START_CASE_CREATION_URL_CASEWORKER_FORMAT, ccdDataStoreApiBaseUrl, uid, jid, ctid,
                Constants.CREATION_EVENT_TRIGGER_ID);
    }

    String buildStartCaseCreationTransferUrl(String uid, String jid, String ctid) {
        return String.format(START_CASE_CREATION_URL_CASEWORKER_FORMAT, ccdDataStoreApiBaseUrl, uid, jid, ctid,
                Constants.CREATION_TRANSFER_EVENT_TRIGGER_ID);
    }

    String buildStartCaseTransferUrl(String uid, String jid, String ctid, String cid) {
        return String.format(START_EVENT_FOR_CASE_URL_CASEWORKER_FORMAT, ccdDataStoreApiBaseUrl, uid, jid, ctid, cid,
                Constants.CASE_TRANSFER_EVENT_TRIGGER_ID);
    }

    String buildStartCaseTransferSameCountryLinkedCaseUrl(String uid, String jid, String ctid, String cid) {
        return String.format(START_EVENT_FOR_CASE_URL_CASEWORKER_FORMAT, ccdDataStoreApiBaseUrl, uid, jid, ctid, cid,
                Constants.CASE_TRANSFER_SAME_COUNTRY_ECC_LINKED_CASE_EVENT_TRIGGER_ID
        );
    }

    String buildReturnCaseCreationTransferUrl(String uid, String jid, String ctid, String cid) {
        return String.format(START_EVENT_FOR_CASE_URL_CASEWORKER_FORMAT, ccdDataStoreApiBaseUrl, uid, jid, ctid, cid,
                Constants.RETURN_TRANSFER_EVENT_TRIGGER_ID);
    }

    String buildStartCaseMultipleCreationUrl(String uid, String jid, String ctid) {
        return String.format(START_CASE_CREATION_URL_CASEWORKER_FORMAT, ccdDataStoreApiBaseUrl, uid, jid, ctid,
                Constants.CREATE_MULTIPLE_EVENT_TRIGGER_ID);
    }

    String buildSubmitCaseCreationUrl(String uid, String jid, String ctid) {
        return String.format(SUBMIT_CASE_CREATION_URL_CASEWORKER_FORMAT, ccdDataStoreApiBaseUrl, uid, jid, ctid);
    }

    String buildRetrieveCaseUrl(String uid, String jid, String ctid, String cid) {
        return String.format(RETRIEVE_CASE_URL_CASEWORKER_FORMAT, ccdDataStoreApiBaseUrl, uid, jid, ctid, cid);
    }

    String buildRetrieveCasesUrl(String uid, String jid, String ctid, String page) {
        String param = "page=" + page;
        log.info("Looking cases by: uid: " + uid + " jid: " + jid + " ctid: " + ctid + " param: " + param);
        log.info("Format: " + String.format(RETRIEVE_CASES_URL_CASEWORKER_FORMAT, ccdDataStoreApiBaseUrl, uid,
                jid, ctid, param));
        return String.format(RETRIEVE_CASES_URL_CASEWORKER_FORMAT, ccdDataStoreApiBaseUrl, uid, jid, ctid, param);
    }

    String buildRetrieveCasesUrlElasticSearch(String ctid) {
        String format = String.format(SEARCH_CASES_FORMAT, ccdDataStoreApiBaseUrl, "ctid=" + ctid);
        log.info("Format: " + format);
        return format;
    }

    String buildStartEventForCaseUrlAPIRole(String uid, String jid, String ctid, String cid) {
        return String.format(START_EVENT_FOR_CASE_URL_CASEWORKER_FORMAT,
                ccdDataStoreApiBaseUrl, uid, jid, ctid, cid, Constants.AMEND_SINGLE_EVENT_TRIGGER_ID);
    }

    String buildStartEventForCaseUrl(String uid, String jid, String ctid, String cid) {
        return String.format(START_EVENT_FOR_CASE_URL_CASEWORKER_FORMAT,
                ccdDataStoreApiBaseUrl, uid, jid, ctid, cid, Constants.UPDATE_EVENT_TRIGGER_ID);
    }

    String buildStartEventForCaseUrlBulkSingle(String uid, String jid, String ctid, String cid) {
        return String.format(START_EVENT_FOR_CASE_URL_CASEWORKER_FORMAT, ccdDataStoreApiBaseUrl, uid, jid,
                ctid, cid, Constants.UPDATE_EVENT_TRIGGER_ID_BULK);
    }

    String buildStartEventForCaseUrlPreAcceptBulkSingle(String uid, String jid, String ctid, String cid) {
        return String.format(START_EVENT_FOR_CASE_URL_CASEWORKER_FORMAT, ccdDataStoreApiBaseUrl, uid, jid,
                ctid, cid, Constants.PRE_ACCEPT_CASE_TRIGGER_ID_BULK);
    }

    String buildStartEventForBulkCaseUrl(String uid, String jid, String ctid, String cid) {
        return String.format(START_EVENT_FOR_CASE_URL_CASEWORKER_FORMAT, ccdDataStoreApiBaseUrl, uid, jid,
                ctid, cid, Constants.UPDATE_BULK_EVENT_TRIGGER_ID);
    }

    String buildStartEventForBulkAmendCaseUrl(String uid, String jid, String ctid, String cid) {
        return String.format(START_EVENT_FOR_CASE_URL_CASEWORKER_FORMAT, ccdDataStoreApiBaseUrl, uid, jid,
                ctid, cid, Constants.AMEND_MULTIPLE_EVENT_TRIGGER_ID);
    }

    String buildSubmitEventForCaseUrl(String uid, String jid, String ctid, String cid) {
        return String.format(SUBMIT_EVENT_FOR_URL_CASEWORKER_FORMAT, ccdDataStoreApiBaseUrl, uid, jid, ctid, cid);
    }

    String buildStartDisposeEventForCaseUrl(String uid, String jid, String ctid, String cid) {
        return String.format(START_EVENT_FOR_CASE_URL_CASEWORKER_FORMAT,
                ccdDataStoreApiBaseUrl, uid, jid, ctid, cid, Constants.DISPOSE_EVENT_TRIGGER_ID);
    }

    String buildPaginationMetadataCaseUrl(String uid, String jid, String ctid) {
        return String.format(PAGINATION_METADATA_FORMAT, ccdDataStoreApiBaseUrl, uid, jid, ctid);
    }

}
