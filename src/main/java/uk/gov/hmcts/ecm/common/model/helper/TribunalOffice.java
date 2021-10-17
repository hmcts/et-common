package uk.gov.hmcts.ecm.common.model.helper;

import java.util.Arrays;
import java.util.Optional;

public enum TribunalOffice {
    ABERDEEN("Aberdeen"),
    BRISTOL("Bristol", "14"),
    DUNDEE("Dundee"),
    EDINBURGH("Edinburgh"),
    GLASGOW("Glasgow", "41"),
    LEEDS("Leeds", "18"),
    LONDON_CENTRAL("London Central", "22"),
    LONDON_EAST("London East", "32"),
    LONDON_SOUTH("London South", "23"),
    MANCHESTER("Manchester", "24"),
    MIDLANDS_EAST("Midlands East", "26"),
    MIDLANDS_WEST("Midlands West", "13"),
    NEWCASTLE("Newcastle", "25"),
    WALES("Wales", "16"),
    WATFORD("Watford", "33");

    private final String officeName;
    private final String officeNumber;

    TribunalOffice(String officeName) {
        this(officeName, null);
    }

    TribunalOffice(String officeName, String officeNumber) {
        this.officeName = officeName;
        this.officeNumber = officeNumber;
    }

    public String getOfficeName() {
        return officeName;
    }

    public String getOfficeNumber() {
        return officeNumber;
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
}
