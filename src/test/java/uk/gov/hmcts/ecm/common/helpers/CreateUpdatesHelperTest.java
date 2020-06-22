package uk.gov.hmcts.ecm.common.helpers;

import org.junit.Before;
import org.junit.Test;
import uk.gov.hmcts.ecm.common.model.servicebus.CreateUpdatesDto;
import uk.gov.hmcts.ecm.common.model.servicebus.CreateUpdatesMsg;
import uk.gov.hmcts.ecm.common.model.servicebus.datamodel.CreationDataModel;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static uk.gov.hmcts.ecm.common.model.helper.Constants.SCOTLAND_BULK_CASE_TYPE_ID;
import static uk.gov.hmcts.ecm.common.model.servicebus.UpdateType.CREATION;

public class CreateUpdatesHelperTest {

    private CreateUpdatesDto createUpdatesDto;
    private CreationDataModel creationDataModel;

    @Before
    public void setUp() {
        List<String> ethosCaseRefCollection = Arrays.asList("4150001/2020", "4150002/2020", "4150003/2020", "4150004/2020", "4150005/2020");
        createUpdatesDto = getCreateUpdatesDto(ethosCaseRefCollection);
        creationDataModel = ServiceBusHelper.getCreationDataModel(ethosCaseRefCollection.get(0));
    }

    @Test
    public void generateUpdateCaseMsgForCreation() {
        List<CreateUpdatesMsg> createUpdatesMsgList = CreateUpdatesHelper.getCreateUpdatesMessagesCollection(
                createUpdatesDto, creationDataModel, 2);
        assertEquals(3, createUpdatesMsgList.size());
    }


    private CreateUpdatesDto getCreateUpdatesDto(List<String> ethosCaseRefCollection) {
        return CreateUpdatesDto.builder()
            .caseTypeId(SCOTLAND_BULK_CASE_TYPE_ID)
            .jurisdiction("EMPLOYMENT")
            .multipleRef("4150001")
            .username("testEmail@hotmail.com")
            .updateType(CREATION.name())
            .ethosCaseRefCollection(ethosCaseRefCollection)
            .build();
    }

}
