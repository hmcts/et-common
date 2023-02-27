package uk.gov.hmcts.ecm.common.model.helper;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static uk.gov.hmcts.ecm.common.model.helper.Constants.ENGLANDWALES_CASE_TYPE_ID;
import static uk.gov.hmcts.ecm.common.model.helper.Constants.SCOTLAND_CASE_TYPE_ID;

public enum TribunalOffice {
    ABERDEEN("Aberdeen"),
    BRISTOL("Bristol", "14", "bristolet@justice.gov.uk"),
    DUNDEE("Dundee"),
    EDINBURGH("Edinburgh"),
    GLASGOW("Glasgow", "41", "glasgowet@justice.gov.uk"),
    LEEDS("Leeds", "18", "leedset@justice.gov.uk"),
    LONDON_CENTRAL("London Central", "22", "londoncentralet@justice.gov.uk"),
    LONDON_EAST("London East", "32", "eastlondon@justice.gov.uk"),
    LONDON_SOUTH("London South", "23", "londonsouthet@justice.gov.uk"),
    MANCHESTER("Manchester", "24", "manchesteret@justice.gov.uk"),
    MIDLANDS_EAST("Midlands East", "26", "midlandseastet@justice.gov.uk"),
    MIDLANDS_WEST("Midlands West", "13", "midlandswestet@justice.gov.uk"),
    NEWCASTLE("Newcastle", "25", "newcastleet@justice.gov.uk"),
    WALES("Wales", "16", "waleset@justice.gov.uk"),
    WATFORD("Watford", "33", "watfordet@justice.gov.uk"),
    SCOTLAND("Scotland");

    public static final List<TribunalOffice> ENGLANDWALES_OFFICES = List.of(
            BRISTOL, LEEDS, LONDON_CENTRAL, LONDON_EAST, LONDON_SOUTH, MANCHESTER, MIDLANDS_EAST, MIDLANDS_WEST,
            NEWCASTLE, WALES, WATFORD
    );

    public static final List<TribunalOffice> SCOTLAND_OFFICES = List.of(
            ABERDEEN, DUNDEE, EDINBURGH, GLASGOW
    );

    private final String officeName;
    private final String officeNumber;
    private final String email;

    TribunalOffice(String officeName) {
        this(officeName, null);
    }

    TribunalOffice(String officeName, String officeNumber) {
        this(officeName, officeNumber, null);
    }

    TribunalOffice(String officeName, String officeNumber, String email) {
        this.officeName = officeName;
        this.officeNumber = officeNumber;
        this.email = email;
    }

    public String getOfficeName() {
        return officeName;
    }

    public String getOfficeNumber() {
        return officeNumber;
    }

    public String getOfficeEmail() {
        return email;
    }

    public static Optional<TribunalOffice> valueOfOfficeNumber(String officeNumber) {
        return Arrays.stream(values())
                .filter(t -> t.officeNumber != null && t.officeNumber.equals(officeNumber))
                .findFirst();
    }

    public static TribunalOffice valueOfOfficeName(String officeName) {
        for (TribunalOffice tribunalOffice : values()) {
            if (tribunalOffice.officeName.equals(officeName)) {
                return tribunalOffice;
            }
        }
        throw new IllegalArgumentException(String.format("Office name %s not recognised", officeName));
    }

    public static String getCaseTypeId(String officeName) {
        TribunalOffice tribunalOffice = TribunalOffice.valueOfOfficeName(officeName);
        if (ENGLANDWALES_OFFICES.contains(tribunalOffice)) {
            return ENGLANDWALES_CASE_TYPE_ID;
        } else if (SCOTLAND_OFFICES.contains(tribunalOffice)) {
            return SCOTLAND_CASE_TYPE_ID;
        } else {
            throw new IllegalArgumentException("Unexpected office " + officeName);
        }
    }

    public static boolean isEnglandWalesOffice(String officeName) {
        return ENGLANDWALES_CASE_TYPE_ID.equals(getCaseTypeId(officeName));
    }

    public static boolean isScotlandOffice(String officeName) {
        return SCOTLAND_CASE_TYPE_ID.equals(getCaseTypeId(officeName));
    }

    public static TribunalOffice getOfficeForReferenceData(TribunalOffice tribunalOffice) {
        return SCOTLAND_OFFICES.contains(tribunalOffice) ? TribunalOffice.SCOTLAND : tribunalOffice;
    }
}
