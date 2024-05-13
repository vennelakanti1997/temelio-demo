package com.temelio.demo.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
public class EmailTemplatesDto {
    @JsonProperty("isActive")
    private boolean active;
    private UUID templateId;
    private TemplateVariables subjectAndVariables;
    private TemplateVariables bodyAndVariables;
}
