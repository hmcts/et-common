package uk.gov.hmcts.ecm.common.model.schedule.types;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static com.google.common.base.Strings.isNullOrEmpty;

@JsonIgnoreProperties(ignoreUnknown = true)
@Data
public class ScheduleClaimantIndType {

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
        return String.join(" ", notNullOrEmptyAtt(new ArrayList<>(), Arrays.asList(claimantTitle,
                claimantTitleOther, claimantFirstNames, claimantLastName)));
    }

    private List<String> notNullOrEmptyAtt(List<String> fullClaimantName, List<String> attributes) {
        for (String aux : attributes) {
            if (!isNullOrEmpty(aux)) {
                fullClaimantName.add(aux);
            }
        }
        return fullClaimantName;
    }
}
