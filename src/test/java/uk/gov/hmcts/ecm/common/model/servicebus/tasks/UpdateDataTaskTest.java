package uk.gov.hmcts.ecm.common.model.servicebus.tasks;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

public class UpdateDataTaskTest {

    CaseDataBuilder caseDataBuilder;
    UpdateDataModelBuilder updateDataModelBuilder;

    @Before
    public void setUp() {
        caseDataBuilder = new CaseDataBuilder();
        updateDataModelBuilder = new UpdateDataModelBuilder();
    }

    @Test
    public void addNewJurisdictionCode() {
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
    public void updateExistingJurisdictionCode() {
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
    public void noUpdateToJurisdictionCode() {
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
    public void addJurisdictionCodeWhenNonExist() {
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
    public void noJurisdictionCode() {
        var updateModel = updateDataModelBuilder.build();
        var submitEvent = caseDataBuilder.buildAsSubmitEvent("Accepted");

        var task = new UpdateDataTask(updateModel);
        task.run(submitEvent);

        assertNull(submitEvent.getCaseData().getJurCodesCollection());
    }

    @Test
    public void checkJurisdictionCode() {
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
}
