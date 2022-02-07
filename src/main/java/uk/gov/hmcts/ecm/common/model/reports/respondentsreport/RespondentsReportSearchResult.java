package uk.gov.hmcts.ecm.common.model.reports.respondentsreport;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RespondentsReportSearchResult {
    private Long total;
    private List<RespondentsReportSubmitEvent> cases;
}
