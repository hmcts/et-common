package uk.gov.hmcts.ecm.common.service.pdf.et3;

import uk.gov.hmcts.et.common.model.ccd.types.RespondentSumType;

import java.util.Optional;
import java.util.concurrent.ConcurrentMap;

import static uk.gov.hmcts.ecm.common.service.pdf.et3.ET3FormConstants.CHECKBOX_PDF_EMPLOYER_CONTRACT_CLAIM_FIELD_YES;
import static uk.gov.hmcts.ecm.common.service.pdf.et3.ET3FormConstants.TXT_PDF_EMPLOYER_CONTRACT_CLAIM_FIELD_DETAILS;
import static uk.gov.hmcts.ecm.common.service.pdf.et3.ET3FormConstants.YES_CAPITALISED;
import static uk.gov.hmcts.ecm.common.service.pdf.et3.ET3FormConstants.YES_LOWERCASE;
import static uk.gov.hmcts.ecm.common.service.pdf.et3.util.ET3FormUtil.putConditionalPdfField;
import static uk.gov.hmcts.ecm.common.service.pdf.et3.util.ET3FormUtil.putPdfCheckboxFieldWhenExpectedValueEqualsActualValue;

public final class ET3FormEmployerContractClaimMapper {

    private ET3FormEmployerContractClaimMapper() {
        // Add a private constructor to hide the implicit public one.
    }

    /**
     * Maps ET3 Form Employer's contract claim with PDF input fields.
     * @param respondentSumType respondent data selected by representative of respondent
     * @param pdfFields print fields that is created in ET3FormMapper
     */
    public static void mapEmployerContractClaim(RespondentSumType respondentSumType,
                                   ConcurrentMap<String, Optional<String>> pdfFields) {
        putPdfCheckboxFieldWhenExpectedValueEqualsActualValue(pdfFields, CHECKBOX_PDF_EMPLOYER_CONTRACT_CLAIM_FIELD_YES,
                YES_LOWERCASE, YES_CAPITALISED, respondentSumType.getEt3ResponseEmployerClaim());
        putConditionalPdfField(pdfFields, TXT_PDF_EMPLOYER_CONTRACT_CLAIM_FIELD_DETAILS, YES_CAPITALISED,
                respondentSumType.getEt3ResponseEmployerClaim(),
                respondentSumType.getEt3ResponseEmployerClaimDetails());
    }

}
