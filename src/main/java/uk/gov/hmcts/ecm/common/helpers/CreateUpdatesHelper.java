package uk.gov.hmcts.ecm.common.helpers;

import uk.gov.hmcts.ecm.common.model.servicebus.CreateUpdatesDto;
import uk.gov.hmcts.ecm.common.model.servicebus.CreateUpdatesMsg;
import uk.gov.hmcts.ecm.common.model.servicebus.datamodel.DataModelParent;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

public final class CreateUpdatesHelper {

    private CreateUpdatesHelper() {
        // private constructor required for utility class
    }

    public static List<CreateUpdatesMsg> getCreateUpdatesMessagesCollection(CreateUpdatesDto createUpdatesDto,
                                                                            DataModelParent dataModelParent,
                                                                            int chunkSize,
                                                                            String updateSize) {

        return Partition.ofSize(createUpdatesDto.getEthosCaseRefCollection(), chunkSize).stream()
            .map(ethosCasesChunked -> createUpdatesMsg(
                    ethosCasesChunked, createUpdatesDto, dataModelParent, updateSize))
            .collect(Collectors.toList());
    }

    private static CreateUpdatesMsg createUpdatesMsg(List<String> ethosCasesChunked,
                                                     CreateUpdatesDto createUpdatesDto,
                                                     DataModelParent dataModelParent,
                                                     String updateSize) {

        return CreateUpdatesMsg.builder()
                .msgId(UUID.randomUUID().toString())
                .jurisdiction(createUpdatesDto.getJurisdiction())
                .caseTypeId(createUpdatesDto.getCaseTypeId())
                .multipleRef(createUpdatesDto.getMultipleRef())
                .ethosCaseRefCollection(ethosCasesChunked)
                .totalCases(updateSize)
                .username(createUpdatesDto.getUsername())
                .confirmation(createUpdatesDto.getConfirmation())
                .dataModelParent(dataModelParent)
                .multipleReferenceLinkMarkUp(createUpdatesDto.getMultipleReferenceLinkMarkUp())
                .build();
    }

}
