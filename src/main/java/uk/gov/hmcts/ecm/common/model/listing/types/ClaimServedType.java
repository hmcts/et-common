package uk.gov.hmcts.ecm.common.model.listing.types;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

@JsonIgnoreProperties(ignoreUnknown = true)
@Data
public class ClaimServedType {
    // For days less than 6, this is the same as the actual number of days.
    // But for 6 days or more of serving claim, this might differ from
    // the actual number of days. E.g. 12 days of serving claim
    // results in 6+ reported and 12 actual days in the Serving Claims report
    @JsonProperty("reportedNumberOfDays")
    private String reportedNumberOfDays;
    @JsonProperty("actualNumberOfDays")
    private String actualNumberOfDays;

    // Claim accepted or rejected
    @JsonProperty("claimServedType")
    private String claimServedType;
    @JsonProperty("claimServedCaseNumber")
    private String claimServedCaseNumber;
    @JsonProperty("caseReceiptDate")
    private String caseReceiptDate;
    @JsonProperty("claimServedDate")
    private String claimServedDate;
}
