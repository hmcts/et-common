package uk.gov.hmcts.ecm.common.helpers;

import uk.gov.hmcts.et.common.model.ccd.types.DocumentType;

import static com.google.common.base.Strings.isNullOrEmpty;
import static uk.gov.hmcts.ecm.common.model.helper.DocumentConstants.ACAS_CERTIFICATE;
import static uk.gov.hmcts.ecm.common.model.helper.DocumentConstants.ANONYMITY_ORDER;
import static uk.gov.hmcts.ecm.common.model.helper.DocumentConstants.APP_FOR_A_JUDGMENT_TO_BE_RECONSIDERED_C;
import static uk.gov.hmcts.ecm.common.model.helper.DocumentConstants.APP_FOR_A_JUDGMENT_TO_BE_RECONSIDERED_R;
import static uk.gov.hmcts.ecm.common.model.helper.DocumentConstants.APP_FOR_A_WITNESS_ORDER;
import static uk.gov.hmcts.ecm.common.model.helper.DocumentConstants.APP_TO_AMEND_CLAIM;
import static uk.gov.hmcts.ecm.common.model.helper.DocumentConstants.APP_TO_AMEND_RESPONSE;
import static uk.gov.hmcts.ecm.common.model.helper.DocumentConstants.APP_TO_EXTEND_TIME_TO_COMPLY_TO_AN_ORDER_DIRECTIONS_C;
import static uk.gov.hmcts.ecm.common.model.helper.DocumentConstants.APP_TO_EXTEND_TIME_TO_COMPLY_TO_AN_ORDER_DIRECTIONS_R;
import static uk.gov.hmcts.ecm.common.model.helper.DocumentConstants.APP_TO_EXTEND_TIME_TO_PRESENT_A_RESPONSE;
import static uk.gov.hmcts.ecm.common.model.helper.DocumentConstants.APP_TO_HAVE_A_LEGAL_OFFICER_DECISION_CONSIDERED_AFRESH_C;
import static uk.gov.hmcts.ecm.common.model.helper.DocumentConstants.APP_TO_HAVE_A_LEGAL_OFFICER_DECISION_CONSIDERED_AFRESH_R;
import static uk.gov.hmcts.ecm.common.model.helper.DocumentConstants.APP_TO_ORDER_THE_C_TO_DO_SOMETHING;
import static uk.gov.hmcts.ecm.common.model.helper.DocumentConstants.APP_TO_ORDER_THE_R_TO_DO_SOMETHING;
import static uk.gov.hmcts.ecm.common.model.helper.DocumentConstants.APP_TO_POSTPONE_C;
import static uk.gov.hmcts.ecm.common.model.helper.DocumentConstants.APP_TO_POSTPONE_R;
import static uk.gov.hmcts.ecm.common.model.helper.DocumentConstants.APP_TO_RESTRICT_PUBLICITY_C;
import static uk.gov.hmcts.ecm.common.model.helper.DocumentConstants.APP_TO_RESTRICT_PUBLICITY_R;
import static uk.gov.hmcts.ecm.common.model.helper.DocumentConstants.APP_TO_REVOKE_AN_ORDER_C;
import static uk.gov.hmcts.ecm.common.model.helper.DocumentConstants.APP_TO_REVOKE_AN_ORDER_R;
import static uk.gov.hmcts.ecm.common.model.helper.DocumentConstants.APP_TO_STRIKE_OUT_ALL_OR_PART_OF_THE_CLAIM;
import static uk.gov.hmcts.ecm.common.model.helper.DocumentConstants.APP_TO_STRIKE_OUT_ALL_OR_PART_OF_THE_RESPONSE;
import static uk.gov.hmcts.ecm.common.model.helper.DocumentConstants.APP_TO_VARY_AN_ORDER_C;
import static uk.gov.hmcts.ecm.common.model.helper.DocumentConstants.APP_TO_VARY_AN_ORDER_R;
import static uk.gov.hmcts.ecm.common.model.helper.DocumentConstants.CASE_MANAGEMENT;
import static uk.gov.hmcts.ecm.common.model.helper.DocumentConstants.CERTIFICATE_OF_CORRECTION;
import static uk.gov.hmcts.ecm.common.model.helper.DocumentConstants.CHANGE_OF_PARTYS_DETAILS;
import static uk.gov.hmcts.ecm.common.model.helper.DocumentConstants.CLAIM_ACCEPTED;
import static uk.gov.hmcts.ecm.common.model.helper.DocumentConstants.CLAIM_PART_REJECTED;
import static uk.gov.hmcts.ecm.common.model.helper.DocumentConstants.CLAIM_REJECTED;
import static uk.gov.hmcts.ecm.common.model.helper.DocumentConstants.COT3;
import static uk.gov.hmcts.ecm.common.model.helper.DocumentConstants.COUNTER_SCHEDULE_OF_LOSS;
import static uk.gov.hmcts.ecm.common.model.helper.DocumentConstants.C_HAS_NOT_COMPLIED_WITH_AN_ORDER_R;
import static uk.gov.hmcts.ecm.common.model.helper.DocumentConstants.DEPOSIT_ORDER;
import static uk.gov.hmcts.ecm.common.model.helper.DocumentConstants.DISABILITY_IMPACT_STATEMENT;
import static uk.gov.hmcts.ecm.common.model.helper.DocumentConstants.ET1;
import static uk.gov.hmcts.ecm.common.model.helper.DocumentConstants.ET1_ATTACHMENT;
import static uk.gov.hmcts.ecm.common.model.helper.DocumentConstants.ET1_VETTING;
import static uk.gov.hmcts.ecm.common.model.helper.DocumentConstants.ET3;
import static uk.gov.hmcts.ecm.common.model.helper.DocumentConstants.ET3_ATTACHMENT;
import static uk.gov.hmcts.ecm.common.model.helper.DocumentConstants.ET3_PROCESSING;
import static uk.gov.hmcts.ecm.common.model.helper.DocumentConstants.EXTRACT_OF_JUDGMENT;
import static uk.gov.hmcts.ecm.common.model.helper.DocumentConstants.HEARINGS;
import static uk.gov.hmcts.ecm.common.model.helper.DocumentConstants.HEARING_BUNDLE;
import static uk.gov.hmcts.ecm.common.model.helper.DocumentConstants.INITIAL_CONSIDERATION;
import static uk.gov.hmcts.ecm.common.model.helper.DocumentConstants.JUDGMENT;
import static uk.gov.hmcts.ecm.common.model.helper.DocumentConstants.JUDGMENT_AND_REASONS;
import static uk.gov.hmcts.ecm.common.model.helper.DocumentConstants.JUDGMENT_WITH_REASONS;
import static uk.gov.hmcts.ecm.common.model.helper.DocumentConstants.LEGACY_DOCUMENT_NAMES;
import static uk.gov.hmcts.ecm.common.model.helper.DocumentConstants.MISC;
import static uk.gov.hmcts.ecm.common.model.helper.DocumentConstants.NOTICE_OF_CLAIM;
import static uk.gov.hmcts.ecm.common.model.helper.DocumentConstants.NOTICE_OF_HEARING;
import static uk.gov.hmcts.ecm.common.model.helper.DocumentConstants.OTHER;
import static uk.gov.hmcts.ecm.common.model.helper.DocumentConstants.REASONS;
import static uk.gov.hmcts.ecm.common.model.helper.DocumentConstants.RECONSIDERATION;
import static uk.gov.hmcts.ecm.common.model.helper.DocumentConstants.REFERRAL_JUDICIAL_DIRECTION;
import static uk.gov.hmcts.ecm.common.model.helper.DocumentConstants.RESPONSE_ACCEPTED;
import static uk.gov.hmcts.ecm.common.model.helper.DocumentConstants.RESPONSE_REJECTED;
import static uk.gov.hmcts.ecm.common.model.helper.DocumentConstants.RESPONSE_TO_A_CLAIM;
import static uk.gov.hmcts.ecm.common.model.helper.DocumentConstants.RULE_27_NOTICE;
import static uk.gov.hmcts.ecm.common.model.helper.DocumentConstants.RULE_28_NOTICE;
import static uk.gov.hmcts.ecm.common.model.helper.DocumentConstants.R_HAS_NOT_COMPLIED_WITH_AN_ORDER_C;
import static uk.gov.hmcts.ecm.common.model.helper.DocumentConstants.SCHEDULE_OF_LOSS;
import static uk.gov.hmcts.ecm.common.model.helper.DocumentConstants.STARTING_A_CLAIM;
import static uk.gov.hmcts.ecm.common.model.helper.DocumentConstants.TRIBUNAL_CASE_FILE;
import static uk.gov.hmcts.ecm.common.model.helper.DocumentConstants.TRIBUNAL_NOTICE;
import static uk.gov.hmcts.ecm.common.model.helper.DocumentConstants.TRIBUNAL_ORDER;
import static uk.gov.hmcts.ecm.common.model.helper.DocumentConstants.UNLESS_ORDER;
import static uk.gov.hmcts.ecm.common.model.helper.DocumentConstants.WITHDRAWAL_OF_ENTIRE_CLAIM;
import static uk.gov.hmcts.ecm.common.model.helper.DocumentConstants.WITHDRAWAL_OF_PART_OF_CLAIM;
import static uk.gov.hmcts.ecm.common.model.helper.DocumentConstants.WITHDRAWAL_SETTLED;

