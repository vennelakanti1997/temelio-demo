package com.temelio.demo.service;

import com.temelio.demo.dto.*;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public interface EmailTemplateService {

  CreateTemplateResponse createTemplate(UUID foundationId, CreateTemplateDto createTemplateDto);

  CreateTemplateResponse deactivateTemplate(UUID foundationId, UUID templateId);

  List<EmailTemplatesDto> listTemplates(UUID foundationId, Boolean isActive);

    Map<String, UUID> sendEmails(UUID foundationId, SendEmails sendEmails);

    Map<UUID, List<SentEmailsList>> listSentEmails(UUID foundationId, UUID nonProfitId);

  List<SentEmailsList> listSentEmailsByTemplateId(UUID foundationId, UUID templateId);
}
