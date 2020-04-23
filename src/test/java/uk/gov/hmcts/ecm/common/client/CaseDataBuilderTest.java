package uk.gov.hmcts.ecm.common.client;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.junit.MockitoJUnitRunner;
import uk.gov.hmcts.ecm.common.bulk.BulkData;
import uk.gov.hmcts.ecm.common.bulk.BulkDetails;
import uk.gov.hmcts.ecm.common.ccd.*;

import static org.junit.Assert.assertEquals;

@RunWith(MockitoJUnitRunner.class)
public class CaseDataBuilderTest {

    static final String CREATION_EVENT_SUMMARY = "Case created automatically";
    static final String UPDATE_BULK_EVENT_SUMMARY = "Bulk case updated by bulk";

    @InjectMocks
    private CaseDataBuilder caseDataBuilder;

    private CaseDetails caseDetails;
    private CCDRequest ccdRequest;
    private CaseData caseData;
    private BulkDetails bulkDetails;
    private BulkData bulkData;

    @Before
    public void setUp() {
        ccdRequest = new CCDRequest();
        ccdRequest.setEventId("1111");
        ccdRequest.setToken("Token");
        caseDetails = new CaseDetails();
        caseDetails.setJurisdiction("TRIBUNALS");
        caseDetails.setCaseTypeId("Type1");
        caseData = new CaseData();
        caseDetails.setCaseData(caseData);
        bulkDetails = new BulkDetails();
        bulkDetails.setJurisdiction("TRIBUNALS");
        bulkDetails.setCaseTypeId("Type1");
        bulkData = new BulkData();
        bulkDetails.setCaseData(bulkData);
    }

    @Test
    public void buildCaseDataContent() {
        CaseDataContent caseDataContent = CaseDataContent.builder()
                .event(Event.builder().eventId(ccdRequest.getEventId()).summary(CREATION_EVENT_SUMMARY).build())
                .ignoreWarning(false)
                .token(ccdRequest.getToken())
                .build();
        assertEquals(caseDataContent, caseDataBuilder.buildCaseDataContent(caseData, ccdRequest, CREATION_EVENT_SUMMARY));
    }

    @Test
    public void buildBulkDataContent() {
        CaseDataContent caseDataContent = CaseDataContent.builder()
                .event(Event.builder().eventId(ccdRequest.getEventId()).summary(UPDATE_BULK_EVENT_SUMMARY).build())
                .ignoreWarning(false)
                .token(ccdRequest.getToken())
                .build();
        assertEquals(caseDataContent, caseDataBuilder.buildBulkDataContent(bulkData, ccdRequest, UPDATE_BULK_EVENT_SUMMARY));
    }
}