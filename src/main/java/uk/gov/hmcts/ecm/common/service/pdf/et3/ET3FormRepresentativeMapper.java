package uk.gov.hmcts.ecm.common.service.pdf.et3;

import uk.gov.hmcts.et.common.model.ccd.CaseData;
import uk.gov.hmcts.et.common.model.ccd.types.RepresentedTypeR;
import uk.gov.hmcts.et.common.model.ccd.types.RespondentSumType;

import java.util.Optional;
import java.util.concurrent.ConcurrentMap;

import static org.apache.commons.lang3.ObjectUtils.isEmpty;
import static org.apache.commons.lang3.ObjectUtils.isNotEmpty;
import static uk.gov.hmcts.ecm.common.service.pdf.et3.ET3FormConstants.CHECKBOX_PDF_REPRESENTATIVE_FIELD_COMMUNICATION_PREFERENCE_EMAIL;
import static uk.gov.hmcts.ecm.common.service.pdf.et3.ET3FormConstants.CHECKBOX_PDF_REPRESENTATIVE_FIELD_COMMUNICATION_PREFERENCE_POST;
import static uk.gov.hmcts.ecm.common.service.pdf.et3.ET3FormConstants.CHECKBOX_PDF_REPRESENTATIVE_FIELD_PHONE_HEARINGS;
import static uk.gov.hmcts.ecm.common.service.pdf.et3.ET3FormConstants.CHECKBOX_PDF_REPRESENTATIVE_FIELD_VIDEO_HEARINGS;
import static uk.gov.hmcts.ecm.common.service.pdf.et3.ET3FormConstants.EMAIL_CAPITALISED;
import static uk.gov.hmcts.ecm.common.service.pdf.et3.ET3FormConstants.EMAIL_LOWERCASE;
import static uk.gov.hmcts.ecm.common.service.pdf.et3.ET3FormConstants.PHONE_HEARINGS;
import static uk.gov.hmcts.ecm.common.service.pdf.et3.ET3FormConstants.POST_CAPITALISED;
import static uk.gov.hmcts.ecm.common.service.pdf.et3.ET3FormConstants.POST_LOWERCASE;
import static uk.gov.hmcts.ecm.common.service.pdf.et3.ET3FormConstants.STRING_EMPTY;
import static uk.gov.hmcts.ecm.common.service.pdf.et3.ET3FormConstants.TXT_PDF_REPRESENTATIVE_FIELD_ADDRESS;
import static uk.gov.hmcts.ecm.common.service.pdf.et3.ET3FormConstants.TXT_PDF_REPRESENTATIVE_FIELD_EMAIL_ADDRESS;
import static uk.gov.hmcts.ecm.common.service.pdf.et3.ET3FormConstants.TXT_PDF_REPRESENTATIVE_FIELD_MOBILE_PHONE_NUMBER;
import static uk.gov.hmcts.ecm.common.service.pdf.et3.ET3FormConstants.TXT_PDF_REPRESENTATIVE_FIELD_NAME;
import static uk.gov.hmcts.ecm.common.service.pdf.et3.ET3FormConstants.TXT_PDF_REPRESENTATIVE_FIELD_ORGANISATION_NAME;
import static uk.gov.hmcts.ecm.common.service.pdf.et3.ET3FormConstants.TXT_PDF_REPRESENTATIVE_FIELD_PHONE_NUMBER;
import static uk.gov.hmcts.ecm.common.service.pdf.et3.ET3FormConstants.TXT_PDF_REPRESENTATIVE_FIELD_POSTCODE;
import static uk.gov.hmcts.ecm.common.service.pdf.et3.ET3FormConstants.TXT_PDF_REPRESENTATIVE_FIELD_REFERENCE_FOR_CORRESPONDENCE;
import static uk.gov.hmcts.ecm.common.service.pdf.et3.ET3FormConstants.VIDEO_HEARINGS;
import static uk.gov.hmcts.ecm.common.service.pdf.et3.ET3FormConstants.YES_CAPITALISED;
import static uk.gov.hmcts.ecm.common.service.pdf.et3.util.ET3FormUtil.putPdfAddressField;
import static uk.gov.hmcts.ecm.common.service.pdf.et3.util.ET3FormUtil.putPdfCheckboxFieldWhenActualValueContainsExpectedValue;
import static uk.gov.hmcts.ecm.common.service.pdf.et3.util.ET3FormUtil.putPdfCheckboxFieldWhenExpectedValueEqualsActualValue;
import static uk.gov.hmcts.ecm.common.service.pdf.et3.util.ET3FormUtil.putPdfTextField;
import static uk.gov.hmcts.ecm.common.service.pdf.et3.util.ET3MapperUtil.findRepresentativeFromCaseData;

