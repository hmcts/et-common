package uk.gov.hmcts.ecm.common.service.pdf;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.util.Arrays;

/**
 * Trivial implementation of the {@link MultipartFile} interface to wrap a byte[] decoded from a PDF encoded String.
 **/
@SuppressWarnings({"NullableProblems"})
@Slf4j
public class PdfDecodedMultipartFile implements MultipartFile {
    private final byte[] fileContent;

    private final String originalName;

    private final String contentType;

    private final String documentDescription;

    public PdfDecodedMultipartFile(byte[] fileContent,
                                   String originalName,
                                   String contentType,
                                   String documentDescription) {
        this.fileContent = fileContent == null ? null : Arrays.copyOf(fileContent, fileContent.length);
        this.originalName = originalName;
        this.contentType = contentType;
        this.documentDescription = documentDescription;
    }

    @Override
    public String getName() {
        return null;
    }

    @Override
    public String getOriginalFilename() {
        return originalName;
    }

    @Override
    public String getContentType() {
        return this.contentType;
    }

    public String getDocumentDescription() {
        return this.documentDescription;
    }

    @Override
    public boolean isEmpty() {
        return fileContent == null || fileContent.length == 0;
    }

    @Override
    public long getSize() {
        return fileContent == null ? 0 : fileContent.length;
    }

    @Override
    public byte[] getBytes() {
        if (fileContent == null) {
            return new byte[0];
        } else {
            return Arrays.copyOf(fileContent, fileContent.length);
        }
    }

    @Override
    public InputStream getInputStream() {
        return new ByteArrayInputStream(fileContent);
    }

    @Override
    public void transferTo(File dest) {
        try (OutputStream os = Files.newOutputStream(dest.toPath())) {
            os.write(fileContent);
        } catch (IOException e) {
            log.error("Error closing output stream");
        }
    }
}
