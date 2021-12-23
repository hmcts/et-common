package uk.gov.hmcts.ecm.common.model.helper;


import junitparams.JUnitParamsRunner;
import junitparams.Parameters;
import junitparams.converters.Nullable;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;
import static uk.gov.hmcts.ecm.common.model.helper.Constants.ENGLANDWALES_CASE_TYPE_ID;
import static uk.gov.hmcts.ecm.common.model.helper.Constants.SCOTLAND_CASE_TYPE_ID;

@RunWith(JUnitParamsRunner.class)
public class TribunalOfficeTest {

    @Test
    @Parameters
    public void testValueOfOfficeNumber(Optional<TribunalOffice> expected, String officeNumber) {
        assertEquals(expected, TribunalOffice.valueOfOfficeNumber(officeNumber));
    }

    private Object[] parametersForTestValueOfOfficeNumber() {
        return new Object[]{
                new Object[]{Optional.empty(), null},
                new Object[]{Optional.empty(), ""},
                new Object[]{Optional.empty(), "0"},
                new Object[]{Optional.of(TribunalOffice.BRISTOL), "14"},
                new Object[]{Optional.of(TribunalOffice.GLASGOW), "41"},
                new Object[]{Optional.of(TribunalOffice.LEEDS), "18"},
                new Object[]{Optional.of(TribunalOffice.LONDON_CENTRAL), "22"},
                new Object[]{Optional.of(TribunalOffice.LONDON_EAST), "32"},
                new Object[]{Optional.of(TribunalOffice.LONDON_SOUTH), "23"},
                new Object[]{Optional.of(TribunalOffice.MANCHESTER), "24"},
                new Object[]{Optional.of(TribunalOffice.MIDLANDS_EAST), "26"},
                new Object[]{Optional.of(TribunalOffice.MIDLANDS_WEST), "13"},
                new Object[]{Optional.of(TribunalOffice.NEWCASTLE), "25"},
                new Object[]{Optional.of(TribunalOffice.WALES), "16"},
                new Object[]{Optional.of(TribunalOffice.WATFORD), "33"}
        };
    }

    @Test
    @Parameters
    public void testValueOfOfficeName(TribunalOffice expected, String officeName) {
        assertEquals(expected, TribunalOffice.valueOfOfficeName(officeName));
    }

    private Object[] parametersForTestValueOfOfficeName() {
        return new Object[]{
                new Object[]{TribunalOffice.ABERDEEN, "Aberdeen"},
                new Object[]{TribunalOffice.BRISTOL, "Bristol"},
                new Object[]{TribunalOffice.DUNDEE, "Dundee"},
                new Object[]{TribunalOffice.EDINBURGH, "Edinburgh"},
                new Object[]{TribunalOffice.GLASGOW, "Glasgow"},
                new Object[]{TribunalOffice.LEEDS, "Leeds"},
                new Object[]{TribunalOffice.LONDON_CENTRAL, "London Central"},
                new Object[]{TribunalOffice.LONDON_EAST, "London East"},
                new Object[]{TribunalOffice.LONDON_SOUTH, "London South"},
                new Object[]{TribunalOffice.MANCHESTER, "Manchester"},
                new Object[]{TribunalOffice.MIDLANDS_EAST, "Midlands East"},
                new Object[]{TribunalOffice.MIDLANDS_WEST, "Midlands West"},
                new Object[]{TribunalOffice.NEWCASTLE, "Newcastle"},
                new Object[]{TribunalOffice.WALES, "Wales"},
                new Object[]{TribunalOffice.WATFORD, "Watford"}
        };
    }

    @Test(expected = IllegalArgumentException.class)
    public void testValueOfOfficeNameNotFound() {
        TribunalOffice.valueOfOfficeName("invalid-office-name");
    }

    @Test
    @Parameters
    public void testGetCaseTypeId(String expectedCaseTypeId, String officeName) {
        assertEquals(expectedCaseTypeId, TribunalOffice.getCaseTypeId(officeName));
    }

    private Object[] parametersForTestGetCaseTypeId() {
        return new Object[]{
                new Object[]{SCOTLAND_CASE_TYPE_ID, "Aberdeen"},
                new Object[]{ENGLANDWALES_CASE_TYPE_ID, "Bristol"},
                new Object[]{SCOTLAND_CASE_TYPE_ID, "Dundee"},
                new Object[]{SCOTLAND_CASE_TYPE_ID, "Edinburgh"},
                new Object[]{SCOTLAND_CASE_TYPE_ID, "Glasgow"},
                new Object[]{ENGLANDWALES_CASE_TYPE_ID, "London East"},
                new Object[]{ENGLANDWALES_CASE_TYPE_ID, "London Central"},
                new Object[]{ENGLANDWALES_CASE_TYPE_ID, "London South"},
                new Object[]{ENGLANDWALES_CASE_TYPE_ID, "Manchester"},
                new Object[]{ENGLANDWALES_CASE_TYPE_ID, "Midlands East"},
                new Object[]{ENGLANDWALES_CASE_TYPE_ID, "Midlands West"},
                new Object[]{ENGLANDWALES_CASE_TYPE_ID, "Newcastle"},
                new Object[]{ENGLANDWALES_CASE_TYPE_ID, "Wales"},
                new Object[]{ENGLANDWALES_CASE_TYPE_ID, "Watford"}
        };
    }

    @Test(expected = IllegalArgumentException.class)
    public void testGetCaseTypeIdNotFound() {
        TribunalOffice.getCaseTypeId("invalid-office-name");
    }

    @Test
    @Parameters({
            "Aberdeen, false",
            "Bristol, true",
            "Dundee, false",
            "Edinburgh, false",
            "Glasgow, false",
            "London Central, true",
            "London East, true",
            "London South, true",
            "Manchester, true",
            "Midlands East, true",
            "Midlands West, true",
            "Newcastle, true",
            "Wales, true",
            "Watford, true"
    })
    public void testIsEnglandWalesOffice(String officeName, boolean expected) {
        assertEquals(expected, TribunalOffice.isEnglandWalesOffice(officeName));
    }

    @Test
    @Parameters({
            "Aberdeen, true",
            "Bristol, false",
            "Dundee, true",
            "Edinburgh, true",
            "Glasgow, true",
            "London Central, false",
            "London East, false",
            "London South, false",
            "Manchester, false",
            "Midlands East, false",
            "Midlands West, false",
            "Newcastle, false",
            "Wales, false",
            "Watford, false"
    })
    public void testIsScotlandOffice(String officeName, boolean expected) {
        assertEquals(expected, TribunalOffice.isScotlandOffice(officeName));
    }

    @Test(expected = IllegalArgumentException.class)
    @Parameters({
            "null",
            "",
            " ",
            "invalid"
    })
    public void testIsEnglandWalesOfficeThrowsException(@Nullable String officeName) {
        TribunalOffice.isEnglandWalesOffice(officeName);
        fail(officeName + " should throw an IllegalArgumentException");
    }

    @Test(expected = IllegalArgumentException.class)
    @Parameters({
            "null",
            "",
            " ",
            "invalid"
    })
    public void testIsScotlandOfficeThrowsException(@Nullable String officeName) {
        TribunalOffice.isScotlandOffice(officeName);
        fail(officeName + " should throw an IllegalArgumentException");
    }
}