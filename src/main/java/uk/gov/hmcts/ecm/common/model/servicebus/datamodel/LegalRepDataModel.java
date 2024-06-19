package uk.gov.hmcts.ecm.common.model.servicebus.datamodel;

import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
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

    private Map<String, List<String>> legalRepIdsByCase;

    private String caseType;

    private String multipleName;
}
