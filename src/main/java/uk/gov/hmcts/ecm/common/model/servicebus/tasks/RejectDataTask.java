package uk.gov.hmcts.ecm.common.model.servicebus.tasks;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import lombok.extern.slf4j.Slf4j;
import uk.gov.hmcts.ecm.common.model.ccd.SubmitEvent;
import uk.gov.hmcts.ecm.common.model.ccd.types.CasePreAcceptType;
import uk.gov.hmcts.ecm.common.model.servicebus.datamodel.DataModelParent;
import uk.gov.hmcts.ecm.common.model.servicebus.datamodel.RejectDataModel;

import static uk.gov.hmcts.ecm.common.model.helper.Constants.*;

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@SuperBuilder
@JsonIgnoreProperties(ignoreUnknown = true)
@Slf4j
public class RejectDataTask extends DataTaskParent {

    public RejectDataTask(DataModelParent dataModelParent) {
        super(dataModelParent);
    }

    public void run(SubmitEvent submitEvent) {

        if (submitEvent.getState().equals(SUBMITTED_STATE)) {
            rejectLogic(submitEvent);
        } else {
            log.info("The case is already accepted or not right state");
        }

    }

    private void rejectLogic(SubmitEvent submitEvent) {

        log.info("Moving to rejected state");
        submitEvent.getCaseData().setState(REJECTED_STATE);
        CasePreAcceptType casePreAcceptType = new CasePreAcceptType();
        casePreAcceptType.setCaseAccepted(NO);
        casePreAcceptType.setDateRejected(((RejectDataModel)dataModelParent).getDateRejected());
        casePreAcceptType.setRejectReason(((RejectDataModel)dataModelParent).getRejectReason());
        submitEvent.getCaseData().setPreAcceptCase(casePreAcceptType);

    }

}
