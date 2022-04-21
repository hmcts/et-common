package uk.gov.hmcts.ecm.common.model.servicebus;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import lombok.extern.slf4j.Slf4j;
import uk.gov.hmcts.ecm.common.model.servicebus.datamodel.DataModelFactory;
import uk.gov.hmcts.ecm.common.model.servicebus.tasks.DataTaskParent;
import uk.gov.hmcts.et.common.model.ccd.SubmitEvent;

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@JsonIgnoreProperties(ignoreUnknown = true)
@Slf4j
public class UpdateCaseMsg extends Msg {

    @JsonProperty("ethosCaseReference")
    private String ethosCaseReference;

    @Override
    public String toString() {
        return "UpdateCaseMsg{"
                + "ethosCaseReference='" + ethosCaseReference + '\''
                + ", msgId='" + msgId + '\''
                +  ", jurisdiction='" + jurisdiction + '\''
                +  ", caseTypeId='" + caseTypeId + '\''
                +  ", multipleRef='" + multipleRef + '\''
                +  ", totalCases='" + totalCases + '\''
                +  ", username='" + username + '\''
                +  ", confirmation='" + confirmation + '\''
                +  ", dataModel=" + dataModelParent + '\''
                +  ", multipleReferenceLinkMarkUp='" + multipleReferenceLinkMarkUp + '\'' + '}';
    }

    public void runTask(SubmitEvent submitEvent) {

        DataTaskParent dataTaskParent = DataModelFactory.getDataModelType(dataModelParent);

        dataTaskParent.run(submitEvent);

    }
}
