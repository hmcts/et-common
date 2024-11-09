package uk.gov.hmcts.ecm.common.service.pdf.et3.util;

import lombok.extern.slf4j.Slf4j;

import java.io.Serial;

import static org.apache.commons.lang3.StringUtils.isNotBlank;
import static uk.gov.hmcts.ecm.common.constants.PdfMapperConstants.STRING_EMPTY;

/**
 * Is thrown when an exception occurs whiles converting case data in pdf.
 * for example an IOException is encountered while loading pdf template
 */
@Slf4j
public class GenericServiceException extends Exception {
    @Serial
    private static final long serialVersionUID = 304268196018404976L;

    /**
     * Creates a {@link GenericServiceException} with an error message and the cause of the error and
     * logs exception with the values to make it easier to find in Azure logs.
     * @param message main message of the exception
     * @param cause main throwable cause of the exception
     * @param firstWord First wordings of the log
     * @param caseReferenceNumber reference number of the case
     * @param className class name where the exception occurred
     * @param methodName method of the exception occurred
     */
    public GenericServiceException(String message, Throwable cause, String firstWord, String caseReferenceNumber,
                                   String className, String methodName) {
        super(message, cause);
        logException(isNotBlank(firstWord) ? firstWord : STRING_EMPTY,
                isNotBlank(caseReferenceNumber) ? caseReferenceNumber : STRING_EMPTY,
                isNotBlank(message) ? message : STRING_EMPTY,
                isNotBlank(className) ? className : STRING_EMPTY,
                isNotBlank(methodName) ? methodName : STRING_EMPTY);
    }

    /**
     * Logs exception according to given user inputs below. It enables us have a better log on azure cloud.
     * @param firstWord first wordings of the log.
     * @param caseReferenceNumber ethos case reference data
     * @param message exception message
     * @param className class name where the exception occurs
     * @param methodName method name where the exception occurs
     */
    public static void logException(String firstWord,
                                    String caseReferenceNumber,
                                    String message,
                                    String className,
                                    String methodName) {
        log.error("*************EXCEPTION OCCURED*************"
                + "\nERROR DESCRIPTION: " + (isNotBlank(firstWord) ? firstWord : STRING_EMPTY)
                + "\nCASE REFERENCE: " + (isNotBlank(caseReferenceNumber) ? caseReferenceNumber : STRING_EMPTY)
                + "\nERROR MESSAGE: " + (isNotBlank(message) ? message : STRING_EMPTY)
                + "\nCLASS NAME: " + (isNotBlank(className) ? className : STRING_EMPTY)
                + "\nMETHOD NAME: " + (isNotBlank(methodName) ? methodName : STRING_EMPTY)
                + "\n*****************END OF EXCEPTION MESSAGE***********************");
    }

}
