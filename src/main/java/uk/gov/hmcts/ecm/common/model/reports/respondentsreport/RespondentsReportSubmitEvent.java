package uk.gov.hmcts.ecm.common.model.reports.respondentsreport;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import uk.gov.hmcts.et.common.model.generic.GenericSubmitEvent;

@Data
@EqualsAndHashCode(callSuper = true)
@JsonIgnoreProperties(ignoreUnknown = true)
public class RespondentsReportSubmitEvent extends GenericSubmitEvent {
    @JsonProperty("case_data")
    private RespondentsReportCaseData caseData;
}
