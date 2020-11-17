package uk.gov.hmcts.ecm.common.model.listing.types;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@JsonIgnoreProperties(ignoreUnknown = true)
@Data
public class ReportRespondentType {

    @JsonProperty("et3ReceivedDate")
    private String et3ReceivedDate;
    @JsonProperty("respondentName")
    private String respondentName;
}
