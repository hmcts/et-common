package uk.gov.hmcts.ecm.common.service.pdf.et3;

import uk.gov.hmcts.et.common.model.ccd.types.RespondentSumType;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.ConcurrentMap;

import static uk.gov.hmcts.ecm.common.service.pdf.et3.util.ET3FormUtil.putConditionalPdfCheckboxField;
import static uk.gov.hmcts.ecm.common.service.pdf.et3.util.ET3FormUtil.putConditionalPdfField;
import static uk.gov.hmcts.ecm.common.service.pdf.et3.util.ET3FormUtil.putPdfCheckboxFieldWhenExpectedValueEqualsActualValue;
import static uk.gov.hmcts.ecm.common.service.pdf.et3.util.ET3FormUtil.putPdfCheckboxFieldWhenOther;
import static uk.gov.hmcts.ecm.common.service.pdf.et3.util.ET3MapperUtil.correctCurrency;

public final class ET3FormEarningsAndBenefitsMapper {

    private ET3FormEarningsAndBenefitsMapper() {
        // Add a private constructor to hide the implicit public one.
    }

    /**
     * Maps pension and benefit values with PDF input fields.
     * @param respondentSumType respondent data selected by representative of respondent
     * @param pdfFields print fields that is created in ET3FormMapper
     */
    public static void mapEarningsAndBenefits(RespondentSumType respondentSumType,
                                              ConcurrentMap<String, Optional<String>> pdfFields) {
        // CLAIMANT WORK HOURS
        putPdfCheckboxFieldWhenExpectedValueEqualsActualValue(pdfFields,
                ET3FormConstants.CHECKBOX_PDF_EARNINGS_BENEFITS_FIELD_HOURS_OF_WORK_CORRECT_YES,
                ET3FormConstants.YES_LOWERCASE,
                ET3FormConstants.YES_CAPITALISED,
                respondentSumType.getEt3ResponseClaimantWeeklyHours());
        putPdfCheckboxFieldWhenExpectedValueEqualsActualValue(pdfFields,
                ET3FormConstants.CHECKBOX_PDF_EARNINGS_BENEFITS_FIELD_HOURS_OF_WORK_CORRECT_NO,
                ET3FormConstants.NO_LOWERCASE,
                ET3FormConstants.NO_CAPITALISED,
                respondentSumType.getEt3ResponseClaimantWeeklyHours());
        putPdfCheckboxFieldWhenOther(pdfFields,
                ET3FormConstants.CHECKBOX_PDF_EARNINGS_BENEFITS_FIELD_HOURS_OF_WORK_CORRECT_NOT_APPLICABLE,
                ET3FormConstants.NO_LOWERCASE,
                List.of(ET3FormConstants.NO_CAPITALISED, ET3FormConstants.YES_CAPITALISED),
                respondentSumType.getEt3ResponseClaimantWeeklyHours());
        putConditionalPdfField(pdfFields,
                ET3FormConstants.TXT_PDF_EARNINGS_BENEFITS_FIELD_WORK_HOURS_DETAILS,
                ET3FormConstants.NO_CAPITALISED,
                respondentSumType.getEt3ResponseClaimantWeeklyHours(),
                respondentSumType.getEt3ResponseClaimantCorrectHours());
        // END OF CLAIMANT WORK HOURS
        // EARNING DETAILS
        putPdfCheckboxFieldWhenExpectedValueEqualsActualValue(pdfFields,
                ET3FormConstants.CHECKBOX_PDF_EARNINGS_BENEFITS_FIELD_EARNING_DETAILS_CORRECT_YES,
                ET3FormConstants.YES_LOWERCASE,
                ET3FormConstants.YES_CAPITALISED,
                respondentSumType.getEt3ResponseEarningDetailsCorrect());
        putPdfCheckboxFieldWhenExpectedValueEqualsActualValue(pdfFields,
                ET3FormConstants.CHECKBOX_PDF_EARNINGS_BENEFITS_FIELD_EARNING_DETAILS_CORRECT_NO,
                ET3FormConstants.NO_LOWERCASE,
                ET3FormConstants.NO_CAPITALISED,
                respondentSumType.getEt3ResponseEarningDetailsCorrect());
        putPdfCheckboxFieldWhenOther(pdfFields,
                ET3FormConstants.CHECKBOX_PDF_EARNINGS_BENEFITS_FIELD_EARNING_DETAILS_CORRECT_NOT_APPLICABLE,
                ET3FormConstants.NO_LOWERCASE,
                List.of(ET3FormConstants.NO_CAPITALISED, ET3FormConstants.YES_CAPITALISED),
                respondentSumType.getEt3ResponseEarningDetailsCorrect());
        putConditionalPdfField(pdfFields,
                ET3FormConstants.TXT_PDF_EARNINGS_BENEFITS_FIELD_PAY_BEFORE_TAX,
                ET3FormConstants.NO_CAPITALISED,
                respondentSumType.getEt3ResponseEarningDetailsCorrect(),
                correctCurrency(respondentSumType.getEt3ResponsePayBeforeTax()));
        putConditionalPdfCheckboxField(pdfFields,
                ET3FormConstants.CHECKBOX_PDF_EARNINGS_BENEFITS_FIELD_PAY_BEFORE_TAX_WEEKLY,
                ET3FormConstants.NO_CAPITALISED,
                respondentSumType.getEt3ResponseEarningDetailsCorrect(),
                getPaymentFrequencyCheckboxValue(respondentSumType.getEt3ResponsePayFrequency(),
                        ET3FormConstants.WEEKLY_CAPITALISED));
        putConditionalPdfCheckboxField(pdfFields,
                ET3FormConstants.CHECKBOX_PDF_EARNINGS_BENEFITS_FIELD_PAY_BEFORE_TAX_MONTHLY,
                ET3FormConstants.NO_CAPITALISED,
                respondentSumType.getEt3ResponseEarningDetailsCorrect(),
                getPaymentFrequencyCheckboxValue(respondentSumType.getEt3ResponsePayFrequency(),
                        ET3FormConstants.MONTHLY_CAPITALISED));
        putConditionalPdfCheckboxField(pdfFields,
                ET3FormConstants.CHECKBOX_PDF_EARNINGS_BENEFITS_FIELD_PAY_BEFORE_TAX_ANNUALLY,
                ET3FormConstants.NO_CAPITALISED,
                respondentSumType.getEt3ResponseEarningDetailsCorrect(),
                getPaymentFrequencyCheckboxValue(respondentSumType.getEt3ResponsePayFrequency(),
                        ET3FormConstants.ANNUALLY_CAPITALISED));
        putConditionalPdfField(pdfFields,
                ET3FormConstants.TXT_PDF_EARNINGS_BENEFITS_FIELD_NORMAL_TAKE_HOME_PAY,
                ET3FormConstants.NO_CAPITALISED,
                respondentSumType.getEt3ResponseEarningDetailsCorrect(),
                correctCurrency(respondentSumType.getEt3ResponsePayTakehome()));
        putConditionalPdfCheckboxField(pdfFields,
                ET3FormConstants.CHECKBOX_PDF_EARNINGS_BENEFITS_FIELD_NORMAL_TAKE_HOME_PAY_WEEKLY,
                ET3FormConstants.NO_CAPITALISED,
                respondentSumType.getEt3ResponseEarningDetailsCorrect(),
                getPaymentFrequencyCheckboxValue(respondentSumType.getEt3ResponsePayFrequency(),
                        ET3FormConstants.WEEKLY_CAPITALISED));
        putConditionalPdfCheckboxField(pdfFields,
                ET3FormConstants.CHECKBOX_PDF_EARNINGS_BENEFITS_FIELD_NORMAL_TAKE_HOME_PAY_MONTHLY,
                ET3FormConstants.NO_CAPITALISED,
                respondentSumType.getEt3ResponseEarningDetailsCorrect(),
                getPaymentFrequencyCheckboxValue(respondentSumType.getEt3ResponsePayFrequency(),
                        ET3FormConstants.MONTHLY_CAPITALISED));
        putConditionalPdfCheckboxField(pdfFields,
                ET3FormConstants.CHECKBOX_PDF_EARNINGS_BENEFITS_FIELD_NORMAL_TAKE_HOME_PAY_ANNUALLY,
                ET3FormConstants.NO_CAPITALISED,
                respondentSumType.getEt3ResponseEarningDetailsCorrect(),
                getPaymentFrequencyCheckboxValue(respondentSumType.getEt3ResponsePayFrequency(),
                        ET3FormConstants.ANNUALLY_CAPITALISED));
        // END OF EARNING DETAILS
        // NOTICE PERIOD DETAILS
        putPdfCheckboxFieldWhenExpectedValueEqualsActualValue(pdfFields,
                ET3FormConstants.CHECKBOX_PDF_EARNINGS_BENEFITS_FIELD_NOTICE_PERIOD_CORRECT_YES,
                ET3FormConstants.YES_LOWERCASE,
                ET3FormConstants.YES_CAPITALISED,
                respondentSumType.getEt3ResponseIsNoticeCorrect());
        putPdfCheckboxFieldWhenExpectedValueEqualsActualValue(pdfFields,
                ET3FormConstants.CHECKBOX_PDF_EARNINGS_BENEFITS_FIELD_NOTICE_PERIOD_CORRECT_NO,
                ET3FormConstants.NO_LOWERCASE,
                ET3FormConstants.NO_CAPITALISED,
                respondentSumType.getEt3ResponseIsNoticeCorrect());
        putPdfCheckboxFieldWhenOther(pdfFields,
                ET3FormConstants.CHECKBOX_PDF_EARNINGS_BENEFITS_FIELD_NOTICE_PERIOD_CORRECT_NOT_APPLICABLE,
                ET3FormConstants.NO_LOWERCASE,
                List.of(ET3FormConstants.NO_CAPITALISED, ET3FormConstants.YES_CAPITALISED),
                respondentSumType.getEt3ResponseIsNoticeCorrect());
        putConditionalPdfField(pdfFields,
                ET3FormConstants.TXT_PDF_EARNINGS_BENEFITS_FIELD_NOTICE_PERIOD_NOT_CORRECT_INFORMATION,
                ET3FormConstants.NO_CAPITALISED,
                respondentSumType.getEt3ResponseIsNoticeCorrect(),
                respondentSumType.getEt3ResponseCorrectNoticeDetails());
        // END OF NOTICE PERIOD DETAILS
        // PENSION & BENEFIT DETAILS
        putPdfCheckboxFieldWhenExpectedValueEqualsActualValue(pdfFields,
                ET3FormConstants.CHECKBOX_PDF_EARNINGS_BENEFITS_FIELD_PENSION_AND_OTHER_BENEFITS_CORRECT_YES,
                ET3FormConstants.YES_LOWERCASE,
                ET3FormConstants.YES_CAPITALISED,
                respondentSumType.getEt3ResponseIsPensionCorrect());
        putPdfCheckboxFieldWhenExpectedValueEqualsActualValue(pdfFields,
                ET3FormConstants.CHECKBOX_PDF_EARNINGS_BENEFITS_FIELD_PENSION_AND_OTHER_BENEFITS_CORRECT_NO,
                ET3FormConstants.NO_LOWERCASE,
                ET3FormConstants.NO_CAPITALISED,
                respondentSumType.getEt3ResponseIsPensionCorrect());
        putPdfCheckboxFieldWhenOther(pdfFields,
                ET3FormConstants.CHECKBOX_PDF_EARNINGS_BENEFITS_FIELD_PENSION_AND_OTHER_BENEFITS_CORRECT_NOT_APPLICABLE,
                ET3FormConstants.NO_LOWERCASE,
                List.of(ET3FormConstants.NO_CAPITALISED, ET3FormConstants.YES_CAPITALISED),
                respondentSumType.getEt3ResponseIsPensionCorrect());
        putConditionalPdfField(pdfFields,
                ET3FormConstants.TXT_PDF_EARNINGS_BENEFITS_FIELD_PENSION_AND_OTHER_BENEFITS_NOT_CORRECT_INFORMATION,
                ET3FormConstants.NO_CAPITALISED,
                respondentSumType.getEt3ResponseIsPensionCorrect(),
                respondentSumType.getEt3ResponsePensionCorrectDetails());
    }

    private static String getPaymentFrequencyCheckboxValue(String payFrequency, String expectedPayFrequency) {
        if (!expectedPayFrequency.equalsIgnoreCase(payFrequency)) {
            return ET3FormConstants.STRING_EMPTY;
        }
        return switch (payFrequency) {
            case ET3FormConstants.WEEKLY_CAPITALISED -> ET3FormConstants.WEEKLY_LOWERCASE;
            case ET3FormConstants.MONTHLY_CAPITALISED -> ET3FormConstants.MONTHLY_LOWERCASE;
            case ET3FormConstants.ANNUALLY_CAPITALISED -> ET3FormConstants.ANNUALLY_LOWERCASE;
            default -> ET3FormConstants.STRING_EMPTY;
        };
    }
}
