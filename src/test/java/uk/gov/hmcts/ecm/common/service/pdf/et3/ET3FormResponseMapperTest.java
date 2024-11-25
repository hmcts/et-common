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
import static uk.gov.hmcts.ecm.common.service.pdf.et3.ET3FormConstants.CHECKBOX_PDF_RESPONSE_FIELD_CONTEST_CLAIM_NO;
import static uk.gov.hmcts.ecm.common.service.pdf.et3.ET3FormConstants.CHECKBOX_PDF_RESPONSE_FIELD_CONTEST_CLAIM_YES;
import static uk.gov.hmcts.ecm.common.service.pdf.et3.ET3FormConstants.NO_CAPITALISED;
import static uk.gov.hmcts.ecm.common.service.pdf.et3.ET3FormConstants.NO_LOWERCASE;
import static uk.gov.hmcts.ecm.common.service.pdf.et3.ET3FormConstants.STRING_EMPTY;
import static uk.gov.hmcts.ecm.common.service.pdf.et3.ET3FormConstants.TXT_PDF_RESPONSE_FIELD_CONTEST_CLAIM_CORRECT_FACTS;
import static uk.gov.hmcts.ecm.common.service.pdf.et3.ET3FormConstants.YES_CAPITALISED;
import static uk.gov.hmcts.ecm.common.service.pdf.et3.ET3FormConstants.YES_LOWERCASE;
import static uk.gov.hmcts.ecm.common.service.pdf.et3.ET3FormResponseMapper.mapResponse;
import static uk.gov.hmcts.ecm.common.service.pdf.et3.ET3FormTestConstants.TEST_DUMMY_VALUE;
import static uk.gov.hmcts.ecm.common.service.pdf.et3.ET3FormTestConstants.TEST_ET3_FORM_CASE_DATA_FILE;
import static uk.gov.hmcts.ecm.common.service.pdf.et3.ET3FormTestConstants.TEST_PDF_RESPONSE_CONTEST_CLAIM_CORRECT_FACTS;
import static uk.gov.hmcts.ecm.common.service.pdf.et3.util.ET3FormTestUtil.getCheckboxValue;
import static uk.gov.hmcts.ecm.common.service.pdf.et3.util.ET3FormTestUtil.getCorrectedDetailValue;
import static uk.gov.hmcts.ecm.common.service.pdf.et3.util.ET3FormUtil.cloneObject;

class ET3FormResponseMapperTest {

    private ConcurrentMap<String, Optional<String>> pdfFields;

    @BeforeEach
    void beforeEach() {

        pdfFields = new ConcurrentHashMap<>();
    }

    @ParameterizedTest
    @MethodSource("provideMapResponseTestData")
    void testMapResponse(RespondentSumType respondentSumType) {
        mapResponse(respondentSumType, pdfFields);
        assertThat(pdfFields.get(CHECKBOX_PDF_RESPONSE_FIELD_CONTEST_CLAIM_YES)).contains(
                getCheckboxValue(respondentSumType.getEt3ResponseRespondentContestClaim(),
                        YES_CAPITALISED, YES_LOWERCASE));
        assertThat(pdfFields.get(CHECKBOX_PDF_RESPONSE_FIELD_CONTEST_CLAIM_NO)).contains(
                getCheckboxValue(respondentSumType.getEt3ResponseRespondentContestClaim(),
                        NO_CAPITALISED, NO_LOWERCASE));
        assertThat(pdfFields.get(TXT_PDF_RESPONSE_FIELD_CONTEST_CLAIM_CORRECT_FACTS)).contains(
                getCorrectedDetailValue(respondentSumType.getEt3ResponseRespondentContestClaim(), YES_CAPITALISED,
                        respondentSumType.getEt3ResponseContestClaimDetails(),
                        TEST_PDF_RESPONSE_CONTEST_CLAIM_CORRECT_FACTS)
        );
    }

    private static Stream<RespondentSumType> provideMapResponseTestData() {
        CaseData caseData = ResourceLoader.fromString(TEST_ET3_FORM_CASE_DATA_FILE, CaseData.class);
        RespondentSumType respondentSumTypeAllValues =
                caseData.getRespondentCollection().stream().filter(r -> caseData.getSubmitEt3Respondent()
                                .getSelectedLabel().equals(r.getValue().getRespondentName()))
                        .toList().get(0).getValue();
        RespondentSumType respondentSumTypeContestYesDetailsNull = cloneObject(respondentSumTypeAllValues,
                RespondentSumType.class);
        respondentSumTypeContestYesDetailsNull.setEt3ResponseRespondentContestClaim(YES_CAPITALISED);
        respondentSumTypeContestYesDetailsNull.setEt3ResponseContestClaimDetails(null);

        RespondentSumType respondentSumTypeContestYesDetailsDummyValue = cloneObject(respondentSumTypeAllValues,
                RespondentSumType.class);
        respondentSumTypeContestYesDetailsDummyValue.setEt3ResponseRespondentContestClaim(NO_CAPITALISED);
        respondentSumTypeContestYesDetailsDummyValue.setEt3ResponseContestClaimDetails(TEST_DUMMY_VALUE);

        RespondentSumType respondentSumTypeContestYesDetailsEmpty = cloneObject(respondentSumTypeAllValues,
                RespondentSumType.class);
        respondentSumTypeContestYesDetailsEmpty.setEt3ResponseRespondentContestClaim(YES_CAPITALISED);
        respondentSumTypeContestYesDetailsEmpty.setEt3ResponseContestClaimDetails(STRING_EMPTY);

        return Stream.of(respondentSumTypeAllValues, respondentSumTypeContestYesDetailsNull,
                respondentSumTypeContestYesDetailsDummyValue, respondentSumTypeContestYesDetailsEmpty);

    }

}
