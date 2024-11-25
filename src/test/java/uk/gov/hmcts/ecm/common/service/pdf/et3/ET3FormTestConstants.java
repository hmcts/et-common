package uk.gov.hmcts.ecm.common.service.pdf.et3;

public final class ET3FormTestConstants {

    // GENERIC CONSTANTS
    public static final String TEST_ET3_FORM_CASE_DATA_FILE = "requests/et3FormCaseData.json";
    public static final String TEST_FIELD_NAME = "testField";
    public static final String TEST_DUMMY_VALUE = "dummy value";
    public static final String TEST_EXPECTED_VALUE = "expectedValue";
    public static final String TEST_ACTUAL_VALUE = "actualValue";
    public static final String TEST_EQUAL_VALUE = "equalValue";
    public static final String TEST_CHECK_VALUE = "checkValue";
    public static final String TEST_ADDRESS_LINE_1 = "addressLine1";
    public static final String TEST_ADDRESS_LINE_2 = "addressLine2";
    public static final String TEST_ADDRESS_LINE_3 = "addressLine3";
    public static final String TEST_ADDRESS_POST_TOWN = "postTown";
    public static final String TEST_ADDRESS_COUNTY = "county";
    public static final String TEST_ADDRESS_COUNTRY = "country";

    // ET3FormHeaderMapperTest CONSTANTS
    public static final String TEST_PDF_HEADER_VALUE_CASE_NUMBER = "6009729/2024";

    // ET3FormClaimantMapperTest CONSTANTS
    public static final String TEST_PDF_CLAIMANT_VALUE_ET1_CLAIMANT_NAME = "et citizen1";
    public static final String TEST_PDF_CLAIMANT_VALUE_ET3_CLAIMANT_NAME = "Mehmet Tahir Dede";

    //ET3FormRespondentMapperTest CONSTANTS
    public static final String TEST_CHECKBOX_PDF_RESPONDENT_EXPECTED_VALUE_TITLE_OTHER = "Other Title";
    public static final String TEST_PDF_RESPONDENT_EXPECTED_VALUE_ADDRESS = """
            50 Tithe Barn Drive
            Maidenhead
            United Kingdom""";
    public static final String TEST_PDF_RESPONDENT_EXPECTED_VALUE_CONTACT_NAME = "Will Respondent";
    public static final String TEST_PDF_RESPONDENT_EXPECTED_VALUE_DX = "Test DX Address";
    public static final String TEST_PDF_RESPONDENT_EXPECTED_VALUE_EMAIL = "michael@jackson.com";
    public static final String TEST_PDF_RESPONDENT_EXPECTED_VALUE_EMPLOYEE_NUMBER_CLAIMANT_WORK_PLACE = "10";
    public static final String TEST_PDF_RESPONDENT_EXPECTED_VALUE_EMPLOYEE_NUMBER_GREAT_BRITAIN = "15";
    public static final String TEST_PDF_RESPONDENT_EXPECTED_VALUE_HEARING_TYPE_PHONE = "Yes";
    public static final String TEST_PDF_RESPONDENT_EXPECTED_VALUE_HEARING_TYPE_VIDEO = "Yes";
    public static final String TEST_PDF_RESPONDENT_EXPECTED_VALUE_MOBILE_NUMBER = "07444518907";
    public static final String TEST_PDF_RESPONDENT_EXPECTED_VALUE_MORE_THAN_ONE_SITE_GREAT_BRITAIN_YES = "yes";
    public static final String TEST_PDF_RESPONDENT_EXPECTED_VALUE_NAME = "Test Company";
    public static final String TEST_PDF_RESPONDENT_EXPECTED_VALUE_NUMBER = "123456";
    public static final String TEST_PDF_RESPONDENT_EXPECTED_VALUE_PHONE_NUMBER = "07444518906";
    public static final String TEST_PDF_RESPONDENT_EXPECTED_VALUE_POSTCODE = "SL6 2DE";
    public static final String TEST_PDF_RESPONDENT_EXPECTED_VALUE_TYPE = "Limited company";

    //ET3FormAcasMapperTest CONSTANTS
    public static final String TEST_PDF_ACAS_EXPECTED_NOT_AGREE_REASON = "Test acas not agreed reason";

