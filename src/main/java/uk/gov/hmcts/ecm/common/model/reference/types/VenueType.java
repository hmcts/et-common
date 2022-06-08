package uk.gov.hmcts.ecm.common.model.reference.types;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import uk.gov.hmcts.ecm.common.model.reference.items.VenueRoomTypeItem;
import uk.gov.hmcts.et.common.model.ccd.Address;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
@Data
public class VenueType {

    @JsonProperty("venueName")
    private String venueName;
    @JsonProperty("venueAddress")
    private Address venueAddress;
    @JsonProperty("venueTel")
    private String venueTel;
    @JsonProperty("venueEmail")
    private String venueEmail;
    @JsonProperty("venueOfficeManager")
    private String venueOfficeManager;
    @JsonProperty("venueOfficeManagerEmail")
    private String venueOfficeManagerEmail;
    @JsonProperty("venueRooms")
    private List<VenueRoomTypeItem> venueRooms;
}
