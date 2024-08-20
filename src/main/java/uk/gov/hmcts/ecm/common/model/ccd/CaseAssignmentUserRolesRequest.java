package uk.gov.hmcts.ecm.common.model.ccd;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;
import lombok.extern.jackson.Jacksonized;

import java.util.List;

@Data
@Builder
@Jacksonized
public class CaseAssignmentUserRolesRequest {

    @JsonProperty("case_users")
    private List<CaseAssignmentUserRole> caseAssignmentUserRoles;

}
