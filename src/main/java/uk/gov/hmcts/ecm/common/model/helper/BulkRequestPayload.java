package uk.gov.hmcts.ecm.common.model.helper;

import lombok.Data;
import lombok.NoArgsConstructor;
import uk.gov.hmcts.ecm.common.model.bulk.BulkDetails;

import java.util.List;

@Data
@NoArgsConstructor
public class BulkRequestPayload {

    private List<String> errors;
    private BulkDetails bulkDetails;
}
