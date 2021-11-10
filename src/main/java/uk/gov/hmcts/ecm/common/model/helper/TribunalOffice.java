package uk.gov.hmcts.ecm.common.model.helper;

import static uk.gov.hmcts.ecm.common.model.helper.Constants.ENGLANDWALES_CASE_TYPE_ID;
import static uk.gov.hmcts.ecm.common.model.helper.Constants.SCOTLAND_CASE_TYPE_ID;

import java.util.Arrays;
import java.util.List;
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
    WATFORD("Watford", "33"),
    SCOTLAND("Scotland");

    public static final TribunalOffice[] SCOTTISH_OFFICES = {
            ABERDEEN, DUNDEE, EDINBURGH, GLASGOW
    };

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

    public static String getCaseTypeId(String officeName) {
        List<String> englandWalesOffices = List.of(BRISTOL.officeName, LEEDS.officeName, LONDON_CENTRAL.officeName,
                LONDON_EAST.officeName, LONDON_SOUTH.officeName, MANCHESTER.officeName,
                MIDLANDS_EAST.officeName, MIDLANDS_WEST.officeName, NEWCASTLE.officeName,
                WALES.officeName, WATFORD.officeName);
        List<String> scotlandOffices = List.of(ABERDEEN.officeName, GLASGOW.officeName,
                EDINBURGH.officeName, DUNDEE.officeName, SCOTLAND.officeName);

        if (englandWalesOffices.contains(officeName)) {
            return ENGLANDWALES_CASE_TYPE_ID;
        } else if (scotlandOffices.contains(officeName)) {
            return SCOTLAND_CASE_TYPE_ID;
        } else {
            throw new IllegalArgumentException("Unexpected office " + officeName);
        }
    }
}
