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
public class TribunalLocationTest {

    @Test
    @Parameters
    public void testValueOfOfficeNumber(Optional<TribunalLocation> expected, String epimmsId) {
        assertEquals(expected, TribunalLocation.valueOfEpimmsId(epimmsId));
    }

    private Object[] parametersForTestValueOfOfficeNumber() {
        return new Object[]{
            new Object[]{Optional.empty(), null},
            new Object[]{Optional.empty(), ""},
            new Object[]{Optional.empty(), "0"},
            new Object[]{Optional.of(TribunalLocation.BRISTOL), "14"},
            new Object[]{Optional.of(TribunalLocation.GLASGOW), "41"},
            new Object[]{Optional.of(TribunalLocation.LEEDS), "18"},
            new Object[]{Optional.of(TribunalLocation.LONDON_CENTRAL), "22"},
            new Object[]{Optional.of(TribunalLocation.LONDON_EAST), "32"},
            new Object[]{Optional.of(TribunalLocation.LONDON_SOUTH), "23"},
            new Object[]{Optional.of(TribunalLocation.MANCHESTER), "24"},
            new Object[]{Optional.of(TribunalLocation.MIDLANDS_EAST), "26"},
            new Object[]{Optional.of(TribunalLocation.MIDLANDS_WEST), "13"},
            new Object[]{Optional.of(TribunalLocation.NEWCASTLE), "25"},
            new Object[]{Optional.of(TribunalLocation.WALES), "16"},
            new Object[]{Optional.of(TribunalLocation.WATFORD), "33"}
        };
    }

    @Test
    @Parameters
    public void testValueOfOfficeName(TribunalLocation expected, String officeName) {
        assertEquals(expected, TribunalLocation.valueOfOfficeName(officeName));
    }

    private Object[] parametersForTestValueOfOfficeName() {
        return new Object[]{
            new Object[]{TribunalLocation.ABERDEEN, "Aberdeen"},
            new Object[]{TribunalLocation.BRISTOL, "Bristol"},
            new Object[]{TribunalLocation.DUNDEE, "Dundee"},
            new Object[]{TribunalLocation.EDINBURGH, "Edinburgh"},
            new Object[]{TribunalLocation.GLASGOW, "Glasgow"},
            new Object[]{TribunalLocation.LEEDS, "Leeds"},
            new Object[]{TribunalLocation.LONDON_CENTRAL, "London Central"},
            new Object[]{TribunalLocation.LONDON_EAST, "London East"},
            new Object[]{TribunalLocation.LONDON_SOUTH, "London South"},
            new Object[]{TribunalLocation.MANCHESTER, "Manchester"},
            new Object[]{TribunalLocation.MIDLANDS_EAST, "Midlands East"},
            new Object[]{TribunalLocation.MIDLANDS_WEST, "Midlands West"},
            new Object[]{TribunalLocation.NEWCASTLE, "Newcastle"},
            new Object[]{TribunalLocation.WALES, "Wales"},
            new Object[]{TribunalLocation.WATFORD, "Watford"}
        };
    }

    @Test(expected = IllegalArgumentException.class)
    public void testValueOfOfficeNameNotFound() {
        TribunalLocation.valueOfOfficeName("invalid-office-name");
    }

    @Test
    @Parameters
    public void testGetCaseTypeId(String expectedCaseTypeId, String officeName) {
        assertEquals(expectedCaseTypeId, TribunalLocation.getCaseTypeId(officeName));
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
        TribunalLocation.getCaseTypeId("invalid-office-name");
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
        assertEquals(expected, TribunalLocation.isEnglandWalesOffice(officeName));
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
        assertEquals(expected, TribunalLocation.isScotlandOffice(officeName));
    }

    @Test(expected = IllegalArgumentException.class)
    @Parameters({
            "null",
            "",
            " ",
            "invalid"
    })
    public void testIsEnglandWalesOfficeThrowsException(@Nullable String officeName) {
        TribunalLocation.isEnglandWalesOffice(officeName);
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
        TribunalLocation.isScotlandOffice(officeName);
        fail(officeName + " should throw an IllegalArgumentException");
    }

    @Test
    @Parameters
    public void testGetOfficeForReferenceData(TribunalLocation tribunalOffice, TribunalLocation expected) {
        assertEquals(expected, TribunalLocation.getOfficeForReferenceData(tribunalOffice));
    }

    private Object[] parametersForTestGetOfficeForReferenceData() {
        return new Object[]{
            new Object[]{TribunalLocation.ABERDEEN, TribunalLocation.SCOTLAND},
            new Object[]{TribunalLocation.BRISTOL, TribunalLocation.BRISTOL},
            new Object[]{TribunalLocation.DUNDEE, TribunalLocation.SCOTLAND},
            new Object[]{TribunalLocation.EDINBURGH, TribunalLocation.SCOTLAND},
            new Object[]{TribunalLocation.GLASGOW, TribunalLocation.SCOTLAND},
            new Object[]{TribunalLocation.LEEDS, TribunalLocation.LEEDS},
            new Object[]{TribunalLocation.LONDON_CENTRAL, TribunalLocation.LONDON_CENTRAL},
            new Object[]{TribunalLocation.LONDON_EAST, TribunalLocation.LONDON_EAST},
            new Object[]{TribunalLocation.LONDON_SOUTH, TribunalLocation.LONDON_SOUTH},
            new Object[]{TribunalLocation.MANCHESTER, TribunalLocation.MANCHESTER},
            new Object[]{TribunalLocation.MIDLANDS_EAST, TribunalLocation.MIDLANDS_EAST},
            new Object[]{TribunalLocation.MIDLANDS_WEST, TribunalLocation.MIDLANDS_WEST},
            new Object[]{TribunalLocation.NEWCASTLE, TribunalLocation.NEWCASTLE},
            new Object[]{TribunalLocation.SCOTLAND, TribunalLocation.SCOTLAND},
            new Object[]{TribunalLocation.WALES, TribunalLocation.WALES},
            new Object[]{TribunalLocation.WATFORD, TribunalLocation.WATFORD}
        };
    }
}