package uk.gov.hmcts.ecm.common.model.reference;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import uk.gov.hmcts.et.common.model.generic.GenericCaseDetails;

@EqualsAndHashCode(callSuper = true)
@JsonIgnoreProperties(ignoreUnknown = true)
@Data
public class ReferenceDetails extends GenericCaseDetails {

    @JsonProperty("case_data")
    private ReferenceData caseData;
}
