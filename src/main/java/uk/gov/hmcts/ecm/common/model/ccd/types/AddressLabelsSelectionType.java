package uk.gov.hmcts.ecm.common.model.ccd.types;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@JsonIgnoreProperties(ignoreUnknown = true)
@Data
public class AddressLabelsSelectionType {

    @JsonProperty("claimantAddressLabel")
    private String claimantAddressLabel;
    @JsonProperty("claimantRepAddressLabel")
    private String claimantRepAddressLabel;
    @JsonProperty("respondentsAddressLabel")
    private String respondentsAddressLabel;
    @JsonProperty("respondentsRepsAddressLabel")
    private String respondentsRepsAddressLabel;
}
