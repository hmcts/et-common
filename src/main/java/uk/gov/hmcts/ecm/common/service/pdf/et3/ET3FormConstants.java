package uk.gov.hmcts.ecm.common.service.pdf.et3;

import org.apache.commons.lang3.StringUtils;

/**
 *  Defines form fields and other form constants.
 */
public final class ET3FormConstants {

    // GENERIC CONSTANTS
    public static final String STRING_EMPTY = StringUtils.EMPTY;
    public static final String STRING_SPACE = StringUtils.SPACE;
    public static final String STRING_COMMA_WITH_SPACE = ", ";
    public static final String STRING_LINE_FEED = StringUtils.LF;
    public static final String YES_LOWERCASE = "yes";
    public static final String YES_CAPITALISED = "Yes";
    public static final String NO_LOWERCASE = "no";
    public static final String NO_CAPITALISED = "No";
    public static final String OFF_CAPITALISED = "Off";
    public static final String EMAIL_LOWERCASE = "email";
    public static final String POST_LOWERCASE = "post";
    public static final String VIDEO_HEARINGS = "Video hearings";
    public static final String PHONE_HEARINGS = "Phone hearings";
    public static final String EMAIL_CAPITALISED = "Email";
    public static final String POST_CAPITALISED = "Post";
    public static final String DATE_FORMAT_YYYY_MM_DD_DASH = "yyyy-MM-dd";
    public static final String DATE_FORMAT_DD_MM_YYYY_DASH = "dd-MM-yyyy";
    public static final String DATE_FORMAT_DD = "dd";
    public static final String DATE_FORMAT_MM = "MM";
    public static final String DATE_FORMAT_YYYY = "YYYY";
    public static final String WEEKLY_LOWERCASE = "weekly";
    public static final String WEEKLY_CAPITALISED = "Weekly";
    public static final String MONTHLY_LOWERCASE = "monthly";
    public static final String MONTHLY_CAPITALISED = "Monthly";
    // Implemented wrong in the PDF file that is why ANNUALLY_LOWERCASE value is monthly
    public static final String ANNUALLY_LOWERCASE = "monthly";
    public static final String ANNUALLY_CAPITALISED = "Annually";

    // HEADER CONSTANTS
    public static final String TXT_PDF_HEADER_FIELD_CASE_NUMBER = "case number";
    public static final String TXT_PDF_HEADER_FIELD_DATE_RECEIVED = "date_received";
    public static final String TXT_PDF_HEADER_FIELD_RTF = "RTF";
    public static final String TXT_PDF_HEADER_VALUE_ADDITIONAL_DOCUMENT_EXISTS = "+%d Doc";

    // SECTION 1 CLAIMANT CONSTANTS
    public static final String TXT_PDF_CLAIMANT_FIELD_NAME = "1.1 Claimant's name";

