package uk.gov.hmcts.ecm.common.model.reference.items;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import uk.gov.hmcts.ecm.common.model.reference.types.VenueRoomType;

@JsonIgnoreProperties(ignoreUnknown = true)
@Data
public class VenueRoomTypeItem {

    @JsonProperty("id")
    private String id;
    @JsonProperty("value")
    private VenueRoomType value;
}
