package uk.gov.hmcts.ecm.common.service.pdf;

import lombok.SneakyThrows;
import org.apache.pdfbox.Loader;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDDocumentCatalog;
import org.apache.pdfbox.pdmodel.interactive.form.PDAcroForm;
import org.apache.pdfbox.pdmodel.interactive.form.PDField;
import org.apache.pdfbox.pdmodel.interactive.form.PDNonTerminalField;
import org.apache.tika.Tika;
import org.elasticsearch.core.Tuple;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import uk.gov.hmcts.ecm.common.constants.PdfMapperConstants;
import uk.gov.hmcts.ecm.common.model.CaseTestData;
import uk.gov.hmcts.ecm.common.service.pdf.et1.GenericServiceUtil;

import java.io.IOException;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;
import static uk.gov.hmcts.ecm.common.constants.PdfMapperConstants.PDF_TYPE_ET1;
import static uk.gov.hmcts.ecm.common.constants.PdfMapperConstants.SUBMIT_ET1;
import static uk.gov.hmcts.ecm.common.service.pdf.et3.ET3FormConstants.ET3_FORM_CLIENT_TYPE_REPRESENTATIVE;
import static uk.gov.hmcts.ecm.common.service.utils.TestConstants.ENGLISH_LANGUAGE;
import static uk.gov.hmcts.ecm.common.service.utils.TestConstants.WELSH_LANGUAGE;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
@SuppressWarnings({"PMD.CloseResource", "PMD.ExcessiveImports", "PMD.TooManyMethods", "PMD.CyclomaticComplexity"})
class PdfServiceTest {
    private static final Map<String, Optional<String>> PDF_VALUES = Map.of(
        PdfMapperConstants.TRIBUNAL_OFFICE, Optional.of("Manchester"),
        PdfMapperConstants.CASE_NUMBER, Optional.of("001"),
        PdfMapperConstants.DATE_RECEIVED, Optional.of("21-07-2022")
    );

    private CaseTestData caseTestData;

    private static final String PDF_TEMPLATE_SOURCE_ATTRIBUTE_VALUE_ENGLISH = "ET1_0224.pdf";
    private static final String PDF_TEMPLATE_SOURCE_ATTRIBUTE_VALUE_ENGLISH_INVALID = "ET1_0722.pdf";
    private static final String PDF_TEMPLATE_SOURCE_ATTRIBUTE_VALUE_ENGLISH_NOT_EXISTS = "invalid_english.pdf";
    private static final String PDF_TEMPLATE_SOURCE_ATTRIBUTE_VALUE_WELSH = "CY_ET1_2222.pdf";
    private static final String PDF_TEMPLATE_SOURCE_ATTRIBUTE_VALUE_WELSH_INVALID = "CY_ET1_0922.pdf";
    private static final String PDF_TEMPLATE_SOURCE_ATTRIBUTE_VALUE_WELSH_NOT_EXISTS = "invalid_welsh.pdf";
    private static final String PDF_FILE_TIKA_CONTENT_TYPE = "application/pdf";

    @Mock
    private ET1PdfMapperService et1PdfMapperService;
    @InjectMocks
    private PdfService pdfService;

    @BeforeEach
    void beforeEach() {
        caseTestData = new CaseTestData();
    }

    @SneakyThrows
    @Test
    void givenPdfValuesProducesAPdfDocument() {
        when(et1PdfMapperService.mapHeadersToPdf(caseTestData.getCaseData())).thenReturn(PDF_VALUES);
        byte[] pdfBytes = pdfService.convertCaseToPdf(
                caseTestData.getCaseData(),
                PDF_TEMPLATE_SOURCE_ATTRIBUTE_VALUE_ENGLISH,
                PDF_TYPE_ET1, ET3_FORM_CLIENT_TYPE_REPRESENTATIVE,
                SUBMIT_ET1
        );
        try (PDDocument actualPdf = Loader.loadPDF(pdfBytes)) {
            Map<String, Optional<String>> actualPdfValues = processPdf(actualPdf);
            PDF_VALUES.forEach((k, v) -> assertThat(actualPdfValues).containsEntry(k, v));
        }
    }

