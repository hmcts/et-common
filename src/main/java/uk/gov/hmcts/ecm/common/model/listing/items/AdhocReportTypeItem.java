package uk.gov.hmcts.ecm.common.model.listing.items;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import uk.gov.hmcts.ecm.common.model.listing.types.AdhocReportType;

@JsonIgnoreProperties(ignoreUnknown = true)
@Data
public class AdhocReportTypeItem {

    @JsonProperty("id")
    private String id;
    @JsonProperty("value")
    private AdhocReportType value;
}
