package uk.gov.hmcts.ecm.common.model.ccd;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;
import lombok.extern.jackson.Jacksonized;

@ToString
@Getter
@Builder(toBuilder = true)
@Jacksonized
public class CaseAssignmentUserRole {

    @JsonProperty("case_id")
    private String caseDataId;

    @JsonProperty("case_type_id")
    private String caseTypeId;

    @JsonProperty("respondent_name")
    private String respondentName;

    @JsonProperty("user_id")
    private String userId;

    @JsonProperty("case_role")
    private String caseRole;
}
