package uk.gov.hmcts.ecm.common.model.listing;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import uk.gov.hmcts.ecm.common.model.ccd.Address;
import uk.gov.hmcts.ecm.common.model.listing.items.AdhocReportTypeItem;
import uk.gov.hmcts.ecm.common.model.listing.items.BFDateTypeItem;
import uk.gov.hmcts.ecm.common.model.listing.items.ListingTypeItem;
import uk.gov.hmcts.ecm.common.model.listing.types.AdhocReportType;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
@Data
public class ListingData {

    @JsonProperty("tribunalCorrespondenceAddress")
    private Address tribunalCorrespondenceAddress;
    @JsonProperty("tribunalCorrespondenceTelephone")
    private String tribunalCorrespondenceTelephone;
    @JsonProperty("tribunalCorrespondenceFax")
    private String tribunalCorrespondenceFax;
    @JsonProperty("tribunalCorrespondenceDX")
    private String tribunalCorrespondenceDX;
    @JsonProperty("tribunalCorrespondenceEmail")
    private String tribunalCorrespondenceEmail;
    @JsonProperty("reportDate")
    private String reportDate;
    @JsonProperty("hearingDateType")
    private String hearingDateType;
    @JsonProperty("listingDate")
    private String listingDate;
    @JsonProperty("listingDateFrom")
    private String listingDateFrom;
    @JsonProperty("listingDateTo")
    private String listingDateTo;
    @JsonProperty("listingVenue")
    private String listingVenue;
    @JsonProperty("listingCollection")
    private List<ListingTypeItem> listingCollection;
    @JsonProperty("listingVenueOfficeGlas")
    private String listingVenueOfficeGlas;
    @JsonProperty("listingVenueOfficeAber")
    private String listingVenueOfficeAber;
    @JsonProperty("venueGlasgow")
    private String venueGlasgow;
    @JsonProperty("venueAberdeen")
    private String venueAberdeen;
    @JsonProperty("venueDundee")
    private String venueDundee;
    @JsonProperty("venueEdinburgh")
    private String venueEdinburgh;
    @JsonProperty("hearingDocType")
    private String hearingDocType;
    @JsonProperty("hearingDocETCL")
    private String hearingDocETCL;
    @JsonProperty("roomOrNoRoom")
    private String roomOrNoRoom;
    @JsonProperty("docMarkUp")
    private String docMarkUp;
    @JsonProperty("bfDateCollection")
    private List<BFDateTypeItem> bfDateCollection;
    @JsonProperty("clerkResponsible")
    private String clerkResponsible;
    @JsonProperty("reportType")
    private String reportType;
    @JsonProperty("documentName")
    private String documentName;
    @JsonProperty("showAll")
    private String showAll;

    @JsonProperty("localReportsSummaryHdr")
    private AdhocReportType localReportsSummaryHdr;
    @JsonProperty("localReportsSummary")
    private List<AdhocReportTypeItem> localReportsSummary;
    @JsonProperty("localReportsSummaryHdr2")
    private AdhocReportType localReportsSummaryHdr2;
    @JsonProperty("localReportsSummary2")
    private List<AdhocReportTypeItem> localReportsSummary2;
    @JsonProperty("localReportsDetailHdr")
    private AdhocReportType localReportsDetailHdr;
    @JsonProperty("localReportsDetail")
    private List<AdhocReportTypeItem> localReportsDetail;

    public void clearReportFields() {
        listingVenueOfficeAber = null;
        listingVenueOfficeGlas = null;
        venueGlasgow = null;
        venueAberdeen = null;
        venueDundee = null;
        venueEdinburgh = null;
        clerkResponsible = null;
    }
}

