package uk.gov.hmcts.ecm.common.service.pdf.et1;

import lombok.extern.slf4j.Slf4j;
import uk.gov.hmcts.et.common.model.ccd.CaseData;

import static uk.gov.hmcts.ecm.common.model.helper.Constants.ENGLISH_LANGUAGE;
import static uk.gov.hmcts.ecm.common.model.helper.Constants.WELSH_LANGUAGE;

@Slf4j
public final class GenericServiceUtil {

    private GenericServiceUtil() {
        // Utility classes should not have a public or default constructor.
    }

    public static String findClaimantLanguage(CaseData caseData) {
        return caseData.getClaimantHearingPreference() != null
               && caseData.getClaimantHearingPreference().getContactLanguage() != null
               && WELSH_LANGUAGE.equals(caseData.getClaimantHearingPreference().getContactLanguage()) ? WELSH_LANGUAGE
                : ENGLISH_LANGUAGE;
    }

    public static void logException(String firstWord, String caseReferenceNumber, String errorMessage,
                                    String className, String methodName) {
        String error = "*************EXCEPTION OCCURED*************"
                + "\nERROR DESCRIPTION: " + firstWord
                + "\nCASE REFERENCE: " + caseReferenceNumber
                + "\nERROR MESSAGE: " + errorMessage
                + "\nCLASS NAME: " + className
                + "\nMETHOD NAME: " + methodName
                + "\n*****************END OF EXCEPTION MESSAGE***********************";
        log.error(error);
        System.out.println(error);
    }

}
