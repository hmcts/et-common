package uk.gov.hmcts.ecm.common.model.servicebus.datamodel;

import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@JsonIgnoreProperties(ignoreUnknown = true)
public class LegalRepDataModel extends DataModelParent {

    @JsonProperty("legalRepIdsByCase")
    private Map<String, List<String>> legalRepIdsByCase;

    @JsonProperty("caseType")
    private String caseType;

    @JsonProperty("multipleReference")
    private String multipleReference;
}
