package uk.gov.hmcts.ecm.common.client;

import lombok.Builder;
import lombok.Getter;
import uk.gov.hmcts.et.common.model.ccd.CCDRequest;
import uk.gov.hmcts.et.common.model.ccd.CaseData;

@Builder
@Getter
public class CcdSubmitEventParams {
    private String authToken;
    private String caseId;
    private String caseTypeId;
    private String jurisdiction;
    private CaseData caseData;
    private CCDRequest ccdRequest;
    private String eventSummary;
    private String eventDescription;
}
