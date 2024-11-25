package uk.gov.hmcts.ecm.common.service.pdf.et3;

import lombok.SneakyThrows;
import org.apache.commons.lang3.ObjectUtils;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.util.CollectionUtils;
import uk.gov.hmcts.ecm.common.service.utils.ResourceLoader;
import uk.gov.hmcts.et.common.model.ccd.CaseData;
import uk.gov.hmcts.et.common.model.ccd.types.RespondentSumType;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Stream;

import static org.apache.commons.lang3.StringUtils.isBlank;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assumptions.assumeTrue;
import static org.junit.platform.commons.util.StringUtils.isNotBlank;
import static uk.gov.hmcts.ecm.common.service.pdf.et3.ET3FormConstants.ET3_FORM_CLIENT_TYPE_REPRESENTATIVE;
import static uk.gov.hmcts.ecm.common.service.pdf.et3.ET3FormMapper.mapEt3Form;
import static uk.gov.hmcts.ecm.common.service.pdf.et3.util.ET3FormTestUtil.getCheckBoxNotApplicableValue;
import static uk.gov.hmcts.ecm.common.service.pdf.et3.util.ET3FormTestUtil.getCheckboxValue;
import static uk.gov.hmcts.ecm.common.service.pdf.et3.util.ET3FormTestUtil.getCorrectedCheckboxValue;
import static uk.gov.hmcts.ecm.common.service.pdf.et3.util.ET3FormTestUtil.getCorrectedDetailValue;
import static uk.gov.hmcts.ecm.common.service.pdf.et3.util.ET3FormUtil.formatDate;

class ET3FormMapperTest {

    @ParameterizedTest
    @MethodSource("provideMapCaseTestData")
    @SneakyThrows
    void testMapRespondent(CaseData caseData) {
        if (ObjectUtils.isEmpty(caseData)) {
            assertThatThrownBy(() -> mapEt3Form(caseData,
                    ET3FormConstants.SUBMIT_ET3,
                    ET3_FORM_CLIENT_TYPE_REPRESENTATIVE))
                    .hasMessage(ET3FormTestConstants.TEST_PDF_CASE_DATA_NOT_FOUND_EXCEPTION_MESSAGE);
            return;
        }
        if (CollectionUtils.isEmpty(caseData.getRespondentCollection())) {
            assertThatThrownBy(() -> mapEt3Form(caseData,
                    ET3FormConstants.SUBMIT_ET3, ET3_FORM_CLIENT_TYPE_REPRESENTATIVE))
                    .hasMessage(ET3FormTestConstants.TEST_PDF_RESPONDENT_COLLECTION_NOT_FOUND_EXCEPTION_MESSAGE);
            return;
        }
        if (ObjectUtils.isEmpty(caseData.getSubmitEt3Respondent())) {
            assertThatThrownBy(() -> mapEt3Form(caseData,
                    ET3FormConstants.SUBMIT_ET3, ET3_FORM_CLIENT_TYPE_REPRESENTATIVE))
                    .hasMessage(ET3FormTestConstants.TEST_PDF_RESPONDENT_NOT_FOUND_IN_CASE_DATA_EXCEPTION_MESSAGE);
            return;
        }
        if (ObjectUtils.isEmpty(caseData.getSubmitEt3Respondent().getValue())) {
            assertThatThrownBy(() -> mapEt3Form(caseData,
                    ET3FormConstants.SUBMIT_ET3, ET3_FORM_CLIENT_TYPE_REPRESENTATIVE)).hasMessage(
                    ET3FormTestConstants.TEST_PDF_RESPONDENT_NOT_FOUND_IN_CASE_DATA_EXCEPTION_MESSAGE);
            return;
        }
        if (isBlank(caseData.getSubmitEt3Respondent().getValue().getLabel())) {
            assertThatThrownBy(() -> mapEt3Form(caseData,
                    ET3FormConstants.SUBMIT_ET3, ET3_FORM_CLIENT_TYPE_REPRESENTATIVE)).hasMessage(
                    ET3FormTestConstants.TEST_PDF_RESPONDENT_NAME_NOT_FOUND_IN_CASE_DATA_EXCEPTION_MESSAGE);
            return;
        }
        if (ET3FormTestConstants.TEST_DUMMY_VALUE.equals(caseData.getSubmitEt3Respondent().getValue().getLabel())) {
            assertThatThrownBy(() -> mapEt3Form(caseData,
                    ET3FormConstants.SUBMIT_ET3, ET3_FORM_CLIENT_TYPE_REPRESENTATIVE))
                    .hasMessage(
                            ET3FormTestConstants
                                    .TEST_PDF_RESPONDENT_NOT_FOUND_IN_RESPONDENT_COLLECTION_EXCEPTION_MESSAGE);
            return;
        }
        RespondentSumType respondentSumType = caseData.getRespondentCollection().stream()
                .filter(r -> caseData.getSubmitEt3Respondent()
                        .getSelectedLabel().equals(r.getValue().getRespondentName()))
                .toList().get(0).getValue();
        Map<String, Optional<String>> pdfFields = mapEt3Form(caseData,
                ET3FormConstants.SUBMIT_ET3, ET3_FORM_CLIENT_TYPE_REPRESENTATIVE);
        checkRespondent(pdfFields, respondentSumType);
        checkHeader(pdfFields, respondentSumType);
        checkClaimant(pdfFields, respondentSumType);
        checkAcas(pdfFields, respondentSumType);
        checkEmployment(pdfFields, respondentSumType);
        checkEarningAndBenefits(pdfFields, respondentSumType);
        checkResponse(pdfFields, respondentSumType);
        checkEmployerContractClaim(pdfFields, respondentSumType);
        checkRepresentative(pdfFields, caseData);
        checkDisability(pdfFields, respondentSumType);
    }

