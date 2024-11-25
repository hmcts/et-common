package uk.gov.hmcts.ecm.common.service.pdf.et1;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import uk.gov.dwp.regex.InvalidPostcodeException;
import uk.gov.dwp.regex.PostCodeValidator;
import uk.gov.hmcts.et.common.model.ccd.Address;
import uk.gov.hmcts.et.common.model.ccd.CaseData;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Locale;
import java.util.Set;

import static uk.gov.hmcts.ecm.common.constants.PdfMapperConstants.LINE_FEED;
import static uk.gov.hmcts.ecm.common.constants.PdfMapperConstants.STRING_BLANK;
import static uk.gov.hmcts.ecm.common.constants.PdfMapperConstants.STRING_COMMA;
import static uk.gov.hmcts.ecm.common.constants.PdfMapperConstants.STRING_EMPTY;
import static uk.gov.hmcts.ecm.common.model.helper.Constants.YES;

/**
 *  This class is implemented as a utility for PDF Mapper class.
 *  All methods and variables are defined as static.
 *  There is a private constructor implemented not to let class have a
 *  public or default constructor because it is a utility class
 * @author Mehmet Tahir Dede
 * @version 1.0
 * @since 1.0
 */
@Slf4j
public final class PdfMapperServiceUtil {

    private static final Set<String> UK_COUNTRY_NAMES = Set.of("ENGLAND",
                                                               "SCOTLAND",
                                                               "NORTHERN IRELAND",
                                                               "NORTHERNIRELAND",
                                                               "WALES",
                                                               "UNITED KINGDOM",
                                                               "UK",
                                                               "UNITEDKINGDOM",
                                                               "GB",
                                                               "GREAT BRITAIN",
                                                               "GREATBRITAIN",
                                                               "BRITAIN");

    private PdfMapperServiceUtil() {
        // Utility classes should not have a public or default constructor.
    }

    /**
     * Returns boolean true when given countryName parameter is one of UK Countries
     * which are "ENGLAND", "SCOTLAND", "NORTHERN IRELAND" or "WALES".
     * <a href="https://en.wikipedia.org/wiki/Countries_of_the_United_Kingdom"> UK Countries</a>
     * @param countryName Name of the country
     * @return boolean true when UK, false when not UK country
     */
    private static boolean isUkCountry(String countryName) {
        return StringUtils.isNotEmpty(countryName) && UK_COUNTRY_NAMES.contains(countryName
                                                                                 .replace(" ", "")
                                                                                 .toUpperCase(Locale.UK)
                                                                                 .trim());
    }

    /**
     * Returns formatted value of given addressLine
     * It converts each address line wordings to capital letters.
     * Such as for given a value as 40 FURROW WAY it formats as 40 Furrow Way
     * @param addressLine Input value of address first line.
     * @return the formatted adressLine value
     */
    private static String convertFirstCharactersOfWordsToCapitalCase(String addressLine) {
        String[] addressLineWords = addressLine.toLowerCase(Locale.UK).split(" ");

        StringBuilder addressLineModified = new StringBuilder();
        for (String word : addressLineWords) {
            if (!StringUtils.isEmpty(word.trim())) {
                addressLineModified.append(word.substring(0, 1).toUpperCase(Locale.UK))
                    .append(word.substring(1)).append(' ');
            }
        }
        return addressLineModified.toString().trim();
    }

    /**
     * Returns a string value for the given Address. Address has 6 values to be converted to String
     * for showing them in PDF text fields.
     * 3 of those values which are AddressLine1, PostTown and Country are compulsory fields. If one or more
     * of those is not entered it returns null.
     * Adds all fields to a string by adding comma and new line to each field.
     * All fields are also converted to have each of their wordings start with a capital letter.
     * @param address model that holds address data
     * @return converted String value of address model.
     */
    public static String formatAddressForTextField(Address address) {
        StringBuilder addressValue = new StringBuilder();
        appendAddress(addressValue, address.getAddressLine1());
        appendAddress(addressValue, address.getAddressLine2());
        appendAddress(addressValue, address.getAddressLine3());
        appendAddress(addressValue, address.getPostTown());
        appendAddress(addressValue, address.getCounty());
        appendAddress(addressValue, address.getCountry());
        addressValue = ObjectUtils.isNotEmpty(addressValue)
                && StringUtils.isNotEmpty(addressValue)
                && addressValue.length() > 1
                && STRING_COMMA.equals(addressValue.substring(addressValue.length() - 1))
                ? new StringBuilder(addressValue.substring(0, addressValue.length() - 1))
                : addressValue;
        return StringUtils.isNotEmpty(addressValue.toString()) ? addressValue.toString() : null;
    }

    private static void appendAddress(StringBuilder addressValue, String appendValue) {
        if (StringUtils.isNotEmpty(appendValue)) {
            if (ObjectUtils.isNotEmpty(addressValue)
                    && StringUtils.isNotEmpty(addressValue.toString())) {
                addressValue.append(LINE_FEED);
            }
            addressValue.append(convertFirstCharactersOfWordsToCapitalCase(appendValue)).append(STRING_COMMA);
        }
    }

