package uk.gov.hmcts.ecm.common.model.reports.casesawaitingjudgment;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CasesAwaitingJudgmentSearchResult {
    private Long total;
    private List<CasesAwaitingJudgmentSubmitEvent> cases;
}
