package uk.gov.hmcts.ecm.common.service.pdf.util;

import org.apache.commons.lang3.StringUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import uk.gov.hmcts.ecm.common.constants.PdfMapperConstants;
import uk.gov.hmcts.ecm.common.service.pdf.et1.PdfMapperClaimDescriptionUtil;
import uk.gov.hmcts.et.common.model.ccd.CaseData;
import uk.gov.hmcts.et.common.model.ccd.types.ClaimantRequestType;

import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import static org.assertj.core.api.Assertions.assertThat;

class PdfMapperClaimDescriptionUtilTest {

    private CaseData caseData;

    @BeforeEach
    void beforeEach() {
        caseData = new CaseData();
        caseData.setEthosCaseReference("1234567890");
    }

    @ParameterizedTest
    @MethodSource("uk.gov.hmcts.ecm.common.service.utils.data.PdfMapperClaimDescriptionUtilTestDataProvider"
        + "#generateClaimantRequests")
    void putClaimDescription(ClaimantRequestType claimantRequests, String expectedResult) {
        ConcurrentMap<String, Optional<String>> printFields = new ConcurrentHashMap<>();
        caseData.setClaimantRequests(claimantRequests);
        PdfMapperClaimDescriptionUtil.putClaimDescription(caseData, printFields);
        if (StringUtils.isBlank(expectedResult)) {
            assertThat(printFields.get(PdfMapperConstants.Q8_CLAIM_DESCRIPTION)).isNull();
        } else {
            assertThat(printFields.get(PdfMapperConstants.Q8_CLAIM_DESCRIPTION)).contains(expectedResult);
        }
    }
}
