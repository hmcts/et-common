package uk.gov.hmcts.ecm.common.model.servicebus.tasks;

import uk.gov.hmcts.ecm.common.model.servicebus.datamodel.RejectDataModel;

import java.util.List;

public class RejectDataModelBuilder {
    private final RejectDataModel dataModel = new RejectDataModel();

    public RejectDataModelBuilder rejectDataModelBuilder(String dateRejected, List<String> rejectReason) {
        dataModel.setDateRejected(dateRejected);
        dataModel.setRejectReason(rejectReason);
        return this;
    }

    public RejectDataModel build() {
        return dataModel;
    }
}
