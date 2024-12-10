package uk.gov.hmcts.ecm.common.service.pdf.et3.util;

import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import uk.gov.hmcts.et.common.model.ccd.types.RespondentSumType;

import static uk.gov.hmcts.ecm.common.constants.PdfMapperConstants.NO;

public class ET3FormRespondentUtil {
    private ET3FormRespondentUtil() {
        // Utility classes should not have a public or default constructor.
    }

    /**
     * Finds respondent name by RespondentSumType model which is entered by the respondent.
     * If response respondent answer to the question is respondent's name entered by the claimant is No it returns
     * the value of response respondent name which is the value entered by the respondent. If it is Yes, it tries
     * to find the respondent name by using the fields, respondent name, respondent organisation or respondent first
     * and last name fields.
     * @param respondentSumType is the type which has the respondent's et3 form responses.
     * @return the name according to the response respondent name question.
     */
    public static String findResponseRespondentName(RespondentSumType respondentSumType) {
        if (NO.equals(respondentSumType.getResponseRespondentNameQuestion())) {
            return respondentSumType.getResponseRespondentName();
        }
        return findRespondentNameByRespondentSumType(respondentSumType);
    }

    /**
     * Tries to find the respondent name by using respondent sum type. First check respondent sum type if it is blank
     * returns an empty string, if not, checks respondent name field, if it is not
     * blank, returns that field, if blank, checks respondent's organisation name if it is not blank returns
     * organisation name, if blank, checks both respondent first and last name and if they are not empty combines them
     * with a space and returns the new value, else returns empty string.
     * @param respondentSumType is the type which has the respondent's information that we use to find the name.
     * @return the name by according to the fields, respondent name, organisation name or first and last name.
     */
    public static String findRespondentNameByRespondentSumType(RespondentSumType respondentSumType) {
        if (ObjectUtils.isEmpty(respondentSumType)) {
            return StringUtils.EMPTY;
        }
        if (StringUtils.isNotBlank(respondentSumType.getRespondentName())) {
            return respondentSumType.getRespondentName();
        }
        if (StringUtils.isNotBlank(respondentSumType.getRespondentOrganisation())) {
            return respondentSumType.getRespondentOrganisation();
        }
        String respondentName = StringUtils.EMPTY;
        if (StringUtils.isNotBlank(respondentSumType.getRespondentFirstName())) {
            respondentName = respondentSumType.getRespondentFirstName();
        }
        if (StringUtils.isNotBlank(respondentSumType.getRespondentLastName())) {
            respondentName = respondentName + StringUtils.SPACE + respondentSumType.getRespondentLastName();
        }
        return respondentName.trim();
    }
}
