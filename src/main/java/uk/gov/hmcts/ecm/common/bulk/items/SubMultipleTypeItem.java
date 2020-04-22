package uk.gov.hmcts.ecm.common.bulk.items;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import uk.gov.hmcts.ecm.common.bulk.types.SubMultipleType;

@JsonIgnoreProperties(ignoreUnknown = true)
@Data
public class SubMultipleTypeItem {

    @JsonProperty("id")
    private String id;
    @JsonProperty("value")
    private SubMultipleType value;
}
