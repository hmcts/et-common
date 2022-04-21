package uk.gov.hmcts.ecm.common.model.servicebus.tasks;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import lombok.extern.slf4j.Slf4j;
import uk.gov.hmcts.ecm.common.model.servicebus.datamodel.CloseDataModel;
import uk.gov.hmcts.ecm.common.model.servicebus.datamodel.DataModelParent;
import uk.gov.hmcts.et.common.model.bulk.types.DynamicFixedListType;
import uk.gov.hmcts.et.common.model.ccd.SubmitEvent;

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

        if (closeDataModel.getFileLocationGlasgow() != null) {
            submitEvent.getCaseData().setFileLocationGlasgow(
                    DynamicFixedListType.of(closeDataModel.getFileLocationGlasgow()));
        }

        if (closeDataModel.getFileLocationAberdeen() != null) {
            submitEvent.getCaseData().setFileLocationAberdeen(
                    DynamicFixedListType.of(closeDataModel.getFileLocationAberdeen()));
        }

        if (closeDataModel.getFileLocationDundee() != null) {
            submitEvent.getCaseData().setFileLocationDundee(
                    DynamicFixedListType.of(closeDataModel.getFileLocationDundee()));
        }

        if (closeDataModel.getFileLocationEdinburgh() != null) {
            submitEvent.getCaseData().setFileLocationEdinburgh(
                    DynamicFixedListType.of(closeDataModel.getFileLocationEdinburgh()));
        }
    }

}