public class DocumentHelper {

    private DocumentHelper() {
        // Access through static methods
    }

    public static String getTopLevelDocument(String typeOfDocument) {
        return switch (typeOfDocument) {
            case ET1, ET1_ATTACHMENT, ACAS_CERTIFICATE, NOTICE_OF_CLAIM, CLAIM_ACCEPTED, CLAIM_REJECTED,
                    CLAIM_PART_REJECTED, ET1_VETTING -> STARTING_A_CLAIM;
            case ET3, ET3_ATTACHMENT, RESPONSE_ACCEPTED, RESPONSE_REJECTED, APP_TO_EXTEND_TIME_TO_PRESENT_A_RESPONSE,
                    ET3_PROCESSING
                    -> RESPONSE_TO_A_CLAIM;
            case INITIAL_CONSIDERATION, RULE_27_NOTICE, RULE_28_NOTICE -> INITIAL_CONSIDERATION;
            case TRIBUNAL_ORDER, DEPOSIT_ORDER, UNLESS_ORDER, TRIBUNAL_NOTICE, APP_TO_VARY_AN_ORDER_C,
                    APP_TO_VARY_AN_ORDER_R, APP_TO_REVOKE_AN_ORDER_C, APP_TO_REVOKE_AN_ORDER_R,
                    APP_TO_EXTEND_TIME_TO_COMPLY_TO_AN_ORDER_DIRECTIONS_C,
                    APP_TO_EXTEND_TIME_TO_COMPLY_TO_AN_ORDER_DIRECTIONS_R, APP_TO_ORDER_THE_R_TO_DO_SOMETHING,
                    APP_TO_ORDER_THE_C_TO_DO_SOMETHING, APP_TO_AMEND_CLAIM, APP_TO_AMEND_RESPONSE,
                    APP_FOR_A_WITNESS_ORDER, DISABILITY_IMPACT_STATEMENT, R_HAS_NOT_COMPLIED_WITH_AN_ORDER_C,
                    C_HAS_NOT_COMPLIED_WITH_AN_ORDER_R, APP_TO_STRIKE_OUT_ALL_OR_PART_OF_THE_CLAIM,
                    APP_TO_STRIKE_OUT_ALL_OR_PART_OF_THE_RESPONSE, REFERRAL_JUDICIAL_DIRECTION,
                    CHANGE_OF_PARTYS_DETAILS -> CASE_MANAGEMENT;
            case WITHDRAWAL_OF_ENTIRE_CLAIM, WITHDRAWAL_OF_PART_OF_CLAIM, COT3 -> WITHDRAWAL_SETTLED;
            case APP_TO_RESTRICT_PUBLICITY_C, APP_TO_RESTRICT_PUBLICITY_R, ANONYMITY_ORDER, NOTICE_OF_HEARING,
                    APP_TO_POSTPONE_C, APP_TO_POSTPONE_R, HEARING_BUNDLE, SCHEDULE_OF_LOSS, COUNTER_SCHEDULE_OF_LOSS
                    -> HEARINGS;
            case JUDGMENT, JUDGMENT_WITH_REASONS, REASONS, EXTRACT_OF_JUDGMENT -> JUDGMENT_AND_REASONS;
            case APP_TO_HAVE_A_LEGAL_OFFICER_DECISION_CONSIDERED_AFRESH_C,
                    APP_TO_HAVE_A_LEGAL_OFFICER_DECISION_CONSIDERED_AFRESH_R, APP_FOR_A_JUDGMENT_TO_BE_RECONSIDERED_C,
                    APP_FOR_A_JUDGMENT_TO_BE_RECONSIDERED_R -> RECONSIDERATION;
            case CERTIFICATE_OF_CORRECTION, TRIBUNAL_CASE_FILE, OTHER -> MISC;
            default -> LEGACY_DOCUMENT_NAMES;
        };
    }

