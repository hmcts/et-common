package uk.gov.hmcts.ecm.common.model.bulk.items;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import uk.gov.hmcts.ecm.common.model.bulk.types.CaseType;

import java.util.UUID;

@JsonIgnoreProperties(ignoreUnknown = true)
@Data
public class CaseIdTypeItem {

    public static CaseIdTypeItem from(String ethosCaseReference) {
        var caseType = new CaseType();
        caseType.setEthosCaseReference(ethosCaseReference);
        var caseIdTypeItem = new CaseIdTypeItem();
        caseIdTypeItem.id = UUID.randomUUID().toString();
        caseIdTypeItem.value = caseType;
        return caseIdTypeItem;
    }

    @JsonProperty("id")
    private String id;
    @JsonProperty("value")
    private CaseType value;
}
