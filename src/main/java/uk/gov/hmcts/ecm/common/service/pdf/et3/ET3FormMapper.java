package uk.gov.hmcts.ecm.common.service.pdf.et3;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;
import uk.gov.hmcts.ecm.common.service.pdf.et3.util.GenericServiceException;
import uk.gov.hmcts.et.common.model.ccd.CaseData;
import uk.gov.hmcts.et.common.model.ccd.items.RespondentSumTypeItem;
import uk.gov.hmcts.et.common.model.ccd.types.RespondentSumType;

import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.stream.Stream;

import static uk.gov.hmcts.ecm.common.service.pdf.et3.ET3FormAcasMapper.mapAcas;
import static uk.gov.hmcts.ecm.common.service.pdf.et3.ET3FormClaimantMapper.mapClaimant;
import static uk.gov.hmcts.ecm.common.service.pdf.et3.ET3FormConstants.CASE_DATA_NOT_FOUND_EXCEPTION_FIRST_WORD;
import static uk.gov.hmcts.ecm.common.service.pdf.et3.ET3FormConstants.CASE_DATA_NOT_FOUND_EXCEPTION_MESSAGE;
import static uk.gov.hmcts.ecm.common.service.pdf.et3.ET3FormConstants.ET3_FORM_CASE_DATA_CHECK_METHOD_NAME;
import static uk.gov.hmcts.ecm.common.service.pdf.et3.ET3FormConstants.ET3_FORM_CLIENT_TYPE_REPRESENTATIVE;
import static uk.gov.hmcts.ecm.common.service.pdf.et3.ET3FormConstants.ET3_FORM_CLIENT_TYPE_RESPONDENT;
import static uk.gov.hmcts.ecm.common.service.pdf.et3.ET3FormConstants.ET3_FORM_MAPPER_CLASS_NAME;
import static uk.gov.hmcts.ecm.common.service.pdf.et3.ET3FormConstants.ET3_FORM_MAPPER_METHOD_NAME;
import static uk.gov.hmcts.ecm.common.service.pdf.et3.ET3FormConstants.RESPONDENT_COLLECTION_NOT_FOUND_EXCEPTION_FIRST_WORD;
import static uk.gov.hmcts.ecm.common.service.pdf.et3.ET3FormConstants.RESPONDENT_COLLECTION_NOT_FOUND_EXCEPTION_MESSAGE;
import static uk.gov.hmcts.ecm.common.service.pdf.et3.ET3FormConstants.RESPONDENT_NAME_NOT_FOUND_IN_CASE_DATA_EXCEPTION_MESSAGE;
import static uk.gov.hmcts.ecm.common.service.pdf.et3.ET3FormConstants.RESPONDENT_NOT_FOUND_EXCEPTION_FIRST_WORD;
import static uk.gov.hmcts.ecm.common.service.pdf.et3.ET3FormConstants.RESPONDENT_NOT_FOUND_IN_CASE_DATA_EXCEPTION_MESSAGE;
import static uk.gov.hmcts.ecm.common.service.pdf.et3.ET3FormConstants.RESPONDENT_NOT_FOUND_IN_RESPONDENT_COLLECTION_EXCEPTION_MESSAGE;
import static uk.gov.hmcts.ecm.common.service.pdf.et3.ET3FormDisabilityMapper.mapDisability;
import static uk.gov.hmcts.ecm.common.service.pdf.et3.ET3FormEarningsAndBenefitsMapper.mapEarningsAndBenefits;
import static uk.gov.hmcts.ecm.common.service.pdf.et3.ET3FormEmployerContractClaimMapper.mapEmployerContractClaim;
import static uk.gov.hmcts.ecm.common.service.pdf.et3.ET3FormEmploymentMapper.mapEmployment;
import static uk.gov.hmcts.ecm.common.service.pdf.et3.ET3FormHeaderMapper.mapHeader;
import static uk.gov.hmcts.ecm.common.service.pdf.et3.ET3FormRepresentativeMapper.mapRepresentative;
import static uk.gov.hmcts.ecm.common.service.pdf.et3.ET3FormRespondentMapper.mapRespondent;
import static uk.gov.hmcts.ecm.common.service.pdf.et3.ET3FormResponseMapper.mapResponse;

/**
 * Service to support ET3 Response journey. Contains methods for generating and saving ET3 Response documents.
 */
@Slf4j
public final class ET3FormMapper {

    private ET3FormMapper() {
        // Add a private constructor to hide the implicit public one.
    }

