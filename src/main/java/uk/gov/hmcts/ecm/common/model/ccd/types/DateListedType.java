package uk.gov.hmcts.ecm.common.model.ccd.types;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@JsonIgnoreProperties(ignoreUnknown = true)
@Data
public class DateListedType {

    @JsonProperty("listedDate")
    private String listedDate;
    @JsonProperty("Hearing_status")
    private String hearingStatus;
    @JsonProperty("Postponed_by")
    private String postponedBy;
    @JsonProperty("Hearing_typeReadingDeliberation")
    private String hearingTypeReadingDeliberation;
    @JsonProperty("hearingVenueDay")
    private String hearingVenueDay;
    @JsonProperty("Hearing_Glasgow")
    private String hearingGlasgow;
    @JsonProperty("Hearing_Aberdeen")
    private String hearingAberdeen;
    @JsonProperty("Hearing_Dundee")
    private String hearingDundee;
    @JsonProperty("Hearing_Edinburgh")
    private String hearingEdinburgh;
    @JsonProperty("Hearing_clerk")
    private String hearingClerk;
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

    //BRISTOL
    @JsonProperty("hearingRoomBarnstaple")
    private String hearingRoomBarnstaple;
    @JsonProperty("hearingRoomBathLawCourts")
    private String hearingRoomBathLawCourts;
    @JsonProperty("hearingRoomBodmin")
    private String hearingRoomBodmin;
    @JsonProperty("hearingRoomBournemouthMC")
    private String hearingRoomBournemouthMC;
    @JsonProperty("hearingRoomBrighton")
    private String hearingRoomBrighton;
    @JsonProperty("hearingRoomBrightonCC")
    private String hearingRoomBrightonCC;
    @JsonProperty("hearingRoomBristol")
    private String hearingRoomBristol;
    @JsonProperty("hearingRoomBristol1")
    private String hearingRoomBristol1;
    @JsonProperty("hearingRoomBristol2")
    private String hearingRoomBristol2;
    @JsonProperty("hearingRoomBristol3")
    private String hearingRoomBristol3;
    @JsonProperty("hearingRoomBristol4")
    private String hearingRoomBristol4;
    @JsonProperty("hearingRoomBristol5")
    private String hearingRoomBristol5;
    @JsonProperty("hearingRoomBristol6")
    private String hearingRoomBristol6;
    @JsonProperty("hearingRoomBristolCFJC")
    private String hearingRoomBristolCFJC;
    @JsonProperty("hearingRoomBristolCrown")
    private String hearingRoomBristolCrown;
    @JsonProperty("hearingRoomBristolMags")
    private String hearingRoomBristolMags;
    @JsonProperty("hearingRoomBristolTBC")
    private String hearingRoomBristolTBC;
    @JsonProperty("hearingRoomBristolVintry")
    private String hearingRoomBristolVintry;
    @JsonProperty("hearingRoomBristolCityHall")
    private String hearingRoomBristolCityHall;
    @JsonProperty("hearingRoomCardiffCFJC")
    private String hearingRoomCardiffCFJC;
    @JsonProperty("hearingRoomExeter")
    private String hearingRoomExeter;
    @JsonProperty("hearingRoomExeterCombined")
    private String hearingRoomExeterCombined;
    @JsonProperty("hearingRoomExeterMags")
    private String hearingRoomExeterMags;
    @JsonProperty("hearingRoomFloatingCase")
    private String hearingRoomFloatingCase;
    @JsonProperty("hearingRoomGloucesterCCFC")
    private String hearingRoomGloucesterCCFC;
    @JsonProperty("hearingRoomHavant")
    private String hearingRoomHavant;
    @JsonProperty("hearingRoomPlymStCathHo")
    private String hearingRoomPlymStCathHo;
    @JsonProperty("hearingRoomPlymouthMags")
    private String hearingRoomPlymouthMags;
    @JsonProperty("hearingRoomQueenswayHouse")
    private String hearingRoomQueenswayHouse;
    @JsonProperty("hearingRoomROIT")
    private String hearingRoomROIT;
    @JsonProperty("hearingRoomSouthampton")
    private String hearingRoomSouthampton;
    @JsonProperty("hearingRoomSwindon")
    private String hearingRoomSwindon;
    @JsonProperty("hearingRoomSwindon2")
    private String hearingRoomSwindon2;
    @JsonProperty("hearingRoomTaunton")
    private String hearingRoomTaunton;
    @JsonProperty("hearingRoomTruro")
    private String hearingRoomTruro;
    @JsonProperty("hearingRoomTruroMags")
    private String hearingRoomTruroMags;
    @JsonProperty("hearingRoomUWEBristol")
    private String hearingRoomUWEBristol;
    @JsonProperty("hearingRoomVintryHouse")
    private String hearingRoomVintryHouse;
    @JsonProperty("hearingRoomWorle")
    private String hearingRoomWorle;
    @JsonProperty("hearingRoomYeovilMags")
    private String hearingRoomYeovilMags;

