package uk.gov.hmcts.ecm.common.model.reports.claimsbyhearingvenue;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import uk.gov.hmcts.et.common.model.generic.GenericSubmitEvent;

@Getter
@Setter
@Data
@EqualsAndHashCode(callSuper = true)
@JsonIgnoreProperties(ignoreUnknown = true)
public class ClaimsByHearingVenueSubmitEvent extends GenericSubmitEvent {
    @JsonProperty("case_data")
    private ClaimsByHearingVenueCaseData caseData;
}
