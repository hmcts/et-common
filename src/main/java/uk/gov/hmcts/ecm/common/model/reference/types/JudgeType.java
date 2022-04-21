package uk.gov.hmcts.ecm.common.model.reference.types;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import uk.gov.hmcts.et.common.model.ccd.Address;

@JsonIgnoreProperties(ignoreUnknown = true)
@Data
public class JudgeType {

    @JsonProperty("titleName")
    private String titleName;
    @JsonProperty("firstName")
    private String firstName;
    @JsonProperty("lastName")
    private String lastName;
    @JsonProperty("gender")
    private String gender;
    @JsonProperty("judgeDisplayName")
    private String judgeDisplayName;
    @JsonProperty("judgeAddress")
    private Address judgeAddress;
    @JsonProperty("status")
    private String status;
    @JsonProperty("itco")
    private String itco;
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
