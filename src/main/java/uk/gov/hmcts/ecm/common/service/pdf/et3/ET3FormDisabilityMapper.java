package uk.gov.hmcts.ecm.common.service.pdf.et3;

import uk.gov.hmcts.et.common.model.ccd.types.RespondentSumType;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.ConcurrentMap;

import static uk.gov.hmcts.ecm.common.service.pdf.et3.ET3FormConstants.CHECKBOX_PDF_DISABILITY_NO;
import static uk.gov.hmcts.ecm.common.service.pdf.et3.ET3FormConstants.CHECKBOX_PDF_DISABILITY_NOT_SURE;
import static uk.gov.hmcts.ecm.common.service.pdf.et3.ET3FormConstants.CHECKBOX_PDF_DISABILITY_YES;
import static uk.gov.hmcts.ecm.common.service.pdf.et3.ET3FormConstants.NO_CAPITALISED;
import static uk.gov.hmcts.ecm.common.service.pdf.et3.ET3FormConstants.NO_LOWERCASE;
import static uk.gov.hmcts.ecm.common.service.pdf.et3.ET3FormConstants.TXT_PDF_DISABILITY_DETAILS;
import static uk.gov.hmcts.ecm.common.service.pdf.et3.ET3FormConstants.YES_CAPITALISED;
import static uk.gov.hmcts.ecm.common.service.pdf.et3.ET3FormConstants.YES_LOWERCASE;
import static uk.gov.hmcts.ecm.common.service.pdf.et3.util.ET3FormUtil.putConditionalPdfField;
import static uk.gov.hmcts.ecm.common.service.pdf.et3.util.ET3FormUtil.putPdfCheckboxFieldWhenExpectedValueEqualsActualValue;
import static uk.gov.hmcts.ecm.common.service.pdf.et3.util.ET3FormUtil.putPdfCheckboxFieldWhenOther;

public final class ET3FormDisabilityMapper {

    private ET3FormDisabilityMapper() {
        // Add a private constructor to hide the implicit public one.
    }

    /**
     * Maps ET3 Form Disability data with PDF input fields.
     * @param respondentSumType respondent data selected by representative of respondent
     * @param pdfFields print fields that is created in ET3FormMapper
     */
    public static void mapDisability(RespondentSumType respondentSumType,
                                     ConcurrentMap<String, Optional<String>> pdfFields) {
        putPdfCheckboxFieldWhenExpectedValueEqualsActualValue(pdfFields,
                CHECKBOX_PDF_DISABILITY_YES, YES_LOWERCASE, YES_CAPITALISED,
                respondentSumType.getEt3ResponseRespondentSupportNeeded());
        putPdfCheckboxFieldWhenExpectedValueEqualsActualValue(pdfFields,
                CHECKBOX_PDF_DISABILITY_NO, NO_LOWERCASE, NO_CAPITALISED,
                respondentSumType.getEt3ResponseRespondentSupportNeeded());
        putPdfCheckboxFieldWhenOther(pdfFields, CHECKBOX_PDF_DISABILITY_NOT_SURE, NO_LOWERCASE,
                List.of(YES_CAPITALISED, NO_CAPITALISED), respondentSumType.getEt3ResponseRespondentSupportNeeded());
        putConditionalPdfField(pdfFields, TXT_PDF_DISABILITY_DETAILS, YES_CAPITALISED,
                respondentSumType.getEt3ResponseRespondentSupportNeeded(),
                respondentSumType.getEt3ResponseRespondentSupportDetails());
    }
}
