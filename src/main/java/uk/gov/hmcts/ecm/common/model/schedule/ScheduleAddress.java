package uk.gov.hmcts.ecm.common.model.schedule;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@JsonIgnoreProperties(ignoreUnknown = true)
@Data
public class ScheduleAddress {

    @JsonProperty("AddressLine1")
    private String addressLine1;
    @JsonProperty("PostCode")
    private String postCode;

}

