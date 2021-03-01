package uk.gov.hmcts.ecm.common.model.ccd.types;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import uk.gov.hmcts.ecm.common.model.bulk.types.DynamicFixedListType;

@JsonIgnoreProperties(ignoreUnknown = true)
@Data
public class BFActionType {

    @JsonProperty("action")
    private DynamicFixedListType action;
    @JsonProperty("cwActions")
    private String cwActions;
    @JsonProperty("dateEntered")
    private String dateEntered;
    @JsonProperty("imported")
    private String imported;
    @JsonProperty("letters")
    private String letters;
    @JsonProperty("allActions")
    private String allActions;
    @JsonProperty("bfDate")
    private String bfDate;
    @JsonProperty("cleared")
    private String cleared;
    @JsonProperty("notes")
    private String notes;

}
