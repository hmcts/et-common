package uk.gov.hmcts.ecm.common.model.helper;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import uk.gov.hmcts.et.common.model.ccd.types.SendNotificationTypeItem;

import java.util.List;

@Data
@AllArgsConstructor
@Builder
public class NotificationSchedulePayload {
    private String ethosCaseRef;
    private List<SendNotificationTypeItem> sendNotificationCollection;
}