public final class ET3FormRepresentativeMapper {

    private ET3FormRepresentativeMapper() {
        // Add a private constructor to hide the implicit public one.
    }

    /**
     * Maps ET3 Form Representative with PDF input fields.
     * @param caseData case data selected by representative of respondent
     * @param respondentSumType respondent data selected by representative of respondent
     * @param pdfFields print fields that is created in ET3FormMapper
     */
    public static void mapRepresentative(CaseData caseData,
                                         RespondentSumType respondentSumType,
                                         ConcurrentMap<String, Optional<String>> pdfFields) {
        RepresentedTypeR representative = findRepresentativeFromCaseData(caseData);
        if (isNotEmpty(representative)) {
            putPdfTextField(pdfFields, TXT_PDF_REPRESENTATIVE_FIELD_NAME, representative.getNameOfRepresentative());
            putPdfTextField(pdfFields, TXT_PDF_REPRESENTATIVE_FIELD_ORGANISATION_NAME,
                    representative.getNameOfOrganisation());
            putPdfAddressField(pdfFields, TXT_PDF_REPRESENTATIVE_FIELD_ADDRESS,
                    representative.getRepresentativeAddress());
            putPdfTextField(pdfFields, TXT_PDF_REPRESENTATIVE_FIELD_POSTCODE,
                    isEmpty(representative.getRepresentativeAddress()) ? STRING_EMPTY
                            : representative.getRepresentativeAddress().getPostCode());
            putPdfTextField(pdfFields, TXT_PDF_REPRESENTATIVE_FIELD_PHONE_NUMBER,
                    representative.getRepresentativePhoneNumber());
            putPdfTextField(pdfFields, TXT_PDF_REPRESENTATIVE_FIELD_MOBILE_PHONE_NUMBER,
                    representative.getRepresentativeMobileNumber());
            putPdfTextField(pdfFields, TXT_PDF_REPRESENTATIVE_FIELD_REFERENCE_FOR_CORRESPONDENCE,
                    representative.getRepresentativeReference());
            putPdfCheckboxFieldWhenExpectedValueEqualsActualValue(pdfFields,
                    CHECKBOX_PDF_REPRESENTATIVE_FIELD_COMMUNICATION_PREFERENCE_EMAIL, EMAIL_LOWERCASE,
                    EMAIL_CAPITALISED, representative.getRepresentativePreference());
            putPdfCheckboxFieldWhenExpectedValueEqualsActualValue(pdfFields,
                    CHECKBOX_PDF_REPRESENTATIVE_FIELD_COMMUNICATION_PREFERENCE_POST, POST_LOWERCASE, POST_CAPITALISED,
                    representative.getRepresentativePreference());
            putPdfTextField(pdfFields, TXT_PDF_REPRESENTATIVE_FIELD_EMAIL_ADDRESS,
                    representative.getRepresentativeEmailAddress());
            putPdfCheckboxFieldWhenActualValueContainsExpectedValue(pdfFields,
                    CHECKBOX_PDF_REPRESENTATIVE_FIELD_VIDEO_HEARINGS, YES_CAPITALISED, VIDEO_HEARINGS,
                    respondentSumType.getEt3ResponseHearingRepresentative());
            putPdfCheckboxFieldWhenActualValueContainsExpectedValue(pdfFields,
                    CHECKBOX_PDF_REPRESENTATIVE_FIELD_PHONE_HEARINGS, YES_CAPITALISED, PHONE_HEARINGS,
                    respondentSumType.getEt3ResponseHearingRepresentative());
        }
    }
}
