package uk.gov.hmcts.ecm.common.model.servicebus.tasks;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import uk.gov.hmcts.ecm.common.model.ccd.SubmitEvent;
import uk.gov.hmcts.ecm.common.model.servicebus.datamodel.DataModelParent;

@Data
@NoArgsConstructor
@SuperBuilder
@JsonIgnoreProperties(ignoreUnknown = true)
public abstract class DataTaskParent {

    DataModelParent dataModelParent;

    DataTaskParent(DataModelParent dataModelParent) {
        this.dataModelParent = dataModelParent;
    }

    public abstract void run(SubmitEvent submitEvent);

}
