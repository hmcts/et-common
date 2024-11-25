package uk.gov.hmcts.ecm.common.service.pdf.et3;

import uk.gov.hmcts.et.common.model.ccd.types.RespondentSumType;

import java.util.Optional;
import java.util.concurrent.ConcurrentMap;

import static uk.gov.hmcts.ecm.common.service.pdf.et3.ET3FormConstants.CHECKBOX_PDF_RESPONSE_FIELD_CONTEST_CLAIM_NO;
import static uk.gov.hmcts.ecm.common.service.pdf.et3.ET3FormConstants.CHECKBOX_PDF_RESPONSE_FIELD_CONTEST_CLAIM_YES;
import static uk.gov.hmcts.ecm.common.service.pdf.et3.ET3FormConstants.NO_CAPITALISED;
import static uk.gov.hmcts.ecm.common.service.pdf.et3.ET3FormConstants.NO_LOWERCASE;
import static uk.gov.hmcts.ecm.common.service.pdf.et3.ET3FormConstants.TXT_PDF_RESPONSE_FIELD_CONTEST_CLAIM_CORRECT_FACTS;
import static uk.gov.hmcts.ecm.common.service.pdf.et3.ET3FormConstants.YES_CAPITALISED;
import static uk.gov.hmcts.ecm.common.service.pdf.et3.ET3FormConstants.YES_LOWERCASE;
import static uk.gov.hmcts.ecm.common.service.pdf.et3.util.ET3FormUtil.putConditionalPdfField;
import static uk.gov.hmcts.ecm.common.service.pdf.et3.util.ET3FormUtil.putPdfCheckboxFieldWhenExpectedValueEqualsActualValue;

public final class ET3FormResponseMapper {

    private ET3FormResponseMapper() {
        // Add a private constructor to hide the implicit public one.
    }

    /**
     * Maps ET3 Form Response with PDF input fields.
     * @param respondentSumType respondent data selected by representative of respondent
     * @param pdfFields print fields that is created in ET3FormMapper
     */
    public static void mapResponse(RespondentSumType respondentSumType,
                                   ConcurrentMap<String, Optional<String>> pdfFields) {
        putPdfCheckboxFieldWhenExpectedValueEqualsActualValue(pdfFields, CHECKBOX_PDF_RESPONSE_FIELD_CONTEST_CLAIM_YES,
                YES_LOWERCASE, YES_CAPITALISED, respondentSumType.getEt3ResponseRespondentContestClaim());
        putPdfCheckboxFieldWhenExpectedValueEqualsActualValue(pdfFields, CHECKBOX_PDF_RESPONSE_FIELD_CONTEST_CLAIM_NO,
                NO_LOWERCASE, NO_CAPITALISED, respondentSumType.getEt3ResponseRespondentContestClaim());
        putConditionalPdfField(pdfFields, TXT_PDF_RESPONSE_FIELD_CONTEST_CLAIM_CORRECT_FACTS, YES_CAPITALISED,
                respondentSumType.getEt3ResponseRespondentContestClaim(),
                respondentSumType.getEt3ResponseContestClaimDetails());
    }

}
