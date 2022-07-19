package uk.gov.hmcts.ecm.common.helpers;

import uk.gov.hmcts.ecm.common.model.servicebus.UpdateCaseMsg;
import uk.gov.hmcts.ecm.common.model.servicebus.datamodel.CloseDataModel;
import uk.gov.hmcts.ecm.common.model.servicebus.datamodel.CreationDataModel;
import uk.gov.hmcts.ecm.common.model.servicebus.datamodel.CreationSingleDataModel;
import uk.gov.hmcts.ecm.common.model.servicebus.datamodel.DataModelParent;
import uk.gov.hmcts.ecm.common.model.servicebus.datamodel.DetachDataModel;
import uk.gov.hmcts.ecm.common.model.servicebus.datamodel.PreAcceptDataModel;
import uk.gov.hmcts.ecm.common.model.servicebus.datamodel.RejectDataModel;
import uk.gov.hmcts.ecm.common.model.servicebus.datamodel.ResetStateDataModel;
import uk.gov.hmcts.ecm.common.model.servicebus.datamodel.UpdateDataModel;
import uk.gov.hmcts.et.common.model.bulk.types.DynamicValueType;
import uk.gov.hmcts.et.common.model.ccd.CaseData;
import uk.gov.hmcts.et.common.model.ccd.SubmitEvent;
import uk.gov.hmcts.et.common.model.ccd.items.JudgementTypeItem;
import uk.gov.hmcts.et.common.model.ccd.items.JurCodesTypeItem;
import uk.gov.hmcts.et.common.model.ccd.items.RepresentedTypeRItem;
import uk.gov.hmcts.et.common.model.ccd.items.RespondentSumTypeItem;
import uk.gov.hmcts.et.common.model.ccd.types.ClaimantIndType;
import uk.gov.hmcts.et.common.model.ccd.types.JudgementType;
import uk.gov.hmcts.et.common.model.ccd.types.JurCodesType;
import uk.gov.hmcts.et.common.model.ccd.types.RepresentedTypeC;
import uk.gov.hmcts.et.common.model.ccd.types.RepresentedTypeR;
import uk.gov.hmcts.et.common.model.ccd.types.RespondentSumType;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

import static uk.gov.hmcts.ecm.common.model.helper.Constants.SCOTLAND_BULK_CASE_TYPE_ID;

public final class ServiceBusHelper {

    private ServiceBusHelper() {
        // utility class requires private constructor
    }

    public static UpdateCaseMsg generateUpdateCaseMsg(DataModelParent dataModelParent) {
        var linkMarkUp = "<a href=\"" + "/cases/details/1591184523086531\"" + ">4150001</a>";
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
                .multipleReferenceLinkMarkUp(linkMarkUp)
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
        JudgementType judgementType = new JudgementType();
        judgementType.setJudgementType("Judgementtype");
        judgementType.setJurisdictionCodes(jurCodesCollection);
        JudgementTypeItem judgementTypeItem = new JudgementTypeItem();
        judgementTypeItem.setValue(judgementType);
        List<JudgementTypeItem> judgementTypeCollection = new ArrayList<>(Collections.singletonList(judgementTypeItem));
        caseData.setJudgementCollection(judgementTypeCollection);
        ClaimantIndType claimantIndType = new ClaimantIndType();
        claimantIndType.setClaimantFirstNames("ClaimantName");
        caseData.setClaimantIndType(claimantIndType);
        RepresentedTypeC representedTypeC = new RepresentedTypeC();
        representedTypeC.setNameOfRepresentative("RepName");
        caseData.setRepresentativeClaimantType(representedTypeC);
        RepresentedTypeRItem representedTypeRItem = new RepresentedTypeRItem();
        RepresentedTypeR representedTypeR = new RepresentedTypeR();
        representedTypeR.setNameOfRepresentative("Rep Name");
        representedTypeR.setRespRepName("RespondentName");
        representedTypeRItem.setValue(representedTypeR);
        List<RepresentedTypeRItem> repCollection = new ArrayList<>(Collections.singletonList(representedTypeRItem));
        caseData.setRepCollection(repCollection);
        RespondentSumTypeItem respondentSumTypeItem = new RespondentSumTypeItem();
        RespondentSumType respondentSumType = new RespondentSumType();
        respondentSumType.setRespondentName("RespondentName");
        respondentSumTypeItem.setId(UUID.randomUUID().toString());
        respondentSumTypeItem.setValue(respondentSumType);
        caseData.setRespondentCollection(new ArrayList<>(Collections.singletonList(respondentSumTypeItem)));
        submitEvent.setCaseData(caseData);
        return submitEvent;
    }

