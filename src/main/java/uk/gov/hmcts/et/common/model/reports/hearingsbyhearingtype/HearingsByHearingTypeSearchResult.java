package uk.gov.hmcts.et.common.model.reports.hearingsbyhearingtype;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class HearingsByHearingTypeSearchResult {
    private Long total;
    private List<HearingsByHearingTypeSubmitEvent> cases;
}
