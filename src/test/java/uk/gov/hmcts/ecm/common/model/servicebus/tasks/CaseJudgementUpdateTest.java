package uk.gov.hmcts.ecm.common.model.servicebus.tasks;

import org.junit.jupiter.api.Test;
import uk.gov.hmcts.et.common.model.bulk.types.DynamicFixedListType;
import uk.gov.hmcts.et.common.model.ccd.CaseData;
import uk.gov.hmcts.et.common.model.ccd.items.JudgementTypeItem;
import uk.gov.hmcts.et.common.model.ccd.items.JurCodesTypeItem;
import uk.gov.hmcts.et.common.model.ccd.types.JudgementType;
import uk.gov.hmcts.et.common.model.ccd.types.JurCodesType;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static uk.gov.hmcts.ecm.common.model.helper.Constants.NO;
import static uk.gov.hmcts.ecm.common.model.helper.Constants.YES;

class CaseJudgementUpdateTest {

    private static final String JUDGEMENT_NOTES = "This is a test judgement";

    @Test
    void shouldNotAddJudgementIfNoJurisdictionCodeExists() {
        // given source case has a judgement for a single jurisdiction
        // given target case has no jurisdictions
        // when we batch update judgements
        // then target case is not updated with judgement

        JudgementType sourceJudgementType = createJudgementType("ADT");
        CaseData caseData = new CaseData();

        CaseJudgementUpdate.updateCaseWithJudgement(caseData, sourceJudgementType);

        assertNull(caseData.getJudgementCollection());
    }

    @Test
    void shouldNotAddJudgementIfJurisdictionCodeDoesNotMatch() {
        // given source case has a judgement for a jurisdiction
        // given target case has a different jurisdiction
        // when we batch update judgements
        // then target case is not updated with judgement

        JudgementType sourceJudgementType = createJudgementType("ADT");
        CaseData caseData = createCaseData("CCP");

        CaseJudgementUpdate.updateCaseWithJudgement(caseData, sourceJudgementType);

        assertNull(caseData.getJudgementCollection());
    }

    @Test
    void shouldNotAddJudgementIfMultipleJurisdictionCodeDontMatch() {
        // given source case has a judgement for a jurisdiction
        // given target case has multiple different jurisdictions
        // when we batch update judgements
        // then target case is not updated with judgement

        JudgementType sourceJudgementType = createJudgementType("ADT", "DSO");
        CaseData caseData = createCaseData("CCP", "DRB");

        CaseJudgementUpdate.updateCaseWithJudgement(caseData, sourceJudgementType);

        assertNull(caseData.getJudgementCollection());
    }

    @Test
    void shouldAddJudgementWhenSingleJurisdictionCodeMatches() {
        // given source case has a judgement for a jurisdiction
        // given target case has the same jurisdiction
        // when we batch update judgements
        // then target case has judgement with jurisdiction

        String jurisdictionCode = "ADT";

        JudgementType sourceJudgementType = createJudgementType(jurisdictionCode);
        CaseData caseData = createCaseData(jurisdictionCode);

        CaseJudgementUpdate.updateCaseWithJudgement(caseData, sourceJudgementType);

        assertEquals(1, caseData.getJudgementCollection().size());
        JudgementType judgementType = caseData.getJudgementCollection().get(0).getValue();
        assertEquals(JUDGEMENT_NOTES, judgementType.getJudgmentNotes());
        assertEquals(1, judgementType.getJurisdictionCodes().size());
        assertEquals(jurisdictionCode, judgementType.getJurisdictionCodes().get(0)
                .getValue().getJuridictionCodesList());
    }

    @Test
    void shouldAddJudgementWhenMultipleJurisdictionCodesMatch() {
        // given source case has a judgement for multiple jurisdictions
        // given target case has one matching jurisdiction
        // when we batch update judgements
        // then target case has judgement with only matching jurisdiction

        JudgementType sourceJudgementType = createJudgementType("ADT", "DSO", "CCP");
        CaseData caseData = createCaseData("ADT", "CCP");

        CaseJudgementUpdate.updateCaseWithJudgement(caseData, sourceJudgementType);

        assertEquals(1, caseData.getJudgementCollection().size());
        JudgementType judgementType = caseData.getJudgementCollection().get(0).getValue();
        assertEquals(JUDGEMENT_NOTES, judgementType.getJudgmentNotes());
        assertEquals(2, judgementType.getJurisdictionCodes().size());
        assertEquals("ADT", judgementType.getJurisdictionCodes().get(0).getValue()
                .getJuridictionCodesList());
        assertEquals("CCP", judgementType.getJurisdictionCodes().get(1).getValue()
                .getJuridictionCodesList());
    }

