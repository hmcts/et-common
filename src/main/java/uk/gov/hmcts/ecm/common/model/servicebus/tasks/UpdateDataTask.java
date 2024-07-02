package uk.gov.hmcts.ecm.common.model.servicebus.tasks;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.google.common.base.Strings;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import uk.gov.hmcts.ecm.common.model.servicebus.datamodel.DataModelParent;
import uk.gov.hmcts.ecm.common.model.servicebus.datamodel.UpdateDataModel;
import uk.gov.hmcts.et.common.model.bulk.types.DynamicFixedListType;
import uk.gov.hmcts.et.common.model.ccd.CaseData;
import uk.gov.hmcts.et.common.model.ccd.SubmitEvent;
import uk.gov.hmcts.et.common.model.ccd.items.JurCodesTypeItem;
import uk.gov.hmcts.et.common.model.ccd.items.RepresentedTypeRItem;
import uk.gov.hmcts.et.common.model.ccd.items.RespondentSumTypeItem;
import uk.gov.hmcts.et.common.model.ccd.types.JudgementType;
import uk.gov.hmcts.et.common.model.ccd.types.JurCodesType;
import uk.gov.hmcts.et.common.model.ccd.types.RepresentedTypeC;
import uk.gov.hmcts.et.common.model.ccd.types.RepresentedTypeR;
import uk.gov.hmcts.et.common.model.ccd.types.RespondentSumType;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

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
        batchUpdate2(submitEvent.getCaseData(), updateDataModel);
        batchUpdate3(submitEvent.getCaseData(), updateDataModel);
        resetJurisdictionCodes(submitEvent.getCaseData(), updateDataModel);
    }

    private void resetJurisdictionCodes(CaseData caseData, UpdateDataModel updateDataModel) {
        if (CollectionUtils.isNotEmpty(caseData.getJurCodesCollection())
                && YES.equals(updateDataModel.getIsFixCase())) {
            for (var jurCodeTypeItem : caseData.getJurCodesCollection()) {
                if (!jurCodeTypeItem.getId().matches(
                        "[0-9a-f]{8}-[0-9a-f]{4}-[1-5][0-9a-f]{3}-[89ab][0-9a-f]{3}-[0-9a-f]{12}")) {
                    jurCodeTypeItem.setId(UUID.randomUUID().toString());
                }
            }
        }

    }

    private void batchUpdate1(CaseData caseData, UpdateDataModel updateDataModel) {
        if (!isNullOrEmpty(updateDataModel.getPositionType())) {
            caseData.setPositionType(updateDataModel.getPositionType());
            dateToCurrentPosition(caseData);
        }

        var clerkResponsible = updateDataModel.getClerkResponsible();
        if (clerkResponsible != null) {
            caseData.setClerkResponsible(DynamicFixedListType.of(clerkResponsible));
        }

        if (!isNullOrEmpty(updateDataModel.getHearingStage())) {
            caseData.setHearingStage(updateDataModel.getHearingStage());
        }

        if (!isNullOrEmpty(updateDataModel.getReceiptDate())) {
            caseData.setReceiptDate(updateDataModel.getReceiptDate());
        }

        if (!isNullOrEmpty(updateDataModel.getBatchCaseStayed())) {
            caseData.setBatchCaseStayed(updateDataModel.getBatchCaseStayed());
        }

        updateManagingOffice(caseData, updateDataModel);
    }

    private void batchUpdate2(CaseData caseData, UpdateDataModel updateDataModel) {
        if (!isNullOrEmpty(updateDataModel.getSubMultiple())) {
            caseData.setSubMultipleName(updateDataModel.getSubMultiple());
        }
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

        var fileLocation = updateDataModel.getFileLocation();
        if (fileLocation != null) {
            caseData.setFileLocation(DynamicFixedListType.of(fileLocation));
        }
        if (updateDataModel.getFileLocationGlasgow() != null) {
            caseData.setFileLocationGlasgow(DynamicFixedListType.of(updateDataModel.getFileLocationGlasgow()));
        }

        if (updateDataModel.getFileLocationAberdeen() != null) {
            caseData.setFileLocationAberdeen(DynamicFixedListType.of(updateDataModel.getFileLocationAberdeen()));
        }

        if (updateDataModel.getFileLocationDundee() != null) {
            caseData.setFileLocationDundee(DynamicFixedListType.of(updateDataModel.getFileLocationDundee()));
        }

        if (updateDataModel.getFileLocationEdinburgh() != null) {
            caseData.setFileLocationEdinburgh(DynamicFixedListType.of(updateDataModel.getFileLocationEdinburgh()));
        }
    }

    private boolean shouldClaimantRepresentativeBeRemoved(CaseData caseData, UpdateDataModel updateDataModel) {
        if (isNullOrEmpty(updateDataModel.getIsClaimantRepRemovalUpdate())
                || updateDataModel.getIsClaimantRepRemovalUpdate().equals(NO)) {
            return false;
        } else if (!isNullOrEmpty(updateDataModel.getIsClaimantRepRemovalUpdate())
                && updateDataModel.getIsClaimantRepRemovalUpdate().equals(YES)
                && (updateDataModel.getRepresentativeClaimantType() != null)
                && (caseData.getRepresentativeClaimantType() != null)
                && updateDataModel.getRepresentativeClaimantType()
                   .getNameOfRepresentative().equals(caseData.getRepresentativeClaimantType()
                        .getNameOfRepresentative())
                && organisationMatch(caseData, updateDataModel)) {
            log.info("Claimant representative will be removed for case: " + caseData.getEthosCaseReference());
            return true;
        }
        return false;
    }

    private boolean shouldRespondentRepresentativeBeRemoved(CaseData caseData, UpdateDataModel updateDataModel) {
        if (Strings.isNullOrEmpty(updateDataModel.getIsRespondentRepRemovalUpdate())
                || updateDataModel.getIsRespondentRepRemovalUpdate().equals(NO)) {
            return false;
        } else if (!Strings.isNullOrEmpty(updateDataModel.getIsRespondentRepRemovalUpdate())
                && updateDataModel.getIsRespondentRepRemovalUpdate().equals(YES)
                && (updateDataModel.getRepresentedType() != null)
                && CollectionUtils.isNotEmpty(caseData.getRepCollection())
                && CollectionUtils.isNotEmpty(caseData.getRepCollection()
                .stream().filter(a -> a.getValue().getRespRepName()
                        .equals(updateDataModel.getRepresentedType().getRespRepName())).collect(Collectors.toList()))) {
            log.info("Respondent representative will be removed for case: " + caseData.getEthosCaseReference());
            return true;
        }
        return false;
    }

    private boolean organisationMatch(CaseData caseData, UpdateDataModel updateDataModel) {
        return Strings.isNullOrEmpty(updateDataModel.getRepresentativeClaimantType().getNameOfOrganisation())
                ? Strings.isNullOrEmpty(caseData.getRepresentativeClaimantType().getNameOfOrganisation())
                : updateDataModel.getRepresentativeClaimantType()
                .getNameOfOrganisation().equals(caseData.getRepresentativeClaimantType().getNameOfOrganisation());
    }

    private void batchUpdate3(CaseData caseData, UpdateDataModel updateDataModel) {
        boolean shouldRepresentativeCBeRemoved = shouldClaimantRepresentativeBeRemoved(caseData, updateDataModel);
        if (updateDataModel.getRepresentativeClaimantType() != null && !shouldRepresentativeCBeRemoved) {
            caseData.setRepresentativeClaimantType(updateDataModel.getRepresentativeClaimantType());
            caseData.setClaimantRepresentedQuestion(YES);
        } else if (updateDataModel.getRepresentativeClaimantType() != null) {
            caseData.setRepresentativeClaimantType(new RepresentedTypeC());
            caseData.setClaimantRepresentedQuestion(NO);
        }

        if (updateDataModel.getJurCodesType() != null) {
            updateJurisdictionCode(caseData, updateDataModel.getJurCodesType());
        }

        if (updateDataModel.getJurCodesList() != null) {
            updateDataModel.getJurCodesList().forEach(o -> updateJurisdictionCode(caseData, o));
        }

        if (updateDataModel.getRespondentSumType() != null) {
            updateRespondentSumType(caseData, updateDataModel.getRespondentSumType());
        }

        if (updateDataModel.getJudgementType() != null) {
            updateJudgement(caseData, updateDataModel.getJudgementType());
        }

        if (updateDataModel.getRepresentedType() != null
                && !shouldRespondentRepresentativeBeRemoved(caseData, updateDataModel)) {
            addRespondentRep(caseData, updateDataModel.getRepresentedType());
        } else if (updateDataModel.getRepresentedType() != null) {
            removeRespondentRep(caseData, updateDataModel.getRepresentedType());
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
                jurCodesTypeItemOptional.get().setValue(jurCodesType);
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

    private void addRespondentRep(CaseData caseData, RepresentedTypeR representedType) {
        if (CollectionUtils.isEmpty(caseData.getRespondentCollection())) {
            return;
        }

        Optional<RespondentSumTypeItem> respondentSumTypeItemOptional =
                caseData.getRespondentCollection().stream()
                        .filter(respondentSumTypeItem ->
                                respondentSumTypeItem.getValue().getRespondentName()
                                        .equals(representedType.getRespRepName()))
                        .findAny();

        if (respondentSumTypeItemOptional.isEmpty()) {
            return;
        }

        if (caseData.getRepCollection() != null) {
            var found = false;
            for (RepresentedTypeRItem representedTypeRItem : caseData.getRepCollection()) {
                if (representedTypeRItem.getValue().getRespRepName().equals(representedType.getRespRepName())) {
                    representedTypeRItem.setValue(representedType);
                    found = true;
                }
            }

            if (!found) {
                caseData.getRepCollection().add(createRespondentRepTypeItem(representedType));
            }
        } else {
            caseData.setRepCollection(
                    new ArrayList<>(Collections.singletonList(createRespondentRepTypeItem(representedType))));
        }
    }

    private void removeRespondentRep(CaseData caseData, RepresentedTypeR representedType) {
        if (caseData.getRespondentCollection() != null) {
            Optional<RespondentSumTypeItem> respondentSumTypeItemOptional =
                    caseData.getRespondentCollection().stream()
                            .filter(respondentSumTypeItem ->
                                    respondentSumTypeItem.getValue().getRespondentName()
                                            .equals(representedType.getRespRepName()))
                            .findAny();

            if (respondentSumTypeItemOptional.isPresent() && CollectionUtils.isNotEmpty(caseData.getRepCollection())) {
                List<RepresentedTypeRItem> toBeRemoved = caseData.getRepCollection()
                        .stream().filter(a -> a.getValue().getRespRepName().equals(representedType.getRespRepName()))
                        .collect(Collectors.toList());
                if (CollectionUtils.isNotEmpty(toBeRemoved)) {
                    log.info("Respondent representatives to be removed are: " + toBeRemoved.size());
                    for (RepresentedTypeRItem r : toBeRemoved) {
                        caseData.getRepCollection().stream().filter(a -> a.getValue().equals(r.getValue()))
                                .findFirst().ifPresent(representedTypeRItem -> representedTypeRItem.setId(null));
                        caseData.getRepCollection().stream().filter(a -> a.getValue().equals(r.getValue()))
                                .findFirst().ifPresent(representedTypeRItem -> representedTypeRItem.setValue(null));
                    }
                }
            }
        }
    }

    private RepresentedTypeRItem createRespondentRepTypeItem(RepresentedTypeR representedType) {
        var representedTypeRItem = new RepresentedTypeRItem();

        representedTypeRItem.setId(UUID.randomUUID().toString());
        representedTypeRItem.setValue(representedType);

        return representedTypeRItem;
    }
}
