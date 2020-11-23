package uk.gov.hmcts.ecm.common.model.schedule;

import java.util.List;

public class ScheduleCaseSearchResult {

    private Long total;
    private List<SchedulePayloadES> cases;

    public ScheduleCaseSearchResult() {
    }

    public ScheduleCaseSearchResult(Long total, List<SchedulePayloadES> cases) {
        this.cases = cases;
        this.total = total;
    }

    public List<SchedulePayloadES> getCases() {
        return cases;
    }

    public Long getTotal() {
        return total;
    }
}
