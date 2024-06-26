package uk.gov.hmcts.ecm.common.model.schedule;

import java.util.List;

public record NotificationScheduleSearchCasesResult(Long total, List<NotificationSchedulePayloadEvent> cases) {
}
