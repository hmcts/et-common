package uk.gov.hmcts.ecm.common.model.multiples;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import uk.gov.hmcts.ecm.common.model.generic.GenericCaseDetails;

@JsonIgnoreProperties(ignoreUnknown = true)
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Data
public class MultipleDetails extends GenericCaseDetails {

    @JsonProperty("case_data")
    private MultipleData caseData;
}
