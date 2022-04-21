package uk.gov.hmcts.ecm.common.model.reports.eccreport;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import uk.gov.hmcts.et.common.model.ccd.items.EccCounterClaimTypeItem;
import uk.gov.hmcts.et.common.model.ccd.items.RespondentSumTypeItem;

import java.util.List;

@Data
public class EccReportCaseData {

    @JsonProperty("ethosCaseReference")
    private String ethosCaseReference;

    @JsonProperty("respondentCollection")
    private List<RespondentSumTypeItem> respondentCollection;

    @JsonProperty("eccCases")
    private List<EccCounterClaimTypeItem> eccCases;

    @JsonProperty("receiptDate")
    private String receiptDate;

    @JsonProperty("managingOffice")
    private String managingOffice;

}

