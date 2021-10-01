package uk.gov.hmcts.ecm.common.model.ccd.types;

import org.junit.Test;
import uk.gov.hmcts.ecm.common.model.bulk.types.DynamicFixedListType;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class DateListedTypeTest {

    @Test
    public void testHasHearingVenue() {
        var dateListedType = new DateListedType();
        assertFalse(dateListedType.hasHearingVenue());

        var dynamicFixedListType = new DynamicFixedListType();
        dateListedType.setHearingVenueDay(dynamicFixedListType);
        assertFalse(dateListedType.hasHearingVenue());

        dynamicFixedListType = new DynamicFixedListType("Venue");
        dateListedType.setHearingVenueDay(dynamicFixedListType);
        assertTrue(dateListedType.hasHearingVenue());
    }

    @Test
    public void testHasHearingRoom() {
        var dateListedType = new DateListedType();
        assertFalse(dateListedType.hasHearingRoom());

        var dynamicFixedListType = new DynamicFixedListType();
        dateListedType.setHearingRoom(dynamicFixedListType);
        assertFalse(dateListedType.hasHearingRoom());

        dynamicFixedListType = new DynamicFixedListType("Room");
        dateListedType.setHearingRoom(dynamicFixedListType);
        assertTrue(dateListedType.hasHearingRoom());
    }

    @Test
    public void testHasHearingClerk() {
        var dateListedType = new DateListedType();
        assertFalse(dateListedType.hasHearingClerk());

        var dynamicFixedListType = new DynamicFixedListType();
        dateListedType.setHearingClerk(dynamicFixedListType);
        assertFalse(dateListedType.hasHearingClerk());

        dynamicFixedListType = new DynamicFixedListType("Clerk");
        dateListedType.setHearingClerk(dynamicFixedListType);
        assertTrue(dateListedType.hasHearingClerk());
    }
}
