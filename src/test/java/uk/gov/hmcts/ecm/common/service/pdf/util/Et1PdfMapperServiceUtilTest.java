package uk.gov.hmcts.ecm.common.service.pdf.util;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import uk.gov.hmcts.ecm.common.model.CaseTestData;
import uk.gov.hmcts.ecm.common.service.pdf.et1.PdfMapperServiceUtil;
import uk.gov.hmcts.ecm.common.service.utils.TestConstants;
import uk.gov.hmcts.ecm.common.service.utils.data.TestDataProvider;
import uk.gov.hmcts.et.common.model.ccd.Address;
import uk.gov.hmcts.et.common.model.ccd.CaseData;

import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static uk.gov.hmcts.ecm.common.service.pdf.et1.PdfMapperServiceUtil.generateClaimantCompensation;
import static uk.gov.hmcts.ecm.common.service.pdf.et1.PdfMapperServiceUtil.generateClaimantTribunalRecommendation;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
@SuppressWarnings({"PMD.TooManyMethods"})
class Et1PdfMapperServiceUtilTest {

    private static final String ADDRESS_LINE1 = "CO-OPERATIVE RETAIL SERVICES LTD, 11, MERRION WAY";
    private static final String ADDRESS_LINE2 = "SAMPLE ADDRESS LINE 2";
    private static final String ADDRESS_LINE3 = "SAMPLE ADDRESS LINE 3";
    private static final String POSTCODE = "LS2 8BT";
    private static final String ENGLAND = "ENGLAND";
    private static final String LEEDS = "LEEDS";

    @Test
    void theFormatAddressForTextFieldWhenAddressLine1PostTownPostCodeCountryExists() {
        // Given
        Address address = new CaseTestData().getCaseData().getClaimantType().getClaimantAddressUK();

        String expectedAddressString = """
            Co-operative Retail Services Ltd, 11, Merrion Way,
            Leeds,
            England""";
        // When
        String actualAddressString = PdfMapperServiceUtil.formatAddressForTextField(address);
        // Then
        assertThat(actualAddressString).isEqualTo(expectedAddressString);
    }

    @Test
    void theFormatAddressForTextFieldWhenAllFieldsExist() {
        // Given
        Address address = new CaseTestData().getCaseData().getClaimantType().getClaimantAddressUK();
        address.setAddressLine1(ADDRESS_LINE1);
        address.setAddressLine2(ADDRESS_LINE2);
        address.setAddressLine3(ADDRESS_LINE3);
        address.setPostCode(POSTCODE);
        address.setPostTown(LEEDS);
        address.setCountry(ENGLAND);
        String expectedAddressString = """
                        Co-operative Retail Services Ltd, 11, Merrion Way,
                        Sample Address Line 2,
                        Sample Address Line 3,
                        Leeds,
                        England""";
        // When
        String actualAddressString = PdfMapperServiceUtil.formatAddressForTextField(address);
        // Then
        assertThat(actualAddressString).isEqualTo(expectedAddressString);
    }

    @Test
    void theFormatAddressForTextFieldWhenAllFieldsAreEmpty() {
        // Given
        Address address = new CaseTestData().getCaseData().getClaimantType().getClaimantAddressUK();
        address.setAddressLine1(null);
        address.setAddressLine2(null);
        address.setAddressLine3(null);
        address.setPostCode(null);
        address.setPostTown(null);
        address.setCountry(null);
        // When
        String actualAddressString = PdfMapperServiceUtil.formatAddressForTextField(address);
        // Then
        assertDoesNotThrow(() -> PdfMapperServiceUtil.formatAddressForTextField(address));
        assertThat(actualAddressString).isNull();
    }

    @Test
    void theFormatAddressForTextFieldWhenAddressLine1IsEmpty() {
        // Given
        Address address = new CaseTestData().getCaseData().getClaimantType().getClaimantAddressUK();
        address.setAddressLine1("");
        address.setAddressLine2(ADDRESS_LINE2);
        address.setAddressLine3(ADDRESS_LINE3);
        address.setPostTown(LEEDS);
        address.setCountry(ENGLAND);
        String expectedAddressString = """
                       Sample Address Line 2,
                       Sample Address Line 3,
                       Leeds,
                       England""";
        // When
        String actualAddressString = PdfMapperServiceUtil.formatAddressForTextField(address);
        // Then
        assertDoesNotThrow(() -> PdfMapperServiceUtil.formatAddressForTextField(address));
        assertThat(actualAddressString).isEqualTo(expectedAddressString);
    }

