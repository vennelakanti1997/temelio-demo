package com.temelio.demo.repository;

import com.temelio.demo.dto.SentEmailsList;
import com.temelio.demo.entity.SentEmails;
import java.util.List;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface SentEmailsRepository extends JpaRepository<SentEmails, UUID> {

  @Transactional(readOnly = true)
  @Query(
      "SELECT new com.temelio.demo.dto.SentEmailsList(se.id,se.toEmail,se.success,se.subject,se.body,se.failureReason,se.nonProfit.id, se.template.id,se.mailSentId,se.nonProfit.name) FROM SentEmails se WHERE se.foundation.id=:foundationId")
  List<SentEmailsList> fetchSentEmailsByFoundationId(
      @Param("foundationId") final UUID foundationId);

  @Transactional(readOnly = true)
  @Query(
      "SELECT new com.temelio.demo.dto.SentEmailsList(se.id,se.toEmail,se.success,se.subject,se.body,se.failureReason,se.nonProfit.id, se.template.id,se.mailSentId,se.nonProfit.name) FROM SentEmails se WHERE se.nonProfit.id=:nonProfitId")
  List<SentEmailsList> fetchSentEmailsByNonProfitId(@Param("nonProfitId") final UUID nonProfitId);

  @Transactional(readOnly = true)
  @Query(
      "SELECT new com.temelio.demo.dto.SentEmailsList(se.id,se.toEmail,se.success,se.subject,se.body,se.failureReason,se.nonProfit.id, se.template.id,se.mailSentId,se.nonProfit.name) FROM SentEmails se WHERE se.template.id=:id")
  List<SentEmailsList> findByTemplateId(@Param("id") final UUID templateId);
}
