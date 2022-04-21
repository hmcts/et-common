package uk.gov.hmcts.ecm.common.model.schedule;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import uk.gov.hmcts.et.common.model.generic.GenericSubmitEvent;

@EqualsAndHashCode(callSuper = true)
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class SchedulePayloadEvent extends GenericSubmitEvent {

    @JsonProperty("case_data")
    private SchedulePayloadES schedulePayloadES;
}
