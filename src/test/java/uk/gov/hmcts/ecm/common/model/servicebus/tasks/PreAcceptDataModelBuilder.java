package uk.gov.hmcts.ecm.common.model.servicebus.tasks;

import uk.gov.hmcts.ecm.common.model.servicebus.datamodel.PreAcceptDataModel;

public class PreAcceptDataModelBuilder {
    private final PreAcceptDataModel dataModel = new PreAcceptDataModel();

    public PreAcceptDataModelBuilder preAcceptDataModelBuilder(String dateAccepted) {
        dataModel.setDateAccepted(dateAccepted);
        return this;
    }

    public PreAcceptDataModel build() {
        return dataModel;
    }
}
