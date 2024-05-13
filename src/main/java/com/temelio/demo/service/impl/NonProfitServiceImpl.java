package com.temelio.demo.service.impl;

import com.temelio.demo.dto.FoundationAndNonProfitDto;
import com.temelio.demo.dto.FoundationAndNonProfitResponse;
import com.temelio.demo.entity.NonProfit;
import com.temelio.demo.repository.FoundationRepository;
import com.temelio.demo.repository.NonProfitRepository;
import com.temelio.demo.service.NonProfitService;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class NonProfitServiceImpl implements NonProfitService {

  @Autowired private transient NonProfitRepository nonProfitRepository;

  @Autowired private transient FoundationRepository foundationRepository;

  @Override
  @Transactional
  public FoundationAndNonProfitResponse createNonProfit(
      final UUID foundationId, final FoundationAndNonProfitDto foundationAndNonProfitDto) {
    final FoundationAndNonProfitResponse response = new FoundationAndNonProfitResponse(null, false);
    foundationRepository
        .findById(foundationId)
        .ifPresentOrElse(
            foundation -> {
              final NonProfit nonProfit = new NonProfit();
              nonProfit.setActive(true);
              nonProfit.setEmail(foundationAndNonProfitDto.getEmail());
              nonProfit.setName(foundationAndNonProfitDto.getName());
              nonProfit.setAddress(foundationAndNonProfitDto.getAddress());
              nonProfit.setFoundation(foundation);
              final NonProfit savedNonProfit = nonProfitRepository.save(nonProfit);
              response.setId(savedNonProfit.getId());
            },
            () -> {
              throw new RuntimeException("Foundation with the given Id is not found");
            });
    return response;
  }

  @Transactional(readOnly = true)
  @Override
  public List<FoundationAndNonProfitResponse> findNonProfits(
      final UUID foundationId, final Boolean isActive) {
    return nonProfitRepository.findByFoundationIdAndStatus(foundationId, isActive).stream()
        .sorted(Comparator.comparing(NonProfit::getCreatedOn))
        .map(
            nonProfit ->
                new FoundationAndNonProfitResponse(
                    nonProfit.getId(),
                    nonProfit.getEmail(),
                    nonProfit.getAddress(),
                    nonProfit.getName(),
                    nonProfit.isActive()))
        .toList();
  }

  @Override
  public Map<String, UUID> deActivateNonProfit(final UUID foundationId, final UUID nonProfitId) {
    final NonProfit nonProfit =
        nonProfitRepository
            .findById(nonProfitId)
            .orElseThrow(() -> new RuntimeException("NonProfit Not found"));
    if (!nonProfit.getFoundation().getId().equals(foundationId)) {
      throw new RuntimeException("Failed to delete non-profit");
    }
    nonProfit.setActive(false);
    nonProfitRepository.save(nonProfit);
    return Map.of("id", nonProfitId);
  }
}
