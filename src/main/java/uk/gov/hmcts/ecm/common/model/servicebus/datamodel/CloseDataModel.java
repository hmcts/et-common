package uk.gov.hmcts.ecm.common.model.servicebus.datamodel;

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
public class CloseDataModel extends DataModelParent {

    @JsonProperty("clerkResponsible")
    private String clerkResponsible;

    @JsonProperty("fileLocation")
    private String fileLocation;

    @JsonProperty("notes")
    private String notes;

    @JsonProperty("managingOffice")
    private String managingOffice;

}
