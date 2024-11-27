package uk.gov.hmcts.ecm.common.service.pdf.et3;

import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import uk.gov.hmcts.et.common.model.ccd.types.RespondentSumType;

import java.util.Optional;
import java.util.concurrent.ConcurrentMap;

import static uk.gov.hmcts.ecm.common.service.pdf.et3.util.ET3FormUtil.putPdfAddressField;
import static uk.gov.hmcts.ecm.common.service.pdf.et3.util.ET3FormUtil.putPdfCheckboxFieldWhenActualValueContainsExpectedValue;
import static uk.gov.hmcts.ecm.common.service.pdf.et3.util.ET3FormUtil.putPdfCheckboxFieldWhenExpectedValueEqualsActualValue;
import static uk.gov.hmcts.ecm.common.service.pdf.et3.util.ET3FormUtil.putPdfTextField;

public final class ET3FormRespondentMapper {

    private ET3FormRespondentMapper() {
        // Add a private constructor to hide the implicit public one.
    }

    /**
     * Maps respondent values with PDF input fields.
     * @param respondentSumType respondent data selected by representative of respondent
     * @param pdfFields print fields that is created in ET3FormMapper
     */
    public static void mapRespondent(RespondentSumType respondentSumType,
                                     ConcurrentMap<String, Optional<String>> pdfFields) {
        // Setting Respondent Title
        String selectedTitle = respondentSumType.getEt3ResponseRespondentPreferredTitle();
        putPdfCheckboxFieldWhenExpectedValueEqualsActualValue(pdfFields,
                ET3FormConstants.CHECKBOX_PDF_RESPONDENT_FIELD_TITLE_MR,
                ET3FormConstants.YES_CAPITALISED,
                ET3FormConstants.CHECKBOX_PDF_RESPONDENT_EXPECTED_VALUE_TITLE_MR,
                selectedTitle);
        putPdfCheckboxFieldWhenExpectedValueEqualsActualValue(pdfFields,
                ET3FormConstants.CHECKBOX_PDF_RESPONDENT_FIELD_TITLE_MRS,
                ET3FormConstants.YES_CAPITALISED,
                ET3FormConstants.CHECKBOX_PDF_RESPONDENT_EXPECTED_VALUE_TITLE_MRS,
                selectedTitle);
        putPdfCheckboxFieldWhenExpectedValueEqualsActualValue(pdfFields,
                ET3FormConstants.CHECKBOX_PDF_RESPONDENT_FIELD_TITLE_MISS,
                ET3FormConstants.YES_CAPITALISED,
                ET3FormConstants.CHECKBOX_PDF_RESPONDENT_EXPECTED_VALUE_TITLE_MISS,
                selectedTitle);
        putPdfCheckboxFieldWhenExpectedValueEqualsActualValue(pdfFields,
                ET3FormConstants.CHECKBOX_PDF_RESPONDENT_FIELD_TITLE_MS,
                ET3FormConstants.YES_CAPITALISED,
                ET3FormConstants.CHECKBOX_PDF_RESPONDENT_EXPECTED_VALUE_TITLE_MS,
                selectedTitle);
        putOtherTitle(selectedTitle, pdfFields);
        // END OF SETTING RESPONDENT TITLE
        putPdfTextField(pdfFields, ET3FormConstants.TXT_PDF_RESPONDENT_FIELD_NAME,
                respondentSumType.getResponseRespondentName());
        putPdfTextField(pdfFields, ET3FormConstants.TXT_PDF_RESPONDENT_FIELD_NUMBER,
                respondentSumType.getEt3ResponseRespondentCompanyNumber());
        putPdfTextField(pdfFields, ET3FormConstants.TXT_PDF_RESPONDENT_FIELD_TYPE,
                respondentSumType.getEt3ResponseRespondentEmployerType());
        putPdfTextField(pdfFields, ET3FormConstants.TXT_PDF_RESPONDENT_FIELD_CONTACT_NAME,
                respondentSumType.getEt3ResponseRespondentContactName());
        putPdfAddressField(pdfFields,
                ET3FormConstants.TXT_PDF_RESPONDENT_FIELD_ADDRESS, respondentSumType.getResponseRespondentAddress());
        putPdfTextField(pdfFields, ET3FormConstants.TXT_PDF_RESPONDENT_FIELD_POSTCODE,
                ObjectUtils.isEmpty(respondentSumType.getResponseRespondentAddress())
                        ? ET3FormConstants.STRING_EMPTY
                        : respondentSumType.getResponseRespondentAddress().getPostCode());
        putPdfTextField(pdfFields, ET3FormConstants.TXT_PDF_RESPONDENT_FIELD_DX,
                respondentSumType.getEt3ResponseDXAddress());
        putPdfTextField(pdfFields, ET3FormConstants.TXT_PDF_RESPONDENT_FIELD_PHONE_NUMBER,
                StringUtils.isNotBlank(respondentSumType.getResponseRespondentPhone1())
                        ? respondentSumType.getResponseRespondentPhone1()
                        : respondentSumType.getResponseRespondentPhone2());
        putPdfTextField(pdfFields, ET3FormConstants.TXT_PDF_RESPONDENT_FIELD_MOBILE_NUMBER,
                respondentSumType.getResponseRespondentPhone2());
        putPdfCheckboxFieldWhenExpectedValueEqualsActualValue(pdfFields,
                ET3FormConstants.CHECKBOX_PDF_RESPONDENT_FIELD_CONTACT_TYPE_EMAIL,
                ET3FormConstants.EMAIL_LOWERCASE,
                ET3FormConstants.EMAIL_CAPITALISED,
                respondentSumType.getResponseRespondentContactPreference());
        putPdfCheckboxFieldWhenExpectedValueEqualsActualValue(pdfFields,
                ET3FormConstants.CHECKBOX_PDF_RESPONDENT_FIELD_CONTACT_TYPE_POST,
                ET3FormConstants.POST_LOWERCASE,
                ET3FormConstants.POST_CAPITALISED,
                respondentSumType.getResponseRespondentContactPreference());
        putPdfTextField(pdfFields, ET3FormConstants.TXT_PDF_RESPONDENT_FIELD_EMAIL,
                respondentSumType.getResponseRespondentEmail());
        putPdfCheckboxFieldWhenActualValueContainsExpectedValue(pdfFields,
                ET3FormConstants.CHECKBOX_PDF_RESPONDENT_FIELD_HEARING_TYPE_VIDEO,
                ET3FormConstants.YES_CAPITALISED,
                ET3FormConstants.VIDEO_HEARINGS,
                respondentSumType.getEt3ResponseHearingRespondent());
        putPdfCheckboxFieldWhenActualValueContainsExpectedValue(pdfFields,
                ET3FormConstants.CHECKBOX_PDF_RESPONDENT_FIELD_HEARING_TYPE_PHONE,
                ET3FormConstants.YES_CAPITALISED,
                ET3FormConstants.PHONE_HEARINGS,
                respondentSumType.getEt3ResponseHearingRespondent());
        putPdfTextField(pdfFields,
                ET3FormConstants.TXT_PDF_RESPONDENT_FIELD_EMPLOYEE_NUMBER_GREAT_BRITAIN,
                respondentSumType.getEt3ResponseEmploymentCount());
        putPdfCheckboxFieldWhenExpectedValueEqualsActualValue(pdfFields,
                ET3FormConstants.CHECKBOX_PDF_RESPONDENT_FIELD_MORE_THAN_ONE_SITE_GREAT_BRITAIN_YES,
                ET3FormConstants.YES_LOWERCASE,
                ET3FormConstants.YES_CAPITALISED,
                respondentSumType.getEt3ResponseMultipleSites());
        putPdfCheckboxFieldWhenExpectedValueEqualsActualValue(pdfFields,
                ET3FormConstants.CHECKBOX_PDF_RESPONDENT_FIELD_MORE_THAN_ONE_SITE_GREAT_BRITAIN_NO,
                ET3FormConstants.NO_LOWERCASE,
                ET3FormConstants.NO_CAPITALISED,
                respondentSumType.getEt3ResponseMultipleSites());
        putPdfTextField(pdfFields,
                ET3FormConstants.TXT_PDF_RESPONDENT_FIELD_EMPLOYEE_NUMBER_CLAIMANT_WORK_PLACE,
                respondentSumType.getEt3ResponseSiteEmploymentCount());

    }

