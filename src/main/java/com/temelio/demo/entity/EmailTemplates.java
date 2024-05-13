package com.temelio.demo.entity;

import com.temelio.demo.entity.converter.TemplateVariableConverter;
import com.temelio.demo.dto.TemplateVariables;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.annotations.ColumnDefault;

@Setter
@Getter
@Entity
@Table(name = "email_templates")
@Slf4j
public class EmailTemplates extends CommonAttributes {

  @ManyToOne
  @JoinColumn(nullable = false, name = "foundation_id")
  private Foundation foundation;

  @Column(name = "subject", nullable = false, columnDefinition = "jsonb")
  @Convert(converter = TemplateVariableConverter.class)
  private TemplateVariables subjectAndVariables;

  @Column(name = "body", nullable = false, columnDefinition = "jsonb")
  @Convert(converter = TemplateVariableConverter.class)
  private TemplateVariables bodyAndVariables;

  @ColumnDefault(value = "true")
  @Column(name = "is_active", nullable = false)
  private boolean active;
}
