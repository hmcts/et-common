package uk.gov.hmcts.ecm.common.model.servicebus.tasks;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import lombok.extern.slf4j.Slf4j;
import uk.gov.hmcts.ecm.common.helpers.UtilHelper;
import uk.gov.hmcts.ecm.common.model.ccd.SubmitEvent;
import uk.gov.hmcts.ecm.common.model.ccd.types.CasePreAcceptType;
import uk.gov.hmcts.ecm.common.model.servicebus.datamodel.DataModelParent;
import java.time.LocalDate;

import static uk.gov.hmcts.ecm.common.model.helper.Constants.*;

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@SuperBuilder
@JsonIgnoreProperties(ignoreUnknown = true)
@Slf4j
public class PreAcceptDataTask extends DataTaskParent {

    public PreAcceptDataTask(DataModelParent dataModelParent) {
        super(dataModelParent);
    }

    public void run(SubmitEvent submitEvent) {

        if (submitEvent.getState().equals(SUBMITTED_STATE)) {
            preAcceptLogic(submitEvent);
        } else {
            log.info("The case is already accepted or not right state");
        }

    }

    private void preAcceptLogic(SubmitEvent submitEvent) {

        log.info("Moving to accepted state");
        submitEvent.getCaseData().setState(ACCEPTED_STATE);
        CasePreAcceptType casePreAcceptType = new CasePreAcceptType();
        casePreAcceptType.setCaseAccepted(YES);
        casePreAcceptType.setDateAccepted(UtilHelper.formatCurrentDate2(LocalDate.now()));
        submitEvent.getCaseData().setPreAcceptCase(casePreAcceptType);

    }

}
