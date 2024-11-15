package uk.gov.hmcts.ecm.common.service.pdf.et3.util;

import com.google.gson.Gson;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.ObjectUtils;
import uk.gov.hmcts.et.common.model.ccd.Address;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Optional;
import java.util.concurrent.ConcurrentMap;

import static java.util.Optional.of;
import static org.apache.commons.lang3.StringUtils.isBlank;
import static org.apache.commons.lang3.StringUtils.isNotBlank;
import static uk.gov.hmcts.ecm.common.service.pdf.et3.ET3FormConstants.OFF_CAPITALISED;
import static uk.gov.hmcts.ecm.common.service.pdf.et3.ET3FormConstants.STRING_COMMA_WITH_SPACE;
import static uk.gov.hmcts.ecm.common.service.pdf.et3.ET3FormConstants.STRING_EMPTY;
import static uk.gov.hmcts.ecm.common.service.pdf.et3.ET3FormConstants.STRING_LINE_FEED;
import static uk.gov.hmcts.ecm.common.service.pdf.et3.ET3FormConstants.STRING_SPACE;

public final class ET3FormUtil {

    private ET3FormUtil() {
        // Utility classes should not have a public or default constructor.
    }

    /**
     * Formats date from YYYY/MM/DD to DD/MM/YYYY.
     * @param dateToFormat String value of date to be formatted
     * @return Formatted date
     */
    public static String formatDate(String dateToFormat, String existingDateFormat, String conversionFormat) {
        if (isBlank(dateToFormat)) {
            return STRING_EMPTY;
        }
        String formattedDateStringValue;
        try {
            formattedDateStringValue =  formatDateToString(formatStringToDate(dateToFormat, existingDateFormat),
                    conversionFormat);
        } catch (ParseException e) {
            return dateToFormat;
        }
        return formattedDateStringValue;
    }

    private static Date formatStringToDate(String stringValue, String format)
            throws ParseException {
        return new SimpleDateFormat(format, Locale.UK).parse(stringValue);
    }

    private static String formatDateToString(Date dateValue, String format) {
        return new SimpleDateFormat(format, Locale.UK).format(dateValue);
    }

    /**
     * Adds a new field to pdf form fields.
     * @param pdfFields map for mapping pdf fields with case data
     * @param fieldName name of the field in the pdf file
     * @param value value of the field in the case data
     */
    public static void putPdfTextField(ConcurrentMap<String, Optional<String>> pdfFields,
                                       String fieldName,
                                       String value) {
        if (isBlank(fieldName)) {
            return;
        }
        if (isBlank(value)) {
            pdfFields.put(fieldName, of(STRING_EMPTY));
            return;
        }
        pdfFields.put(fieldName, of(value));
    }

    /**
     * Adds a new field to pdf form fields when expected value equals to actual value and
     * fieldname, expectedValue, actualValue and valurToPut parameters are not blank.
     * @param pdfFields map for mapping pdf fields with case data
     * @param fieldName name of the field in the pdf file
     * @param expectedValue value to check with actual value of the condition
     * @param actualValue value to check with expected value of the condition
     * @param valueToPut value to add pdf fields
     */
    public static void putConditionalPdfField(ConcurrentMap<String, Optional<String>> pdfFields,
                                              String fieldName,
                                              String expectedValue,
                                              String actualValue,
                                              String valueToPut) {
        if (isBlank(fieldName)) {
            return;
        }
        if (isBlank(expectedValue) || isBlank(actualValue) || isBlank(valueToPut)
                || !expectedValue.equalsIgnoreCase(actualValue)) {
            pdfFields.put(fieldName, of(STRING_EMPTY));
            return;
        }
        pdfFields.put(fieldName, of(valueToPut));
    }

    /**
     * Adds a new field to pdf form fields when expected value equals to actual value and
     * fieldname, expectedValue, actualValue and valurToPut parameters are not blank. Else puts Off value to checkboxes
     * @param pdfFields map for mapping pdf fields with case data
     * @param fieldName name of the field in the pdf file
     * @param expectedValue value to check with actual value of the condition
     * @param actualValue value to check with expected value of the condition
     * @param valueToPut value to add pdf fields
     */
    public static void putConditionalPdfCheckboxField(ConcurrentMap<String, Optional<String>> pdfFields,
                                                      String fieldName,
                                                      String expectedValue,
                                                      String actualValue,
                                                      String valueToPut) {
        if (isBlank(fieldName)) {
            return;
        }
        if (isNotBlank(expectedValue)
                && isNotBlank(actualValue)
                && isNotBlank(valueToPut)
                && expectedValue.equalsIgnoreCase(actualValue)) {
            pdfFields.put(fieldName, of(valueToPut));
            return;
        }
        pdfFields.put(fieldName, of(OFF_CAPITALISED));

    }

    /**
     * Puts checkbox field value to the pdf fields map. Checks field name, actual value, expected value, and
     * check value if they are empty or not. If not empty checks if expected value equals to the actual value.
     * If check passes it fills the checkbox with the check value to check it.
     * @param pdfFields map for mapping pdf fields with case data
     * @param fieldName name of the field in the pdf file
     * @param expectedValue value that will be checked with the actual value to check the checkbox
     * @param checkValue value that is required to set checkbox checked
     * @param actualValue value that will be checked with the expected value to check the checkbox
     */
    public static void putPdfCheckboxFieldWhenExpectedValueEqualsActualValue(
            ConcurrentMap<String, Optional<String>> pdfFields,
            String fieldName,
            String checkValue,
            String expectedValue,
            String actualValue) {
        if (isBlank(fieldName)) {
            return;
        }
        if (isBlank(checkValue)
                || isBlank(expectedValue)
                || isBlank(actualValue)
                || !expectedValue.equalsIgnoreCase(actualValue)) {
            pdfFields.put(fieldName, of(OFF_CAPITALISED));
        } else {
            pdfFields.put(fieldName, of(checkValue));
        }
    }

