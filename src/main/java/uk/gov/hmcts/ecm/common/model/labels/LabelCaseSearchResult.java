package uk.gov.hmcts.ecm.common.model.labels;

import java.util.List;

public class LabelCaseSearchResult {

    private Long total;
    private List<LabelPayloadEvent> cases;

    public LabelCaseSearchResult() {
    }

    public LabelCaseSearchResult(Long total, List<LabelPayloadEvent> cases) {
        this.cases = cases;
        this.total = total;
    }

    public List<LabelPayloadEvent> getCases() {
        return cases;
    }

    public Long getTotal() {
        return total;
    }
}
