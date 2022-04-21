package uk.gov.hmcts.ecm.common.model.servicebus.tasks;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import lombok.extern.slf4j.Slf4j;
import uk.gov.hmcts.ecm.common.model.servicebus.datamodel.DataModelParent;
import uk.gov.hmcts.ecm.common.model.servicebus.datamodel.PreAcceptDataModel;
import uk.gov.hmcts.et.common.model.ccd.SubmitEvent;
import uk.gov.hmcts.et.common.model.ccd.types.CasePreAcceptType;

import static uk.gov.hmcts.ecm.common.model.helper.Constants.NO;
import static uk.gov.hmcts.ecm.common.model.helper.Constants.SINGLE_OPEN_CASE_STATES;
import static uk.gov.hmcts.ecm.common.model.helper.Constants.YES;

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

        if (SINGLE_OPEN_CASE_STATES.contains(submitEvent.getState())) {
            preAcceptLogic(submitEvent);
        } else {
            log.info("Case {} is not in the right state", submitEvent.getCaseData().getEthosCaseReference());
        }

    }

    private void preAcceptLogic(SubmitEvent submitEvent) {
        var caseData = submitEvent.getCaseData();
        log.info("Moving to accepted state");
        if (caseData.getPreAcceptCase() == null || NO.equals(caseData.getPreAcceptCase().getCaseAccepted())) {
            var casePreAcceptType = new CasePreAcceptType();
            casePreAcceptType.setCaseAccepted(YES);
            casePreAcceptType.setDateAccepted(((PreAcceptDataModel)dataModelParent).getDateAccepted());
            submitEvent.getCaseData().setPreAcceptCase(casePreAcceptType);
        }

    }

}
