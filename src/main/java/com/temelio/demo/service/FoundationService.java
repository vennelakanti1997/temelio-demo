package com.temelio.demo.service;

import com.temelio.demo.dto.FoundationAndNonProfitDto;
import com.temelio.demo.dto.FoundationAndNonProfitResponse;

import java.util.List;
import java.util.Map;
import java.util.UUID;

public interface FoundationService {
    FoundationAndNonProfitResponse createFoundation(FoundationAndNonProfitDto foundationAndNonProfitDto);

    Map<String, UUID> findFoundationId(String email, String name);
}
