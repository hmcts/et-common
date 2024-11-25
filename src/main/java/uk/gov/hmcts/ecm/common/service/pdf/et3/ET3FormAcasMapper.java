package uk.gov.hmcts.ecm.common.service.pdf.et3;

import uk.gov.hmcts.et.common.model.ccd.types.RespondentSumType;

import java.util.Optional;
import java.util.concurrent.ConcurrentMap;

import static uk.gov.hmcts.ecm.common.service.pdf.et3.ET3FormConstants.CHECKBOX_PDF_ACAS_FIELD_AGREEMENT_NO;
import static uk.gov.hmcts.ecm.common.service.pdf.et3.ET3FormConstants.CHECKBOX_PDF_ACAS_FIELD_AGREEMENT_YES;
import static uk.gov.hmcts.ecm.common.service.pdf.et3.ET3FormConstants.NO_CAPITALISED;
import static uk.gov.hmcts.ecm.common.service.pdf.et3.ET3FormConstants.TXT_PDF_ACAS_FIELD_AGREEMENT_NO_REASON;
import static uk.gov.hmcts.ecm.common.service.pdf.et3.ET3FormConstants.YES_CAPITALISED;
import static uk.gov.hmcts.ecm.common.service.pdf.et3.util.ET3FormUtil.putConditionalPdfField;
import static uk.gov.hmcts.ecm.common.service.pdf.et3.util.ET3FormUtil.putPdfCheckboxFieldWhenExpectedValueEqualsActualValue;

public final class ET3FormAcasMapper {

    private ET3FormAcasMapper() {
        // Add a private constructor to hide the implicit public one.
    }

    /**
     * Maps acas values with PDF input fields.
     * @param respondentSumType respondent data selected by representative of respondent
     * @param pdfFields print fields that is created in ET3FormMapper
     */
    public static void mapAcas(RespondentSumType respondentSumType,
                               ConcurrentMap<String, Optional<String>> pdfFields) {

        putPdfCheckboxFieldWhenExpectedValueEqualsActualValue(pdfFields,
                CHECKBOX_PDF_ACAS_FIELD_AGREEMENT_YES,
                YES_CAPITALISED,
                YES_CAPITALISED,
                respondentSumType.getEt3ResponseAcasAgree());
        putPdfCheckboxFieldWhenExpectedValueEqualsActualValue(pdfFields,
                CHECKBOX_PDF_ACAS_FIELD_AGREEMENT_NO,
                NO_CAPITALISED,
                NO_CAPITALISED,
                respondentSumType.getEt3ResponseAcasAgree());
        putConditionalPdfField(pdfFields, TXT_PDF_ACAS_FIELD_AGREEMENT_NO_REASON, NO_CAPITALISED,
                respondentSumType.getEt3ResponseAcasAgree(), respondentSumType.getEt3ResponseAcasAgreeReason());
    }
}
