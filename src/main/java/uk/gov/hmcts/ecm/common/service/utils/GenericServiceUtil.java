package uk.gov.hmcts.ecm.common.service.utils;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public final class GenericServiceUtil {

    private GenericServiceUtil() {
        // Utility classes should not have a public or default constructor.
    }

    public static void logException(String firstWord, String caseReferenceNumber, String errorMessage,
                             String className, String methodName) {
        log.error("*************EXCEPTION OCCURED*************"
                     + "\nERROR DESCRIPTION: " + firstWord
                     + "\nCASE REFERENCE: " + caseReferenceNumber
                     + "\nERROR MESSAGE: " + errorMessage
                     + "\nCLASS NAME: " + className
                     + "\nMETHOD NAME: " + methodName
                     + "\n*****************END OF EXCEPTION MESSAGE***********************");
    }

}
