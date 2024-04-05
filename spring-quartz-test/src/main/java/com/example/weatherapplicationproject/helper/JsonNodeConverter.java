package com.example.weatherapplicationproject.helper;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter(autoApply = true)
public class JsonNodeConverter implements AttributeConverter<JsonNode, String> {

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public String convertToDatabaseColumn(JsonNode attribute) {
        try {
            return objectMapper.writeValueAsString(attribute);
        } catch (Exception ex) {
            System.out.println("Failed to convert JSON to String: " + ex.getMessage());
            throw new RuntimeException("Failed to convert JSON to String: " + ex.getMessage(), ex);
        }
    }

    @Override
    public JsonNode convertToEntityAttribute(String dbData) {
        try {
            return objectMapper.readTree(dbData);
        } catch (Exception ex) {
            System.out.println("Failed to convert String to JSON: " + ex.getMessage());
            throw new RuntimeException("Failed to convert String to JSON: " + ex.getMessage(), ex);
        }
    }
}
