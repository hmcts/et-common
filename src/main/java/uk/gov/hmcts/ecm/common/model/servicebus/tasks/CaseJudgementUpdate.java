package uk.gov.hmcts.ecm.common.model.servicebus.tasks;

import org.apache.commons.collections4.CollectionUtils;
import uk.gov.hmcts.et.common.model.ccd.CaseData;
import uk.gov.hmcts.et.common.model.ccd.items.JudgementTypeItem;
import uk.gov.hmcts.et.common.model.ccd.items.JurCodesTypeItem;
import uk.gov.hmcts.et.common.model.ccd.types.JudgementType;

import java.util.ArrayList;
import java.util.List;

import static uk.gov.hmcts.ecm.common.model.helper.Constants.NO;
import static uk.gov.hmcts.ecm.common.model.helper.Constants.YES;

public class CaseJudgementUpdate {

    private CaseJudgementUpdate() {
        // Access through static methods
    }

    /**
     * Updates a case with the judgement only if the case has at least one jurisdiction that matches the judgement or
     * the judgement contains no jurisdictions.
     * <p>The judgement is updated to include only those jurisdictions that are also in the case.</p>
     * @param caseData the case to be updated
     * @param judgementType the judgement to add to the case.
     */
    public static void updateCaseWithJudgement(CaseData caseData, JudgementType judgementType) {
        if (!isValidForUpdate(caseData, judgementType)) {
            return;
        }

        if (isJudgementWithJurisdiction(judgementType)) {
            List<JurCodesTypeItem> jurCodesTypeItems = filterJurCodesForJudgement(caseData,
                    judgementType.getJurisdictionCodes());
            if (jurCodesTypeItems.isEmpty()) {
                return;
            } else {
                judgementType.setJurisdictionCodes(jurCodesTypeItems);
            }
        }

        if (caseData.getJudgementCollection() == null) {
            caseData.setJudgementCollection(new ArrayList<>());
        }

        if (NO.equals(judgementType.getNonHearingJudgment())) {
            judgementType.setDynamicJudgementHearing(null);
            judgementType.setJudgmentHearingDate(null);
            judgementType.setNonHearingJudgment(YES);
        }

        caseData.getJudgementCollection().add(JudgementTypeItem.from(judgementType));
    }

    private static boolean isValidForUpdate(CaseData caseData, JudgementType judgementType) {
        // If the judgement has jurisdictions then the case must have some also
        if (CollectionUtils.isNotEmpty(judgementType.getJurisdictionCodes())) {
            return isCaseWithJurisdiction(caseData);
        } else {
            return true;
        }
    }

    private static boolean isCaseWithJurisdiction(CaseData caseData) {
        return CollectionUtils.isNotEmpty(caseData.getJurCodesCollection());
    }

    private static boolean isJudgementWithJurisdiction(JudgementType judgementType) {
        return CollectionUtils.isNotEmpty(judgementType.getJurisdictionCodes());
    }

    private static List<JurCodesTypeItem> filterJurCodesForJudgement(CaseData caseData,
                                                              List<JurCodesTypeItem> jurisdictionCodes) {
        List<String> existingJurisdictionCodes =
                caseData.getJurCodesCollection().stream()
                        .map(jurCodesTypeItem -> jurCodesTypeItem.getValue().getJuridictionCodesList())
                        .toList();

        return jurisdictionCodes.stream()
                .filter(jurCodesTypeItem -> existingJurisdictionCodes
                        .contains(jurCodesTypeItem.getValue().getJuridictionCodesList()))
                .toList();
    }
}
