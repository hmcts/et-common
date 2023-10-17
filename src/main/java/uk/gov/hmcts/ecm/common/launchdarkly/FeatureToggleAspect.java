package uk.gov.hmcts.ecm.common.launchdarkly;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

@Slf4j
@Aspect
@Component
@RequiredArgsConstructor
public class FeatureToggleAspect {

    private final FeatureToggleApi featureToggleApi;

    @Around("execution(* *(*)) && @annotation(featureToggle)")
    public void checkFeatureEnabled(ProceedingJoinPoint joinPoint, FeatureToggle featureToggle) throws Throwable {

        if (featureToggle.value() && featureToggleApi.isFeatureEnabled(featureToggle.feature())) {
            joinPoint.proceed();
        } else if (!featureToggle.value() && !featureToggleApi.isFeatureEnabled(featureToggle.feature())) {
            joinPoint.proceed();
        } else {
            log.warn(
                    String.format("Feature %s is not enabled for method %s",
                    featureToggle.feature(),
                    joinPoint.getSignature().getName())
            );
        }
    }
}
