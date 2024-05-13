package com.temelio.demo.repository;

import com.temelio.demo.entity.NonProfit;
import java.util.List;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface NonProfitRepository extends JpaRepository<NonProfit, UUID> {

  @Transactional(readOnly = true)
  @Query(
      "FROM NonProfit np WHERE np.foundation.id=:foundationId AND (:isActive IS NULL OR np.active=:isActive)")
  List<NonProfit> findByFoundationIdAndStatus(
      @Param("foundationId") final UUID foundationId, @Param("isActive") final Boolean isActive);

  @Transactional(readOnly = true)
  List<NonProfit> findByIdIn(final List<UUID> nonProfitIds);
}
