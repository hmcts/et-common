package uk.gov.hmcts.ecm.common.servicebus;

import com.microsoft.azure.servicebus.MessageBody;
import org.springframework.util.CollectionUtils;

import java.util.List;

public final class MessageBodyRetriever {

    public static byte[] getBinaryData(MessageBody messageBody) {
        List<byte[]> binaryData = messageBody.getBinaryData();

        return CollectionUtils.isEmpty(binaryData) ? null : binaryData.getFirst();
    }

    private MessageBodyRetriever() {
        // utility class construct
    }
}
