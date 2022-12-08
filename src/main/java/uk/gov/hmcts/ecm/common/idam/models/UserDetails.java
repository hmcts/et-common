package uk.gov.hmcts.ecm.common.idam.models;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
@Data
public class UserDetails {

    @JsonAlias({"sub", "email"})
    private String email;

    private String uid;

    private List<String> roles;

    private String name;

    @JsonProperty("given_name")
    @JsonAlias({"given_name", "forename"})
    private String firstName;

    @JsonProperty("family_name")
    @JsonAlias({"family_name", "surname"})
    private String lastName;
}
