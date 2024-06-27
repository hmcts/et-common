package uk.gov.hmcts.ecm.common.model.servicebus.tasks;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import uk.gov.hmcts.et.common.model.ccd.types.JurCodesType;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static uk.gov.hmcts.ecm.common.model.helper.Constants.YES;

class UpdateDataTaskTest {

    CaseDataBuilder caseDataBuilder;
    UpdateDataModelBuilder updateDataModelBuilder;

    @BeforeEach
    void setUp() {
        caseDataBuilder = new CaseDataBuilder();
        updateDataModelBuilder = new UpdateDataModelBuilder();
    }

    @Test
    void addNewJurisdictionCode() {
        var updateModel = updateDataModelBuilder.withJurisdictionCode("Code1", "Outcome1").build();
        var submitEvent = caseDataBuilder.withJurisdictionCode("Code2", "Outcome2")
                .buildAsSubmitEvent("Accepted");

        var task = new UpdateDataTask(updateModel);
        task.run(submitEvent);

        var expectedCode = "Code2";
        var expectedOutcome = "Outcome2";

        var resultJurCollection = submitEvent.getCaseData().getJurCodesCollection();
        assertEquals(2, resultJurCollection.size());
        assertNotNull(
                resultJurCollection.stream().filter(j ->
                        j.getValue().getJuridictionCodesList().equals(expectedCode)
                                && j.getValue().getJudgmentOutcome().equals(expectedOutcome))
        );
    }

    @Test
    void addNewMultipleJurisdictionCodes() {
        var updateModel = updateDataModelBuilder.withJurisdictionCode("Code1", "Outcome1").build();
        var jurCodeType = new JurCodesType();
        jurCodeType.setJuridictionCodesList("DAG");
        jurCodeType.setJudgmentOutcome("Not allocated");
        updateModel.getJurCodesList().add(jurCodeType);

        var submitEvent = caseDataBuilder.withJurisdictionCode("Code2", "Outcome2")
                .buildAsSubmitEvent("Accepted");

        var task = new UpdateDataTask(updateModel);
        task.run(submitEvent);

        var resultJurCollection = submitEvent.getCaseData().getJurCodesCollection();
        assertEquals("DAG", resultJurCollection.get(2).getValue().getJuridictionCodesList());
        assertEquals("Not allocated", resultJurCollection.get(2).getValue().getJudgmentOutcome());
        assertEquals(3, resultJurCollection.size());
    }

    @Test
    void updateExistingJurisdictionCode() {
        var updateModel = updateDataModelBuilder.withJurisdictionCode("Code1", "Outcome1").build();
        var submitEvent = caseDataBuilder.withJurisdictionCode("Code1", "Outcome2")
                                        .buildAsSubmitEvent("Accepted");

        var task = new UpdateDataTask(updateModel);
        task.run(submitEvent);

        var expectedCode = "Code1";
        var expectedOutcome = "Outcome2";

        var resultJurCollection = submitEvent.getCaseData().getJurCodesCollection();
        assertEquals(1, resultJurCollection.size());
        assertNotNull(
                resultJurCollection.stream().filter(j ->
                        j.getValue().getJuridictionCodesList().equals(expectedCode)
                                && j.getValue().getJudgmentOutcome().equals(expectedOutcome))
        );
    }

    @Test
    void noUpdateToJurisdictionCode() {
        var updateModel = updateDataModelBuilder.build();
        var submitEvent = caseDataBuilder.withJurisdictionCode("Code1", "Outcome1")
                .buildAsSubmitEvent("Accepted");

        var task = new UpdateDataTask(updateModel);
        task.run(submitEvent);

        var expectedCode = "Code1";
        var expectedOutcome = "Outcome1";

        var resultJurCollection = submitEvent.getCaseData().getJurCodesCollection();
        assertEquals(1, resultJurCollection.size());
        assertNotNull(
                resultJurCollection.stream().filter(j ->
                        j.getValue().getJuridictionCodesList().equals(expectedCode)
                                && j.getValue().getJudgmentOutcome().equals(expectedOutcome))
        );
    }

    @Test
    void addJurisdictionCodeWhenNonExist() {
        var updateModel = updateDataModelBuilder.withJurisdictionCode("Code1", "Outcome1").build();
        var submitEvent = caseDataBuilder.buildAsSubmitEvent("Accepted");

        var task = new UpdateDataTask(updateModel);
        task.run(submitEvent);

        var expectedCode = "Code1";
        var expectedOutcome = "Outcome1";

        var resultJurCollection = submitEvent.getCaseData().getJurCodesCollection();
        assertEquals(1, resultJurCollection.size());
        assertNotNull(
                resultJurCollection.stream().filter(j ->
                        j.getValue().getJuridictionCodesList().equals(expectedCode)
                                && j.getValue().getJudgmentOutcome().equals(expectedOutcome))
        );
    }

    @Test
    void noJurisdictionCodeOrCaseStayed() {
        var updateModel = updateDataModelBuilder.build();
        var submitEvent = caseDataBuilder.buildAsSubmitEvent("Accepted");

        var task = new UpdateDataTask(updateModel);
        task.run(submitEvent);

        assertNull(submitEvent.getCaseData().getJurCodesCollection());
        assertNull(submitEvent.getCaseData().getBatchCaseStayed());
    }

    @Test
    void checkJurisdictionCode() {
        var updateModel = updateDataModelBuilder.build();
        updateModel.setIsFixCase("Yes");

        var submitEvent = caseDataBuilder.withJurisdictionCode("ADT", "Outcome1")
                .buildAsSubmitEvent("Accepted");
        submitEvent.getCaseData().getJurCodesCollection().get(0).setId("ADT");

        assertEquals("ADT", submitEvent.getCaseData().getJurCodesCollection().get(0).getId());

        var task = new UpdateDataTask(updateModel);
        task.run(submitEvent);

        assertNotNull(submitEvent.getCaseData().getJurCodesCollection());
        assertNotEquals("ADT", submitEvent.getCaseData().getJurCodesCollection().get(0).getId());
        assertTrue(submitEvent.getCaseData().getJurCodesCollection().get(0).getId().matches(
                "[0-9a-f]{8}-[0-9a-f]{4}-[1-5][0-9a-f]{3}-[89ab][0-9a-f]{3}-[0-9a-f]{12}"));
    }

    @Test
    void checkSubMultiple() {
        var updateModel = updateDataModelBuilder.build();
        updateModel.setSubMultiple("SubMultiple");
        var submitEvent = caseDataBuilder.buildAsSubmitEvent("Accepted");
        var task = new UpdateDataTask(updateModel);
        task.run(submitEvent);
        assertEquals("SubMultiple", submitEvent.getCaseData().getSubMultipleName());
    }

    @Test
    void batchUpdate1_withCaseStayed_setsBatchCaseStayed() {
        var updateModel = updateDataModelBuilder.build();
        updateModel.setBatchCaseStayed(YES);
        var submitEvent = caseDataBuilder.buildAsSubmitEvent("Accepted");

        var task = new UpdateDataTask(updateModel);
        task.run(submitEvent);

        assertEquals(YES, submitEvent.getCaseData().getBatchCaseStayed());
    }
}
