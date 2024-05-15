package uk.gov.hmcts.ecm.common.client;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import uk.gov.hmcts.et.common.model.bulk.BulkData;
import uk.gov.hmcts.et.common.model.ccd.CCDRequest;
import uk.gov.hmcts.et.common.model.ccd.CaseData;
import uk.gov.hmcts.et.common.model.ccd.CaseDataContent;
import uk.gov.hmcts.et.common.model.ccd.Event;
import uk.gov.hmcts.et.common.model.multiples.MultipleData;

import java.util.HashMap;
import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
class CaseDataBuilderTest {
    static final String CREATION_EVENT_SUMMARY = "Case created automatically";
    static final String UPDATE_BULK_EVENT_SUMMARY = "Bulk case updated by bulk";
    static final String UPDATE_CHANGE_ORG_SUMMARY = "Change of organisation completed";
    static final String CASE_CREATION_BY_GENERIC_CASE_DATA_SUMMARY
            = "Case with generic case data creation content summary";
    static final String CASE_CREATION_BY_GENERIC_CASE_DATA_DESCRIPTION
            = "Case with generic case data creation content description";

    @InjectMocks
    private CaseDataBuilder caseDataBuilder;

    @Mock
    private ObjectMapper objectMapper;

    private CCDRequest ccdRequest;
    private CaseData caseData;
    private BulkData bulkData;
    private MultipleData multipleData;

    @BeforeEach
    void setUp() {
        ccdRequest = new CCDRequest();
        ccdRequest.setEventId("1111");
        ccdRequest.setToken("Token");
        caseData = new CaseData();
        bulkData = new BulkData();
        multipleData = new MultipleData();
    }

    @Test
    void buildCaseDataContent() {
        CaseDataContent caseDataContent = CaseDataContent.builder()
                .event(Event.builder().eventId(ccdRequest.getEventId()).summary(CREATION_EVENT_SUMMARY).build())
                .ignoreWarning(false)
                .token(ccdRequest.getToken())
                .build();
        assertEquals(caseDataContent, caseDataBuilder.buildCaseDataContent(caseData,
                ccdRequest, CREATION_EVENT_SUMMARY));
    }

    @Test
    void buildBulkDataContent() {
        CaseDataContent caseDataContent = CaseDataContent.builder()
                .event(Event.builder().eventId(ccdRequest.getEventId()).summary(UPDATE_BULK_EVENT_SUMMARY).build())
                .ignoreWarning(false)
                .token(ccdRequest.getToken())
                .build();
        assertEquals(caseDataContent, caseDataBuilder.buildBulkDataContent(bulkData,
                ccdRequest, UPDATE_BULK_EVENT_SUMMARY));
    }

    @Test
    void buildMultipleDataContent() {
        CaseDataContent caseDataContent = CaseDataContent.builder()
                .event(Event.builder().eventId(ccdRequest.getEventId()).summary(UPDATE_BULK_EVENT_SUMMARY).build())
                .ignoreWarning(false)
                .token(ccdRequest.getToken())
                .build();
        assertEquals(caseDataContent, caseDataBuilder.buildMultipleDataContent(multipleData,
                ccdRequest, UPDATE_BULK_EVENT_SUMMARY));
    }

    @Test
    void buildChangeOrganisationDataContent() {
        CaseDataContent caseDataContent = CaseDataContent.builder()
            .event(Event.builder().eventId(ccdRequest.getEventId()).summary(UPDATE_CHANGE_ORG_SUMMARY).build())
            .ignoreWarning(false)
            .token(ccdRequest.getToken())
            .build();
        assertEquals(caseDataContent, caseDataBuilder.buildChangeOrganisationDataContent(new HashMap<>(),
            ccdRequest, UPDATE_CHANGE_ORG_SUMMARY));
    }

    @Test
    void buildGenericCaseDataContent() {
        CaseDataContent caseDataContent = CaseDataContent.builder()
                .event(Event.builder().eventId(ccdRequest.getEventId())
                        .summary(CASE_CREATION_BY_GENERIC_CASE_DATA_SUMMARY)
                        .description(CASE_CREATION_BY_GENERIC_CASE_DATA_DESCRIPTION).build())
                .ignoreWarning(false)
                .token(ccdRequest.getToken())
                .build();
        assertEquals(caseDataContent, caseDataBuilder.buildGenericCaseDataContent(caseData, ccdRequest,
                CASE_CREATION_BY_GENERIC_CASE_DATA_SUMMARY, CASE_CREATION_BY_GENERIC_CASE_DATA_DESCRIPTION));
    }
}