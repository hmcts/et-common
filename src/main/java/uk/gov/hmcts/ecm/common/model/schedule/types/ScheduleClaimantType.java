package uk.gov.hmcts.ecm.common.model.schedule.types;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import uk.gov.hmcts.et.common.model.ccd.Address;

@JsonIgnoreProperties(ignoreUnknown = true)
@Data
public class ScheduleClaimantType {

    @JsonProperty("claimant_addressUK")
    private Address claimantAddressUK;
}
