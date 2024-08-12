package uk.gov.hmcts.ecm.common.client;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import uk.gov.hmcts.et.common.model.bulk.BulkData;
import uk.gov.hmcts.et.common.model.ccd.CCDRequest;
import uk.gov.hmcts.et.common.model.ccd.CaseData;
import uk.gov.hmcts.et.common.model.ccd.CaseDataContent;
import uk.gov.hmcts.et.common.model.ccd.Event;
import uk.gov.hmcts.et.common.model.generic.GenericRequest;
import uk.gov.hmcts.et.common.model.multiples.MultipleData;

import java.util.Map;

public class CaseDataBuilder {

    private final ObjectMapper objectMapper;
    private static final Boolean IGNORE_WARNING = Boolean.FALSE;

    public CaseDataBuilder(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    public CaseDataContent buildCaseDataContent(CaseData caseData, CCDRequest req, String eventSummary) {
        return buildCaseDataContent(caseData, req, eventSummary, null);
    }

    public CaseDataContent buildCaseDataContent(CaseData caseData, CCDRequest req, String eventSummary,
                                                String eventDescription) {
        return getCaseDataContent(req, objectMapper.convertValue(caseData, new TypeReference<>() {
        }), eventSummary, eventDescription);
    }

    public <T> CaseDataContent buildGenericCaseDataContent(T caseData, CCDRequest req, String eventSummary,
                                                           String eventDescription) {
        return getCaseDataContent(req, objectMapper.convertValue(caseData, new TypeReference<>() {}),
                eventSummary, eventDescription);
    }

    public CaseDataContent buildBulkDataContent(BulkData bulkData, CCDRequest req, String eventSummary) {
        return getCaseDataContent(req, objectMapper.convertValue(bulkData, new TypeReference<>() {
        }), eventSummary, null);
    }

    public CaseDataContent buildMultipleDataContent(MultipleData mdata, GenericRequest req, String eventSummary) {
        return getCaseDataContent(req, objectMapper.convertValue(mdata, new TypeReference<>() {
        }), eventSummary, null);
    }

    public CaseDataContent buildChangeOrganisationDataContent(Map<String, Object> changeOrganisationRequest,
                                                              CCDRequest req, String updateChangeOrgSummary) {
        return getCaseDataContent(req, objectMapper.convertValue(changeOrganisationRequest, new TypeReference<>() {
        }), updateChangeOrgSummary, null);
    }

    private CaseDataContent getCaseDataContent(GenericRequest req, Map<String, JsonNode> data, String eventSummary,
                                               String eventDescription) {
        var event = Event.builder()
                .eventId(req.getEventId())
                .summary(eventSummary)
                .description(eventDescription)
                .build();
        return CaseDataContent.builder()
                .event(event)
                .data(data)
                .token(req.getToken())
                .ignoreWarning(IGNORE_WARNING)
                .build();
    }
}
