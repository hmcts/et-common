package uk.gov.hmcts.ecm.common.service.utils;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.type.TypeFactory;
import lombok.SneakyThrows;

import java.util.List;

public final class ResourceLoader {

    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper()
        .setSerializationInclusion(JsonInclude.Include.NON_EMPTY)
        .disable(SerializationFeature.FAIL_ON_EMPTY_BEANS);

    private ResourceLoader() {
        // Utility class
    }

    @SneakyThrows
    public static <T> T fromString(String jsonFileName, Class<T> clazz) {
        String json = ResourceUtil.resourceAsString(jsonFileName);
        return OBJECT_MAPPER.readValue(json, clazz);
    }

    @SneakyThrows
    public static <T> List<T> fromStringToList(String jsonFileName, Class<T> className) {
        String json = ResourceUtil.resourceAsString(jsonFileName);
        return OBJECT_MAPPER.readValue(json, TypeFactory.defaultInstance().constructCollectionType(
            List.class,
            className
        ));
    }

    public static String toJson(Object input) {
        try {
            return OBJECT_MAPPER.writeValueAsString(input);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(
                String.format("Failed to serialize '%s' to JSON", input.getClass().getSimpleName()), e
            );
        }
    }
}
