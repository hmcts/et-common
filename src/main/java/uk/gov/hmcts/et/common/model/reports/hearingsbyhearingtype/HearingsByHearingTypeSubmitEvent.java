package uk.gov.hmcts.et.common.model.reports.hearingsbyhearingtype;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import uk.gov.hmcts.et.common.model.generic.GenericSubmitEvent;

@Data
@EqualsAndHashCode(callSuper = true)
@JsonIgnoreProperties(ignoreUnknown = true)
public class HearingsByHearingTypeSubmitEvent extends GenericSubmitEvent {
    @JsonProperty("case_data")
    private HearingsByHearingTypeCaseData caseData;
}