package uk.gov.hmcts.et.common.model.servicebus.tasks;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import uk.gov.hmcts.et.common.model.ccd.types.CasePreAcceptType;
import uk.gov.hmcts.et.common.model.helper.Constants;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class RejectDataTaskTest {

    CaseDataBuilder caseDataBuilder;
    RejectDataModelBuilder rejectDataModelBuilder;

    @BeforeEach
    void setUp() {
        caseDataBuilder = new CaseDataBuilder();
        rejectDataModelBuilder = new RejectDataModelBuilder();
    }

    @ParameterizedTest
    @CsvSource({Constants.TRANSFERRED_STATE, Constants.CLOSED_STATE})
    void checkInvalidCaseStates(String state) {
        // When an invalid case state is passed through, the rejected data should remain the same
        var rejectReasons = List.of("Defect");
        var updateModel = rejectDataModelBuilder.rejectDataModelBuilder(
                "2022-02-02", rejectReasons).build();
        var submitEvent = caseDataBuilder.buildAsSubmitEvent(state);
        var casePreAcceptType = new CasePreAcceptType();
        casePreAcceptType.setCaseAccepted(Constants.NO);
        casePreAcceptType.setDateRejected("2021-01-01");
        casePreAcceptType.setRejectReason(List.of("Defect", "Not on Prescribed Form"));
        submitEvent.getCaseData().setPreAcceptCase(casePreAcceptType);

        var task = new RejectDataTask(updateModel);
        task.run(submitEvent);

        assertEquals("2021-01-01", submitEvent.getCaseData().getPreAcceptCase().getDateRejected());
        assertEquals(2, submitEvent.getCaseData().getPreAcceptCase().getRejectReason().size());
        assertEquals(Constants.NO, submitEvent.getCaseData().getPreAcceptCase().getCaseAccepted());
    }

    @ParameterizedTest
    @CsvSource({
            Constants.ACCEPTED_STATE + "," + Constants.NO,
            Constants.REJECTED_STATE + "," + Constants.NO,
            Constants.SUBMITTED_STATE + "," + Constants.NO
    })
    void checkValidCaseStates(String state, String caseAcceptedStatus) {
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

    @Test
    void checkIfCaseAlreadyRejected() {
        // If a case has already been rejected, it should not be overwritten with a new data
        var rejectReasons = List.of("Defect", "Not on Prescribed Form");
        var updateModel = rejectDataModelBuilder.rejectDataModelBuilder(
                "2022-02-02", rejectReasons).build();
        var submitEvent = caseDataBuilder.buildAsSubmitEvent(Constants.REJECTED_STATE);
        var casePreAcceptType = new CasePreAcceptType();
        casePreAcceptType.setCaseAccepted(Constants.NO);
        casePreAcceptType.setDateRejected("2021-01-01");
        casePreAcceptType.setRejectReason(List.of("Defect"));
        submitEvent.getCaseData().setPreAcceptCase(casePreAcceptType);

        var task = new RejectDataTask(updateModel);
        task.run(submitEvent);

        assertEquals(Constants.NO, submitEvent.getCaseData().getPreAcceptCase().getCaseAccepted());
        assertEquals("2021-01-01", submitEvent.getCaseData().getPreAcceptCase().getDateRejected());
        assertEquals(1, submitEvent.getCaseData().getPreAcceptCase().getRejectReason().size());
    }
}