    public static Map<String, Optional<String>> mapEt3Form(CaseData caseData, String event, String clientType)
            throws GenericServiceException {
        checkCaseData(caseData);
        Stream<RespondentSumTypeItem> respondentSumTypeStream = getRespondentSumTypeItemStream(caseData, clientType);
        if (ObjectUtils.isEmpty(respondentSumTypeStream)) {
            Throwable throwable = new Exception(
                    RESPONDENT_NOT_FOUND_IN_RESPONDENT_COLLECTION_EXCEPTION_MESSAGE);
            throw new GenericServiceException(RESPONDENT_NOT_FOUND_IN_RESPONDENT_COLLECTION_EXCEPTION_MESSAGE,
                    throwable, RESPONDENT_NOT_FOUND_EXCEPTION_FIRST_WORD
                    + caseData.getSubmitEt3Respondent().getSelectedLabel(), caseData.getEthosCaseReference(),
                    ET3_FORM_MAPPER_CLASS_NAME, ET3_FORM_MAPPER_METHOD_NAME);
        }
        Optional<RespondentSumTypeItem> selectedRespondent = respondentSumTypeStream.findFirst();
        if (ObjectUtils.isEmpty(selectedRespondent)
                || selectedRespondent.isEmpty()
                || ObjectUtils.isEmpty(selectedRespondent.get())
                || ObjectUtils.isEmpty(selectedRespondent.get().getValue())) {
            Throwable throwable = new Exception(
                    RESPONDENT_NOT_FOUND_IN_RESPONDENT_COLLECTION_EXCEPTION_MESSAGE);
            throw new GenericServiceException(RESPONDENT_NOT_FOUND_IN_RESPONDENT_COLLECTION_EXCEPTION_MESSAGE,
                    throwable, RESPONDENT_NOT_FOUND_EXCEPTION_FIRST_WORD
                    + caseData.getSubmitEt3Respondent().getSelectedLabel(), caseData.getEthosCaseReference(),
                    ET3_FORM_MAPPER_CLASS_NAME, ET3_FORM_MAPPER_METHOD_NAME);
        }
        ConcurrentMap<String, Optional<String>> pdfFields = new ConcurrentHashMap<>();
        RespondentSumType respondentSumType = selectedRespondent.get().getValue();
        mapHeader(caseData, respondentSumType, pdfFields, event);
        mapClaimant(caseData, respondentSumType, pdfFields);
        mapRespondent(respondentSumType, pdfFields);
        mapAcas(respondentSumType, pdfFields);
        mapEmployment(respondentSumType, pdfFields);
        mapEarningsAndBenefits(respondentSumType, pdfFields);
        mapResponse(respondentSumType, pdfFields);
        mapEmployerContractClaim(respondentSumType, pdfFields);
        if (ET3_FORM_CLIENT_TYPE_REPRESENTATIVE.equals(clientType)) {
            mapRepresentative(caseData, respondentSumType, pdfFields);
        }
        mapDisability(respondentSumType, pdfFields);
        return pdfFields;
    }

    private static Stream<RespondentSumTypeItem> getRespondentSumTypeItemStream(CaseData caseData, String clientType) {
        String submitRespondent = caseData.getSubmitEt3Respondent().getSelectedLabel();
        Stream<RespondentSumTypeItem> respondentSumTypeStream = null;
        if (ET3_FORM_CLIENT_TYPE_REPRESENTATIVE.equals(clientType)) {
            respondentSumTypeStream = caseData.getRespondentCollection().stream()
                    .filter(r -> submitRespondent.equals(r.getValue().getRespondentName()));
        }
        if (ET3_FORM_CLIENT_TYPE_RESPONDENT.equals(clientType)) {
            respondentSumTypeStream = caseData.getRespondentCollection().stream()
                    .filter(r -> submitRespondent.equals(r.getId()));
        }
        return respondentSumTypeStream;
    }

    private static void checkCaseData(CaseData caseData) throws GenericServiceException {
        if (ObjectUtils.isEmpty(caseData)) {
            Throwable throwable = new Exception(CASE_DATA_NOT_FOUND_EXCEPTION_MESSAGE);
            throw new GenericServiceException(CASE_DATA_NOT_FOUND_EXCEPTION_MESSAGE, throwable,
                    CASE_DATA_NOT_FOUND_EXCEPTION_FIRST_WORD, CASE_DATA_NOT_FOUND_EXCEPTION_MESSAGE,
                    ET3_FORM_MAPPER_CLASS_NAME, ET3_FORM_MAPPER_METHOD_NAME);
        }
        if (CollectionUtils.isEmpty(caseData.getRespondentCollection())) {
            Throwable throwable = new Exception(RESPONDENT_COLLECTION_NOT_FOUND_EXCEPTION_MESSAGE);
            throw new GenericServiceException(RESPONDENT_COLLECTION_NOT_FOUND_EXCEPTION_MESSAGE, throwable,
                    RESPONDENT_COLLECTION_NOT_FOUND_EXCEPTION_FIRST_WORD, caseData.getEthosCaseReference(),
                    ET3_FORM_MAPPER_CLASS_NAME, ET3_FORM_CASE_DATA_CHECK_METHOD_NAME);
        }
        checkCaseDataSubmitRespondentCollection(caseData);
    }

    private static void checkCaseDataSubmitRespondentCollection(CaseData caseData) throws GenericServiceException {
        if (ObjectUtils.isEmpty(caseData.getSubmitEt3Respondent())
                || ObjectUtils.isEmpty(caseData.getSubmitEt3Respondent().getValue())) {
            Throwable throwable = new Exception(RESPONDENT_NOT_FOUND_IN_CASE_DATA_EXCEPTION_MESSAGE);
            throw new GenericServiceException(RESPONDENT_NOT_FOUND_IN_CASE_DATA_EXCEPTION_MESSAGE, throwable,
                    RESPONDENT_NOT_FOUND_IN_CASE_DATA_EXCEPTION_MESSAGE, caseData.getEthosCaseReference(),
                    ET3_FORM_MAPPER_CLASS_NAME, ET3_FORM_CASE_DATA_CHECK_METHOD_NAME);
        }
        if (StringUtils.isBlank(caseData.getSubmitEt3Respondent().getSelectedLabel())) {
            Throwable throwable = new Exception(RESPONDENT_NAME_NOT_FOUND_IN_CASE_DATA_EXCEPTION_MESSAGE);
            throw new GenericServiceException(RESPONDENT_NAME_NOT_FOUND_IN_CASE_DATA_EXCEPTION_MESSAGE, throwable,
                    RESPONDENT_NAME_NOT_FOUND_IN_CASE_DATA_EXCEPTION_MESSAGE, caseData.getEthosCaseReference(),
                    ET3_FORM_MAPPER_CLASS_NAME, ET3_FORM_CASE_DATA_CHECK_METHOD_NAME);
        }
    }

}
