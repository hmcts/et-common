package uk.gov.hmcts.ecm.common.exceptions;

public class ServiceBusConnectionTimeoutException extends RuntimeException {

    private static final long serialVersionUID = -1850692854300268468L;

    public ServiceBusConnectionTimeoutException(String message, Throwable cause) {
        super(message, cause);
    }

}
