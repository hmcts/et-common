package uk.gov.hmcts.ecm.common.launchdarkly;

import com.launchdarkly.sdk.LDUser;
import com.launchdarkly.sdk.server.interfaces.LDClientInterface;
import java.io.IOException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class FeatureToggleApi {

    private final LDClientInterface internalClient;
    private final String environment;

    @Autowired
    public FeatureToggleApi(LDClientInterface internalClient, @Value("${launchdarkly.env}") String environment) {
        this.internalClient = internalClient;
        this.environment = environment;
        Runtime.getRuntime().addShutdownHook(new Thread(this::close));
    }

    public boolean isFeatureEnabled(String feature) {
        return internalClient.boolVariation(feature, createLDUser().build(), false);
    }

    public boolean isFeatureEnabled(String feature, boolean defaultValue) {
        return internalClient.boolVariation(feature, createLDUser().build(), defaultValue);
    }

    public boolean isFeatureEnabled(String feature, LDUser user) {
        return internalClient.boolVariation(feature, user, false);
    }

    public boolean isFeatureEnabled(String feature, LDUser user, boolean defaultValue) {
        return internalClient.boolVariation(feature, user, defaultValue);
    }

    public LDUser.Builder createLDUser() {
        return new LDUser.Builder("et-cos")
                .custom("timestamp", String.valueOf(System.currentTimeMillis()))
                .custom("environment", environment);
    }

    private void close() {
        try {
            internalClient.close();
        } catch (IOException e) {
            log.error("Error in closing the Launchdarkly client::", e);
        }
    }
}