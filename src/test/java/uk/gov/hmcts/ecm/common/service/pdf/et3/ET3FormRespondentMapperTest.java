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

import static org.apache.commons.lang3.StringUtils.isNotBlank;
import static org.assertj.core.api.Assertions.assertThat;
import static uk.gov.hmcts.ecm.common.service.pdf.et3.ET3FormRespondentMapper.mapRespondent;
import static uk.gov.hmcts.ecm.common.service.pdf.et3.util.ET3FormUtil.cloneObject;

class ET3FormRespondentMapperTest {

    private ConcurrentMap<String, Optional<String>> pdfFields;

    @BeforeEach
    void beforeEach() {
        pdfFields = new ConcurrentHashMap<>();
    }

    @ParameterizedTest
    @MethodSource("provideMapRespondentTestData")
    void testMapRespondent(RespondentSumType respondentSumType) {
        mapRespondent(respondentSumType, pdfFields);
        String selectedTitle = respondentSumType.getEt3ResponseRespondentPreferredTitle();
        assertThat(pdfFields.get(ET3FormConstants.CHECKBOX_PDF_RESPONDENT_FIELD_TITLE_MR)).contains(
                ET3FormConstants.CHECKBOX_PDF_RESPONDENT_EXPECTED_VALUE_TITLE_MR.equals(selectedTitle)
                        ? ET3FormConstants.YES_CAPITALISED : ET3FormConstants.OFF_CAPITALISED);
        assertThat(pdfFields.get(ET3FormConstants.CHECKBOX_PDF_RESPONDENT_FIELD_TITLE_MS)).contains(
                ET3FormConstants.CHECKBOX_PDF_RESPONDENT_EXPECTED_VALUE_TITLE_MS.equals(selectedTitle)
                        ? ET3FormConstants.YES_CAPITALISED : ET3FormConstants.OFF_CAPITALISED);
        assertThat(pdfFields.get(ET3FormConstants.CHECKBOX_PDF_RESPONDENT_FIELD_TITLE_MRS)).contains(
                ET3FormConstants.CHECKBOX_PDF_RESPONDENT_EXPECTED_VALUE_TITLE_MRS.equals(selectedTitle)
                        ? ET3FormConstants.YES_CAPITALISED : ET3FormConstants.OFF_CAPITALISED);
        assertThat(pdfFields.get(ET3FormConstants.CHECKBOX_PDF_RESPONDENT_FIELD_TITLE_MISS)).contains(
                ET3FormConstants.CHECKBOX_PDF_RESPONDENT_EXPECTED_VALUE_TITLE_MISS.equals(selectedTitle)
                        ? ET3FormConstants.YES_CAPITALISED : ET3FormConstants.OFF_CAPITALISED);
        if (isOtherTitle(selectedTitle)) {
            assertThat(pdfFields.get(ET3FormConstants.CHECKBOX_PDF_RESPONDENT_FIELD_TITLE_OTHER))
                    .contains(ET3FormConstants.YES_CAPITALISED);
            assertThat(pdfFields.get(ET3FormConstants.TXT_PDF_RESPONDENT_FIELD_TITLE_OTHER)).contains(selectedTitle);
        }
        assertThat(pdfFields.get(ET3FormConstants.TXT_PDF_RESPONDENT_FIELD_NAME))
                .contains(ET3FormTestConstants.TEST_PDF_RESPONDENT_EXPECTED_VALUE_NAME);
        assertThat(pdfFields.get(ET3FormConstants.TXT_PDF_RESPONDENT_FIELD_NUMBER))
                .contains(ET3FormTestConstants.TEST_PDF_RESPONDENT_EXPECTED_VALUE_NUMBER);
        assertThat(pdfFields.get(ET3FormConstants.TXT_PDF_RESPONDENT_FIELD_TYPE))
                .contains(ET3FormTestConstants.TEST_PDF_RESPONDENT_EXPECTED_VALUE_TYPE);
        assertThat(pdfFields.get(ET3FormConstants.TXT_PDF_RESPONDENT_FIELD_CONTACT_NAME))
                .contains(ET3FormTestConstants.TEST_PDF_RESPONDENT_EXPECTED_VALUE_CONTACT_NAME);
        assertThat(pdfFields.get(ET3FormConstants.TXT_PDF_RESPONDENT_FIELD_POSTCODE))
                .contains(ET3FormTestConstants.TEST_PDF_RESPONDENT_EXPECTED_VALUE_POSTCODE);
        assertThat(pdfFields.get(ET3FormConstants.TXT_PDF_RESPONDENT_FIELD_DX))
                .contains(ET3FormTestConstants.TEST_PDF_RESPONDENT_EXPECTED_VALUE_DX);
        assertThat(pdfFields.get(ET3FormConstants.TXT_PDF_RESPONDENT_FIELD_PHONE_NUMBER))
                .contains(ET3FormTestConstants.TEST_PDF_RESPONDENT_EXPECTED_VALUE_PHONE_NUMBER);
        assertThat(pdfFields.get(ET3FormConstants.TXT_PDF_RESPONDENT_FIELD_MOBILE_NUMBER))
                .contains(ET3FormTestConstants.TEST_PDF_RESPONDENT_EXPECTED_VALUE_MOBILE_NUMBER);
        assertThat(pdfFields.get(ET3FormConstants.CHECKBOX_PDF_RESPONDENT_FIELD_CONTACT_TYPE_EMAIL))
                .contains(ET3FormConstants.EMAIL_LOWERCASE);
        assertThat(pdfFields.get(ET3FormConstants.CHECKBOX_PDF_RESPONDENT_FIELD_CONTACT_TYPE_POST))
                .contains(ET3FormConstants.OFF_CAPITALISED);
        assertThat(pdfFields.get(ET3FormConstants.TXT_PDF_RESPONDENT_FIELD_EMAIL))
                .contains(ET3FormTestConstants.TEST_PDF_RESPONDENT_EXPECTED_VALUE_EMAIL);
        assertThat(pdfFields.get(ET3FormConstants.CHECKBOX_PDF_RESPONDENT_FIELD_HEARING_TYPE_VIDEO))
                .contains(ET3FormTestConstants.TEST_PDF_RESPONDENT_EXPECTED_VALUE_HEARING_TYPE_VIDEO);
        assertThat(pdfFields.get(ET3FormConstants.CHECKBOX_PDF_RESPONDENT_FIELD_HEARING_TYPE_PHONE))
                .contains(ET3FormTestConstants.TEST_PDF_RESPONDENT_EXPECTED_VALUE_HEARING_TYPE_PHONE);
        assertThat(pdfFields.get(ET3FormConstants.TXT_PDF_RESPONDENT_FIELD_EMPLOYEE_NUMBER_GREAT_BRITAIN))
                .contains(ET3FormTestConstants.TEST_PDF_RESPONDENT_EXPECTED_VALUE_EMPLOYEE_NUMBER_GREAT_BRITAIN);
        assertThat(pdfFields.get(ET3FormConstants.CHECKBOX_PDF_RESPONDENT_FIELD_MORE_THAN_ONE_SITE_GREAT_BRITAIN_YES))
                .contains(ET3FormTestConstants.TEST_PDF_RESPONDENT_EXPECTED_VALUE_MORE_THAN_ONE_SITE_GREAT_BRITAIN_YES);
        assertThat(pdfFields.get(ET3FormConstants.CHECKBOX_PDF_RESPONDENT_FIELD_MORE_THAN_ONE_SITE_GREAT_BRITAIN_NO))
                .contains(ET3FormConstants.OFF_CAPITALISED);
        assertThat(pdfFields.get(ET3FormConstants.TXT_PDF_RESPONDENT_FIELD_EMPLOYEE_NUMBER_CLAIMANT_WORK_PLACE))
                .contains(ET3FormTestConstants.TEST_PDF_RESPONDENT_EXPECTED_VALUE_EMPLOYEE_NUMBER_CLAIMANT_WORK_PLACE);
        assertThat(pdfFields.get(ET3FormConstants.TXT_PDF_RESPONDENT_FIELD_ADDRESS))
                .contains(ET3FormTestConstants.TEST_PDF_RESPONDENT_EXPECTED_VALUE_ADDRESS);

    }

