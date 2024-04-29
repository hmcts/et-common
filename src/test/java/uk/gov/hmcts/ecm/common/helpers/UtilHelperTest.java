package uk.gov.hmcts.ecm.common.helpers;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.Month;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static uk.gov.hmcts.ecm.common.model.helper.Constants.ENGLANDWALES_BULK_CASE_TYPE_ID;
import static uk.gov.hmcts.ecm.common.model.helper.Constants.ENGLANDWALES_CASE_TYPE_ID;
import static uk.gov.hmcts.ecm.common.model.helper.Constants.ENGLANDWALES_LISTING_CASE_TYPE_ID;
import static uk.gov.hmcts.ecm.common.model.helper.Constants.SCOTLAND_BULK_CASE_TYPE_ID;
import static uk.gov.hmcts.ecm.common.model.helper.Constants.SCOTLAND_CASE_TYPE_ID;
import static uk.gov.hmcts.ecm.common.model.helper.Constants.SCOTLAND_LISTING_CASE_TYPE_ID;

class UtilHelperTest {

    @Test
    void getCaseTypeId() {
        String caseId = ENGLANDWALES_CASE_TYPE_ID;
        assertEquals(caseId, UtilHelper.getCaseTypeId(ENGLANDWALES_BULK_CASE_TYPE_ID));
        caseId = SCOTLAND_CASE_TYPE_ID;
        assertEquals(caseId, UtilHelper.getCaseTypeId(SCOTLAND_BULK_CASE_TYPE_ID));
    }

    @Test
    void getListingCaseTypeId() {
        assertEquals(ENGLANDWALES_CASE_TYPE_ID,
                UtilHelper.getListingCaseTypeId(ENGLANDWALES_LISTING_CASE_TYPE_ID));
        assertEquals(SCOTLAND_CASE_TYPE_ID,
                UtilHelper.getListingCaseTypeId(SCOTLAND_LISTING_CASE_TYPE_ID));
    }

    @Test
  void getBulkCaseTypeId() {
        String caseId = SCOTLAND_BULK_CASE_TYPE_ID;
        assertEquals(caseId, UtilHelper.getBulkCaseTypeId(SCOTLAND_CASE_TYPE_ID));
        caseId = ENGLANDWALES_BULK_CASE_TYPE_ID;
        Assertions.assertEquals(caseId, UtilHelper.getBulkCaseTypeId(ENGLANDWALES_CASE_TYPE_ID));
    }

    @Test
    void getCaseTypeIdForMultiple() {
        String office = "EnglandWales";
        assertEquals(office, UtilHelper.getCaseTypeId("EnglandWales_Multiple"));
        office = "Scotland";
        assertEquals(office, UtilHelper.getCaseTypeId("Scotland_Multiple"));
    }

    @Test
    void formatLocalDate() {
        assertEquals("2 February 2020", UtilHelper.formatLocalDate("2020-02-02T11:02:11.000"));
    }

    @Test
    void listingFormatLocalDate() {
        assertEquals("2 February 2020", UtilHelper.listingFormatLocalDate("2020-02-02"));
    }

    @Test
    void formatLocalDateTime() {
        assertEquals("2 February 2020 11:02", UtilHelper.formatLocalDateTime("2020-02-02T11:02:11.000"));
    }

    @Test
    void formatLocalTime() {
        assertEquals("11:02", UtilHelper.formatLocalTime("2020-02-02T11:02:11.000"));
    }

    @Test
    void formatCurrentDatePlusDays() {
        assertEquals("6 February 2017",
                UtilHelper.formatCurrentDatePlusDays(LocalDate.of(2017, Month.FEBRUARY, 3), 3));
    }

    @Test
    void formatCurrentDate() {
        assertEquals("3 February 2017",
                UtilHelper.formatCurrentDate(LocalDate.of(2017, Month.FEBRUARY, 3)));
    }

    @Test
    void formatCurrentDate2() {
        assertEquals("2017-02-03",
                UtilHelper.formatCurrentDate2(LocalDate.of(2017, Month.FEBRUARY, 3)));
    }
}