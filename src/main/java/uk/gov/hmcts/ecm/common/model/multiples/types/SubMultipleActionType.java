package uk.gov.hmcts.ecm.common.model.multiples.types;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@JsonIgnoreProperties(ignoreUnknown = true)
@Data
public class SubMultipleActionType {

    @JsonProperty("actionType")
    private String actionType;

}
