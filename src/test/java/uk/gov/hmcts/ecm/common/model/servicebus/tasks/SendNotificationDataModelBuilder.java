package uk.gov.hmcts.ecm.common.model.servicebus.tasks;

import uk.gov.hmcts.ecm.common.model.servicebus.datamodel.SendNotificationDataModel;
import uk.gov.hmcts.et.common.model.ccd.types.SendNotificationType;

public class SendNotificationDataModelBuilder {

    private final SendNotificationDataModel dataModel = new SendNotificationDataModel();

    public SendNotificationDataModelBuilder withSendNotification(SendNotificationType sendNotification) {
        dataModel.setSendNotification(sendNotification);
        return this;
    }

    public SendNotificationDataModel build() {
        return dataModel;
    }
}
