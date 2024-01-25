package uk.gov.hmcts.ecm.common.model.servicebus.tasks;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import uk.gov.hmcts.ecm.common.model.servicebus.datamodel.RejectDataModel;
import uk.gov.hmcts.et.common.model.ccd.SubmitEvent;
import uk.gov.hmcts.et.common.model.ccd.types.CasePreAcceptType;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static uk.gov.hmcts.ecm.common.model.helper.Constants.ACCEPTED_STATE;
import static uk.gov.hmcts.ecm.common.model.helper.Constants.CLOSED_STATE;
import static uk.gov.hmcts.ecm.common.model.helper.Constants.NO;
import static uk.gov.hmcts.ecm.common.model.helper.Constants.REJECTED_STATE;
import static uk.gov.hmcts.ecm.common.model.helper.Constants.SUBMITTED_STATE;
import static uk.gov.hmcts.ecm.common.model.helper.Constants.TRANSFERRED_STATE;

class RejectDataTaskTest {

    CaseDataBuilder caseDataBuilder;
    RejectDataModelBuilder rejectDataModelBuilder;

    @BeforeEach
    void setUp() {
        caseDataBuilder = new CaseDataBuilder();
        rejectDataModelBuilder = new RejectDataModelBuilder();
    }

    @ParameterizedTest
    @CsvSource({TRANSFERRED_STATE, CLOSED_STATE})
    void checkInvalidCaseStates(String state) {
        // When an invalid case state is passed through, the rejected data should remain the same
        CasePreAcceptType casePreAcceptType = new CasePreAcceptType();
        casePreAcceptType.setCaseAccepted(NO);
        casePreAcceptType.setDateRejected("2021-01-01");
        casePreAcceptType.setRejectReason(List.of("Defect", "Not on Prescribed Form"));
        SubmitEvent submitEvent = caseDataBuilder.buildAsSubmitEvent(state);
        submitEvent.getCaseData().setPreAcceptCase(casePreAcceptType);
        List<String> rejectReasons = List.of("Defect");
        RejectDataModel updateModel = rejectDataModelBuilder.rejectDataModelBuilder(
                "2022-02-02", rejectReasons).build();
        var task = new RejectDataTask(updateModel);
        task.run(submitEvent);

        assertEquals("2021-01-01", submitEvent.getCaseData().getPreAcceptCase().getDateRejected());
        assertEquals(2, submitEvent.getCaseData().getPreAcceptCase().getRejectReason().size());
        assertEquals(NO, submitEvent.getCaseData().getPreAcceptCase().getCaseAccepted());
    }

    @ParameterizedTest
    @CsvSource({
        ACCEPTED_STATE + "," + NO,
        REJECTED_STATE + "," + NO,
        SUBMITTED_STATE + "," + NO
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
        CasePreAcceptType casePreAcceptType = new CasePreAcceptType();
        casePreAcceptType.setCaseAccepted(NO);
        casePreAcceptType.setDateRejected("2021-01-01");
        casePreAcceptType.setRejectReason(List.of("Defect"));
        SubmitEvent submitEvent = caseDataBuilder.buildAsSubmitEvent(REJECTED_STATE);
        submitEvent.getCaseData().setPreAcceptCase(casePreAcceptType);
        List<String> rejectReasons = List.of("Defect", "Not on Prescribed Form");
        RejectDataModel updateModel = rejectDataModelBuilder.rejectDataModelBuilder(
                "2022-02-02", rejectReasons).build();
        RejectDataTask task = new RejectDataTask(updateModel);
        task.run(submitEvent);

        assertEquals(NO, submitEvent.getCaseData().getPreAcceptCase().getCaseAccepted());
        assertEquals("2021-01-01", submitEvent.getCaseData().getPreAcceptCase().getDateRejected());
        assertEquals(1, submitEvent.getCaseData().getPreAcceptCase().getRejectReason().size());
    }
}
