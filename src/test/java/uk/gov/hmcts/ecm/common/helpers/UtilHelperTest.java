package uk.gov.hmcts.ecm.common.helpers;

import org.junit.Test;

import java.time.LocalDate;
import java.time.Month;

import static org.junit.Assert.assertEquals;
import static uk.gov.hmcts.ecm.common.model.helper.Constants.BRISTOL_BULK_CASE_TYPE_ID;
import static uk.gov.hmcts.ecm.common.model.helper.Constants.BRISTOL_CASE_TYPE_ID;
import static uk.gov.hmcts.ecm.common.model.helper.Constants.BRISTOL_DEV_BULK_CASE_TYPE_ID;
import static uk.gov.hmcts.ecm.common.model.helper.Constants.BRISTOL_DEV_CASE_TYPE_ID;
import static uk.gov.hmcts.ecm.common.model.helper.Constants.BRISTOL_DEV_LISTING_CASE_TYPE_ID;
import static uk.gov.hmcts.ecm.common.model.helper.Constants.BRISTOL_LISTING_CASE_TYPE_ID;
import static uk.gov.hmcts.ecm.common.model.helper.Constants.BRISTOL_USERS_BULK_CASE_TYPE_ID;
import static uk.gov.hmcts.ecm.common.model.helper.Constants.BRISTOL_USERS_CASE_TYPE_ID;
import static uk.gov.hmcts.ecm.common.model.helper.Constants.BRISTOL_USERS_LISTING_CASE_TYPE_ID;
import static uk.gov.hmcts.ecm.common.model.helper.Constants.LEEDS_BULK_CASE_TYPE_ID;
import static uk.gov.hmcts.ecm.common.model.helper.Constants.LEEDS_CASE_TYPE_ID;
import static uk.gov.hmcts.ecm.common.model.helper.Constants.LEEDS_DEV_BULK_CASE_TYPE_ID;
import static uk.gov.hmcts.ecm.common.model.helper.Constants.LEEDS_DEV_CASE_TYPE_ID;
import static uk.gov.hmcts.ecm.common.model.helper.Constants.LEEDS_DEV_LISTING_CASE_TYPE_ID;
import static uk.gov.hmcts.ecm.common.model.helper.Constants.LEEDS_LISTING_CASE_TYPE_ID;
import static uk.gov.hmcts.ecm.common.model.helper.Constants.LEEDS_USERS_BULK_CASE_TYPE_ID;
import static uk.gov.hmcts.ecm.common.model.helper.Constants.LEEDS_USERS_CASE_TYPE_ID;
import static uk.gov.hmcts.ecm.common.model.helper.Constants.LEEDS_USERS_LISTING_CASE_TYPE_ID;
import static uk.gov.hmcts.ecm.common.model.helper.Constants.LONDON_CENTRAL_BULK_CASE_TYPE_ID;
import static uk.gov.hmcts.ecm.common.model.helper.Constants.LONDON_CENTRAL_CASE_TYPE_ID;
import static uk.gov.hmcts.ecm.common.model.helper.Constants.LONDON_CENTRAL_DEV_BULK_CASE_TYPE_ID;
import static uk.gov.hmcts.ecm.common.model.helper.Constants.LONDON_CENTRAL_DEV_CASE_TYPE_ID;
import static uk.gov.hmcts.ecm.common.model.helper.Constants.LONDON_CENTRAL_DEV_LISTING_CASE_TYPE_ID;
import static uk.gov.hmcts.ecm.common.model.helper.Constants.LONDON_CENTRAL_LISTING_CASE_TYPE_ID;
import static uk.gov.hmcts.ecm.common.model.helper.Constants.LONDON_CENTRAL_USERS_BULK_CASE_TYPE_ID;
import static uk.gov.hmcts.ecm.common.model.helper.Constants.LONDON_CENTRAL_USERS_CASE_TYPE_ID;
import static uk.gov.hmcts.ecm.common.model.helper.Constants.LONDON_CENTRAL_USERS_LISTING_CASE_TYPE_ID;
import static uk.gov.hmcts.ecm.common.model.helper.Constants.LONDON_EAST_BULK_CASE_TYPE_ID;
import static uk.gov.hmcts.ecm.common.model.helper.Constants.LONDON_EAST_CASE_TYPE_ID;
import static uk.gov.hmcts.ecm.common.model.helper.Constants.LONDON_EAST_DEV_BULK_CASE_TYPE_ID;
import static uk.gov.hmcts.ecm.common.model.helper.Constants.LONDON_EAST_DEV_CASE_TYPE_ID;
import static uk.gov.hmcts.ecm.common.model.helper.Constants.LONDON_EAST_DEV_LISTING_CASE_TYPE_ID;
import static uk.gov.hmcts.ecm.common.model.helper.Constants.LONDON_EAST_LISTING_CASE_TYPE_ID;
import static uk.gov.hmcts.ecm.common.model.helper.Constants.LONDON_EAST_USERS_BULK_CASE_TYPE_ID;
import static uk.gov.hmcts.ecm.common.model.helper.Constants.LONDON_EAST_USERS_CASE_TYPE_ID;
import static uk.gov.hmcts.ecm.common.model.helper.Constants.LONDON_EAST_USERS_LISTING_CASE_TYPE_ID;
import static uk.gov.hmcts.ecm.common.model.helper.Constants.LONDON_SOUTH_BULK_CASE_TYPE_ID;
import static uk.gov.hmcts.ecm.common.model.helper.Constants.LONDON_SOUTH_CASE_TYPE_ID;
import static uk.gov.hmcts.ecm.common.model.helper.Constants.LONDON_SOUTH_DEV_BULK_CASE_TYPE_ID;
import static uk.gov.hmcts.ecm.common.model.helper.Constants.LONDON_SOUTH_DEV_CASE_TYPE_ID;
import static uk.gov.hmcts.ecm.common.model.helper.Constants.LONDON_SOUTH_DEV_LISTING_CASE_TYPE_ID;
import static uk.gov.hmcts.ecm.common.model.helper.Constants.LONDON_SOUTH_LISTING_CASE_TYPE_ID;
import static uk.gov.hmcts.ecm.common.model.helper.Constants.LONDON_SOUTH_USERS_BULK_CASE_TYPE_ID;
import static uk.gov.hmcts.ecm.common.model.helper.Constants.LONDON_SOUTH_USERS_CASE_TYPE_ID;
import static uk.gov.hmcts.ecm.common.model.helper.Constants.LONDON_SOUTH_USERS_LISTING_CASE_TYPE_ID;
import static uk.gov.hmcts.ecm.common.model.helper.Constants.MANCHESTER_BULK_CASE_TYPE_ID;
import static uk.gov.hmcts.ecm.common.model.helper.Constants.MANCHESTER_CASE_TYPE_ID;
import static uk.gov.hmcts.ecm.common.model.helper.Constants.MANCHESTER_DEV_BULK_CASE_TYPE_ID;
import static uk.gov.hmcts.ecm.common.model.helper.Constants.MANCHESTER_DEV_CASE_TYPE_ID;
import static uk.gov.hmcts.ecm.common.model.helper.Constants.MANCHESTER_DEV_LISTING_CASE_TYPE_ID;
import static uk.gov.hmcts.ecm.common.model.helper.Constants.MANCHESTER_LISTING_CASE_TYPE_ID;
import static uk.gov.hmcts.ecm.common.model.helper.Constants.MANCHESTER_USERS_BULK_CASE_TYPE_ID;
import static uk.gov.hmcts.ecm.common.model.helper.Constants.MANCHESTER_USERS_CASE_TYPE_ID;
import static uk.gov.hmcts.ecm.common.model.helper.Constants.MANCHESTER_USERS_LISTING_CASE_TYPE_ID;
import static uk.gov.hmcts.ecm.common.model.helper.Constants.MIDLANDS_EAST_BULK_CASE_TYPE_ID;
import static uk.gov.hmcts.ecm.common.model.helper.Constants.MIDLANDS_EAST_CASE_TYPE_ID;
import static uk.gov.hmcts.ecm.common.model.helper.Constants.MIDLANDS_EAST_DEV_BULK_CASE_TYPE_ID;
import static uk.gov.hmcts.ecm.common.model.helper.Constants.MIDLANDS_EAST_DEV_CASE_TYPE_ID;
import static uk.gov.hmcts.ecm.common.model.helper.Constants.MIDLANDS_EAST_DEV_LISTING_CASE_TYPE_ID;
import static uk.gov.hmcts.ecm.common.model.helper.Constants.MIDLANDS_EAST_LISTING_CASE_TYPE_ID;
import static uk.gov.hmcts.ecm.common.model.helper.Constants.MIDLANDS_EAST_USERS_BULK_CASE_TYPE_ID;
import static uk.gov.hmcts.ecm.common.model.helper.Constants.MIDLANDS_EAST_USERS_CASE_TYPE_ID;
import static uk.gov.hmcts.ecm.common.model.helper.Constants.MIDLANDS_EAST_USERS_LISTING_CASE_TYPE_ID;
import static uk.gov.hmcts.ecm.common.model.helper.Constants.MIDLANDS_WEST_BULK_CASE_TYPE_ID;
import static uk.gov.hmcts.ecm.common.model.helper.Constants.MIDLANDS_WEST_CASE_TYPE_ID;
import static uk.gov.hmcts.ecm.common.model.helper.Constants.MIDLANDS_WEST_DEV_BULK_CASE_TYPE_ID;
import static uk.gov.hmcts.ecm.common.model.helper.Constants.MIDLANDS_WEST_DEV_CASE_TYPE_ID;
import static uk.gov.hmcts.ecm.common.model.helper.Constants.MIDLANDS_WEST_DEV_LISTING_CASE_TYPE_ID;
import static uk.gov.hmcts.ecm.common.model.helper.Constants.MIDLANDS_WEST_LISTING_CASE_TYPE_ID;
import static uk.gov.hmcts.ecm.common.model.helper.Constants.MIDLANDS_WEST_USERS_BULK_CASE_TYPE_ID;
import static uk.gov.hmcts.ecm.common.model.helper.Constants.MIDLANDS_WEST_USERS_CASE_TYPE_ID;
import static uk.gov.hmcts.ecm.common.model.helper.Constants.MIDLANDS_WEST_USERS_LISTING_CASE_TYPE_ID;
import static uk.gov.hmcts.ecm.common.model.helper.Constants.NEWCASTLE_BULK_CASE_TYPE_ID;
import static uk.gov.hmcts.ecm.common.model.helper.Constants.NEWCASTLE_CASE_TYPE_ID;
import static uk.gov.hmcts.ecm.common.model.helper.Constants.NEWCASTLE_DEV_BULK_CASE_TYPE_ID;
import static uk.gov.hmcts.ecm.common.model.helper.Constants.NEWCASTLE_DEV_CASE_TYPE_ID;
import static uk.gov.hmcts.ecm.common.model.helper.Constants.NEWCASTLE_DEV_LISTING_CASE_TYPE_ID;
import static uk.gov.hmcts.ecm.common.model.helper.Constants.NEWCASTLE_LISTING_CASE_TYPE_ID;
import static uk.gov.hmcts.ecm.common.model.helper.Constants.NEWCASTLE_USERS_BULK_CASE_TYPE_ID;
import static uk.gov.hmcts.ecm.common.model.helper.Constants.NEWCASTLE_USERS_CASE_TYPE_ID;
import static uk.gov.hmcts.ecm.common.model.helper.Constants.NEWCASTLE_USERS_LISTING_CASE_TYPE_ID;
import static uk.gov.hmcts.ecm.common.model.helper.Constants.SCOTLAND_BULK_CASE_TYPE_ID;
import static uk.gov.hmcts.ecm.common.model.helper.Constants.SCOTLAND_CASE_TYPE_ID;
import static uk.gov.hmcts.ecm.common.model.helper.Constants.SCOTLAND_DEV_BULK_CASE_TYPE_ID;
import static uk.gov.hmcts.ecm.common.model.helper.Constants.SCOTLAND_DEV_CASE_TYPE_ID;
import static uk.gov.hmcts.ecm.common.model.helper.Constants.SCOTLAND_DEV_LISTING_CASE_TYPE_ID;
import static uk.gov.hmcts.ecm.common.model.helper.Constants.SCOTLAND_LISTING_CASE_TYPE_ID;
import static uk.gov.hmcts.ecm.common.model.helper.Constants.SCOTLAND_USERS_BULK_CASE_TYPE_ID;
import static uk.gov.hmcts.ecm.common.model.helper.Constants.SCOTLAND_USERS_CASE_TYPE_ID;
import static uk.gov.hmcts.ecm.common.model.helper.Constants.SCOTLAND_USERS_LISTING_CASE_TYPE_ID;
import static uk.gov.hmcts.ecm.common.model.helper.Constants.WALES_BULK_CASE_TYPE_ID;
import static uk.gov.hmcts.ecm.common.model.helper.Constants.WALES_CASE_TYPE_ID;
import static uk.gov.hmcts.ecm.common.model.helper.Constants.WALES_DEV_BULK_CASE_TYPE_ID;
import static uk.gov.hmcts.ecm.common.model.helper.Constants.WALES_DEV_CASE_TYPE_ID;
import static uk.gov.hmcts.ecm.common.model.helper.Constants.WALES_DEV_LISTING_CASE_TYPE_ID;
import static uk.gov.hmcts.ecm.common.model.helper.Constants.WALES_LISTING_CASE_TYPE_ID;
import static uk.gov.hmcts.ecm.common.model.helper.Constants.WALES_USERS_BULK_CASE_TYPE_ID;
import static uk.gov.hmcts.ecm.common.model.helper.Constants.WALES_USERS_CASE_TYPE_ID;
import static uk.gov.hmcts.ecm.common.model.helper.Constants.WALES_USERS_LISTING_CASE_TYPE_ID;
import static uk.gov.hmcts.ecm.common.model.helper.Constants.WATFORD_BULK_CASE_TYPE_ID;
import static uk.gov.hmcts.ecm.common.model.helper.Constants.WATFORD_CASE_TYPE_ID;
import static uk.gov.hmcts.ecm.common.model.helper.Constants.WATFORD_DEV_BULK_CASE_TYPE_ID;
import static uk.gov.hmcts.ecm.common.model.helper.Constants.WATFORD_DEV_CASE_TYPE_ID;
import static uk.gov.hmcts.ecm.common.model.helper.Constants.WATFORD_DEV_LISTING_CASE_TYPE_ID;
import static uk.gov.hmcts.ecm.common.model.helper.Constants.WATFORD_LISTING_CASE_TYPE_ID;
import static uk.gov.hmcts.ecm.common.model.helper.Constants.WATFORD_USERS_BULK_CASE_TYPE_ID;
import static uk.gov.hmcts.ecm.common.model.helper.Constants.WATFORD_USERS_CASE_TYPE_ID;
import static uk.gov.hmcts.ecm.common.model.helper.Constants.WATFORD_USERS_LISTING_CASE_TYPE_ID;

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
        caseId = SCOTLAND_CASE_TYPE_ID;
        assertEquals(caseId, UtilHelper.getCaseTypeId(SCOTLAND_BULK_CASE_TYPE_ID));
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

    @Test
    public void getListingCaseTypeId() {
        assertEquals(MANCHESTER_DEV_CASE_TYPE_ID, UtilHelper.getListingCaseTypeId(MANCHESTER_DEV_LISTING_CASE_TYPE_ID));
        assertEquals(MANCHESTER_USERS_CASE_TYPE_ID,
                UtilHelper.getListingCaseTypeId(MANCHESTER_USERS_LISTING_CASE_TYPE_ID));
        assertEquals(MANCHESTER_CASE_TYPE_ID, UtilHelper.getListingCaseTypeId(MANCHESTER_LISTING_CASE_TYPE_ID));
        assertEquals(LEEDS_DEV_CASE_TYPE_ID, UtilHelper.getListingCaseTypeId(LEEDS_DEV_LISTING_CASE_TYPE_ID));
        assertEquals(LEEDS_USERS_CASE_TYPE_ID, UtilHelper.getListingCaseTypeId(LEEDS_USERS_LISTING_CASE_TYPE_ID));
        assertEquals(LEEDS_CASE_TYPE_ID, UtilHelper.getListingCaseTypeId(LEEDS_LISTING_CASE_TYPE_ID));
        assertEquals(SCOTLAND_DEV_CASE_TYPE_ID, UtilHelper.getListingCaseTypeId(SCOTLAND_DEV_LISTING_CASE_TYPE_ID));
        assertEquals(SCOTLAND_USERS_CASE_TYPE_ID, UtilHelper.getListingCaseTypeId(SCOTLAND_USERS_LISTING_CASE_TYPE_ID));
        assertEquals(SCOTLAND_CASE_TYPE_ID, UtilHelper.getListingCaseTypeId(SCOTLAND_LISTING_CASE_TYPE_ID));
        assertEquals(BRISTOL_DEV_CASE_TYPE_ID, UtilHelper.getListingCaseTypeId(BRISTOL_DEV_LISTING_CASE_TYPE_ID));
        assertEquals(BRISTOL_USERS_CASE_TYPE_ID, UtilHelper.getListingCaseTypeId(BRISTOL_USERS_LISTING_CASE_TYPE_ID));
        assertEquals(BRISTOL_CASE_TYPE_ID, UtilHelper.getListingCaseTypeId(BRISTOL_LISTING_CASE_TYPE_ID));
        assertEquals(LONDON_CENTRAL_DEV_CASE_TYPE_ID,
                UtilHelper.getListingCaseTypeId(LONDON_CENTRAL_DEV_LISTING_CASE_TYPE_ID));
        assertEquals(LONDON_CENTRAL_USERS_CASE_TYPE_ID,
                UtilHelper.getListingCaseTypeId(LONDON_CENTRAL_USERS_LISTING_CASE_TYPE_ID));
        assertEquals(LONDON_CENTRAL_CASE_TYPE_ID, UtilHelper.getListingCaseTypeId(LONDON_CENTRAL_LISTING_CASE_TYPE_ID));
        assertEquals(LONDON_EAST_DEV_CASE_TYPE_ID,
                UtilHelper.getListingCaseTypeId(LONDON_EAST_DEV_LISTING_CASE_TYPE_ID));
        assertEquals(LONDON_EAST_USERS_CASE_TYPE_ID,
                UtilHelper.getListingCaseTypeId(LONDON_EAST_USERS_LISTING_CASE_TYPE_ID));
        assertEquals(LONDON_EAST_CASE_TYPE_ID, UtilHelper.getListingCaseTypeId(LONDON_EAST_LISTING_CASE_TYPE_ID));
        assertEquals(LONDON_SOUTH_DEV_CASE_TYPE_ID,
                UtilHelper.getListingCaseTypeId(LONDON_SOUTH_DEV_LISTING_CASE_TYPE_ID));
        assertEquals(LONDON_SOUTH_USERS_CASE_TYPE_ID,
                UtilHelper.getListingCaseTypeId(LONDON_SOUTH_USERS_LISTING_CASE_TYPE_ID));
        assertEquals(LONDON_SOUTH_CASE_TYPE_ID, UtilHelper.getListingCaseTypeId(LONDON_SOUTH_LISTING_CASE_TYPE_ID));
        assertEquals(MIDLANDS_EAST_DEV_CASE_TYPE_ID,
                UtilHelper.getListingCaseTypeId(MIDLANDS_EAST_DEV_LISTING_CASE_TYPE_ID));
        assertEquals(MIDLANDS_EAST_USERS_CASE_TYPE_ID,
                UtilHelper.getListingCaseTypeId(MIDLANDS_EAST_USERS_LISTING_CASE_TYPE_ID));
        assertEquals(MIDLANDS_EAST_CASE_TYPE_ID, UtilHelper.getListingCaseTypeId(MIDLANDS_EAST_LISTING_CASE_TYPE_ID));
        assertEquals(MIDLANDS_WEST_DEV_CASE_TYPE_ID,
                UtilHelper.getListingCaseTypeId(MIDLANDS_WEST_DEV_LISTING_CASE_TYPE_ID));
        assertEquals(MIDLANDS_WEST_USERS_CASE_TYPE_ID,
                UtilHelper.getListingCaseTypeId(MIDLANDS_WEST_USERS_LISTING_CASE_TYPE_ID));
        assertEquals(MIDLANDS_WEST_CASE_TYPE_ID, UtilHelper.getListingCaseTypeId(MIDLANDS_WEST_LISTING_CASE_TYPE_ID));
        assertEquals(NEWCASTLE_DEV_CASE_TYPE_ID, UtilHelper.getListingCaseTypeId(NEWCASTLE_DEV_LISTING_CASE_TYPE_ID));
        assertEquals(NEWCASTLE_USERS_CASE_TYPE_ID,
                UtilHelper.getListingCaseTypeId(NEWCASTLE_USERS_LISTING_CASE_TYPE_ID));
        assertEquals(NEWCASTLE_CASE_TYPE_ID, UtilHelper.getListingCaseTypeId(NEWCASTLE_LISTING_CASE_TYPE_ID));
        assertEquals(WALES_DEV_CASE_TYPE_ID, UtilHelper.getListingCaseTypeId(WALES_DEV_LISTING_CASE_TYPE_ID));
        assertEquals(WALES_USERS_CASE_TYPE_ID, UtilHelper.getListingCaseTypeId(WALES_USERS_LISTING_CASE_TYPE_ID));
        assertEquals(WALES_CASE_TYPE_ID, UtilHelper.getListingCaseTypeId(WALES_LISTING_CASE_TYPE_ID));
        assertEquals(WATFORD_DEV_CASE_TYPE_ID, UtilHelper.getListingCaseTypeId(WATFORD_DEV_LISTING_CASE_TYPE_ID));
        assertEquals(WATFORD_USERS_CASE_TYPE_ID, UtilHelper.getListingCaseTypeId(WATFORD_USERS_LISTING_CASE_TYPE_ID));
        assertEquals(WATFORD_CASE_TYPE_ID, UtilHelper.getListingCaseTypeId(WATFORD_LISTING_CASE_TYPE_ID));
    }

    @Test
    public void getBulkCaseTypeId() {
        String caseId = MANCHESTER_DEV_BULK_CASE_TYPE_ID;
        assertEquals(caseId, UtilHelper.getBulkCaseTypeId(MANCHESTER_DEV_CASE_TYPE_ID));
        caseId = MANCHESTER_USERS_BULK_CASE_TYPE_ID;
        assertEquals(caseId, UtilHelper.getBulkCaseTypeId(MANCHESTER_USERS_CASE_TYPE_ID));
        caseId = SCOTLAND_DEV_BULK_CASE_TYPE_ID;
        assertEquals(caseId, UtilHelper.getBulkCaseTypeId(SCOTLAND_DEV_CASE_TYPE_ID));
        caseId = SCOTLAND_USERS_BULK_CASE_TYPE_ID;
        assertEquals(caseId, UtilHelper.getBulkCaseTypeId(SCOTLAND_USERS_CASE_TYPE_ID));
        caseId = SCOTLAND_BULK_CASE_TYPE_ID;
        assertEquals(caseId, UtilHelper.getBulkCaseTypeId(SCOTLAND_CASE_TYPE_ID));
        caseId = MANCHESTER_BULK_CASE_TYPE_ID;
        assertEquals(caseId, UtilHelper.getBulkCaseTypeId(MANCHESTER_CASE_TYPE_ID));
        caseId = BRISTOL_DEV_BULK_CASE_TYPE_ID;
        assertEquals(caseId, UtilHelper.getBulkCaseTypeId(BRISTOL_DEV_CASE_TYPE_ID));
        caseId = BRISTOL_USERS_BULK_CASE_TYPE_ID;
        assertEquals(caseId, UtilHelper.getBulkCaseTypeId(BRISTOL_USERS_CASE_TYPE_ID));
        caseId = BRISTOL_BULK_CASE_TYPE_ID;
        assertEquals(caseId, UtilHelper.getBulkCaseTypeId(BRISTOL_CASE_TYPE_ID));
        caseId = LEEDS_DEV_BULK_CASE_TYPE_ID;
        assertEquals(caseId, UtilHelper.getBulkCaseTypeId(LEEDS_DEV_CASE_TYPE_ID));
        caseId = LEEDS_USERS_BULK_CASE_TYPE_ID;
        assertEquals(caseId, UtilHelper.getBulkCaseTypeId(LEEDS_USERS_CASE_TYPE_ID));
        caseId = LEEDS_BULK_CASE_TYPE_ID;
        assertEquals(caseId, UtilHelper.getBulkCaseTypeId(LEEDS_CASE_TYPE_ID));
        caseId = LONDON_CENTRAL_DEV_BULK_CASE_TYPE_ID;
        assertEquals(caseId, UtilHelper.getBulkCaseTypeId(LONDON_CENTRAL_DEV_CASE_TYPE_ID));
        caseId = LONDON_CENTRAL_USERS_BULK_CASE_TYPE_ID;
        assertEquals(caseId, UtilHelper.getBulkCaseTypeId(LONDON_CENTRAL_USERS_CASE_TYPE_ID));
        caseId = LONDON_CENTRAL_BULK_CASE_TYPE_ID;
        assertEquals(caseId, UtilHelper.getBulkCaseTypeId(LONDON_CENTRAL_CASE_TYPE_ID));
        caseId = LONDON_EAST_DEV_BULK_CASE_TYPE_ID;
        assertEquals(caseId, UtilHelper.getBulkCaseTypeId(LONDON_EAST_DEV_CASE_TYPE_ID));
        caseId = LONDON_EAST_USERS_BULK_CASE_TYPE_ID;
        assertEquals(caseId, UtilHelper.getBulkCaseTypeId(LONDON_EAST_USERS_CASE_TYPE_ID));
        caseId = LONDON_EAST_BULK_CASE_TYPE_ID;
        assertEquals(caseId, UtilHelper.getBulkCaseTypeId(LONDON_EAST_CASE_TYPE_ID));
        caseId = LONDON_SOUTH_DEV_BULK_CASE_TYPE_ID;
        assertEquals(caseId, UtilHelper.getBulkCaseTypeId(LONDON_SOUTH_DEV_CASE_TYPE_ID));
        caseId = LONDON_SOUTH_USERS_BULK_CASE_TYPE_ID;
        assertEquals(caseId, UtilHelper.getBulkCaseTypeId(LONDON_SOUTH_USERS_CASE_TYPE_ID));
        caseId = LONDON_SOUTH_BULK_CASE_TYPE_ID;
        assertEquals(caseId, UtilHelper.getBulkCaseTypeId(LONDON_SOUTH_CASE_TYPE_ID));
        caseId = MIDLANDS_EAST_DEV_BULK_CASE_TYPE_ID;
        assertEquals(caseId, UtilHelper.getBulkCaseTypeId(MIDLANDS_EAST_DEV_CASE_TYPE_ID));
        caseId = MIDLANDS_EAST_USERS_BULK_CASE_TYPE_ID;
        assertEquals(caseId, UtilHelper.getBulkCaseTypeId(MIDLANDS_EAST_USERS_CASE_TYPE_ID));
        caseId = MIDLANDS_EAST_BULK_CASE_TYPE_ID;
        assertEquals(caseId, UtilHelper.getBulkCaseTypeId(MIDLANDS_EAST_CASE_TYPE_ID));
        caseId = MIDLANDS_WEST_DEV_BULK_CASE_TYPE_ID;
        assertEquals(caseId, UtilHelper.getBulkCaseTypeId(MIDLANDS_WEST_DEV_CASE_TYPE_ID));
        caseId = MIDLANDS_WEST_USERS_BULK_CASE_TYPE_ID;
        assertEquals(caseId, UtilHelper.getBulkCaseTypeId(MIDLANDS_WEST_USERS_CASE_TYPE_ID));
        caseId = MIDLANDS_WEST_BULK_CASE_TYPE_ID;
        assertEquals(caseId, UtilHelper.getBulkCaseTypeId(MIDLANDS_WEST_CASE_TYPE_ID));
        caseId = NEWCASTLE_DEV_BULK_CASE_TYPE_ID;
        assertEquals(caseId, UtilHelper.getBulkCaseTypeId(NEWCASTLE_DEV_CASE_TYPE_ID));
        caseId = NEWCASTLE_USERS_BULK_CASE_TYPE_ID;
        assertEquals(caseId, UtilHelper.getBulkCaseTypeId(NEWCASTLE_USERS_CASE_TYPE_ID));
        caseId = NEWCASTLE_BULK_CASE_TYPE_ID;
        assertEquals(caseId, UtilHelper.getBulkCaseTypeId(NEWCASTLE_CASE_TYPE_ID));
        caseId = WALES_DEV_BULK_CASE_TYPE_ID;
        assertEquals(caseId, UtilHelper.getBulkCaseTypeId(WALES_DEV_CASE_TYPE_ID));
        caseId = WALES_USERS_BULK_CASE_TYPE_ID;
        assertEquals(caseId, UtilHelper.getBulkCaseTypeId(WALES_USERS_CASE_TYPE_ID));
        caseId = WALES_BULK_CASE_TYPE_ID;
        assertEquals(caseId, UtilHelper.getBulkCaseTypeId(WALES_CASE_TYPE_ID));
        caseId = WATFORD_DEV_BULK_CASE_TYPE_ID;
        assertEquals(caseId, UtilHelper.getBulkCaseTypeId(WATFORD_DEV_CASE_TYPE_ID));
        caseId = WATFORD_USERS_BULK_CASE_TYPE_ID;
        assertEquals(caseId, UtilHelper.getBulkCaseTypeId(WATFORD_USERS_CASE_TYPE_ID));
        caseId = WATFORD_BULK_CASE_TYPE_ID;
        assertEquals(caseId, UtilHelper.getBulkCaseTypeId(WATFORD_CASE_TYPE_ID));
    }

    @Test
    public void getCaseTypeIdForMultiple() {
        String office = "Manchester";
        assertEquals(office, UtilHelper.getCaseTypeId("Manchester_Multiple"));
        office = "Scotland";
        assertEquals(office, UtilHelper.getCaseTypeId("Scotland_Multiple"));
        office = "Bristol";
        assertEquals(office, UtilHelper.getCaseTypeId("Bristol_Multiple"));
        office = "Leeds";
        assertEquals(office, UtilHelper.getCaseTypeId("Leeds_Multiple"));
        office = "LondonCentral";
        assertEquals(office, UtilHelper.getCaseTypeId("LondonCentral_Multiple"));
        office = "LondonEast";
        assertEquals(office, UtilHelper.getCaseTypeId("LondonEast_Multiple"));
        office = "LondonSouth";
        assertEquals(office, UtilHelper.getCaseTypeId("LondonSouth_Multiple"));
        office = "MidlandsEast";
        assertEquals(office, UtilHelper.getCaseTypeId("MidlandsEast_Multiple"));
        office = "MidlandsWest";
        assertEquals(office, UtilHelper.getCaseTypeId("MidlandsWest_Multiple"));
        office = "Newcastle";
        assertEquals(office, UtilHelper.getCaseTypeId("Newcastle_Multiple"));
        office = "Wales";
        assertEquals(office, UtilHelper.getCaseTypeId("Wales_Multiple"));
        office = "Watford";
        assertEquals(office, UtilHelper.getCaseTypeId("Watford_Multiple"));
    }

    @Test
    public void getCaseTypeIdForMultipleUser() {
        String office = "Manchester_User";
        assertEquals(office, UtilHelper.getCaseTypeId("Manchester_Multiple_User"));
        office = "Scotland_User";
        assertEquals(office, UtilHelper.getCaseTypeId("Scotland_Multiple_User"));
        office = "Bristol_User";
        assertEquals(office, UtilHelper.getCaseTypeId("Bristol_Multiple_User"));
        office = "Leeds_User";
        assertEquals(office, UtilHelper.getCaseTypeId("Leeds_Multiple_User"));
        office = "LondonCentral_User";
        assertEquals(office, UtilHelper.getCaseTypeId("LondonCentral_Multiple_User"));
    }

    @Test
    public void getCaseTypeIdForMultipleDev() {
        String office = "Manchester_Dev";
        assertEquals(office, UtilHelper.getCaseTypeId("Manchester_Multiple_Dev"));
        office = "Scotland_Dev";
        assertEquals(office, UtilHelper.getCaseTypeId("Scotland_Multiple_Dev"));
        office = "Bristol_Dev";
        assertEquals(office, UtilHelper.getCaseTypeId("Bristol_Multiple_Dev"));
        office = "Leeds_Dev";
        assertEquals(office, UtilHelper.getCaseTypeId("Leeds_Multiple_Dev"));
        office = "LondonCentral_Dev";
        assertEquals(office, UtilHelper.getCaseTypeId("LondonCentral_Multiple_Dev"));
    }

    @Test
    public void formatLocalDate() {
        assertEquals("2 February 2020", UtilHelper.formatLocalDate("2020-02-02T11:02:11.000"));
    }

    @Test
    public void listingFormatLocalDate() {
        assertEquals("2 February 2020", UtilHelper.listingFormatLocalDate("2020-02-02"));
    }

    @Test
    public void formatLocalDateTime() {
        assertEquals("2 February 2020 11:02", UtilHelper.formatLocalDateTime("2020-02-02T11:02:11.000"));
    }

    @Test
    public void formatLocalTime() {
        assertEquals("11:02", UtilHelper.formatLocalTime("2020-02-02T11:02:11.000"));
    }

    @Test
    public void formatCurrentDatePlusDays() {
        assertEquals("6 February 2017",
                UtilHelper.formatCurrentDatePlusDays(LocalDate.of(2017, Month.FEBRUARY, 3), 3));
    }

    @Test
    public void formatCurrentDate() {
        assertEquals("3 February 2017",
                UtilHelper.formatCurrentDate(LocalDate.of(2017, Month.FEBRUARY, 3)));
    }

    @Test
    public void formatCurrentDate2() {
        assertEquals("2017-02-03",
                UtilHelper.formatCurrentDate2(LocalDate.of(2017,Month.FEBRUARY, 3)));
    }
}