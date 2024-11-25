package uk.gov.hmcts.ecm.common.service.pdf.et3;

import uk.gov.hmcts.et.common.model.ccd.CaseData;
import uk.gov.hmcts.et.common.model.ccd.types.RespondentSumType;

import java.util.Optional;
import java.util.concurrent.ConcurrentMap;

import static org.apache.commons.lang3.ObjectUtils.isEmpty;
import static uk.gov.hmcts.ecm.common.service.pdf.et3.ET3FormConstants.TXT_PDF_CLAIMANT_FIELD_NAME;
import static uk.gov.hmcts.ecm.common.service.pdf.et3.util.ET3FormUtil.putPdfTextField;

public final class ET3FormClaimantMapper {

    private ET3FormClaimantMapper() {
        // Add a private constructor to hide the implicit public one.
    }

    /**
     * Maps claimant values (claimant name) with PDF input fields.
     * @param caseData case data that is received by case number
     * @param respondentSumType respondent data selected by representative of respondent
     * @param pdfFields print fields that is created in ET3FormMapper
     */
    public static void mapClaimant(CaseData caseData,
                                   RespondentSumType respondentSumType,
                                   ConcurrentMap<String, Optional<String>> pdfFields) {
        putPdfTextField(pdfFields, TXT_PDF_CLAIMANT_FIELD_NAME,
                isEmpty(respondentSumType.getEt3ResponseClaimantNameCorrection())
                        ? caseData.getClaimant() : respondentSumType.getEt3ResponseClaimantNameCorrection());
    }

}
