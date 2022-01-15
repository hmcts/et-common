package uk.gov.hmcts.ecm.common.model.listing;

import org.junit.Test;
import uk.gov.hmcts.ecm.common.model.bulk.types.DynamicFixedListType;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class ListingDataTest {

    @Test
    public void testHasListingVenueReturnsTrue() {
        var listingData = new ListingData();
        listingData.setListingVenue(new DynamicFixedListType("value"));

        assertTrue(listingData.hasListingVenue());
    }

    @Test
    public void testHasListingVenueReturnsFalseWhenSelectedValueIsNull() {
        var listingData = new ListingData();
        listingData.setListingVenue(new DynamicFixedListType());

        assertFalse(listingData.hasListingVenue());
    }

    @Test
    public void testHasListingVenueReturnsFalseWhenPropertyIsNull() {
        var listingData = new ListingData();
        listingData.setListingVenue(null);

        assertFalse(listingData.hasListingVenue());
    }
}
