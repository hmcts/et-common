package uk.gov.hmcts.ecm.common.service.pdf.et1;

import uk.gov.hmcts.ecm.common.constants.PdfMapperConstants;
import uk.gov.hmcts.ecm.common.model.pdf.AcasCertificatePdfFieldModel;
import uk.gov.hmcts.ecm.common.model.pdf.RespondentPdfFieldModel;

public enum PdfTemplateRespondentFieldNamesEnum {
    FIRST_RESPONDENT(new RespondentPdfFieldModel(
        PdfMapperConstants.Q2_EMPLOYER_NAME,
        PdfMapperConstants.PDF_TEMPLATE_Q2_2_1_FIRST_RESPONDENT_ADDRESS,
        PdfMapperConstants.PDF_TEMPLATE_Q2_2_2_FIRST_RESPONDENT_POSTCODE,
        new AcasCertificatePdfFieldModel(
            PdfMapperConstants.PDF_TEMPLATE_Q2_3_1_1_FIRST_RESPONDENT_ACAS_CERTIFICATE_CHECK_YES,
            PdfMapperConstants.PDF_TEMPLATE_Q2_3_1_2_FIRST_RESPONDENT_ACAS_CERTIFICATE_CHECK_NO,
            PdfMapperConstants.PDF_TEMPLATE_Q2_3_2_FIRST_RESPONDENT_ACAS_CERTIFICATE_NUMBER,
            PdfMapperConstants.PDF_TEMPLATE_Q2_3_3_4_FIRST_RESPONDENT_NO_ACAS_REASON_UNFAIR_DISMISSAL,
            PdfMapperConstants.PDF_TEMPLATE_Q2_3_3_1_FIRST_RESPONDENT_NO_ACAS_REASON_ANOTHER_PERSON,
            PdfMapperConstants.PDF_TEMPLATE_Q2_3_3_2_FIRST_RESPONDENT_NO_ACAS_REASON_NO_POWER_TO_CONCILIATE,
            PdfMapperConstants.PDF_TEMPLATE_Q2_3_3_3_FIRST_RESPONDENT_NO_ACAS_REASON_EMPLOYER_CONTACTED
        )
    )),
    SECOND_RESPONDENT(new RespondentPdfFieldModel(
        PdfMapperConstants.PDF_TEMPLATE_Q2_5_1_SECOND_RESPONDENT_NAME,
        PdfMapperConstants.PDF_TEMPLATE_Q2_5_2_SECOND_RESPONDENT_ADDRESS,
        PdfMapperConstants.PDF_TEMPLATE_Q2_5_3_SECOND_RESPONDENT_POSTCODE,
        new AcasCertificatePdfFieldModel(
            PdfMapperConstants.PDF_TEMPLATE_Q2_6_1_1_SECOND_RESPONDENT_ACAS_CERTIFICATE_CHECK_YES,
            PdfMapperConstants.PDF_TEMPLATE_Q2_6_1_2_SECOND_RESPONDENT_ACAS_CERTIFICATE_CHECK_NO,
            PdfMapperConstants.PDF_TEMPLATE_Q2_6_2_SECOND_RESPONDENT_ACAS_CERTIFICATE_NUMBER,
            PdfMapperConstants.PDF_TEMPLATE_Q2_6_3_4_SECOND_RESPONDENT_NO_ACAS_REASON_UNFAIR_DISMISSAL,
            PdfMapperConstants.PDF_TEMPLATE_Q2_6_3_1_SECOND_RESPONDENT_NO_ACAS_REASON_ANOTHER_PERSON,
            PdfMapperConstants.PDF_TEMPLATE_Q2_6_3_2_SECOND_RESPONDENT_NO_ACAS_REASON_NO_POWER_TO_CONCILIATE,
            PdfMapperConstants.PDF_TEMPLATE_Q2_6_3_3_SECOND_RESPONDENT_NO_ACAS_REASON_EMPLOYER_CONTACTED
        )
    )),
    THIRD_RESPONDENT(new RespondentPdfFieldModel(
        PdfMapperConstants.PDF_TEMPLATE_Q2_7_1_THIRD_RESPONDENT_NAME,
        PdfMapperConstants.PDF_TEMPLATE_Q2_7_2_THIRD_RESPONDENT_ADDRESS,
        PdfMapperConstants.PDF_TEMPLATE_Q2_7_3_THIRD_RESPONDENT_POSTCODE,
        new AcasCertificatePdfFieldModel(
            PdfMapperConstants.PDF_TEMPLATE_Q2_8_1_1_THIRD_RESPONDENT_ACAS_CERTIFICATE_CHECK_YES,
            PdfMapperConstants.PDF_TEMPLATE_Q2_8_1_2_THIRD_RESPONDENT_ACAS_CERTIFICATE_CHECK_NO,
            PdfMapperConstants.PDF_TEMPLATE_Q2_8_2_THIRD_RESPONDENT_ACAS_CERTIFICATE_NUMBER,
            PdfMapperConstants.PDF_TEMPLATE_Q2_8_3_4_THIRD_RESPONDENT_NO_ACAS_REASON_UNFAIR_DISMISSAL,
            PdfMapperConstants.PDF_TEMPLATE_Q2_8_3_1_THIRD_RESPONDENT_NO_ACAS_REASON_ANOTHER_PERSON,
            PdfMapperConstants.PDF_TEMPLATE_Q2_8_3_2_THIRD_RESPONDENT_NO_ACAS_REASON_NO_POWER_TO_CONCILIATE,
            PdfMapperConstants.PDF_TEMPLATE_Q2_8_3_3_THIRD_RESPONDENT_NO_ACAS_REASON_EMPLOYER_CONTACTED
        )
    )),
    FORTH_RESPONDENT(new RespondentPdfFieldModel(
        PdfMapperConstants.PDF_TEMPLATE_Q13_1_1_FORTH_RESPONDENT_NAME,
        PdfMapperConstants.PDF_TEMPLATE_Q13_1_2_FORTH_RESPONDENT_ADDRESS,
        PdfMapperConstants.PDF_TEMPLATE_Q13_1_3_FORTH_RESPONDENT_POSTCODE,
        new AcasCertificatePdfFieldModel(
            PdfMapperConstants.PDF_TEMPLATE_Q13_2_1_1_FORTH_RESPONDENT_ACAS_CERTIFICATE_CHECK_YES,
            PdfMapperConstants.PDF_TEMPLATE_Q13_2_1_2_FORTH_RESPONDENT_ACAS_CERTIFICATE_CHECK_NO,
            PdfMapperConstants.PDF_TEMPLATE_Q13_2_2_FORTH_RESPONDENT_ACAS_CERTIFICATE_NUMBER,
            PdfMapperConstants.PDF_TEMPLATE_Q13_2_3_4_FORTH_RESPONDENT_NO_ACAS_REASON_UNFAIR_DISMISSAL,
            PdfMapperConstants.PDF_TEMPLATE_Q13_2_3_1_FORTH_RESPONDENT_NO_ACAS_REASON_ANOTHER_PERSON,
            PdfMapperConstants.PDF_TEMPLATE_Q13_2_3_2_FORTH_RESPONDENT_NO_ACAS_REASON_NO_POWER_TO_CONCILIATE,
            PdfMapperConstants.PDF_TEMPLATE_Q13_2_3_3_FORTH_RESPONDENT_NO_ACAS_REASON_EMPLOYER_CONTACTED
        )
    )),
    FIFTH_RESPONDENT(new RespondentPdfFieldModel(
        PdfMapperConstants.PDF_TEMPLATE_Q13_3_1_FIFTH_RESPONDENT_NAME,
        PdfMapperConstants.PDF_TEMPLATE_Q13_3_2_FIFTH_RESPONDENT_ADDRESS,
        PdfMapperConstants.PDF_TEMPLATE_Q13_3_3_FIFTH_RESPONDENT_POSTCODE,
        new AcasCertificatePdfFieldModel(
            PdfMapperConstants.PDF_TEMPLATE_Q13_4_1_1_FIFTH_RESPONDENT_ACAS_CERTIFICATE_CHECK_YES,
            PdfMapperConstants.PDF_TEMPLATE_Q13_4_1_2_FIFTH_RESPONDENT_ACAS_CERTIFICATE_CHECK_NO,
            PdfMapperConstants.PDF_TEMPLATE_Q13_4_2_FIFTH_RESPONDENT_ACAS_CERTIFICATE_NUMBER,
            PdfMapperConstants.PDF_TEMPLATE_Q13_4_3_4_FIFTH_RESPONDENT_NO_ACAS_REASON_UNFAIR_DISMISSAL,
            PdfMapperConstants.PDF_TEMPLATE_Q13_4_3_1_FIFTH_RESPONDENT_NO_ACAS_REASON_ANOTHER_PERSON,
            PdfMapperConstants.PDF_TEMPLATE_Q13_4_3_2_FIFTH_RESPONDENT_NO_ACAS_REASON_NO_POWER_TO_CONCILIATE,
            PdfMapperConstants.PDF_TEMPLATE_Q13_4_3_3_FIFTH_RESPONDENT_NO_ACAS_REASON_EMPLOYER_CONTACTED
        )
    ));
    public final RespondentPdfFieldModel respondentPdfFieldModel;

    PdfTemplateRespondentFieldNamesEnum(RespondentPdfFieldModel respondentPdfFieldModel) {
        this.respondentPdfFieldModel = respondentPdfFieldModel;
    }
}