    public static void setSecondLevelDocumentFromType(DocumentType documentType, String typeOfDocument) {
        switch (typeOfDocument) {
            case ET1, ET1_ATTACHMENT, ACAS_CERTIFICATE, NOTICE_OF_CLAIM, CLAIM_ACCEPTED, CLAIM_REJECTED,
                    CLAIM_PART_REJECTED, ET1_VETTING -> documentType.setStartingClaimDocuments(typeOfDocument);
            case ET3, ET3_ATTACHMENT, RESPONSE_ACCEPTED, RESPONSE_REJECTED, APP_TO_EXTEND_TIME_TO_PRESENT_A_RESPONSE,
                    ET3_PROCESSING -> documentType.setResponseClaimDocuments(typeOfDocument);
            case INITIAL_CONSIDERATION, RULE_27_NOTICE, RULE_28_NOTICE
                    -> documentType.setInitialConsiderationDocuments(typeOfDocument);
            case TRIBUNAL_ORDER, DEPOSIT_ORDER, UNLESS_ORDER, TRIBUNAL_NOTICE, APP_TO_VARY_AN_ORDER_C,
                    APP_TO_VARY_AN_ORDER_R, APP_TO_REVOKE_AN_ORDER_C, APP_TO_REVOKE_AN_ORDER_R,
                    APP_TO_EXTEND_TIME_TO_COMPLY_TO_AN_ORDER_DIRECTIONS_C,
                    APP_TO_EXTEND_TIME_TO_COMPLY_TO_AN_ORDER_DIRECTIONS_R, APP_TO_ORDER_THE_R_TO_DO_SOMETHING,
                    APP_TO_ORDER_THE_C_TO_DO_SOMETHING, APP_TO_AMEND_CLAIM, APP_TO_AMEND_RESPONSE,
                    APP_FOR_A_WITNESS_ORDER, DISABILITY_IMPACT_STATEMENT, R_HAS_NOT_COMPLIED_WITH_AN_ORDER_C,
                    C_HAS_NOT_COMPLIED_WITH_AN_ORDER_R, APP_TO_STRIKE_OUT_ALL_OR_PART_OF_THE_CLAIM,
                    APP_TO_STRIKE_OUT_ALL_OR_PART_OF_THE_RESPONSE, REFERRAL_JUDICIAL_DIRECTION,
                    CHANGE_OF_PARTYS_DETAILS -> documentType.setCaseManagementDocuments(typeOfDocument);
            case WITHDRAWAL_OF_ENTIRE_CLAIM, WITHDRAWAL_OF_PART_OF_CLAIM, COT3
                    -> documentType.setWithdrawalSettledDocuments(typeOfDocument);
            case APP_TO_RESTRICT_PUBLICITY_C, APP_TO_RESTRICT_PUBLICITY_R, ANONYMITY_ORDER, NOTICE_OF_HEARING,
                    APP_TO_POSTPONE_C, APP_TO_POSTPONE_R, HEARING_BUNDLE, SCHEDULE_OF_LOSS, COUNTER_SCHEDULE_OF_LOSS
                    -> documentType.setHearingsDocuments(typeOfDocument);
            case JUDGMENT, JUDGMENT_WITH_REASONS, REASONS, EXTRACT_OF_JUDGMENT
                    -> documentType.setJudgmentAndReasonsDocuments(typeOfDocument);
            case APP_TO_HAVE_A_LEGAL_OFFICER_DECISION_CONSIDERED_AFRESH_C,
                    APP_TO_HAVE_A_LEGAL_OFFICER_DECISION_CONSIDERED_AFRESH_R, APP_FOR_A_JUDGMENT_TO_BE_RECONSIDERED_C,
                    APP_FOR_A_JUDGMENT_TO_BE_RECONSIDERED_R -> documentType.setReconsiderationDocuments(typeOfDocument);
            case CERTIFICATE_OF_CORRECTION, TRIBUNAL_CASE_FILE, OTHER -> documentType.setMiscDocuments(typeOfDocument);
            default -> documentType.setTypeOfDocument(typeOfDocument);
        }
    }

