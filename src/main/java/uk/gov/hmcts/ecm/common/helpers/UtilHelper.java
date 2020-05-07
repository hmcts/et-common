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
        switch (caseTypeId) {
            case MANCHESTER_DEV_BULK_CASE_TYPE_ID:
                return MANCHESTER_DEV_CASE_TYPE_ID;
            case MANCHESTER_USERS_BULK_CASE_TYPE_ID:
                return MANCHESTER_USERS_CASE_TYPE_ID;
            case MANCHESTER_BULK_CASE_TYPE_ID:
                return MANCHESTER_CASE_TYPE_ID;
            case SCOTLAND_DEV_BULK_CASE_TYPE_ID:
                return SCOTLAND_DEV_CASE_TYPE_ID;
            case SCOTLAND_USERS_BULK_CASE_TYPE_ID:
                return SCOTLAND_USERS_CASE_TYPE_ID;
            case BRISTOL_DEV_BULK_CASE_TYPE_ID:
                return BRISTOL_DEV_CASE_TYPE_ID;
            case BRISTOL_USERS_BULK_CASE_TYPE_ID:
                return BRISTOL_USERS_CASE_TYPE_ID;
            case BRISTOL_BULK_CASE_TYPE_ID:
                return BRISTOL_CASE_TYPE_ID;
            case LEEDS_DEV_BULK_CASE_TYPE_ID:
                return LEEDS_DEV_CASE_TYPE_ID;
            case LEEDS_USERS_BULK_CASE_TYPE_ID:
                return LEEDS_USERS_CASE_TYPE_ID;
            case LEEDS_BULK_CASE_TYPE_ID:
                return LEEDS_CASE_TYPE_ID;
            case LONDON_CENTRAL_DEV_BULK_CASE_TYPE_ID:
                return LONDON_CENTRAL_DEV_CASE_TYPE_ID;
            case LONDON_CENTRAL_USERS_BULK_CASE_TYPE_ID:
                return LONDON_CENTRAL_USERS_CASE_TYPE_ID;
            case LONDON_CENTRAL_BULK_CASE_TYPE_ID:
                return LONDON_CENTRAL_CASE_TYPE_ID;
            case LONDON_EAST_DEV_BULK_CASE_TYPE_ID:
                return LONDON_EAST_DEV_CASE_TYPE_ID;
            case LONDON_EAST_USERS_BULK_CASE_TYPE_ID:
                return LONDON_EAST_USERS_CASE_TYPE_ID;
            case LONDON_EAST_BULK_CASE_TYPE_ID:
                return LONDON_EAST_CASE_TYPE_ID;
            case LONDON_SOUTH_DEV_BULK_CASE_TYPE_ID:
                return LONDON_SOUTH_DEV_CASE_TYPE_ID;
            case LONDON_SOUTH_USERS_BULK_CASE_TYPE_ID:
                return LONDON_SOUTH_USERS_CASE_TYPE_ID;
            case LONDON_SOUTH_BULK_CASE_TYPE_ID:
                return LONDON_SOUTH_CASE_TYPE_ID;
            case MIDLANDS_EAST_DEV_BULK_CASE_TYPE_ID:
                return MIDLANDS_EAST_DEV_CASE_TYPE_ID;
            case MIDLANDS_EAST_USERS_BULK_CASE_TYPE_ID:
                return MIDLANDS_EAST_USERS_CASE_TYPE_ID;
            case MIDLANDS_EAST_BULK_CASE_TYPE_ID:
                return MIDLANDS_EAST_CASE_TYPE_ID;
            case MIDLANDS_WEST_DEV_BULK_CASE_TYPE_ID:
                return MIDLANDS_WEST_DEV_CASE_TYPE_ID;
            case MIDLANDS_WEST_USERS_BULK_CASE_TYPE_ID:
                return MIDLANDS_WEST_USERS_CASE_TYPE_ID;
            case MIDLANDS_WEST_BULK_CASE_TYPE_ID:
                return MIDLANDS_WEST_CASE_TYPE_ID;
            case NEWCASTLE_DEV_BULK_CASE_TYPE_ID:
                return NEWCASTLE_DEV_CASE_TYPE_ID;
            case NEWCASTLE_USERS_BULK_CASE_TYPE_ID:
                return NEWCASTLE_USERS_CASE_TYPE_ID;
            case NEWCASTLE_BULK_CASE_TYPE_ID:
                return NEWCASTLE_CASE_TYPE_ID;
            case WALES_DEV_BULK_CASE_TYPE_ID:
                return WALES_DEV_CASE_TYPE_ID;
            case WALES_USERS_BULK_CASE_TYPE_ID:
                return WALES_USERS_CASE_TYPE_ID;
            case WALES_BULK_CASE_TYPE_ID:
                return WALES_CASE_TYPE_ID;
            case WATFORD_DEV_BULK_CASE_TYPE_ID:
                return WATFORD_DEV_CASE_TYPE_ID;
            case WATFORD_USERS_BULK_CASE_TYPE_ID:
                return WATFORD_USERS_CASE_TYPE_ID;
            case WATFORD_BULK_CASE_TYPE_ID:
                return WATFORD_CASE_TYPE_ID;
            default:
                return SCOTLAND_CASE_TYPE_ID;
        }
    }

    public static String getBulkCaseTypeId(String caseTypeId) {
        switch (caseTypeId) {
            case MANCHESTER_DEV_CASE_TYPE_ID:
                return MANCHESTER_DEV_BULK_CASE_TYPE_ID;
            case MANCHESTER_USERS_CASE_TYPE_ID:
                return MANCHESTER_USERS_BULK_CASE_TYPE_ID;
            case MANCHESTER_CASE_TYPE_ID:
                return MANCHESTER_BULK_CASE_TYPE_ID;
            case SCOTLAND_DEV_CASE_TYPE_ID:
                return SCOTLAND_DEV_BULK_CASE_TYPE_ID;
            case SCOTLAND_USERS_CASE_TYPE_ID:
                return SCOTLAND_USERS_BULK_CASE_TYPE_ID;
            case BRISTOL_DEV_CASE_TYPE_ID:
                return BRISTOL_DEV_BULK_CASE_TYPE_ID;
            case BRISTOL_USERS_CASE_TYPE_ID:
                return BRISTOL_USERS_BULK_CASE_TYPE_ID;
            case BRISTOL_CASE_TYPE_ID:
                return BRISTOL_BULK_CASE_TYPE_ID;
            case LEEDS_DEV_CASE_TYPE_ID:
                return LEEDS_DEV_BULK_CASE_TYPE_ID;
            case LEEDS_USERS_CASE_TYPE_ID:
                return LEEDS_USERS_BULK_CASE_TYPE_ID;
            case LEEDS_CASE_TYPE_ID:
                return LEEDS_BULK_CASE_TYPE_ID;
            case LONDON_CENTRAL_DEV_CASE_TYPE_ID:
                return LONDON_CENTRAL_DEV_BULK_CASE_TYPE_ID;
            case LONDON_CENTRAL_USERS_CASE_TYPE_ID:
                return LONDON_CENTRAL_USERS_BULK_CASE_TYPE_ID;
            case LONDON_CENTRAL_CASE_TYPE_ID:
                return LONDON_CENTRAL_BULK_CASE_TYPE_ID;
            case LONDON_EAST_DEV_CASE_TYPE_ID:
                return LONDON_EAST_DEV_BULK_CASE_TYPE_ID;
            case LONDON_EAST_USERS_CASE_TYPE_ID:
                return LONDON_EAST_USERS_BULK_CASE_TYPE_ID;
            case LONDON_EAST_CASE_TYPE_ID:
                return LONDON_EAST_BULK_CASE_TYPE_ID;
            case LONDON_SOUTH_DEV_CASE_TYPE_ID:
                return LONDON_SOUTH_DEV_BULK_CASE_TYPE_ID;
            case LONDON_SOUTH_USERS_CASE_TYPE_ID:
                return LONDON_SOUTH_USERS_BULK_CASE_TYPE_ID;
            case LONDON_SOUTH_CASE_TYPE_ID:
                return LONDON_SOUTH_BULK_CASE_TYPE_ID;
            case MIDLANDS_EAST_DEV_CASE_TYPE_ID:
                return MIDLANDS_EAST_DEV_BULK_CASE_TYPE_ID;
            case MIDLANDS_EAST_USERS_CASE_TYPE_ID:
                return MIDLANDS_EAST_USERS_BULK_CASE_TYPE_ID;
            case MIDLANDS_EAST_CASE_TYPE_ID:
                return MIDLANDS_EAST_BULK_CASE_TYPE_ID;
            case MIDLANDS_WEST_DEV_CASE_TYPE_ID:
                return MIDLANDS_WEST_DEV_BULK_CASE_TYPE_ID;
            case MIDLANDS_WEST_USERS_CASE_TYPE_ID:
                return MIDLANDS_WEST_USERS_BULK_CASE_TYPE_ID;
            case MIDLANDS_WEST_CASE_TYPE_ID:
                return MIDLANDS_WEST_BULK_CASE_TYPE_ID;
            case NEWCASTLE_DEV_CASE_TYPE_ID:
                return NEWCASTLE_DEV_BULK_CASE_TYPE_ID;
            case NEWCASTLE_USERS_CASE_TYPE_ID:
                return NEWCASTLE_USERS_BULK_CASE_TYPE_ID;
            case NEWCASTLE_CASE_TYPE_ID:
                return NEWCASTLE_BULK_CASE_TYPE_ID;
            case WALES_DEV_CASE_TYPE_ID:
                return WALES_DEV_BULK_CASE_TYPE_ID;
            case WALES_USERS_CASE_TYPE_ID:
                return WALES_USERS_BULK_CASE_TYPE_ID;
            case WALES_CASE_TYPE_ID:
                return WALES_BULK_CASE_TYPE_ID;
            case WATFORD_DEV_CASE_TYPE_ID:
                return WATFORD_DEV_BULK_CASE_TYPE_ID;
            case WATFORD_USERS_CASE_TYPE_ID:
                return WATFORD_USERS_BULK_CASE_TYPE_ID;
            case WATFORD_CASE_TYPE_ID:
                return WATFORD_BULK_CASE_TYPE_ID;
            default:
                return SCOTLAND_BULK_CASE_TYPE_ID;
        }
    }
}
