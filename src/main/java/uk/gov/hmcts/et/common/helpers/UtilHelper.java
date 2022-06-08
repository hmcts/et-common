package uk.gov.hmcts.et.common.helpers;

import lombok.extern.slf4j.Slf4j;
import uk.gov.hmcts.et.common.model.helper.Constants;

import java.time.LocalDate;
import java.time.LocalDateTime;

import static com.google.common.base.Strings.isNullOrEmpty;

@Slf4j
public class UtilHelper {

    public static String formatLocalDate(String date) {
        return !isNullOrEmpty(date) ? LocalDate.parse(date, Constants.OLD_DATE_TIME_PATTERN).format(Constants.NEW_DATE_PATTERN) : "";
    }

    public static String listingFormatLocalDate(String date) {
        return !isNullOrEmpty(date) ? LocalDate.parse(date, Constants.OLD_DATE_TIME_PATTERN2).format(Constants.NEW_DATE_PATTERN) : "";
    }

    public static String formatLocalDateTime(String date) {
        return !isNullOrEmpty(date) ?
                LocalDateTime.parse(date, Constants.OLD_DATE_TIME_PATTERN).format(Constants.NEW_DATE_TIME_PATTERN) : "";
    }

    public static String formatLocalTime(String date) {
        return !isNullOrEmpty(date) ? LocalDateTime.parse(date, Constants.OLD_DATE_TIME_PATTERN).format(Constants.NEW_TIME_PATTERN) : "";
    }

    public static String formatCurrentDatePlusDays(LocalDate date, long days) {
        return !isNullOrEmpty(date.toString()) ? date.plusDays(days).format(Constants.NEW_DATE_PATTERN) : "";
    }

    public static String formatCurrentDate(LocalDate date) {
        return !isNullOrEmpty(date.toString()) ? date.format(Constants.NEW_DATE_PATTERN) : "";
    }

    public static String formatCurrentDate2(LocalDate date) {
        return !isNullOrEmpty(date.toString()) ? date.format(Constants.OLD_DATE_TIME_PATTERN2) : "";
    }

    public static String getCaseTypeId(String caseTypeId) {
        if (caseTypeId.contains(Constants.MULTIPLES_DEV)) {
            return getSingleOffice(caseTypeId, Constants.MULTIPLES_DEV, Constants.DEV);
        } else {
            if (caseTypeId.contains(Constants.MULTIPLE_DEV)) {
                return getSingleOffice(caseTypeId, Constants.MULTIPLE_DEV, Constants.DEV);
            } else {
                if (caseTypeId.contains(Constants.MULTIPLES_USER)) {
                    return getSingleOffice(caseTypeId, Constants.MULTIPLES_USER, Constants.USER);
                } else {
                    if (caseTypeId.contains(Constants.MULTIPLE_USER)) {
                        return getSingleOffice(caseTypeId, Constants.MULTIPLE_USER, Constants.USER);
                    } else {
                        if (caseTypeId.contains(Constants.MULTIPLES)) {
                            return getSingleOffice(caseTypeId, Constants.MULTIPLES, "");
                        } else {
                            return getSingleOffice(caseTypeId, Constants.MULTIPLE, "");
                        }
                    }
                }
            }
        }
    }

    private static String getSingleOffice(String caseTypeId, String caseTypeIdSearch, String user) {
        String office = caseTypeId.substring(0, caseTypeId.lastIndexOf(caseTypeIdSearch));
        return office + user;
    }

    public static String getBulkCaseTypeId(String caseTypeId) {
        String office;
        if (caseTypeId.contains(Constants.DEV)) {
            office = caseTypeId.substring(0, caseTypeId.lastIndexOf(Constants.DEV));
            return office + Constants.MULTIPLE_DEV;
        } else {
            if (caseTypeId.contains(Constants.USER)) {
                office = caseTypeId.substring(0, caseTypeId.lastIndexOf(Constants.USER));
                return office + Constants.MULTIPLE_USER;
            } else {
                return caseTypeId + Constants.MULTIPLE;
            }
        }
    }

    public static String getListingCaseTypeId(String caseTypeId) {
        if (caseTypeId.contains(Constants.LISTINGS_DEV)) {
            return getSingleOffice(caseTypeId, Constants.LISTINGS_DEV, Constants.DEV);
        } else {
            if (caseTypeId.contains(Constants.LISTINGS_USER)) {
                return getSingleOffice(caseTypeId, Constants.LISTINGS_USER, Constants.USER);
            } else {
                return getSingleOffice(caseTypeId, Constants.LISTINGS, "");
            }
        }
    }

}
