package uk.gov.hmcts.ecm.common.service.pdf;

import lombok.SneakyThrows;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import uk.gov.hmcts.ecm.common.service.utils.data.TestDataProvider;
import uk.gov.hmcts.et.common.model.ccd.CaseData;

import static org.assertj.core.api.Assertions.assertThat;
import static uk.gov.hmcts.ecm.common.service.pdf.et3.ET3FormConstants.SUBMIT_ET3_CITIZEN;
import static uk.gov.hmcts.ecm.common.service.utils.TestConstants.ENGLISH_PDF_TEMPLATE_SOURCE;
import static uk.gov.hmcts.ecm.common.service.utils.TestConstants.ET3_FORM_CLIENT_TYPE_RESPONDENT;
import static uk.gov.hmcts.ecm.common.service.utils.TestConstants.ET3_FORM_TYPE;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
class ET3PdfServiceTest {

    private PdfService pdfService;

    @BeforeEach
    void setUp() {
        pdfService = new PdfService(new ET1PdfMapperService());
    }

    @Test
    @SneakyThrows
    void theConvertCaseToPdfForET3CaseData() {
        CaseData et3CaseData = TestDataProvider.generateEt3CaseData();
        byte[] pdfByteArray = pdfService.convertCaseToPdf(et3CaseData,
                ENGLISH_PDF_TEMPLATE_SOURCE, ET3_FORM_TYPE, ET3_FORM_CLIENT_TYPE_RESPONDENT, SUBMIT_ET3_CITIZEN);
        assertThat(pdfByteArray).isNotEmpty();
    }
}

