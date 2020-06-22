package uk.gov.hmcts.ecm.common.model.servicebus;

import lombok.*;
import java.util.List;

@Builder
@Data
public class CreateUpdatesDto {

    private String jurisdiction;
    private String multipleRef;
    private String username;
    private String caseTypeId;
    private List<String> ethosCaseRefCollection;

}
