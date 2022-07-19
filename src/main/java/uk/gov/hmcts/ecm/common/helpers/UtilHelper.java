package uk.gov.hmcts.ecm.common.helpers;

import lombok.extern.slf4j.Slf4j;
import java.time.LocalDate;
import java.time.LocalDateTime;

import static com.google.common.base.Strings.isNullOrEmpty;
import static uk.gov.hmcts.ecm.common.model.helper.Constants.DEV;
import static uk.gov.hmcts.ecm.common.model.helper.Constants.LISTINGS;
import static uk.gov.hmcts.ecm.common.model.helper.Constants.LISTINGS_DEV;
import static uk.gov.hmcts.ecm.common.model.helper.Constants.LISTINGS_USER;
import static uk.gov.hmcts.ecm.common.model.helper.Constants.MULTIPLE;
import static uk.gov.hmcts.ecm.common.model.helper.Constants.MULTIPLES;
import static uk.gov.hmcts.ecm.common.model.helper.Constants.MULTIPLES_DEV;
import static uk.gov.hmcts.ecm.common.model.helper.Constants.MULTIPLES_USER;
import static uk.gov.hmcts.ecm.common.model.helper.Constants.MULTIPLE_DEV;
import static uk.gov.hmcts.ecm.common.model.helper.Constants.MULTIPLE_USER;
import static uk.gov.hmcts.ecm.common.model.helper.Constants.NEW_DATE_PATTERN;
import static uk.gov.hmcts.ecm.common.model.helper.Constants.NEW_DATE_TIME_PATTERN;
import static uk.gov.hmcts.ecm.common.model.helper.Constants.NEW_TIME_PATTERN;
import static uk.gov.hmcts.ecm.common.model.helper.Constants.OLD_DATE_TIME_PATTERN;
import static uk.gov.hmcts.ecm.common.model.helper.Constants.OLD_DATE_TIME_PATTERN2;
import static uk.gov.hmcts.ecm.common.model.helper.Constants.USER;

@Slf4j
public final class UtilHelper {

    private UtilHelper() {
        // private constructor required for utility class
    }

    public static String formatLocalDate(String date) {
        return !isNullOrEmpty(date) ? LocalDate.parse(date, OLD_DATE_TIME_PATTERN).format(NEW_DATE_PATTERN) : "";
    }

    public static String listingFormatLocalDate(String date) {
        return !isNullOrEmpty(date) ? LocalDate.parse(date, OLD_DATE_TIME_PATTERN2).format(NEW_DATE_PATTERN) : "";
    }

    public static String formatLocalDateTime(String date) {
        return !isNullOrEmpty(date) ? LocalDateTime.parse(
                date, OLD_DATE_TIME_PATTERN).format(NEW_DATE_TIME_PATTERN) : "";
    }

    public static String formatLocalTime(String date) {
        return !isNullOrEmpty(date) ? LocalDateTime.parse(date, OLD_DATE_TIME_PATTERN).format(NEW_TIME_PATTERN) : "";
    }

    public static String formatCurrentDatePlusDays(LocalDate date, long days) {
        return !isNullOrEmpty(date.toString()) ? date.plusDays(days).format(NEW_DATE_PATTERN) : "";
    }

    public static String formatCurrentDate(LocalDate date) {
        return !isNullOrEmpty(date.toString()) ? date.format(NEW_DATE_PATTERN) : "";
    }

    public static String formatCurrentDate2(LocalDate date) {
        return !isNullOrEmpty(date.toString()) ? date.format(OLD_DATE_TIME_PATTERN2) : "";
    }

    public static String getCaseTypeId(String caseTypeId) {
        if (caseTypeId.contains(MULTIPLES_DEV)) {
            return getSingleOffice(caseTypeId, MULTIPLES_DEV, DEV);
        } else {
            if (caseTypeId.contains(MULTIPLE_DEV)) {
                return getSingleOffice(caseTypeId, MULTIPLE_DEV, DEV);
            } else {
                if (caseTypeId.contains(MULTIPLES_USER)) {
                    return getSingleOffice(caseTypeId, MULTIPLES_USER, USER);
                } else {
                    if (caseTypeId.contains(MULTIPLE_USER)) {
                        return getSingleOffice(caseTypeId, MULTIPLE_USER, USER);
                    } else {
                        if (caseTypeId.contains(MULTIPLES)) {
                            return getSingleOffice(caseTypeId, MULTIPLES, "");
                        } else {
                            return getSingleOffice(caseTypeId, MULTIPLE, "");
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
        if (caseTypeId.contains(DEV)) {
            office = caseTypeId.substring(0, caseTypeId.lastIndexOf(DEV));
            return office + MULTIPLE_DEV;
        } else {
            if (caseTypeId.contains(USER)) {
                office = caseTypeId.substring(0, caseTypeId.lastIndexOf(USER));
                return office + MULTIPLE_USER;
            } else {
                return caseTypeId + MULTIPLE;
            }
        }
    }

    public static String getListingCaseTypeId(String caseTypeId) {
        if (caseTypeId.contains(LISTINGS_DEV)) {
            return getSingleOffice(caseTypeId, LISTINGS_DEV, DEV);
        } else {
            if (caseTypeId.contains(LISTINGS_USER)) {
                return getSingleOffice(caseTypeId, LISTINGS_USER, USER);
            } else {
                return getSingleOffice(caseTypeId, LISTINGS, "");
            }
        }
    }

}
