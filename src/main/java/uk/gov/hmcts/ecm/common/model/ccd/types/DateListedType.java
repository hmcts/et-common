package uk.gov.hmcts.ecm.common.model.ccd.types;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import uk.gov.hmcts.ecm.common.model.bulk.types.DynamicFixedListType;

@JsonIgnoreProperties(ignoreUnknown = true)
@Data
public class DateListedType {

    @JsonProperty("listedDate")
    private String listedDate;
    @JsonProperty("Hearing_status")
    private String hearingStatus;
    @JsonProperty("Postponed_by")
    private String postponedBy;
    @JsonProperty("hearingVenueDay")
    private DynamicFixedListType hearingVenueDay;
    @JsonProperty("hearingVenueDayScotland")
    private String hearingVenueDayScotland;
    @JsonProperty("hearingRoom")
    private DynamicFixedListType hearingRoom;
    @JsonProperty("hearingClerk")
    private DynamicFixedListType hearingClerk;
    @JsonProperty("Hearing_typeReadingDeliberation")
    private String hearingTypeReadingDeliberation;
    @JsonProperty("Hearing_Glasgow")
    private DynamicFixedListType hearingGlasgow;
    @JsonProperty("Hearing_Aberdeen")
    private DynamicFixedListType hearingAberdeen;
    @JsonProperty("Hearing_Dundee")
    private DynamicFixedListType hearingDundee;
    @JsonProperty("Hearing_Edinburgh")
    private DynamicFixedListType hearingEdinburgh;
    @JsonProperty("hearingCaseDisposed")
    private String hearingCaseDisposed;
    @JsonProperty("Hearing_part_heard")
    private String hearingPartHeard;
    @JsonProperty("Hearing_reserved_judgement")
    private String hearingReservedJudgement;
    @JsonProperty("attendee_claimant")
    private String attendeeClaimant;
    @JsonProperty("attendee_non_attendees")
    private String attendeeNonAttendees;
    @JsonProperty("attendee_resp_no_rep")
    private String attendeeRespNoRep;
    @JsonProperty("attendee_resp_&_rep")
    private String attendeeRespAndRep;
    @JsonProperty("attendee_rep_only")
    private String attendeeRepOnly;
    @JsonProperty("hearingTimingStart")
    private String hearingTimingStart;
    @JsonProperty("hearingTimingBreak")
    private String hearingTimingBreak;
    @JsonProperty("hearingTimingResume")
    private String hearingTimingResume;
    @JsonProperty("hearingTimingFinish")
    private String hearingTimingFinish;
    @JsonProperty("hearingTimingDuration")
    private String hearingTimingDuration;
    @JsonProperty("HearingNotes2")
    private String hearingNotes2;
    @JsonProperty("postponedDate")
    private String postponedDate;

    public boolean hasHearingVenue() {
        return hearingVenueDay != null && hearingVenueDay.getValue() != null;
    }

    public boolean hasHearingRoom() {
        return hearingRoom != null && hearingRoom.getValue() != null;
    }

    public boolean hasHearingClerk() {
        return hearingClerk != null && hearingClerk.getValue() != null;
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