    // SECTION 2 RESPONDENT CONSTANTS
    public static final String CHECKBOX_PDF_RESPONDENT_EXPECTED_VALUE_TITLE_MISS = "Miss";
    public static final String CHECKBOX_PDF_RESPONDENT_EXPECTED_VALUE_TITLE_MR = "Mr";
    public static final String CHECKBOX_PDF_RESPONDENT_EXPECTED_VALUE_TITLE_MRS = "Mrs";
    public static final String CHECKBOX_PDF_RESPONDENT_EXPECTED_VALUE_TITLE_MS = "Ms";
    public static final String CHECKBOX_PDF_RESPONDENT_FIELD_CONTACT_TYPE_EMAIL = "2.5 Email";
    public static final String CHECKBOX_PDF_RESPONDENT_FIELD_CONTACT_TYPE_POST = "2.5 Post";
    public static final String CHECKBOX_PDF_RESPONDENT_FIELD_HEARING_TYPE_PHONE = "2.10 phone";
    public static final String CHECKBOX_PDF_RESPONDENT_FIELD_HEARING_TYPE_VIDEO = "2.10";
    public static final String CHECKBOX_PDF_RESPONDENT_FIELD_MORE_THAN_ONE_SITE_GREAT_BRITAIN_NO =
            "2.9 more than one site - no";
    public static final String CHECKBOX_PDF_RESPONDENT_FIELD_MORE_THAN_ONE_SITE_GREAT_BRITAIN_YES =
            "2.9 more than one site - yes";
    public static final String CHECKBOX_PDF_RESPONDENT_FIELD_TITLE_MISS = "2.1 Miss";
    public static final String CHECKBOX_PDF_RESPONDENT_FIELD_TITLE_MR = "2.1 Mr";
    public static final String CHECKBOX_PDF_RESPONDENT_FIELD_TITLE_MRS = "2.1 Mrs";
    public static final String CHECKBOX_PDF_RESPONDENT_FIELD_TITLE_MS = "2.1 Ms";
    public static final String CHECKBOX_PDF_RESPONDENT_FIELD_TITLE_OTHER = "2.1 Other";
    public static final String TXT_PDF_RESPONDENT_FIELD_ADDRESS = "2.3 Respondent's address: number or name";
    public static final String TXT_PDF_RESPONDENT_FIELD_CONTACT_NAME = "2.2 name of contact";
    public static final String TXT_PDF_RESPONDENT_FIELD_DX = "2.3 dx number";
    public static final String TXT_PDF_RESPONDENT_FIELD_EMAIL = "2.6 email address";
    public static final String TXT_PDF_RESPONDENT_FIELD_EMPLOYEE_NUMBER_CLAIMANT_WORK_PLACE =
            "2.13 employees employed at that site";
    public static final String TXT_PDF_RESPONDENT_FIELD_EMPLOYEE_NUMBER_GREAT_BRITAIN = "2.11 number of employees";
    public static final String TXT_PDF_RESPONDENT_FIELD_MOBILE_NUMBER = "2.4 mobile number";
    public static final String TXT_PDF_RESPONDENT_FIELD_NAME = "2.2 name";
    public static final String TXT_PDF_RESPONDENT_FIELD_NUMBER = "2.3";
    public static final String TXT_PDF_RESPONDENT_FIELD_PHONE_NUMBER = "2.4 phone number";
    public static final String TXT_PDF_RESPONDENT_FIELD_POSTCODE = "2.3 Respondent's address: postcode";
    public static final String TXT_PDF_RESPONDENT_FIELD_TITLE_OTHER = "2.1 other specify";
    public static final String TXT_PDF_RESPONDENT_FIELD_TYPE = "2.4";

    // SECTION 3 ACAS CONSTANTS
    public static final String CHECKBOX_PDF_ACAS_FIELD_AGREEMENT_YES = "3.1 early conciliation details - yes";
    public static final String CHECKBOX_PDF_ACAS_FIELD_AGREEMENT_NO = "3.1 early conciliation details - no";
    public static final String TXT_PDF_ACAS_FIELD_AGREEMENT_NO_REASON = "3.1 If no, please explain why";