    private static void checkClaimant(Map<String, Optional<String>> pdfFields, RespondentSumType respondentSumType) {
        if (isBlank(respondentSumType.getEt3ResponseClaimantNameCorrection())) {
            assertThat(pdfFields.get(ET3FormConstants.TXT_PDF_CLAIMANT_FIELD_NAME))
                    .contains(ET3FormTestConstants.TEST_PDF_CLAIMANT_VALUE_ET1_CLAIMANT_NAME);
        } else {
            assertThat(pdfFields.get(ET3FormConstants.TXT_PDF_CLAIMANT_FIELD_NAME))
                    .contains(ET3FormTestConstants.TEST_PDF_CLAIMANT_VALUE_ET3_CLAIMANT_NAME);
        }
    }

    private static void checkHeader(Map<String, Optional<String>> pdfFields, RespondentSumType respondentSumType) {
        assertThat(pdfFields.get(ET3FormConstants.TXT_PDF_HEADER_FIELD_CASE_NUMBER))
                .contains(ET3FormTestConstants.TEST_PDF_HEADER_VALUE_CASE_NUMBER);
        assertThat(pdfFields.get(ET3FormConstants.TXT_PDF_HEADER_FIELD_DATE_RECEIVED)).contains(
                formatDate(LocalDate.now().toString(), ET3FormConstants.DATE_FORMAT_YYYY_MM_DD_DASH,
                        ET3FormConstants.DATE_FORMAT_DD_MM_YYYY_DASH)
        );
        if (ObjectUtils.isEmpty(respondentSumType.getEt3ResponseRespondentSupportDocument())
                && ObjectUtils.isEmpty(respondentSumType.getEt3ResponseEmployerClaimDocument())
                && CollectionUtils.isEmpty(respondentSumType.getEt3ResponseContestClaimDocument())) {
            assertNull(pdfFields.get(ET3FormConstants.TXT_PDF_HEADER_FIELD_RTF));
        } else {
            assertThat(pdfFields.get(ET3FormConstants.TXT_PDF_HEADER_FIELD_RTF))
                    .contains(ET3FormConstants.TXT_PDF_HEADER_VALUE_ADDITIONAL_DOCUMENT_EXISTS.formatted(3));
        }
    }

