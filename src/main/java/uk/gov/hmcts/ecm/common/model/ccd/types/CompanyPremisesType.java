package uk.gov.hmcts.ecm.common.model.ccd.types;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import uk.gov.hmcts.ecm.common.model.ccd.Address;

@JsonIgnoreProperties(ignoreUnknown = true)
@Data
public class CompanyPremisesType {

    @JsonProperty("premises")
    private String premises;
    @JsonProperty("address")
    private Address address;

}
