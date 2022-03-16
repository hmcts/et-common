package uk.gov.hmcts.ecm.common.model.reports.hearingsbyhearingtype;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;
import lombok.Data;
import uk.gov.hmcts.ecm.common.model.ccd.items.HearingTypeItem;

@Data
public class HearingsByHearingTypeCaseData {

    @JsonProperty("ethosCaseReference")
    private String ethosCaseReference;

    @JsonProperty("hearingCollection")
    private List<HearingTypeItem> hearingCollection;

    @JsonProperty("managingOffice")
    private String managingOffice;

    @JsonProperty("multipleReference")
    private String multipleReference;

    @JsonProperty("subMultipleName")
    private String subMultipleName;

    @JsonProperty("leadClaimant")
    private String leadClaimant;

}

