package uk.gov.hmcts.ecm.common.service.pdf.util;

import org.apache.commons.lang3.StringUtils;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.NullSource;
import org.springframework.util.ObjectUtils;
import uk.gov.hmcts.ecm.common.constants.PdfMapperConstants;
import uk.gov.hmcts.ecm.common.service.pdf.et1.PdfMapperHearingPreferencesUtil;
import uk.gov.hmcts.ecm.common.service.pdf.et1.PdfMapperServiceUtil;
import uk.gov.hmcts.et.common.model.ccd.CaseData;

import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import static org.assertj.core.api.Assertions.assertThat;
import static uk.gov.hmcts.ecm.common.constants.PdfMapperConstants.NO_LOWERCASE;
import static uk.gov.hmcts.ecm.common.constants.PdfMapperConstants.PHONE;
import static uk.gov.hmcts.ecm.common.constants.PdfMapperConstants.VIDEO;
import static uk.gov.hmcts.ecm.common.model.helper.Constants.YES;

class PdfMapperHearingPreferencesUtilTest {

    @ParameterizedTest
    @NullSource
    @MethodSource("uk.gov.hmcts.ecm.common.service.utils.data.PdfMapperHearingPreferencesUtilTestDataProvider"
        + "#generateCaseDataSamplesWithHearingPreferences")
    void putHearingPreferences(CaseData caseData) {
        ConcurrentMap<String, Optional<String>> printFields = new ConcurrentHashMap<>();
        PdfMapperHearingPreferencesUtil.putHearingPreferences(caseData, printFields);
        if (ObjectUtils.isEmpty(caseData)) {
            assertThat(printFields.get(PdfMapperConstants.I_CAN_TAKE_PART_IN_NO_HEARINGS)).isNull();
            assertThat(printFields.get(PdfMapperConstants.I_CAN_TAKE_PART_IN_NO_HEARINGS_EXPLAIN)).isNull();
            assertThat(printFields.get(PdfMapperConstants.I_CAN_TAKE_PART_IN_PHONE_HEARINGS)).isNull();
            assertThat(printFields.get(PdfMapperConstants.I_CAN_TAKE_PART_IN_VIDEO_HEARINGS)).isNull();
        } else {
            if (!ObjectUtils.isEmpty(caseData.getClaimantHearingPreference())
                && !StringUtils.isEmpty(caseData.getClaimantHearingPreference().getReasonableAdjustments())) {
                checkReasonableAdjustments(caseData, printFields);
            }
            checkHearingPreferences(caseData, printFields);
            checkClaimantHearingPanelPreference(caseData, printFields);
        }
    }

    private static void checkReasonableAdjustments(CaseData caseData,
                                                   ConcurrentMap<String, Optional<String>> printFields) {
        if (PdfMapperServiceUtil.isYes(caseData.getClaimantHearingPreference().getReasonableAdjustments())) {
            assertThat(printFields.get(PdfMapperConstants.Q12_DISABILITY_YES)).contains(YES);
        } else {
            assertThat(printFields.get(PdfMapperConstants.Q12_DISABILITY_NO)).contains(NO_LOWERCASE);
        }

        if (!StringUtils.isEmpty(caseData.getClaimantHearingPreference().getReasonableAdjustmentsDetail())) {
            assertThat(printFields.get(PdfMapperConstants.Q12_DISABILITY_DETAILS)).contains(
                caseData.getClaimantHearingPreference().getReasonableAdjustmentsDetail());
        }
    }

    private static void checkHearingPreferences(CaseData caseData,
                                                ConcurrentMap<String, Optional<String>> printFields) {
        if (ObjectUtils.isEmpty(caseData.getClaimantHearingPreference())
            || ObjectUtils.isEmpty(caseData.getClaimantHearingPreference().getHearingPreferences())
            || !caseData.getClaimantHearingPreference().getHearingPreferences().contains(VIDEO)
            && !caseData.getClaimantHearingPreference().getHearingPreferences().contains(PHONE)) {
            checkHearingPreferencesNotEntered(caseData, printFields);
        } else {
            if (!ObjectUtils.isEmpty(caseData.getClaimantHearingPreference())
                && !ObjectUtils.isEmpty(caseData.getClaimantHearingPreference().getHearingPreferences())) {
                checkHearingPreferencesEntered(caseData, printFields);
            }
        }
    }

