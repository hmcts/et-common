package uk.gov.hmcts.ecm.common.model.ccd;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class CaseAssignmentUserRolesRequestTest {

    private static final String TEST_CASE_DATA_ID = "test_case_data_id";
    private static final String TEST_USER_ID = "test_user_id";
    private static final String TEST_USER_ROLE = "test_user_role";

    @Test
    void theCaseAssignmentUserRolesRequestTestModel() {
        CaseAssignmentUserRolesRequest caseAssignmentUserRolesRequestEmpty = CaseAssignmentUserRolesRequest
                .builder().build();
        assertThat(caseAssignmentUserRolesRequestEmpty).isNotNull();
        assertThat(caseAssignmentUserRolesRequestEmpty.getCaseAssignmentUserRoles()).isNullOrEmpty();

        CaseAssignmentUserRole caseAssignmentUserRole = CaseAssignmentUserRole
                .builder()
                .caseDataId(TEST_CASE_DATA_ID)
                .userId(TEST_USER_ID)
                .caseRole(TEST_USER_ROLE)
                .build();
        CaseAssignmentUserRolesRequest caseAssignmentUserRolesRequestNotEmpty = CaseAssignmentUserRolesRequest
                .builder().caseAssignmentUserRoles(List.of(caseAssignmentUserRole)).build();
        assertThat(caseAssignmentUserRolesRequestNotEmpty).isNotNull();
        assertThat(caseAssignmentUserRolesRequestNotEmpty.getCaseAssignmentUserRoles()).isNotNull();
        assertThat(caseAssignmentUserRolesRequestNotEmpty.getCaseAssignmentUserRoles()).hasSize(1);
        assertThat(caseAssignmentUserRolesRequestNotEmpty.getCaseAssignmentUserRoles()
                .get(0).getCaseRole()).isEqualTo(TEST_USER_ROLE);
        assertThat(caseAssignmentUserRolesRequestNotEmpty.getCaseAssignmentUserRoles().get(0)
                .getCaseDataId()).isEqualTo(TEST_CASE_DATA_ID);
        assertThat(caseAssignmentUserRolesRequestNotEmpty.getCaseAssignmentUserRoles().get(0)
                .getUserId()).isEqualTo(TEST_USER_ID);
    }
}
