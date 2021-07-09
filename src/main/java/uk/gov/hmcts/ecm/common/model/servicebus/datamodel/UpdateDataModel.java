package uk.gov.hmcts.ecm.common.model.servicebus.datamodel;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import lombok.experimental.SuperBuilder;
import uk.gov.hmcts.ecm.common.model.ccd.types.JudgementType;
import uk.gov.hmcts.ecm.common.model.ccd.types.JurCodesType;
import uk.gov.hmcts.ecm.common.model.ccd.types.RepresentedTypeC;
import uk.gov.hmcts.ecm.common.model.ccd.types.RepresentedTypeR;
import uk.gov.hmcts.ecm.common.model.ccd.types.RespondentSumType;

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@JsonIgnoreProperties(ignoreUnknown = true)
public class UpdateDataModel extends DataModelParent {

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

    @JsonProperty("clerkResponsible")
    private String clerkResponsible;

    @JsonProperty("positionType")
    private String positionType;

    @JsonProperty("receiptDate")
    private String receiptDate;

    @JsonProperty("hearingStage")
    private String hearingStage;

    @JsonProperty("representativeClaimantType")
    private RepresentedTypeC representativeClaimantType;

    @JsonProperty("jurCodesType")
    private JurCodesType jurCodesType;

    @JsonProperty("outcomeUpdate")
    private RespondentSumType respondentSumType;

    @JsonProperty("judgementType")
    private JudgementType judgementType;

    @JsonProperty("representedType")
    private RepresentedTypeR representedType;

}
