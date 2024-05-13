package com.temelio.demo.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
public class SentEmailsList {
    private UUID id;
    private String toEmail;
    @JsonProperty("isSent")
    private boolean sent;
    private String subject;
    private String body;
    private String failureReason;
    private UUID nonProfitId;
    private UUID templateId;
    @JsonIgnore
    private UUID emailSentId;
    private String nonProfitName;
}
