package uk.gov.hmcts.ecm.common.model.schedule;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import uk.gov.hmcts.et.common.model.ccd.types.SendNotificationTypeItem;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
@Data
public class NotificationSchedulePayloadES {
    @JsonProperty("ethosCaseReference")
    private String ethosCaseReference;

    @JsonProperty("sendNotificationCollection")
    private List<SendNotificationTypeItem> sendNotificationCollection;
}
