package uk.gov.hmcts.et.common.model.schedule.items;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import uk.gov.hmcts.et.common.model.schedule.types.ScheduleRespondentSumType;

@JsonIgnoreProperties(ignoreUnknown = true)
@Data
public class ScheduleRespondentSumTypeItem {

    @JsonProperty("value")
    private ScheduleRespondentSumType value;
}