    /**
     * Puts checkbox field value to the pdf fields map. Checks field name, actual value, expected value List, and
     * check value if they are empty or not. If passes empty checks, checks if actual value not exists in
     * expected value list. If check passes it fills the checkbox with the check value to check it.
     * If both expected value list and actual value are blank then fills the checkbox with the check value.
     * @param pdfFields map for mapping pdf fields with case data
     * @param fieldName name of the field in the pdf file
     * @param expectedValueList value list that will be checked with the actual value to check the checkbox
     * @param checkValue value that is required to set checkbox checked
     * @param actualValue value that will be checked with the expected value to check the checkbox
     */
    public static void putPdfCheckboxFieldWhenOther(
            ConcurrentMap<String, Optional<String>> pdfFields,
            String fieldName,
            String checkValue,
            List<String> expectedValueList,
            String actualValue) {
        if (isBlank(fieldName)) {
            return;
        }
        if (isBlank(checkValue)) {
            pdfFields.put(fieldName, of(OFF_CAPITALISED));
            return;
        }
        if (CollectionUtils.isEmpty(expectedValueList) || isBlank(actualValue)) {
            pdfFields.put(fieldName, of(checkValue));
            return;
        }
        if (expectedValueList.contains(actualValue)) {
            pdfFields.put(fieldName, of(OFF_CAPITALISED));
            return;
        }
        pdfFields.put(fieldName, of(checkValue));
    }

    /**
     * Puts checkbox field value to the pdf fields map. Checks field name, actual value, expected value, and
     * check value if they are empty or not. If not empty checks if actual value contains expected value.
     * If all checks pass it fills the checkbox with the check value to check it.
     * @param pdfFields map for mapping pdf fields with case data
     * @param fieldName name of the field in the pdf file
     * @param expectedValue value that will be checked with the actual value to check the checkbox
     * @param checkValue value that is required to set checkbox checked
     * @param actualValue value that will be checked with the expected value to check the checkbox
     */
    public static void putPdfCheckboxFieldWhenActualValueContainsExpectedValue(
            ConcurrentMap<String, Optional<String>> pdfFields,
            String fieldName,
            String checkValue,
            String expectedValue,
            List<String> actualValue) {
        if (isBlank(fieldName)) {
            return;
        }
        if (isBlank(checkValue)
                || isBlank(expectedValue)
                || CollectionUtils.isEmpty(actualValue)
                || !actualValue.contains(expectedValue)) {
            pdfFields.put(fieldName, of(STRING_EMPTY));
        } else {
            pdfFields.put(fieldName, of(checkValue));
        }
    }

    /**
     * Puts address into pdf field with the given address values. Replaces blank address fields with empty string
     *  and adds a blank character to not blank address fields. After organising address fields, concatenates them.
     * @param pdfFields map for mapping pdf fields with case data
     * @param fieldName name of the field in the pdf file
     * @param address address to put into the pdf field
     */
    public static void putPdfAddressField(ConcurrentMap<String, Optional<String>> pdfFields,
                                          String fieldName,
                                          Address address) {
        if (isBlank(fieldName)) {
            return;
        }
        if (ObjectUtils.isEmpty(address)) {
            putPdfTextField(pdfFields, fieldName, STRING_EMPTY);
            return;
        }
        String addressLineValue1 = addPrefixToNotBlankString(address.getAddressLine1(), STRING_EMPTY)
                + addPrefixToNotBlankString(address.getAddressLine2(),
                isNotBlank(address.getAddressLine1()) ? STRING_SPACE : STRING_EMPTY);
        String addressLineValue = addressLineValue1 + addPrefixToNotBlankString(address.getAddressLine3(),
                isNotBlank(addressLineValue1) ? STRING_SPACE : STRING_EMPTY);
        String addressPostTownCountyValue =  addPrefixToNotBlankString(address.getPostTown(), STRING_EMPTY)
                + addPrefixToNotBlankString(address.getCounty(),
                isNotBlank(address.getPostTown()) ? STRING_COMMA_WITH_SPACE : STRING_EMPTY);
        String addressLinePostTownCountyValue = addressLineValue + addPrefixToNotBlankString(addressPostTownCountyValue,
                isNotBlank(addressLineValue) ? STRING_LINE_FEED : STRING_EMPTY);
        String addressValue = addressLinePostTownCountyValue + addPrefixToNotBlankString(address.getCountry(),
                isNotBlank(addressLinePostTownCountyValue) ? STRING_LINE_FEED : STRING_EMPTY);
        putPdfTextField(pdfFields, fieldName, addressValue);
    }

    /**
     * This method replaces blank strings with string empty and adds postfix value at the end of the not blank
     * string values.
     * @param stringValue string value to be modified
     * @param prefix prefix value to be added at the end of the string value
     * @return replaced string value
     */
    private static String addPrefixToNotBlankString(String stringValue, String prefix) {
        return isNotBlank(stringValue) ? prefix + stringValue : STRING_EMPTY;
    }

    /**
     * Clones (creates a new instance) of any instance of any class.
     * @param object object to be cloned
     * @param classType class of the object to be cloned
     * @param <T> generics type of the object
     * @return  cloned object
     */
    public static <T> T cloneObject(T object, Class<T> classType) {
        Gson gson = new Gson();
        return classType.cast(gson.fromJson(gson.toJson(object), object.getClass()));
    }
}
