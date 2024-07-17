package uk.gov.hmcts.ecm.common.model.helper;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
@Builder
public class SchedulePayload {
    private String claimantName;
    private String respondentName;
    private String positionType;
    private String ethosCaseRef;

    private String claimantAddressLine1;
    private String claimantAddressLine2;
    private String claimantAddressLine3;
    private String claimantTown;
    private String claimantPostCode;

    private String respondentAddressLine1;
    private String respondentAddressLine2;
    private String respondentAddressLine3;
    private String respondentTown;
    private String respondentPostCode;
    private String state;
}