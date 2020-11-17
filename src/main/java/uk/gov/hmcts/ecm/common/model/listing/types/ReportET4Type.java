package uk.gov.hmcts.ecm.common.model.listing.types;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@JsonIgnoreProperties(ignoreUnknown = true)
@Data
public class ReportET4Type {

    @JsonProperty("actioned")
    private String actioned;
    @JsonProperty("bfDate")
    private String bfDate;
    @JsonProperty("bfDateCleared")
    private String bfDateCleared;
    @JsonProperty("user")
    private String user;
}