    private Map<String, Optional<String>> processPdf(PDDocument pdDocument) {
        PDDocumentCatalog pdDocumentCatalog = pdDocument.getDocumentCatalog();
        PDAcroForm pdfForm = pdDocumentCatalog.getAcroForm();
        Map<String, Optional<String>> returnFields = new ConcurrentHashMap<>();
        pdfForm.getFields().forEach(
            field -> {
                Tuple<String, String> fieldTuple = processField(field);
                returnFields.put(fieldTuple.v1(), Optional.ofNullable(fieldTuple.v2()));
            }
        );
        return returnFields;
    }

    private Tuple<String, String> processField(PDField field) {
        if (field instanceof PDNonTerminalField) {
            for (PDField child : ((PDNonTerminalField) field).getChildren()) {
                processField(child);
            }
        }

        return new Tuple<>(field.getFullyQualifiedName(), field.getValueAsString());
    }

    @SneakyThrows
    @Test
    void shouldCreateEnglishPdfFile() {
        PdfService pdfService1 = new PdfService(new ET1PdfMapperService());
        byte[] pdfData = pdfService1.createPdf(
                caseTestData.getCaseData(),
                PDF_TEMPLATE_SOURCE_ATTRIBUTE_VALUE_ENGLISH,
                PDF_TYPE_ET1, ET3_FORM_CLIENT_TYPE_REPRESENTATIVE,
                SUBMIT_ET1);
        assertThat(pdfData).isNotEmpty();
        assertThat(new Tika().detect(pdfData)).isEqualTo(PDF_FILE_TIKA_CONTENT_TYPE);
    }

    @SneakyThrows
    @Test
    void shouldNotCreateEnglishPdfFileWhenEnglishPdfTemplateIsNull() {
        PdfService pdfService1 = new PdfService(new ET1PdfMapperService());
        byte[] pdfData = pdfService1.createPdf(caseTestData.getCaseData(), null, PDF_TYPE_ET1,
                ET3_FORM_CLIENT_TYPE_REPRESENTATIVE, SUBMIT_ET1);
        assertThat(pdfData).isEmpty();
    }

    @SneakyThrows
    @Test
    void shouldNotCreateEnglishPdfFileWhenEnglishPdfTemplateNotExists() {
        PdfService pdfService1 = new PdfService(new ET1PdfMapperService());
        byte[] pdfData = pdfService1.createPdf(caseTestData.getCaseData(), null, PDF_TYPE_ET1,
                ET3_FORM_CLIENT_TYPE_REPRESENTATIVE, SUBMIT_ET1);
        assertThat(pdfData).isEmpty();
    }

