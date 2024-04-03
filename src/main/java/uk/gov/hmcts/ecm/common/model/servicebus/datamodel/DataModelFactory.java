package uk.gov.hmcts.ecm.common.model.servicebus.datamodel;

import uk.gov.hmcts.ecm.common.model.servicebus.tasks.CloseDataTask;
import uk.gov.hmcts.ecm.common.model.servicebus.tasks.CreationDataTask;
import uk.gov.hmcts.ecm.common.model.servicebus.tasks.CreationSingleDataTask;
import uk.gov.hmcts.ecm.common.model.servicebus.tasks.DataTaskParent;
import uk.gov.hmcts.ecm.common.model.servicebus.tasks.DetachDataTask;
import uk.gov.hmcts.ecm.common.model.servicebus.tasks.PreAcceptDataTask;
import uk.gov.hmcts.ecm.common.model.servicebus.tasks.RejectDataTask;
import uk.gov.hmcts.ecm.common.model.servicebus.tasks.ResetStateDataTask;
import uk.gov.hmcts.ecm.common.model.servicebus.tasks.SendNotificationTask;
import uk.gov.hmcts.ecm.common.model.servicebus.tasks.UpdateDataTask;

public class DataModelFactory {

    private DataModelFactory() {
    }

    public static DataTaskParent getDataModelType(DataModelParent dataModelParent) {

        if (dataModelParent instanceof CreationDataModel) {
            return new CreationDataTask(dataModelParent);
        } else if (dataModelParent instanceof UpdateDataModel) {
            return new UpdateDataTask(dataModelParent);
        } else if (dataModelParent instanceof DetachDataModel) {
            return new DetachDataTask(dataModelParent);
        } else if (dataModelParent instanceof RejectDataModel) {
            return new RejectDataTask(dataModelParent);
        } else if (dataModelParent instanceof CloseDataModel) {
            return new CloseDataTask(dataModelParent);
        } else if (dataModelParent instanceof ResetStateDataModel) {
            return new ResetStateDataTask(dataModelParent);
        } else if (dataModelParent instanceof CreationSingleDataModel) {
            return new CreationSingleDataTask(dataModelParent);
        } else if (dataModelParent instanceof SendNotificationDataModel) {
            return new SendNotificationTask(dataModelParent);
        } else {
            return new PreAcceptDataTask(dataModelParent);
        }

    }
}
