package uk.gov.hmcts.ecm.common.model.reports.casesawaitingjudgment;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import uk.gov.hmcts.et.common.model.ccd.items.HearingTypeItem;
import uk.gov.hmcts.et.common.model.ccd.items.JudgementTypeItem;

import java.util.List;

@Data
public class CaseData {
    @JsonProperty("caseType")
    private String ecmCaseType;

    @JsonProperty("conciliationTrack")
    private String conciliationTrack;

    @JsonProperty("currentPosition")
    private String currentPosition;

    @JsonProperty("dateToPosition")
    private String dateToPosition;

    @JsonProperty("ethosCaseReference")
    private String ethosCaseReference;

    @JsonProperty("hearingCollection")
    private List<HearingTypeItem> hearingCollection;

    @JsonProperty("judgementCollection")
    private List<JudgementTypeItem> judgementCollection;

    @JsonProperty("multipleReference")
    private String multipleReference;

    @JsonProperty("positionType")
    private String positionType;
}
