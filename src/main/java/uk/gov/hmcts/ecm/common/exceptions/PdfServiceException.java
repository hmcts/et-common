package uk.gov.hmcts.ecm.common.exceptions;

import uk.gov.hmcts.ecm.common.service.pdf.PdfService;

import java.io.Serial;

/**
 * Is thrown when an exception occurs whiles converting case data in pdf in {@link PdfService}.
 * for example an IOException is encountered while loading pdf template
 */
public class PdfServiceException extends Exception {
    @Serial
    private static final long serialVersionUID = 304268196018404976L;

    /**
     * Creates a {@link PdfServiceException} with an error message and the cause of the error.
     *
     * @param message the message explaining why the error occurred
     * @param cause the cause of the error
     */
    public PdfServiceException(String message, Throwable cause) {
        super(message, cause);
    }
}
