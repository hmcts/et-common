package uk.gov.hmcts.ecm.common.client;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import uk.gov.hmcts.ecm.common.model.bulk.BulkData;
import uk.gov.hmcts.ecm.common.model.ccd.CCDRequest;
import uk.gov.hmcts.ecm.common.model.ccd.CaseData;
import uk.gov.hmcts.ecm.common.model.ccd.CaseDataContent;
import uk.gov.hmcts.ecm.common.model.ccd.Event;
import uk.gov.hmcts.ecm.common.model.multiples.MultipleData;

import java.util.Map;

public class EcmCaseDataBuilder {

    private final ObjectMapper objectMapper;
    private static final Boolean IGNORE_WARNING = Boolean.FALSE;

    public EcmCaseDataBuilder(ObjectMapper objectMapper) {
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

    public CaseDataContent buildBulkDataContent(BulkData bulkData, CCDRequest req, String eventSummary) {
        return getCaseDataContent(req, objectMapper.convertValue(bulkData, new TypeReference<>() {
        }), eventSummary, null);
    }

    public CaseDataContent buildMultipleDataContent(MultipleData multipleData, CCDRequest req, String eventSummary) {
        return getCaseDataContent(req, objectMapper.convertValue(multipleData, new TypeReference<>() {
        }), eventSummary, null);
    }

    private CaseDataContent getCaseDataContent(CCDRequest req, Map<String, JsonNode> data, String eventSummary,
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
