package uk.gov.hmcts.ecm.common.model.servicebus.datamodel;

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
public class TransferToEcmDataModel extends DataModelParent {

    @JsonProperty("officeCT")
    private String officeCT;

    @JsonProperty("positionTypeCT")
    private String positionTypeCT;

    @JsonProperty("ccdGatewayBaseUrl")
    private String ccdGatewayBaseUrl;

    @JsonProperty("reasonForCT")
    private String reasonForCT;

    @JsonProperty("sourceEthosCaseReference")
    private String sourceEthosCaseReference;

}