    public static void setDocumentTypeForDocument(DocumentType documentType) {
        if (!isNullOrEmpty(documentType.getTopLevelDocuments()) || !isNullOrEmpty(documentType.getTypeOfDocument())) {
            if (!isNullOrEmpty(documentType.getStartingClaimDocuments())) {
                documentType.setDocumentType(documentType.getStartingClaimDocuments());
            } else if (!isNullOrEmpty(documentType.getResponseClaimDocuments())) {
                documentType.setDocumentType(documentType.getResponseClaimDocuments());
            } else if (!isNullOrEmpty(documentType.getInitialConsiderationDocuments())) {
                documentType.setDocumentType(documentType.getInitialConsiderationDocuments());
            } else if (!isNullOrEmpty(documentType.getCaseManagementDocuments())) {
                documentType.setDocumentType(documentType.getCaseManagementDocuments());
            } else if (!isNullOrEmpty(documentType.getWithdrawalSettledDocuments())) {
                documentType.setDocumentType(documentType.getWithdrawalSettledDocuments());
            } else if (!isNullOrEmpty(documentType.getHearingsDocuments())) {
                documentType.setDocumentType(documentType.getHearingsDocuments());
            } else if (!isNullOrEmpty(documentType.getJudgmentAndReasonsDocuments())) {
                documentType.setDocumentType(documentType.getJudgmentAndReasonsDocuments());
            } else if (!isNullOrEmpty(documentType.getReconsiderationDocuments())) {
                documentType.setDocumentType(documentType.getReconsiderationDocuments());
            } else if (!isNullOrEmpty(documentType.getMiscDocuments())) {
                documentType.setDocumentType(documentType.getMiscDocuments());
            } else {
                documentType.setDocumentType(documentType.getTypeOfDocument());
            }
        }
    }
}
