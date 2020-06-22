package uk.gov.hmcts.ecm.common.model.servicebus;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import lombok.extern.slf4j.Slf4j;
import uk.gov.hmcts.ecm.common.model.ccd.SubmitEvent;
import uk.gov.hmcts.ecm.common.model.servicebus.datamodel.CreationDataModel;
import uk.gov.hmcts.ecm.common.model.servicebus.datamodel.DetachDataModel;
import uk.gov.hmcts.ecm.common.model.servicebus.datamodel.UpdateDataModel;
import uk.gov.hmcts.ecm.common.model.servicebus.tasks.*;

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
        return "UpdateCaseMsg{" +
            "ethosCaseReference='" + ethosCaseReference + '\'' +
            ", msgId='" + msgId + '\'' +
            ", jurisdiction='" + jurisdiction + '\'' +
            ", caseTypeId='" + caseTypeId + '\'' +
            ", multipleRef='" + multipleRef + '\'' +
            ", totalCases='" + totalCases + '\'' +
            ", username='" + username + '\'' +
            ", updateType='" + updateType + '\'' +
            ", dataModel=" + dataModelParent +
            '}';
    }

    public void runTask(SubmitEvent submitEvent) {

        DataTaskParent dataTaskParent;

        if (dataModelParent instanceof CreationDataModel) {
            dataTaskParent = new CreationDataTask(dataModelParent);
        } else if (dataModelParent instanceof UpdateDataModel) {
            dataTaskParent = new UpdateDataTask(dataModelParent);
        } else if (dataModelParent instanceof DetachDataModel) {
            dataTaskParent = new DetachDataTask(dataModelParent);
        } else {
            dataTaskParent = new PreAcceptDataTask(dataModelParent);
        }

        dataTaskParent.run(submitEvent);

    }
}