package uk.gov.hmcts.ecm.common.model.multiples;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class MultipleObject {

    private String ethosCaseRef;
    private String subMultiple;
    private String flag1;
    private String flag2;
    private String flag3;
    private String flag4;

}
