package uk.gov.hmcts.ecm.common.model.reference.types;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@JsonIgnoreProperties(ignoreUnknown = true)
@Data
public class VenueRoomType {

    @JsonProperty("room")
    private String room;
}
