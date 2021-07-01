package uk.gov.hmcts.ecm.common.model.servicebus.tasks;

import uk.gov.hmcts.ecm.common.model.ccd.CaseData;
import uk.gov.hmcts.ecm.common.model.ccd.items.JudgementTypeItem;
import uk.gov.hmcts.ecm.common.model.ccd.items.JurCodesTypeItem;
import uk.gov.hmcts.ecm.common.model.ccd.types.JudgementType;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class CaseJudgementUpdate {

    private CaseJudgementUpdate() {
        // Access through static methods
    }

    /**
     * Updates a case with the judgement only if the case has at least one jurisdiction that matches the judgement.
     * <p>The judgement is updated to include only those jurisdictions that are also in the case</p>
     * @param caseData the case to be updated
     * @param judgementType the judgement to add to the case
     */
    public static void updateCaseWithJudgement(CaseData caseData, JudgementType judgementType) {
        if (!isCaseWithJurisdiction(caseData)) {
            return;
        }

        List<JurCodesTypeItem> jurCodesTypeItems = filterJurCodesForJudgement(caseData, judgementType.getJurisdictionCodes());
        if (jurCodesTypeItems.isEmpty()) {
            return;
        }

        judgementType.setJurisdictionCodes(jurCodesTypeItems);

        if (caseData.getJudgementCollection() == null) {
            caseData.setJudgementCollection(new ArrayList<>());
        }

        caseData.getJudgementCollection().add(JudgementTypeItem.from(judgementType));
    }

    private static boolean isCaseWithJurisdiction(CaseData caseData) {
        return caseData.getJurCodesCollection() != null && !caseData.getJurCodesCollection().isEmpty();
    }

    private static List<JurCodesTypeItem> filterJurCodesForJudgement(CaseData caseData,
                                                              List<JurCodesTypeItem> jurisdictionCodes) {
        List<String> existingJurisdictionCodes =
                caseData.getJurCodesCollection().stream()
                        .map(jurCodesTypeItem -> jurCodesTypeItem.getValue().getJuridictionCodesList())
                        .collect(Collectors.toList());

        return jurisdictionCodes.stream()
                .filter(jurCodesTypeItem -> existingJurisdictionCodes
                        .contains(jurCodesTypeItem.getValue().getJuridictionCodesList()))
                .collect(Collectors.toList());
    }
}
