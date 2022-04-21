package uk.gov.hmcts.ecm.common.model.reports.respondentsreport;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import uk.gov.hmcts.et.common.model.ccd.items.RepresentedTypeRItem;
import uk.gov.hmcts.et.common.model.ccd.items.RespondentSumTypeItem;

import java.util.List;

@Data
public class RespondentsReportCaseData {

    @JsonProperty("ethosCaseReference")
    private String ethosCaseReference;

    @JsonProperty("respondentCollection")
    private List<RespondentSumTypeItem> respondentCollection;

    @JsonProperty("repCollection")
    private List<RepresentedTypeRItem> repCollection;

}

