package uk.gov.hmcts.ecm.common.model.reference;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import uk.gov.hmcts.et.common.model.generic.GenericCallbackResponse;

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@JsonIgnoreProperties(ignoreUnknown = true)
public class ReferenceCallbackResponse extends GenericCallbackResponse {

    private ReferenceData data;
}
