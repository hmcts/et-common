package uk.gov.hmcts.ecm.common.model.servicebus.tasks;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import lombok.extern.slf4j.Slf4j;
import uk.gov.hmcts.ecm.common.model.ccd.SubmitEvent;
import uk.gov.hmcts.ecm.common.model.ccd.items.JurCodesTypeItem;
import uk.gov.hmcts.ecm.common.model.ccd.items.RepresentedTypeRItem;
import uk.gov.hmcts.ecm.common.model.ccd.types.*;
import uk.gov.hmcts.ecm.common.model.servicebus.datamodel.DataModelParent;
import uk.gov.hmcts.ecm.common.model.servicebus.datamodel.UpdateDataModel;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static com.google.common.base.Strings.isNullOrEmpty;
import static uk.gov.hmcts.ecm.common.model.helper.Constants.*;

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@SuperBuilder
@JsonIgnoreProperties(ignoreUnknown = true)
@Slf4j
public class UpdateDataTask extends DataTaskParent {

    public UpdateDataTask(DataModelParent dataModelParent) {
        super(dataModelParent);
    }

    public void run(SubmitEvent submitEvent) {

        amendUpdateFields(submitEvent);

    }

    private void amendUpdateFields(SubmitEvent submitEvent) {

        UpdateDataModel updateDataModel = ((UpdateDataModel) dataModelParent);

        if (!isNullOrEmpty(updateDataModel.getClaimantName())) {
            updateClaimantName(submitEvent, updateDataModel.getClaimantName());
        }
        if (!isNullOrEmpty(updateDataModel.getClaimantRep())) {
            updateClaimantRep(submitEvent, updateDataModel.getClaimantRep());
        }
        if (!isNullOrEmpty(updateDataModel.getRespondentRep())) {
            updateRespondentRep(submitEvent, updateDataModel.getRespondentRep());
        }

        if (!isNullOrEmpty(updateDataModel.getNewMultipleReference())) {
            submitEvent.getCaseData().setMultipleReference(updateDataModel.getNewMultipleReference());
        }
        if (!isNullOrEmpty(updateDataModel.getClerkResponsible())) {
            submitEvent.getCaseData().setClerkResponsible(updateDataModel.getClerkResponsible());
        }
        if (!isNullOrEmpty(updateDataModel.getPositionType())) {
            submitEvent.getCaseData().setPositionType(updateDataModel.getPositionType());
        }

        if (!isNullOrEmpty(updateDataModel.getJurisdictionCode())
                && !updateDataModel.getJurisdictionCode().equals(SELECT_NONE_VALUE)
                && !isNullOrEmpty(updateDataModel.getOutcomeUpdate())) {
            updateJurisdictionCode(submitEvent, updateDataModel.getJurisdictionCode(), updateDataModel.getOutcomeUpdate());
        }

        updateManagingOffice(submitEvent, updateDataModel);

        updateFlags(submitEvent, updateDataModel);
    }

    private void updateClaimantName(SubmitEvent submitEvent, String claimantNameNewValue) {

        ClaimantIndType claimantIndType;
        if (submitEvent.getCaseData().getClaimantIndType() != null) {
            claimantIndType = submitEvent.getCaseData().getClaimantIndType();
        } else {
            claimantIndType = new ClaimantIndType();
        }
        claimantIndType.setClaimantFirstNames(claimantNameNewValue);
        submitEvent.getCaseData().setClaimantIndType(claimantIndType);
    }

    private void updateClaimantRep(SubmitEvent submitEvent, String claimantRepNewValue) {

        RepresentedTypeC representedTypeC;
        if (submitEvent.getCaseData().getRepresentativeClaimantType() != null) {
            representedTypeC = submitEvent.getCaseData().getRepresentativeClaimantType();
        } else {
            representedTypeC = new RepresentedTypeC();
            submitEvent.getCaseData().setClaimantRepresentedQuestion(YES);
        }
        representedTypeC.setNameOfRepresentative(claimantRepNewValue);
        submitEvent.getCaseData().setRepresentativeClaimantType(representedTypeC);
    }

