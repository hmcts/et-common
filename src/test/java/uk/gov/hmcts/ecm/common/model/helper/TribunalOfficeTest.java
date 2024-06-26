package uk.gov.hmcts.ecm.common.model.helper;

import junitparams.JUnitParamsRunner;
import junitparams.converters.Nullable;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.junit.runner.RunWith;

import java.util.Optional;
import java.util.stream.Stream;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static uk.gov.hmcts.ecm.common.model.helper.Constants.ENGLANDWALES_CASE_TYPE_ID;
import static uk.gov.hmcts.ecm.common.model.helper.Constants.SCOTLAND_CASE_TYPE_ID;

@RunWith(JUnitParamsRunner.class)
class TribunalOfficeTest {

    @ParameterizedTest
    @MethodSource("parametersForTestValueOfOfficeNumber")
    void testValueOfOfficeNumber(Optional<TribunalOffice> expected, String officeNumber) {
        assertEquals(expected, TribunalOffice.valueOfOfficeNumber(officeNumber));
    }

    private static Object[] parametersForTestValueOfOfficeNumber() {
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

    @ParameterizedTest
    @MethodSource("parametersForTestValueOfOfficeName")
    void testValueOfOfficeName(TribunalOffice expected, String officeName) {
        assertEquals(expected, TribunalOffice.valueOfOfficeName(officeName));
    }

    private static Object[] parametersForTestValueOfOfficeName() {
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

    @Test()
    void testValueOfOfficeNameNotFound() {
        assertThrows(IllegalArgumentException.class, () -> {
            TribunalOffice.valueOfOfficeName("invalid-office-name");
            ;
        });
    }

    @ParameterizedTest
    @MethodSource("parametersForTestGetCaseTypeId")
    void testGetCaseTypeId(String expectedCaseTypeId, String officeName) {
        assertEquals(expectedCaseTypeId, TribunalOffice.getCaseTypeId(officeName));
    }

    private static Object[] parametersForTestGetCaseTypeId() {
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

    @Test()
    void testGetCaseTypeIdNotFound() {
        assertThrows(IllegalArgumentException.class, () -> {
            TribunalOffice.getCaseTypeId("invalid-office-name");
        });
    }

    @ParameterizedTest
    @MethodSource("parametersFoTestIsEnglandWalesOffice")
    void testIsEnglandWalesOffice(String officeName, boolean expected) {
        assertEquals(expected, TribunalOffice.isEnglandWalesOffice(officeName));
    }

    private static Stream<Arguments> parametersFoTestIsEnglandWalesOffice() {
        return Stream.of(
                Arguments.of("Aberdeen", false),
                Arguments.of("Bristol", true),
                Arguments.of("Dundee", false),
                Arguments.of("Edinburgh", false),
                Arguments.of("Glasgow", false),
                Arguments.of("London Central", true),
                Arguments.of("London East", true),
                Arguments.of("London South", true),
                Arguments.of("Manchester", true),
                Arguments.of("Midlands East", true),
                Arguments.of("Midlands West", true),
                Arguments.of("Newcastle", true),
                Arguments.of("Wales", true),
                Arguments.of("Watford", true));
    }

    @ParameterizedTest
    @MethodSource("parametersForTestIsScotlandOffice")
    void testIsScotlandOffice(String officeName, boolean expected) {
        assertEquals(expected, TribunalOffice.isScotlandOffice(officeName));
    }

    private static Stream<Arguments> parametersForTestIsScotlandOffice() {
        return Stream.of(
                Arguments.of("Aberdeen", true),
                Arguments.of("Bristol", false),
                Arguments.of("Dundee", true),
                Arguments.of("Edinburgh", true),
                Arguments.of("Glasgow", true),
                Arguments.of("London Central", false),
                Arguments.of("London East", false),
                Arguments.of("London South", false),
                Arguments.of("Manchester", false),
                Arguments.of("Midlands East", false),
                Arguments.of("Midlands West", false),
                Arguments.of("Newcastle", false),
                Arguments.of("Wales", false),
                Arguments.of("Watford", false));
    }

    @ParameterizedTest
    @ValueSource(strings = {"null", "", " ", "invalid"})
    void testIsEnglandWalesOfficeThrowsException(@Nullable String officeName) {
        assertThrows(IllegalArgumentException.class, () -> {
            TribunalOffice.isEnglandWalesOffice(officeName);
            fail(officeName + " should throw an IllegalArgumentException");
        });
    }

    @ParameterizedTest()
    @ValueSource(strings = {"null", "", " ", "invalid"})
    void testIsScotlandOfficeThrowsException(@Nullable String officeName) {
        assertThrows(IllegalArgumentException.class, () -> {
            TribunalOffice.isScotlandOffice(officeName);
            fail(officeName + " should throw an IllegalArgumentException");
        });
    }

    @ParameterizedTest
    @MethodSource("parametersForTestGetOfficeForReferenceData")
    void testGetOfficeForReferenceData(TribunalOffice tribunalOffice, TribunalOffice expected) {
        assertEquals(expected, TribunalOffice.getOfficeForReferenceData(tribunalOffice));
    }

    private static Object[] parametersForTestGetOfficeForReferenceData() {
        return new Object[]{
            new Object[]{TribunalOffice.ABERDEEN, TribunalOffice.SCOTLAND},
            new Object[]{TribunalOffice.BRISTOL, TribunalOffice.BRISTOL},
            new Object[]{TribunalOffice.DUNDEE, TribunalOffice.SCOTLAND},
            new Object[]{TribunalOffice.EDINBURGH, TribunalOffice.SCOTLAND},
            new Object[]{TribunalOffice.GLASGOW, TribunalOffice.SCOTLAND},
            new Object[]{TribunalOffice.LEEDS, TribunalOffice.LEEDS},
            new Object[]{TribunalOffice.LONDON_CENTRAL, TribunalOffice.LONDON_CENTRAL},
            new Object[]{TribunalOffice.LONDON_EAST, TribunalOffice.LONDON_EAST},
            new Object[]{TribunalOffice.LONDON_SOUTH, TribunalOffice.LONDON_SOUTH},
            new Object[]{TribunalOffice.MANCHESTER, TribunalOffice.MANCHESTER},
            new Object[]{TribunalOffice.MIDLANDS_EAST, TribunalOffice.MIDLANDS_EAST},
            new Object[]{TribunalOffice.MIDLANDS_WEST, TribunalOffice.MIDLANDS_WEST},
            new Object[]{TribunalOffice.NEWCASTLE, TribunalOffice.NEWCASTLE},
            new Object[]{TribunalOffice.SCOTLAND, TribunalOffice.SCOTLAND},
            new Object[]{TribunalOffice.WALES, TribunalOffice.WALES},
            new Object[]{TribunalOffice.WATFORD, TribunalOffice.WATFORD}
        };
    }
}