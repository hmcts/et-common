package uk.gov.hmcts.ecm.common.model.ccd;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.Test;
import uk.gov.hmcts.ecm.common.model.ccd.types.ClaimantIndType;

import static org.junit.Assert.assertEquals;

public class CaseDataTest {

    private CaseData caseData;

    @Before
    public void setUp() throws Exception {
        String json = "{"
                + " \"caseNotes\" : \"1111\", "
                + " \"positionType\" : \"Single\", "
                + " \"receiptDate\" : \"20 Jan 2019\", "
                + " \"userLocation\" : \"Bath\", "
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
        assertEquals(caseData.getEcmCaseType(), "Single");
        assertEquals(caseData.getFeeGroupReference(), "1212");
    }
    @Test
    public void claimantFullNamesOthersTest() {
        var claimantIndType = new ClaimantIndType();
        claimantIndType.setClaimantTitle("Other");
        claimantIndType.setClaimantTitleOther("Mx");
        claimantIndType.setClaimantFirstNames("A");
        claimantIndType.setClaimantLastName("B");
        caseData.setClaimantIndType(claimantIndType);
        assertEquals(caseData.getClaimantIndType().claimantFullName(), "Mx A B");
    }
    @Test
    public void claimantFullNamesTest() {
        var claimantIndType = new ClaimantIndType();
        claimantIndType.setClaimantTitle("Mr");
        claimantIndType.setClaimantTitleOther("Mx");
        claimantIndType.setClaimantFirstNames("A");
        claimantIndType.setClaimantLastName("B");
        caseData.setClaimantIndType(claimantIndType);
        assertEquals(caseData.getClaimantIndType().claimantFullName(), "Mr A B");
    }
}