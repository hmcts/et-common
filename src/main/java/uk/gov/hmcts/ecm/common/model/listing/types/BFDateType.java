package uk.gov.hmcts.ecm.common.model.listing.types;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@JsonIgnoreProperties(ignoreUnknown = true)
@Data
public class BFDateType {

    @JsonProperty("caseNumber")
    private String caseNumber;
    @JsonProperty("action")
    private String action;
    @JsonProperty("dateEntered")
    private String dateEntered;
    @JsonProperty("bfDate")
    private String bfDate;
    @JsonProperty("cleared")
    private String cleared;
    @JsonProperty("notes")
    private String notes;

}
