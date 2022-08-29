package uk.gov.hmcts.et.common.helpers;

import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.MatchQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.query.RangeQueryBuilder;
import org.elasticsearch.index.query.TermsQueryBuilder;
import org.elasticsearch.search.builder.SearchSourceBuilder;

import java.util.List;
import java.util.stream.Collectors;

import static org.elasticsearch.index.query.QueryBuilders.boolQuery;
import static org.elasticsearch.index.query.QueryBuilders.termsQuery;
import uk.gov.hmcts.et.common.model.helper.Constants;

@Slf4j
public class ESHelper {

    private static final String ETHOS_CASE_REFERENCE_KEYWORD = "data.ethosCaseReference.keyword";
    private static final String MULTIPLE_CASE_REFERENCE_KEYWORD = "data.multipleReference.keyword";
    private static final String LISTING_DATE_FIELD_NAME =
            "data.hearingCollection.value.hearingDateCollection.value.listedDate";
    private static final String RECEIPT_DATE_FIELD_NAME = "data.receiptDate";
    public static final String LISTING_VENUE_FIELD_NAME =
            "data.hearingCollection.value.hearingDateCollection.value.hearingVenueDay.value.code.keyword";
    public static final String BROUGHT_FORWARD_DATE_FIELD_NAME = "data.bfActions.value.bfDate";
    public static final String CLAIMS_ACCEPTED_DATE_FIELD_NAME = "data.preAcceptCase.dateAccepted";
    public static final String MANAGING_OFFICE_FIELD_NAME = "data.managingOffice";
    private static final String REPORT_TYPE_NOT_FOUND = "Report type not found";
    public static final String CLAIMS_SERVED_DATE_FIELD_NAME = "data.claimServedDate";
    public static final String LISTING_GLASGOW_VENUE_FIELD_NAME =
            "data.hearingCollection.value.hearingDateCollection.value.Hearing_Glasgow.value.code.keyword";
    public static final String LISTING_ABERDEEN_VENUE_FIELD_NAME =
            "data.hearingCollection.value.hearingDateCollection.value.Hearing_Aberdeen.value.code.keyword";
    public static final String LISTING_DUNDEE_VENUE_FIELD_NAME =
            "data.hearingCollection.value.hearingDateCollection.value.Hearing_Dundee.value.code.keyword";
    public static final String LISTING_EDINBURGH_VENUE_FIELD_NAME =
            "data.hearingCollection.value.hearingDateCollection.value.Hearing_Edinburgh.value.code.keyword";
    public static final String MEMBER_DAYS_DATE_FIELD_NAME =
        "data.hearingCollection.value.hearingDateCollection.value.listedDate";

    private ESHelper() {
        // All access through static methods
    }

    public static String getSearchQuery(List<String> caseIds) {
        TermsQueryBuilder termsQueryBuilder = termsQuery(ETHOS_CASE_REFERENCE_KEYWORD, caseIds);
        return new SearchSourceBuilder()
                .size(Constants.MAX_ES_SIZE)
                //.fetchSource(new String[]{"data"}, null)
                .query(termsQueryBuilder).toString();
    }

    public static String getSearchQuerySchedule(List<String> caseIds) {
        String cases = caseIds.stream()
                .map(s -> "\"" + s + "\"")
                .collect(Collectors.joining(","));

        return String.format("{\"size\":%s,"
                        + "\"query\":{\"terms\":{\"%s\":[%s],\"boost\":1.0}},"
                        + "\"_source\":["
                        + "\"data.claimantIndType.*\","
                        + "\"data.claimantType.claimant_addressUK.*\","
                        + "\"data.claimant_Company\","
                        + "\"data.positionType\","
                        + "\"data.ethosCaseReference\","
                        + "\"data.respondentCollection.*\"]}",
                Constants.MAX_ES_SIZE / 2, ETHOS_CASE_REFERENCE_KEYWORD, cases);
    }

    public static String getSearchQueryLabels(List<String> caseIds) {
        String cases = caseIds.stream()
                .map(s -> "\"" + s + "\"")
                .collect(Collectors.joining(","));

        return String.format("{\"size\":%s,"
                        + "\"query\":{\"terms\":{\"%s\":[%s],\"boost\":1.0}},"
                        + "\"_source\":["
                        + "\"data.claimantIndType.*\","
                        + "\"data.claimantType.*\","
                        + "\"data.claimant_TypeOfClaimant\","
                        + "\"data.claimant_Company\","
                        + "\"data.representativeClaimantType.*\","
                        + "\"data.claimantRepresentedQuestion\","
                        + "\"data.respondentCollection.*\","
                        + "\"data.repCollection.*\","
                        + "\"data.ethosCaseReference\"]}",
                Constants.MAX_ES_SIZE / 2, ETHOS_CASE_REFERENCE_KEYWORD, cases);
    }