    public static CreationDataModel getCreationDataModel(String leadRef) {
        var linkMarkUp = "<a href=" + "\"" + "/cases/details/1591184523086531" + "\"" + ">4150001</a>";
        return CreationDataModel.builder()
                .lead(leadRef)
                .multipleRef("4150001")
                .multipleReferenceLinkMarkUp(linkMarkUp)
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
                .clerkResponsible(DynamicValueType.create("ClerkResponsible", "ClerkResponsible"))
                .fileLocation(DynamicValueType.create("FileLocation", "FileLocation"))
                .notes("Notes")
                .managingOffice("ManagingOffice")
                .fileLocationGlasgow(DynamicValueType.create("FileLocationGlasgow", "FileLocationGlasgow"))
                .fileLocationAberdeen(DynamicValueType.create("FileLocationAberdeen", "FileLocationAberdeen"))
                .fileLocationDundee(DynamicValueType.create("FileLocationDundee", "FileLocationDundee"))
                .fileLocationEdinburgh(DynamicValueType.create("FileLocationEdinburgh", "FileLocationEdinburgh"))
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
                .officeCT("Manchester")
                .positionTypeCT("PositionType")
                .ccdGatewayBaseUrl("ccdGatewayBaseUrl")
                .reasonForCT("reasonForCT")
                .build();
    }

    public static UpdateDataModel getUpdateDataModel() {
        return UpdateDataModel.builder()
                .managingOffice("ManagingOffice")
                .fileLocation(DynamicValueType.create("FileLocation", "FileLocation"))
                .fileLocationGlasgow(DynamicValueType.create("FileLocationGlasgow", "FileLocationGlasgow"))
                .fileLocationAberdeen(DynamicValueType.create("FileLocationAberdeen", "FileLocationAberdeen"))
                .fileLocationDundee(DynamicValueType.create("FileLocationDundee", "FileLocationDundee"))
                .fileLocationEdinburgh(DynamicValueType.create("FileLocationEdinburgh", "FileLocationEdinburgh"))
                .clerkResponsible(DynamicValueType.create("ClerkResponsible", "ClerkResponsible"))
                .positionType("PositionType")
                .receiptDate("25/08/1999")
                .hearingStage("HearingStage")
                .representativeClaimantType(getRepresentativeClaimant())
                .jurCodesType(getJurCodesType())
                .respondentSumType(getRespondentSubType())
                .judgementType(getJudgementType())
                .representedType(getRepresentedType())
                .isFixCase("No")
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

    public static JudgementType getJudgementType() {

        JudgementType judgementType = new JudgementType();

        judgementType.setJudgementType("JudgementType");
        JurCodesTypeItem jurCodesTypeItem = new JurCodesTypeItem();
        jurCodesTypeItem.setId(UUID.randomUUID().toString());
        jurCodesTypeItem.setValue(getJurCodesType());
        judgementType.setJurisdictionCodes(new ArrayList<>(Collections.singletonList(jurCodesTypeItem)));

        return judgementType;

    }

    public static RepresentedTypeR getRepresentedType() {

        RepresentedTypeR representedTypeR = new RepresentedTypeR();
        representedTypeR.setRespRepName("RespondentName");
        representedTypeR.setNameOfRepresentative("Rep Name");

        return representedTypeR;

    }

}
