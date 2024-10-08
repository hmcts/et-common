package uk.gov.hmcts.ecm.common.model.ccd;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;
import lombok.extern.jackson.Jacksonized;

@ToString
@Getter
@SuperBuilder(toBuilder = true)
@Jacksonized
public class CaseAssignmentUserRole {

    @JsonProperty("case_id")
    private String caseDataId;

    @JsonProperty("user_id")
    private String userId;

    @JsonProperty("case_role")
    private String caseRole;
}
