package uk.gov.hmcts.et.common.model.servicebus.tasks;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import lombok.extern.slf4j.Slf4j;
import uk.gov.hmcts.et.common.model.ccd.SubmitEvent;
import uk.gov.hmcts.et.common.model.servicebus.datamodel.DataModelParent;

import static uk.gov.hmcts.et.common.model.helper.Constants.NO;
import static uk.gov.hmcts.et.common.model.helper.Constants.SINGLE_CASE_TYPE;

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@SuperBuilder
@JsonIgnoreProperties(ignoreUnknown = true)
@Slf4j
public class DetachDataTask extends DataTaskParent {

    public DetachDataTask(DataModelParent dataModelParent) {
        super(dataModelParent);
    }

    public void run(SubmitEvent submitEvent) {

        detachCaseFieldsFromMultiple(submitEvent);

    }

    private void detachCaseFieldsFromMultiple(SubmitEvent submitEvent) {

        log.info("Detaching case from the Multiples");
        submitEvent.getCaseData().setMultipleReference(null);
        submitEvent.getCaseData().setLeadClaimant(" ");
        submitEvent.getCaseData().setEcmCaseType(SINGLE_CASE_TYPE);
        submitEvent.getCaseData().setMultipleFlag(NO);

    }

}
