package uk.gov.hmcts.ecm.common.model.servicebus.datamodel;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import lombok.experimental.SuperBuilder;

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@JsonIgnoreProperties(ignoreUnknown = true)
public class UpdateDataModel extends DataModelParent {

    @JsonProperty("claimantName")
    private String claimantName;

    @JsonProperty("claimantRep")
    private String claimantRep;

    @JsonProperty("respondentRep")
    private String respondentRep;

    @JsonProperty("managingOffice")
    private String managingOffice;

    @JsonProperty("fileLocation")
    private String fileLocation;

    @JsonProperty("fileLocationGlasgow")
    private String fileLocationGlasgow;

    @JsonProperty("fileLocationAberdeen")
    private String fileLocationAberdeen;

    @JsonProperty("fileLocationDundee")
    private String fileLocationDundee;

    @JsonProperty("fileLocationEdinburgh")
    private String fileLocationEdinburgh;

    @JsonProperty("newMultipleReference")
    private String newMultipleReference;

    @JsonProperty("clerkResponsible")
    private String clerkResponsible;

    @JsonProperty("positionType")
    private String positionType;

    @JsonProperty("receiptDate")
    private String receiptDate;

    @JsonProperty("hearingStage")
    private String hearingStage;

    @JsonProperty("jurisdictionCode")
    private String jurisdictionCode;

    @JsonProperty("outcomeUpdate")
    private String outcomeUpdate;

}
