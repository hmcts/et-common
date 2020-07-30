package uk.gov.hmcts.ecm.common.helpers;

import org.junit.Test;

import java.time.LocalDate;
import java.time.Month;

import static org.junit.Assert.assertEquals;
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
        assertEquals("6 February 2017", UtilHelper.formatCurrentDatePlusDays(LocalDate.of(2017, Month.FEBRUARY,3), 3));
    }

    @Test
    public void formatCurrentDate() {
        assertEquals("3 February 2017", UtilHelper.formatCurrentDate(LocalDate.of(2017, Month.FEBRUARY,3)));
    }

    @Test
    public void formatCurrentDate2() {
        assertEquals("2017-02-03", UtilHelper.formatCurrentDate2(LocalDate.of(2017,Month.FEBRUARY,3)));
    }
}