    private void updateRespondentRep(SubmitEvent submitEvent, String respondentRepNewValue) {

        if (submitEvent.getCaseData().getRepCollection() != null && !submitEvent.getCaseData().getRepCollection().isEmpty()) {
            RepresentedTypeRItem representedTypeRItem = submitEvent.getCaseData().getRepCollection().get(0);
            RepresentedTypeR representedTypeR;
            if (representedTypeRItem != null) {
                representedTypeR = representedTypeRItem.getValue();
            } else {
                representedTypeRItem = new RepresentedTypeRItem();
                representedTypeR = new RepresentedTypeR();
            }
            representedTypeR.setNameOfRepresentative(respondentRepNewValue);
            representedTypeRItem.setValue(representedTypeR);
            submitEvent.getCaseData().getRepCollection().set(0, representedTypeRItem);
        } else {
            RepresentedTypeRItem representedTypeRItem = new RepresentedTypeRItem();
            RepresentedTypeR representedTypeR = new RepresentedTypeR();
            representedTypeR.setNameOfRepresentative(respondentRepNewValue);
            representedTypeRItem.setValue(representedTypeR);
            List<RepresentedTypeRItem> repCollection = new ArrayList<>(Collections.singletonList(representedTypeRItem));
            submitEvent.getCaseData().setRepCollection(repCollection);
        }
    }

    private void updateManagingOffice(SubmitEvent submitEvent, UpdateDataModel updateDataModel) {

        if (!isNullOrEmpty(updateDataModel.getManagingOffice())) {
            submitEvent.getCaseData().setManagingOffice(updateDataModel.getManagingOffice());
        }
        if (!isNullOrEmpty(updateDataModel.getFileLocation())) {
            submitEvent.getCaseData().setFileLocation(updateDataModel.getFileLocation());
        }
        if (!isNullOrEmpty(updateDataModel.getFileLocationGlasgow())) {
            submitEvent.getCaseData().setFileLocationGlasgow(updateDataModel.getFileLocationGlasgow());
        }
        if (!isNullOrEmpty(updateDataModel.getFileLocationAberdeen())) {
            submitEvent.getCaseData().setFileLocationAberdeen(updateDataModel.getFileLocationAberdeen());
        }
        if (!isNullOrEmpty(updateDataModel.getFileLocationDundee())) {
            submitEvent.getCaseData().setFileLocationDundee(updateDataModel.getFileLocationDundee());
        }
        if (!isNullOrEmpty(updateDataModel.getFileLocationEdinburgh())) {
            submitEvent.getCaseData().setFileLocationEdinburgh(updateDataModel.getFileLocationEdinburgh());
        }
    }

    private void updateFlags(SubmitEvent submitEvent, UpdateDataModel updateDataModel) {

        if (!isNullOrEmpty(updateDataModel.getFlag1())) {
            submitEvent.getCaseData().setFlag1(updateDataModel.getFlag1());
        }
        if (!isNullOrEmpty(updateDataModel.getFlag2())) {
            submitEvent.getCaseData().setFlag2(updateDataModel.getFlag2());
        }
        if (!isNullOrEmpty(updateDataModel.getEQP())) {
            submitEvent.getCaseData().setEQP(updateDataModel.getEQP());
        }
    }

    private void updateJurisdictionCode(SubmitEvent submitEvent, String jurisdictionCode, String outcome) {

        List<JurCodesTypeItem> jurCodesTypeItems = new ArrayList<>();

        if (submitEvent.getCaseData().getJurCodesCollection() != null
                && !submitEvent.getCaseData().getJurCodesCollection().isEmpty()) {

            for (JurCodesTypeItem jurCodesTypeItem : submitEvent.getCaseData().getJurCodesCollection()) {

                if (jurCodesTypeItem.getValue().getJuridictionCodesList().equals(jurisdictionCode)) {
                    JurCodesType jurCodesType = jurCodesTypeItem.getValue();
                    jurCodesType.setJudgmentOutcome(outcome);
                    jurCodesTypeItem.setValue(jurCodesType);
                }

                jurCodesTypeItems.add(jurCodesTypeItem);
            }
        }

        submitEvent.getCaseData().setJurCodesCollection(jurCodesTypeItems);
    }

}
