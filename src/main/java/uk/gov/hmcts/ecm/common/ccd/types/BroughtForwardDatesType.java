package uk.gov.hmcts.ecm.common.ccd.types;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@JsonIgnoreProperties(ignoreUnknown = true)
@Data
public class BroughtForwardDatesType {

    @JsonProperty("broughtForwardDate")
    private String broughtForwardDate;
    @JsonProperty("broughtForwardDateReason")
    private String broughtForwardDateReason;
    @JsonProperty("broughtForwardDateComplete")
    private String broughtForwardDateComplete;
}
