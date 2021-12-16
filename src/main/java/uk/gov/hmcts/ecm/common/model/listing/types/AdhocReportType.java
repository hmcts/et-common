package uk.gov.hmcts.ecm.common.model.listing.types;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import uk.gov.hmcts.ecm.common.model.listing.items.ReportET4TypeItem;
import uk.gov.hmcts.ecm.common.model.listing.items.ReportListingsTypeItem;
import uk.gov.hmcts.ecm.common.model.listing.items.ReportRespondentTypeItem;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
@Data
public class AdhocReportType {

    @JsonProperty("reportDate")
    private String reportDate;
    @JsonProperty("reportOffice")
    private String reportOffice;
    @JsonProperty("receiptDate")
    private String receiptDate;
    @JsonProperty("hearingDate")
    private String hearingDate;
    @JsonProperty("date")
    private String date;
    @JsonProperty("full")
    private String full;
    @JsonProperty("half")
    private String half;
    @JsonProperty("mins")
    private String mins;
    @JsonProperty("total")
    private String total;
    @JsonProperty("eeMember")
    private String eeMember;
    @JsonProperty("erMember")
    private String erMember;
    @JsonProperty("caseReference")
    private String caseReference;
    @JsonProperty("multipleRef")
    private String multipleRef;
    @JsonProperty("multSub")
    private String multSub;
    @JsonProperty("hearingNumber")
    private String hearingNumber;
    @JsonProperty("hearingType")
    private String hearingType;
    @JsonProperty("hearingTelConf")
    private String hearingTelConf;
    @JsonProperty("hearingDuration")
    private String hearingDuration;
    @JsonProperty("hearingClerk")
    private String hearingClerk;
    @JsonProperty("clerk")
    private String clerk;
    @JsonProperty("hearingSitAlone")
    private String hearingSitAlone;
    @JsonProperty("hearingJudge")
    private String hearingJudge;
    @JsonProperty("judgeType")
    private String judgeType;
    @JsonProperty("judgementDateSent")
    private String judgementDateSent;
    @JsonProperty("position")
    private String position;
    @JsonProperty("dateToPosition")
    private String dateToPosition;
    @JsonProperty("fileLocation")
    private String fileLocation;
    @JsonProperty("fileLocationGlasgow")
    private String fileLocationGlasgow;
    @JsonProperty("fileLocationAberdeen")
    private String fileLocationAberdeen;
    @JsonProperty("fileLocationDundee")
    private String fileLocationDundee;
    @JsonProperty("fileLocationEdinburgh")
    private String fileLocationEdinburgh;
    @JsonProperty("casesCompletedHearingTotal")
    private String casesCompletedHearingTotal;
    @JsonProperty("casesCompletedHearing")
    private String casesCompletedHearing;
    @JsonProperty("sessionType")
    private String sessionType;
    @JsonProperty("sessionDays")
    private String sessionDays;
    @JsonProperty("sessionDaysTotal")
    private String sessionDaysTotal;
    @JsonProperty("sessionDaysTotalDetail")
    private String sessionDaysTotalDetail;
    @JsonProperty("completedPerSession")
    private String completedPerSession;
    @JsonProperty("completedPerSessionTotal")
    private String completedPerSessionTotal;
    @JsonProperty("ftSessionDays")
    private String ftSessionDays;
    @JsonProperty("ftSessionDaysTotal")
    private String ftSessionDaysTotal;
    @JsonProperty("ptSessionDays")
    private String ptSessionDays;
    @JsonProperty("ptSessionDaysTotal")
    private String ptSessionDaysTotal;
    @JsonProperty("ptSessionDaysPerCent")
    private String ptSessionDaysPerCent;
    @JsonProperty("otherSessionDaysTotal")
    private String otherSessionDaysTotal;
    @JsonProperty("otherSessionDays")
    private String otherSessionDays;
    @JsonProperty("conciliationTrack")
    private String conciliationTrack;
    @JsonProperty("conciliationTrackNo")
    private String conciliationTrackNo;
    @JsonProperty("ConNoneCasesCompletedHearing")
    private String ConNoneCasesCompletedHearing;
    @JsonProperty("ConNoneSessionDays")
    private String ConNoneSessionDays;
    @JsonProperty("ConNoneCompletedPerSession")
    private String ConNoneCompletedPerSession;
    @JsonProperty("ConFastCasesCompletedHearing")
    private String ConFastCasesCompletedHearing;
    @JsonProperty("ConFastSessionDays")
    private String ConFastSessionDays;
    @JsonProperty("ConFastCompletedPerSession")
    private String ConFastCompletedPerSession;
    @JsonProperty("ConStdCasesCompletedHearing")
    private String ConStdCasesCompletedHearing;
    @JsonProperty("ConStdSessionDays")
    private String ConStdSessionDays;
    @JsonProperty("ConStdCompletedPerSession")
    private String ConStdCompletedPerSession;
    @JsonProperty("ConOpenCasesCompletedHearing")
    private String ConOpenCasesCompletedHearing;
    @JsonProperty("ConOpenSessionDays")
    private String ConOpenSessionDays;
    @JsonProperty("ConOpenCompletedPerSession")
    private String ConOpenCompletedPerSession;
    @JsonProperty("totalCases")
    private String totalCases;
    @JsonProperty("Total26wk")
    private String Total26wk;
    @JsonProperty("Total26wkPerCent")
    private String Total26wkPerCent;
    @JsonProperty("Totalx26wk")
    private String Totalx26wk;
    @JsonProperty("Totalx26wkPerCent")
    private String Totalx26wkPerCent;
    @JsonProperty("Total4wk")
    private String Total4wk;
    @JsonProperty("Total4wkPerCent")
    private String Total4wkPerCent;
    @JsonProperty("Totalx4wk")
    private String Totalx4wk;
    @JsonProperty("Totalx4wkPerCent")
    private String Totalx4wkPerCent;
    @JsonProperty("respondentName")
    private String respondentName;
    @JsonProperty("actioned")
    private String actioned;
    @JsonProperty("bfDate")
    private String bfDate;
    @JsonProperty("bfDateCleared")
    private String bfDateCleared;
    @JsonProperty("reservedHearing")
    private String reservedHearing;
    @JsonProperty("hearingCM")
    private String hearingCM;
    @JsonProperty("costs")
    private String costs;
    @JsonProperty("hearingInterloc")
    private String hearingInterloc;
    @JsonProperty("hearingPH")
    private String hearingPH;
    @JsonProperty("hearingPrelim")
    private String hearingPrelim;
    @JsonProperty("stage")
    private String stage;
    @JsonProperty("hearingStage1")
    private String hearingStage1;
    @JsonProperty("hearingStage2")
    private String hearingStage2;
    @JsonProperty("hearingFull")
    private String hearingFull;
    @JsonProperty("hearing")
    private String hearing;
    @JsonProperty("remedy")
    private String remedy;
    @JsonProperty("review")
    private String review;
    @JsonProperty("reconsider")
    private String reconsider;
    @JsonProperty("subSplit")
    private String subSplit;
    @JsonProperty("leadCase")
    private String leadCase;
    @JsonProperty("et3ReceivedDate")
    private String et3ReceivedDate;
    @JsonProperty("judicialMediation")
    private String judicialMediation;
    @JsonProperty("caseType")
    private String caseType;
    @JsonProperty("singlesTotal")
    private String singlesTotal;
    @JsonProperty("multiplesTotal")
    private String multiplesTotal;
    @JsonProperty("dateOfAcceptance")
    private String dateOfAcceptance;
    @JsonProperty("respondentET3")
    private List<ReportRespondentTypeItem> respondentET3;
    @JsonProperty("respondentET4")
    private List<ReportET4TypeItem> respondentET4;
    @JsonProperty("listingHistory")
    private List<ReportListingsTypeItem> listingHistory;
    @JsonProperty("ConNoneTotal")
    private String ConNoneTotal;
    @JsonProperty("ConStdTotal")
    private String ConStdTotal;
    @JsonProperty("ConFastTotal")
    private String ConFastTotal;
    @JsonProperty("ConOpenTotal")
    private String ConOpenTotal;
    @JsonProperty("ConNone26wkTotal")
    private String ConNone26wkTotal;
    @JsonProperty("ConStd26wkTotal")
    private String ConStd26wkTotal;
    @JsonProperty("ConFast26wkTotal")
    private String ConFast26wkTotal;
    @JsonProperty("ConOpen26wkTotal")
    private String ConOpen26wkTotal;
    @JsonProperty("ConNone26wkTotalPerCent")
    private String ConNone26wkTotalPerCent;
    @JsonProperty("ConStd26wkTotalPerCent")
    private String ConStd26wkTotalPerCent;
    @JsonProperty("ConFast26wkTotalPerCent")
    private String ConFast26wkTotalPerCent;
    @JsonProperty("ConOpen26wkTotalPerCent")
    private String ConOpen26wkTotalPerCent;
    @JsonProperty("xConNone26wkTotal")
    private String xConNone26wkTotal;
    @JsonProperty("xConStd26wkTotal")
    private String xConStd26wkTotal;
    @JsonProperty("xConFast26wkTotal")
    private String xConFast26wkTotal;
    @JsonProperty("xConOpen26wkTotal")
    private String xConOpen26wkTotal;
    @JsonProperty("xConNone26wkTotalPerCent")
    private String xConNone26wkTotalPerCent;
    @JsonProperty("xConStd26wkTotalPerCent")
    private String xConStd26wkTotalPerCent;
    @JsonProperty("xConFast26wkTotalPerCent")
    private String xConFast26wkTotalPerCent;
    @JsonProperty("xConOpen26wkTotalPerCent")
    private String xConOpen26wkTotalPerCent;
    @JsonProperty("delayedDaysForFirstHearing")
    private String delayedDaysForFirstHearing;

