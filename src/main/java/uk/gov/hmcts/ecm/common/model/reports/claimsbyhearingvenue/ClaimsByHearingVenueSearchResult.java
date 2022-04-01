package uk.gov.hmcts.ecm.common.model.reports.claimsbyhearingvenue;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ClaimsByHearingVenueSearchResult {
    private Long total;
    private List<ClaimsByHearingVenueSubmitEvent> cases;
}
