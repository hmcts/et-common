package uk.gov.hmcts.ecm.common.model.generic;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import uk.gov.hmcts.ecm.common.model.ccd.SignificantItem;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@JsonIgnoreProperties(ignoreUnknown = true)
public class GenericCallbackResponse {

    private List<String> errors;
    private List<String> warnings;
    private String confirmation_header;
    private SignificantItem significant_item;
    private String state;
}

