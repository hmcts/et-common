package uk.gov.hmcts.ecm.common.model.servicebus.tasks;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import uk.gov.hmcts.ecm.common.model.servicebus.datamodel.DataModelParent;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static uk.gov.hmcts.ecm.common.model.helper.Constants.ACCEPTED_STATE;
import static uk.gov.hmcts.ecm.common.model.helper.Constants.MULTIPLE_CASE_TYPE;
import static uk.gov.hmcts.ecm.common.model.helper.Constants.NO;
import static uk.gov.hmcts.ecm.common.model.helper.Constants.SINGLE_CASE_TYPE;
import static uk.gov.hmcts.ecm.common.model.helper.Constants.YES;

class DataDetachTaskTest {

    public static final String MULTIPLE_REF = "6001";
    public static final String EMPTY_STRING = " ";
    CaseDataBuilder caseDataBuilder;

    @BeforeEach
    void setUp() {
        caseDataBuilder = new CaseDataBuilder();
    }

    @Test
    void detachCaseFieldsFromMultiple() {
        var submitEvent = caseDataBuilder.buildAsSubmitEvent(ACCEPTED_STATE);
        submitEvent.getCaseData().setMultipleReference(MULTIPLE_REF);
        submitEvent.getCaseData().setLeadClaimant(MULTIPLE_REF + "/2024");
        submitEvent.getCaseData().setEcmCaseType(MULTIPLE_CASE_TYPE);
        submitEvent.getCaseData().setMultipleFlag(YES);
        submitEvent.getCaseData().setSubMultipleName("SomeName");
        submitEvent.getCaseData().setMultipleReferenceLinkMarkUp("SomeLink");

        var task = new DetachDataTask(new DataModelParent());
        task.run(submitEvent);

        assertEquals(EMPTY_STRING, submitEvent.getCaseData().getMultipleReference());
        assertEquals(EMPTY_STRING, submitEvent.getCaseData().getLeadClaimant());
        assertEquals(EMPTY_STRING, submitEvent.getCaseData().getSubMultipleName());
        assertEquals(EMPTY_STRING, submitEvent.getCaseData().getMultipleReferenceLinkMarkUp());

        assertEquals(SINGLE_CASE_TYPE, submitEvent.getCaseData().getEcmCaseType());
        assertEquals(NO, submitEvent.getCaseData().getMultipleFlag());
    }

    @Test
    void detachCaseFieldsFromMultiple_NoSubMultiple() {
        var submitEvent = caseDataBuilder.buildAsSubmitEvent(ACCEPTED_STATE);
        submitEvent.getCaseData().setMultipleReference(MULTIPLE_REF);
        submitEvent.getCaseData().setLeadClaimant(MULTIPLE_REF + "/2024");
        submitEvent.getCaseData().setEcmCaseType(MULTIPLE_CASE_TYPE);
        submitEvent.getCaseData().setMultipleFlag(YES);
        submitEvent.getCaseData().setMultipleReferenceLinkMarkUp("SomeLink");

        var task = new DetachDataTask(new DataModelParent());
        task.run(submitEvent);

        assertNull(submitEvent.getCaseData().getSubMultipleName());

        assertEquals(EMPTY_STRING, submitEvent.getCaseData().getMultipleReference());
        assertEquals(EMPTY_STRING, submitEvent.getCaseData().getLeadClaimant());
        assertEquals(EMPTY_STRING, submitEvent.getCaseData().getMultipleReferenceLinkMarkUp());

        assertEquals(SINGLE_CASE_TYPE, submitEvent.getCaseData().getEcmCaseType());
        assertEquals(NO, submitEvent.getCaseData().getMultipleFlag());
    }

}
