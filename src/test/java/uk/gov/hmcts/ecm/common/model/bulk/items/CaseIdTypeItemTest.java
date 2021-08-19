package uk.gov.hmcts.ecm.common.model.bulk.items;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class CaseIdTypeItemTest {

    @Test
    public void shouldCreateCaseIdTypeItem() {
        var ethosCaseReference = "2500001/2021";
        var caseIdTypeItem = CaseIdTypeItem.from(ethosCaseReference);
        
        assertEquals(ethosCaseReference, caseIdTypeItem.getValue().getEthosCaseReference());
        assertNotNull(caseIdTypeItem.getId());
    }
}