    public static String getBulkSearchQuery(String multipleReference) {
        TermsQueryBuilder termsQueryBuilder = termsQuery(MULTIPLE_CASE_REFERENCE_KEYWORD, multipleReference);
        return new SearchSourceBuilder()
                .size(Constants.MAX_ES_SIZE)
                .query(termsQueryBuilder).toString();
    }

    public static String getListingVenueAndRangeDateSearchQuery(String dateToSearchFrom, String dateToSearchTo,
                                                                String venueToSearch, String venueToSearchMapping,
                                                                String managingOffice) {
        BoolQueryBuilder boolQueryBuilder = boolQuery()
                .filter(QueryBuilders.termQuery(venueToSearchMapping, venueToSearch))
                .filter(new RangeQueryBuilder(LISTING_DATE_FIELD_NAME).gte(dateToSearchFrom).lte(dateToSearchTo))
                .must(new MatchQueryBuilder(MANAGING_OFFICE_FIELD_NAME, managingOffice));
        return new SearchSourceBuilder()
                .size(Constants.MAX_ES_SIZE)
                .query(boolQueryBuilder).toString();
    }

    public static String getListingRangeDateSearchQuery(String dateToSearchFrom,
                                                        String dateToSearchTo,
                                                        String managingOffice) {
        BoolQueryBuilder boolQueryBuilder = boolQuery()
                .filter(new RangeQueryBuilder(LISTING_DATE_FIELD_NAME).gte(dateToSearchFrom).lte(dateToSearchTo))
                .must(new MatchQueryBuilder(MANAGING_OFFICE_FIELD_NAME, managingOffice));
        return new SearchSourceBuilder()
                .size(Constants.MAX_ES_SIZE)
                .query(boolQueryBuilder).toString();
    }

    public static String getReportRangeDateSearchQuery(String dateToSearchFrom, String dateToSearchTo,
                                                       String reportType, String managingOffice) {
        String dateFieldName = getDateFieldName(reportType);
        BoolQueryBuilder boolQueryBuilder = boolQuery()
                .must(new MatchQueryBuilder(MANAGING_OFFICE_FIELD_NAME, managingOffice))
                .filter(new RangeQueryBuilder(dateFieldName).gte(dateToSearchFrom).lte(dateToSearchTo));
        return new SearchSourceBuilder()
                .size(Constants.MAX_ES_SIZE)
                .query(boolQueryBuilder).toString();
    }

    public static String getReportRangeDateSearchQuery(String dateToSearchFrom, String dateToSearchTo,
                                                       String reportType) {
        String dateFieldName = getDateFieldName(reportType);
        BoolQueryBuilder boolQueryBuilder = boolQuery()
                .filter(new RangeQueryBuilder(dateFieldName).gte(dateToSearchFrom).lte(dateToSearchTo));
        return new SearchSourceBuilder()
                .size(Constants.MAX_ES_SIZE)
                .query(boolQueryBuilder).toString();
    }

    private static String getDateFieldName(String reportType) {
        log.info("REPORT TYPE: " + reportType);
        switch (reportType) {
            case Constants.BROUGHT_FORWARD_REPORT:
                return BROUGHT_FORWARD_DATE_FIELD_NAME;
            case Constants.CLAIMS_ACCEPTED_REPORT:
            case Constants.LIVE_CASELOAD_REPORT:
                return CLAIMS_ACCEPTED_DATE_FIELD_NAME;
            case Constants.CASES_COMPLETED_REPORT:
            case Constants.TIME_TO_FIRST_HEARING_REPORT:
            case Constants.HEARINGS_BY_HEARING_TYPE_REPORT:
                return LISTING_DATE_FIELD_NAME;
            case Constants.CASE_SOURCE_LOCAL_REPORT:
                return RECEIPT_DATE_FIELD_NAME;
            case Constants.SERVING_CLAIMS_REPORT:
                return CLAIMS_SERVED_DATE_FIELD_NAME;
            case Constants.MEMBER_DAYS_REPORT:
                return MEMBER_DAYS_DATE_FIELD_NAME;
            default:
                return REPORT_TYPE_NOT_FOUND;
        }
    }

}
