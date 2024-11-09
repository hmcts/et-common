package uk.gov.hmcts.ecm.common.service.pdf.et3;

import uk.gov.hmcts.et.common.model.ccd.types.RespondentSumType;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.ConcurrentMap;

import static uk.gov.hmcts.ecm.common.service.pdf.et3.ET3FormConstants.CHECKBOX_PDF_EMPLOYMENT_FIELD_CONTINUES_NO;
import static uk.gov.hmcts.ecm.common.service.pdf.et3.ET3FormConstants.CHECKBOX_PDF_EMPLOYMENT_FIELD_CONTINUES_NOT_APPLICABLE;
import static uk.gov.hmcts.ecm.common.service.pdf.et3.ET3FormConstants.CHECKBOX_PDF_EMPLOYMENT_FIELD_CONTINUES_YES;
import static uk.gov.hmcts.ecm.common.service.pdf.et3.ET3FormConstants.CHECKBOX_PDF_EMPLOYMENT_FIELD_DATES_CORRECT_NO;
import static uk.gov.hmcts.ecm.common.service.pdf.et3.ET3FormConstants.CHECKBOX_PDF_EMPLOYMENT_FIELD_DATES_CORRECT_NOT_APPLICABLE;
import static uk.gov.hmcts.ecm.common.service.pdf.et3.ET3FormConstants.CHECKBOX_PDF_EMPLOYMENT_FIELD_DATES_CORRECT_YES;
import static uk.gov.hmcts.ecm.common.service.pdf.et3.ET3FormConstants.CHECKBOX_PDF_EMPLOYMENT_FIELD_JOB_TITLE_CORRECT_NO;
import static uk.gov.hmcts.ecm.common.service.pdf.et3.ET3FormConstants.CHECKBOX_PDF_EMPLOYMENT_FIELD_JOB_TITLE_CORRECT_NOT_APPLICABLE;
import static uk.gov.hmcts.ecm.common.service.pdf.et3.ET3FormConstants.CHECKBOX_PDF_EMPLOYMENT_FIELD_JOB_TITLE_CORRECT_YES;
import static uk.gov.hmcts.ecm.common.service.pdf.et3.ET3FormConstants.DATE_FORMAT_DD;
import static uk.gov.hmcts.ecm.common.service.pdf.et3.ET3FormConstants.DATE_FORMAT_MM;
import static uk.gov.hmcts.ecm.common.service.pdf.et3.ET3FormConstants.DATE_FORMAT_YYYY;
import static uk.gov.hmcts.ecm.common.service.pdf.et3.ET3FormConstants.DATE_FORMAT_YYYY_MM_DD_DASH;
import static uk.gov.hmcts.ecm.common.service.pdf.et3.ET3FormConstants.NO_CAPITALISED;
import static uk.gov.hmcts.ecm.common.service.pdf.et3.ET3FormConstants.NO_LOWERCASE;
import static uk.gov.hmcts.ecm.common.service.pdf.et3.ET3FormConstants.TXT_PDF_EMPLOYMENT_FIELD_DATES_FURTHER_INFO;
import static uk.gov.hmcts.ecm.common.service.pdf.et3.ET3FormConstants.TXT_PDF_EMPLOYMENT_FIELD_END_DATE_DAY;
import static uk.gov.hmcts.ecm.common.service.pdf.et3.ET3FormConstants.TXT_PDF_EMPLOYMENT_FIELD_END_DATE_MONTH;
import static uk.gov.hmcts.ecm.common.service.pdf.et3.ET3FormConstants.TXT_PDF_EMPLOYMENT_FIELD_END_DATE_YEAR;
import static uk.gov.hmcts.ecm.common.service.pdf.et3.ET3FormConstants.TXT_PDF_EMPLOYMENT_FIELD_JOB_TITLE_CORRECT_DETAILS;
import static uk.gov.hmcts.ecm.common.service.pdf.et3.ET3FormConstants.TXT_PDF_EMPLOYMENT_FIELD_START_DATE_DAY;
import static uk.gov.hmcts.ecm.common.service.pdf.et3.ET3FormConstants.TXT_PDF_EMPLOYMENT_FIELD_START_DATE_MONTH;
import static uk.gov.hmcts.ecm.common.service.pdf.et3.ET3FormConstants.TXT_PDF_EMPLOYMENT_FIELD_START_DATE_YEAR;
import static uk.gov.hmcts.ecm.common.service.pdf.et3.ET3FormConstants.YES_CAPITALISED;
import static uk.gov.hmcts.ecm.common.service.pdf.et3.ET3FormConstants.YES_LOWERCASE;
import static uk.gov.hmcts.ecm.common.service.pdf.et3.util.ET3FormUtil.formatDate;
import static uk.gov.hmcts.ecm.common.service.pdf.et3.util.ET3FormUtil.putConditionalPdfField;
import static uk.gov.hmcts.ecm.common.service.pdf.et3.util.ET3FormUtil.putPdfCheckboxFieldWhenExpectedValueEqualsActualValue;
import static uk.gov.hmcts.ecm.common.service.pdf.et3.util.ET3FormUtil.putPdfCheckboxFieldWhenOther;
import static uk.gov.hmcts.ecm.common.service.pdf.et3.util.ET3FormUtil.putPdfTextField;

