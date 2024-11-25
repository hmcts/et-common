package uk.gov.hmcts.ecm.common.service.pdf.et3;

import org.apache.commons.lang3.ObjectUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.util.CollectionUtils;
import uk.gov.hmcts.ecm.common.service.utils.ResourceLoader;
import uk.gov.hmcts.et.common.model.ccd.CaseData;
import uk.gov.hmcts.et.common.model.ccd.types.RespondentSumType;

import java.time.LocalDate;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static uk.gov.hmcts.ecm.common.service.pdf.et3.ET3FormConstants.DATE_FORMAT_DD_MM_YYYY_DASH;
import static uk.gov.hmcts.ecm.common.service.pdf.et3.ET3FormConstants.DATE_FORMAT_YYYY_MM_DD_DASH;
import static uk.gov.hmcts.ecm.common.service.pdf.et3.ET3FormConstants.STRING_EMPTY;
import static uk.gov.hmcts.ecm.common.service.pdf.et3.ET3FormConstants.SUBMIT_ET3_CITIZEN;
import static uk.gov.hmcts.ecm.common.service.pdf.et3.ET3FormConstants.TXT_PDF_HEADER_FIELD_CASE_NUMBER;
import static uk.gov.hmcts.ecm.common.service.pdf.et3.ET3FormConstants.TXT_PDF_HEADER_FIELD_DATE_RECEIVED;
import static uk.gov.hmcts.ecm.common.service.pdf.et3.ET3FormConstants.TXT_PDF_HEADER_FIELD_RTF;
import static uk.gov.hmcts.ecm.common.service.pdf.et3.ET3FormConstants.TXT_PDF_HEADER_VALUE_ADDITIONAL_DOCUMENT_EXISTS;
import static uk.gov.hmcts.ecm.common.service.pdf.et3.ET3FormHeaderMapper.mapHeader;
import static uk.gov.hmcts.ecm.common.service.pdf.et3.ET3FormTestConstants.TEST_ET3_FORM_CASE_DATA_FILE;
import static uk.gov.hmcts.ecm.common.service.pdf.et3.ET3FormTestConstants.TEST_PDF_HEADER_VALUE_CASE_NUMBER;
import static uk.gov.hmcts.ecm.common.service.pdf.et3.util.ET3FormUtil.cloneObject;
import static uk.gov.hmcts.ecm.common.service.pdf.et3.util.ET3FormUtil.formatDate;

class ET3FormHeaderMapperTest {

    private ConcurrentMap<String, Optional<String>> pdfFields;

    @BeforeEach
    void beforeEach() {
        pdfFields = new ConcurrentHashMap<>();
    }

    @ParameterizedTest
    @MethodSource("provideMapHeaderTestData")
    void testMapHeader(CaseData caseData, RespondentSumType respondentSumType) {
        mapHeader(caseData, respondentSumType, pdfFields, SUBMIT_ET3_CITIZEN);
        assertThat(pdfFields.get(TXT_PDF_HEADER_FIELD_CASE_NUMBER)).contains(TEST_PDF_HEADER_VALUE_CASE_NUMBER);
        assertThat(pdfFields.get(TXT_PDF_HEADER_FIELD_DATE_RECEIVED)).contains(
                formatDate(LocalDate.now().toString(), DATE_FORMAT_YYYY_MM_DD_DASH, DATE_FORMAT_DD_MM_YYYY_DASH));
        if (ObjectUtils.isEmpty(respondentSumType.getEt3ResponseRespondentSupportDocument())
                && ObjectUtils.isEmpty(respondentSumType.getEt3ResponseEmployerClaimDocument())
                && CollectionUtils.isEmpty(respondentSumType.getEt3ResponseContestClaimDocument())) {
            assertEquals(pdfFields.get(TXT_PDF_HEADER_FIELD_RTF), Optional.of(STRING_EMPTY));
        } else {
            assertThat(pdfFields.get(TXT_PDF_HEADER_FIELD_RTF))
                    .contains(TXT_PDF_HEADER_VALUE_ADDITIONAL_DOCUMENT_EXISTS.formatted(3));
        }
    }

    private static Stream<Arguments> provideMapHeaderTestData() {
        CaseData caseData = ResourceLoader.fromString(TEST_ET3_FORM_CASE_DATA_FILE, CaseData.class);
        RespondentSumType respondentSumTypeWithDocuments = caseData.getRespondentCollection().stream()
                .filter(r -> caseData.getSubmitEt3Respondent()
                        .getSelectedLabel().equals(r.getValue().getRespondentName()))
                .toList().get(0).getValue();
        RespondentSumType respondentSumTypeWithoutDocuments = cloneObject(respondentSumTypeWithDocuments,
                RespondentSumType.class);
        respondentSumTypeWithoutDocuments.setEt3ResponseRespondentSupportDocument(null);
        respondentSumTypeWithoutDocuments.setEt3ResponseEmployerClaimDocument(null);
        respondentSumTypeWithoutDocuments.setEt3ResponseContestClaimDocument(null);
        return Stream.of(Arguments.of(caseData, respondentSumTypeWithDocuments),
                Arguments.of(caseData, respondentSumTypeWithoutDocuments));
    }

}
