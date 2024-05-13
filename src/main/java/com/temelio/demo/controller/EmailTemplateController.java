package com.temelio.demo.controller;

import com.temelio.demo.dto.*;
import com.temelio.demo.service.EmailTemplateService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(value = {"*"})
@RestController
@Validated
public class EmailTemplateController {

  @Autowired private transient EmailTemplateService emailTemplateService;

  @PostMapping(path = "/{foundationId}/template")
  public ResponseEntity<CreateTemplateResponse> createTemplate(
      @PathVariable(name = "foundationId") final UUID foundationId,
      @Valid @RequestBody final CreateTemplateDto createTemplateDto) {
    return ResponseEntity.status(HttpStatus.CREATED)
        .body(emailTemplateService.createTemplate(foundationId, createTemplateDto));
  }

  @DeleteMapping("/{foundationId}/template")
  public ResponseEntity<CreateTemplateResponse> deactivateTemplate(
      @PathVariable(name = "foundationId") final UUID foundationId,
      @RequestParam(name = "id") @NotNull(message = "TemplateId is required")
          final UUID templateId) {

    return ResponseEntity.ok(emailTemplateService.deactivateTemplate(foundationId, templateId));
  }

  @GetMapping(path = "/{foundationId}/template")
  public ResponseEntity<List<EmailTemplatesDto>> getTemplates(
      @PathVariable(name = "foundationId") final UUID foundationId,
      @RequestParam(name = "isActive", required = false) final Boolean isActive) {
    return ResponseEntity.ok(emailTemplateService.listTemplates(foundationId, isActive));
  }

  @PostMapping("/{foundationId}/emails")
  public ResponseEntity<Map<String, UUID>> sendEmails(
      @PathVariable(name = "foundationId") final UUID foundationId,
      @RequestBody @Valid final SendEmails sendEmails) {

    return ResponseEntity.ok(emailTemplateService.sendEmails(foundationId, sendEmails));
  }

  @GetMapping("/{foundationId}/emails")
  public ResponseEntity<Map<UUID, List<SentEmailsList>>> listSentEmails(
      @PathVariable(name = "foundationId") final UUID foundationId,
      @RequestParam(name = "nonProfitId", required = false) final UUID nonProfitId) {
    return ResponseEntity.ok(emailTemplateService.listSentEmails(foundationId, nonProfitId));
  }

  @GetMapping("/{foundationId}/{templateId}/emails")
  public ResponseEntity<List<SentEmailsList>> listSentEmailsByTemplateId(
      @PathVariable(name = "foundationId") final UUID foundationId,
      @PathVariable(name = "templateId") final UUID templateId) {
    return ResponseEntity.ok(
        emailTemplateService.listSentEmailsByTemplateId(foundationId, templateId));
  }
}
