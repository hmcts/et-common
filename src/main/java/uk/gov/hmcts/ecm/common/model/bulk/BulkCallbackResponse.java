package uk.gov.hmcts.ecm.common.model.bulk;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;
import lombok.experimental.SuperBuilder;
import uk.gov.hmcts.ecm.common.model.generic.GenericCallbackResponse;

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@JsonIgnoreProperties(ignoreUnknown = true)
public class BulkCallbackResponse extends GenericCallbackResponse {

    private BulkData data;
}

