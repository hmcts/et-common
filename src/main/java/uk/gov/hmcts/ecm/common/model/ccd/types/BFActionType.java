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
    @JsonProperty("dateEntered")
    private String dateEntered;
    @JsonProperty("bfDate")
    private String bfDate;
    @JsonProperty("cleared")
    private String cleared;
    @JsonProperty("notes")
    private String notes;

}
