package uk.gov.hmcts.ecm.common.model.reports.sessiondays;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SessionDaysSearchResult {
    private Long total;
    private List<SessionDaysSubmitEvent> cases;
}
