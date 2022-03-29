package uk.gov.hmcts.ecm.common.model.servicebus.tasks;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static uk.gov.hmcts.ecm.common.model.helper.Constants.ACCEPTED_STATE;
import static uk.gov.hmcts.ecm.common.model.helper.Constants.NO;
import static uk.gov.hmcts.ecm.common.model.helper.Constants.REJECTED_STATE;
import static uk.gov.hmcts.ecm.common.model.helper.Constants.SUBMITTED_STATE;

class RejectDataTaskTest {

    CaseDataBuilder caseDataBuilder;
    RejectDataModelBuilder rejectDataModelBuilder;

    @BeforeEach
    void setUp() {
        caseDataBuilder = new CaseDataBuilder();
        rejectDataModelBuilder = new RejectDataModelBuilder();
    }

    @ParameterizedTest
    @CsvSource({
            ACCEPTED_STATE + "," + NO,
            REJECTED_STATE + "," + NO,
            SUBMITTED_STATE + "," + NO
    })
    void checkCaseStates(String state, String caseAcceptedStatus) {
        var rejectReasons = List.of("Defect", "Not on Prescribed Form");
        var updateModel = rejectDataModelBuilder.rejectDataModelBuilder(
                "2022-02-02", rejectReasons).build();
        var submitEvent = caseDataBuilder.buildAsSubmitEvent(state);

        var task = new RejectDataTask(updateModel);
        task.run(submitEvent);

        assertEquals(caseAcceptedStatus, submitEvent.getCaseData().getPreAcceptCase().getCaseAccepted());
        assertEquals("2022-02-02", submitEvent.getCaseData().getPreAcceptCase().getDateRejected());
        assertEquals(2, submitEvent.getCaseData().getPreAcceptCase().getRejectReason().size());
    }

}
