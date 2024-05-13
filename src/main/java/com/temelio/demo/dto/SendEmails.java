package com.temelio.demo.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.UUID;

@Getter
@Setter
public class SendEmails {
    @NotNull(message = "Template Id is required")
    private UUID templateId;
    @NotEmpty(message = "Select atleast one non-profit")
    private List<UUID> nonProfitIds;
}
