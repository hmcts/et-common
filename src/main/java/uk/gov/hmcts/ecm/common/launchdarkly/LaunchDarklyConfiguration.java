package uk.gov.hmcts.ecm.common.launchdarkly;

import com.launchdarkly.sdk.server.Components;
import com.launchdarkly.sdk.server.LDClient;
import com.launchdarkly.sdk.server.LDConfig;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.Duration;

@Configuration
@Slf4j
public class LaunchDarklyConfiguration {

    @Bean
    public LDClient ldClient(@Value("${launchdarkly.sdk-key}") String sdkKey,
                             @Value("${launchdarkly.offline-mode:false}") Boolean offlineMode) {
        LDConfig.Builder builder = new LDConfig.Builder()
            .http(Components.httpConfiguration().connectTimeout(Duration.ofSeconds(10)))
            .offline(offlineMode);
        return new LDClient(sdkKey, builder.build());
    }
}
