package uk.gov.hmcts.ecm.common.model.ccd;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;

public class CaseDataTest {

    private CaseData caseData;

    @Before
    public void setUp() throws Exception {
        String json = "{"
                + " \"caseNotes\" : \"1111\", "
                + " \"positionType\" : \"Single\", "
                + " \"receiptDate\" : \"20 Jan 2019\", "
                + " \"userLocation\" : \"Bath\", "
                + " \"fileLocation\" : \"City\", "
                + " \"caseType\" : \"Single\", "
                + " \"feeGroupReference\" : \"1212\" "
                + "} ";
        ObjectMapper mapper = new ObjectMapper();
        caseData = mapper.readValue(json, CaseData.class);
    }

    @Test
    public void shouldCreateCaseDataFromJson() {
        assertEquals(caseData.getCaseNotes(), "1111");
        assertEquals(caseData.getPositionType(), "Single");
        assertEquals(caseData.getReceiptDate(), "20 Jan 2019");
        assertEquals(caseData.getUserLocation(), "Bath");
        assertEquals(caseData.getFileLocation(), "City");
        assertEquals(caseData.getCaseType(), "Single");
        assertEquals(caseData.getFeeGroupReference(), "1212");
    }
}