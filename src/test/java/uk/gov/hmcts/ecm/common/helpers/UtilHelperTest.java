package uk.gov.hmcts.ecm.common.helpers;

import org.junit.Test;
import static org.junit.Assert.*;
import static uk.gov.hmcts.ecm.common.model.helper.Constants.*;

public class UtilHelperTest {

    @Test
    public void getCaseTypeId() {
        String caseId = MANCHESTER_DEV_CASE_TYPE_ID;
        assertEquals(caseId, UtilHelper.getCaseTypeId(MANCHESTER_DEV_BULK_CASE_TYPE_ID));
        caseId = MANCHESTER_USERS_CASE_TYPE_ID;
        assertEquals(caseId, UtilHelper.getCaseTypeId(MANCHESTER_USERS_BULK_CASE_TYPE_ID));
        caseId = SCOTLAND_DEV_CASE_TYPE_ID;
        assertEquals(caseId, UtilHelper.getCaseTypeId(SCOTLAND_DEV_BULK_CASE_TYPE_ID));
        caseId = SCOTLAND_USERS_CASE_TYPE_ID;
        assertEquals(caseId, UtilHelper.getCaseTypeId(SCOTLAND_USERS_BULK_CASE_TYPE_ID));
        caseId = MANCHESTER_CASE_TYPE_ID;
        assertEquals(caseId, UtilHelper.getCaseTypeId(MANCHESTER_BULK_CASE_TYPE_ID));
        caseId = BRISTOL_DEV_CASE_TYPE_ID;
        assertEquals(caseId, UtilHelper.getCaseTypeId(BRISTOL_DEV_BULK_CASE_TYPE_ID));
        caseId = BRISTOL_USERS_CASE_TYPE_ID;
        assertEquals(caseId, UtilHelper.getCaseTypeId(BRISTOL_USERS_BULK_CASE_TYPE_ID));
        caseId = BRISTOL_CASE_TYPE_ID;
        assertEquals(caseId, UtilHelper.getCaseTypeId(BRISTOL_BULK_CASE_TYPE_ID));
        caseId = LEEDS_DEV_CASE_TYPE_ID;
        assertEquals(caseId, UtilHelper.getCaseTypeId(LEEDS_DEV_BULK_CASE_TYPE_ID));
        caseId = LEEDS_USERS_CASE_TYPE_ID;
        assertEquals(caseId, UtilHelper.getCaseTypeId(LEEDS_USERS_BULK_CASE_TYPE_ID));
        caseId = LEEDS_CASE_TYPE_ID;
        assertEquals(caseId, UtilHelper.getCaseTypeId(LEEDS_BULK_CASE_TYPE_ID));
        caseId = LONDON_CENTRAL_DEV_CASE_TYPE_ID;
        assertEquals(caseId, UtilHelper.getCaseTypeId(LONDON_CENTRAL_DEV_BULK_CASE_TYPE_ID));
        caseId = LONDON_CENTRAL_USERS_CASE_TYPE_ID;
        assertEquals(caseId, UtilHelper.getCaseTypeId(LONDON_CENTRAL_USERS_BULK_CASE_TYPE_ID));
        caseId = LONDON_CENTRAL_CASE_TYPE_ID;
        assertEquals(caseId, UtilHelper.getCaseTypeId(LONDON_CENTRAL_BULK_CASE_TYPE_ID));
        caseId = LONDON_EAST_DEV_CASE_TYPE_ID;
        assertEquals(caseId, UtilHelper.getCaseTypeId(LONDON_EAST_DEV_BULK_CASE_TYPE_ID));
        caseId = LONDON_EAST_USERS_CASE_TYPE_ID;
        assertEquals(caseId, UtilHelper.getCaseTypeId(LONDON_EAST_USERS_BULK_CASE_TYPE_ID));
        caseId = LONDON_EAST_CASE_TYPE_ID;
        assertEquals(caseId, UtilHelper.getCaseTypeId(LONDON_EAST_BULK_CASE_TYPE_ID));
        caseId = LONDON_SOUTH_DEV_CASE_TYPE_ID;
        assertEquals(caseId, UtilHelper.getCaseTypeId(LONDON_SOUTH_DEV_BULK_CASE_TYPE_ID));
        caseId = LONDON_SOUTH_USERS_CASE_TYPE_ID;
        assertEquals(caseId, UtilHelper.getCaseTypeId(LONDON_SOUTH_USERS_BULK_CASE_TYPE_ID));
        caseId = LONDON_SOUTH_CASE_TYPE_ID;
        assertEquals(caseId, UtilHelper.getCaseTypeId(LONDON_SOUTH_BULK_CASE_TYPE_ID));
        caseId = MIDLANDS_EAST_DEV_CASE_TYPE_ID;
        assertEquals(caseId, UtilHelper.getCaseTypeId(MIDLANDS_EAST_DEV_BULK_CASE_TYPE_ID));
        caseId = MIDLANDS_EAST_USERS_CASE_TYPE_ID;
        assertEquals(caseId, UtilHelper.getCaseTypeId(MIDLANDS_EAST_USERS_BULK_CASE_TYPE_ID));
        caseId = MIDLANDS_EAST_CASE_TYPE_ID;
        assertEquals(caseId, UtilHelper.getCaseTypeId(MIDLANDS_EAST_BULK_CASE_TYPE_ID));
        caseId = MIDLANDS_WEST_DEV_CASE_TYPE_ID;
        assertEquals(caseId, UtilHelper.getCaseTypeId(MIDLANDS_WEST_DEV_BULK_CASE_TYPE_ID));
        caseId = MIDLANDS_WEST_USERS_CASE_TYPE_ID;
        assertEquals(caseId, UtilHelper.getCaseTypeId(MIDLANDS_WEST_USERS_BULK_CASE_TYPE_ID));
        caseId = MIDLANDS_WEST_CASE_TYPE_ID;
        assertEquals(caseId, UtilHelper.getCaseTypeId(MIDLANDS_WEST_BULK_CASE_TYPE_ID));
        caseId = NEWCASTLE_DEV_CASE_TYPE_ID;
        assertEquals(caseId, UtilHelper.getCaseTypeId(NEWCASTLE_DEV_BULK_CASE_TYPE_ID));
        caseId = NEWCASTLE_USERS_CASE_TYPE_ID;
        assertEquals(caseId, UtilHelper.getCaseTypeId(NEWCASTLE_USERS_BULK_CASE_TYPE_ID));
        caseId = NEWCASTLE_CASE_TYPE_ID;
        assertEquals(caseId, UtilHelper.getCaseTypeId(NEWCASTLE_BULK_CASE_TYPE_ID));
        caseId = WALES_DEV_CASE_TYPE_ID;
        assertEquals(caseId, UtilHelper.getCaseTypeId(WALES_DEV_BULK_CASE_TYPE_ID));
        caseId = WALES_USERS_CASE_TYPE_ID;
        assertEquals(caseId, UtilHelper.getCaseTypeId(WALES_USERS_BULK_CASE_TYPE_ID));
        caseId = WALES_CASE_TYPE_ID;
        assertEquals(caseId, UtilHelper.getCaseTypeId(WALES_BULK_CASE_TYPE_ID));
        caseId = WATFORD_DEV_CASE_TYPE_ID;
        assertEquals(caseId, UtilHelper.getCaseTypeId(WATFORD_DEV_BULK_CASE_TYPE_ID));
        caseId = WATFORD_USERS_CASE_TYPE_ID;
        assertEquals(caseId, UtilHelper.getCaseTypeId(WATFORD_USERS_BULK_CASE_TYPE_ID));
        caseId = WATFORD_CASE_TYPE_ID;
        assertEquals(caseId, UtilHelper.getCaseTypeId(WATFORD_BULK_CASE_TYPE_ID));
    }

}