package uk.gov.hmcts.ecm.common.model.servicebus.datamodel;

import uk.gov.hmcts.ecm.common.model.servicebus.tasks.*;

public class DataModelFactory {

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
        } else {
            return new PreAcceptDataTask(dataModelParent);
        }

    }
}
