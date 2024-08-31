package uk.gov.hmcts.ecm.common.model.ccd;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;
import lombok.extern.jackson.Jacksonized;

import java.util.List;

@Data
@Builder
@Jacksonized
public class SearchCaseAssignedUserRolesRequest {
    @JsonProperty("case_ids")
    private List<String> caseIds;

    @JsonProperty("user_ids")
    private List<String> userIds;

}
