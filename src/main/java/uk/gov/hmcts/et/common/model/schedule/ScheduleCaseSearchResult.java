package uk.gov.hmcts.et.common.model.schedule;

import java.util.List;

public class ScheduleCaseSearchResult {

    private Long total;
    private List<SchedulePayloadEvent> cases;

    public ScheduleCaseSearchResult() {
    }

    public ScheduleCaseSearchResult(Long total, List<SchedulePayloadEvent> cases) {
        this.cases = cases;
        this.total = total;
    }

    public List<SchedulePayloadEvent> getCases() {
        return cases;
    }

    public Long getTotal() {
        return total;
    }
}
