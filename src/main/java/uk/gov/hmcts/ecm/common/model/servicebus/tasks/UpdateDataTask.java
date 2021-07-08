package uk.gov.hmcts.ecm.common.model.servicebus.tasks;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.google.common.base.Strings;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import lombok.extern.slf4j.Slf4j;
import uk.gov.hmcts.ecm.common.model.ccd.CaseData;
import uk.gov.hmcts.ecm.common.model.ccd.SubmitEvent;
import uk.gov.hmcts.ecm.common.model.ccd.items.JurCodesTypeItem;
import uk.gov.hmcts.ecm.common.model.ccd.items.RepresentedTypeRItem;
import uk.gov.hmcts.ecm.common.model.ccd.items.RespondentSumTypeItem;
import uk.gov.hmcts.ecm.common.model.ccd.types.JudgementType;
import uk.gov.hmcts.ecm.common.model.ccd.types.JurCodesType;
import uk.gov.hmcts.ecm.common.model.ccd.types.RepresentedTypeR;
import uk.gov.hmcts.ecm.common.model.ccd.types.RespondentSumType;
import uk.gov.hmcts.ecm.common.model.servicebus.datamodel.DataModelParent;
import uk.gov.hmcts.ecm.common.model.servicebus.datamodel.UpdateDataModel;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Optional;
import java.util.UUID;

