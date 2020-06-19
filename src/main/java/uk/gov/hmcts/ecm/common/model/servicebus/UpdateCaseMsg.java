package uk.gov.hmcts.ecm.common.model.servicebus;

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
public class UpdateCaseMsg extends Msg {

    @JsonProperty("ethosCaseReference")
    private String ethosCaseReference;

    @Override
    public String toString() {
        return "UpdateCaseMsg{" +
            "ethosCaseReference='" + ethosCaseReference + '\'' +
            ", msgId='" + msgId + '\'' +
            ", jurisdiction='" + jurisdiction + '\'' +
            ", caseTypeId='" + caseTypeId + '\'' +
            ", multipleRef='" + multipleRef + '\'' +
            ", totalCases='" + totalCases + '\'' +
            ", username='" + username + '\'' +
            ", updateType='" + updateType + '\'' +
            ", updateData=" + updateData +
            '}';
    }
}
