package uk.gov.hmcts.ecm.common.model.servicebus.datamodel;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import uk.gov.hmcts.et.common.model.bulk.types.DynamicValueType;

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@JsonIgnoreProperties(ignoreUnknown = true)
public class CloseDataModel extends DataModelParent {

    @JsonProperty("clerkResponsible")
    private DynamicValueType clerkResponsible;

    @JsonProperty("fileLocation")
    private DynamicValueType fileLocation;

    @JsonProperty("fileLocationEdinburgh")
    private DynamicValueType fileLocationEdinburgh;

    @JsonProperty("fileLocationDundee")
    private DynamicValueType fileLocationDundee;

    @JsonProperty("fileLocationAberdeen")
    private DynamicValueType fileLocationAberdeen;

    @JsonProperty("fileLocationGlasgow")
    private DynamicValueType fileLocationGlasgow;

    @JsonProperty("notes")
    private String notes;

    @JsonProperty("managingOffice")
    private String managingOffice;

}