    private static void checkRespondent(Map<String, Optional<String>> pdfFields, RespondentSumType respondentSumType) {
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
        assertThat(pdfFields.get(ET3FormConstants.CHECKBOX_PDF_RESPONDENT_FIELD_CONTACT_TYPE_POST)).contains(
                ET3FormConstants.OFF_CAPITALISED);
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

    private static void checkAcas(Map<String, Optional<String>> pdfFields, RespondentSumType respondentSumType) {
        String acasAgreed = respondentSumType.getEt3ResponseAcasAgree();
        assertThat(pdfFields.get(ET3FormConstants.CHECKBOX_PDF_ACAS_FIELD_AGREEMENT_YES))
                .contains(ET3FormConstants.YES_CAPITALISED.equals(acasAgreed)
                ? ET3FormConstants.YES_CAPITALISED : ET3FormConstants.OFF_CAPITALISED);
        assertThat(pdfFields.get(ET3FormConstants.CHECKBOX_PDF_ACAS_FIELD_AGREEMENT_NO))
                .contains(ET3FormConstants.NO_CAPITALISED.equals(acasAgreed)
                ? ET3FormConstants.NO_CAPITALISED : ET3FormConstants.OFF_CAPITALISED);
        assertThat(pdfFields.get(ET3FormConstants.TXT_PDF_ACAS_FIELD_AGREEMENT_NO_REASON))
                .contains(ET3FormConstants.NO_CAPITALISED.equals(acasAgreed)
                ? respondentSumType.getEt3ResponseAcasAgreeReason() : ET3FormConstants.OFF_CAPITALISED);
    }

    private static void checkEmployment(Map<String, Optional<String>> pdfFields, RespondentSumType respondentSumType) {
        assertThat(pdfFields.get(ET3FormConstants.CHECKBOX_PDF_EMPLOYMENT_FIELD_DATES_CORRECT_YES))
                .contains(getCheckboxValue(respondentSumType.getEt3ResponseAreDatesCorrect(),
                        ET3FormConstants.YES_CAPITALISED, ET3FormConstants.YES_LOWERCASE));
        assertThat(pdfFields.get(ET3FormConstants.CHECKBOX_PDF_EMPLOYMENT_FIELD_DATES_CORRECT_NO))
                .contains(getCheckboxValue(respondentSumType.getEt3ResponseAreDatesCorrect(),
                        ET3FormConstants.NO_CAPITALISED, ET3FormConstants.NO_LOWERCASE));
        assertThat(pdfFields.get(ET3FormConstants.CHECKBOX_PDF_EMPLOYMENT_FIELD_DATES_CORRECT_NOT_APPLICABLE))
                .contains(getCheckBoxNotApplicableValue(respondentSumType.getEt3ResponseAreDatesCorrect(),
                        List.of(ET3FormConstants.YES_CAPITALISED, ET3FormConstants.NO_CAPITALISED),
                        ET3FormConstants.NO_LOWERCASE));

        assertThat(pdfFields.get(ET3FormConstants.TXT_PDF_EMPLOYMENT_FIELD_START_DATE_DAY))
                .contains(getCorrectedDetailValue(respondentSumType.getEt3ResponseAreDatesCorrect(),
                        ET3FormConstants.NO_CAPITALISED,
                        respondentSumType.getEt3ResponseEmploymentStartDate(),
                        ET3FormTestConstants.TEST_PDF_EMPLOYMENT_START_DAY));
        assertThat(pdfFields.get(ET3FormConstants.TXT_PDF_EMPLOYMENT_FIELD_START_DATE_MONTH))
                .contains(getCorrectedDetailValue(respondentSumType.getEt3ResponseAreDatesCorrect(),
                        ET3FormConstants.NO_CAPITALISED,
                        respondentSumType.getEt3ResponseEmploymentStartDate(),
                        ET3FormTestConstants.TEST_PDF_EMPLOYMENT_START_MONTH));
        assertThat(pdfFields.get(ET3FormConstants.TXT_PDF_EMPLOYMENT_FIELD_START_DATE_YEAR))
                .contains(getCorrectedDetailValue(respondentSumType.getEt3ResponseAreDatesCorrect(),
                        ET3FormConstants.NO_CAPITALISED,
                        respondentSumType.getEt3ResponseEmploymentStartDate(),
                        ET3FormTestConstants.TEST_PDF_EMPLOYMENT_START_YEAR));
        assertThat(pdfFields.get(ET3FormConstants.TXT_PDF_EMPLOYMENT_FIELD_END_DATE_DAY))
                .contains(getCorrectedDetailValue(respondentSumType.getEt3ResponseAreDatesCorrect(),
                        ET3FormConstants.NO_CAPITALISED,
                        respondentSumType.getEt3ResponseEmploymentEndDate(),
                        ET3FormTestConstants.TEST_PDF_EMPLOYMENT_END_DAY));
        assertThat(pdfFields.get(ET3FormConstants.TXT_PDF_EMPLOYMENT_FIELD_END_DATE_MONTH))
                .contains(getCorrectedDetailValue(respondentSumType.getEt3ResponseAreDatesCorrect(),
                        ET3FormConstants.NO_CAPITALISED,
                        respondentSumType.getEt3ResponseEmploymentEndDate(),
                        ET3FormTestConstants.TEST_PDF_EMPLOYMENT_END_MONTH));
        assertThat(pdfFields.get(ET3FormConstants.TXT_PDF_EMPLOYMENT_FIELD_END_DATE_YEAR))
                .contains(getCorrectedDetailValue(respondentSumType.getEt3ResponseAreDatesCorrect(),
                        ET3FormConstants.NO_CAPITALISED,
                        respondentSumType.getEt3ResponseEmploymentEndDate(),
                        ET3FormTestConstants.TEST_PDF_EMPLOYMENT_END_YEAR));
        assertThat(pdfFields.get(ET3FormConstants.TXT_PDF_EMPLOYMENT_FIELD_DATES_FURTHER_INFO))
                .contains(getCorrectedDetailValue(respondentSumType.getEt3ResponseAreDatesCorrect(),
                        ET3FormConstants.NO_CAPITALISED, respondentSumType.getEt3ResponseEmploymentInformation(),
                        ET3FormTestConstants.TEST_PDF_EMPLOYMENT_DATE_INFORMATION));
        assertThat(pdfFields.get(ET3FormConstants.CHECKBOX_PDF_EMPLOYMENT_FIELD_CONTINUES_YES))
                .contains(getCheckboxValue(respondentSumType.getEt3ResponseContinuingEmployment(),
                        ET3FormConstants.YES_CAPITALISED, ET3FormConstants.YES_LOWERCASE));
        assertThat(pdfFields.get(ET3FormConstants.CHECKBOX_PDF_EMPLOYMENT_FIELD_CONTINUES_NO))
                .contains(getCheckboxValue(respondentSumType.getEt3ResponseContinuingEmployment(),
                        ET3FormConstants.NO_CAPITALISED, ET3FormConstants.NO_LOWERCASE));
        assertThat(pdfFields.get(ET3FormConstants.CHECKBOX_PDF_EMPLOYMENT_FIELD_CONTINUES_NOT_APPLICABLE))
                .contains(getCheckBoxNotApplicableValue(respondentSumType.getEt3ResponseContinuingEmployment(),
                        List.of(ET3FormConstants.YES_CAPITALISED, ET3FormConstants.NO_CAPITALISED),
                        ET3FormConstants.NO_LOWERCASE));
        assertThat(pdfFields.get(ET3FormConstants.CHECKBOX_PDF_EMPLOYMENT_FIELD_JOB_TITLE_CORRECT_YES))
                .contains(getCheckboxValue(respondentSumType.getEt3ResponseIsJobTitleCorrect(),
                        ET3FormConstants.YES_CAPITALISED, ET3FormConstants.YES_LOWERCASE));
        assertThat(pdfFields.get(ET3FormConstants.CHECKBOX_PDF_EMPLOYMENT_FIELD_JOB_TITLE_CORRECT_NO))
                .contains(getCheckboxValue(respondentSumType.getEt3ResponseIsJobTitleCorrect(),
                        ET3FormConstants.NO_CAPITALISED, ET3FormConstants.NO_LOWERCASE));
        assertThat(pdfFields.get(ET3FormConstants.CHECKBOX_PDF_EMPLOYMENT_FIELD_JOB_TITLE_CORRECT_NOT_APPLICABLE))
                .contains(getCheckBoxNotApplicableValue(respondentSumType.getEt3ResponseIsJobTitleCorrect(),
                        List.of(ET3FormConstants.YES_CAPITALISED, ET3FormConstants.NO_CAPITALISED),
                        ET3FormConstants.NO_LOWERCASE));
        assertThat(pdfFields.get(ET3FormConstants.TXT_PDF_EMPLOYMENT_FIELD_JOB_TITLE_CORRECT_DETAILS))
                .contains(getCorrectedDetailValue(respondentSumType.getEt3ResponseIsJobTitleCorrect(),
                        ET3FormConstants.NO_CAPITALISED,
                        respondentSumType.getEt3ResponseCorrectJobTitle(),
                        ET3FormTestConstants.TEST_PDF_EMPLOYMENT_CORRECT_JOB_TITLE));
    }

    private static void checkEarningAndBenefits(Map<String, Optional<String>> pdfFields,
                                                RespondentSumType respondentSumType) {
        assertThat(pdfFields.get(ET3FormConstants.CHECKBOX_PDF_EARNINGS_BENEFITS_FIELD_HOURS_OF_WORK_CORRECT_YES))
                .contains(getCheckboxValue(respondentSumType.getEt3ResponseClaimantWeeklyHours(),
                        ET3FormConstants.YES_CAPITALISED, ET3FormConstants.YES_LOWERCASE));
        assertThat(pdfFields.get(ET3FormConstants.CHECKBOX_PDF_EARNINGS_BENEFITS_FIELD_HOURS_OF_WORK_CORRECT_NO))
                .contains(getCheckboxValue(respondentSumType.getEt3ResponseClaimantWeeklyHours(),
                        ET3FormConstants.NO_CAPITALISED, ET3FormConstants.NO_LOWERCASE));
        assertThat(pdfFields.get(
                ET3FormConstants.CHECKBOX_PDF_EARNINGS_BENEFITS_FIELD_HOURS_OF_WORK_CORRECT_NOT_APPLICABLE))
                .contains(getCheckBoxNotApplicableValue(respondentSumType.getEt3ResponseClaimantWeeklyHours(),
                        List.of(ET3FormConstants.NO_CAPITALISED, ET3FormConstants.YES_CAPITALISED),
                        ET3FormConstants.NO_LOWERCASE));
        assertThat(pdfFields.get(ET3FormConstants.TXT_PDF_EARNINGS_BENEFITS_FIELD_WORK_HOURS_DETAILS))
                .contains(getCorrectedDetailValue(respondentSumType.getEt3ResponseClaimantWeeklyHours(),
                        ET3FormConstants.NO_CAPITALISED, respondentSumType.getEt3ResponseClaimantCorrectHours(),
                        ET3FormTestConstants.TEST_PDF_EARNINGS_BENEFITS_CORRECT_HOURS));
        assertThat(pdfFields.get(ET3FormConstants.CHECKBOX_PDF_EARNINGS_BENEFITS_FIELD_EARNING_DETAILS_CORRECT_YES))
                .contains(getCheckboxValue(respondentSumType.getEt3ResponseEarningDetailsCorrect(),
                        ET3FormConstants.YES_CAPITALISED, ET3FormConstants.YES_LOWERCASE));
        assertThat(pdfFields.get(ET3FormConstants.CHECKBOX_PDF_EARNINGS_BENEFITS_FIELD_EARNING_DETAILS_CORRECT_NO))
                .contains(getCheckboxValue(respondentSumType.getEt3ResponseEarningDetailsCorrect(),
                        ET3FormConstants.NO_CAPITALISED, ET3FormConstants.NO_LOWERCASE));
        assertThat(pdfFields.get(
                ET3FormConstants.CHECKBOX_PDF_EARNINGS_BENEFITS_FIELD_EARNING_DETAILS_CORRECT_NOT_APPLICABLE))
                .contains(getCheckBoxNotApplicableValue(respondentSumType.getEt3ResponseEarningDetailsCorrect(),
                        List.of(ET3FormConstants.NO_CAPITALISED, ET3FormConstants.YES_CAPITALISED),
                        ET3FormConstants.NO_LOWERCASE));
        assertThat(pdfFields.get(ET3FormConstants.TXT_PDF_EARNINGS_BENEFITS_FIELD_PAY_BEFORE_TAX))
                .contains(getCorrectedDetailValue(respondentSumType.getEt3ResponseEarningDetailsCorrect(),
                        ET3FormConstants.NO_CAPITALISED, respondentSumType.getEt3ResponsePayBeforeTax(),
                        ET3FormTestConstants.TEST_PDF_EARNINGS_BENEFITS_PAY_BEFORE_TAX));
        assertThat(pdfFields.get(ET3FormConstants.CHECKBOX_PDF_EARNINGS_BENEFITS_FIELD_PAY_BEFORE_TAX_WEEKLY))
                .contains(getCorrectedCheckboxValue(respondentSumType.getEt3ResponseEarningDetailsCorrect(),
                        ET3FormConstants.NO_LOWERCASE, respondentSumType.getEt3ResponsePayFrequency(),
                        ET3FormConstants.WEEKLY_CAPITALISED,
                        ET3FormConstants.WEEKLY_LOWERCASE));
        assertThat(pdfFields.get(ET3FormConstants.CHECKBOX_PDF_EARNINGS_BENEFITS_FIELD_PAY_BEFORE_TAX_MONTHLY))
                .contains(getCorrectedCheckboxValue(respondentSumType.getEt3ResponseEarningDetailsCorrect(),
                        ET3FormConstants.NO_LOWERCASE, respondentSumType.getEt3ResponsePayFrequency(),
                        ET3FormConstants.MONTHLY_CAPITALISED,
                        ET3FormConstants.MONTHLY_LOWERCASE));
        assertThat(pdfFields.get(ET3FormConstants.CHECKBOX_PDF_EARNINGS_BENEFITS_FIELD_PAY_BEFORE_TAX_ANNUALLY))
                .contains(getCorrectedCheckboxValue(respondentSumType.getEt3ResponseEarningDetailsCorrect(),
                        ET3FormConstants.NO_LOWERCASE, respondentSumType.getEt3ResponsePayFrequency(),
                        ET3FormConstants.ANNUALLY_CAPITALISED,
                        ET3FormConstants.ANNUALLY_LOWERCASE));
        assertThat(pdfFields.get(ET3FormConstants.TXT_PDF_EARNINGS_BENEFITS_FIELD_NORMAL_TAKE_HOME_PAY))
                .contains(getCorrectedDetailValue(respondentSumType.getEt3ResponseEarningDetailsCorrect(),
                        ET3FormConstants.NO_CAPITALISED, respondentSumType.getEt3ResponsePayTakehome(),
                        ET3FormTestConstants.TEST_PDF_EARNINGS_BENEFITS_PAY_TAKE_HOME));
        assertThat(pdfFields.get(ET3FormConstants.CHECKBOX_PDF_EARNINGS_BENEFITS_FIELD_NORMAL_TAKE_HOME_PAY_WEEKLY))
                .contains(getCorrectedCheckboxValue(respondentSumType.getEt3ResponseEarningDetailsCorrect(),
                        ET3FormConstants.NO_LOWERCASE, respondentSumType.getEt3ResponsePayFrequency(),
                        ET3FormConstants.WEEKLY_CAPITALISED,
                        ET3FormConstants.WEEKLY_LOWERCASE));
        assertThat(pdfFields.get(ET3FormConstants.CHECKBOX_PDF_EARNINGS_BENEFITS_FIELD_NORMAL_TAKE_HOME_PAY_MONTHLY))
                .contains(getCorrectedCheckboxValue(respondentSumType.getEt3ResponseEarningDetailsCorrect(),
                        ET3FormConstants.NO_LOWERCASE, respondentSumType.getEt3ResponsePayFrequency(),
                        ET3FormConstants.MONTHLY_CAPITALISED,
                        ET3FormConstants.MONTHLY_LOWERCASE));
        assertThat(pdfFields.get(ET3FormConstants.CHECKBOX_PDF_EARNINGS_BENEFITS_FIELD_NORMAL_TAKE_HOME_PAY_ANNUALLY))
                .contains(getCorrectedCheckboxValue(respondentSumType.getEt3ResponseEarningDetailsCorrect(),
                        ET3FormConstants.NO_LOWERCASE, respondentSumType.getEt3ResponsePayFrequency(),
                        ET3FormConstants.ANNUALLY_CAPITALISED,
                        ET3FormConstants.ANNUALLY_LOWERCASE));
        assertThat(pdfFields.get(ET3FormConstants.CHECKBOX_PDF_EARNINGS_BENEFITS_FIELD_NOTICE_PERIOD_CORRECT_YES))
                .contains(getCheckboxValue(respondentSumType.getEt3ResponseIsNoticeCorrect(),
                        ET3FormConstants.YES_CAPITALISED, ET3FormConstants.YES_LOWERCASE));
        assertThat(pdfFields.get(ET3FormConstants.CHECKBOX_PDF_EARNINGS_BENEFITS_FIELD_NOTICE_PERIOD_CORRECT_NO))
                .contains(getCheckboxValue(respondentSumType.getEt3ResponseIsNoticeCorrect(),
                        ET3FormConstants.NO_CAPITALISED, ET3FormConstants.NO_LOWERCASE));
        assertThat(pdfFields.get(
                ET3FormConstants.CHECKBOX_PDF_EARNINGS_BENEFITS_FIELD_NOTICE_PERIOD_CORRECT_NOT_APPLICABLE))
                .contains(getCheckBoxNotApplicableValue(respondentSumType.getEt3ResponseIsNoticeCorrect(),
                        List.of(ET3FormConstants.NO_CAPITALISED, ET3FormConstants.YES_CAPITALISED),
                        ET3FormConstants.NO_LOWERCASE));
        assertThat(pdfFields.get(
                ET3FormConstants.TXT_PDF_EARNINGS_BENEFITS_FIELD_NOTICE_PERIOD_NOT_CORRECT_INFORMATION))
                .contains(getCorrectedDetailValue(respondentSumType.getEt3ResponseIsNoticeCorrect(),
                        ET3FormConstants.NO_CAPITALISED, respondentSumType.getEt3ResponseCorrectNoticeDetails(),
                        ET3FormTestConstants.TEST_PDF_EARNINGS_BENEFITS_CORRECT_NOTICE_PERIOD));
        assertThat(pdfFields.get(
                ET3FormConstants.CHECKBOX_PDF_EARNINGS_BENEFITS_FIELD_PENSION_AND_OTHER_BENEFITS_CORRECT_YES))
                .contains(getCheckboxValue(respondentSumType.getEt3ResponseIsPensionCorrect(),
                        ET3FormConstants.YES_CAPITALISED, ET3FormConstants.YES_LOWERCASE));
        assertThat(pdfFields.get(
                ET3FormConstants.CHECKBOX_PDF_EARNINGS_BENEFITS_FIELD_PENSION_AND_OTHER_BENEFITS_CORRECT_NO))
                .contains(getCheckboxValue(respondentSumType.getEt3ResponseIsPensionCorrect(),
                        ET3FormConstants.NO_CAPITALISED, ET3FormConstants.NO_LOWERCASE));
        assertThat(pdfFields.get(
                ET3FormConstants
                        .CHECKBOX_PDF_EARNINGS_BENEFITS_FIELD_PENSION_AND_OTHER_BENEFITS_CORRECT_NOT_APPLICABLE))
                .contains(getCheckBoxNotApplicableValue(respondentSumType.getEt3ResponseIsPensionCorrect(),
                        List.of(ET3FormConstants.NO_CAPITALISED,
                                ET3FormConstants.YES_CAPITALISED),
                        ET3FormConstants.NO_LOWERCASE));
        assertThat(pdfFields.get(
                ET3FormConstants.TXT_PDF_EARNINGS_BENEFITS_FIELD_PENSION_AND_OTHER_BENEFITS_NOT_CORRECT_INFORMATION))
                .contains(getCorrectedDetailValue(respondentSumType.getEt3ResponseIsPensionCorrect(),
                        ET3FormConstants.NO_CAPITALISED, respondentSumType.getEt3ResponsePensionCorrectDetails(),
                        ET3FormTestConstants.TEST_PDF_EARNINGS_BENEFITS_CORRECT_PENSION_AND_OTHER_BENEFITS));
    }

    private static void checkResponse(Map<String, Optional<String>> pdfFields, RespondentSumType respondentSumType) {
        assertThat(pdfFields.get(ET3FormConstants.CHECKBOX_PDF_RESPONSE_FIELD_CONTEST_CLAIM_YES)).contains(
                getCheckboxValue(respondentSumType.getEt3ResponseRespondentContestClaim(),
                        ET3FormConstants.YES_CAPITALISED, ET3FormConstants.YES_LOWERCASE));
        assertThat(pdfFields.get(ET3FormConstants.CHECKBOX_PDF_RESPONSE_FIELD_CONTEST_CLAIM_NO)).contains(
                getCheckboxValue(respondentSumType.getEt3ResponseRespondentContestClaim(),
                        ET3FormConstants.NO_CAPITALISED, ET3FormConstants.NO_LOWERCASE));
        assertThat(pdfFields.get(ET3FormConstants.TXT_PDF_RESPONSE_FIELD_CONTEST_CLAIM_CORRECT_FACTS)).contains(
                getCorrectedDetailValue(respondentSumType.getEt3ResponseRespondentContestClaim(),
                        ET3FormConstants.YES_CAPITALISED,
                        respondentSumType.getEt3ResponseContestClaimDetails(),
                        ET3FormTestConstants.TEST_PDF_RESPONSE_CONTEST_CLAIM_CORRECT_FACTS)
        );
    }

    private static void checkEmployerContractClaim(Map<String, Optional<String>> pdfFields,
                                                   RespondentSumType respondentSumType) {
        assertThat(pdfFields.get(ET3FormConstants.CHECKBOX_PDF_EMPLOYER_CONTRACT_CLAIM_FIELD_YES)).contains(
                getCheckboxValue(respondentSumType.getEt3ResponseEmployerClaim(),
                        ET3FormConstants.YES_CAPITALISED,
                        ET3FormConstants.YES_LOWERCASE));
        assertThat(pdfFields.get(ET3FormConstants.TXT_PDF_EMPLOYER_CONTRACT_CLAIM_FIELD_DETAILS)).contains(
                getCorrectedDetailValue(respondentSumType.getEt3ResponseEmployerClaim(),
                        ET3FormConstants.YES_CAPITALISED,
                        respondentSumType.getEt3ResponseEmployerClaimDetails(),
                        ET3FormTestConstants.TEST_PDF_RESPONSE_EMPLOYER_CONTRACT_CLAIM_CORRECT_DETAILS));
    }

    private static void checkRepresentative(Map<String, Optional<String>> pdfFields, CaseData caseData) {
        assumeTrue(ObjectUtils.isNotEmpty(caseData));
        assumeTrue(!CollectionUtils.isEmpty(caseData.getRepCollection()));
        assumeTrue(ObjectUtils.isNotEmpty(caseData.getRepCollection().get(0)));
        assumeTrue(ObjectUtils.isNotEmpty(caseData.getRepCollection().get(0).getValue()));
        assumeTrue(!ET3FormTestConstants.TEST_DUMMY_VALUE.equals(
                caseData.getRepCollection().get(0).getValue().getRespRepName()));
        assertThat(pdfFields.get(ET3FormConstants.TXT_PDF_REPRESENTATIVE_FIELD_NAME))
                .contains(ET3FormTestConstants.TEST_PDF_REPRESENTATIVE_NAME);
        assertThat(pdfFields.get(ET3FormConstants.TXT_PDF_REPRESENTATIVE_FIELD_ORGANISATION_NAME))
                .contains(ET3FormTestConstants.TEST_PDF_REPRESENTATIVE_ORGANISATION_NAME);
        assertThat(pdfFields.get(ET3FormConstants.TXT_PDF_REPRESENTATIVE_FIELD_ADDRESS))
                .contains(ET3FormTestConstants.TEST_PDF_REPRESENTATIVE_ADDRESS);
        assertThat(pdfFields.get(ET3FormConstants.TXT_PDF_REPRESENTATIVE_FIELD_POSTCODE))
                .contains(ET3FormTestConstants.TEST_PDF_REPRESENTATIVE_POSTCODE);
        assertThat(pdfFields.get(ET3FormConstants.TXT_PDF_REPRESENTATIVE_FIELD_PHONE_NUMBER)).contains(isNotBlank(
                caseData.getRepCollection().get(0).getValue().getRepresentativePhoneNumber())
                ? ET3FormTestConstants.TEST_PDF_REPRESENTATIVE_PHONE_NUMBER : ET3FormConstants.STRING_EMPTY);
        assertThat(pdfFields.get(ET3FormConstants.TXT_PDF_REPRESENTATIVE_FIELD_MOBILE_PHONE_NUMBER))
                .contains(isNotBlank(caseData.getRepCollection().get(0).getValue().getRepresentativeMobileNumber())
                ? ET3FormTestConstants.TEST_PDF_REPRESENTATIVE_MOBILE_PHONE_NUMBER : ET3FormConstants.STRING_EMPTY);
        assertThat(pdfFields.get(ET3FormConstants.TXT_PDF_REPRESENTATIVE_FIELD_REFERENCE_FOR_CORRESPONDENCE))
                .contains(isNotBlank(caseData.getRepCollection().get(0).getValue()
                        .getRepresentativeReference())
                        ? ET3FormTestConstants.TEST_PDF_REPRESENTATIVE_REFERENCE_FOR_CORRESPONDENCE
                        : ET3FormConstants.STRING_EMPTY);
        assertThat(pdfFields.get(ET3FormConstants.CHECKBOX_PDF_REPRESENTATIVE_FIELD_COMMUNICATION_PREFERENCE_EMAIL))
                .contains(getCheckboxValue(caseData.getRepCollection().get(0).getValue().getRepresentativePreference(),
                        ET3FormConstants.EMAIL_CAPITALISED, ET3FormConstants.EMAIL_LOWERCASE));
        assertThat(pdfFields.get(ET3FormConstants.CHECKBOX_PDF_REPRESENTATIVE_FIELD_COMMUNICATION_PREFERENCE_POST))
                .contains(getCheckboxValue(caseData.getRepCollection().get(0).getValue().getRepresentativePreference(),
                        ET3FormConstants.POST_CAPITALISED, ET3FormConstants.POST_LOWERCASE));
        assertThat(pdfFields.get(ET3FormConstants.TXT_PDF_REPRESENTATIVE_FIELD_EMAIL_ADDRESS)).contains(isNotBlank(
                caseData.getRepCollection().get(0).getValue().getRepresentativeEmailAddress())
                ? ET3FormTestConstants.TEST_PDF_REPRESENTATIVE_EMAIL_ADDRESS : ET3FormConstants.STRING_EMPTY);
        assertThat(pdfFields.get(ET3FormConstants.CHECKBOX_PDF_REPRESENTATIVE_FIELD_VIDEO_HEARINGS))
                .contains(ET3FormConstants.YES_CAPITALISED);
        assertThat(pdfFields.get(ET3FormConstants.CHECKBOX_PDF_REPRESENTATIVE_FIELD_PHONE_HEARINGS))
                .contains(ET3FormConstants.YES_CAPITALISED);
    }

    private static void checkDisability(Map<String, Optional<String>> pdfFields, RespondentSumType respondentSumType) {
        assertThat(pdfFields.get(ET3FormConstants.CHECKBOX_PDF_DISABILITY_YES)).contains(
                getCheckboxValue(respondentSumType.getEt3ResponseRespondentSupportNeeded(),
                        ET3FormConstants.YES_CAPITALISED, ET3FormConstants.YES_LOWERCASE));
        assertThat(pdfFields.get(ET3FormConstants.CHECKBOX_PDF_DISABILITY_NO)).contains(
                getCheckboxValue(respondentSumType.getEt3ResponseRespondentSupportNeeded(),
                        ET3FormConstants.NO_CAPITALISED, ET3FormConstants.NO_LOWERCASE));
        assertThat(pdfFields.get(ET3FormConstants.CHECKBOX_PDF_DISABILITY_NOT_SURE)).contains(
                getCheckBoxNotApplicableValue(respondentSumType.getEt3ResponseRespondentSupportNeeded(),
                        List.of(ET3FormConstants.YES_CAPITALISED,
                                ET3FormConstants.NO_CAPITALISED),
                        ET3FormConstants.NO_LOWERCASE));
        assertThat(pdfFields.get(ET3FormConstants.TXT_PDF_DISABILITY_DETAILS)).contains(
                getCorrectedDetailValue(respondentSumType.getEt3ResponseRespondentSupportNeeded(),
                        ET3FormConstants.YES_CAPITALISED,
                        respondentSumType.getEt3ResponseRespondentSupportDetails(),
                        ET3FormTestConstants.TEST_PDF_DISABILITY_DETAIL));
    }

    private static boolean isOtherTitle(String selectedTitle) {
        return isNotBlank(selectedTitle)
                && !ET3FormConstants.CHECKBOX_PDF_RESPONDENT_EXPECTED_VALUE_TITLE_MR.equals(selectedTitle)
                && !ET3FormConstants.CHECKBOX_PDF_RESPONDENT_EXPECTED_VALUE_TITLE_MS.equals(selectedTitle)
                && !ET3FormConstants.CHECKBOX_PDF_RESPONDENT_EXPECTED_VALUE_TITLE_MRS.equals(selectedTitle)
                && !ET3FormConstants.CHECKBOX_PDF_RESPONDENT_EXPECTED_VALUE_TITLE_MISS.equals(selectedTitle);
    }

    private static Stream<CaseData> provideMapCaseTestData() {
        CaseData caseDataNullEt3Respondent = ResourceLoader
                .fromString(ET3FormTestConstants.TEST_ET3_FORM_CASE_DATA_FILE, CaseData.class);
        caseDataNullEt3Respondent.setSubmitEt3Respondent(null);
        CaseData caseDataNullEt3RespondentValue = ResourceLoader.fromString(
                ET3FormTestConstants.TEST_ET3_FORM_CASE_DATA_FILE, CaseData.class);
        caseDataNullEt3RespondentValue.getSubmitEt3Respondent().setValue(null);
        CaseData caseDataEmptyEt3RespondentLabel = ResourceLoader.fromString(
                ET3FormTestConstants.TEST_ET3_FORM_CASE_DATA_FILE, CaseData.class);
        caseDataEmptyEt3RespondentLabel.getSubmitEt3Respondent().getValue().setLabel(ET3FormConstants.STRING_EMPTY);
        CaseData caseDataEmptyEt3RespondentWrongLabel = ResourceLoader.fromString(
                ET3FormTestConstants.TEST_ET3_FORM_CASE_DATA_FILE, CaseData.class);
        caseDataEmptyEt3RespondentWrongLabel.getSubmitEt3Respondent()
                .getValue().setLabel(ET3FormTestConstants.TEST_DUMMY_VALUE);
        CaseData caseDataEmpty = new CaseData();
        CaseData caseDataFull = ResourceLoader
                .fromString(ET3FormTestConstants.TEST_ET3_FORM_CASE_DATA_FILE, CaseData.class);
        return Stream.of(null, caseDataEmpty, caseDataFull, caseDataNullEt3Respondent,
                caseDataNullEt3RespondentValue, caseDataEmptyEt3RespondentLabel, caseDataEmptyEt3RespondentWrongLabel);
    }
}
