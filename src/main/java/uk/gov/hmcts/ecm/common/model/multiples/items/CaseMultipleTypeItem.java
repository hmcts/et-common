package uk.gov.hmcts.ecm.common.model.multiples.items;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import uk.gov.hmcts.ecm.common.model.multiples.types.MultipleObjectType;

@JsonIgnoreProperties(ignoreUnknown = true)
@Data
public class CaseMultipleTypeItem {

    @JsonProperty("id")
    private String id;
    @JsonProperty("value")
    private MultipleObjectType value;
}
