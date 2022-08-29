package uk.gov.hmcts.et.common.helpers;

import org.junit.Assert;
import org.junit.Test;

import java.time.LocalDate;
import java.time.Month;

import static org.junit.Assert.assertEquals;
import uk.gov.hmcts.et.common.model.helper.Constants;

public class UtilHelperTest {

    @Test
    public void getCaseTypeId() {
        String caseId = Constants.ENGLANDWALES_CASE_TYPE_ID;
        assertEquals(caseId, UtilHelper.getCaseTypeId(Constants.ENGLANDWALES_BULK_CASE_TYPE_ID));
        caseId = Constants.SCOTLAND_CASE_TYPE_ID;
        assertEquals(caseId, UtilHelper.getCaseTypeId(Constants.SCOTLAND_BULK_CASE_TYPE_ID));
    }

    @Test
    public void getListingCaseTypeId() {
        Assert.assertEquals(Constants.ENGLANDWALES_CASE_TYPE_ID, UtilHelper.getListingCaseTypeId(Constants.ENGLANDWALES_LISTING_CASE_TYPE_ID));
        Assert.assertEquals(Constants.SCOTLAND_CASE_TYPE_ID, UtilHelper.getListingCaseTypeId(Constants.SCOTLAND_LISTING_CASE_TYPE_ID));
    }

    @Test
    public void getBulkCaseTypeId() {
        String caseId = Constants.SCOTLAND_BULK_CASE_TYPE_ID;
        assertEquals(caseId, UtilHelper.getBulkCaseTypeId(Constants.SCOTLAND_CASE_TYPE_ID));
        caseId = Constants.ENGLANDWALES_BULK_CASE_TYPE_ID;
        assertEquals(caseId, UtilHelper.getBulkCaseTypeId(Constants.ENGLANDWALES_CASE_TYPE_ID));
    }

    @Test
    public void getCaseTypeIdForMultiple() {
        String office = "EnglandWales";
        assertEquals(office, UtilHelper.getCaseTypeId("EnglandWales_Multiple"));
        office = "Scotland";
        assertEquals(office, UtilHelper.getCaseTypeId("Scotland_Multiple"));
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
                UtilHelper.formatCurrentDate2(LocalDate.of(2017, Month.FEBRUARY, 3)));
    }
}