    @Test
    void theFormatAddressForTextFieldWhenPostTownIsEmpty() {
        // Given
        Address address = new CaseTestData().getCaseData().getClaimantType().getClaimantAddressUK();
        address.setAddressLine1(ADDRESS_LINE1);
        address.setAddressLine2(ADDRESS_LINE2);
        address.setAddressLine3(ADDRESS_LINE3);
        address.setPostTown("");
        address.setCountry(ENGLAND);
        String expectedAddressString = """
                       Co-operative Retail Services Ltd, 11, Merrion Way,
                       Sample Address Line 2,
                       Sample Address Line 3,
                       England""";
        // When
        String actualAddressString = PdfMapperServiceUtil.formatAddressForTextField(address);
        // Then
        assertDoesNotThrow(() -> PdfMapperServiceUtil.formatAddressForTextField(address));
        assertThat(actualAddressString).isEqualTo(expectedAddressString);
    }

    @Test
    void theFormatAddressForTextFieldWhenCountryIsEmpty() {
        // Given
        Address address = new CaseTestData().getCaseData().getClaimantType().getClaimantAddressUK();
        address.setAddressLine1(ADDRESS_LINE1);
        address.setAddressLine2(ADDRESS_LINE2);
        address.setAddressLine3(ADDRESS_LINE3);
        address.setPostCode(POSTCODE);
        address.setPostTown(LEEDS);
        address.setCountry("");
        String expectedAddressString = """
                       Co-operative Retail Services Ltd, 11, Merrion Way,
                       Sample Address Line 2,
                       Sample Address Line 3,
                       Leeds""";
        // When
        String actualAddressString = PdfMapperServiceUtil.formatAddressForTextField(address);
        // Then
        assertDoesNotThrow(() -> PdfMapperServiceUtil.formatAddressForTextField(address));
        assertThat(actualAddressString).isEqualTo(expectedAddressString);
    }

    @Test
    void theFormatAddressForTextFieldReturnValueNotIncludesPostcode() {
        // Given
        Address address = new CaseTestData().getCaseData().getClaimantType().getClaimantAddressUK();
        address.setAddressLine1(ADDRESS_LINE1);
        address.setAddressLine2(ADDRESS_LINE2);
        address.setAddressLine3(ADDRESS_LINE3);
        address.setPostCode(POSTCODE);
        address.setCountry(ENGLAND);
        address.setPostTown(LEEDS);
        // When
        String actualAddressString = PdfMapperServiceUtil.formatAddressForTextField(address);
        // Then
        assertThat(actualAddressString).isNotEmpty().doesNotContain(address.getPostCode());
    }

    @Test
    void formatUkPostcodeNull() {
        Address address = new CaseTestData().getCaseData().getClaimantType().getClaimantAddressUK();
        address.setPostCode(null);
        assertThat(PdfMapperServiceUtil.formatPostcode(address)).isBlank();
    }

    @ParameterizedTest
    @MethodSource("postcodeAddressArguments")
    void formatUkPostcode(String expectedPostCode, Address address) {
        assertThat(PdfMapperServiceUtil.formatPostcode(address)).isEqualTo(expectedPostCode);
    }