    @JsonProperty("claimServedDay1Total")
    private String claimServedDay1Total;
    @JsonProperty("claimServedDay1Percent")
    private String claimServedDay1Percent;

    @JsonProperty("claimServedDay2Total")
    private String claimServedDay2Total;
    @JsonProperty("claimServedDay2Percent")
    private String claimServedDay2Percent;

    @JsonProperty("claimServedDay3Total")
    private String claimServedDay3Total;
    @JsonProperty("claimServedDay3Percent")
    private String claimServedDay3Percent;

    @JsonProperty("claimServedDay4Total")
    private String claimServedDay4Total;
    @JsonProperty("claimServedDay4Percent")
    private String claimServedDay4Percent;

    @JsonProperty("claimServedDay5Total")
    private String claimServedDay5Total;
    @JsonProperty("claimServedDay5Percent")
    private String claimServedDay5Percent;

    @JsonProperty("claimServed6PlusDaysTotal")
    private String claimServed6PlusDaysTotal;
    @JsonProperty("claimServed6PlusDaysPercent")
    private String claimServed6PlusDaysPercent;

    @JsonProperty("claimServedTotal")
    private String claimServedTotal;

    @JsonProperty("claimServedItems")
    private List<ClaimServedTypeItem> claimServedItems;

    @JsonProperty("manuallyCreatedTotalCases")
    private String manuallyCreatedTotalCases;
    @JsonProperty("et1OnlineTotalCases")
    private String et1OnlineTotalCases;
    @JsonProperty("eccTotalCases")
    private String eccTotalCases;
    @JsonProperty("migratedTotalCases")
    private String migratedTotalCases;

    @JsonProperty("manuallyCreatedTotalCasesPercent")
    private String manuallyCreatedTotalCasesPercent;
    @JsonProperty("et1OnlineTotalCasesPercent")
    private String et1OnlineTotalCasesPercent;
    @JsonProperty("eccTotalCasesPercent")
    private String eccTotalCasesPercent;
    @JsonProperty("migratedTotalCasesPercent")
    private String migratedTotalCasesPercent;

}
