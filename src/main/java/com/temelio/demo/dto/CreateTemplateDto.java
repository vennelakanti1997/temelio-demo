package com.temelio.demo.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateTemplateDto {

    private TemplateVariables subject;
    private TemplateVariables body;
}