    private static boolean isOtherTitle(String selectedTitle) {
        return isNotBlank(selectedTitle)
                && !ET3FormConstants.CHECKBOX_PDF_RESPONDENT_EXPECTED_VALUE_TITLE_MR.equals(selectedTitle)
                && !ET3FormConstants.CHECKBOX_PDF_RESPONDENT_EXPECTED_VALUE_TITLE_MS.equals(selectedTitle)
                && !ET3FormConstants.CHECKBOX_PDF_RESPONDENT_EXPECTED_VALUE_TITLE_MRS.equals(selectedTitle)
                && !ET3FormConstants.CHECKBOX_PDF_RESPONDENT_EXPECTED_VALUE_TITLE_MISS.equals(selectedTitle);
    }

    private static Stream<RespondentSumType> provideMapRespondentTestData() {
        CaseData caseData = ResourceLoader
                .fromString(ET3FormTestConstants.TEST_ET3_FORM_CASE_DATA_FILE, CaseData.class);
        RespondentSumType respondentSumType = caseData.getRespondentCollection().stream()
                .filter(r -> caseData.getSubmitEt3Respondent()
                        .getSelectedLabel().equals(r.getValue().getRespondentName()))
                .toList().get(0).getValue();
        RespondentSumType respondentSumTypeTitleMr = cloneObject(respondentSumType, RespondentSumType.class);
        respondentSumTypeTitleMr
                .setEt3ResponseRespondentPreferredTitle(
                        ET3FormConstants.CHECKBOX_PDF_RESPONDENT_EXPECTED_VALUE_TITLE_MR);
        RespondentSumType respondentSumTypeTitleMrs = cloneObject(respondentSumTypeTitleMr, RespondentSumType.class);
        respondentSumTypeTitleMrs
                .setEt3ResponseRespondentPreferredTitle(
                        ET3FormConstants.CHECKBOX_PDF_RESPONDENT_EXPECTED_VALUE_TITLE_MRS);
        RespondentSumType respondentSumTypeTitleMs = cloneObject(respondentSumTypeTitleMr, RespondentSumType.class);
        respondentSumTypeTitleMs
                .setEt3ResponseRespondentPreferredTitle(
                        ET3FormConstants.CHECKBOX_PDF_RESPONDENT_EXPECTED_VALUE_TITLE_MS);
        RespondentSumType respondentSumTypeTitleMiss = cloneObject(respondentSumTypeTitleMr, RespondentSumType.class);
        respondentSumTypeTitleMiss
                .setEt3ResponseRespondentPreferredTitle(
                        ET3FormConstants.CHECKBOX_PDF_RESPONDENT_EXPECTED_VALUE_TITLE_MISS);
        RespondentSumType respondentSumTypeTitleOther = cloneObject(respondentSumTypeTitleMr, RespondentSumType.class);
        respondentSumTypeTitleOther
                .setEt3ResponseRespondentPreferredTitle(
                        ET3FormTestConstants.TEST_CHECKBOX_PDF_RESPONDENT_EXPECTED_VALUE_TITLE_OTHER);
        return Stream.of(respondentSumType, respondentSumTypeTitleMr, respondentSumTypeTitleMrs,
                respondentSumTypeTitleMs, respondentSumTypeTitleMiss, respondentSumTypeTitleOther);
    }

}