    private static void putOtherTitle(String selectedTitle,
                                      ConcurrentMap<String, Optional<String>> pdfFields) {
        if (StringUtils.isBlank(selectedTitle)
                || ET3FormConstants.CHECKBOX_PDF_RESPONDENT_EXPECTED_VALUE_TITLE_MR.equalsIgnoreCase(selectedTitle)
                || ET3FormConstants.CHECKBOX_PDF_RESPONDENT_EXPECTED_VALUE_TITLE_MRS.equalsIgnoreCase(selectedTitle)
                || ET3FormConstants.CHECKBOX_PDF_RESPONDENT_EXPECTED_VALUE_TITLE_MISS.equalsIgnoreCase(selectedTitle)
                || ET3FormConstants.CHECKBOX_PDF_RESPONDENT_EXPECTED_VALUE_TITLE_MS.equalsIgnoreCase(selectedTitle)) {
            putPdfTextField(pdfFields, ET3FormConstants.TXT_PDF_RESPONDENT_FIELD_TITLE_OTHER,
                    ET3FormConstants.STRING_EMPTY);
            return;
        }
        putPdfCheckboxFieldWhenExpectedValueEqualsActualValue(pdfFields,
                ET3FormConstants.CHECKBOX_PDF_RESPONDENT_FIELD_TITLE_OTHER,
                ET3FormConstants.YES_CAPITALISED,
                selectedTitle,
                selectedTitle);
        putPdfTextField(pdfFields, ET3FormConstants.TXT_PDF_RESPONDENT_FIELD_TITLE_OTHER, selectedTitle);
    }

}
