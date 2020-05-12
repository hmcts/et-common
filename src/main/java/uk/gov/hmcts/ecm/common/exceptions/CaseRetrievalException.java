package uk.gov.hmcts.ecm.common.exceptions;

public class CaseRetrievalException extends RuntimeException {
    public CaseRetrievalException(String message) {
        super(message);
    }
}
