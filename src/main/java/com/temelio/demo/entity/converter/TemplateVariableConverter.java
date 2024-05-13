package com.temelio.demo.entity.converter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.temelio.demo.dto.TemplateVariables;
import jakarta.persistence.AttributeConverter;
import java.io.IOException;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class TemplateVariableConverter implements AttributeConverter<TemplateVariables, String> {
  private final ObjectMapper objectMapper = new ObjectMapper();

  @Override
  public String convertToDatabaseColumn(final TemplateVariables templateVariables) {
    try {
      return objectMapper.writeValueAsString(templateVariables);
    } catch (IOException e) {
      log.error("Failed to convert String to JsonNode - {}", templateVariables, e);
      throw new RuntimeException("Make sure the data is in correct format");
    }
  }

  @Override
  public TemplateVariables convertToEntityAttribute(final String s) {
    if (s!=null&& !s.isBlank()) {
      try {
        return objectMapper.readValue(s, TemplateVariables.class);
      } catch (IOException e) {
        log.error("Failed to convert String to TemplateVariables - {}", s, e);
        throw new RuntimeException("Make sure the data is in correct format");
      }
    }
    return null;
  }
}
