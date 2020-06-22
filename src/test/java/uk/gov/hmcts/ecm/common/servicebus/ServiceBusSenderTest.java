package uk.gov.hmcts.ecm.common.servicebus;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.microsoft.azure.servicebus.IQueueClient;
import com.microsoft.azure.servicebus.primitives.ServiceBusException;
import com.microsoft.azure.servicebus.primitives.TimeoutException;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import uk.gov.hmcts.ecm.common.exceptions.InvalidMessageException;
import uk.gov.hmcts.ecm.common.exceptions.ServiceBusConnectionTimeoutException;
import uk.gov.hmcts.ecm.common.helpers.ServiceBusHelper;
import uk.gov.hmcts.ecm.common.model.servicebus.UpdateCaseMsg;
import uk.gov.hmcts.ecm.common.model.servicebus.datamodel.CreationDataModel;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doThrow;

@RunWith(MockitoJUnitRunner.class)
public class ServiceBusSenderTest {

    @InjectMocks
    private ServiceBusSender serviceBusSender;
    @Mock
    private IQueueClient sendClient;
    @Mock
    private ObjectMapper objectMapper;

    private UpdateCaseMsg updateCaseMsg;

    @Before
    public void setUp() {
        serviceBusSender = new ServiceBusSender(sendClient, objectMapper);
        CreationDataModel creationDataModel = ServiceBusHelper.getCreationDataModel("4150002/2020");
        updateCaseMsg = ServiceBusHelper.generateUpdateCaseMsg(creationDataModel);
    }

    @Test
    public void sendMessageAsync() {
        serviceBusSender.sendMessageAsync(updateCaseMsg);
    }

    @Test
    public void sendMessage() {
        serviceBusSender.sendMessage(updateCaseMsg);
    }

    @Test(expected = InvalidMessageException.class)
    public void sendMessageNull() {
        serviceBusSender.sendMessageAsync(null);
    }

    @Test(expected = InvalidMessageException.class)
    public void sendMessageNullId() {
        updateCaseMsg.setMsgId(null);
        serviceBusSender.sendMessageAsync(updateCaseMsg);
    }

    @Test(expected = ServiceBusConnectionTimeoutException.class)
    public void sendMessageTimeoutException() throws ServiceBusException, InterruptedException {
        doThrow(new TimeoutException()).when(sendClient).send(any());
        serviceBusSender.sendMessage(updateCaseMsg);
    }

    @Test(expected = InvalidMessageException.class)
    public void sendMessageInterruptedException() throws ServiceBusException, InterruptedException {
        doThrow(new InterruptedException()).when(sendClient).send(any());
        serviceBusSender.sendMessage(updateCaseMsg);
    }

    @Test(expected = InvalidMessageException.class)
    public void sendMessageServiceBusException() throws ServiceBusException, InterruptedException {
        doThrow(new ServiceBusException(true)).when(sendClient).send(any());
        serviceBusSender.sendMessage(updateCaseMsg);
    }

}
