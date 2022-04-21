package uk.gov.hmcts.ecm.common.model.reports.hearingstojudgments;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import uk.gov.hmcts.et.common.model.ccd.items.HearingTypeItem;
import uk.gov.hmcts.et.common.model.ccd.items.JudgementTypeItem;

import java.util.List;

@Data
public class HearingsToJudgmentsCaseData {
    @JsonProperty("ethosCaseReference")
    private String ethosCaseReference;

    @JsonProperty("hearingCollection")
    private List<HearingTypeItem> hearingCollection;

    @JsonProperty("judgementCollection")
    private List<JudgementTypeItem> judgementCollection;

    @JsonProperty("managingOffice")
    private String managingOffice;
}
