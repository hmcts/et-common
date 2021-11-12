package uk.gov.hmcts.ecm.common.model.helper;

import org.junit.Test;

import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static uk.gov.hmcts.ecm.common.model.helper.Constants.ENGLANDWALES_CASE_TYPE_ID;
import static uk.gov.hmcts.ecm.common.model.helper.Constants.SCOTLAND_CASE_TYPE_ID;

public class TribunalOfficeTest {

    @Test
    public void testValueOfOfficeNumber() {
        assertEquals(Optional.empty(), TribunalOffice.valueOfOfficeNumber(null));
        assertEquals(Optional.empty(), TribunalOffice.valueOfOfficeNumber(""));
        assertEquals(Optional.empty(), TribunalOffice.valueOfOfficeNumber("0"));
        assertEquals(Optional.of(TribunalOffice.BRISTOL), TribunalOffice.valueOfOfficeNumber("14"));
        assertEquals(Optional.of(TribunalOffice.GLASGOW), TribunalOffice.valueOfOfficeNumber("41"));
        assertEquals(Optional.of(TribunalOffice.LEEDS), TribunalOffice.valueOfOfficeNumber("18"));
        assertEquals(Optional.of(TribunalOffice.LONDON_CENTRAL), TribunalOffice.valueOfOfficeNumber("22"));
        assertEquals(Optional.of(TribunalOffice.LONDON_EAST), TribunalOffice.valueOfOfficeNumber("32"));
        assertEquals(Optional.of(TribunalOffice.LONDON_SOUTH), TribunalOffice.valueOfOfficeNumber("23"));
        assertEquals(Optional.of(TribunalOffice.MANCHESTER), TribunalOffice.valueOfOfficeNumber("24"));
        assertEquals(Optional.of(TribunalOffice.MIDLANDS_EAST), TribunalOffice.valueOfOfficeNumber("26"));
        assertEquals(Optional.of(TribunalOffice.MIDLANDS_WEST), TribunalOffice.valueOfOfficeNumber("13"));
        assertEquals(Optional.of(TribunalOffice.NEWCASTLE), TribunalOffice.valueOfOfficeNumber("25"));
        assertEquals(Optional.of(TribunalOffice.WALES), TribunalOffice.valueOfOfficeNumber("16"));
        assertEquals(Optional.of(TribunalOffice.WATFORD), TribunalOffice.valueOfOfficeNumber("33"));
    }

    @Test
    public void testValueOfOfficeName() {
        assertEquals(TribunalOffice.ABERDEEN, TribunalOffice.valueOfOfficeName("Aberdeen"));
        assertEquals(TribunalOffice.BRISTOL, TribunalOffice.valueOfOfficeName("Bristol"));
        assertEquals(TribunalOffice.DUNDEE, TribunalOffice.valueOfOfficeName("Dundee"));
        assertEquals(TribunalOffice.EDINBURGH, TribunalOffice.valueOfOfficeName("Edinburgh"));
        assertEquals(TribunalOffice.GLASGOW, TribunalOffice.valueOfOfficeName("Glasgow"));
        assertEquals(TribunalOffice.LEEDS, TribunalOffice.valueOfOfficeName("Leeds"));
        assertEquals(TribunalOffice.LONDON_CENTRAL, TribunalOffice.valueOfOfficeName("London Central"));
        assertEquals(TribunalOffice.LONDON_EAST, TribunalOffice.valueOfOfficeName("London East"));
        assertEquals(TribunalOffice.LONDON_SOUTH, TribunalOffice.valueOfOfficeName("London South"));
        assertEquals(TribunalOffice.MANCHESTER, TribunalOffice.valueOfOfficeName("Manchester"));
        assertEquals(TribunalOffice.MIDLANDS_EAST, TribunalOffice.valueOfOfficeName("Midlands East"));
        assertEquals(TribunalOffice.MIDLANDS_WEST, TribunalOffice.valueOfOfficeName("Midlands West"));
        assertEquals(TribunalOffice.NEWCASTLE, TribunalOffice.valueOfOfficeName("Newcastle"));
        assertEquals(TribunalOffice.WALES, TribunalOffice.valueOfOfficeName("Wales"));
        assertEquals(TribunalOffice.WATFORD, TribunalOffice.valueOfOfficeName("Watford"));
    }

    @Test(expected = IllegalArgumentException.class)
    public void testValueOfOfficeNameNotFound() {
        TribunalOffice.valueOfOfficeName("invalid-office-name");
    }

    @Test
    public void testGetCaseTypeId() {
        assertEquals(SCOTLAND_CASE_TYPE_ID, TribunalOffice.getCaseTypeId("Aberdeen"));
        assertEquals(ENGLANDWALES_CASE_TYPE_ID, TribunalOffice.getCaseTypeId("Bristol"));
        assertEquals(SCOTLAND_CASE_TYPE_ID, TribunalOffice.getCaseTypeId("Dundee"));
        assertEquals(SCOTLAND_CASE_TYPE_ID, TribunalOffice.getCaseTypeId("Edinburgh"));
        assertEquals(SCOTLAND_CASE_TYPE_ID, TribunalOffice.getCaseTypeId("Glasgow"));
        assertEquals(ENGLANDWALES_CASE_TYPE_ID, TribunalOffice.getCaseTypeId("London East"));
        assertEquals(ENGLANDWALES_CASE_TYPE_ID, TribunalOffice.getCaseTypeId("London Central"));
        assertEquals(ENGLANDWALES_CASE_TYPE_ID, TribunalOffice.getCaseTypeId("London South"));
        assertEquals(ENGLANDWALES_CASE_TYPE_ID, TribunalOffice.getCaseTypeId("Manchester"));
        assertEquals(ENGLANDWALES_CASE_TYPE_ID, TribunalOffice.getCaseTypeId("Midlands East"));
        assertEquals(ENGLANDWALES_CASE_TYPE_ID, TribunalOffice.getCaseTypeId("Midlands West"));
        assertEquals(ENGLANDWALES_CASE_TYPE_ID, TribunalOffice.getCaseTypeId("Newcastle"));
        assertEquals(ENGLANDWALES_CASE_TYPE_ID, TribunalOffice.getCaseTypeId("Wales"));
        assertEquals(ENGLANDWALES_CASE_TYPE_ID, TribunalOffice.getCaseTypeId("Watford"));
    }

    @Test(expected = IllegalArgumentException.class)
    public void testGetCaseTypeIdNotFound() {
        TribunalOffice.getCaseTypeId("invalid-office-name");
    }
}