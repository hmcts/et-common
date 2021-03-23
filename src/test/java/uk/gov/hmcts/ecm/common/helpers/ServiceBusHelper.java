package uk.gov.hmcts.ecm.common.helpers;

import uk.gov.hmcts.ecm.common.model.ccd.CaseData;
import uk.gov.hmcts.ecm.common.model.ccd.SubmitEvent;
import uk.gov.hmcts.ecm.common.model.ccd.items.JurCodesTypeItem;
import uk.gov.hmcts.ecm.common.model.ccd.items.RepresentedTypeRItem;
import uk.gov.hmcts.ecm.common.model.ccd.types.*;
import uk.gov.hmcts.ecm.common.model.servicebus.UpdateCaseMsg;
import uk.gov.hmcts.ecm.common.model.servicebus.datamodel.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static uk.gov.hmcts.ecm.common.model.helper.Constants.SCOTLAND_BULK_CASE_TYPE_ID;

public class ServiceBusHelper {

    public static UpdateCaseMsg generateUpdateCaseMsg(DataModelParent dataModelParent) {
        return UpdateCaseMsg.builder()
                .msgId("1")
                .jurisdiction("EMPLOYMENT")
                .caseTypeId(SCOTLAND_BULK_CASE_TYPE_ID)
                .multipleRef("4150001")
                .ethosCaseReference("4150002/2020")
                .totalCases("1")
                .username("eric.ccdcooper@gmail.com")
                .confirmation("YES")
                .dataModelParent(dataModelParent)
                .build();
    }

    public static SubmitEvent generateSubmitEvent(String state) {
        SubmitEvent submitEvent = new SubmitEvent();
        submitEvent.setState(state);
        CaseData caseData = new CaseData();
        caseData.setEthosCaseReference("4150002/2020");
        JurCodesType jurCodesType = new JurCodesType();
        jurCodesType.setJuridictionCodesList("ECM");
        JurCodesTypeItem jurCodesTypeItem = new JurCodesTypeItem();
        jurCodesTypeItem.setValue(jurCodesType);
        List<JurCodesTypeItem> jurCodesCollection = new ArrayList<>(Collections.singletonList(jurCodesTypeItem));
        caseData.setJurCodesCollection(jurCodesCollection);
        submitEvent.setCaseData(caseData);
        return submitEvent;
    }

    public static SubmitEvent generateSubmitEventDetailed(String state) {
        SubmitEvent submitEvent = new SubmitEvent();
        submitEvent.setState(state);
        CaseData caseData = new CaseData();
        caseData.setEthosCaseReference("4150002/2020");
        JurCodesType jurCodesType = new JurCodesType();
        jurCodesType.setJuridictionCodesList("ECM");
        JurCodesTypeItem jurCodesTypeItem = new JurCodesTypeItem();
        jurCodesTypeItem.setValue(jurCodesType);
        List<JurCodesTypeItem> jurCodesCollection = new ArrayList<>(Collections.singletonList(jurCodesTypeItem));
        caseData.setJurCodesCollection(jurCodesCollection);
        ClaimantIndType claimantIndType = new ClaimantIndType();
        claimantIndType.setClaimantFirstNames("ClaimantName");
        caseData.setClaimantIndType(claimantIndType);
        RepresentedTypeC representedTypeC = new RepresentedTypeC();
        representedTypeC.setNameOfRepresentative("RepName");
        caseData.setRepresentativeClaimantType(representedTypeC);
        RepresentedTypeRItem representedTypeRItem = new RepresentedTypeRItem();
        RepresentedTypeR representedTypeR = new RepresentedTypeR();
        representedTypeR.setNameOfRepresentative("RespondentRepName");
        representedTypeRItem.setValue(representedTypeR);
        List<RepresentedTypeRItem> repCollection = new ArrayList<>(Collections.singletonList(representedTypeRItem));
        caseData.setRepCollection(repCollection);
        submitEvent.setCaseData(caseData);
        return submitEvent;
    }

    public static CreationDataModel getCreationDataModel(String leadRef) {
        return CreationDataModel.builder()
                .lead(leadRef)
                .multipleRef("4150001")
                .build();
    }

    public static PreAcceptDataModel getPreAcceptDataModel() {
        return PreAcceptDataModel.builder()
                .dateAccepted(("25-10-2020"))
                .build();
    }

    public static RejectDataModel getRejectDataModel() {
        return RejectDataModel.builder()
                .dateRejected("25-10-2020")
                .rejectReason(new ArrayList<>(Arrays.asList("RejectionReason1", "RejectionReason2")))
                .build();
    }

    public static CloseDataModel getCloseDataModel() {
        return CloseDataModel.builder()
                .clerkResponsible("ClerkResponsible")
                .fileLocation("FileLocation")
                .notes("Notes")
                .managingOffice("ManagingOffice")
                .fileLocationGlasgow("FileLocationGlasgow")
                .fileLocationAberdeen("FileLocationAberdeen")
                .fileLocationDundee("FileLocationDundee")
                .fileLocationEdinburgh("FileLocationEdinburgh")
                .build();
    }

    public static DetachDataModel getDetachDataModel() {
        return DetachDataModel.builder()
                .build();
    }

    public static ResetStateDataModel getResetStateDataModel() {
        return ResetStateDataModel.builder()
                .build();
    }

    public static CreationSingleDataModel getCreationSingleDataModel() {
        return CreationSingleDataModel.builder()
                .ethosCaseReference("2512345/2020")
                .officeCT("Manchester")
                .positionTypeCT("PositionType")
                .build();
    }

    public static UpdateDataModel getUpdateDataModel() {
        return UpdateDataModel.builder()
                .managingOffice("ManagingOffice")
                .fileLocation("FileLocation")
                .fileLocationGlasgow("FileLocationGlasgow")
                .fileLocationAberdeen("FileLocationAberdeen")
                .fileLocationDundee("FileLocationDundee")
                .fileLocationEdinburgh("FileLocationEdinburgh")
                .clerkResponsible("ClerkResponsible")
                .positionType("PositionType")
                .receiptDate("25/08/1999")
                .hearingStage("HearingStage")
                .representativeClaimantType(getRepresentativeClaimant())
                .jurCodesType(getJurCodesType())
                .respondentSumType(getRespondentSubType())
                .build();
    }

    public static RepresentedTypeC getRepresentativeClaimant() {

        RepresentedTypeC representedTypeC = new RepresentedTypeC();

        representedTypeC.setNameOfRepresentative("RepName");
        representedTypeC.setRepresentativeEmailAddress("repEmail@hotmail.com");
        representedTypeC.setRepresentativePhoneNumber("072224232");

        return representedTypeC;

    }

    public static JurCodesType getJurCodesType() {

        JurCodesType jurCodesType = new JurCodesType();

        jurCodesType.setJuridictionCodesList("AC");
        jurCodesType.setJudgmentOutcome("outcome");

        return jurCodesType;

    }

    public static RespondentSumType getRespondentSubType() {

        RespondentSumType respondentSumType = new RespondentSumType();

        respondentSumType.setRespondentName("RespondentName");
        respondentSumType.setRespondentEmail("respondentEmail@hotmail.com");
        respondentSumType.setRespondentPhone1("072323232");

        return respondentSumType;

    }

}
