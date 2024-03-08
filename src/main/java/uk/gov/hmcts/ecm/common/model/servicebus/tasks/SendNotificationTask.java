package uk.gov.hmcts.ecm.common.model.servicebus.tasks;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import lombok.extern.slf4j.Slf4j;
import uk.gov.hmcts.ecm.common.model.servicebus.datamodel.DataModelParent;
import uk.gov.hmcts.ecm.common.model.servicebus.datamodel.SendNotificationDataModel;
import uk.gov.hmcts.et.common.model.ccd.CaseData;
import uk.gov.hmcts.et.common.model.ccd.SubmitEvent;
import uk.gov.hmcts.et.common.model.ccd.types.SendNotificationType;

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@SuperBuilder
@JsonIgnoreProperties(ignoreUnknown = true)
@Slf4j
public class SendNotificationTask extends DataTaskParent {

    public SendNotificationTask(DataModelParent dataModelParent) {
        super(dataModelParent);
    }

    public void run(SubmitEvent submitEvent) {
        log.info("Setting single case with Notification");
        CaseData caseData = submitEvent.getCaseData();
        SendNotificationType sendNotificationData = ((SendNotificationDataModel) dataModelParent).getSendNotification();

        caseData.setSendNotificationTitle(sendNotificationData.getSendNotificationTitle());
        caseData.setSendNotificationLetter(sendNotificationData.getSendNotificationLetter());
        caseData.setSendNotificationUploadDocument(sendNotificationData.getSendNotificationUploadDocument());
        caseData.setSendNotificationSubject(sendNotificationData.getSendNotificationSubject());
        caseData.setSendNotificationAdditionalInfo(sendNotificationData.getSendNotificationAdditionalInfo());

        log.info("Send notify " + sendNotificationData.getSendNotificationNotify());
        if (sendNotificationData.getSendNotificationNotify().equals("Lead case")) {
            // Translates SendNotificationNotify property on multiple into fixed list format on single
            // TODO Implementation for sending to lead and sub cases
            caseData.setSendNotificationNotify(sendNotificationData.getSendNotificationNotifyLeadCase());
            log.info("Send notify now " +  caseData.getSendNotificationNotify());
        }
        caseData.setSendNotificationSelectHearing(sendNotificationData.getSendNotificationSelectHearing());
        caseData.setSendNotificationCaseManagement(sendNotificationData.getSendNotificationCaseManagement());
        caseData.setSendNotificationResponseTribunal(sendNotificationData.getSendNotificationResponseTribunal());
        caseData.setSendNotificationWhoCaseOrder(sendNotificationData.getSendNotificationWhoCaseOrder());
        caseData.setSendNotificationSelectParties(sendNotificationData.getSendNotificationSelectParties());
        caseData.setSendNotificationFullName(sendNotificationData.getSendNotificationFullName());
        caseData.setSendNotificationFullName2(sendNotificationData.getSendNotificationFullName2());
        caseData.setSendNotificationDecision(sendNotificationData.getSendNotificationDecision());
        caseData.setSendNotificationDetails(sendNotificationData.getSendNotificationDetails());
        caseData.setSendNotificationRequestMadeBy(sendNotificationData.getSendNotificationRequestMadeBy());
        caseData.setSendNotificationEccQuestion(sendNotificationData.getSendNotificationEccQuestion());
        caseData.setSendNotificationWhoMadeJudgement(sendNotificationData.getSendNotificationWhoCaseOrder());
    }

}
