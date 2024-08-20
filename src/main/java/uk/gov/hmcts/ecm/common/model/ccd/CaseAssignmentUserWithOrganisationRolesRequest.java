package uk.gov.hmcts.ecm.common.model.ccd;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;
import lombok.extern.jackson.Jacksonized;

import java.util.List;

@Data
@Builder
@Jacksonized
public class CaseAssignmentUserWithOrganisationRolesRequest {

    @JsonProperty("case_users")
    private List<CaseAssignmentUserWithOrganisationRole> caseAssignmentUserRoles;

}
