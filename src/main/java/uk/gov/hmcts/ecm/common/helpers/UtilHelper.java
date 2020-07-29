package uk.gov.hmcts.ecm.common.helpers;

import lombok.extern.slf4j.Slf4j;
import java.time.LocalDate;
import java.time.LocalDateTime;
import static com.google.common.base.Strings.isNullOrEmpty;
import static uk.gov.hmcts.ecm.common.model.helper.Constants.*;

@Slf4j
public class UtilHelper {

    public static String formatLocalDate(String date) {
        return !isNullOrEmpty(date) ? LocalDate.parse(date, OLD_DATE_TIME_PATTERN).format(NEW_DATE_PATTERN) : "";
    }

    public static String listingFormatLocalDate(String date) {
        return !isNullOrEmpty(date) ? LocalDate.parse(date, OLD_DATE_TIME_PATTERN2).format(NEW_DATE_PATTERN) : "";
    }

    public static String formatLocalDateTime(String date) {
        return !isNullOrEmpty(date) ? LocalDateTime.parse(date, OLD_DATE_TIME_PATTERN).format(NEW_DATE_TIME_PATTERN) : "";
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
        String office;
        if (caseTypeId.contains(MULTIPLES_DEV)) {
            office = caseTypeId.substring(0, caseTypeId.lastIndexOf(MULTIPLES_DEV));
            return office + DEV;
        } else {
            if (caseTypeId.contains(MULTIPLES_USER)) {
                office = caseTypeId.substring(0, caseTypeId.lastIndexOf(MULTIPLES_USER));
                return office + USER;
            } else {
                return caseTypeId.substring(0, caseTypeId.lastIndexOf(MULTIPLES));
            }
        }
    }

    public static String getBulkCaseTypeId(String caseTypeId) {
        String office;
        if (caseTypeId.contains(DEV)) {
            office = caseTypeId.substring(0, caseTypeId.lastIndexOf(DEV));
            return office + MULTIPLES_DEV;
        } else {
            if (caseTypeId.contains(USER)) {
                office = caseTypeId.substring(0, caseTypeId.lastIndexOf(USER));
                return office + MULTIPLES_USER;
            } else {
                return caseTypeId + MULTIPLES;
            }
        }
    }

    public static String getSingleCaseTypeFromMultiple(String multipleCaseTypeId) {
        int lastIndex = multipleCaseTypeId.lastIndexOf(MULTIPLE);
        return multipleCaseTypeId.substring(0, lastIndex);
    }
}
