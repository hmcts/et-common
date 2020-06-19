package uk.gov.hmcts.ecm.common.helpers;

import uk.gov.hmcts.ecm.common.model.servicebus.CreateUpdatesDto;
import uk.gov.hmcts.ecm.common.model.servicebus.CreateUpdatesMsg;
import uk.gov.hmcts.ecm.common.model.servicebus.UpdateData;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

public class CreateUpdatesHelper {

    public static List<CreateUpdatesMsg> getCreateUpdatesMessagesCollection(CreateUpdatesDto createUpdatesDto, int chunkSize) {

        return Partition.ofSize(createUpdatesDto.getEthosCaseRefCollection(), chunkSize).stream()
            .map(ethosCasesChunked -> createUpdatesMsg(ethosCasesChunked, createUpdatesDto))
            .collect(Collectors.toList());
    }

    private static CreateUpdatesMsg createUpdatesMsg(List<String> ethosCasesChunked, CreateUpdatesDto createUpdatesDto) {

        UpdateData updateData = UpdateData.builder()
            .lead(createUpdatesDto.getEthosCaseRefCollection().get(0))
            .claimantRep("")
            .build();

        return CreateUpdatesMsg.builder()
            .msgId(UUID.randomUUID().toString())
            .updateType(createUpdatesDto.getUpdateType())
            .jurisdiction(createUpdatesDto.getJurisdiction())
            .caseTypeId(createUpdatesDto.getCaseTypeId())
            .multipleRef(createUpdatesDto.getMultipleRef())
            .ethosCaseRefCollection(ethosCasesChunked)
            .totalCases(String.valueOf(createUpdatesDto.getEthosCaseRefCollection().size()))
            .username(createUpdatesDto.getUsername())
            .updateData(updateData)
            .build();
    }

}
