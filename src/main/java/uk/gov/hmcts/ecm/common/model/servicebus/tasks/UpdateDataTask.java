package uk.gov.hmcts.ecm.common.model.servicebus.tasks;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import lombok.extern.slf4j.Slf4j;
import uk.gov.hmcts.ecm.common.model.ccd.CaseData;
import uk.gov.hmcts.ecm.common.model.ccd.SubmitEvent;
import uk.gov.hmcts.ecm.common.model.ccd.items.JurCodesTypeItem;
import uk.gov.hmcts.ecm.common.model.ccd.items.RespondentSumTypeItem;
import uk.gov.hmcts.ecm.common.model.ccd.types.*;
import uk.gov.hmcts.ecm.common.model.servicebus.datamodel.DataModelParent;
import uk.gov.hmcts.ecm.common.model.servicebus.datamodel.UpdateDataModel;
import java.util.ArrayList;
import java.util.Collections;

import static com.google.common.base.Strings.isNullOrEmpty;
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

        UpdateDataModel updateDataModel = ((UpdateDataModel) dataModelParent);

        batchUpdate1(submitEvent.getCaseData(), updateDataModel);

        batchUpdate3(submitEvent.getCaseData(), updateDataModel);

    }

    private void batchUpdate1(CaseData caseData, UpdateDataModel updateDataModel) {

        if (!isNullOrEmpty(updateDataModel.getPositionType())) {
            caseData.setPositionType(updateDataModel.getPositionType());
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

    private void batchUpdate3(CaseData caseData, UpdateDataModel updateDataModel) {

        if (updateDataModel.getRepresentativeClaimantType() != null) {
            caseData.setRepresentativeClaimantType(updateDataModel.getRepresentativeClaimantType());
            caseData.setClaimantRepresentedQuestion(YES);
        }

        if (updateDataModel.getJurCodesType() != null) {
            updateJurisdictionCode(caseData, updateDataModel.getJurCodesType());
        }

        if (updateDataModel.getRespondentSumType() != null) {
            updateRespondentSumType(caseData, updateDataModel.getRespondentSumType());
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

        RespondentSumTypeItem respondentSumTypeItem = new RespondentSumTypeItem();

        respondentSumTypeItem.setId(respondentSumType.getRespondentName());
        respondentSumTypeItem.setValue(respondentSumType);

        return respondentSumTypeItem;

    }

    private void updateJurisdictionCode(CaseData caseData, JurCodesType jurCodesType) {

        if (caseData.getJurCodesCollection() != null) {

            caseData.getJurCodesCollection().add(createJurCodesTypeItem(jurCodesType));

        } else {
            caseData.setJurCodesCollection(
                    new ArrayList<>(Collections.singletonList(createJurCodesTypeItem(jurCodesType))));
        }

    }

    private JurCodesTypeItem createJurCodesTypeItem(JurCodesType jurCodesType) {

        JurCodesTypeItem jurCodesTypeItem = new JurCodesTypeItem();

        jurCodesTypeItem.setId(jurCodesType.getJuridictionCodesList());
        jurCodesTypeItem.setValue(jurCodesType);

        return jurCodesTypeItem;

    }

}
