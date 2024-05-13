package com.temelio.demo.repository;

import com.temelio.demo.entity.EmailTemplates;
import java.util.List;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface EmailTemplateRepository extends JpaRepository<EmailTemplates, UUID> {

  @Transactional
  @Modifying
  @Query(
      "UPDATE EmailTemplates et SET et.active=:status WHERE et.id=:templateId AND et.foundation.id=:foundationId")
  int updateTemplateStatus(
      @Param("status") final boolean status,
      @Param("templateId") final UUID templateId,
      @Param("foundationId") final UUID foundationId);

  @Transactional(readOnly = true)
  @Query(
      "FROM EmailTemplates et WHERE et.foundation.id=:foundationId AND (:isActive IS NULL OR et.active=:isActive)")
  List<EmailTemplates> findByFoundationId(
      @Param("foundationId") final UUID foundationId, @Param("isActive") final Boolean isActive);
}
