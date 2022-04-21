package uk.gov.hmcts.ecm.common.model.labels;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import uk.gov.hmcts.et.common.model.generic.GenericSubmitEvent;

@EqualsAndHashCode(callSuper = true)
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class LabelPayloadEvent extends GenericSubmitEvent {

    @JsonProperty("case_data")
    private LabelPayloadES labelPayloadES;
}
