package uk.gov.hmcts.ecm.common.launchdarkly;

import com.google.common.collect.ImmutableList;
import com.launchdarkly.sdk.LDContext;
import com.launchdarkly.sdk.LDUser;
import com.launchdarkly.sdk.server.interfaces.LDClientInterface;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyBoolean;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class FeatureToggleApiTest {

    private static final String FAKE_FEATURE = "fake-feature";
    private static final String FAKE_ENVIRONMENT = "fake-env";

    @Mock
    private LDClientInterface ldClient;

    @Captor
    private ArgumentCaptor<LDContext> ldContextArgumentCaptor;

    private FeatureToggleApi featureToggleApi;

    @BeforeEach
    void setUp() {
        featureToggleApi = new FeatureToggleApi(ldClient, FAKE_ENVIRONMENT);
    }

    @ParameterizedTest
    @ValueSource(booleans = {true, false})
    void shouldReturnCorrectState_whenUserIsProvided(Boolean toggleState) {
        LDUser ldUSer = new LDUser.Builder("et-cos")
                .custom("timestamp", String.valueOf(System.currentTimeMillis()))
                .custom("environment", FAKE_ENVIRONMENT).build();
        givenToggle(toggleState);

        assertThat(featureToggleApi.isFeatureEnabled(FAKE_FEATURE, ldUSer)).isEqualTo(toggleState);

        verify(ldClient).boolVariation(
                FAKE_FEATURE,
                LDContext.fromUser(ldUSer),
                false
        );
    }

    @ParameterizedTest
    @ValueSource(booleans = {true, false})
    void shouldReturnCorrectState_whenDefaultServiceUser(Boolean toggleState) {
        givenToggle(toggleState);

        assertThat(featureToggleApi.isFeatureEnabled(FAKE_FEATURE)).isEqualTo(toggleState);
        verifyBoolVariationCalled(List.of("timestamp", "environment"));
    }

    private void givenToggle(boolean state) {
        when(ldClient.boolVariation(eq(FeatureToggleApiTest.FAKE_FEATURE), any(LDContext.class), anyBoolean()))
                .thenReturn(state);
    }

    private void verifyBoolVariationCalled(List<String> customAttributesKeys) {
        verify(ldClient).boolVariation(
                eq(FeatureToggleApiTest.FAKE_FEATURE),
                ldContextArgumentCaptor.capture(),
                eq(false)
        );
        LDContext capturedLdContext = ldContextArgumentCaptor.getValue();
        assertThat(capturedLdContext.getKey()).isEqualTo("et-cos");
        assertThat(ImmutableList.copyOf(capturedLdContext.getCustomAttributeNames()))
                .containsOnlyOnceElementsOf(customAttributesKeys);
    }
}