package uk.gov.hmcts.ecm.common.model.reports.sessiondays;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import uk.gov.hmcts.et.common.model.ccd.items.HearingTypeItem;

import java.util.List;

@Data
public class SessionDaysCaseData {

    @JsonProperty("ethosCaseReference")
    private String ethosCaseReference;

    @JsonProperty("hearingCollection")
    private List<HearingTypeItem> hearingCollection;

    @JsonProperty("managingOffice")
    private String managingOffice;

}
