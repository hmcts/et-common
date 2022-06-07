package uk.gov.hmcts.et.common.model.servicebus.tasks;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import uk.gov.hmcts.et.common.model.ccd.types.CasePreAcceptType;
import uk.gov.hmcts.et.common.model.helper.Constants;

import static org.junit.jupiter.api.Assertions.assertEquals;

class PreAcceptDataTaskTest {

    CaseDataBuilder caseDataBuilder;
    PreAcceptDataModelBuilder preAcceptDataModelBuilder;

    @BeforeEach
    void setUp() {
        caseDataBuilder = new CaseDataBuilder();
        preAcceptDataModelBuilder = new PreAcceptDataModelBuilder();
    }

    @ParameterizedTest
    @CsvSource({Constants.TRANSFERRED_STATE, Constants.CLOSED_STATE})
    void checkInvalidCaseStates(String state) {
        // When an invalid case state is passed through, the date's and accepted status should remain the same
        var updateModel = preAcceptDataModelBuilder.preAcceptDataModelBuilder("2022-02-02").build();
        var submitEvent = caseDataBuilder.buildAsSubmitEvent(state);
        var casePreAcceptType = new CasePreAcceptType();
        casePreAcceptType.setCaseAccepted(Constants.YES);
        casePreAcceptType.setDateAccepted("2021-01-01");
        submitEvent.getCaseData().setPreAcceptCase(casePreAcceptType);

        var task = new PreAcceptDataTask(updateModel);
        task.run(submitEvent);

        assertEquals("2021-01-01", submitEvent.getCaseData().getPreAcceptCase().getDateAccepted());
        assertEquals(Constants.YES, submitEvent.getCaseData().getPreAcceptCase().getCaseAccepted());
    }

    @ParameterizedTest
    @CsvSource({
            Constants.ACCEPTED_STATE + "," + Constants.YES,
            Constants.REJECTED_STATE + "," + Constants.YES,
            Constants.SUBMITTED_STATE + "," + Constants.YES
    })
    void checkValidCaseStates(String state, String caseAccepted) {
        var updateModel = preAcceptDataModelBuilder.preAcceptDataModelBuilder("2022-02-02").build();
        var submitEvent = caseDataBuilder.buildAsSubmitEvent(state);

        var task = new PreAcceptDataTask(updateModel);
        task.run(submitEvent);

        assertEquals(caseAccepted, submitEvent.getCaseData().getPreAcceptCase().getCaseAccepted());
        assertEquals("2022-02-02", submitEvent.getCaseData().getPreAcceptCase().getDateAccepted());
    }

    @Test
    void checkIfCaseAlreadyAccepted() {
        // If a case has already been accepted, it should not be overwritten with a new data
        var updateModel = preAcceptDataModelBuilder.preAcceptDataModelBuilder("2022-02-02").build();
        var submitEvent = caseDataBuilder.buildAsSubmitEvent(Constants.ACCEPTED_STATE);
        var casePreAcceptType = new CasePreAcceptType();
        casePreAcceptType.setCaseAccepted(Constants.YES);
        casePreAcceptType.setDateAccepted("2021-01-01");
        submitEvent.getCaseData().setPreAcceptCase(casePreAcceptType);
        var task = new PreAcceptDataTask(updateModel);
        task.run(submitEvent);

        assertEquals("2021-01-01", submitEvent.getCaseData().getPreAcceptCase().getDateAccepted());
        assertEquals(Constants.YES, submitEvent.getCaseData().getPreAcceptCase().getCaseAccepted());
    }
}
