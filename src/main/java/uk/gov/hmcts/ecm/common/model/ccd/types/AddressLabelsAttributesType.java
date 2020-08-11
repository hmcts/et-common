package uk.gov.hmcts.ecm.common.model.ccd.types;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@JsonIgnoreProperties(ignoreUnknown = true)
@Data
public class AddressLabelsAttributesType {

    @JsonProperty("numberOfSelectedLabels")
    private String numberOfSelectedLabels;
    @JsonProperty("numberOfCopies")
    private String numberOfCopies;
    @JsonProperty("startingLabel")
    private String startingLabel;
    @JsonProperty("showTelFax")
    private String showTelFax;
}