    private static Stream<Arguments> postcodeAddressArguments() {
        return Stream.of(
                Arguments.of("A1 1AA",
                        TestDataProvider.generateTestAddressByPostcodeCountry("A1      1AA",
                                TestConstants.NULL_STRING)),
                Arguments.of("A2 2AA",
                        TestDataProvider.generateTestAddressByPostcodeCountry("A22AA",
                                TestConstants.NULL_STRING)),
                Arguments.of("A3 3AA",
                        TestDataProvider.generateTestAddressByPostcodeCountry("A 3  3 A  A",
                                TestConstants.NULL_STRING)),
                Arguments.of("NG4 4JF",
                        TestDataProvider.generateTestAddressByPostcodeCountry("NG44JF",
                                TestConstants.NULL_STRING)),
                Arguments.of("NG5 5JF",
                        TestDataProvider.generateTestAddressByPostcodeCountry("NG5      5JF",
                                TestConstants.NULL_STRING)),
                Arguments.of("NG6 6JF",
                        TestDataProvider.generateTestAddressByPostcodeCountry("N  G 6      6  J F",
                                TestConstants.NULL_STRING)),
                Arguments.of("HU10 7NA",
                        TestDataProvider.generateTestAddressByPostcodeCountry("HU107NA",
                                TestConstants.NULL_STRING)),
                Arguments.of("HU10 8NA",
                        TestDataProvider.generateTestAddressByPostcodeCountry("HU10      8NA",
                                TestConstants.NULL_STRING)),
                Arguments.of("HU10 9NA",
                        TestDataProvider.generateTestAddressByPostcodeCountry("H U 1 0 9 N A",
                                TestConstants.NULL_STRING)),
                Arguments.of("34730",
                        TestDataProvider.generateTestAddressByPostcodeCountry("34730",
                                "Turkey")),
                Arguments.of("CF119HA",
                        TestDataProvider.generateTestAddressByPostcodeCountry("C F 1 1  9 H A",
                                "Turkey")),
                Arguments.of("CF11 9HA",
                        TestDataProvider.generateTestAddressByPostcodeCountry("C F 1 1  9 H A",
                                "wales")),
                Arguments.of("AB11 1AB",
                        TestDataProvider.generateTestAddressByPostcodeCountry("AB111AB",
                                "United kingdom")),
                Arguments.of("AB12 1AB",
                        TestDataProvider.generateTestAddressByPostcodeCountry("AB121AB",
                                TestConstants.NULL_STRING)),
                Arguments.of("AB131AB",
                        TestDataProvider.generateTestAddressByPostcodeCountry("AB13 1AB",
                                TestConstants.EMPTY_STRING)),
                Arguments.of(TestConstants.EMPTY_STRING,
                        TestDataProvider.generateTestAddressByPostcodeCountry(TestConstants.EMPTY_STRING,
                                TestConstants.NULL_STRING))
        );
    }

    @ParameterizedTest
    @CsvSource({"1979-08-05,05-08-1979", "null,null", "\"\", \"\"", ",''"})
    void formatDate(String inputDate, String expectedDate) {
        assertThat(PdfMapperServiceUtil.formatDate(inputDate)).isEqualTo(expectedDate);
    }

    @ParameterizedTest
    @CsvSource({"yes,true", "Yes,true", "YEs,true", "YES,true", "yES,true", "yeS,true", "yEs,true",
        "No,false", "test,false"})
    void isYes(String inputDate, boolean expected) {
        assertThat(PdfMapperServiceUtil.isYes(inputDate)).isEqualTo(expected);
    }

    @ParameterizedTest
    @MethodSource("compensationArguments")
    void theGenerateClaimantCompensation(CaseData caseData, String expectedValue) {
        assertThat(generateClaimantCompensation(caseData)).isEqualTo(expectedValue);
    }

    public static Stream<Arguments> compensationArguments() {

        CaseData caseData1 = TestDataProvider.generateTestCaseDataByClaimantCompensation("Test Compensation",
                "",
                "");
        CaseData caseData2 = TestDataProvider.generateTestCaseDataByClaimantCompensation("Test Compensation",
                "2000",
                "");
        CaseData caseData3 = TestDataProvider.generateTestCaseDataByClaimantCompensation(null,
                "2000",
                "");
        CaseData caseData4 = TestDataProvider.generateTestCaseDataByClaimantCompensation("",
                "",
                ":");
        CaseData caseData5 = new CaseTestData().getCaseData();
        caseData5.setClaimantRequests(null);

        return Stream.of(
                Arguments.of(null, ""),
                Arguments.of(caseData1, "Compensation:\"Test Compensation\"" + System.lineSeparator()
                                        + System.lineSeparator()),
                Arguments.of(caseData2, "Compensation:\"Test Compensation\nAmount requested: £2000\""
                                        + System.lineSeparator() + System.lineSeparator()),
                Arguments.of(caseData3, "Compensation:\"Amount requested: £2000\"" + System.lineSeparator()
                                        + System.lineSeparator()),
                Arguments.of(caseData4, ""),
                Arguments.of(caseData5, "")
        );

    }

    @Test
    void theGenerateClaimantTribunalRecommendationCaseDataNull() {
        assertThat(generateClaimantTribunalRecommendation(null)).isEmpty();
    }

    @Test
    void theGenerateClaimantClaimantRequestsNull() {
        CaseData caseData = new CaseTestData().getCaseData();
        caseData.setClaimantRequests(null);
        assertThat(generateClaimantTribunalRecommendation(caseData)).isEmpty();
    }

    @Test
    void theGenerateClaimantClaimantTribunalRecommendationNull() {
        CaseData caseData = new CaseTestData().getCaseData();
        caseData.getClaimantRequests().setClaimantTribunalRecommendation(null);
        assertThat(generateClaimantTribunalRecommendation(caseData)).isEmpty();
    }

}
