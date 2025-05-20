package uk.gov.hmcts.ecm.common.service.pdf;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.pdfbox.Loader;
import org.apache.pdfbox.cos.COSName;
import org.apache.pdfbox.io.RandomAccessReadBufferedFile;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDDocumentCatalog;
import org.apache.pdfbox.pdmodel.PDResources;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.apache.pdfbox.pdmodel.font.Standard14Fonts;
import org.apache.pdfbox.pdmodel.interactive.form.PDAcroForm;
import org.apache.pdfbox.pdmodel.interactive.form.PDField;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import uk.gov.hmcts.ecm.common.exceptions.PdfServiceException;
import uk.gov.hmcts.ecm.common.service.pdf.et1.GenericServiceUtil;
import uk.gov.hmcts.ecm.common.service.pdf.et3.ET3FormMapper;
import uk.gov.hmcts.ecm.common.service.pdf.et3.util.GenericServiceException;
import uk.gov.hmcts.et.common.model.ccd.CaseData;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

import static java.util.Objects.requireNonNull;
import static uk.gov.hmcts.ecm.common.constants.PdfMapperConstants.CREATE_PDF;
import static uk.gov.hmcts.ecm.common.constants.PdfMapperConstants.HELVETICA_PDFBOX_CHARACTER_CODE_1;
import static uk.gov.hmcts.ecm.common.constants.PdfMapperConstants.HELVETICA_PDFBOX_CHARACTER_CODE_2;
import static uk.gov.hmcts.ecm.common.constants.PdfMapperConstants.PDF_TYPE_ET1;
import static uk.gov.hmcts.ecm.common.constants.PdfMapperConstants.PDF_TYPE_ET3;
import static uk.gov.hmcts.ecm.common.constants.PdfMapperConstants.TIMES_NEW_ROMAN_PDFBOX_CHARACTER_CODE;
import static uk.gov.hmcts.ecm.common.service.pdf.et3.ET3FormConstants.UNABLE_TO_MAP_RESPONDENT_TO_ET3_FORM;

@Slf4j
@Service
@RequiredArgsConstructor
public class PdfService {
    private final ET1PdfMapperService et1PdfMapperService;

    /**
     * Converts a {@link CaseData} class object into a PDF document
     * using template (ver. ET1_0224)
     *
     * @param caseData  The data that is to be converted into PDF
     * @param pdfSource The source location of the PDF file to be used as the template
     * @return A byte array that contains the PDF document.
     */
    public byte[] convertCaseToPdf(CaseData caseData, String pdfSource, String pdfType, String clientType, String event)
            throws PdfServiceException {
        byte[] pdfDocumentBytes;
        try {
            pdfDocumentBytes = createPdf(caseData, pdfSource, pdfType, clientType, event);
        } catch (IOException ioe) {
            throw new PdfServiceException("Failed to convert to PDF", ioe);
        }
        return pdfDocumentBytes;
    }

    /**
     * Populates a PDF document with data stored in the case data parameter.
     *
     * @param caseData  {@link CaseData} object with information in which to populate the PDF with
     * @param pdfSource file name of the PDF template used to create the PDF
     * @return a byte array of the generated PDF file.
     * @throws IOException if there is an issue reading the PDF template
     */
    public byte[] createPdf(CaseData caseData,
                            String pdfSource,
                            String pdfType,
                            String clientType,
                            String event) throws IOException,
            PdfServiceException {
        ClassLoader cl = Thread.currentThread().getContextClassLoader();
        File pdfFile = new File(requireNonNull(cl.getResource(pdfSource)).getFile());

        if (ObjectUtils.isEmpty(pdfFile)) {
            return new byte[0];
        }

        try (PDDocument pdfDocument = Loader.loadPDF(new RandomAccessReadBufferedFile(pdfFile) {})) {
            Set<Map.Entry<String, Optional<String>>> pdfEntriesMap = null;
            if (PDF_TYPE_ET1.equals(pdfType)) {
                pdfEntriesMap = this.et1PdfMapperService.mapHeadersToPdf(caseData).entrySet();
            } else if (PDF_TYPE_ET3.equals(pdfType)) {
                Map<String, Optional<String>> pdfMap;
                try {
                    pdfMap = ET3FormMapper.mapEt3Form(caseData, event, clientType);
                } catch (GenericServiceException e) {
                    GenericServiceUtil.logException(UNABLE_TO_MAP_RESPONDENT_TO_ET3_FORM,
                            caseData.getEthosCaseReference(),
                            UNABLE_TO_MAP_RESPONDENT_TO_ET3_FORM,
                            "PdfService",
                            CREATE_PDF);
                    throw new PdfServiceException("Failed to convert to PDF", e);
                }
                pdfEntriesMap = pdfMap.entrySet();
            }
            if (pdfEntriesMap == null) {
                GenericServiceUtil.logException(UNABLE_TO_MAP_RESPONDENT_TO_ET3_FORM,
                        caseData.getEthosCaseReference(),
                        UNABLE_TO_MAP_RESPONDENT_TO_ET3_FORM,
                        "PdfService",
                        CREATE_PDF);
                throw new PdfServiceException("Failed to convert to PDF",
                        new Exception("Unable to map case data to et3 PDF form"));
            }
            PDDocumentCatalog pdDocumentCatalog = pdfDocument.getDocumentCatalog();
            PDAcroForm pdfForm = pdDocumentCatalog.getAcroForm();
            PDResources defaultResources = pdfForm.getDefaultResources();
            defaultResources.put(COSName.getPDFName(HELVETICA_PDFBOX_CHARACTER_CODE_1),
                    new PDType1Font(Standard14Fonts.FontName.HELVETICA));
            defaultResources.put(COSName.getPDFName(HELVETICA_PDFBOX_CHARACTER_CODE_2),
                    new PDType1Font(Standard14Fonts.FontName.HELVETICA));
            defaultResources.put(COSName.getPDFName(TIMES_NEW_ROMAN_PDFBOX_CHARACTER_CODE),
                    new PDType1Font(Standard14Fonts.FontName.TIMES_ROMAN));

            pdfEntriesMap.forEach(entry -> {
                String entryKey = entry.getKey();
                Optional<String> entryValue = entry.getValue();
                if (entryValue.isPresent()) {
                    try {
                        PDField pdfField = pdfForm.getField(entryKey);
                        pdfField.setValue(entryValue.get());
                    } catch (Exception e) {
                        GenericServiceUtil.logException("Error while parsing PDF file for entry key \""
                                                        + entryKey, caseData.getEthosCaseReference(), e.getMessage(),
                                this.getClass().getName(), CREATE_PDF);
                    }
                }
            });
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            pdfDocument.save(byteArrayOutputStream);
            return byteArrayOutputStream.toByteArray();
        }
    }

}
