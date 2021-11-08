package uk.gov.hmcts.ecm.common.model.helper;

import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static uk.gov.hmcts.ecm.common.model.helper.Constants.ENGLANDWALES_BULK_CASE_TYPE_ID;
import static uk.gov.hmcts.ecm.common.model.helper.Constants.ENGLANDWALES_CASE_TYPE_ID;
import static uk.gov.hmcts.ecm.common.model.helper.Constants.ENGLANDWALES_LISTING_CASE_TYPE_ID;
import static uk.gov.hmcts.ecm.common.model.helper.Constants.SCOTLAND_BULK_CASE_TYPE_ID;
import static uk.gov.hmcts.ecm.common.model.helper.Constants.SCOTLAND_CASE_TYPE_ID;
import static uk.gov.hmcts.ecm.common.model.helper.Constants.SCOTLAND_LISTING_CASE_TYPE_ID;

public class CaseHelperTest {

    @Test
    public void testIsScottishCase() {
        assertTrue(CaseHelper.isScottishCase(SCOTLAND_BULK_CASE_TYPE_ID));
        assertTrue(CaseHelper.isScottishCase(SCOTLAND_CASE_TYPE_ID));
        assertTrue(CaseHelper.isScottishCase(SCOTLAND_LISTING_CASE_TYPE_ID));
        assertFalse(CaseHelper.isScottishCase(ENGLANDWALES_BULK_CASE_TYPE_ID));
        assertFalse(CaseHelper.isScottishCase(ENGLANDWALES_CASE_TYPE_ID));
        assertFalse(CaseHelper.isScottishCase(ENGLANDWALES_LISTING_CASE_TYPE_ID));
        assertFalse(CaseHelper.isScottishCase(""));
    }
}
