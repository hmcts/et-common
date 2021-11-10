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
    @JsonProperty("Hearing_venue_Scotland")
    private String hearingVenueScotland;
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
    private DynamicFixedListType hearingGlasgow;
    @JsonProperty("Hearing_Aberdeen")
    private DynamicFixedListType hearingAberdeen;
    @JsonProperty("Hearing_Dundee")
    private DynamicFixedListType hearingDundee;
    @JsonProperty("Hearing_Edinburgh")
    private DynamicFixedListType hearingEdinburgh;
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

    public boolean hasHearingGlasgow() {
        return hearingGlasgow != null && hearingGlasgow.getValue() != null;
    }

    public boolean hasHearingAberdeen() {
        return hearingAberdeen != null && hearingAberdeen.getValue() != null;
    }

    public boolean hasHearingDundee() {
        return hearingDundee != null && hearingDundee.getValue() != null;
    }

    public boolean hasHearingEdinburgh() {
        return hearingEdinburgh != null && hearingEdinburgh.getValue() != null;
    }
}
