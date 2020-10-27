package uk.gov.hmcts.ecm.common.model.multiples.types;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@JsonIgnoreProperties(ignoreUnknown = true)
@Data
public class MultipleObjectType {

    @JsonProperty("ethosCaseRef")
    private String ethosCaseRef;
    @JsonProperty("subMultiple")
    private String subMultiple;
    @JsonProperty("flag1")
    private String flag1;
    @JsonProperty("flag2")
    private String flag2;
    @JsonProperty("flag3")
    private String flag3;
    @JsonProperty("flag4")
    private String flag4;
}
