package uk.gov.hmcts.ecm.common.model.ccd.types;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
@Data
public class CasePreAcceptType {

    @JsonProperty("caseAccepted")
    private String caseAccepted;
    @JsonProperty("dateAccepted")
    private String dateAccepted;
    @JsonProperty("dateRejected")
    private String dateRejected;
    @JsonProperty("rejectReason")
    private List<String> rejectReason;

}
