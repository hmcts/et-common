package uk.gov.hmcts.ecm.common.model.servicebus.tasks;

import uk.gov.hmcts.ecm.common.model.servicebus.datamodel.SendNotificationDataModel;
import uk.gov.hmcts.et.common.model.ccd.types.SendNotificationTypeMultiple;

public class SendNotificationDataModelBuilder {

    private final SendNotificationDataModel dataModel = new SendNotificationDataModel();

    public SendNotificationDataModelBuilder withSendNotification(SendNotificationTypeMultiple sendNotification) {
        dataModel.setSendNotification(sendNotification);
        return this;
    }

    public SendNotificationDataModel build() {
        return dataModel;
    }
}
