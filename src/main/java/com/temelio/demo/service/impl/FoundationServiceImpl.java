package com.temelio.demo.service.impl;

import com.temelio.demo.dto.FoundationAndNonProfitDto;
import com.temelio.demo.dto.FoundationAndNonProfitResponse;
import com.temelio.demo.entity.Foundation;
import com.temelio.demo.repository.FoundationRepository;
import com.temelio.demo.service.FoundationService;
import java.util.Map;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class FoundationServiceImpl implements FoundationService {

  @Autowired private transient FoundationRepository foundationRepository;

  @Override
  @Transactional
  public FoundationAndNonProfitResponse createFoundation(
      final FoundationAndNonProfitDto foundationAndNonProfitDto) {
    final Foundation foundation = new Foundation();
    foundation.setEmail(foundationAndNonProfitDto.getEmail());
    foundation.setName(foundationAndNonProfitDto.getName());
    foundation.setAddress(foundationAndNonProfitDto.getAddress());
    final Foundation savedFoundation = foundationRepository.save(foundation);
    return new FoundationAndNonProfitResponse(savedFoundation.getId(), true);
  }

  @Transactional(readOnly = true)
  @Override
  public Map<String, UUID> findFoundationId(final String email, final String name) {
    final Foundation foundation =
        foundationRepository
            .findByNameAndEmail(name, email)
            .orElseThrow(() -> new RuntimeException("Foundation Not found"));
    return Map.of("id", foundation.getId());
  }
}
