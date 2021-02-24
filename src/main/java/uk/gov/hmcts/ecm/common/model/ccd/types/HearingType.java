package uk.gov.hmcts.ecm.common.model.ccd.types;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
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
    private String hearingVenue;
    @JsonProperty("hearingEstLengthNum")
    private String hearingEstLengthNum;
    @JsonProperty("hearingEstLengthNumType")
    private String hearingEstLengthNumType;
    @JsonProperty("hearingSitAlone")
    private String hearingSitAlone;
    @JsonProperty("hearingERMember")
    private String hearingERMember;
    @JsonProperty("hearingEEMember")
    private String hearingEEMember;
    @JsonProperty("Hearing_stage")
    private String hearingStage;
    @JsonProperty("Hearing_notes")
    private String hearingNotes;
    @JsonProperty("judge")
    private String judge;
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
}
