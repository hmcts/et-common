package uk.gov.hmcts.ecm.common.model.ccd;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;
import lombok.extern.jackson.Jacksonized;

/**
 * Model for the modification of user case roles.
 * case type name can be ET_EnglandWales or ET_Scotland, user full name is the name of the user correspondent to the
 * user id. For case assignment process we are using this info as the Organisation Name or the full name of the
 * respondent to match one of the fields in the Respondent Model. Field names are respondentOrganisation,
 * respondent_name or respondent.
 */
@ToString
@Getter
@SuperBuilder
@Jacksonized
public class ModifyCaseUserRole extends CaseAssignmentUserRole {
    @JsonProperty("case_type_id")
    private String caseTypeId;
    @JsonProperty("respondent_name")
    private String respondentName;
}