    /**
     * Removes all space characters in between all country's postcode characters.
     * If postcode is a postcode in the UK.
     * UK postcode has a space character before 3rd character of it's last character.
     * Such as SL63NY should be formatted as SL6 3NY or WF102SX as WF10 2SX etc...
     * @param address address model that holds address data
     * @return formatted String value of Postcode
     */
    public static String formatPostcode(Address address) {
        if (ObjectUtils.isEmpty(address) || StringUtils.isBlank(address.getPostCode())) {
            return STRING_EMPTY;
        }
        if (isUkCountry(address.getCountry())) {
            try {
                PostCodeValidator postCodeValidator = new PostCodeValidator(address.getPostCode());

                String outward = StringUtils.isNotBlank(postCodeValidator.returnOutwardCode())
                        ? postCodeValidator.returnOutwardCode().replace(STRING_BLANK, STRING_EMPTY).trim()
                        : STRING_EMPTY;
                String inward = StringUtils.isNotBlank(postCodeValidator.returnInwardCode())
                        ? postCodeValidator.returnInwardCode().replace(STRING_BLANK, STRING_EMPTY).trim()
                        : STRING_EMPTY;

                return outward + STRING_BLANK + inward;
            } catch (InvalidPostcodeException e) {
                GenericServiceUtil.logException("Exception occurred when formatting postcode " + address.getPostCode(),
                        "can not reach case reference number", e.getMessage(), "PdfMapperServiceUtil",
                        "formatUkPostcode");
                return address.getPostCode().replace(STRING_BLANK, STRING_EMPTY).trim();
            }
        } else {
            return address.getPostCode().replace(STRING_BLANK, STRING_EMPTY).trim();
        }
    }

    /**
     * Formats date from YYYY/MM/DD to DD/MM/YYYY.
     * @param dateToFormat String value of date to be formatted
     * @return Formatted date
     */
    public static String formatDate(String dateToFormat) {
        SimpleDateFormat parsingFormatter = new SimpleDateFormat("yyyy-MM-dd", Locale.UK);
        SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy", Locale.UK);
        String formattedDateStringValue;
        try {
            formattedDateStringValue = dateToFormat == null ? "" :
                formatter.format(parsingFormatter.parse(dateToFormat));
        } catch (ParseException e) {
            return dateToFormat;
        }
        return formattedDateStringValue;
    }

    /**
     * Generates claimant compensation value according to given compensation text and amount.
     * If claimant compensation text is null, blank or just ":" sets compensation text to blank string
     * If claimant compensation amount is null, blank or just ":"  sets compensation amount to blank string
     * If claimant compensation text exists adds text to claimant compensation
     * If claimant compensation amount exists adds text to claimant compensation
     * @param caseData uses claimantReqest, claimantCompensationText and claimantCompensationAmount
     * @return claimantCompensation as a text field.
     */
    public static String generateClaimantCompensation(CaseData caseData) {

        String claimantCompensation = "";

        if (caseData != null && caseData.getClaimantRequests() != null) {
            String claimantCompensationText =
                StringUtils.stripToEmpty(caseData.getClaimantRequests().getClaimantCompensationText());

            claimantCompensationText = ":".equals(claimantCompensationText) ? "" : claimantCompensationText;
            String claimantCompensationAmount =
                StringUtils.stripToEmpty(caseData.getClaimantRequests().getClaimantCompensationAmount())
                    .replace(":", "");
            claimantCompensationAmount = StringUtils.isBlank(claimantCompensationAmount) ? "" :
                "Amount requested: £" + claimantCompensationAmount;

            claimantCompensation =
                StringUtils.isNotBlank(claimantCompensationText) ? claimantCompensationText : "";

            claimantCompensation = addClaimantCompensationAmount(claimantCompensation, claimantCompensationAmount);

            claimantCompensation = StringUtils.isBlank(claimantCompensation) ? "" :
                "Compensation:\"" + claimantCompensation + "\"" + System.lineSeparator() + System.lineSeparator();
        }
        return claimantCompensation;
    }

    private static String addClaimantCompensationAmount(String claimantCompensation,
                                                        String claimantCompensationAmount) {
        String tmpClaimantCompensation;
        if (StringUtils.isNotBlank(claimantCompensation) && StringUtils.isNotBlank(claimantCompensationAmount)) {
            tmpClaimantCompensation = claimantCompensation + "\n" + claimantCompensationAmount;
        } else {
            if (StringUtils.isNotBlank(claimantCompensationAmount)) {
                tmpClaimantCompensation = claimantCompensationAmount;
            } else {
                tmpClaimantCompensation = claimantCompensation;
            }
        }
        return tmpClaimantCompensation;
    }

    /**
     * Generates claimant tribunal recommendation value according to given claimant tribunal recommendation.
     * If claimant tribunal recommendation value is null returns an empty string
     * If claimant tribunal recommendation value exists returns this value by adding Tribunal recommendation prefix
     * @param caseData uses claimantRequests claimantTribunalRecommendation
     * @return claimantTribunalRecommendation not null value
     */
    public static String generateClaimantTribunalRecommendation(CaseData caseData) {
        String claimantTribunalRecommendation = "";
        if (caseData != null && caseData.getClaimantRequests() != null) {
            claimantTribunalRecommendation =
                StringUtils.stripToEmpty(caseData.getClaimantRequests().getClaimantTribunalRecommendation());
            if (StringUtils.isNotBlank(claimantTribunalRecommendation)) {
                claimantTribunalRecommendation = "Tribunal recommendation:\"" + claimantTribunalRecommendation + "\"";
            }
        }
        return claimantTribunalRecommendation;
    }

    public static boolean isYes(String stringValue) {
        return YES.equalsIgnoreCase(stringValue);
    }
}
