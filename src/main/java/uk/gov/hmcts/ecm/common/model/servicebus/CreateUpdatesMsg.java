package uk.gov.hmcts.ecm.common.model.servicebus;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@JsonIgnoreProperties(ignoreUnknown = true)
public class CreateUpdatesMsg extends Msg {

    @JsonProperty("ethosCaseRefCollection")
    private List<String> ethosCaseRefCollection;

    @Override
    public String toString() {
        return "CreateUpdatesMsg{"
                + "ethosCaseRefCollection="
                + ethosCaseRefCollection
                + ", msgId='"
                + msgId
                + '\''
                + ", jurisdiction='"
                + jurisdiction
                + '\''
                + ", caseTypeId='"
                + caseTypeId
                + '\''
                + ", multipleRef='"
                + multipleRef
                + '\''
                + ", totalCases='"
                + totalCases
                + '\''
                + ", username='"
                + username
                + '\''
                + ", confirmation='"
                + confirmation
                + '\''
                + ", dataModel="
                + dataModelParent
                + ", multipleReferenceLinkMarkUp='"
                + multipleReferenceLinkMarkUp
                + '\''
                + '}';
    }
}
