package uk.gov.hmcts.ecm.common.model.schedule;

import lombok.Getter;

import java.util.List;

@Getter
public class NotificationScheduleSearchCasesResult {
    private Long total;
    private List<NotificationSchedulePayloadEvent> cases;

    public NotificationScheduleSearchCasesResult() {
    }

    public NotificationScheduleSearchCasesResult(Long total, List<NotificationSchedulePayloadEvent> cases) {
        this.cases = cases;
        this.total = total;
    }

}
