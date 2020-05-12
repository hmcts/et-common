package uk.gov.hmcts.ecm.common.model.reference.types;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@JsonIgnoreProperties(ignoreUnknown = true)
@Data
public class ClerkType {

    @JsonProperty("firstName")
    private String firstName;
    @JsonProperty("lastName")
    private String lastName;
    @JsonProperty("address")
    private String address;
    @JsonProperty("status")
    private String status;
    @JsonProperty("availableFrom")
    private String availableFrom;
    @JsonProperty("availableTo")
    private String availableTo;
    @JsonProperty("phoneLandLine")
    private String phoneLandLine;
    @JsonProperty("phoneMobile")
    private String phoneMobile;
    @JsonProperty("emailGov")
    private String emailGov;
    @JsonProperty("emailPersonal")
    private String emailPersonal;
}
