package uk.gov.hmcts.ecm.common.model.reports.claimsbyhearingvenue;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import uk.gov.hmcts.et.common.model.ccd.items.RespondentSumTypeItem;
import uk.gov.hmcts.et.common.model.ccd.types.ClaimantType;
import uk.gov.hmcts.et.common.model.ccd.types.ClaimantWorkAddressType;
import java.util.List;

@Data
public class ClaimsByHearingVenueCaseData {
    @JsonProperty("ethosCaseReference")
    private String ethosCaseReference;

    @JsonProperty("receiptDate")
    private String receiptDate;

    @JsonProperty("claimantType")
    private ClaimantType claimantType;

    @JsonProperty("claimantWorkAddress")
    private ClaimantWorkAddressType claimantWorkAddressType;

    @JsonProperty("respondentCollection")
    private List<RespondentSumTypeItem> respondentCollection;

    @JsonProperty("managingOffice")
    private String managingOffice;
}
