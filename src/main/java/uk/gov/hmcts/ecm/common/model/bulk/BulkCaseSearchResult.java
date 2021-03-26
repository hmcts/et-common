package uk.gov.hmcts.ecm.common.model.bulk;

import java.util.List;

public class BulkCaseSearchResult {

    private transient Long total;
    private transient List<SubmitBulkEvent> cases;

    public BulkCaseSearchResult() {
    }

    public BulkCaseSearchResult(Long total, List<SubmitBulkEvent> cases) {
        this.cases = cases;
        this.total = total;
    }

    public List<SubmitBulkEvent> getCases() {
        return cases;
    }

    public Long getTotal() {
        return total;
    }
}
