package uk.gov.hmcts.ecm.common.model.servicebus.tasks;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import lombok.extern.slf4j.Slf4j;
import uk.gov.hmcts.ecm.common.model.ccd.SubmitEvent;
import uk.gov.hmcts.ecm.common.model.servicebus.datamodel.CreationDataModel;
import uk.gov.hmcts.ecm.common.model.servicebus.datamodel.DataModelParent;

import static uk.gov.hmcts.ecm.common.model.helper.Constants.*;

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
            submitEvent.getCaseData().setLeadClaimant(YES);
        } else {
            submitEvent.getCaseData().setLeadClaimant(NO);
        }

    }

    private void amendCreationFields(SubmitEvent submitEvent) {

        log.info("Moving case to Multiples case type");
        submitEvent.getCaseData().setMultipleReference(((CreationDataModel) dataModelParent).getMultipleRef());
        submitEvent.getCaseData().setCaseType(MULTIPLE_CASE_TYPE);

    }

}
