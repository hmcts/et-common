package uk.gov.hmcts.ecm.common.model.listing.items;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import uk.gov.hmcts.ecm.common.model.listing.types.ReportRespondentType;

@JsonIgnoreProperties(ignoreUnknown = true)
@Data
public class ReportRespondentTypeItem {

    @JsonProperty("id")
    private String id;
    @JsonProperty("value")
    private ReportRespondentType value;
}
