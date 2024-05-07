package uk.gov.hmcts.ecm.common.model.servicebus.tasks;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import uk.gov.hmcts.et.common.model.ccd.types.SendNotificationTypeMultiple;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static uk.gov.hmcts.ecm.common.model.helper.Constants.ACCEPTED_STATE;
import static uk.gov.hmcts.ecm.common.model.helper.Constants.CLAIMANT_ONLY;
import static uk.gov.hmcts.ecm.common.model.helper.Constants.SEND_NOTIFICATION_LEAD;

class SendNotificationTaskTest {

    public static final String MULTIPLE_REF = "6001";
    CaseDataBuilder caseDataBuilder;
    SendNotificationDataModelBuilder sendNotificationDataModelBuilder;

    @BeforeEach
    void setUp() {
        caseDataBuilder = new CaseDataBuilder();
        sendNotificationDataModelBuilder = new SendNotificationDataModelBuilder();
    }

    @Test
    void addSendNotification() {
        SendNotificationTypeMultiple sendNotification = new SendNotificationTypeMultiple();
        sendNotification.setSendNotificationNotifyLeadCase(CLAIMANT_ONLY);
        sendNotification.setSendNotificationNotify(SEND_NOTIFICATION_LEAD);
        sendNotification.setNotificationSentFrom(MULTIPLE_REF);
        var updateModel = sendNotificationDataModelBuilder.withSendNotification(sendNotification).build();
        var submitEvent = caseDataBuilder.buildAsSubmitEvent(ACCEPTED_STATE);

        var task = new SendNotificationTask(updateModel);
        task.run(submitEvent);

        assertEquals(CLAIMANT_ONLY, submitEvent.getCaseData().getSendNotificationNotify());
        assertEquals(MULTIPLE_REF, submitEvent.getCaseData().getNotificationSentFrom());
    }

}