    //LEEDS
    @JsonProperty("hearingRoomHarrogateCJC")
    private String hearingRoomHarrogateCJC;
    @JsonProperty("hearingRoomHull")
    private String hearingRoomHull;
    @JsonProperty("hearingRoomIAC")
    private String hearingRoomIAC;
    @JsonProperty("hearingRoomLeeds")
    private String hearingRoomLeeds;
    @JsonProperty("hearingRoomScarborough")
    private String hearingRoomScarborough;
    @JsonProperty("hearingRoomSheffieldCombined")
    private String hearingRoomSheffieldCombined;
    @JsonProperty("hearingRoomTeesside")
    private String hearingRoomTeesside;
    @JsonProperty("hearingRoomWakefield")
    private String hearingRoomWakefield;
    @JsonProperty("hearingRoomHullCombined")
    private String hearingRoomHullCombined;

    //LONDON CENTRAL
    @JsonProperty("hearingRoomFieldHouse")
    private String hearingRoomFieldHouse;
    @JsonProperty("hearingRoomFoxCourt")
    private String hearingRoomFoxCourt;
    @JsonProperty("hearingRoomLondonCentral")
    private String hearingRoomLondonCentral;
    @JsonProperty("hearingRoomRCJ")
    private String hearingRoomRCJ;

    //LONDON EAST
    @JsonProperty("hearingRoomColchester")
    private String hearingRoomColchester;
    @JsonProperty("hearingRoomEastLondon")
    private String hearingRoomEastLondon;
    @JsonProperty("hearingRoomStratford")
    private String hearingRoomStratford;
    @JsonProperty("hearingRoomWoburnPlace")
    private String hearingRoomWoburnPlace;

    //LONDON SOUTH
    @JsonProperty("hearingRoomAshford")
    private String hearingRoomAshford;
    @JsonProperty("hearingRoomCroydon")
    private String hearingRoomCroydon;
    @JsonProperty("hearingRoomLondon")
    private String hearingRoomLondon;

    //MANCHESTER
    @JsonProperty("Hearing_room_M")
    private String hearingRoomM;
    @JsonProperty("Hearing_room_L")
    private String hearingRoomL;
    @JsonProperty("Hearing_room_CM")
    private String hearingRoomCM;
    @JsonProperty("Hearing_room_CC")
    private String hearingRoomCC;
    @JsonProperty("hearingRoomCrownCourt")
    private String hearingRoomCrownCourt;
    @JsonProperty("hearingRoomKendal")
    private String hearingRoomKendal;
    //@JsonProperty("hearingRoomLeeds")
    //private String hearingRoomLeeds;
    @JsonProperty("hearingRoomMinshullSt")
    private String hearingRoomMinshullSt;
    @JsonProperty("hearingRoomMancMagistrate")
    private String hearingRoomMancMagistrate;
    @JsonProperty("hearingRoomBlackpoolMag")
    private String hearingRoomBlackpoolMag;
    @JsonProperty("hearingRoomTheLowry")
    private String hearingRoomTheLowry;

