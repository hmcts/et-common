package uk.gov.hmcts.ecm.common.model.reference;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import uk.gov.hmcts.ecm.common.model.reference.types.ClerkType;
import uk.gov.hmcts.ecm.common.model.reference.types.JudgeType;
import uk.gov.hmcts.ecm.common.model.reference.types.VenueType;

@JsonIgnoreProperties(ignoreUnknown = true)
@Data
public class ReferenceData {

    @JsonProperty("refDataType")
    private String refDataType;
    @JsonProperty("judges")
    private JudgeType judgeType;
    @JsonProperty("clerks")
    private ClerkType clerkType;
    @JsonProperty("venues")
    private VenueType venueType;
}
