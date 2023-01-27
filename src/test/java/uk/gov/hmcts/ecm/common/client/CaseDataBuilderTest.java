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
public class CaseDataBuilderTest {
    static final String CREATION_EVENT_SUMMARY = "Case created automatically";
    static final String UPDATE_BULK_EVENT_SUMMARY = "Bulk case updated by bulk";
    static final String UPDATE_CHANGE_ORG_SUMMARY = "Change of organisation completed";

    @InjectMocks
    private CaseDataBuilder caseDataBuilder;

    @Mock
    private ObjectMapper objectMapper;

    private CCDRequest ccdRequest;
    private CaseData caseData;
    private BulkData bulkData;
    private MultipleData multipleData;

    @BeforeEach
    public void setUp() {
        ccdRequest = new CCDRequest();
        ccdRequest.setEventId("1111");
        ccdRequest.setToken("Token");
        caseData = new CaseData();
        bulkData = new BulkData();
        multipleData = new MultipleData();
    }

    @Test
    public void buildCaseDataContent() {
        CaseDataContent caseDataContent = CaseDataContent.builder()
                .event(Event.builder().eventId(ccdRequest.getEventId()).summary(CREATION_EVENT_SUMMARY).build())
                .ignoreWarning(false)
                .token(ccdRequest.getToken())
                .build();
        assertEquals(caseDataContent, caseDataBuilder.buildCaseDataContent(caseData,
                ccdRequest, CREATION_EVENT_SUMMARY));
    }

    @Test
    public void buildBulkDataContent() {
        CaseDataContent caseDataContent = CaseDataContent.builder()
                .event(Event.builder().eventId(ccdRequest.getEventId()).summary(UPDATE_BULK_EVENT_SUMMARY).build())
                .ignoreWarning(false)
                .token(ccdRequest.getToken())
                .build();
        assertEquals(caseDataContent, caseDataBuilder.buildBulkDataContent(bulkData,
                ccdRequest, UPDATE_BULK_EVENT_SUMMARY));
    }

    @Test
    public void buildMultipleDataContent() {
        CaseDataContent caseDataContent = CaseDataContent.builder()
                .event(Event.builder().eventId(ccdRequest.getEventId()).summary(UPDATE_BULK_EVENT_SUMMARY).build())
                .ignoreWarning(false)
                .token(ccdRequest.getToken())
                .build();
        assertEquals(caseDataContent, caseDataBuilder.buildMultipleDataContent(multipleData,
                ccdRequest, UPDATE_BULK_EVENT_SUMMARY));
    }

    @Test
    public void buildChangeOrganisationDataContent() {
        CaseDataContent caseDataContent = CaseDataContent.builder()
            .event(Event.builder().eventId(ccdRequest.getEventId()).summary(UPDATE_CHANGE_ORG_SUMMARY).build())
            .ignoreWarning(false)
            .token(ccdRequest.getToken())
            .build();
        assertEquals(caseDataContent, caseDataBuilder.buildChangeOrganisationDataContent(new HashMap<>(),
            ccdRequest, UPDATE_CHANGE_ORG_SUMMARY));
    }
}