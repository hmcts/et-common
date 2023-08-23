package uk.gov.hmcts.ecm.common.model.helper;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public enum TribunalLocation {
    ABERDEEN("Aberdeen", "219164", "11"),
    BRISTOL("Bristol", "819890", "6"),
    DUNDEE("Dundee", "367564", "11"),
    EDINBURGH("Edinburgh", "368308", "11"),
    GLASGOW("Glasgow", "366559", "11"),
    LEEDS("Leeds", "36313", "3"),
    LONDON_CENTRAL("London Central", "21153", "1"),
    LONDON_EAST("London East", "816964", "1"),
    LONDON_SOUTH("London South", "227942", "1"),
    MANCHESTER("Manchester", "301017", "4"),
    MIDLANDS_EAST("Midlands East", "618632", "2"),
    MIDLANDS_WEST("Midlands West", "877347", "2"),
    NEWCASTLE("Newcastle", "366796", "3"),
    WALES("Wales", "779109", "7"),
    WATFORD("Watford", "685391", "5"),
    SCOTLAND("Scotland");

    public static final List<TribunalLocation> ENGLANDWALES_OFFICES = List.of(
            BRISTOL, LEEDS, LONDON_CENTRAL, LONDON_EAST, LONDON_SOUTH, MANCHESTER, MIDLANDS_EAST, MIDLANDS_WEST,
            NEWCASTLE, WALES, WATFORD
    );

    public static final List<TribunalLocation> SCOTLAND_OFFICES = List.of(
            ABERDEEN, DUNDEE, EDINBURGH, GLASGOW
    );

    private final String officeName;
    private final String epimmsId;
    private final String regionId;

    TribunalLocation(String officeName) {
        this(officeName, null);
    }

    TribunalLocation(String officeName, String epimmsId) {
        this(officeName, epimmsId, null);
    }

    TribunalLocation(String officeName, String epimmsId, String regionId) {
        this.officeName = officeName;
        this.epimmsId = epimmsId;
        this.regionId = regionId;
    }

    public String getOfficeName() {
        return officeName;
    }

    public String getEpimmsId() {
        return epimmsId;
    }

    public String getRegionId() {
        return regionId;
    }

    public static Optional<TribunalLocation> valueOfEpimmsId(String epimmsId) {
        return Arrays.stream(values())
                .filter(t -> t.epimmsId != null && t.epimmsId.equals(epimmsId))
                .findFirst();
    }

    public static TribunalLocation valueOfOfficeName(String officeName) {
        for (TribunalLocation tribunalOffice : values()) {
            if (tribunalOffice.officeName.equals(officeName)) {
                return tribunalOffice;
            }
        }
        throw new IllegalArgumentException(String.format("Office name %s not recognised", officeName));
    }

    public static String getCaseTypeId(String officeName) {
        TribunalLocation tribunalOffice = TribunalLocation.valueOfOfficeName(officeName);
        if (ENGLANDWALES_OFFICES.contains(tribunalOffice)) {
            return Constants.ENGLANDWALES_CASE_TYPE_ID;
        } else if (SCOTLAND_OFFICES.contains(tribunalOffice)) {
            return Constants.SCOTLAND_CASE_TYPE_ID;
        } else {
            throw new IllegalArgumentException("Unexpected office " + officeName);
        }
    }

    public static boolean isEnglandWalesOffice(String officeName) {
        return Constants.ENGLANDWALES_CASE_TYPE_ID.equals(getCaseTypeId(officeName));
    }

    public static boolean isScotlandOffice(String officeName) {
        return Constants.SCOTLAND_CASE_TYPE_ID.equals(getCaseTypeId(officeName));
    }

    public static TribunalLocation getOfficeForReferenceData(TribunalLocation tribunalOffice) {
        return SCOTLAND_OFFICES.contains(tribunalOffice) ? TribunalLocation.SCOTLAND : tribunalOffice;
    }
}