    // SECTION 4 EMPLOYMENT CONSTANTS
    public static final String CHECKBOX_PDF_EMPLOYMENT_FIELD_DATES_CORRECT_YES = "4.1 dates correct - yes";
    public static final String CHECKBOX_PDF_EMPLOYMENT_FIELD_DATES_CORRECT_NO = "4.1 dates correct - no";
    public static final String CHECKBOX_PDF_EMPLOYMENT_FIELD_DATES_CORRECT_NOT_APPLICABLE =
            "4.1 dates correct - not applicable";
    public static final String TXT_PDF_EMPLOYMENT_FIELD_START_DATE_DAY = "4.1 date day";
    public static final String TXT_PDF_EMPLOYMENT_FIELD_START_DATE_MONTH = "4.1 date month";
    public static final String TXT_PDF_EMPLOYMENT_FIELD_START_DATE_YEAR = "4.1 date year";
    public static final String TXT_PDF_EMPLOYMENT_FIELD_END_DATE_DAY = "4.1b date day";
    public static final String TXT_PDF_EMPLOYMENT_FIELD_END_DATE_MONTH = "4.1b date month";
    public static final String TXT_PDF_EMPLOYMENT_FIELD_END_DATE_YEAR = "4.1b date year";
    public static final String TXT_PDF_EMPLOYMENT_FIELD_DATES_FURTHER_INFO =
            "4.1 reasons you disagree with the claimant's dates";
    public static final String CHECKBOX_PDF_EMPLOYMENT_FIELD_CONTINUES_YES = "4.2 employment continuing - yes";
    public static final String CHECKBOX_PDF_EMPLOYMENT_FIELD_CONTINUES_NO = "4.2 employment continuing - no";
    public static final String CHECKBOX_PDF_EMPLOYMENT_FIELD_CONTINUES_NOT_APPLICABLE =
            "4.2 employment continuing - not applicable";
    public static final String CHECKBOX_PDF_EMPLOYMENT_FIELD_JOB_TITLE_CORRECT_YES =
            "4.3 job description correct - yes";
    public static final String CHECKBOX_PDF_EMPLOYMENT_FIELD_JOB_TITLE_CORRECT_NO =
            "4.3 job description correct - no";
    public static final String CHECKBOX_PDF_EMPLOYMENT_FIELD_JOB_TITLE_CORRECT_NOT_APPLICABLE =
            "4.3 job description correct - not applicable";
    public static final String TXT_PDF_EMPLOYMENT_FIELD_JOB_TITLE_CORRECT_DETAILS = "4.3 correct details";