    //MIDLANDS EAST
    @JsonProperty("hearingRoomBedford")
    private String hearingRoomBedford;
    @JsonProperty("hearingRoomBostonCourtHo")
    private String hearingRoomBostonCourtHo;
    @JsonProperty("hearingRoomBostonSessions")
    private String hearingRoomBostonSessions;
    @JsonProperty("hearingRoomIlkeston")
    private String hearingRoomIlkeston;
    @JsonProperty("hearingRoomLeicester")
    private String hearingRoomLeicester;
    @JsonProperty("hearingRoomLincoln")
    private String hearingRoomLincoln;
    //@JsonProperty("hearingRoomLondonCentral")
    //private String hearingRoomLondonCentral;
    @JsonProperty("hearingRoomNewcastle")
    private String hearingRoomNewcastle;
    @JsonProperty("hearingRoomNottingham")
    private String hearingRoomNottingham;
    @JsonProperty("hearingRoomNottinghamold")
    private String hearingRoomNottinghamold;
    @JsonProperty("hearingRoomSleaford")
    private String hearingRoomSleaford;
    @JsonProperty("hearingRoomSpalding")
    private String hearingRoomSpalding;
    @JsonProperty("hearingRoomTeeside")
    private String hearingRoomTeeside;

    //MIDLANDS WEST
    @JsonProperty("hearingRoomBCJC")
    private String hearingRoomBCJC;
    @JsonProperty("hearingRoomBirminghamCCT")
    private String hearingRoomBirminghamCCT;
    @JsonProperty("hearingRoomBuryStEdmunds")
    private String hearingRoomBuryStEdmunds;
    @JsonProperty("hearingRoomCambridge")
    private String hearingRoomCambridge;
    @JsonProperty("hearingRoomHerefordCourt")
    private String hearingRoomHerefordCourt;
    //@JsonProperty("hearingRoomLeicester")
    //private String hearingRoomLeicester;
    @JsonProperty("hearingRoomStoke")
    private String hearingRoomStoke;
    @JsonProperty("hearingRoomStokeHanley")
    private String hearingRoomStokeHanley;
    @JsonProperty("hearingRoomTelford")
    private String hearingRoomTelford;

    //NEWCASTLE
    @JsonProperty("hearingRoomCarlisle")
    private String hearingRoomCarlisle;
    @JsonProperty("hearingRoomKendalMags")
    private String hearingRoomKendalMags;
    @JsonProperty("hearingRoomKingsCourt")
    private String hearingRoomKingsCourt;
    @JsonProperty("hearingRoomLeedsET")
    private String hearingRoomLeedsET;
    @JsonProperty("hearingRoomLondonSouthET")
    private String hearingRoomLondonSouthET;
    @JsonProperty("hearingRoomManorview")
    private String hearingRoomManorview;
    @JsonProperty("hearingRoomMiddlesbrough")
    private String hearingRoomMiddlesbrough;
    @JsonProperty("hearingRoomNewcastleCFT")
    private String hearingRoomNewcastleCFT;
    @JsonProperty("hearingRoomNewcastleET")
    private String hearingRoomNewcastleET;
    @JsonProperty("hearingRoomTeessideMags")
    private String hearingRoomTeessideMags;
    @JsonProperty("hearingRoomWhitby")
    private String hearingRoomWhitby;