import static com.google.common.base.Strings.isNullOrEmpty;
import static uk.gov.hmcts.ecm.common.model.helper.Constants.NO;
import static uk.gov.hmcts.ecm.common.model.helper.Constants.YES;

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

        var updateDataModel = ((UpdateDataModel) dataModelParent);

        batchUpdate1(submitEvent.getCaseData(), updateDataModel);

        batchUpdate3(submitEvent.getCaseData(), updateDataModel);

    }

    private void batchUpdate1(CaseData caseData, UpdateDataModel updateDataModel) {

        if (!isNullOrEmpty(updateDataModel.getPositionType())) {
            caseData.setPositionType(updateDataModel.getPositionType());
            dateToCurrentPosition(caseData);
        }

        if (!isNullOrEmpty(updateDataModel.getClerkResponsible())) {
            caseData.setClerkResponsible(updateDataModel.getClerkResponsible());
        }

        if (!isNullOrEmpty(updateDataModel.getHearingStage())) {
            caseData.setHearingStage(updateDataModel.getHearingStage());
        }

        if (!isNullOrEmpty(updateDataModel.getReceiptDate())) {
            caseData.setReceiptDate(updateDataModel.getReceiptDate());
        }

        updateManagingOffice(caseData, updateDataModel);

    }

    private void dateToCurrentPosition(CaseData caseData) {
        if (isNullOrEmpty(caseData.getCurrentPosition())
                || !caseData.getPositionType().equals(caseData.getCurrentPosition())) {
            caseData.setDateToPosition(LocalDate.now().toString());
            caseData.setCurrentPosition(caseData.getPositionType());
        }
    }

    private void updateManagingOffice(CaseData caseData, UpdateDataModel updateDataModel) {

        if (!isNullOrEmpty(updateDataModel.getManagingOffice())) {
            caseData.setManagingOffice(updateDataModel.getManagingOffice());
        }

        if (!isNullOrEmpty(updateDataModel.getFileLocation())) {
            caseData.setFileLocation(updateDataModel.getFileLocation());
        }

        if (!isNullOrEmpty(updateDataModel.getFileLocationGlasgow())) {
            caseData.setFileLocationGlasgow(updateDataModel.getFileLocationGlasgow());
        }

        if (!isNullOrEmpty(updateDataModel.getFileLocationAberdeen())) {
            caseData.setFileLocationAberdeen(updateDataModel.getFileLocationAberdeen());
        }

        if (!isNullOrEmpty(updateDataModel.getFileLocationDundee())) {
            caseData.setFileLocationDundee(updateDataModel.getFileLocationDundee());
        }

        if (!isNullOrEmpty(updateDataModel.getFileLocationEdinburgh())) {
            caseData.setFileLocationEdinburgh(updateDataModel.getFileLocationEdinburgh());
        }

    }

    private boolean shouldRepresentativeCBeRemoved(CaseData caseData, UpdateDataModel updateDataModel) {
        if (Strings.isNullOrEmpty(updateDataModel.getIsClaimantRepRemovalUpdate()) || updateDataModel.getIsClaimantRepRemovalUpdate().equals(NO)) {
            return false;
        }
       else if (!Strings.isNullOrEmpty(updateDataModel.getIsClaimantRepRemovalUpdate())
                && updateDataModel.getIsClaimantRepRemovalUpdate().equals(YES)
                && updateDataModel.getRepresentativeClaimantType().getNameOfRepresentative().equals(caseData.getRepresentativeClaimantType().getNameOfRepresentative())
                && updateDataModel.getRepresentativeClaimantType().getNameOfOrganisation().equals(caseData.getRepresentativeClaimantType().getNameOfOrganisation()))  {
            return true;
        }
        return false;
    }
    private void batchUpdate3(CaseData caseData, UpdateDataModel updateDataModel) {
        boolean shouldRepresentativeCBeRemoved = shouldRepresentativeCBeRemoved(caseData,updateDataModel);
        if (updateDataModel.getRepresentativeClaimantType() != null && !shouldRepresentativeCBeRemoved) {
            caseData.setRepresentativeClaimantType(updateDataModel.getRepresentativeClaimantType());
            caseData.setClaimantRepresentedQuestion(YES);
        }
        else if (updateDataModel.getRepresentativeClaimantType() != null  && shouldRepresentativeCBeRemoved) {
                caseData.setRepresentativeClaimantType(null);
                caseData.setClaimantRepresentedQuestion(NO);
        }

        if (updateDataModel.getJurCodesType() != null) {
            updateJurisdictionCode(caseData, updateDataModel.getJurCodesType());
        }

        if (updateDataModel.getRespondentSumType() != null) {
            updateRespondentSumType(caseData, updateDataModel.getRespondentSumType());
        }

        if (updateDataModel.getJudgementType() != null) {
            updateJudgement(caseData, updateDataModel.getJudgementType());
        }

        if (updateDataModel.getRepresentedType() != null) {
            updateRespondentRep(caseData, updateDataModel);
        }

    }

    private void updateRespondentSumType(CaseData caseData, RespondentSumType respondentSumType) {

        if (caseData.getRespondentCollection() != null) {
            caseData.getRespondentCollection().add(createRespondentSumTypeItem(respondentSumType));

        } else {
            caseData.setRespondentCollection(
                    new ArrayList<>(Collections.singletonList(createRespondentSumTypeItem(respondentSumType))));
        }

    }

    private RespondentSumTypeItem createRespondentSumTypeItem(RespondentSumType respondentSumType) {

        var respondentSumTypeItem = new RespondentSumTypeItem();

        respondentSumTypeItem.setId(UUID.randomUUID().toString());
        respondentSumTypeItem.setValue(respondentSumType);

        return respondentSumTypeItem;

    }

    private void updateJurisdictionCode(CaseData caseData, JurCodesType jurCodesType) {

        if (caseData.getJurCodesCollection() != null) {

            log.info("JurCodesCollection: " + caseData.getJurCodesCollection());
            log.info("JurCodesType: " + jurCodesType);
            Optional<JurCodesTypeItem> jurCodesTypeItemOptional =
                    caseData.getJurCodesCollection().stream()
                            .filter(jurCodesTypeItem ->
                                    jurCodesTypeItem.getValue().getJuridictionCodesList()
                                            .equals(jurCodesType.getJuridictionCodesList()))
                            .findAny();

            if (jurCodesTypeItemOptional.isEmpty()) {
                log.info("JurCodes Empty");
                caseData.getJurCodesCollection().add(createJurCodesTypeItem(jurCodesType));
            } else {
                log.info("JurCodes Not Empty");
            }

        } else {
            caseData.setJurCodesCollection(
                    new ArrayList<>(Collections.singletonList(createJurCodesTypeItem(jurCodesType))));
        }

    }

    private JurCodesTypeItem createJurCodesTypeItem(JurCodesType jurCodesType) {

        var jurCodesTypeItem = new JurCodesTypeItem();

        jurCodesTypeItem.setId(UUID.randomUUID().toString());
        jurCodesTypeItem.setValue(jurCodesType);

        return jurCodesTypeItem;

    }

    private void updateJudgement(CaseData caseData, JudgementType judgementType) {
        CaseJudgementUpdate.updateCaseWithJudgement(caseData, judgementType);
    }

    private void updateRespondentRep(CaseData caseData, UpdateDataModel updateDataModel) {

        if (caseData.getRespondentCollection() != null) {
            RepresentedTypeR representedType = updateDataModel.getRepresentedType();
            String isRespondentRepRemovalUpdate = updateDataModel.getIsRespondentRepRemovalUpdate();
            Optional<RespondentSumTypeItem> respondentSumTypeItemOptional =
                    caseData.getRespondentCollection().stream()
                            .filter(respondentSumTypeItem ->
                                    respondentSumTypeItem.getValue().getRespondentName()
                                            .equals(representedType.getRespRepName()))
                            .findAny();

            if (respondentSumTypeItemOptional.isPresent()) {
                if (Strings.isNullOrEmpty(isRespondentRepRemovalUpdate) || isRespondentRepRemovalUpdate.equals(NO)) {
                    addRespondentRepUpdates(caseData, updateDataModel);
                } else if (!Strings.isNullOrEmpty(isRespondentRepRemovalUpdate) && isRespondentRepRemovalUpdate.equals(YES)) {
                    addRespondentRepRemovalUpdate(caseData, representedType);
                }
            }
        }

    }

    private void addRespondentRepRemovalUpdate(CaseData caseData, RepresentedTypeR representedType) {

        if (representedTypeRItemExists(caseData, representedType)) {

            var representedTypeRItem = getExistingRepresentedTypeRItem(caseData, representedType);

            if (representedTypeRItem != null) {
                caseData.getRepCollection().remove(representedTypeRItem);
            }
        }

    }

    private void addRespondentRepUpdates(CaseData caseData, UpdateDataModel updateDataModel) {

        RepresentedTypeR representedType = updateDataModel.getRepresentedType();

        if (caseData.getRepCollection() != null) {
            updateCaseDataRepCollection(caseData, representedType);
        } else {
            caseData.setRepCollection(
                    new ArrayList<>(Collections.singletonList(createRespondentRepTypeItem(representedType))));
        }

    }

    private void updateCaseDataRepCollection(CaseData caseData, RepresentedTypeR representedType) {

        if (representedTypeRItemExists(caseData, representedType)) {
            var representedTypeRItem = getExistingRepresentedTypeRItem(caseData,
                    representedType);
            if (representedTypeRItem != null) {
                representedTypeRItem.setValue(representedType);
            }
        } else {
            caseData.getRepCollection().add(createRespondentRepTypeItem(representedType));
        }

    }


    private RepresentedTypeRItem getExistingRepresentedTypeRItem(CaseData caseData, RepresentedTypeR representedType) {

        for (RepresentedTypeRItem representedTypeRItem : caseData.getRepCollection()) {
            if (representedTypeRItem.getValue().getRespRepName() != null &&
                    (representedTypeRItem.getValue().getRespRepName()
                            .equals(representedType.getRespRepName()))) {
                return representedTypeRItem;
            }
        }
        return null;

    }

    private boolean representedTypeRItemExists(CaseData caseData, RepresentedTypeR representedType) {

        var representedTypeRItemFound = false;
        for (RepresentedTypeRItem representedTypeRItem : caseData.getRepCollection()) {
            if (representedTypeRItem.getValue().getRespRepName()
                    .equals(representedType.getRespRepName())) {
                representedTypeRItem.setValue(new RepresentedTypeR());
                representedTypeRItemFound = true;
            }
        }
        return representedTypeRItemFound;

    }

    private RepresentedTypeRItem createRespondentRepTypeItem(RepresentedTypeR representedType) {

        var representedTypeRItem = new RepresentedTypeRItem();

        representedTypeRItem.setId(UUID.randomUUID().toString());
        representedTypeRItem.setValue(representedType);

        return representedTypeRItem;

    }

}
