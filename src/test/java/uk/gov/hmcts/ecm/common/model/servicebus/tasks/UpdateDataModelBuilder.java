package uk.gov.hmcts.ecm.common.model.servicebus.tasks;

import uk.gov.hmcts.ecm.common.model.servicebus.datamodel.UpdateDataModel;
import uk.gov.hmcts.et.common.model.ccd.types.JurCodesType;

public class UpdateDataModelBuilder {
    private final UpdateDataModel dataModel = new UpdateDataModel();

    public UpdateDataModelBuilder withJurisdictionCode(String jurCode, String outcome) {
        var jurCodeType = new JurCodesType();
        jurCodeType.setJuridictionCodesList(jurCode);
        jurCodeType.setJudgmentOutcome(outcome);

        dataModel.setJurCodesType(jurCodeType);
        return this;
    }

    public UpdateDataModel build() {
        return dataModel;
    }
}
