package uk.gov.hmcts.et.common.model.servicebus.tasks;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import lombok.extern.slf4j.Slf4j;
import uk.gov.hmcts.et.common.model.ccd.SubmitEvent;
import uk.gov.hmcts.et.common.model.helper.Constants;
import uk.gov.hmcts.et.common.model.servicebus.datamodel.CreationDataModel;
import uk.gov.hmcts.et.common.model.servicebus.datamodel.DataModelParent;

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@SuperBuilder
@JsonIgnoreProperties(ignoreUnknown = true)
@Slf4j
public class CreationDataTask extends DataTaskParent {

    public CreationDataTask(DataModelParent dataModelParent) {
        super(dataModelParent);
    }

    public void run(SubmitEvent submitEvent) {

        checkLeadClaimant(submitEvent);

        amendCreationFields(submitEvent);

    }

    private void checkLeadClaimant(SubmitEvent submitEvent) {

        if (submitEvent.getCaseData().getEthosCaseReference()
                .equals(((CreationDataModel)dataModelParent).getLead())) {
            log.info("Adding lead");
            submitEvent.getCaseData().setLeadClaimant(Constants.YES);
        } else {
            submitEvent.getCaseData().setLeadClaimant(Constants.NO);
        }

    }

    private void amendCreationFields(SubmitEvent submitEvent) {

        log.info("Moving case to Multiples case type");
        submitEvent.getCaseData().setMultipleReference(((CreationDataModel) dataModelParent).getMultipleRef());
        submitEvent.getCaseData().setEcmCaseType(Constants.MULTIPLE_CASE_TYPE);
        submitEvent.getCaseData().setMultipleFlag(Constants.YES);
        submitEvent.getCaseData().setMultipleReferenceLinkMarkUp(
                ((CreationDataModel) dataModelParent).getMultipleReferenceLinkMarkUp());

    }

}