    // SECTION 5 EARNINGS AND BENEFITS CONSTANTS
    public static final String CHECKBOX_PDF_EARNINGS_BENEFITS_FIELD_HOURS_OF_WORK_CORRECT_YES =
            "5.1 claimant's hours of work are correct - yes";
    public static final String CHECKBOX_PDF_EARNINGS_BENEFITS_FIELD_HOURS_OF_WORK_CORRECT_NO =
            "5.1 claimant's hours of work are correct - no";
    public static final String CHECKBOX_PDF_EARNINGS_BENEFITS_FIELD_HOURS_OF_WORK_CORRECT_NOT_APPLICABLE =
            "5.1 claimant's hours of work are correct - not applicable";
    public static final String TXT_PDF_EARNINGS_BENEFITS_FIELD_WORK_HOURS_DETAILS = "Work hours details";
    public static final String CHECKBOX_PDF_EARNINGS_BENEFITS_FIELD_EARNING_DETAILS_CORRECT_YES =
            "5.2 earning details correct - yes";
    public static final String CHECKBOX_PDF_EARNINGS_BENEFITS_FIELD_EARNING_DETAILS_CORRECT_NO =
            "5.2 earning details correct - no";
    public static final String CHECKBOX_PDF_EARNINGS_BENEFITS_FIELD_EARNING_DETAILS_CORRECT_NOT_APPLICABLE =
            "5.2 earning details correct - not applicable";
    public static final String TXT_PDF_EARNINGS_BENEFITS_FIELD_PAY_BEFORE_TAX = "5.2 pay before tax";
    public static final String CHECKBOX_PDF_EARNINGS_BENEFITS_FIELD_PAY_BEFORE_TAX_WEEKLY =
            "5.2 pay before tax - weekly";
    public static final String CHECKBOX_PDF_EARNINGS_BENEFITS_FIELD_PAY_BEFORE_TAX_MONTHLY =
            "5.2 pay before tax - monthly";
    public static final String CHECKBOX_PDF_EARNINGS_BENEFITS_FIELD_PAY_BEFORE_TAX_ANNUALLY =
            "5.2 pay before tax - annually";
    public static final String TXT_PDF_EARNINGS_BENEFITS_FIELD_NORMAL_TAKE_HOME_PAY = "5.2 normal take-home pay";
    public static final String CHECKBOX_PDF_EARNINGS_BENEFITS_FIELD_NORMAL_TAKE_HOME_PAY_WEEKLY =
            "5.2 take-home pay - weekly";
    public static final String CHECKBOX_PDF_EARNINGS_BENEFITS_FIELD_NORMAL_TAKE_HOME_PAY_MONTHLY =
            "5.2 take-home pay - monthly";
    public static final String CHECKBOX_PDF_EARNINGS_BENEFITS_FIELD_NORMAL_TAKE_HOME_PAY_ANNUALLY =
            "5.2 take-home pay - annually";
    public static final String CHECKBOX_PDF_EARNINGS_BENEFITS_FIELD_NOTICE_PERIOD_CORRECT_YES =
            "5.3 period of notice information - correct";
    public static final String CHECKBOX_PDF_EARNINGS_BENEFITS_FIELD_NOTICE_PERIOD_CORRECT_NO =
            "5.3 period of notice information - not correct";
    public static final String CHECKBOX_PDF_EARNINGS_BENEFITS_FIELD_NOTICE_PERIOD_CORRECT_NOT_APPLICABLE =
            "5.3 period of notice information - not applicable";
    public static final String TXT_PDF_EARNINGS_BENEFITS_FIELD_NOTICE_PERIOD_NOT_CORRECT_INFORMATION =
            "5.3 if information is not correct";
    public static final String CHECKBOX_PDF_EARNINGS_BENEFITS_FIELD_PENSION_AND_OTHER_BENEFITS_CORRECT_YES =
            "5.4 pension details - correct";
    public static final String CHECKBOX_PDF_EARNINGS_BENEFITS_FIELD_PENSION_AND_OTHER_BENEFITS_CORRECT_NO =
            "5.4 pension details - not correct";
    public static final String CHECKBOX_PDF_EARNINGS_BENEFITS_FIELD_PENSION_AND_OTHER_BENEFITS_CORRECT_NOT_APPLICABLE =
            "5.4 pension details - not applicable";
    public static final String TXT_PDF_EARNINGS_BENEFITS_FIELD_PENSION_AND_OTHER_BENEFITS_NOT_CORRECT_INFORMATION =
            "5.4 if not correct, details";

    // SECTION 6 RESPONSE CONSTANTS
    public static final String CHECKBOX_PDF_RESPONSE_FIELD_CONTEST_CLAIM_YES = "6.1 defend the claim - yes";
    public static final String CHECKBOX_PDF_RESPONSE_FIELD_CONTEST_CLAIM_NO = "6.1 defend the claim - no";
    public static final String TXT_PDF_RESPONSE_FIELD_CONTEST_CLAIM_CORRECT_FACTS = "6.1 if yes, facts in defence";

    // SECTION 7 EMPLOYER CONTRACT CLAIM CONSTANTS
    public static final String CHECKBOX_PDF_EMPLOYER_CONTRACT_CLAIM_FIELD_YES = "7.2 Employer's Contract Claim - yes";
    public static final String TXT_PDF_EMPLOYER_CONTRACT_CLAIM_FIELD_DETAILS =
            "7.3 details of Employer's Contract Claim";

