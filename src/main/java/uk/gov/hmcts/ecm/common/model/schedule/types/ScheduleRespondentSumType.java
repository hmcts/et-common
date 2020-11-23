package uk.gov.hmcts.ecm.common.model.schedule.types;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import uk.gov.hmcts.ecm.common.model.schedule.ScheduleAddress;

@JsonIgnoreProperties(ignoreUnknown = true)
@Data
public class ScheduleRespondentSumType {

    @JsonProperty("respondent_name")
    private String respondentName;
    @JsonProperty("respondent_address")
    private ScheduleAddress respondentAddress;
}
