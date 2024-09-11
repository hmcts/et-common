package uk.gov.hmcts.ecm.common.model.ccd;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;
import lombok.extern.jackson.Jacksonized;
import uk.gov.hmcts.et.common.model.ccd.Et1CaseData;

import java.util.List;

/**
 * Model for the modification of user case roles request.
 * This model includes list of case users of {@link ModifyCaseUserRole} type. The main difference between
 * {@link CaseAssignmentUserRolesRequest} and {@link ModifyCaseUserRolesRequest} is, it has 2 more fields as
 * userFullName and caseTypeId to find the correspondent respondent from {@link CaseData} object's
 * RespondentCollection attribute which is inherited from {@link Et1CaseData} to set idam id for the respondent object
 * of RespondentCollection. Used for Citizen ET3 Form.
 */
@Data
@Builder
@Jacksonized
public class ModifyCaseUserRolesRequest {

    @JsonProperty("case_users")
    private List<ModifyCaseUserRole> modifyCaseUserRoles;

}
