package uk.gov.hmcts.ecm.common.model.servicebus.tasks;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import lombok.extern.slf4j.Slf4j;
import uk.gov.hmcts.ecm.common.model.bulk.types.DynamicFixedListType;
import uk.gov.hmcts.ecm.common.model.ccd.SubmitEvent;
import uk.gov.hmcts.ecm.common.model.servicebus.datamodel.CloseDataModel;
import uk.gov.hmcts.ecm.common.model.servicebus.datamodel.DataModelParent;

import static com.google.common.base.Strings.isNullOrEmpty;
import static uk.gov.hmcts.ecm.common.model.helper.Constants.CASE_CLOSED_POSITION;
import static uk.gov.hmcts.ecm.common.model.helper.Constants.CLOSED_STATE;

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
        var caseData = submitEvent.getCaseData();
        caseData.setPositionType(CASE_CLOSED_POSITION);
        var clerkResponsible = ((CloseDataModel)dataModelParent).getClerkResponsible();
        if (clerkResponsible != null) {
            caseData.setClerkResponsible(DynamicFixedListType.of(clerkResponsible));
        }
        var fileLocation = ((CloseDataModel)dataModelParent).getFileLocation();
        if (fileLocation != null) {
            caseData.setFileLocation(DynamicFixedListType.of(fileLocation));
        }
        caseData.setCaseNotes(((CloseDataModel)dataModelParent).getNotes());

        managingOffice(submitEvent, ((CloseDataModel)dataModelParent));
    }

    private void managingOffice(SubmitEvent submitEvent, CloseDataModel closeDataModel) {

        if (!isNullOrEmpty(closeDataModel.getManagingOffice())) {
            submitEvent.getCaseData().setManagingOffice(closeDataModel.getManagingOffice());
        }

        if (!isNullOrEmpty(closeDataModel.getFileLocationGlasgow())) {
            submitEvent.getCaseData().setFileLocationGlasgow(closeDataModel.getFileLocationGlasgow());
        }

        if (!isNullOrEmpty(closeDataModel.getFileLocationAberdeen())) {
            submitEvent.getCaseData().setFileLocationAberdeen(closeDataModel.getFileLocationAberdeen());
        }

        if (!isNullOrEmpty(closeDataModel.getFileLocationDundee())) {
            submitEvent.getCaseData().setFileLocationDundee(closeDataModel.getFileLocationDundee());
        }

        if (!isNullOrEmpty(closeDataModel.getFileLocationEdinburgh())) {
            submitEvent.getCaseData().setFileLocationEdinburgh(closeDataModel.getFileLocationEdinburgh());
        }

    }

}
