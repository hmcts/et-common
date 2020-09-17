package uk.gov.hmcts.ecm.common.model.multiples;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import uk.gov.hmcts.ecm.common.model.bulk.items.CaseIdTypeItem;
import uk.gov.hmcts.ecm.common.model.bulk.types.DynamicFixedListType;
import uk.gov.hmcts.ecm.common.model.multiples.items.SubMultipleTypeItem;
import uk.gov.hmcts.ecm.common.model.multiples.types.MoveCasesType;
import uk.gov.hmcts.ecm.common.model.multiples.types.SubMultipleActionType;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
@Data
public class MultipleData {

    @JsonProperty("caseIdCollection")
    private List<CaseIdTypeItem> caseIdCollection;

    @JsonProperty("multipleName")
    private String multipleName;
    @JsonProperty("multipleReference")
    private String multipleReference;

    @JsonProperty("caseImporterFile")
    private CaseImporterFile caseImporterFile;

    @JsonProperty("state")
    private String state;
    @JsonProperty("multipleSource")
    private String multipleSource;
    @JsonProperty("leadCase")
    private String leadCase;

    @JsonProperty("flag1")
    private DynamicFixedListType flag1;
    @JsonProperty("flag2")
    private DynamicFixedListType flag2;
    @JsonProperty("flag3")
    private DynamicFixedListType flag3;
    @JsonProperty("flag4")
    private DynamicFixedListType flag4;

    @JsonProperty("scheduleDocName")
    private String scheduleDocName;
    @JsonProperty("docMarkUp")
    private String docMarkUp;

    @JsonProperty("batchUpdateType")
    private String batchUpdateType;
    @JsonProperty("batchUpdateCase")
    private String batchUpdateCase;

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
    @JsonProperty("positionType")
    private String positionType;
    @JsonProperty("clerkResponsible")
    private String clerkResponsible;
    @JsonProperty("receiptDate")
    private String receiptDate;
    @JsonProperty("hearingStageEQP")
    private String hearingStage;

    @JsonProperty("batchMoveCases")
    private MoveCasesType moveCases;

    @JsonProperty("subMultipleCollection")
    private List<SubMultipleTypeItem> subMultipleCollection;
    @JsonProperty("subMultipleAction")
    private SubMultipleActionType subMultipleAction;
}