public final class ET3FormEmploymentMapper {

    private ET3FormEmploymentMapper() {
        // Add a private constructor to hide the implicit public one.
    }

    /**
     * Maps employment values with PDF input fields.
     * @param respondentSumType respondent data selected by representative of respondent
     * @param pdfFields print fields that is created in ET3FormMapper
     */
    public static void mapEmployment(RespondentSumType respondentSumType,
                                     ConcurrentMap<String, Optional<String>> pdfFields) {
        // EMPLOYMENT DATES SECTION
        putPdfCheckboxFieldWhenExpectedValueEqualsActualValue(pdfFields,
                CHECKBOX_PDF_EMPLOYMENT_FIELD_DATES_CORRECT_YES,
                YES_LOWERCASE,
                YES_CAPITALISED,
                respondentSumType.getEt3ResponseAreDatesCorrect());
        putPdfCheckboxFieldWhenExpectedValueEqualsActualValue(pdfFields,
                CHECKBOX_PDF_EMPLOYMENT_FIELD_DATES_CORRECT_NO,
                NO_LOWERCASE,
                NO_CAPITALISED,
                respondentSumType.getEt3ResponseAreDatesCorrect());
        putPdfCheckboxFieldWhenOther(pdfFields,
                CHECKBOX_PDF_EMPLOYMENT_FIELD_DATES_CORRECT_NOT_APPLICABLE,
                NO_LOWERCASE,
                List.of(YES_CAPITALISED, NO_CAPITALISED),
                respondentSumType.getEt3ResponseAreDatesCorrect());
        putConditionalPdfField(pdfFields, TXT_PDF_EMPLOYMENT_FIELD_START_DATE_DAY, NO_CAPITALISED,
                respondentSumType.getEt3ResponseAreDatesCorrect(),
                formatDate(respondentSumType.getEt3ResponseEmploymentStartDate(),
                        DATE_FORMAT_YYYY_MM_DD_DASH, DATE_FORMAT_DD));
        putConditionalPdfField(pdfFields, TXT_PDF_EMPLOYMENT_FIELD_START_DATE_MONTH, NO_CAPITALISED,
                respondentSumType.getEt3ResponseAreDatesCorrect(),
                formatDate(respondentSumType.getEt3ResponseEmploymentStartDate(),
                        DATE_FORMAT_YYYY_MM_DD_DASH, DATE_FORMAT_MM));
        putConditionalPdfField(pdfFields, TXT_PDF_EMPLOYMENT_FIELD_START_DATE_YEAR, NO_CAPITALISED,
                respondentSumType.getEt3ResponseAreDatesCorrect(),
                formatDate(respondentSumType.getEt3ResponseEmploymentStartDate(),
                        DATE_FORMAT_YYYY_MM_DD_DASH, DATE_FORMAT_YYYY));
        putConditionalPdfField(pdfFields, TXT_PDF_EMPLOYMENT_FIELD_END_DATE_DAY, NO_CAPITALISED,
                respondentSumType.getEt3ResponseAreDatesCorrect(),
                formatDate(respondentSumType.getEt3ResponseEmploymentEndDate(),
                        DATE_FORMAT_YYYY_MM_DD_DASH, DATE_FORMAT_DD));
        putConditionalPdfField(pdfFields, TXT_PDF_EMPLOYMENT_FIELD_END_DATE_MONTH, NO_CAPITALISED,
                respondentSumType.getEt3ResponseAreDatesCorrect(),
                formatDate(respondentSumType.getEt3ResponseEmploymentEndDate(),
                        DATE_FORMAT_YYYY_MM_DD_DASH, DATE_FORMAT_MM));
        putConditionalPdfField(pdfFields, TXT_PDF_EMPLOYMENT_FIELD_END_DATE_YEAR, NO_CAPITALISED,
                respondentSumType.getEt3ResponseAreDatesCorrect(),
                formatDate(respondentSumType.getEt3ResponseEmploymentEndDate(),
                        DATE_FORMAT_YYYY_MM_DD_DASH, DATE_FORMAT_YYYY));
        putPdfTextField(pdfFields, TXT_PDF_EMPLOYMENT_FIELD_DATES_FURTHER_INFO,
                respondentSumType.getEt3ResponseEmploymentInformation());
        // END OF EMPLOYMENT DATES SECTION
        // EMPLOYMENT CONTINUES SECTION
        putPdfCheckboxFieldWhenExpectedValueEqualsActualValue(pdfFields,
                CHECKBOX_PDF_EMPLOYMENT_FIELD_CONTINUES_YES,
                YES_LOWERCASE,
                YES_CAPITALISED,
                respondentSumType.getEt3ResponseContinuingEmployment());
        putPdfCheckboxFieldWhenExpectedValueEqualsActualValue(pdfFields,
                CHECKBOX_PDF_EMPLOYMENT_FIELD_CONTINUES_NO,
                NO_LOWERCASE,
                NO_CAPITALISED,
                respondentSumType.getEt3ResponseContinuingEmployment());
        putPdfCheckboxFieldWhenOther(pdfFields,
                CHECKBOX_PDF_EMPLOYMENT_FIELD_CONTINUES_NOT_APPLICABLE,
                NO_LOWERCASE,
                List.of(YES_CAPITALISED, NO_CAPITALISED),
                respondentSumType.getEt3ResponseContinuingEmployment());
        // END OF EMPLOYMENT CONTINUES SECTION
        // DESCRIPTION OF JOB SECTION
        putPdfCheckboxFieldWhenExpectedValueEqualsActualValue(pdfFields,
                CHECKBOX_PDF_EMPLOYMENT_FIELD_JOB_TITLE_CORRECT_YES,
                YES_LOWERCASE,
                YES_CAPITALISED,
                respondentSumType.getEt3ResponseIsJobTitleCorrect());
        putPdfCheckboxFieldWhenExpectedValueEqualsActualValue(pdfFields,
                CHECKBOX_PDF_EMPLOYMENT_FIELD_JOB_TITLE_CORRECT_NO,
                NO_LOWERCASE,
                NO_CAPITALISED,
                respondentSumType.getEt3ResponseIsJobTitleCorrect());
        putPdfCheckboxFieldWhenOther(pdfFields,
                CHECKBOX_PDF_EMPLOYMENT_FIELD_JOB_TITLE_CORRECT_NOT_APPLICABLE,
                NO_LOWERCASE,
                List.of(YES_CAPITALISED, NO_CAPITALISED),
                respondentSumType.getEt3ResponseIsJobTitleCorrect());
        putConditionalPdfField(pdfFields, TXT_PDF_EMPLOYMENT_FIELD_JOB_TITLE_CORRECT_DETAILS, NO_CAPITALISED,
                respondentSumType.getEt3ResponseIsJobTitleCorrect(),
                respondentSumType.getEt3ResponseCorrectJobTitle());
    }
}
