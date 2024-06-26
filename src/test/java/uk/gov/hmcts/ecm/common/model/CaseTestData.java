package uk.gov.hmcts.ecm.common.model;

import lombok.Data;
import uk.gov.hmcts.ecm.common.service.utils.ResourceLoader;
import uk.gov.hmcts.et.common.model.ccd.CaseData;

@Data
@SuppressWarnings({"PMD.TooManyFields"})
public final class CaseTestData {

    private final CaseData caseData = ResourceLoader.fromString(
        "requests/caseData.json",
        CaseData.class
    );

}
