package com.temelio.demo.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;

import java.util.UUID;

@Getter
@Setter
@Entity
@Table(name = "emails_sent")
public class SentEmails extends CommonAttributes{

    @Column(nullable = false, name = "email_sent_id")
    private UUID mailSentId;

    @Column(name ="to_email", nullable = false)
    private String toEmail;

    @Column(name = "subject", nullable = false,columnDefinition = "text")
    private String subject;

    @Column(name = "body", nullable = false, columnDefinition = "text")
    private String body;

    @Column(name = "is_sent", nullable = false)
    @ColumnDefault(value = "true")
    private boolean success;

    @ManyToOne
    @JoinColumn(name = "foundation_id", nullable = false)
    private Foundation foundation;

    @ManyToOne
    @JoinColumn(name = "non_profit_id",nullable = false)
    private NonProfit nonProfit;

    @ManyToOne
    @JoinColumn(name = "template_id",nullable = false)
    private EmailTemplates template;

    @Column(name = "failure_reason")
    private String failureReason;
}
