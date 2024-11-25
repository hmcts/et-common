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
import static uk.gov.hmcts.ecm.common.service.pdf.et3.ET3FormConstants.CHECKBOX_PDF_EMPLOYER_CONTRACT_CLAIM_FIELD_YES;
import static uk.gov.hmcts.ecm.common.service.pdf.et3.ET3FormConstants.STRING_EMPTY;
import static uk.gov.hmcts.ecm.common.service.pdf.et3.ET3FormConstants.TXT_PDF_EMPLOYER_CONTRACT_CLAIM_FIELD_DETAILS;
import static uk.gov.hmcts.ecm.common.service.pdf.et3.ET3FormConstants.YES_CAPITALISED;
import static uk.gov.hmcts.ecm.common.service.pdf.et3.ET3FormConstants.YES_LOWERCASE;
import static uk.gov.hmcts.ecm.common.service.pdf.et3.ET3FormEmployerContractClaimMapper.mapEmployerContractClaim;
import static uk.gov.hmcts.ecm.common.service.pdf.et3.ET3FormTestConstants.TEST_DUMMY_VALUE;
import static uk.gov.hmcts.ecm.common.service.pdf.et3.ET3FormTestConstants.TEST_ET3_FORM_CASE_DATA_FILE;
import static uk.gov.hmcts.ecm.common.service.pdf.et3.ET3FormTestConstants.TEST_PDF_RESPONSE_EMPLOYER_CONTRACT_CLAIM_CORRECT_DETAILS;
import static uk.gov.hmcts.ecm.common.service.pdf.et3.util.ET3FormTestUtil.getCheckboxValue;
import static uk.gov.hmcts.ecm.common.service.pdf.et3.util.ET3FormTestUtil.getCorrectedDetailValue;
import static uk.gov.hmcts.ecm.common.service.pdf.et3.util.ET3FormUtil.cloneObject;

class ET3FormEmployerContractClaimMapperTest {

    private ConcurrentMap<String, Optional<String>> pdfFields;

    @BeforeEach
    void beforeEach() {

        pdfFields = new ConcurrentHashMap<>();
    }

    @ParameterizedTest
    @MethodSource("provideMapEmployerContractClaimTestData")
    void testMapEmployerContractClaim(RespondentSumType respondentSumType) {
        mapEmployerContractClaim(respondentSumType, pdfFields);

        assertThat(pdfFields.get(CHECKBOX_PDF_EMPLOYER_CONTRACT_CLAIM_FIELD_YES)).contains(
                getCheckboxValue(respondentSumType.getEt3ResponseEmployerClaim(), YES_CAPITALISED, YES_LOWERCASE));
        assertThat(pdfFields.get(TXT_PDF_EMPLOYER_CONTRACT_CLAIM_FIELD_DETAILS)).contains(
                getCorrectedDetailValue(respondentSumType.getEt3ResponseEmployerClaim(), YES_CAPITALISED,
                        respondentSumType.getEt3ResponseEmployerClaimDetails(),
                        TEST_PDF_RESPONSE_EMPLOYER_CONTRACT_CLAIM_CORRECT_DETAILS));
    }

    private static Stream<RespondentSumType> provideMapEmployerContractClaimTestData() {
        CaseData caseData = ResourceLoader.fromString(TEST_ET3_FORM_CASE_DATA_FILE, CaseData.class);
        RespondentSumType respondentSumTypeAllValues =
                caseData.getRespondentCollection().stream().filter(r -> caseData.getSubmitEt3Respondent()
                                .getSelectedLabel().equals(r.getValue().getRespondentName()))
                        .toList().get(0).getValue();

        RespondentSumType respondentSumTypeEmployerContractClaimYesDetailsNull =
                cloneObject(respondentSumTypeAllValues, RespondentSumType.class);
        respondentSumTypeEmployerContractClaimYesDetailsNull.setEt3ResponseEmployerClaimDetails(null);

        RespondentSumType respondentSumTypeEmployerContractClaimYesDetailsEmpty =
                cloneObject(respondentSumTypeAllValues, RespondentSumType.class);
        respondentSumTypeEmployerContractClaimYesDetailsEmpty.setEt3ResponseEmployerClaimDetails(STRING_EMPTY);

        RespondentSumType respondentSumTypeEmployerContractClaimNullDetailsNull =
                cloneObject(respondentSumTypeAllValues, RespondentSumType.class);
        respondentSumTypeEmployerContractClaimNullDetailsNull.setEt3ResponseEmployerClaim(null);
        respondentSumTypeEmployerContractClaimNullDetailsNull.setEt3ResponseEmployerClaimDetails(null);

        RespondentSumType respondentSumTypeEmployerContractClaimNullDetailsDummy =
                cloneObject(respondentSumTypeAllValues, RespondentSumType.class);
        respondentSumTypeEmployerContractClaimNullDetailsDummy.setEt3ResponseEmployerClaim(null);
        respondentSumTypeEmployerContractClaimNullDetailsDummy.setEt3ResponseEmployerClaimDetails(TEST_DUMMY_VALUE);

        RespondentSumType respondentSumTypeEmployerContractClaimNullDetailsEmpty =
                cloneObject(respondentSumTypeAllValues, RespondentSumType.class);
        respondentSumTypeEmployerContractClaimNullDetailsEmpty.setEt3ResponseEmployerClaim(null);
        respondentSumTypeEmployerContractClaimNullDetailsEmpty.setEt3ResponseEmployerClaimDetails(STRING_EMPTY);

        RespondentSumType respondentSumTypeEmployerContractClaimEmptyDetailsNull =
                cloneObject(respondentSumTypeAllValues, RespondentSumType.class);
        respondentSumTypeEmployerContractClaimEmptyDetailsNull.setEt3ResponseEmployerClaim(STRING_EMPTY);
        respondentSumTypeEmployerContractClaimEmptyDetailsNull.setEt3ResponseEmployerClaimDetails(null);

        RespondentSumType respondentSumTypeEmployerContractClaimEmptyDetailsEmpty =
                cloneObject(respondentSumTypeAllValues, RespondentSumType.class);
        respondentSumTypeEmployerContractClaimEmptyDetailsEmpty.setEt3ResponseEmployerClaim(STRING_EMPTY);
        respondentSumTypeEmployerContractClaimEmptyDetailsEmpty.setEt3ResponseEmployerClaimDetails(STRING_EMPTY);

        RespondentSumType respondentSumTypeEmployerContractClaimEmptyDetailsDummy =
                cloneObject(respondentSumTypeAllValues, RespondentSumType.class);
        respondentSumTypeEmployerContractClaimEmptyDetailsDummy.setEt3ResponseEmployerClaim(STRING_EMPTY);
        respondentSumTypeEmployerContractClaimEmptyDetailsDummy.setEt3ResponseEmployerClaimDetails(TEST_DUMMY_VALUE);

        return Stream.of(respondentSumTypeAllValues, respondentSumTypeEmployerContractClaimYesDetailsNull,
                respondentSumTypeEmployerContractClaimYesDetailsEmpty,
                respondentSumTypeEmployerContractClaimNullDetailsNull,
                respondentSumTypeEmployerContractClaimNullDetailsDummy,
                respondentSumTypeEmployerContractClaimNullDetailsEmpty,
                respondentSumTypeEmployerContractClaimEmptyDetailsNull,
                respondentSumTypeEmployerContractClaimEmptyDetailsEmpty,
                respondentSumTypeEmployerContractClaimEmptyDetailsDummy);

    }

}