    @ParameterizedTest
    @SneakyThrows
    @CsvSource({
        ENGLISH_LANGUAGE + "," + PDF_TEMPLATE_SOURCE_ATTRIBUTE_VALUE_ENGLISH,
        ENGLISH_LANGUAGE + "," + PDF_TEMPLATE_SOURCE_ATTRIBUTE_VALUE_ENGLISH_INVALID,
        ENGLISH_LANGUAGE + "," + PDF_TEMPLATE_SOURCE_ATTRIBUTE_VALUE_ENGLISH_NOT_EXISTS,
        ENGLISH_LANGUAGE + ",",
        WELSH_LANGUAGE + "," + PDF_TEMPLATE_SOURCE_ATTRIBUTE_VALUE_WELSH,
        WELSH_LANGUAGE + "," + PDF_TEMPLATE_SOURCE_ATTRIBUTE_VALUE_WELSH_INVALID,
        WELSH_LANGUAGE + "," + PDF_TEMPLATE_SOURCE_ATTRIBUTE_VALUE_WELSH_NOT_EXISTS,
        WELSH_LANGUAGE + ","
    })
    void shouldCreatePdfFileAccordingToSelectedLanguageAndTemplateSource(String language, String templateSource) {

        try (MockedStatic<GenericServiceUtil> mockedServiceUtil = Mockito.mockStatic(GenericServiceUtil.class)) {
            if (ENGLISH_LANGUAGE.equals(language)) {
                mockedServiceUtil.when(() -> GenericServiceUtil.findClaimantLanguage(caseTestData.getCaseData()))
                    .thenReturn(ENGLISH_LANGUAGE);
            }
            if (WELSH_LANGUAGE.equals(language)) {
                mockedServiceUtil.when(() -> GenericServiceUtil.findClaimantLanguage(caseTestData.getCaseData()))
                    .thenReturn(WELSH_LANGUAGE);
            }
            PdfService pdfService1 = new PdfService(new ET1PdfMapperService());
            byte[] pdfData = pdfService1.createPdf(caseTestData.getCaseData(),
                    templateSource,
                    PDF_TYPE_ET1,
                    ET3_FORM_CLIENT_TYPE_REPRESENTATIVE,
                    SUBMIT_ET1);
            if (PDF_TEMPLATE_SOURCE_ATTRIBUTE_VALUE_ENGLISH.equals(templateSource)
                || PDF_TEMPLATE_SOURCE_ATTRIBUTE_VALUE_WELSH.equals(templateSource)) {
                assertThat(pdfData).isNotEmpty();
                assertThat(new Tika().detect(pdfData)).isEqualTo(PDF_FILE_TIKA_CONTENT_TYPE);
            }
            if (templateSource == null || templateSource.contains("invalid")) {
                assertThat(pdfData).isEmpty();
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @SneakyThrows
    @Test
    void shouldCreateWelshPdfFile() {
        caseTestData.getCaseData().getClaimantHearingPreference().setContactLanguage(WELSH_LANGUAGE);
        PdfService pdfService1 = new PdfService(new ET1PdfMapperService());
        byte[] pdfData = pdfService1.createPdf(caseTestData.getCaseData(),
                PDF_TEMPLATE_SOURCE_ATTRIBUTE_VALUE_WELSH,
                PDF_TYPE_ET1,
                ET3_FORM_CLIENT_TYPE_REPRESENTATIVE,
                SUBMIT_ET1);
        assertThat(pdfData).isNotEmpty();
        assertThat(new Tika().detect(pdfData)).isEqualTo(PDF_FILE_TIKA_CONTENT_TYPE);
    }

    @SneakyThrows
    @Test
    void shouldNotCreateWelshPdfFileWhenWelshPdfTemplateIsNull() {
        caseTestData.getCaseData().getClaimantHearingPreference().setContactLanguage(WELSH_LANGUAGE);
        PdfService pdfService1 = new PdfService(new ET1PdfMapperService());
        byte[] pdfData = pdfService1.createPdf(caseTestData.getCaseData(), null, PDF_TYPE_ET1,
                ET3_FORM_CLIENT_TYPE_REPRESENTATIVE, SUBMIT_ET1);
        assertThat(pdfData).isEmpty();
    }

    @SneakyThrows
    @Test
    void shouldNotCreateWelshPdfFileWhenWelshPdfTemplateNotExists() {
        caseTestData.getCaseData().getClaimantHearingPreference().setContactLanguage(WELSH_LANGUAGE);
        PdfService pdfService1 = new PdfService(new ET1PdfMapperService());
        byte[] pdfData = pdfService1.createPdf(
                caseTestData.getCaseData(),
                PDF_TEMPLATE_SOURCE_ATTRIBUTE_VALUE_WELSH_NOT_EXISTS,
                PDF_TYPE_ET1, ET3_FORM_CLIENT_TYPE_REPRESENTATIVE,
                SUBMIT_ET1);
        assertThat(pdfData).isEmpty();
    }

}
