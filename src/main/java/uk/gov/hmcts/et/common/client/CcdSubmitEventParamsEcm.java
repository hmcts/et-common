package uk.gov.hmcts.et.common.client;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class CcdSubmitEventParamsEcm {
    private String authToken;
    private String caseId;
    private String caseTypeId;
    private String jurisdiction;
    private uk.gov.hmcts.ecm.common.model.ccd.CaseData caseData;
    private uk.gov.hmcts.ecm.common.model.ccd.CCDRequest ccdRequest;
    private String eventSummary;
    private String eventDescription;
}
