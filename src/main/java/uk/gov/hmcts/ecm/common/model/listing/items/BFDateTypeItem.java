package uk.gov.hmcts.ecm.common.model.listing.items;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import uk.gov.hmcts.ecm.common.model.listing.types.BFDateType;
import java.time.LocalDate;

@JsonIgnoreProperties(ignoreUnknown = true)
@Data
public class BFDateTypeItem {

    @JsonProperty("id")
    private String id;
    @JsonProperty("value")
    private BFDateType value;

    public int comparedTo(BFDateTypeItem bfDateTypeItem) {
        return LocalDate.parse(this.getValue().getBroughtForwardDate())
            .compareTo(LocalDate.parse(bfDateTypeItem.getValue().getBroughtForwardDate()));
    }
}
