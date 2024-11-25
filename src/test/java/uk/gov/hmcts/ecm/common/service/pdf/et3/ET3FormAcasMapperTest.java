package uk.gov.hmcts.ecm.common.service.pdf.et3;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import uk.gov.hmcts.ecm.common.service.utils.ResourceLoader;
import uk.gov.hmcts.et.common.model.ccd.CaseData;
import uk.gov.hmcts.et.common.model.ccd.types.RespondentSumType;

import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static uk.gov.hmcts.ecm.common.service.pdf.et3.ET3FormAcasMapper.mapAcas;
import static uk.gov.hmcts.ecm.common.service.pdf.et3.ET3FormConstants.CHECKBOX_PDF_ACAS_FIELD_AGREEMENT_NO;
import static uk.gov.hmcts.ecm.common.service.pdf.et3.ET3FormConstants.CHECKBOX_PDF_ACAS_FIELD_AGREEMENT_YES;
import static uk.gov.hmcts.ecm.common.service.pdf.et3.ET3FormConstants.NO_CAPITALISED;
import static uk.gov.hmcts.ecm.common.service.pdf.et3.ET3FormConstants.OFF_CAPITALISED;
import static uk.gov.hmcts.ecm.common.service.pdf.et3.ET3FormConstants.STRING_EMPTY;
import static uk.gov.hmcts.ecm.common.service.pdf.et3.ET3FormConstants.TXT_PDF_ACAS_FIELD_AGREEMENT_NO_REASON;
import static uk.gov.hmcts.ecm.common.service.pdf.et3.ET3FormConstants.YES_CAPITALISED;
import static uk.gov.hmcts.ecm.common.service.pdf.et3.ET3FormTestConstants.TEST_ET3_FORM_CASE_DATA_FILE;
import static uk.gov.hmcts.ecm.common.service.pdf.et3.ET3FormTestConstants.TEST_PDF_ACAS_EXPECTED_NOT_AGREE_REASON;
import static uk.gov.hmcts.ecm.common.service.pdf.et3.util.ET3FormUtil.cloneObject;

class ET3FormAcasMapperTest {

    private ConcurrentMap<String, Optional<String>> pdfFields;

    @BeforeEach
    void beforeEach() {
        pdfFields = new ConcurrentHashMap<>();
    }

    @ParameterizedTest
    @MethodSource("provideMapAcasTestData")
    void testMapAcas(RespondentSumType respondentSumType) {
        mapAcas(respondentSumType, pdfFields);
        String acasAgreed = respondentSumType.getEt3ResponseAcasAgree();
        assertThat(pdfFields.get(CHECKBOX_PDF_ACAS_FIELD_AGREEMENT_YES)).contains(YES_CAPITALISED.equals(acasAgreed)
                ? YES_CAPITALISED : OFF_CAPITALISED);
        assertThat(pdfFields.get(CHECKBOX_PDF_ACAS_FIELD_AGREEMENT_NO)).contains(NO_CAPITALISED.equals(acasAgreed)
                ? NO_CAPITALISED : OFF_CAPITALISED);
        assertThat(pdfFields.get(TXT_PDF_ACAS_FIELD_AGREEMENT_NO_REASON)).contains(NO_CAPITALISED.equals(acasAgreed)
                ? respondentSumType.getEt3ResponseAcasAgreeReason() : STRING_EMPTY);
    }

    private static Stream<RespondentSumType> provideMapAcasTestData() {
        CaseData caseData = ResourceLoader.fromString(TEST_ET3_FORM_CASE_DATA_FILE, CaseData.class);
        RespondentSumType respondentSumTypeAcasAgreed = caseData.getRespondentCollection().stream()
                .filter(r -> caseData.getSubmitEt3Respondent().getSelectedLabel().equals(
                        r.getValue().getRespondentName())).toList().get(0).getValue();
        RespondentSumType respondentSumTypeAcasNotAgreed =
                cloneObject(respondentSumTypeAcasAgreed, RespondentSumType.class);
        respondentSumTypeAcasNotAgreed.setEt3ResponseAcasAgree(NO_CAPITALISED);
        respondentSumTypeAcasNotAgreed.setEt3ResponseAcasAgreeReason(TEST_PDF_ACAS_EXPECTED_NOT_AGREE_REASON);
        return Stream.of(respondentSumTypeAcasAgreed, respondentSumTypeAcasNotAgreed);
    }

}
