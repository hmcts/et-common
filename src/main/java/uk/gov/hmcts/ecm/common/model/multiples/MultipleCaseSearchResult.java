package uk.gov.hmcts.ecm.common.model.multiples;

import java.util.List;

public class MultipleCaseSearchResult {

    private Long total;
    private List<SubmitMultipleEvent> cases;

    public MultipleCaseSearchResult() {
    }

    public MultipleCaseSearchResult(Long total, List<SubmitMultipleEvent> cases) {
        this.cases = cases;
        this.total = total;
    }

    public List<SubmitMultipleEvent> getCases() {
        return cases;
    }

    public Long getTotal() {
        return total;
    }
}
