package uk.gov.hmcts.ecm.common.model.bulk;

import lombok.Data;

@Data
public class MultRefComplexType {

    private boolean exist;

    private SubmitBulkEvent submitBulkEvent;

}
