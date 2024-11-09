package uk.gov.hmcts.ecm.common.service.pdf.et3;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import uk.gov.hmcts.ecm.common.service.utils.ResourceLoader;
import uk.gov.hmcts.et.common.model.ccd.CaseData;
import uk.gov.hmcts.et.common.model.ccd.types.RespondentSumType;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static uk.gov.hmcts.ecm.common.service.pdf.et3.ET3FormConstants.CHECKBOX_PDF_DISABILITY_NO;
import static uk.gov.hmcts.ecm.common.service.pdf.et3.ET3FormConstants.CHECKBOX_PDF_DISABILITY_NOT_SURE;
import static uk.gov.hmcts.ecm.common.service.pdf.et3.ET3FormConstants.CHECKBOX_PDF_DISABILITY_YES;
import static uk.gov.hmcts.ecm.common.service.pdf.et3.ET3FormConstants.NO_CAPITALISED;
import static uk.gov.hmcts.ecm.common.service.pdf.et3.ET3FormConstants.NO_LOWERCASE;
import static uk.gov.hmcts.ecm.common.service.pdf.et3.ET3FormConstants.STRING_EMPTY;
import static uk.gov.hmcts.ecm.common.service.pdf.et3.ET3FormConstants.TXT_PDF_DISABILITY_DETAILS;
import static uk.gov.hmcts.ecm.common.service.pdf.et3.ET3FormConstants.YES_CAPITALISED;
import static uk.gov.hmcts.ecm.common.service.pdf.et3.ET3FormConstants.YES_LOWERCASE;
import static uk.gov.hmcts.ecm.common.service.pdf.et3.ET3FormDisabilityMapper.mapDisability;
import static uk.gov.hmcts.ecm.common.service.pdf.et3.ET3FormTestConstants.TEST_DUMMY_VALUE;
import static uk.gov.hmcts.ecm.common.service.pdf.et3.ET3FormTestConstants.TEST_ET3_FORM_CASE_DATA_FILE;
import static uk.gov.hmcts.ecm.common.service.pdf.et3.ET3FormTestConstants.TEST_PDF_DISABILITY_DETAIL;
import static uk.gov.hmcts.ecm.common.service.pdf.et3.util.ET3FormTestUtil.getCheckBoxNotApplicableValue;
import static uk.gov.hmcts.ecm.common.service.pdf.et3.util.ET3FormTestUtil.getCheckboxValue;
import static uk.gov.hmcts.ecm.common.service.pdf.et3.util.ET3FormTestUtil.getCorrectedDetailValue;
import static uk.gov.hmcts.ecm.common.service.pdf.et3.util.ET3FormUtil.cloneObject;

class ET3FormDisabilityMapperTest {

    private ConcurrentMap<String, Optional<String>> pdfFields;

    @BeforeEach
    void beforeEach() {

        pdfFields = new ConcurrentHashMap<>();
    }

    @ParameterizedTest
    @MethodSource("provideMapDisabilityTestData")
    void testMapDisability(RespondentSumType respondentSumType) {
        mapDisability(respondentSumType, pdfFields);
        assertThat(pdfFields.get(CHECKBOX_PDF_DISABILITY_YES)).contains(
                getCheckboxValue(respondentSumType.getEt3ResponseRespondentSupportNeeded(),
                        YES_CAPITALISED, YES_LOWERCASE));
        assertThat(pdfFields.get(CHECKBOX_PDF_DISABILITY_NO)).contains(
                getCheckboxValue(respondentSumType.getEt3ResponseRespondentSupportNeeded(),
                        NO_CAPITALISED, NO_LOWERCASE));
        assertThat(pdfFields.get(CHECKBOX_PDF_DISABILITY_NOT_SURE)).contains(
                getCheckBoxNotApplicableValue(respondentSumType.getEt3ResponseRespondentSupportNeeded(),
                        List.of(YES_CAPITALISED, NO_CAPITALISED), NO_LOWERCASE));
        assertThat(pdfFields.get(TXT_PDF_DISABILITY_DETAILS)).contains(
                getCorrectedDetailValue(respondentSumType.getEt3ResponseRespondentSupportNeeded(), YES_CAPITALISED,
                        respondentSumType.getEt3ResponseRespondentSupportDetails(),
                        TEST_PDF_DISABILITY_DETAIL)
        );
    }

    private static Stream<RespondentSumType> provideMapDisabilityTestData() {
        CaseData caseData = ResourceLoader.fromString(TEST_ET3_FORM_CASE_DATA_FILE, CaseData.class);
        RespondentSumType respondentSumTypeAllValues =
                caseData.getRespondentCollection().stream().filter(r -> caseData.getSubmitEt3Respondent()
                                .getSelectedLabel().equals(r.getValue().getRespondentName()))
                        .toList().get(0).getValue();
        RespondentSumType respondentSumTypeSupportNeededNoDetailsNull = cloneObject(respondentSumTypeAllValues,
                RespondentSumType.class);
        respondentSumTypeSupportNeededNoDetailsNull.setEt3ResponseRespondentSupportNeeded(NO_CAPITALISED);
        respondentSumTypeSupportNeededNoDetailsNull.setEt3ResponseRespondentSupportDetails(null);

        RespondentSumType respondentSumTypeSupportNeededNoDetailsDummyValue = cloneObject(respondentSumTypeAllValues,
                RespondentSumType.class);
        respondentSumTypeSupportNeededNoDetailsDummyValue.setEt3ResponseRespondentSupportNeeded(NO_CAPITALISED);
        respondentSumTypeSupportNeededNoDetailsDummyValue.setEt3ResponseRespondentSupportDetails(TEST_DUMMY_VALUE);

        RespondentSumType respondentSumTypeSupportNeededNoDetailsEmpty = cloneObject(respondentSumTypeAllValues,
                RespondentSumType.class);
        respondentSumTypeSupportNeededNoDetailsEmpty.setEt3ResponseRespondentSupportNeeded(NO_CAPITALISED);
        respondentSumTypeSupportNeededNoDetailsEmpty.setEt3ResponseRespondentSupportDetails(STRING_EMPTY);

        return Stream.of(respondentSumTypeAllValues, respondentSumTypeSupportNeededNoDetailsNull,
                respondentSumTypeSupportNeededNoDetailsDummyValue, respondentSumTypeSupportNeededNoDetailsEmpty);
    }

}
