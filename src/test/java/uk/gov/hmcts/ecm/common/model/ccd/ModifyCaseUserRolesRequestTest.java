package uk.gov.hmcts.ecm.common.model.ccd;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class ModifyCaseUserRolesRequestTest {

    @Test
    void theModifyCaseUserRolesRequestModel() {
        ModifyCaseUserRolesRequest modifyCaseUserRolesRequest = ModifyCaseUserRolesRequest.builder().build();
        assertThat(modifyCaseUserRolesRequest).isNotNull();
        assertThat(modifyCaseUserRolesRequest.getModifyCaseUserRoles()).isNullOrEmpty();

        ModifyCaseUserRole modifyCaseUserRoleEmpty = ModifyCaseUserRole.builder().build();
        ModifyCaseUserRolesRequest modifyCaseUserRolesRequestWithEmptyCaseUserRole =
                ModifyCaseUserRolesRequest.builder().modifyCaseUserRoles(List.of(modifyCaseUserRoleEmpty)).build();
        assertThat(modifyCaseUserRolesRequestWithEmptyCaseUserRole).isNotNull();
        assertThat(modifyCaseUserRolesRequestWithEmptyCaseUserRole.getModifyCaseUserRoles()).isNotNull();
        assertThat(modifyCaseUserRolesRequestWithEmptyCaseUserRole.getModifyCaseUserRoles()).isNotEmpty();
        assertThat(modifyCaseUserRolesRequestWithEmptyCaseUserRole.getModifyCaseUserRoles().get(0))
                .isExactlyInstanceOf(ModifyCaseUserRole.class);
    }

}
