package uk.gov.hmcts.ecm.common.model.listing.items;

import java.time.LocalDate;
import java.util.Comparator;

public class BFDateTypeItemComparator implements Comparator<BFDateTypeItem> {
    @Override
    public int compare(BFDateTypeItem firstItem, BFDateTypeItem secondItem) {
        return LocalDate.parse(firstItem.getValue().getBroughtForwardDate())
            .compareTo(LocalDate.parse(secondItem.getValue().getBroughtForwardDate()));
    }
}
