package uk.gov.hmcts.ecm.common.model.servicebus.tasks;

import org.junit.Before;
import org.junit.Test;
import uk.gov.hmcts.et.common.model.ccd.types.SendNotificationType;

import static org.junit.Assert.assertEquals;

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
        String whoToNotify = "Claimant only";
        SendNotificationType sendNotification = SendNotificationType.builder()
                .sendNotificationNotify("Lead case")
                .sendNotificationNotifyLeadCase(whoToNotify)
                .build();
        var updateModel = sendNotificationDataModelBuilder.withSendNotification(sendNotification).build();
        var submitEvent = caseDataBuilder.buildAsSubmitEvent("Accepted");

        var task = new SendNotificationTask(updateModel);
        task.run(submitEvent);

        assertEquals(whoToNotify, submitEvent.getCaseData().getSendNotificationNotify());

    }

}