    //SCOTLAND
    @JsonProperty("Hearing_room_Dundee")
    private String HearingRoomDundee;
    @JsonProperty("Hearing_room_Edinburgh")
    private String hearingRoomEdinburgh;
    @JsonProperty("Hearing_room_Glasgow")
    private String hearingRoomGlasgow;
    @JsonProperty("Hearing_room_GTC")
    private String hearingRoomGTC;
    @JsonProperty("Hearing_room_Cambeltown")
    private String hearingRoomCambeltown;
    @JsonProperty("Hearing_room_Dumfries")
    private String hearingRoomDumfries;
    @JsonProperty("Hearing_room_Oban")
    private String hearingRoomOban;
    @JsonProperty("Hearing_room_FortWilliam")
    private String hearingRoomFortWilliam;
    @JsonProperty("Hearing_room_Kirkcudbright")
    private String hearingRoomKirkcubright;
    @JsonProperty("Hearing_room_Lochmaddy")
    private String hearingRoomLockmaddy;
    @JsonProperty("Hearing_room_Portree")
    private String hearingRoomPortree;
    @JsonProperty("Hearing_room_Stirling")
    private String hearingRoomStirling;
    @JsonProperty("Hearing_room_StornowaySC")
    private String hearingRoomStornowaySC;
    @JsonProperty("Hearing_room_Stranraer")
    private String hearingRoomStranraer;
    @JsonProperty("Hearing_room_Aberdeen")
    private String hearingRoomAberdeen;
    @JsonProperty("Hearing_room_Lerwick")
    private String hearingRoomLerwick;
    @JsonProperty("Hearing_room_RRShetland")
    private String hearingRoomRRShetland;
    @JsonProperty("Hearing_room_Stornoway")
    private String hearingRoomStornoway;
    @JsonProperty("Hearing_room_Wick")
    private String hearingRoomWick;
    @JsonProperty("Hearing_room_IJC")
    private String hearingRoomIJC;
    @JsonProperty("Hearing_room_Inverness")
    private String hearingRoomInverness;
    @JsonProperty("Hearing_room_Kirkwall")
    private String hearingRoomKirkawall;
    @JsonProperty("roomDundeeTribunal")
    private String roomDundeeTribunal;

