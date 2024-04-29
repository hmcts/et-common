package uk.gov.hmcts.ecm.common.servicebus;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.microsoft.azure.servicebus.IQueueClient;
import com.microsoft.azure.servicebus.primitives.ServiceBusException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import uk.gov.hmcts.ecm.common.exceptions.InvalidMessageException;
import uk.gov.hmcts.ecm.common.exceptions.ServiceBusConnectionTimeoutException;
import uk.gov.hmcts.ecm.common.helpers.ServiceBusHelper;
import uk.gov.hmcts.ecm.common.model.servicebus.UpdateCaseMsg;
import uk.gov.hmcts.ecm.common.model.servicebus.datamodel.CreationDataModel;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doThrow;

@ExtendWith(MockitoExtension.class)
class ServiceBusSenderTest {

    @InjectMocks
    private ServiceBusSender serviceBusSender;
    @Mock
    private IQueueClient sendClient;
    @Mock
    private ObjectMapper objectMapper;

    private UpdateCaseMsg updateCaseMsg;

    @BeforeEach
    public void setUp() {
        serviceBusSender = new ServiceBusSender(sendClient, objectMapper);
        CreationDataModel creationDataModel = ServiceBusHelper.getCreationDataModel("4150002/2020");
        updateCaseMsg = ServiceBusHelper.generateUpdateCaseMsg(creationDataModel);
    }

    @Test
   void sendMessageAsync() {
        serviceBusSender.sendMessageAsync(updateCaseMsg);
    }

    @Test
    void sendMessage() {
        serviceBusSender.sendMessage(updateCaseMsg);
    }

    @Test
    void sendMessageNull() {
        assertThrows(InvalidMessageException.class, () -> {
            serviceBusSender.sendMessage(null);
        });
    }

    @Test()
    void sendMessageNullId() {
        updateCaseMsg.setMsgId(null);
        assertThrows(InvalidMessageException.class, () -> {
            serviceBusSender.sendMessageAsync(updateCaseMsg);
        });

    }

    @Test()
    void sendMessageTimeoutException() throws ServiceBusException, InterruptedException {
        doThrow(ServiceBusConnectionTimeoutException.class).when(sendClient).send(any());
        assertThrows(ServiceBusConnectionTimeoutException.class, () -> {
            serviceBusSender.sendMessage(updateCaseMsg);
        });

    }

    @Test()
    void sendMessageInterruptedException() throws ServiceBusException, InterruptedException {
        doThrow(new InterruptedException()).when(sendClient).send(any());
        assertThrows(InvalidMessageException.class, () -> {
            serviceBusSender.sendMessage(updateCaseMsg);
        });
    }

    @Test()
    void sendMessageServiceBusException() throws ServiceBusException, InterruptedException {
        doThrow(new ServiceBusException(true)).when(sendClient).send(any());
        assertThrows(InvalidMessageException.class, () -> {
            serviceBusSender.sendMessage(updateCaseMsg);
        });
    }

}
