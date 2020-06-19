package uk.gov.hmcts.ecm.common.helpers;

import uk.gov.hmcts.ecm.common.model.servicebus.CreateUpdatesMsg;
import uk.gov.hmcts.ecm.common.model.servicebus.UpdateCaseMsg;
import uk.gov.hmcts.ecm.common.model.servicebus.UpdateData;

import java.util.Arrays;

import static uk.gov.hmcts.ecm.common.model.helper.Constants.SCOTLAND_BULK_CASE_TYPE_ID;
import static uk.gov.hmcts.ecm.common.model.servicebus.UpdateType.CREATION;

public class ServiceBusHelper {

    private static UpdateData updateData = UpdateData.builder()
        .lead("4150002/2020")
        .claimantRep("ClaimantRep")
        .build();

    public static UpdateCaseMsg generateUpdateCaseMsg() {
        return UpdateCaseMsg.builder()
            .msgId("1")
            .updateType(CREATION.name())
            .jurisdiction("EMPLOYMENT")
            .caseTypeId(SCOTLAND_BULK_CASE_TYPE_ID)
            .multipleRef("4150001")
            .ethosCaseReference("4150002/2020")
            .totalCases("1")
            .username("eric.ccdcooper@gmail.com")
            .updateData(updateData)
            .build();
    }

    public static CreateUpdatesMsg generateCreateUpdatesMsg() {
        return CreateUpdatesMsg.builder()
            .msgId("1")
            .updateType(CREATION.name())
            .jurisdiction("EMPLOYMENT")
            .caseTypeId(SCOTLAND_BULK_CASE_TYPE_ID)
            .multipleRef("4150001")
            .ethosCaseRefCollection(Arrays.asList("4150001/2020", "4150002/2020", "4150003/2020"))
            .totalCases("3")
            .username("eric.ccdcooper@gmail.com")
            .updateData(updateData)
            .build();
    }

}
