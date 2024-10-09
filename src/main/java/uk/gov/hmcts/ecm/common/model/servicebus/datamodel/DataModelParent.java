package uk.gov.hmcts.ecm.common.model.servicebus.datamodel;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@NoArgsConstructor
@SuperBuilder
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonTypeInfo(
        use = JsonTypeInfo.Id.NAME,
        property = "type")
@JsonSubTypes({
    @JsonSubTypes.Type(value = CreationDataModel.class, name = "creationDataModel"),
    @JsonSubTypes.Type(value = DetachDataModel.class, name = "detachDataModel"),
    @JsonSubTypes.Type(value = UpdateDataModel.class, name = "updateDataModel"),
    @JsonSubTypes.Type(value = PreAcceptDataModel.class, name = "preAcceptDataModel"),
    @JsonSubTypes.Type(value = RejectDataModel.class, name = "rejectDataModel"),
    @JsonSubTypes.Type(value = CloseDataModel.class, name = "closeDataModel"),
    @JsonSubTypes.Type(value = ResetStateDataModel.class, name = "resetStateDataModel"),
    @JsonSubTypes.Type(value = CreationSingleDataModel.class, name = "creationSingleDataModel"),
    @JsonSubTypes.Type(value = TransferToEcmDataModel.class, name = "transferToEcmDataModel"),
    @JsonSubTypes.Type(value = SendNotificationDataModel.class, name = "SendNotificationDataModel"),
    @JsonSubTypes.Type(value = LegalRepDataModel.class, name = "LegalRepDataModel"),
    @JsonSubTypes.Type(value = DigitalCaseFileDataModel.class, name = "DigitalCaseFileDataModel")

})
public class DataModelParent {

}