    //WALES
    @JsonProperty("hearingRoomAbergele")
    private String hearingRoomAbergele;
    @JsonProperty("hearingRoomAberystwyth_CC")
    private String hearingRoomAberystwythCC;
    @JsonProperty("hearingRoomAberystwyth_JC")
    private String hearingRoomAberystwythJC;
    @JsonProperty("hearingRoomAberystwyth_Mag")
    private String hearingRoomAberystwythMag;
    @JsonProperty("hearingRoomBrecon_Court")
    private String hearingRoomBreconCourt;
    @JsonProperty("hearingRoomCaernarfon_CC")
    private String hearingRoomCaernarfonCC;
    @JsonProperty("hearingRoomCaernarfon_CJC")
    private String hearingRoomCaernarfonCJC;
    @JsonProperty("hearingRoomCardiff")
    private String hearingRoomCardiff;
    @JsonProperty("hearingRoomCardiff_CJC")
    private String hearingRoomCardiffCJC;
    @JsonProperty("hearingRoomCardiff_Crown")
    private String hearingRoomCardiffCrown;
    @JsonProperty("hearingRoomCardiff_Mag_YC1")
    private String hearingRoomCardiffMagYC1;
    @JsonProperty("hearingRoomCardiff_Mag_YC2")
    private String hearingRoomCardiffMagYC2;
    @JsonProperty("hearingRoomCardiff_Mags")
    private String hearingRoomCardiffMags;
    @JsonProperty("hearingRoomCardiffSocial")
    private String hearingRoomCardiffSocial;
    @JsonProperty("hearingRoomCARMARTHEN")
    private String hearingRoomCARMARTHEN;
    @JsonProperty("hearingRoomCarmarthen_CC")
    private String hearingRoomCarmarthenCC;
    @JsonProperty("hearingRoomCARMARTHEN_DC")
    private String hearingRoomCARMARTHENDC;
    @JsonProperty("hearingRoomCarmarthen_HH")
    private String hearingRoomCarmarthenHH;
    @JsonProperty("hearingRoomChester_County")
    private String hearingRoomChesterCounty;
    @JsonProperty("hearingRoomChester_Mags")
    private String hearingRoomChesterMags;
    @JsonProperty("hearingRoomDenbigh_Mags")
    private String hearingRoomDenbighMags;
    @JsonProperty("hearingRoomDolgellau_Court")
    private String hearingRoomDolgellauCourt;
    @JsonProperty("hearingRoomFlint_Mags")
    private String hearingRoomFlintMags;
    @JsonProperty("hearingRoomHaverfordwest_M")
    private String hearingRoomHaverfordwestM;
    @JsonProperty("hearingRoomHaverfordwestCC")
    private String hearingRoomHaverfordwestCC;
    @JsonProperty("hearingRoomHEREFORD")
    private String hearingRoomHEREFORD;
    @JsonProperty("hearingRoomHolyhead_Mags")
    private String hearingRoomHolyheadMags;
    @JsonProperty("hearingRoomLEDBURY")
    private String hearingRoomLEDBURY;
    @JsonProperty("hearingRoomLlandrindod_W")
    private String hearingRoomLlandrindodW;
    @JsonProperty("hearingRoomLlandudno_Mags")
    private String hearingRoomLlandudnoMags;
    @JsonProperty("hearingRoomLlanelli_County")
    private String hearingRoomLlanelliCounty;
    @JsonProperty("hearingRoomLlanelli_Mags")
    private String hearingRoomLlanelliMags;
    @JsonProperty("hearingRoomLlangefni_Court")
    private String hearingRoomLlangefniCourt;
    @JsonProperty("hearingRoomLlangefni_Mags")
    private String hearingRoomLlangefniMags;
    @JsonProperty("hearingRoomMold_Court")
    private String hearingRoomMoldCourt;
    @JsonProperty("hearingRoomNeath_Mags")
    private String hearingRoomNeathMags;
    @JsonProperty("hearingRoomNewport")
    private String hearingRoomNewport;
    @JsonProperty("hearingRoomNewport_AIT")
    private String hearingRoomNewportAIT;
    @JsonProperty("hearingRoomPort_Talbot")
    private String hearingRoomPortTalbot;
    @JsonProperty("hearingRoomPrestatyn_JC")
    private String hearingRoomPrestatynJC;
    @JsonProperty("hearingRoomRhyl_Court")
    private String hearingRoomRhylCourt;
    @JsonProperty("hearingRoomShrewsbury")
    private String hearingRoomShrewsbury;
    @JsonProperty("hearingRoomSwansea_CJ")
    private String hearingRoomSwanseaCJ;
    @JsonProperty("hearingRoomSwansea_Mags")
    private String hearingRoomSwanseaMags;
    @JsonProperty("hearingRoomTowyn_&_Kinmel")
    private String hearingRoomTowynKinmel;
    @JsonProperty("hearingRoomWalesRooms")
    private String hearingRoomWalesRooms;
    @JsonProperty("hearingRoomWelshpool_Mags")
    private String hearingRoomWelshpoolMags;
    @JsonProperty("hearingRoomWrexham_Courts")
    private String hearingRoomWrexhamCourts;
    @JsonProperty("hearingRoomWrexham_SSCSA")
    private String hearingRoomWrexhamSSCSA;
    @JsonProperty("hearingRoomCardiffSSCS")
    private String hearingRoomCardiffSSCS;

    //WATFORD
    @JsonProperty("hearingRoomAmersham")
    private String hearingRoomAmersham;
    @JsonProperty("hearingRoomAylesburyCrown")
    private String hearingRoomAylesburyCrown;
    //@JsonProperty("hearingRoomBedford")
    //private String hearingRoomBedford;
    @JsonProperty("hearingRoomBuryStEd")
    private String hearingRoomBuryStEd;
    //@JsonProperty("hearingRoomCambridge")
    //private String hearingRoomCambridge;
    @JsonProperty("hearingRoomHuntingdon")
    private String hearingRoomHuntingdon;
    @JsonProperty("hearingRoomPeterborough")
    private String hearingRoomPeterborough;
    @JsonProperty("hearingRoomReading")
    private String hearingRoomReading;
    @JsonProperty("hearingRoomWatford")
    private String hearingRoomWatford;

}