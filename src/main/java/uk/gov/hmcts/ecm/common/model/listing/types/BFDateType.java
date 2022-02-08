package uk.gov.hmcts.ecm.common.model.listing.types;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@JsonIgnoreProperties(ignoreUnknown = true)
@Data
public class BFDateType {

    @JsonProperty("caseReference")
    private String caseReference;
    @JsonProperty("broughtForwardAction")
    private String broughtForwardAction;
    @JsonProperty("broughtForwardDate")
    private String broughtForwardDate;
    @JsonProperty("broughtForwardDateCleared")
    private String broughtForwardDateCleared;
    @JsonProperty("broughtForwardEnteredDate")
    private String broughtForwardEnteredDate;
    @JsonProperty("broughtForwardDateReason")
    private String broughtForwardDateReason;
}
