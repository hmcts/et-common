package uk.gov.hmcts.et.common.servicebus;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;
import uk.gov.hmcts.et.common.helpers.ServiceBusHelper;
import uk.gov.hmcts.et.common.model.ccd.SubmitEvent;
import uk.gov.hmcts.et.common.model.helper.Constants;
import uk.gov.hmcts.et.common.model.servicebus.UpdateCaseMsg;
import uk.gov.hmcts.et.common.model.servicebus.datamodel.CloseDataModel;
import uk.gov.hmcts.et.common.model.servicebus.datamodel.CreationDataModel;
import uk.gov.hmcts.et.common.model.servicebus.datamodel.CreationSingleDataModel;
import uk.gov.hmcts.et.common.model.servicebus.datamodel.DetachDataModel;
import uk.gov.hmcts.et.common.model.servicebus.datamodel.PreAcceptDataModel;
import uk.gov.hmcts.et.common.model.servicebus.datamodel.RejectDataModel;
import uk.gov.hmcts.et.common.model.servicebus.datamodel.ResetStateDataModel;
import uk.gov.hmcts.et.common.model.servicebus.datamodel.UpdateDataModel;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

@RunWith(MockitoJUnitRunner.class)
public class UpdateCaseMsgTest {

    private UpdateCaseMsg updateCaseMsg;
    private SubmitEvent submitEventSubmitted;
    private SubmitEvent submitEventAccepted;
    private SubmitEvent submitEventDetailed;

    @Before
    public void setUp() {
        submitEventAccepted = ServiceBusHelper.generateSubmitEvent(Constants.ACCEPTED_STATE);
        submitEventSubmitted = ServiceBusHelper.generateSubmitEvent(Constants.SUBMITTED_STATE);
        submitEventDetailed = ServiceBusHelper.generateSubmitEventDetailed(Constants.ACCEPTED_STATE);
    }

    @Test
    public void toStringMethod() {
        CreationDataModel creationDataModel = ServiceBusHelper.getCreationDataModel("4150002/2020");
        updateCaseMsg = ServiceBusHelper.generateUpdateCaseMsg(creationDataModel);
        String linkMarkUp = "<a href=\"/cases/details/1591184523086531\"" + ">4150001</a>";
        assertEquals(
                "UpdateCaseMsg{"
                        + "ethosCaseReference='4150002/2020', msgId='1', jurisdiction='EMPLOYMENT', "
                        + "caseTypeId='ET_Scotland_Multiple', multipleRef='4150001', totalCases='1', "
                        + "username='eric.ccdcooper@gmail.com', confirmation='YES', "
                        + "dataModel=CreationDataModel(lead=4150002/2020, multipleRef=4150001, "
                        + "multipleReferenceLinkMarkUp=" + linkMarkUp + ")', "
                        + "multipleReferenceLinkMarkUp='" + linkMarkUp + "'}",
                updateCaseMsg.toString());
    }

    @Test
    public void runTaskCreation() {
        CreationDataModel creationDataModel = ServiceBusHelper.getCreationDataModel("4150002/2020");
        updateCaseMsg = ServiceBusHelper.generateUpdateCaseMsg(creationDataModel);
        updateCaseMsg.runTask(submitEventAccepted);
        Assert.assertEquals(Constants.MULTIPLE_CASE_TYPE, submitEventAccepted.getCaseData().getEcmCaseType());
        assertEquals("4150001", submitEventAccepted.getCaseData().getMultipleReference());
        Assert.assertEquals(Constants.YES, submitEventAccepted.getCaseData().getLeadClaimant());
    }

    @Test
    public void runTaskPreAccept() {
        PreAcceptDataModel preAcceptDataModel = ServiceBusHelper.getPreAcceptDataModel();
        updateCaseMsg = ServiceBusHelper.generateUpdateCaseMsg(preAcceptDataModel);
        updateCaseMsg.runTask(submitEventSubmitted);
        assertEquals("25-10-2020", submitEventSubmitted.getCaseData().getPreAcceptCase().getDateAccepted());
        Assert.assertEquals(Constants.YES, submitEventSubmitted.getCaseData().getPreAcceptCase().getCaseAccepted());
    }

