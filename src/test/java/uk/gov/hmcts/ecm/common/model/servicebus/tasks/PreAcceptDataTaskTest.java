package uk.gov.hmcts.ecm.common.model.servicebus.tasks;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static uk.gov.hmcts.ecm.common.model.helper.Constants.ACCEPTED_STATE;
import static uk.gov.hmcts.ecm.common.model.helper.Constants.REJECTED_STATE;
import static uk.gov.hmcts.ecm.common.model.helper.Constants.SUBMITTED_STATE;
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
    @CsvSource({
            ACCEPTED_STATE + "," + YES,
            REJECTED_STATE + "," + YES,
            SUBMITTED_STATE + "," + YES
    })
     void checkCaseStates(String state, String caseAccepted) {
        var updateModel = preAcceptDataModelBuilder.preAcceptDataModelBuilder("2022-02-02").build();
        var submitEvent = caseDataBuilder.buildAsSubmitEvent(state);

        var task = new PreAcceptDataTask(updateModel);
        task.run(submitEvent);

        assertEquals(caseAccepted, submitEvent.getCaseData().getPreAcceptCase().getCaseAccepted());
        assertEquals("2022-02-02", submitEvent.getCaseData().getPreAcceptCase().getDateAccepted());
    }

}
