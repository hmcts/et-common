package uk.gov.hmcts.ecm.common.model.reports.eccreport;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EccReportSearchResult {
    private Long total;
    private List<EccReportSubmitEvent> cases;
}
