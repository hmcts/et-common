package uk.gov.hmcts.ecm.common.model.servicebus;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Builder
@Data
public class CreateUpdatesDto {

    private String jurisdiction;
    private String multipleRef;
    private String multipleReferenceLinkMarkUp;
    private String username;
    private String caseTypeId;
    private String confirmation;
    private List<String> ethosCaseRefCollection;

}
