package uk.gov.hmcts.ecm.common.model.ccd.types;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import uk.gov.hmcts.ecm.common.model.bulk.types.DynamicFixedListType;
import uk.gov.hmcts.ecm.common.model.ccd.items.DateListedTypeItem;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
@Data
public class HearingType {

    @JsonProperty("Hearing_type")
    private String hearingType;
    @JsonProperty("hearingPublicPrivate")
    private String hearingPublicPrivate;
    @JsonProperty("hearingNumber")
    private String hearingNumber;
    @JsonProperty("Hearing_venue")
    private DynamicFixedListType hearingVenue;
    @JsonProperty("hearingEstLengthNum")
    private String hearingEstLengthNum;
    @JsonProperty("hearingEstLengthNumType")
    private String hearingEstLengthNumType;
    @JsonProperty("hearingSitAlone")
    private String hearingSitAlone;
    @JsonProperty("hearingERMember")
    private DynamicFixedListType hearingERMember;
    @JsonProperty("hearingEEMember")
    private DynamicFixedListType hearingEEMember;
    @JsonProperty("Hearing_stage")
    private String hearingStage;
    @JsonProperty("Hearing_notes")
    private String hearingNotes;
    @JsonProperty("judge")
    private DynamicFixedListType judge;
    @JsonProperty("Hearing_Glasgow")
    private String hearingGlasgow;
    @JsonProperty("Hearing_Aberdeen")
    private String hearingAberdeen;
    @JsonProperty("Hearing_Dundee")
    private String hearingDundee;
    @JsonProperty("Hearing_Edinburgh")
    private String hearingEdinburgh;
    @JsonProperty("hearingDateCollection")
    private List<DateListedTypeItem> hearingDateCollection;
    @JsonProperty("hearingFormat")
    private List<String> hearingFormat;
    @JsonProperty("judicialMediation")
    private String judicialMediation;

    public boolean hasHearingJudge() {
        return judge != null && judge.getValue() != null;
    }

    public boolean hasHearingEmployerMember() {
        return hearingERMember != null && hearingERMember.getValue() != null;
    }

    public boolean hasHearingEmployeeMember() {
        return hearingEEMember != null && hearingEEMember.getValue() != null;
    }
}
