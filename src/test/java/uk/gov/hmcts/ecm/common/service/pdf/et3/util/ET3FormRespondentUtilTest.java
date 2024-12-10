package uk.gov.hmcts.ecm.common.service.pdf.et3.util;

import org.apache.commons.lang3.StringUtils;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import uk.gov.hmcts.et.common.model.ccd.types.RespondentSumType;

import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static uk.gov.hmcts.ecm.common.constants.PdfMapperConstants.NO;
import static uk.gov.hmcts.ecm.common.constants.PdfMapperConstants.YES;

public class ET3FormRespondentUtilTest {

    private static final String EXPECTED_RESPONDENT_NAME = "Michael Jackson";
    private static final String EXPECTED_RESPONDENT_ORGANISATION = "Michael Jackson Ltd.";
    private static final String EXPECTED_RESPONDENT_FIRST_NAME = "Michael";
    private static final String EXPECTED_RESPONDENT_LAST_NAME = "Jackson";

    @ParameterizedTest
    @MethodSource("provideFindRespondentNameByRespondentSumType")
    void theFindRespondentNameByRespondentSumType(RespondentSumType respondentSumType, String expectedRespondentName) {
        assertThat(ET3FormRespondentUtil.findRespondentNameByRespondentSumType(respondentSumType))
                .isEqualTo(expectedRespondentName);
    }

    private static Stream<Arguments> provideFindRespondentNameByRespondentSumType() {
        RespondentSumType respondentSumTypeWithRespondentName =
                RespondentSumType.builder().respondentName(EXPECTED_RESPONDENT_NAME).build();
        RespondentSumType respondentSumTypeWithOrganisationName =
                RespondentSumType.builder().respondentOrganisation(EXPECTED_RESPONDENT_ORGANISATION).build();
        RespondentSumType respondentSumTypeWithFirstName =
                RespondentSumType.builder().respondentFirstName(EXPECTED_RESPONDENT_FIRST_NAME).build();
        RespondentSumType respondentSumTypeWithLastName =
                RespondentSumType.builder().respondentLastName(EXPECTED_RESPONDENT_LAST_NAME).build();
        RespondentSumType respondentSumTypeWithFirstAndLastName =
                RespondentSumType.builder().respondentFirstName(EXPECTED_RESPONDENT_FIRST_NAME)
                        .respondentLastName(EXPECTED_RESPONDENT_LAST_NAME).build();
        return Stream.of(Arguments.of(null, StringUtils.EMPTY),
                Arguments.of(respondentSumTypeWithRespondentName, EXPECTED_RESPONDENT_NAME),
                Arguments.of(respondentSumTypeWithOrganisationName, EXPECTED_RESPONDENT_ORGANISATION),
                Arguments.of(respondentSumTypeWithFirstName, EXPECTED_RESPONDENT_FIRST_NAME),
                Arguments.of(respondentSumTypeWithLastName, EXPECTED_RESPONDENT_LAST_NAME),
                Arguments.of(respondentSumTypeWithFirstAndLastName, EXPECTED_RESPONDENT_FIRST_NAME
                        + StringUtils.SPACE
                        + EXPECTED_RESPONDENT_LAST_NAME)
        );
    }

    @Test
    void theFindResponseRespondentName() {
        RespondentSumType respondentSumTypeNameQuestionYes = RespondentSumType.builder()
                .responseRespondentNameQuestion(YES)
                .respondentName(EXPECTED_RESPONDENT_NAME)
                .build();
        assertThat(ET3FormRespondentUtil.findResponseRespondentName(respondentSumTypeNameQuestionYes))
                .isEqualTo(EXPECTED_RESPONDENT_NAME);
        RespondentSumType respondentSumTypeNameQuestionNo = RespondentSumType.builder()
                .responseRespondentNameQuestion(NO)
                .responseRespondentName(EXPECTED_RESPONDENT_NAME)
                .build();
        assertThat(ET3FormRespondentUtil.findResponseRespondentName(respondentSumTypeNameQuestionNo))
                .isEqualTo(EXPECTED_RESPONDENT_NAME);
    }
}
