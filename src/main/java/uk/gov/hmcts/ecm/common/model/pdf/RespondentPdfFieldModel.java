package uk.gov.hmcts.ecm.common.model.pdf;

public record RespondentPdfFieldModel(String respondentNameFieldName, String respondentAddressFieldName,
                                      String respondentPostcodeFieldName,
                                      AcasCertificatePdfFieldModel respondentAcasCertificatePdfFieldModel) {
}
