package uk.gov.hmcts.ecm.common.model.listing.types;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@JsonIgnoreProperties(ignoreUnknown = true)
@Data
public class ReportListingsType {

    @JsonProperty("listedDate")
    private String listedDate;
    @JsonProperty("hearingNumber")
    private String hearingNumber;
    @JsonProperty("hearingType")
    private String hearingType;
    @JsonProperty("hearingStatus")
    private String hearingStatus;
    @JsonProperty("hearingClerk")
    private String hearingClerk;
}
