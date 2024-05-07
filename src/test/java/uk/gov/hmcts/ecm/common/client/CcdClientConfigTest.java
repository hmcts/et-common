package uk.gov.hmcts.ecm.common.client;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class CcdClientConfigTest {

    private CcdClientConfig ccdClientConfig;

    @BeforeEach
    void setup() {
        ccdClientConfig = new CcdClientConfig("https://localhost:4452");
    }

    @Test
    void buildStartCaseCreationUrl() {
        String uri = ccdClientConfig.buildStartCaseCreationUrl("1123", "TRIBUNALS", "TRIB_03");
        assertEquals("https://localhost:4452/caseworkers/1123/jurisdictions/TRIBUNALS/case-types/TRIB_03/event-triggers/"
                + "initiateCase/token?ignore-warning=true", uri);
    }

    @Test
    void buildStartCaseCreationTransferUrl() {
        String uri = ccdClientConfig.buildStartCaseCreationTransferUrl("1123", "TRIBUNALS", "TRIB_03");
        assertEquals("https://localhost:4452/caseworkers/1123/jurisdictions/TRIBUNALS/case-types/TRIB_03/event-triggers/"
                + "processCaseTransfer/token?ignore-warning=true", uri);
    }

    @Test
    void buildStartCaseTransferUrl() {
        String uri = ccdClientConfig.buildStartCaseTransferUrl("1123", "TRIBUNALS", "TRIB_03",
                "223");
        assertEquals("https://localhost:4452/caseworkers/1123/jurisdictions/TRIBUNALS/case-types/TRIB_03/cases/223/"
                + "event-triggers/caseTransferMultiple/token", uri);
    }

    @Test
    void buildReturnCaseCreationTransferUrl() {
        String uri = ccdClientConfig.buildReturnCaseCreationTransferUrl("1123", "TRIBUNALS", "TRIB_03",
                "233");
        assertEquals("https://localhost:4452/caseworkers/1123/jurisdictions/TRIBUNALS/case-types/TRIB_03/cases/233/"
                + "event-triggers/returnCaseTransfer/token", uri);
    }

    @Test
    void buildStartCaseMultipleCreationUrl() {
        String uri = ccdClientConfig.buildStartCaseMultipleCreationUrl("1123", "TRIBUNALS", "TRIB_03");
        assertEquals("https://localhost:4452/caseworkers/1123/jurisdictions/TRIBUNALS/case-types/TRIB_03/event-triggers/"
                + "createMultiple/token?ignore-warning=true", uri);
    }

    @Test
    void buildSubmitCaseCreationUrl() {
        String uri = ccdClientConfig.buildSubmitCaseCreationUrl("1123", "TRIBUNALS", "TRIB_03");
        assertEquals("https://localhost:4452/caseworkers/1123/jurisdictions/TRIBUNALS/case-types/TRIB_03/cases", uri);
    }

    @Test
    void buildRetrieveCaseUrl() {
        String uri = ccdClientConfig.buildRetrieveCaseUrl("1123", "TRIBUNALS", "TRIB_03", "1222222");
        assertEquals("https://localhost:4452/caseworkers/1123/jurisdictions/TRIBUNALS/case-types/TRIB_03/cases/1222222", uri);
    }

    @Test
    void buildRetrieveCasesUrl() {
        String uri = ccdClientConfig.buildRetrieveCasesUrl("1123", "TRIBUNALS", "TRIB_03", "2");
        assertEquals("https://localhost:4452/caseworkers/1123/jurisdictions/TRIBUNALS/case-types/TRIB_03/cases?page=2", uri);
    }

    @Test
    void buildRetrieveCasesUrlElasticSearch() {
        String uri = ccdClientConfig.buildRetrieveCasesUrlElasticSearch("1123");
        assertEquals("https://localhost:4452/searchCases?ctid=1123", uri);
    }

    @Test
    void buildStartEventForCaseUrl() {
        String uri = ccdClientConfig.buildStartEventForCaseUrl("1123", "TRIBUNALS", "TRIB_03",
                "1222222");
        assertEquals("https://localhost:4452/caseworkers/1123/jurisdictions/TRIBUNALS/case-types/TRIB_03/cases/1222222"
                + "/event-triggers/amendCaseDetails/token", uri);
    }

    @Test
    void buildStartEventForCaseUrlAPIRole() {
        String uri = ccdClientConfig.buildStartEventForCaseUrlAPIRole("1123", "TRIBUNALS", "TRIB_03",
                "1222222");
        assertEquals("https://localhost:4452/caseworkers/1123/jurisdictions/TRIBUNALS/case-types/TRIB_03/cases/1222222"
                + "/event-triggers/amendSingle/token", uri);
    }

    @Test
    void buildStartEventForCaseUrlBulkSingle() {
        String uri = ccdClientConfig.buildStartEventForCaseUrlBulkSingle("1123", "TRIBUNALS", "TRIB_03",
                "1222222");
        assertEquals("https://localhost:4452/caseworkers/1123/jurisdictions/TRIBUNALS/case-types/TRIB_03/cases/1222222"
                + "/event-triggers/amendCaseDetailsBulk/token", uri);
    }

    @Test
    void buildStartEventForCaseUrlPreAcceptBulkSingle() {
        String uri = ccdClientConfig.buildStartEventForCaseUrlPreAcceptBulkSingle("1123", "TRIBUNALS",
                "TRIB_03", "1222222");
        assertEquals("https://localhost:4452/caseworkers/1123/jurisdictions/TRIBUNALS/case-types/TRIB_03/cases/1222222"
                + "/event-triggers/preAcceptanceCase/token", uri);
    }

    @Test
    void buildStartEventForBulkCaseUrl() {
        String uri = ccdClientConfig.buildStartEventForBulkCaseUrl("1123", "TRIBUNALS", "BULK_03",
                "1222222");
        assertEquals("https://localhost:4452/caseworkers/1123/jurisdictions/TRIBUNALS/case-types/BULK_03/cases/1222222"
                + "/event-triggers/updateBulkAction/token", uri);
    }

    @Test
    void buildStartEventForBulkAmendCaseUrl() {
        String uri = ccdClientConfig.buildStartEventForBulkAmendCaseUrl("1123", "TRIBUNALS", "BULK_03",
                "1222222");
        assertEquals("https://localhost:4452/caseworkers/1123/jurisdictions/TRIBUNALS/case-types/BULK_03/cases/1222222"
                + "/event-triggers/amendMultipleAPI/token", uri);
    }

    @Test
    void buildSubmitEventForCaseUrl() {
        String uri = ccdClientConfig.buildSubmitEventForCaseUrl("1123", "TRIBUNALS", "TRIB_03",
                "1222222");
        assertEquals("https://localhost:4452/caseworkers/1123/jurisdictions/TRIBUNALS/case-types/TRIB_03"
                + "/cases/1222222/events", uri);
    }

    @Test
    void buildPaginationMetadataCaseUrl() {
        String uri = ccdClientConfig.buildPaginationMetadataCaseUrl("1123", "TRIBUNALS", "TRIB_03");
        assertEquals("https://localhost:4452/caseworkers/1123/jurisdictions/TRIBUNALS/case-types/TRIB_03"
                + "/cases/pagination_metadata", uri);
    }

    @Test
    void buildStartDisposeEventForCaseUrl() {
        String uri = ccdClientConfig.buildStartDisposeEventForCaseUrl("1123", "TRIBUNALS", "TRIB_03",
                "1222222");
        assertEquals("https://localhost:4452/caseworkers/1123/jurisdictions/TRIBUNALS/case-types/TRIB_03/cases/1222222"
                + "/event-triggers/disposeCase/token", uri);
    }

    @Test
    void buildStartUpdateRepEventForCaseUrl() {
        String uri = ccdClientConfig.buildStartUpdateRepEventForCaseUrl("1123", "EMPLOYMENT", "ET_EnglandWales",
            "1222222");
        assertEquals("https://localhost:4452/caseworkers/1123/jurisdictions/EMPLOYMENT/case-types/ET_EnglandWales/cases/1222222"
            + "/event-triggers/updateRepresentation/token", uri);
    }

    @Test
    void buildUrlForSupplementaryApi() {
        String uri = ccdClientConfig.buildUrlForSupplementaryApi("1123");
        assertEquals("https://localhost:4452/cases/1123/supplementary-data", uri);
    }

    @Test
    void addLegalRepToMultiCaseUrl() {
        String uri = ccdClientConfig.addLegalRepToMultiCaseUrl("123", "EMPLOYMENT",
                "ET_EnglandWales_Multiples", "6000001");
        assertEquals("https://localhost:4452"
                        + "/caseworkers/123"
                        + "/jurisdictions/EMPLOYMENT"
                        + "/case-types/ET_EnglandWales_Multiples"
                        + "/cases/6000001"
                        + "/users", uri);
    }

    @Test
    void removeLegalRepFromMultiCaseUrl() {
        String uri = ccdClientConfig.removeLegalRepFromMultiCaseUrl("123", "EMPLOYMENT",
                "ET_EnglandWales_Multiples", "6000001", "456");
        assertEquals("https://localhost:4452"
                        + "/caseworkers/123"
                        + "/jurisdictions/EMPLOYMENT"
                        + "/case-types/ET_EnglandWales_Multiples"
                        + "/cases/6000001"
                        + "/users/456", uri);
    }
}