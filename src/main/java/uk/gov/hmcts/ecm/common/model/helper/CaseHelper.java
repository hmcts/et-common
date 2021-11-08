package uk.gov.hmcts.ecm.common.model.helper;

import java.util.List;

import static uk.gov.hmcts.ecm.common.model.helper.Constants.SCOTLAND_BULK_CASE_TYPE_ID;
import static uk.gov.hmcts.ecm.common.model.helper.Constants.SCOTLAND_CASE_TYPE_ID;
import static uk.gov.hmcts.ecm.common.model.helper.Constants.SCOTLAND_LISTING_CASE_TYPE_ID;

public class CaseHelper {

    private static final List<String> SCOTTISH_CASE_TYPE_IDS = List.of(SCOTLAND_CASE_TYPE_ID,
            SCOTLAND_BULK_CASE_TYPE_ID, SCOTLAND_LISTING_CASE_TYPE_ID);

    private CaseHelper() {
        // All access via static methods
    }

    public static boolean isScottishCase(String caseTypeId) {
        return SCOTTISH_CASE_TYPE_IDS.contains(caseTypeId);
    }
}