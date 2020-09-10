package uk.gov.hmcts.ecm.common.model.multiples.types;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@JsonIgnoreProperties(ignoreUnknown = true)
@Data
public class SubMultipleType {

    @JsonProperty("subMultipleName")
    private String subMultipleName;
    @JsonProperty("subMultipleRef")
    private String subMultipleRef;
}
