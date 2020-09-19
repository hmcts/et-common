package uk.gov.hmcts.ecm.common.model.ccd.types;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@JsonIgnoreProperties(ignoreUnknown = true)
@Data
public class SingleMoveCasesType {

    @JsonProperty("leadCase")
    private String leadCase;
    @JsonProperty("updatedMultipleRef")
    private String updatedMultipleRef;
    @JsonProperty("updatedSubMultipleName")
    private String updatedSubMultipleName;
}
