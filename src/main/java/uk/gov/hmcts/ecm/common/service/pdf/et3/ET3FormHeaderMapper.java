package uk.gov.hmcts.ecm.common.service.pdf.et3;

import org.springframework.util.CollectionUtils;
import uk.gov.hmcts.et.common.model.ccd.CaseData;
import uk.gov.hmcts.et.common.model.ccd.types.RespondentSumType;

import java.time.LocalDate;
import java.util.Optional;
import java.util.concurrent.ConcurrentMap;

import static org.apache.commons.lang3.ObjectUtils.isEmpty;
import static uk.gov.hmcts.ecm.common.service.pdf.et3.ET3FormConstants.DATE_FORMAT_DD_MM_YYYY_DASH;
import static uk.gov.hmcts.ecm.common.service.pdf.et3.ET3FormConstants.DATE_FORMAT_YYYY_MM_DD_DASH;
import static uk.gov.hmcts.ecm.common.service.pdf.et3.ET3FormConstants.SUBMIT_ET3;
import static uk.gov.hmcts.ecm.common.service.pdf.et3.ET3FormConstants.SUBMIT_ET3_CITIZEN;
import static uk.gov.hmcts.ecm.common.service.pdf.et3.ET3FormConstants.TXT_PDF_HEADER_FIELD_CASE_NUMBER;
import static uk.gov.hmcts.ecm.common.service.pdf.et3.ET3FormConstants.TXT_PDF_HEADER_FIELD_DATE_RECEIVED;
import static uk.gov.hmcts.ecm.common.service.pdf.et3.ET3FormConstants.TXT_PDF_HEADER_FIELD_RTF;
import static uk.gov.hmcts.ecm.common.service.pdf.et3.ET3FormConstants.TXT_PDF_HEADER_VALUE_ADDITIONAL_DOCUMENT_EXISTS;
import static uk.gov.hmcts.ecm.common.service.pdf.et3.util.ET3FormUtil.formatDate;
import static uk.gov.hmcts.ecm.common.service.pdf.et3.util.ET3FormUtil.putPdfTextField;

public final class ET3FormHeaderMapper {

    private ET3FormHeaderMapper() {
        // Add a private constructor to hide the implicit public one.
    }

    /**
     * Maps header values (case number, case received date, RFT) with PDF input fields.
     * @param caseData case data that is received by case number
     * @param respondentSumType respondent data selected by representative of respondent
     * @param pdfFields print fields that is created in ET3FormMapper
     */
    public static void mapHeader(CaseData caseData,
                                 RespondentSumType respondentSumType,
                                 ConcurrentMap<String, Optional<String>> pdfFields,
                                 String event) {
        putPdfTextField(pdfFields, TXT_PDF_HEADER_FIELD_CASE_NUMBER, caseData.getEthosCaseReference());
        if (SUBMIT_ET3.equals(event) || SUBMIT_ET3_CITIZEN.equals(event)) {
            putPdfTextField(pdfFields, TXT_PDF_HEADER_FIELD_DATE_RECEIVED,
                    formatDate(LocalDate.now().toString(), DATE_FORMAT_YYYY_MM_DD_DASH, DATE_FORMAT_DD_MM_YYYY_DASH));
        }
        int numberOfDocumentsAttached = getNumberOfDocumentsAttached(respondentSumType);
        putPdfTextField(pdfFields, TXT_PDF_HEADER_FIELD_RTF,
               numberOfDocumentsAttached == 0
                ? null
                : String.format(TXT_PDF_HEADER_VALUE_ADDITIONAL_DOCUMENT_EXISTS, numberOfDocumentsAttached));
    }

    private static int getNumberOfDocumentsAttached(RespondentSumType respondentSumType) {
        int numberOfDocumentsAttached = 0;
        if (!CollectionUtils.isEmpty(respondentSumType.getEt3ResponseContestClaimDocument())) {
            numberOfDocumentsAttached += respondentSumType.getEt3ResponseContestClaimDocument().size();
        }
        if (!isEmpty(respondentSumType.getEt3ResponseEmployerClaimDocument())) {
            numberOfDocumentsAttached++;
        }
        if (!isEmpty(respondentSumType.getEt3ResponseRespondentSupportDocument())) {
            numberOfDocumentsAttached++;
        }

        return numberOfDocumentsAttached;
    }

}
