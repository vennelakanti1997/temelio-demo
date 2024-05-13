package com.temelio.demo.service.impl;

import com.temelio.demo.dto.*;
import com.temelio.demo.entity.EmailTemplates;
import com.temelio.demo.entity.Foundation;
import com.temelio.demo.entity.NonProfit;
import com.temelio.demo.entity.SentEmails;
import com.temelio.demo.repository.EmailTemplateRepository;
import com.temelio.demo.repository.FoundationRepository;
import com.temelio.demo.repository.NonProfitRepository;
import com.temelio.demo.repository.SentEmailsRepository;
import com.temelio.demo.service.EmailTemplateService;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class EmailTemplateServiceImpl implements EmailTemplateService {

  @Autowired private transient SentEmailsRepository sentEmailsRepository;
  @Autowired private transient FoundationRepository foundationRepository;
  @Autowired private transient NonProfitRepository nonProfitRepository;
  @Autowired private transient EmailTemplateRepository emailTemplateRepository;

  @Transactional
  @Override
  public CreateTemplateResponse createTemplate(
      final UUID foundationId, final CreateTemplateDto createTemplateDto) {
    final CreateTemplateResponse createTemplateResponse = new CreateTemplateResponse();
    final Foundation foundation =
        foundationRepository
            .findById(foundationId)
            .orElseThrow(() -> new RuntimeException("Foundation with the given Id does not exist"));
    final EmailTemplates emailTemplates = new EmailTemplates();
    emailTemplates.setBodyAndVariables(createTemplateDto.getBody());
    emailTemplates.setFoundation(foundation);
    emailTemplates.setActive(true);
    emailTemplates.setSubjectAndVariables(createTemplateDto.getSubject());
    final EmailTemplates savedEmailTemplates = emailTemplateRepository.save(emailTemplates);
    createTemplateResponse.setTemplateId(savedEmailTemplates.getId());
    createTemplateResponse.setActive(true);
    return createTemplateResponse;
  }

  @Transactional
  @Override
  public CreateTemplateResponse deactivateTemplate(final UUID foundationId, final UUID templateId) {
    int updatedCount =
        emailTemplateRepository.updateTemplateStatus(false, templateId, foundationId);
    if (updatedCount > 0) {
      return new CreateTemplateResponse(templateId, false);
    }
    throw new RuntimeException("Template not found");
  }

  @Override
  public List<EmailTemplatesDto> listTemplates(final UUID foundationId, final Boolean isActive) {
    return emailTemplateRepository.findByFoundationId(foundationId, isActive).stream()
        .map(
            emailTemplates ->
                new EmailTemplatesDto(
                    emailTemplates.isActive(),
                    emailTemplates.getId(),
                    emailTemplates.getSubjectAndVariables(),
                    emailTemplates.getBodyAndVariables()))
        .toList();
  }

  @Override
  @Transactional
  public Map<String, UUID> sendEmails(final UUID foundationId, final SendEmails sendEmails) {
    final Foundation foundation =
        foundationRepository
            .findById(foundationId)
            .orElseThrow(() -> new RuntimeException("Foundation with given Id is not found"));
    final EmailTemplates emailTemplates =
        emailTemplateRepository
            .findById(sendEmails.getTemplateId())
            .orElseThrow(() -> new RuntimeException("Template Not found"));
    if (!emailTemplates.isActive()) {
      throw new RuntimeException("Email Template is deactivated");
    }
    final UUID emailSentId = UUID.randomUUID();
    final List<NonProfit> nonProfits = nonProfitRepository.findByIdIn(sendEmails.getNonProfitIds());
    if (nonProfits.isEmpty()) {
      throw new RuntimeException("Please select valid non-profits");
    }
    nonProfits.forEach(
        nonProfit -> {
          final SentEmails sentEmail = new SentEmails();
          sentEmail.setMailSentId(emailSentId);
          sentEmail.setToEmail(nonProfit.getEmail());
          sentEmail.setTemplate(emailTemplates);
          sentEmail.setNonProfit(nonProfit);
          sentEmail.setFoundation(foundation);
          final boolean success =
              nonProfit.isActive() && nonProfit.getFoundation().getId().equals(foundationId);
          sentEmail.setSuccess(success);
          if (!success) {
            sentEmail.setFailureReason(
                nonProfit.isActive() ? "Non-Profit is inactive" : "Invalid non-profit id");

          } else {

            final String subject =
                generateString(emailTemplates.getSubjectAndVariables(), nonProfit);
            sentEmail.setSubject(subject);
            final String body = generateString(emailTemplates.getBodyAndVariables(), nonProfit);
            sentEmail.setBody(body);
          }
          sentEmailsRepository.save(sentEmail);
        });
    return Map.of("requestId", emailSentId);
  }

  @Transactional(readOnly = true)
  @Override
  public Map<UUID, List<SentEmailsList>> listSentEmails(
      final UUID foundationId, final UUID nonProfitId) {
    if (nonProfitId != null) {
      final NonProfit nonProfit =
          nonProfitRepository
              .findById(nonProfitId)
              .orElseThrow(() -> new RuntimeException("Non Profit Not Found"));
      if (!nonProfit.getFoundation().getId().equals(foundationId)) {
        throw new RuntimeException("Foundation Not Found");
      }
    } else {
      foundationRepository
          .findById(foundationId)
          .orElseThrow(() -> new RuntimeException("Foundation Not Found"));
    }

    final List<SentEmailsList> sentEmailsLists =
        nonProfitId == null
            ? sentEmailsRepository.fetchSentEmailsByFoundationId(foundationId)
            : sentEmailsRepository.fetchSentEmailsByNonProfitId(nonProfitId);
    return nonProfitId == null
        ? sentEmailsLists.stream().collect(Collectors.groupingBy(SentEmailsList::getEmailSentId))
        : Map.of(UUID.randomUUID(), sentEmailsLists);
  }

  @Transactional(readOnly = true)
  @Override
  public List<SentEmailsList> listSentEmailsByTemplateId(
      final UUID foundationId, final UUID templateId) {
    foundationRepository
        .findById(foundationId)
        .orElseThrow(() -> new RuntimeException("Foundation Not Found"));
    final EmailTemplates emailTemplates =
        emailTemplateRepository
            .findById(templateId)
            .orElseThrow(() -> new RuntimeException("Template Not Found"));

    if (!emailTemplates.getFoundation().getId().equals(foundationId)){
      throw new RuntimeException("Template not Found");
    }
    return sentEmailsRepository.findByTemplateId(templateId);
  }

  private String generateString(final TemplateVariables object, final NonProfit nonProfit) {
    if (object.getContent() != null && !object.getContent().isBlank()) {
      final AtomicReference<String> content = new AtomicReference<>(object.getContent());
      final List<VariableDto> variables = object.getVariables();
      if (variables == null || variables.isEmpty()) {
        return content.get();
      } else {
        variables.forEach(
            variable -> {
              final String variableName = variable.getName();
              if (variable.getType().equalsIgnoreCase("string")) {
                if (variableName.equalsIgnoreCase("name")) {
                  content.set(content.get().replace("{name}", nonProfit.getName()));
                }
                if (variableName.equalsIgnoreCase("address")) {
                  content.set(content.get().replace("{address}", nonProfit.getAddress()));
                }
              }
            });
        return content.get();
      }
    }
    return null;
  }
}
