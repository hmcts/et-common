package uk.gov.hmcts.ecm.common.configuration;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * This is a mappings class providing the mappings for all postcodes to appropriate tribunal offices.  Simply first past
 * of postcode to the term of the office known by.  E.g.
 *
 * <pre>
 *     RG10 - Reading
 *     CW7 - Manchester
 *     etc...
 * </pre>
 */
@Configuration
@ConfigurationProperties("tribunal-offices")
@PropertySource(value = "classpath:postcode-office.yml", factory = YamlPropertySourceFactory.class)
public class PostcodeToOfficeMappings {

    private final Map<String, String> postcodes = new ConcurrentHashMap<>();

    /**
     * Retrieves the map of postcode to office mappings.
     *
     * @return a map of postcode to office mappings
     */
    public Map<String, String> getPostcodes() {
        return postcodes;
    }
}
