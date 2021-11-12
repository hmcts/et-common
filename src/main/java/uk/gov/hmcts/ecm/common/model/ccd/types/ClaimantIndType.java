package uk.gov.hmcts.ecm.common.model.ccd.types;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.common.base.Strings;
import lombok.Data;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static com.google.common.base.Strings.isNullOrEmpty;

@JsonIgnoreProperties(ignoreUnknown = true)
@Data
public class ClaimantIndType {

    @JsonProperty("claimant_title1")
    private String claimantTitle;
    @JsonProperty("claimant_title_other")
    private String claimantTitleOther;
    @JsonProperty("claimant_first_names")
    private String claimantFirstNames;
    @JsonProperty("claimant_last_name")
    private String claimantLastName;
    @JsonProperty("claimant_date_of_birth")
    private String claimantDateOfBirth;
    @JsonProperty("claimant_gender")
    private String claimantGender;

    public String claimantFullNames() {
        var title = "";
        if (!Strings.isNullOrEmpty(claimantTitle)) {
            if (claimantTitle.trim().equals("Other")) {
                title = claimantTitleOther;
            } else {
                title = claimantTitle;
            }
        }
        var fullNameList = List.of(title, claimantFirstNames, claimantLastName);
        return String.join(" ", notNullOrEmptyAtt(new ArrayList<>(), fullNameList));
    }

    public String claimantFullName() {
        var title = "";
        if (!Strings.isNullOrEmpty(claimantTitle)) {
            if (claimantTitle.trim().equals("Other")) {
                title = claimantTitleOther;
            } else {
                title = claimantTitle;
            }
        }
        var fullNameList = List.of(title, getInitials(), claimantLastName);
        return String.join(" ", notNullOrEmptyAtt(new ArrayList<>(), fullNameList));
    }

    private String getInitials() {
        if (!isNullOrEmpty(claimantFirstNames)) {
            return Arrays.stream(claimantFirstNames.split(" ")).map(str -> str.substring(0, 1)).collect(Collectors.joining(" "));
        }
        return "";
    }

    private List<String> notNullOrEmptyAtt(List<String> fullClaimantName, List<String> attributes) {
        for (String aux : attributes) {
            if (!isNullOrEmpty(aux)) fullClaimantName.add(aux);
        }
        return fullClaimantName;
    }
}