    // SECTION 8 REPRESENTATIVE CONSTANTS
    public static final String TXT_PDF_REPRESENTATIVE_FIELD_NAME = "8.1 name of your representative";
    public static final String TXT_PDF_REPRESENTATIVE_FIELD_ORGANISATION_NAME =
            "8.2 Name of representative's organisation";
    public static final String TXT_PDF_REPRESENTATIVE_FIELD_ADDRESS = "8.3 representative's number or name";
    public static final String TXT_PDF_REPRESENTATIVE_FIELD_POSTCODE = "8.3 representative's postcode";
    public static final String TXT_PDF_REPRESENTATIVE_FIELD_PHONE_NUMBER = "8.5 phone number";
    public static final String TXT_PDF_REPRESENTATIVE_FIELD_MOBILE_PHONE_NUMBER = "8.6 mobile phone number";
    public static final String TXT_PDF_REPRESENTATIVE_FIELD_REFERENCE_FOR_CORRESPONDENCE =
            "8.7 representative's reference for correspondence";
    public static final String CHECKBOX_PDF_REPRESENTATIVE_FIELD_COMMUNICATION_PREFERENCE_EMAIL = "8.8 email";
    public static final String CHECKBOX_PDF_REPRESENTATIVE_FIELD_COMMUNICATION_PREFERENCE_POST = "8.8 post";
    public static final String TXT_PDF_REPRESENTATIVE_FIELD_EMAIL_ADDRESS = "8.9 email address";
    public static final String CHECKBOX_PDF_REPRESENTATIVE_FIELD_VIDEO_HEARINGS = "8.10 video";
    public static final String CHECKBOX_PDF_REPRESENTATIVE_FIELD_PHONE_HEARINGS = "8.10 phone";

    // SECTION 9 DISABILITY CONSTANTS
    public static final String CHECKBOX_PDF_DISABILITY_YES = "9.1 disability - yes";
    public static final String CHECKBOX_PDF_DISABILITY_NO = "9.1 disability - no";
    public static final String CHECKBOX_PDF_DISABILITY_NOT_SURE = "9.1 disability - I'm not sure yet";
    public static final String TXT_PDF_DISABILITY_DETAILS = "9.1 details of disability";

    // ET3 FORM MAPPER CONSTANTS
    public static final String RESPONDENT_NOT_FOUND_EXCEPTION_FIRST_WORD =
            "There were no respondent found for the entered respondent name ";
    public static final String CASE_DATA_NOT_FOUND_EXCEPTION_FIRST_WORD = "Case data is empty";
    public static final String RESPONDENT_COLLECTION_NOT_FOUND_EXCEPTION_FIRST_WORD =
            "There is no respondent defined in the case";
    public static final String ET3_FORM_MAPPER_CLASS_NAME = "ET3FormMapper";
    public static final String ET3_FORM_MAPPER_METHOD_NAME = "mapEt3Form";
    public static final String ET3_FORM_CASE_DATA_CHECK_METHOD_NAME = "mapEt3Form";
    public static final String CASE_DATA_NOT_FOUND_EXCEPTION_MESSAGE = "Case Data is null or empty";
    public static final String RESPONDENT_COLLECTION_NOT_FOUND_EXCEPTION_MESSAGE = "Respondent Collection is empty";
    public static final String RESPONDENT_NOT_FOUND_IN_RESPONDENT_COLLECTION_EXCEPTION_MESSAGE =
            "ET3 Respondent not found, most probably label in the "
                    + "case data(caseData.getSubmitEt3Respondent().getSelectedLabel()) "
                    + "does not match with any name in the respondent collection";
    public static final String  RESPONDENT_NOT_FOUND_IN_CASE_DATA_EXCEPTION_MESSAGE =
            "ET3 Form submitting respondent not found in case data";
    public static final String RESPONDENT_NAME_NOT_FOUND_IN_CASE_DATA_EXCEPTION_MESSAGE =
            "ET3 Form submitting respondent name is empty or blank";
    public static final String SUBMIT_ET3 = "submitEt3";
    public static final String SUBMIT_ET3_CITIZEN = "submitEt3Citizen";
    public static final String UNABLE_TO_MAP_RESPONDENT_TO_ET3_FORM = "Unable to map respondent to et3 form";
    public static final String ET3_FORM_CLIENT_TYPE_REPRESENTATIVE = "representative";
    public static final String ET3_FORM_CLIENT_TYPE_RESPONDENT = "respondent";

    private ET3FormConstants() {
        // Add a private constructor to hide the implicit public one.
    }
}