    //ET3FormEmploymentMapperTest CONSTANTS
    public static final String TEST_PDF_EMPLOYMENT_START_DAY = "24";
    public static final String TEST_PDF_EMPLOYMENT_START_MONTH = "02";
    public static final String TEST_PDF_EMPLOYMENT_START_YEAR = "2020";
    public static final String TEST_PDF_EMPLOYMENT_END_DAY = "28";
    public static final String TEST_PDF_EMPLOYMENT_END_MONTH = "12";
    public static final String TEST_PDF_EMPLOYMENT_END_YEAR = "2021";
    public static final String TEST_PDF_EMPLOYMENT_DATE_INFORMATION = "Claimant gave wrong dates";
    public static final String TEST_PDF_EMPLOYMENT_CORRECT_JOB_TITLE = "IT Guy";

    //ET3FormEarningsBenefitsMapperTest CONSTANTS
    public static final String TEST_PDF_EARNINGS_BENEFITS_CORRECT_HOURS = "36";
    public static final String TEST_PDF_EARNINGS_BENEFITS_PAY_BEFORE_TAX = "35000.00";
    public static final String TEST_PDF_EARNINGS_BENEFITS_PAY_TAKE_HOME = "30000.00";
    public static final String TEST_PDF_EARNINGS_BENEFITS_CORRECT_NOTICE_PERIOD =
            "His notice period was only for 3 weeks";
    public static final String TEST_PDF_EARNINGS_BENEFITS_CORRECT_PENSION_AND_OTHER_BENEFITS =
            "His pension was 300 and he didn't have a car.";

    //ET3FormResponseMapperTest CONSTANTS
    public static final String TEST_PDF_RESPONSE_CONTEST_CLAIM_CORRECT_FACTS = "I don't agree with the claimant.";

    //ET3FormEmployerContractClaimMapperTest CONSTANTS
    public static final String TEST_PDF_RESPONSE_EMPLOYER_CONTRACT_CLAIM_CORRECT_DETAILS =
            "I want to make employer's contract claim.";

    //ET3FormRepresentativeMapperTest CONSTANTS
    public static final String TEST_PDF_REPRESENTATIVE_NAME = "EeT TesterOne";
    public static final String TEST_PDF_REPRESENTATIVE_ORGANISATION_NAME = "ET Test Factory Solicitor";
    public static final String TEST_PDF_REPRESENTATIVE_ADDRESS = """
        10, GROVELANDS AVENUE
        SWINDON""";
    public static final String TEST_PDF_REPRESENTATIVE_POSTCODE = "SN1 4ET";
    public static final String TEST_PDF_REPRESENTATIVE_PHONE_NUMBER = "07444518910";
    public static final String TEST_PDF_REPRESENTATIVE_MOBILE_PHONE_NUMBER = "07444518911";
    public static final String TEST_PDF_REPRESENTATIVE_REFERENCE_FOR_CORRESPONDENCE = "Reference for Correspondence";
    public static final String TEST_PDF_REPRESENTATIVE_EMAIL_ADDRESS = "et.legalrep.1@gmail.com";

    // ET3FormDisabilityMapperTest
    public static final String TEST_PDF_DISABILITY_DETAIL = "He needs some food. Because he is always hungry.";
    public static final String TEST_PDF_CASE_DATA_NOT_FOUND_EXCEPTION_MESSAGE = "Case Data is null or empty";
    public static final String TEST_PDF_RESPONDENT_COLLECTION_NOT_FOUND_EXCEPTION_MESSAGE =
            "Respondent Collection is empty";
    public static final String TEST_PDF_RESPONDENT_NOT_FOUND_IN_CASE_DATA_EXCEPTION_MESSAGE =
            "ET3 Form submitting respondent not found in case data";
    public static final String TEST_PDF_RESPONDENT_NAME_NOT_FOUND_IN_CASE_DATA_EXCEPTION_MESSAGE =
            "ET3 Form submitting respondent name is empty or blank";
    public static final String TEST_PDF_RESPONDENT_NOT_FOUND_IN_RESPONDENT_COLLECTION_EXCEPTION_MESSAGE =
            "ET3 Respondent not found, most probably label in the "
                    + "case data(caseData.getSubmitEt3Respondent().getSelectedLabel()) "
                    + "does not match with any name in the respondent collection";

    private ET3FormTestConstants() {
        // Add a private constructor to hide the implicit public one.
    }
}
