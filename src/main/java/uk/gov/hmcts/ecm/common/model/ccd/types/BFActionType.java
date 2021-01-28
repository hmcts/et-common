package uk.gov.hmcts.ecm.common.model.ccd.types;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@JsonIgnoreProperties(ignoreUnknown = true)
@Data
public class BFActionType {

    @JsonProperty("cwActions")
    private String cwActions;
    @JsonProperty("allActions")
    private String allActions;
    @JsonProperty("dateEntered")
    private String dateEntered;
    @JsonProperty("bfDate")
    private String bfDate;
    @JsonProperty("cleared")
    private String cleared;
    @JsonProperty("notes")
    private String notes;

}
