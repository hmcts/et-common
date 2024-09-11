package uk.gov.hmcts.ecm.common.model.ccd;

import org.apache.commons.lang3.ObjectUtils;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class CaseAssignmentUserRolesRequestWithRespondentNameTest {

    private static final String TEST_CASE_DATA_ID = "test-case-data-id";
    private static final String TEST_CASE_ROLE = "test-case-role";
    private static final String TEST_CASE_USER_ID = "test-case-user-id";
    private static final String TEST_RESPONDENT_NAME = "test-respondent-name";

    @Test
    void theCaseAssignmentUserRolesRequestWithRespondentNameModel() {
        CaseAssignmentUserRole caseAssignmentUserRole =
                CaseAssignmentUserRole
                        .builder()
                        .caseDataId(TEST_CASE_DATA_ID)
                        .caseRole(TEST_CASE_ROLE)
                        .userId(TEST_CASE_USER_ID)
                        .build();
        CaseAssignmentUserRolesRequest caseAssignmentUserRolesRequest =
                CaseAssignmentUserRolesRequest
                        .builder()
                        .caseAssignmentUserRoles(List.of(caseAssignmentUserRole))
                        .build();
        CaseAssignmentUserRolesRequestWithRespondentName caseAssignmentUserRolesRequestWithRespondentName =
                CaseAssignmentUserRolesRequestWithRespondentName
                        .builder()
                        .respondentName(TEST_RESPONDENT_NAME)
                        .caseAssignmentUserRolesRequest(caseAssignmentUserRolesRequest)
                        .build();
        assertFalse(caseAssignmentUserRolesRequestWithRespondentName
                .getCaseAssignmentUserRolesRequest().getCaseAssignmentUserRoles().isEmpty());
        assertTrue(ObjectUtils.isNotEmpty(caseAssignmentUserRolesRequestWithRespondentName
                .getCaseAssignmentUserRolesRequest().getCaseAssignmentUserRoles().get(0)));
        assertThat(caseAssignmentUserRolesRequestWithRespondentName
                .getCaseAssignmentUserRolesRequest().getCaseAssignmentUserRoles().get(0).getCaseRole())
                .isEqualTo(TEST_CASE_ROLE);
        assertThat(caseAssignmentUserRolesRequestWithRespondentName
                .getCaseAssignmentUserRolesRequest().getCaseAssignmentUserRoles().get(0).getUserId())
                .isEqualTo(TEST_CASE_USER_ID);
        assertThat(caseAssignmentUserRolesRequestWithRespondentName
                .getCaseAssignmentUserRolesRequest().getCaseAssignmentUserRoles().get(0).getCaseDataId())
                .isEqualTo(TEST_CASE_DATA_ID);
    }

}
