package uk.gov.hmcts.ecm.common.service.utils;

import org.apache.commons.lang3.StringUtils;
import org.springframework.util.ObjectUtils;
import uk.gov.hmcts.ecm.common.constants.PdfMapperConstants;
import uk.gov.hmcts.et.common.model.ccd.CaseData;

import java.util.Optional;
import java.util.concurrent.ConcurrentMap;

import static java.util.Optional.ofNullable;

public final class PdfMapperClaimDescriptionUtil {

    private PdfMapperClaimDescriptionUtil() {
        // Utility classes should not have a public or default constructor.
    }

    public static void putClaimDescription(CaseData caseData, ConcurrentMap<String, Optional<String>> printFields) {
        try {
            if (!ObjectUtils.isEmpty(caseData.getClaimantRequests())
                && StringUtils.isNotBlank(caseData.getClaimantRequests().getClaimDescription())) {
                printFields.put(
                    PdfMapperConstants.Q8_CLAIM_DESCRIPTION,
                    ofNullable(caseData.getClaimantRequests().getClaimDescription())
                );
            }
        } catch (Exception e) {
            GenericServiceUtil.logException("An error occurred while printing claim description to pdf file",
                                            caseData.getEthosCaseReference(),
                                            e.getMessage(),
                                            "PdfMapperClaimDescriptionUtil",
                                            "putClaimDescription");
        }
    }

}