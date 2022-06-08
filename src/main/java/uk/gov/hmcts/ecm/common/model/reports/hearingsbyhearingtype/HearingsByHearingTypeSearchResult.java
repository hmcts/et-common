package uk.gov.hmcts.ecm.common.model.reports.hearingsbyhearingtype;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class HearingsByHearingTypeSearchResult {
    private Long total;
    private List<HearingsByHearingTypeSubmitEvent> cases;
}
