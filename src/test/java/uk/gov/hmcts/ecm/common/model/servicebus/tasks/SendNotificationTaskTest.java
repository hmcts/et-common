package uk.gov.hmcts.ecm.common.model.servicebus.tasks;

import org.junit.Before;
import org.junit.Test;
import uk.gov.hmcts.et.common.model.ccd.types.SendNotificationTypeMultiple;

import static org.junit.Assert.assertEquals;
import static uk.gov.hmcts.ecm.common.model.helper.Constants.ACCEPTED_STATE;
import static uk.gov.hmcts.ecm.common.model.helper.Constants.CLAIMANT_ONLY;
import static uk.gov.hmcts.ecm.common.model.helper.Constants.SEND_NOTIFICATION_LEAD;

public class SendNotificationTaskTest {

    CaseDataBuilder caseDataBuilder;
    SendNotificationDataModelBuilder sendNotificationDataModelBuilder;

    @Before
    public void setUp() {
        caseDataBuilder = new CaseDataBuilder();
        sendNotificationDataModelBuilder = new SendNotificationDataModelBuilder();
    }

    @Test
    public void addSendNotification() {
        SendNotificationTypeMultiple sendNotification = new SendNotificationTypeMultiple();
        sendNotification.setSendNotificationNotifyLeadCase(CLAIMANT_ONLY);
        sendNotification.setSendNotificationNotify(SEND_NOTIFICATION_LEAD);
        var updateModel = sendNotificationDataModelBuilder.withSendNotification(sendNotification).build();
        var submitEvent = caseDataBuilder.buildAsSubmitEvent(ACCEPTED_STATE);

        var task = new SendNotificationTask(updateModel);
        task.run(submitEvent);

        assertEquals(CLAIMANT_ONLY, submitEvent.getCaseData().getSendNotificationNotify());

    }

}
