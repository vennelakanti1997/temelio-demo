package com.temelio.demo.service;

import com.temelio.demo.dto.FoundationAndNonProfitDto;
import com.temelio.demo.dto.FoundationAndNonProfitResponse;

import java.util.List;
import java.util.Map;
import java.util.UUID;

public interface NonProfitService {
    FoundationAndNonProfitResponse createNonProfit(UUID foundationId, FoundationAndNonProfitDto foundationAndNonProfitDto);

    List<FoundationAndNonProfitResponse> findNonProfits(UUID foundationId, Boolean isActive);

    Map<String, UUID> deActivateNonProfit(UUID foundationId, UUID nonProfitId);
}
