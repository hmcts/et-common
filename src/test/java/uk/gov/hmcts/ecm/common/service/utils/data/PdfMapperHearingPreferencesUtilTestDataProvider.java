package uk.gov.hmcts.ecm.common.service.utils.data;

import org.junit.jupiter.params.provider.Arguments;
import uk.gov.hmcts.ecm.common.service.utils.TestConstants;
import uk.gov.hmcts.et.common.model.ccd.CaseData;
import uk.gov.hmcts.et.common.model.ccd.types.ClaimantHearingPreference;

import java.util.List;
import java.util.stream.Stream;

import static uk.gov.hmcts.ecm.common.service.utils.TestConstants.TRUE;
import static uk.gov.hmcts.ecm.common.service.utils.TestConstants.VIDEO;
import static uk.gov.hmcts.ecm.common.service.utils.TestConstants.YES;

public final class PdfMapperHearingPreferencesUtilTestDataProvider {

    private PdfMapperHearingPreferencesUtilTestDataProvider() {
        // Utility classes should not have a public or default constructor.
    }

    public static Stream<Arguments> generateCaseDataSamplesWithHearingPreferences() {
        // Empty Reasonable Adjustments
        CaseData caseDataEmptyReasonableAdjustment = new CaseData();
        caseDataEmptyReasonableAdjustment.setClaimantHearingPreference(
            generateClaimantHearingPreference(TRUE, TestConstants.NULL_STRING, TestConstants.NULL_STRING,
                                              TestConstants.EMPTY_STRING_ARRAY, TestConstants.HEARING_ASSISTANCE,
                                              TestConstants.NULL_STRING, TestConstants.NULL_STRING));
        caseDataEmptyReasonableAdjustment.setEthosCaseReference(TestConstants.STRING_NUMERIC_ONE);
        // Yes Reasonable Adjustments
        CaseData caseDataYesReasonableAdjustments = new CaseData();
        caseDataYesReasonableAdjustments.setClaimantHearingPreference(
            generateClaimantHearingPreference(TestConstants.FALSE, YES, TestConstants.REASONABLE_ADJUSTMENT_DETAILS,
                                              TestConstants.EMPTY_STRING_ARRAY, TestConstants.HEARING_ASSISTANCE,
                                              TestConstants.NULL_STRING, TestConstants.NULL_STRING));
        caseDataYesReasonableAdjustments.setEthosCaseReference(TestConstants.STRING_NUMERIC_TWO);
        // No Reasonable Adjustments
        CaseData caseDataNoReasonableAdjustments = new CaseData();
        caseDataNoReasonableAdjustments.setClaimantHearingPreference(
            generateClaimantHearingPreference(TestConstants.FALSE, TestConstants.NO, TestConstants.NULL_STRING,
                                              TestConstants.EMPTY_STRING_ARRAY, TestConstants.HEARING_ASSISTANCE,
                                              TestConstants.NULL_STRING, TestConstants.NULL_STRING));
        caseDataNoReasonableAdjustments.setEthosCaseReference(TestConstants.STRING_NUMERIC_THREE);
        // No Hearing Preference Selected
        CaseData caseDataNoHearingPreferenceSelected = new CaseData();
        caseDataNoHearingPreferenceSelected.setClaimantHearingPreference(
            generateClaimantHearingPreference(TestConstants.FALSE, YES, TestConstants.REASONABLE_ADJUSTMENT_DETAILS,
                                              TestConstants.EMPTY_STRING_ARRAY, TestConstants.HEARING_ASSISTANCE,
                                              TestConstants.NULL_STRING, TestConstants.NULL_STRING));
        caseDataNoHearingPreferenceSelected.setEthosCaseReference(TestConstants.STRING_NUMERIC_FOUR);
        // No Hearing Preference Selected
        CaseData caseDataHearingPreferenceVideoPhoneSelected = new CaseData();
        caseDataHearingPreferenceVideoPhoneSelected.setClaimantHearingPreference(
            generateClaimantHearingPreference(TestConstants.FALSE, YES, TestConstants.REASONABLE_ADJUSTMENT_DETAILS,
                    new String[]{ VIDEO, TestConstants.PHONE },
                    TestConstants.HEARING_ASSISTANCE,
                    TestConstants.NULL_STRING,
                    TestConstants.NULL_STRING));
        caseDataHearingPreferenceVideoPhoneSelected.setEthosCaseReference(TestConstants.STRING_NUMERIC_FIVE);
        // Invalid Hearing Preference Selected
        CaseData caseDataInvalidHearingPreferenceSelected = new CaseData();
        caseDataInvalidHearingPreferenceSelected.setClaimantHearingPreference(
            generateClaimantHearingPreference(TestConstants.FALSE, YES, TestConstants.REASONABLE_ADJUSTMENT_DETAILS,
                                              new String[]{"Dummy", "String"}, TestConstants.HEARING_ASSISTANCE,
                                              TestConstants.NULL_STRING, TestConstants.NULL_STRING));
        caseDataHearingPreferenceVideoPhoneSelected.setEthosCaseReference(TestConstants.STRING_NUMERIC_SIX);
        // No Hearing Panel Preference Selected
        CaseData caseDataNoHearingPanelPreferenceSelected = new CaseData();
        caseDataNoHearingPanelPreferenceSelected.setClaimantHearingPreference(
                generateClaimantHearingPreference(TestConstants.FALSE, YES,
                        TestConstants.REASONABLE_ADJUSTMENT_DETAILS,
                        new String[]{"Dummy", "String"}, TestConstants.HEARING_ASSISTANCE,
                        TestConstants.NULL_STRING, TestConstants.NULL_STRING));
        caseDataNoHearingPanelPreferenceSelected.setEthosCaseReference(TestConstants.STRING_NUMERIC_SEVEN);
        // NO_PREFERENCE Hearing Panel Preference Selected
        CaseData caseDataNoPreferenceHearingPanelPreferenceSelected = new CaseData();
        caseDataNoPreferenceHearingPanelPreferenceSelected.setClaimantHearingPreference(
                generateClaimantHearingPreference(TestConstants.FALSE, YES,
                        TestConstants.REASONABLE_ADJUSTMENT_DETAILS,
                        new String[]{"Dummy", "String"}, TestConstants.HEARING_ASSISTANCE,
                        TestConstants.HEARING_PANEL_NO_PREFERENCE, TestConstants.NULL_STRING));
        caseDataNoPreferenceHearingPanelPreferenceSelected.setEthosCaseReference(TestConstants.STRING_NUMERIC_EIGHT);
        // JUDGE Hearing Panel Preference Selected
        CaseData caseDataJudgeHearingPanelPreferenceSelected = new CaseData();
        caseDataJudgeHearingPanelPreferenceSelected.setClaimantHearingPreference(
                generateClaimantHearingPreference(TestConstants.FALSE, YES, TestConstants.REASONABLE_ADJUSTMENT_DETAILS,
                        new String[]{"Dummy", "String"}, TestConstants.HEARING_ASSISTANCE,
                        TestConstants.HEARING_PANEL_PREFERENCE_JUDGE, TestConstants.HEARING_PANEL_PREFERENCE_REASON));
        caseDataJudgeHearingPanelPreferenceSelected.setEthosCaseReference(TestConstants.STRING_NUMERIC_NINE);
        // PANEL Hearing Panel Preference Selected
        CaseData caseDataPanelHearingPanelPreferenceSelected = new CaseData();
        caseDataPanelHearingPanelPreferenceSelected.setClaimantHearingPreference(
                generateClaimantHearingPreference(TestConstants.FALSE, YES, TestConstants.REASONABLE_ADJUSTMENT_DETAILS,
                        new String[]{"Dummy", "String"}, TestConstants.HEARING_ASSISTANCE,
                        TestConstants.HEARING_PANEL_PREFERENCE_PANEL, TestConstants.HEARING_PANEL_PREFERENCE_REASON));
        caseDataPanelHearingPanelPreferenceSelected.setEthosCaseReference(TestConstants.STRING_NUMERIC_TEN);
        // JUDGE selected with NO reason provided
        CaseData caseDataHearingPanelPreferenceSelectedNoReasonProvided = new CaseData();
        caseDataHearingPanelPreferenceSelectedNoReasonProvided.setClaimantHearingPreference(
                generateClaimantHearingPreference(TestConstants.FALSE, YES, TestConstants.REASONABLE_ADJUSTMENT_DETAILS,
                        new String[]{"Dummy", "String"}, TestConstants.HEARING_ASSISTANCE,
                        TestConstants.HEARING_PANEL_PREFERENCE_JUDGE, TestConstants.EMPTY_STRING));
        caseDataHearingPanelPreferenceSelectedNoReasonProvided.setEthosCaseReference(
                TestConstants.STRING_NUMERIC_ELEVEN);
        // Empty Hearing Preferences
        CaseData caseDataClaimantHearingPreferencesEmpty = new CaseData();
        return Stream.of(Arguments.of(caseDataClaimantHearingPreferencesEmpty),
                         Arguments.of(caseDataEmptyReasonableAdjustment),
                         Arguments.of(caseDataYesReasonableAdjustments),
                         Arguments.of(caseDataNoReasonableAdjustments),
                         Arguments.of(caseDataInvalidHearingPreferenceSelected),
                         Arguments.of(caseDataHearingPreferenceVideoPhoneSelected),
                         Arguments.of(caseDataNoHearingPreferenceSelected),
                         Arguments.of(caseDataNoHearingPanelPreferenceSelected),
                         Arguments.of(caseDataNoPreferenceHearingPanelPreferenceSelected),
                         Arguments.of(caseDataJudgeHearingPanelPreferenceSelected),
                         Arguments.of(caseDataPanelHearingPanelPreferenceSelected),
                         Arguments.of(caseDataHearingPanelPreferenceSelectedNoReasonProvided));
    }

    public static ClaimantHearingPreference generateClaimantHearingPreference(boolean isEmptyReasonableAdjustment,
                                                                              String reasonableAdjustment,
                                                                              String reasonableAdjustmentDetails,
                                                                              String[] hearingPreferences,
                                                                              String hearingAssistance,
                                                                              String hearingPanelPreference,
                                                                              String hearingPanelPreferenceReason) {
        ClaimantHearingPreference claimantHearingPreference = new ClaimantHearingPreference();
        if (isEmptyReasonableAdjustment) {
            return claimantHearingPreference;
        }
        claimantHearingPreference.setReasonableAdjustments(reasonableAdjustment);
        claimantHearingPreference.setReasonableAdjustmentsDetail(reasonableAdjustmentDetails);
        claimantHearingPreference.setHearingPreferences(List.of(hearingPreferences));
        claimantHearingPreference.setHearingAssistance(hearingAssistance);
        claimantHearingPreference.setClaimantHearingPanelPreference(hearingPanelPreference);
        claimantHearingPreference.setClaimantHearingPanelPreferenceWhy(hearingPanelPreferenceReason);
        return claimantHearingPreference;
    }

}
