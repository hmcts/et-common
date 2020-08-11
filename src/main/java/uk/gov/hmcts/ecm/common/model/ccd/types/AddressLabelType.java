package uk.gov.hmcts.ecm.common.model.ccd.types;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import uk.gov.hmcts.ecm.common.model.ccd.Address;

@JsonIgnoreProperties(ignoreUnknown = true)
@Data
public class AddressLabelType {

    @JsonProperty("printLabel")
    private String printLabel;
    @JsonProperty("fullName")
    private String fullName;
    @JsonProperty("fullAddress")
    private String fullAddress;
    @JsonProperty("labelEntityName01")
    private String labelEntityName01;
    @JsonProperty("labelEntityName02")
    private String labelEntityName02;
    @JsonProperty("labelEntityAddress")
    private Address labelEntityAddress;
    @JsonProperty("labelEntityTelephone")
    private String labelEntityTelephone;
    @JsonProperty("labelEntityFax")
    private String labelEntityFax;
    @JsonProperty("labelEntityReference")
    private String labelEntityReference;
    @JsonProperty("labelCaseReference")
    private String labelCaseReference;
}
