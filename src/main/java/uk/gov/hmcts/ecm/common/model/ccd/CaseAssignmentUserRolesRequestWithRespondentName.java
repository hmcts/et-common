package uk.gov.hmcts.ecm.common.model.ccd;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;
import lombok.extern.jackson.Jacksonized;

@Data
@Builder
@Jacksonized
public class CaseAssignmentUserRolesRequestWithRespondentName {

    @JsonProperty("respondent_name")
    private String respondentName;
    @JsonProperty("case_type_id")
    private String caseTypeId;
    @JsonProperty("case_assignment_user_roles_request")
    private CaseAssignmentUserRolesRequest caseAssignmentUserRolesRequest;

}