    private static void checkHearingPreferencesNotEntered(CaseData caseData,
                                                          ConcurrentMap<String, Optional<String>> printFields) {
        assertThat(printFields.get(PdfMapperConstants.I_CAN_TAKE_PART_IN_NO_HEARINGS)).contains(YES);
        if (!ObjectUtils.isEmpty(caseData.getClaimantHearingPreference())
            && StringUtils.isNotBlank(caseData.getClaimantHearingPreference().getHearingAssistance())) {
            assertThat(printFields.get(PdfMapperConstants.I_CAN_TAKE_PART_IN_NO_HEARINGS_EXPLAIN))
                .contains(caseData.getClaimantHearingPreference().getHearingAssistance());
        }
    }

    private static void checkHearingPreferencesEntered(CaseData caseData,
                                                       ConcurrentMap<String, Optional<String>> printFields) {
        if (caseData.getClaimantHearingPreference().getHearingPreferences().contains(VIDEO)) {
            assertThat(printFields.get(PdfMapperConstants.I_CAN_TAKE_PART_IN_VIDEO_HEARINGS)).contains(YES);
        }
        if (caseData.getClaimantHearingPreference().getHearingPreferences().contains(PHONE)) {
            assertThat(printFields.get(PdfMapperConstants.I_CAN_TAKE_PART_IN_PHONE_HEARINGS)).contains(YES);
        }
    }

    private static void checkClaimantHearingPanelPreference(CaseData caseData,
                                                          ConcurrentMap<String, Optional<String>> printFields) {

        if (ObjectUtils.isEmpty(caseData) || ObjectUtils.isEmpty(caseData.getClaimantHearingPreference())
                || StringUtils.isEmpty(caseData.getClaimantHearingPreference().getClaimantHearingPanelPreference())) {
            assertThat(printFields.get(PdfMapperConstants.CLAIMANT_HEARING_PANEL_NO_PREFERENCE)).isNull();
            assertThat(printFields.get(PdfMapperConstants.CLAIMANT_HEARING_PANEL_PREFERENCE_JUDGE)).isNull();
            assertThat(printFields.get(PdfMapperConstants.CLAIMANT_HEARING_PANEL_PREFERENCE_PANEL)).isNull();
            assertThat(printFields.get(PdfMapperConstants.CLAIMANT_HEARING_PANEL_REASON)).isNull();
        } else {
            if (caseData.getClaimantHearingPreference().getClaimantHearingPanelPreference()
                    .contains(PdfMapperConstants.HEARING_PANEL_NO_PREFERENCE)) {
                assertThat(printFields.get(PdfMapperConstants.CLAIMANT_HEARING_PANEL_NO_PREFERENCE)).contains(YES);
            }
            if (caseData.getClaimantHearingPreference().getClaimantHearingPanelPreference()
                    .contains(PdfMapperConstants.HEARING_PANEL_PREFERENCE_JUDGE)) {
                assertThat(printFields.get(PdfMapperConstants.CLAIMANT_HEARING_PANEL_PREFERENCE_JUDGE)).contains(YES);
            }
            if (caseData.getClaimantHearingPreference().getClaimantHearingPanelPreference()
                    .contains(PdfMapperConstants.HEARING_PANEL_PREFERENCE_PANEL)) {
                assertThat(printFields.get(PdfMapperConstants.CLAIMANT_HEARING_PANEL_PREFERENCE_PANEL)).contains(YES);
            }
            if (StringUtils.isNotBlank(caseData.getClaimantHearingPreference()
                    .getClaimantHearingPanelPreferenceWhy())) {
                assertThat(printFields.get(PdfMapperConstants.CLAIMANT_HEARING_PANEL_REASON))
                        .contains(caseData.getClaimantHearingPreference().getClaimantHearingPanelPreferenceWhy());
            }
        }
    }
}
