package uk.gov.hmcts.ecm.common.model.multiples.types;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@JsonIgnoreProperties(ignoreUnknown = true)
@Data
public class MoveCasesType {

    @JsonProperty("convertToSingle")
    private String convertToSingle;
    @JsonProperty("updatedMultipleRef")
    private String updatedMultipleRef;
    @JsonProperty("updatedSubMultipleRef")
    private String updatedSubMultipleRef;
}
