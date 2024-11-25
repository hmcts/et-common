package uk.gov.hmcts.ecm.common.service.pdf.et3.util;

import org.springframework.util.CollectionUtils;
import uk.gov.hmcts.ecm.common.service.pdf.et3.ET3FormConstants;
import uk.gov.hmcts.et.common.model.ccd.CaseData;
import uk.gov.hmcts.et.common.model.ccd.items.RepresentedTypeRItem;
import uk.gov.hmcts.et.common.model.ccd.types.RepresentedTypeR;

import java.util.Optional;
import java.util.stream.Stream;

import static org.apache.commons.lang3.ObjectUtils.isEmpty;
import static org.apache.commons.lang3.ObjectUtils.isNotEmpty;
import static org.apache.commons.lang3.StringUtils.isBlank;
import static uk.gov.hmcts.ecm.common.constants.PdfMapperConstants.CHARACTER_DOT;
import static uk.gov.hmcts.ecm.common.constants.PdfMapperConstants.CURRENCY_DECIMAL_ZERO_WITH_DOT;
import static uk.gov.hmcts.ecm.common.constants.PdfMapperConstants.ONE;
import static uk.gov.hmcts.ecm.common.constants.PdfMapperConstants.STRING_ZERO;
import static uk.gov.hmcts.ecm.common.constants.PdfMapperConstants.TWO;
import static uk.gov.hmcts.ecm.common.constants.PdfMapperConstants.ZERO;

public final class ET3MapperUtil {

    private ET3MapperUtil() {
        // Utility classes should not have a public or default constructor.
    }

    /**
     * Converts given value to currency. Value is coming from frontend with extra 2 ZEROs. That is why
     * we are adding a dot(.) in between last 2 digits to format it as currency.
     * Code checks if the entered value has any (.). If it has returns the value and checks if the value's length is
     * less than 2. If it is less than 2 returns the value adding .00, If value is empty then returns an empty string.
     * @param value entered by user but added extra 2 zeros.
     * @return string that has a dot(.) before the ending 2 zeros.
     */
    public static String correctCurrency(String value) {
        if (isBlank(value)) {
            return ET3FormConstants.STRING_EMPTY;
        }
        if (value.indexOf(CHARACTER_DOT) == ZERO) {
            return STRING_ZERO + value;
        }
        if (value.indexOf(CHARACTER_DOT) >= ONE) {
            return value;
        }
        if (value.length() < TWO) {
            return value + CURRENCY_DECIMAL_ZERO_WITH_DOT;
        }
        return value.substring(0, value.length() - 2) + CHARACTER_DOT
                + value.substring(value.length() - TWO);
    }

    public static RepresentedTypeR findRepresentativeFromCaseData(CaseData caseData) {
        if (isCaseDataRespondentEmpty(caseData)) {
            return null;
        }
        //TODO change this with an if clause -- if representative or respondent...
        Stream<RepresentedTypeRItem> selectedRepresentativeStream = caseData.getRepCollection().stream().filter(
                rep -> isNotEmpty(rep.getValue()) && rep.getValue().getRespRepName().equals(
                        caseData.getSubmitEt3Respondent().getSelectedLabel()
                )
        );
        if (isEmpty(selectedRepresentativeStream)) {
            return null;
        }
        Optional<RepresentedTypeRItem> selectedRepresentative = selectedRepresentativeStream.findFirst();
        if (selectedRepresentative.isEmpty()
                || isEmpty(selectedRepresentative.get())
                || isEmpty(selectedRepresentative.get().getValue())) {
            return null;
        }
        return selectedRepresentative.get().getValue();
    }

    private static boolean isCaseDataRespondentEmpty(CaseData caseData) {
        return isEmpty(caseData) || CollectionUtils.isEmpty(caseData.getRepCollection())
                || isEmpty(caseData.getSubmitEt3Respondent())
                || isBlank(caseData.getSubmitEt3Respondent().getSelectedLabel());
    }
}
