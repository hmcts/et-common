package uk.gov.hmcts.ecm.common.model.servicebus.tasks;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import lombok.extern.slf4j.Slf4j;
import uk.gov.hmcts.ecm.common.model.ccd.SubmitEvent;
import uk.gov.hmcts.ecm.common.model.servicebus.datamodel.CloseDataModel;
import uk.gov.hmcts.ecm.common.model.servicebus.datamodel.DataModelParent;

import static uk.gov.hmcts.ecm.common.model.helper.Constants.*;

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@SuperBuilder
@JsonIgnoreProperties(ignoreUnknown = true)
@Slf4j
public class CloseDataTask extends DataTaskParent {

    public CloseDataTask(DataModelParent dataModelParent) {
        super(dataModelParent);
    }

    public void run(SubmitEvent submitEvent) {

        if (!submitEvent.getState().equals(CLOSED_STATE)) {
            closeLogic(submitEvent);
        } else {
            log.info("The case is already closed");
        }

    }

    private void closeLogic(SubmitEvent submitEvent) {

        log.info("Moving to close state");
        submitEvent.getCaseData().setState(CLOSED_STATE);
        submitEvent.getCaseData().setPositionType(CASE_CLOSED_POSITION);
        submitEvent.getCaseData().setClerkResponsible(((CloseDataModel)dataModelParent).getClerkResponsible());
        submitEvent.getCaseData().setFileLocation(((CloseDataModel)dataModelParent).getFileLocation());
        submitEvent.getCaseData().setCaseNotes(((CloseDataModel)dataModelParent).getNotes());

    }

}