    @Test
    public void runTaskPreAcceptAlreadyAccepted() {
        PreAcceptDataModel preAcceptDataModel = ServiceBusHelper.getPreAcceptDataModel();
        updateCaseMsg = ServiceBusHelper.generateUpdateCaseMsg(preAcceptDataModel);
        updateCaseMsg.runTask(submitEventAccepted);
        Assert.assertEquals(Constants.ACCEPTED_STATE, submitEventAccepted.getState());
    }

    @Test
    public void runTaskReject() {
        RejectDataModel rejectDataModel = ServiceBusHelper.getRejectDataModel();
        updateCaseMsg = ServiceBusHelper.generateUpdateCaseMsg(rejectDataModel);
        updateCaseMsg.runTask(submitEventSubmitted);
        List<String> reasons = new ArrayList<>(Arrays.asList("RejectionReason1", "RejectionReason2"));
        Assert.assertEquals(Constants.NO, submitEventSubmitted.getCaseData().getPreAcceptCase().getCaseAccepted());
        assertEquals("25-10-2020", submitEventSubmitted.getCaseData().getPreAcceptCase().getDateRejected());
        assertEquals(reasons, submitEventSubmitted.getCaseData().getPreAcceptCase().getRejectReason());
    }

    @Test
    public void runTaskClose() {
        CloseDataModel closeDataModel = ServiceBusHelper.getCloseDataModel();
        updateCaseMsg = ServiceBusHelper.generateUpdateCaseMsg(closeDataModel);
        updateCaseMsg.runTask(submitEventSubmitted);
        Assert.assertEquals(Constants.CASE_CLOSED_POSITION, submitEventSubmitted.getCaseData().getPositionType());
        assertEquals("FileLocation", submitEventSubmitted.getCaseData().getFileLocation().getSelectedCode());
        assertEquals("ClerkResponsible", submitEventSubmitted.getCaseData().getClerkResponsible().getSelectedCode());
        assertEquals("Notes", submitEventSubmitted.getCaseData().getCaseNotes());
        assertEquals("ManagingOffice", submitEventSubmitted.getCaseData().getManagingOffice());
        assertEquals("FileLocationGlasgow",
                submitEventSubmitted.getCaseData().getFileLocationGlasgow().getSelectedCode());
        assertEquals("FileLocationAberdeen",
                submitEventSubmitted.getCaseData().getFileLocationAberdeen().getSelectedCode());
        assertEquals("FileLocationDundee",
                submitEventSubmitted.getCaseData().getFileLocationDundee().getSelectedCode());
        assertEquals("FileLocationEdinburgh",
                submitEventSubmitted.getCaseData().getFileLocationEdinburgh().getSelectedCode());
    }

    @Test
    public void runTaskDetach() {
        DetachDataModel detachDataModel = ServiceBusHelper.getDetachDataModel();
        updateCaseMsg = ServiceBusHelper.generateUpdateCaseMsg(detachDataModel);
        updateCaseMsg.runTask(submitEventAccepted);
        Assert.assertEquals(Constants.SINGLE_CASE_TYPE, submitEventAccepted.getCaseData().getEcmCaseType());
        assertNull(submitEventAccepted.getCaseData().getMultipleReference());
    }

    @Test
    public void runTaskResetState() {
        ResetStateDataModel resetStateDataModel = ServiceBusHelper.getResetStateDataModel();
        updateCaseMsg = ServiceBusHelper.generateUpdateCaseMsg(resetStateDataModel);
        updateCaseMsg.runTask(submitEventAccepted);
        Assert.assertEquals(Constants.ACCEPTED_STATE, submitEventAccepted.getState());
    }

