package io.github.sevenevy.diagnosis.config;

import java.util.Map;
import java.util.stream.Collectors;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;


public class EnvironmentProperties {

    private static final String HEALTH_INDICATORS_PROPERTY = "health.indicators.mapping";
    private static final Map<Integer, String> DEFAULT_HEALTH_INDEX_MAPPING =
        Map.of(3, "Cardiologie", 5, "Traumatologie");

    private Map<Integer, String> healthIndexMapping;
    
    public EnvironmentProperties() {
        try {
            String jsonMapping = System.getenv(HEALTH_INDICATORS_PROPERTY);
            healthIndexMapping = parseProperties(jsonMapping);
        } catch (IllegalArgumentException | JsonProcessingException e) {
            System.err.println("Could not load indicators from environment, using default ones");
        } finally {
            healthIndexMapping = DEFAULT_HEALTH_INDEX_MAPPING;
        }
    }

    @SuppressWarnings("unchecked")
    private Map<Integer, String> parseProperties(String json) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        Map<String, String> mapping = mapper.readValue(json, Map.class);
        return mapping.entrySet().stream()
                .collect(Collectors.toMap(
                    e -> Integer.parseInt(e.getKey().toString()),
                    e -> e.getValue().toString()
                ));
    }

    public Map<Integer, String> getMapping() {
        return healthIndexMapping;
    }
}
