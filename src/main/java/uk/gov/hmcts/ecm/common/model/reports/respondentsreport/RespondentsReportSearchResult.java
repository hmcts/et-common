package uk.gov.hmcts.ecm.common.model.reports.respondentsreport;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RespondentsReportSearchResult {
    private Long total;
    private List<RespondentsReportSubmitEvent> cases;
}
