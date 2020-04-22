package uk.gov.hmcts.ecm.common.bulk;

import lombok.Data;
import uk.gov.hmcts.ecm.common.ccd.DocumentInfo;

import java.util.List;

@Data
public class BulkDocumentInfo {

    private String markUps;
    private List<String> errors;
    private DocumentInfo documentInfo;

}
