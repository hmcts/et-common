package uk.gov.hmcts.et.common.client;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.junit.MockitoJUnitRunner;

import static org.junit.Assert.assertEquals;

@RunWith(MockitoJUnitRunner.class)
public class CcdClientConfigTest {

    @InjectMocks
    private CcdClientConfig ccdClientConfig;

    @Test
    public void buildStartCaseCreationUrl() {
        String uri = ccdClientConfig.buildStartCaseCreationUrl("1123", "TRIBUNALS", "TRIB_03");
        assertEquals("null/caseworkers/1123/jurisdictions/TRIBUNALS/case-types/TRIB_03/event-triggers/" +
                "initiateCase/token?ignore-warning=true", uri);
    }

    @Test
    public void buildStartCaseCreationTransferUrl() {
        String uri = ccdClientConfig.buildStartCaseCreationTransferUrl("1123", "TRIBUNALS", "TRIB_03");
        assertEquals("null/caseworkers/1123/jurisdictions/TRIBUNALS/case-types/TRIB_03/event-triggers/"
                + "processCaseTransfer/token?ignore-warning=true", uri);
    }

    @Test
    public void buildStartCaseTransferUrl() {
        String uri = ccdClientConfig.buildStartCaseTransferUrl("1123", "TRIBUNALS", "TRIB_03",
                "223");
        assertEquals("null/caseworkers/1123/jurisdictions/TRIBUNALS/case-types/TRIB_03/cases/223/"
                + "event-triggers/caseTransferMultiple/token", uri);
    }

    @Test
    public void buildReturnCaseCreationTransferUrl() {
        String uri = ccdClientConfig.buildReturnCaseCreationTransferUrl("1123", "TRIBUNALS", "TRIB_03",
                "233");
        assertEquals("null/caseworkers/1123/jurisdictions/TRIBUNALS/case-types/TRIB_03/cases/233/"
                + "event-triggers/returnCaseTransfer/token", uri);
    }

    @Test
    public void buildStartCaseMultipleCreationUrl() {
        String uri = ccdClientConfig.buildStartCaseMultipleCreationUrl("1123", "TRIBUNALS", "TRIB_03");
        assertEquals("null/caseworkers/1123/jurisdictions/TRIBUNALS/case-types/TRIB_03/event-triggers/"
                + "createMultiple/token?ignore-warning=true", uri);
    }

    @Test
    public void buildSubmitCaseCreationUrl() {
        String uri = ccdClientConfig.buildSubmitCaseCreationUrl("1123", "TRIBUNALS", "TRIB_03");
        assertEquals("null/caseworkers/1123/jurisdictions/TRIBUNALS/case-types/TRIB_03/cases", uri);
    }

    @Test
    public void buildRetrieveCaseUrl() {
        String uri = ccdClientConfig.buildRetrieveCaseUrl("1123", "TRIBUNALS", "TRIB_03", "1222222");
        assertEquals("null/caseworkers/1123/jurisdictions/TRIBUNALS/case-types/TRIB_03/cases/1222222", uri);
    }

    @Test
    public void buildRetrieveCasesUrl() {
        String uri = ccdClientConfig.buildRetrieveCasesUrl("1123", "TRIBUNALS", "TRIB_03", "2");
        assertEquals("null/caseworkers/1123/jurisdictions/TRIBUNALS/case-types/TRIB_03/cases?page=2", uri);
    }

    @Test
    public void buildRetrieveCasesUrlElasticSearch() {
        String uri = ccdClientConfig.buildRetrieveCasesUrlElasticSearch("1123");
        assertEquals("null/searchCases?ctid=1123", uri);
    }

    @Test
    public void buildStartEventForCaseUrl() {
        String uri = ccdClientConfig.buildStartEventForCaseUrl("1123", "TRIBUNALS", "TRIB_03",
                "1222222");
        assertEquals("null/caseworkers/1123/jurisdictions/TRIBUNALS/case-types/TRIB_03/cases/1222222"
                + "/event-triggers/amendCaseDetails/token", uri);
    }

    @Test
    public void buildStartEventForCaseUrlAPIRole() {
        String uri = ccdClientConfig.buildStartEventForCaseUrlAPIRole("1123", "TRIBUNALS", "TRIB_03",
                "1222222");
        assertEquals("null/caseworkers/1123/jurisdictions/TRIBUNALS/case-types/TRIB_03/cases/1222222"
                + "/event-triggers/amendSingle/token", uri);
    }

    @Test
    public void buildStartEventForCaseUrlBulkSingle() {
        String uri = ccdClientConfig.buildStartEventForCaseUrlBulkSingle("1123", "TRIBUNALS", "TRIB_03",
                "1222222");
        assertEquals("null/caseworkers/1123/jurisdictions/TRIBUNALS/case-types/TRIB_03/cases/1222222"
                + "/event-triggers/amendCaseDetailsBulk/token", uri);
    }

    @Test
    public void buildStartEventForCaseUrlPreAcceptBulkSingle() {
        String uri = ccdClientConfig.buildStartEventForCaseUrlPreAcceptBulkSingle("1123", "TRIBUNALS",
                "TRIB_03", "1222222");
        assertEquals("null/caseworkers/1123/jurisdictions/TRIBUNALS/case-types/TRIB_03/cases/1222222"
                + "/event-triggers/preAcceptanceCase/token", uri);
    }

    @Test
    public void buildStartEventForBulkCaseUrl() {
        String uri = ccdClientConfig.buildStartEventForBulkCaseUrl("1123", "TRIBUNALS", "BULK_03",
                "1222222");
        assertEquals("null/caseworkers/1123/jurisdictions/TRIBUNALS/case-types/BULK_03/cases/1222222"
                + "/event-triggers/updateBulkAction/token", uri);
    }

    @Test
    public void buildStartEventForBulkAmendCaseUrl() {
        String uri = ccdClientConfig.buildStartEventForBulkAmendCaseUrl("1123", "TRIBUNALS", "BULK_03",
                "1222222");
        assertEquals("null/caseworkers/1123/jurisdictions/TRIBUNALS/case-types/BULK_03/cases/1222222"
                + "/event-triggers/amendMultipleAPI/token", uri);
    }

    @Test
    public void buildSubmitEventForCaseUrl() {
        String uri = ccdClientConfig.buildSubmitEventForCaseUrl("1123", "TRIBUNALS", "TRIB_03",
                "1222222");
        assertEquals("null/caseworkers/1123/jurisdictions/TRIBUNALS/case-types/TRIB_03"
                + "/cases/1222222/events", uri);
    }

    @Test
    public void buildPaginationMetadataCaseUrl() {
        String uri = ccdClientConfig.buildPaginationMetadataCaseUrl("1123", "TRIBUNALS", "TRIB_03");
        assertEquals("null/caseworkers/1123/jurisdictions/TRIBUNALS/case-types/TRIB_03"
                + "/cases/pagination_metadata", uri);
    }

    @Test
    public void buildStartDisposeEventForCaseUrl() {
        String uri = ccdClientConfig.buildStartDisposeEventForCaseUrl("1123", "TRIBUNALS", "TRIB_03",
                "1222222");
        assertEquals("null/caseworkers/1123/jurisdictions/TRIBUNALS/case-types/TRIB_03/cases/1222222"
                + "/event-triggers/disposeCase/token", uri);
    }
}