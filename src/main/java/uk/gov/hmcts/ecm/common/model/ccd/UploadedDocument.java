package uk.gov.hmcts.ecm.common.model.ccd;

import lombok.Builder;
import lombok.Data;
import org.springframework.core.io.Resource;

@Builder
@Data
public class UploadedDocument {

    private final Resource content;
    private final String name;
    private final String contentType;

}