    @Test
    void shouldAddJudgementToExistingJudgementCollection() {
        // given source case has a judgement
        // given target case has a judgement
        // given target jurisdiction matches source case judgement jurisdiction
        // when we batch update judgements
        // then target case has 2 judgements

        JudgementType sourceJudgementType = createJudgementType("DSO", "CCP");
        CaseData caseData = createCaseData("ADT", "CCP");
        caseData.setJudgementCollection(new ArrayList<>());
        addCaseJudgement(caseData, "ADT");

        CaseJudgementUpdate.updateCaseWithJudgement(caseData, sourceJudgementType);

        assertEquals(2, caseData.getJudgementCollection().size());

        // Check existing judgement
        JudgementType judgementType = caseData.getJudgementCollection().get(0).getValue();
        assertEquals(JUDGEMENT_NOTES, judgementType.getJudgmentNotes());
        assertEquals(1, judgementType.getJurisdictionCodes().size());
        assertEquals("ADT", judgementType.getJurisdictionCodes().get(0).getValue()
                .getJuridictionCodesList());

        // Check new judgement
        judgementType = caseData.getJudgementCollection().get(1).getValue();
        assertEquals(JUDGEMENT_NOTES, judgementType.getJudgmentNotes());
        assertEquals(1, judgementType.getJurisdictionCodes().size());
        assertEquals("CCP", judgementType.getJurisdictionCodes().get(0).getValue()
                .getJuridictionCodesList());
    }

    @Test
    void shouldAddJudgementIfItHasNoJurisdiction() {
        // given source case has a judgement for no jurisdictions
        // given target case has no jurisdictions
        // when we batch update judgements
        // then target case is updated with a judgement with no jurisdictions

        JudgementType sourceJudgementType = createJudgementType();
        CaseData caseData = createCaseData();

        CaseJudgementUpdate.updateCaseWithJudgement(caseData, sourceJudgementType);

        assertEquals(1, caseData.getJudgementCollection().size());
        JudgementType judgementType = caseData.getJudgementCollection().get(0).getValue();
        assertEquals(JUDGEMENT_NOTES, judgementType.getJudgmentNotes());
        assertNull(judgementType.getJurisdictionCodes());
    }

    @Test
    void shouldAddJudgementIfBothCaseAndJudgementHaveNoJurisdiction() {
        // given source case has a judgement for no jurisdictions
        // given target case has jurisdictions
        // when we batch update judgements
        // then target case is updated with a judgement with no jurisdictions

        JudgementType sourceJudgementType = createJudgementType();
        CaseData caseData = createCaseData("ADT", "CCP");

        CaseJudgementUpdate.updateCaseWithJudgement(caseData, sourceJudgementType);

        assertEquals(1, caseData.getJudgementCollection().size());
        JudgementType judgementType = caseData.getJudgementCollection().get(0).getValue();
        assertEquals(JUDGEMENT_NOTES, judgementType.getJudgmentNotes());
        assertNull(judgementType.getJurisdictionCodes());
    }

    @Test
    void shouldSetNonHearingToYesIfHearingExists() {
        // give source case has a judgment which is hearing related
        // when we batch update judgments
        // the target case should be set as a non hearing judgment
        JudgementType judgementType = createJudgementType("ADT");
        judgementType.setNonHearingJudgment(NO);
        judgementType.setDynamicJudgementHearing(new DynamicFixedListType());
        judgementType.setJudgmentHearingDate("2022-02-02");
        CaseData caseData = createCaseData("ADT");

        CaseJudgementUpdate.updateCaseWithJudgement(caseData, judgementType);

        assertEquals(1, caseData.getJudgementCollection().size());
        judgementType = caseData.getJudgementCollection().get(0).getValue();
        assertEquals(YES, judgementType.getNonHearingJudgment());
        assertNull(judgementType.getJudgmentHearingDate());
        assertNull(judgementType.getDynamicJudgementHearing());
    }

    private void addCaseJudgement(CaseData caseData, String... jurisdictionCodes) {
        JudgementType judgementType = createJudgementType(jurisdictionCodes);
        JudgementTypeItem judgementTypeItem = new JudgementTypeItem();
        judgementTypeItem.setValue(judgementType);
        caseData.getJudgementCollection().add(judgementTypeItem);
    }

    private JudgementType createJudgementType(String... jurisdictionCodes) {
        JudgementType judgementType = new JudgementType();
        judgementType.setJudgmentNotes(JUDGEMENT_NOTES);

        if (jurisdictionCodes.length > 0) {
            List<JurCodesTypeItem> jurCodesTypeItems = new ArrayList<>();

            for (String jurisdictionCode : jurisdictionCodes) {
                JurCodesType jurCodesType = new JurCodesType();
                jurCodesType.setJuridictionCodesList(jurisdictionCode);
                JurCodesTypeItem jurCodesTypeItem = new JurCodesTypeItem();
                jurCodesTypeItem.setValue(jurCodesType);

                jurCodesTypeItems.add(jurCodesTypeItem);
            }

            judgementType.setJurisdictionCodes(jurCodesTypeItems);
        }

        return judgementType;
    }

    private CaseData createCaseData(String... jurisdictionCodes) {
        CaseData caseData = new CaseData();

        if (jurisdictionCodes.length > 0) {
            List<JurCodesTypeItem> jurCodesCollection = new ArrayList<>();
            for (String jurisdictionCode : jurisdictionCodes) {
                JurCodesType jurCodesType = new JurCodesType();
                jurCodesType.setJuridictionCodesList(jurisdictionCode);
                JurCodesTypeItem jurCodesTypeItem = new JurCodesTypeItem();
                jurCodesTypeItem.setValue(jurCodesType);

                jurCodesCollection.add(jurCodesTypeItem);
            }
            caseData.setJurCodesCollection(jurCodesCollection);
        }

        return caseData;
    }
}