    @Test
    public void runTaskCreationSingle() {
        CreationSingleDataModel creationSingleDataModel = ServiceBusHelper.getCreationSingleDataModel();
        updateCaseMsg = ServiceBusHelper.generateUpdateCaseMsg(creationSingleDataModel);
        updateCaseMsg.runTask(submitEventAccepted);
        Assert.assertEquals(Constants.ACCEPTED_STATE, submitEventAccepted.getState());
    }

    @Test
    public void runTaskUpdate() {
        UpdateDataModel updateDataModel = ServiceBusHelper.getUpdateDataModel();
        updateCaseMsg = ServiceBusHelper.generateUpdateCaseMsg(updateDataModel);
        updateCaseMsg.runTask(submitEventAccepted);
        assertEquals("ManagingOffice", submitEventAccepted.getCaseData().getManagingOffice());
        assertEquals("FileLocation", submitEventAccepted.getCaseData().getFileLocation().getSelectedCode());
        assertEquals("FileLocationGlasgow",
                submitEventAccepted.getCaseData().getFileLocationGlasgow().getSelectedCode());
        assertEquals("FileLocationAberdeen",
                submitEventAccepted.getCaseData().getFileLocationAberdeen().getSelectedCode());
        assertEquals("FileLocationDundee", submitEventAccepted.getCaseData().getFileLocationDundee().getSelectedCode());
        assertEquals("FileLocationEdinburgh",
                submitEventAccepted.getCaseData().getFileLocationEdinburgh().getSelectedCode());
        assertEquals("ClerkResponsible", submitEventAccepted.getCaseData().getClerkResponsible().getSelectedCode());
        assertEquals("PositionType", submitEventAccepted.getCaseData().getPositionType());
        assertEquals("25/08/1999", submitEventAccepted.getCaseData().getReceiptDate());
        assertEquals("HearingStage", submitEventAccepted.getCaseData().getHearingStage());
        assertEquals("RepName", submitEventAccepted.getCaseData()
                .getRepresentativeClaimantType().getNameOfRepresentative());
        assertEquals("AC", submitEventAccepted.getCaseData()
                .getJurCodesCollection().get(1).getValue().getJuridictionCodesList());
        assertEquals("RespondentName", submitEventAccepted.getCaseData()
                .getRespondentCollection().get(0).getValue().getRespondentName());
    }

    @Test
    public void runTaskUpdateSubmitEventDetailed() {
        UpdateDataModel updateDataModel = ServiceBusHelper.getUpdateDataModel();
        updateCaseMsg = ServiceBusHelper.generateUpdateCaseMsg(updateDataModel);
        updateCaseMsg.runTask(submitEventDetailed);
        assertEquals("ManagingOffice", submitEventDetailed.getCaseData().getManagingOffice());
        assertEquals("FileLocation", submitEventDetailed.getCaseData().getFileLocation().getSelectedCode());
        assertEquals("FileLocationGlasgow",
                submitEventDetailed.getCaseData().getFileLocationGlasgow().getSelectedCode());
        assertEquals("FileLocationAberdeen",
                submitEventDetailed.getCaseData().getFileLocationAberdeen().getSelectedCode());
        assertEquals("FileLocationDundee", submitEventDetailed.getCaseData().getFileLocationDundee().getSelectedCode());
        assertEquals("FileLocationEdinburgh",
                submitEventDetailed.getCaseData().getFileLocationEdinburgh().getSelectedCode());
        assertEquals("ClerkResponsible", submitEventDetailed.getCaseData().getClerkResponsible().getSelectedCode());
        assertEquals("PositionType", submitEventDetailed.getCaseData().getPositionType());
        assertEquals("RepName", submitEventDetailed
                .getCaseData().getRepresentativeClaimantType().getNameOfRepresentative());
        assertEquals("AC", submitEventDetailed
                .getCaseData().getJurCodesCollection().get(1).getValue().getJuridictionCodesList());
        assertEquals("RespondentName", submitEventDetailed
                .getCaseData().getRespondentCollection().get(0).getValue().getRespondentName());
        assertEquals("RespondentName", submitEventDetailed
                .getCaseData().getRepCollection().get(0).getValue().getRespRepName());
    }
}