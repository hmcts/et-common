package uk.gov.hmcts.ecm.common.service.utils;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.util.ObjectUtils;
import uk.gov.hmcts.ecm.common.constants.PdfMapperConstants;
import uk.gov.hmcts.et.common.model.ccd.CaseData;

import java.util.Optional;
import java.util.concurrent.ConcurrentMap;

import static java.util.Optional.ofNullable;
import static uk.gov.hmcts.ecm.common.constants.PdfMapperConstants.NO_LOWERCASE;
import static uk.gov.hmcts.ecm.common.constants.PdfMapperConstants.PHONE;
import static uk.gov.hmcts.ecm.common.constants.PdfMapperConstants.VIDEO;
import static uk.gov.hmcts.ecm.common.model.helper.Constants.YES;

@Slf4j
public final class PdfMapperHearingPreferencesUtil {

    private PdfMapperHearingPreferencesUtil() {
        // Utility classes should not have a public or default constructor.
    }

    public static void putHearingPreferences(CaseData caseData, ConcurrentMap<String, Optional<String>> printFields) {
        try {
            if (!ObjectUtils.isEmpty(caseData)) {
                setClaimantReasonableAdjustments(caseData, printFields);
                setClaimantHearingPreferences(caseData, printFields);
            }
        } catch (Exception e) {
            GenericServiceUtil.logException("An error occured while printing hearing preferences to pdf file",
                                            caseData.getEthosCaseReference(),
                                            e.getMessage(),
                                            "PdfMapperHearingPreferencesUtil",
                                            "printHearingPreferences");
        }
    }

    private static void setClaimantReasonableAdjustments(CaseData caseData,
                                                        ConcurrentMap<String, Optional<String>> printFields) {
        if (!ObjectUtils.isEmpty(caseData.getClaimantHearingPreference())
            && !StringUtils.isEmpty(caseData.getClaimantHearingPreference().getReasonableAdjustments())) {
            if (PdfMapperServiceUtil.isYes(caseData.getClaimantHearingPreference().getReasonableAdjustments())) {
                printFields.put(PdfMapperConstants.Q12_DISABILITY_YES, Optional.of(YES));
            } else {
                printFields.put(PdfMapperConstants.Q12_DISABILITY_NO, Optional.of(NO_LOWERCASE));
            }
            printFields.put(
                PdfMapperConstants.Q12_DISABILITY_DETAILS,
                ofNullable(caseData.getClaimantHearingPreference().getReasonableAdjustmentsDetail())
            );
        }

    }

    private static void setClaimantHearingPreferences(CaseData caseData,
                                                ConcurrentMap<String, Optional<String>> printFields) {
        if (checkIfHearingPreferencesNoHearingsOrNothingSelected(caseData)) {
            setClaimantHearingPreferencesNoneWhenNothingSelected(caseData, printFields);
        } else {
            setClaimantHearingPreferencesWhenVideoPhoneSelected(caseData, printFields);
        }
    }

    private static void setClaimantHearingPreferencesNoneWhenNothingSelected(
        CaseData caseData, ConcurrentMap<String, Optional<String>> printFields) {
        printFields.put(PdfMapperConstants.I_CAN_TAKE_PART_IN_NO_HEARINGS, Optional.of(YES));
        if (!ObjectUtils.isEmpty(caseData.getClaimantHearingPreference())
            && StringUtils.isNotBlank(caseData.getClaimantHearingPreference().getHearingAssistance())) {
            printFields.put(
                PdfMapperConstants.I_CAN_TAKE_PART_IN_NO_HEARINGS_EXPLAIN,
                ofNullable(caseData.getClaimantHearingPreference().getHearingAssistance())
            );
        }
    }

    private static boolean checkIfHearingPreferencesNoHearingsOrNothingSelected(CaseData caseData) {
        return ObjectUtils.isEmpty(caseData.getClaimantHearingPreference())
            || ObjectUtils.isEmpty(caseData.getClaimantHearingPreference().getHearingPreferences())
            || !caseData.getClaimantHearingPreference().getHearingPreferences().contains(VIDEO)
            && !caseData.getClaimantHearingPreference().getHearingPreferences().contains(PHONE);
    }

    private static void
        setClaimantHearingPreferencesWhenVideoPhoneSelected(CaseData caseData,
                                                            ConcurrentMap<String, Optional<String>> printFields) {
        if (!ObjectUtils.isEmpty(caseData.getClaimantHearingPreference())
            && !ObjectUtils.isEmpty(caseData.getClaimantHearingPreference().getHearingPreferences())) {
            if (caseData.getClaimantHearingPreference().getHearingPreferences().contains(VIDEO)) {
                printFields.put(
                    PdfMapperConstants.I_CAN_TAKE_PART_IN_VIDEO_HEARINGS,
                    Optional.of(YES)
                );
            }
            if (caseData.getClaimantHearingPreference().getHearingPreferences().contains(PHONE)) {
                printFields.put(
                    PdfMapperConstants.I_CAN_TAKE_PART_IN_PHONE_HEARINGS,
                    Optional.of(YES)
                );
            }
        }
    }

}
