package uk.gov.hmcts.ecm.common.model.schedule;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import uk.gov.hmcts.ecm.common.model.schedule.types.ScheduleClaimantIndType;
import uk.gov.hmcts.ecm.common.model.schedule.types.ScheduleClaimantType;
import uk.gov.hmcts.et.common.model.ccd.items.RespondentSumTypeItem;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
@Data
public class SchedulePayloadES {

    @JsonProperty("claimantIndType")
    private ScheduleClaimantIndType claimantIndType;
    @JsonProperty("claimantType")
    private ScheduleClaimantType claimantType;
    @JsonProperty("claimant_Company")
    private String claimantCompany;

    @JsonProperty("positionType")
    private String positionType;
    @JsonProperty("ethosCaseReference")
    private String ethosCaseReference;

    @JsonProperty("respondentCollection")
    private List<RespondentSumTypeItem> respondentCollection;
    //private List<ScheduleRespondentSumTypeItem> respondentCollection;
}

