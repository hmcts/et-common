package uk.gov.hmcts.ecm.common.model.ccd.types;

import org.junit.Test;
import uk.gov.hmcts.ecm.common.model.bulk.types.DynamicFixedListType;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class HearingTypeTest {
    @Test
    public void testHasHearingJudge() {
        var hearingType = new HearingType();
        assertFalse(hearingType.hasHearingJudge());

        var dynamicFixedListType = new DynamicFixedListType();
        hearingType.setJudge(dynamicFixedListType);
        assertFalse(hearingType.hasHearingJudge());

        dynamicFixedListType = new DynamicFixedListType("Judge");
        hearingType.setJudge(dynamicFixedListType);
        assertTrue(hearingType.hasHearingJudge());
    }

    @Test
    public void testHasHearingEmployerMember() {
        var hearingType = new HearingType();
        assertFalse(hearingType.hasHearingEmployerMember());

        var dynamicFixedListType = new DynamicFixedListType();
        hearingType.setHearingERMember(dynamicFixedListType);
        assertFalse(hearingType.hasHearingEmployerMember());

        dynamicFixedListType = new DynamicFixedListType("Employer Member");
        hearingType.setHearingERMember(dynamicFixedListType);
        assertTrue(hearingType.hasHearingEmployerMember());
    }

    @Test
    public void testHasHearingEmployeeMember() {
        var hearingType = new HearingType();
        assertFalse(hearingType.hasHearingEmployeeMember());

        var dynamicFixedListType = new DynamicFixedListType();
        hearingType.setHearingEEMember(dynamicFixedListType);
        assertFalse(hearingType.hasHearingEmployeeMember());

        dynamicFixedListType = new DynamicFixedListType("Employee Member");
        hearingType.setHearingEEMember(dynamicFixedListType);
        assertTrue(hearingType.hasHearingEmployeeMember());
    }
}