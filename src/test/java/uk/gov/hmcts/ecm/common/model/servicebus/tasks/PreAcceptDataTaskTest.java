package uk.gov.hmcts.ecm.common.model.servicebus.tasks;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import uk.gov.hmcts.ecm.common.model.servicebus.datamodel.PreAcceptDataModel;
import uk.gov.hmcts.et.common.model.ccd.SubmitEvent;
import uk.gov.hmcts.et.common.model.ccd.types.CasePreAcceptType;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static uk.gov.hmcts.ecm.common.model.helper.Constants.ACCEPTED_STATE;
import static uk.gov.hmcts.ecm.common.model.helper.Constants.CLOSED_STATE;
import static uk.gov.hmcts.ecm.common.model.helper.Constants.REJECTED_STATE;
import static uk.gov.hmcts.ecm.common.model.helper.Constants.SUBMITTED_STATE;
import static uk.gov.hmcts.ecm.common.model.helper.Constants.TRANSFERRED_STATE;
import static uk.gov.hmcts.ecm.common.model.helper.Constants.YES;

class PreAcceptDataTaskTest {

    CaseDataBuilder caseDataBuilder;
    PreAcceptDataModelBuilder preAcceptDataModelBuilder;

    @BeforeEach
    void setUp() {
        caseDataBuilder = new CaseDataBuilder();
        preAcceptDataModelBuilder = new PreAcceptDataModelBuilder();
    }

    @ParameterizedTest
    @CsvSource({TRANSFERRED_STATE, CLOSED_STATE})
    void checkInvalidCaseStates(String state) {
        // When an invalid case state is passed through, the date's and accepted status should remain the same
        SubmitEvent submitEvent = caseDataBuilder.buildAsSubmitEvent(state);
        CasePreAcceptType casePreAcceptType = new CasePreAcceptType();
        casePreAcceptType.setCaseAccepted(YES);
        casePreAcceptType.setDateAccepted("2021-01-01");
        submitEvent.getCaseData().setPreAcceptCase(casePreAcceptType);
        PreAcceptDataModel updateModel = preAcceptDataModelBuilder.preAcceptDataModelBuilder("2022-02-02").build();
        PreAcceptDataTask task = new PreAcceptDataTask(updateModel);
        task.run(submitEvent);
        assertEquals("2021-01-01", submitEvent.getCaseData().getPreAcceptCase().getDateAccepted());
        assertEquals(YES, submitEvent.getCaseData().getPreAcceptCase().getCaseAccepted());
    }

    @ParameterizedTest
    @CsvSource({
        ACCEPTED_STATE + "," + YES,
        REJECTED_STATE + "," + YES,
        SUBMITTED_STATE + "," + YES
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
        SubmitEvent submitEvent = caseDataBuilder.buildAsSubmitEvent(ACCEPTED_STATE);
        CasePreAcceptType casePreAcceptType = new CasePreAcceptType();
        casePreAcceptType.setCaseAccepted(YES);
        casePreAcceptType.setDateAccepted("2021-01-01");
        submitEvent.getCaseData().setPreAcceptCase(casePreAcceptType);
        PreAcceptDataModel updateModel = preAcceptDataModelBuilder.preAcceptDataModelBuilder("2022-02-02").build();
        PreAcceptDataTask task = new PreAcceptDataTask(updateModel);
        task.run(submitEvent);

        assertEquals("2021-01-01", submitEvent.getCaseData().getPreAcceptCase().getDateAccepted());
        assertEquals(YES, submitEvent.getCaseData().getPreAcceptCase().getCaseAccepted());
    }
}
