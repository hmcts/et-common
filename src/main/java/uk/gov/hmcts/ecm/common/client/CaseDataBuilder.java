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

public class CaseDataBuilder {

    private final ObjectMapper objectMapper;
    private static final Boolean IGNORE_WARNING = Boolean.FALSE;

    public CaseDataBuilder(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    public CaseDataContent buildCaseDataContent(CaseData caseData, CCDRequest req, String eventSummary) {
        return getCaseDataContent(req, objectMapper.convertValue(caseData, new TypeReference<>() {
        }), eventSummary);
    }

    public CaseDataContent buildBulkDataContent(BulkData bulkData, CCDRequest req, String eventSummary) {
        return getCaseDataContent(req, objectMapper.convertValue(bulkData, new TypeReference<>() {
        }), eventSummary);
    }

    public CaseDataContent buildMultipleDataContent(MultipleData multipleData, CCDRequest req, String eventSummary) {
        return getCaseDataContent(req, objectMapper.convertValue(multipleData, new TypeReference<>() {
        }), eventSummary);
    }

    private CaseDataContent getCaseDataContent(CCDRequest req, Map<String, JsonNode> data, String eventSummary) {
        return CaseDataContent.builder()
                .event(Event.builder().eventId(req.getEventId()).summary(eventSummary).build())
                .data(data)
                .token(req.getToken())
                .ignoreWarning(IGNORE_WARNING)
                .build();
    }
}
