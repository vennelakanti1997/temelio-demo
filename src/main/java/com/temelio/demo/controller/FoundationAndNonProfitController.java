package com.temelio.demo.controller;

import com.temelio.demo.dto.FoundationAndNonProfitDto;
import com.temelio.demo.dto.FoundationAndNonProfitResponse;
import com.temelio.demo.service.FoundationService;
import com.temelio.demo.service.NonProfitService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(value = {"*"})
@RestController
@Validated
@Tag(
    name = "Foundation And Non-Profit Controller",
    description = "CRUD operations for foundation and/or non-profit")
public class FoundationAndNonProfitController {

  @Autowired private transient FoundationService foundationService;

  @Autowired private transient NonProfitService nonProfitService;

  @Operation(description = "API to save foundation and/or non-profit")
  @PostMapping(path = "/{type}")
  public ResponseEntity<FoundationAndNonProfitResponse> createNonProfit(
      @PathVariable(name = "type")
          @NotBlank(message = "Organisation type is required")
          @Pattern(regexp = "foundation|nonprofit", message = "Invalid organisation type")
          final String type,
      @RequestParam(name = "foundationId", required = false) final UUID foundationId,
      @RequestBody @Valid final FoundationAndNonProfitDto foundationAndNonProfitDto) {
    return ResponseEntity.status(HttpStatus.CREATED)
        .body(
            "foundation".equals(type)
                ? foundationService.createFoundation(foundationAndNonProfitDto)
                : nonProfitService.createNonProfit(foundationId, foundationAndNonProfitDto));
  }

  @GetMapping(path = "/{foundationId}/nonprofits")
  public ResponseEntity<List<FoundationAndNonProfitResponse>> listNonProfits(
      @PathVariable(name = "foundationId") final UUID foundationId,
      @RequestParam(name = "isActive", required = false) final Boolean isActive) {
    return ResponseEntity.ok(nonProfitService.findNonProfits(foundationId, isActive));
  }

  @DeleteMapping(path = "/{foundationId}/nonprofits")
  public ResponseEntity<Map<String, UUID>> deleteNonProfit(
      @PathVariable(name = "foundationId") final UUID foundationId,
      @RequestParam(name = "id") final UUID nonProfitId) {
    return ResponseEntity.ok(nonProfitService.deActivateNonProfit(foundationId, nonProfitId));
  }

  @GetMapping("/foundation")
  public ResponseEntity<Map<String, UUID>> findFoundationId(
      @RequestParam(name = "email") @NotBlank(message = "Email is required") final String email,
      @NotBlank(message = "Name is required") @RequestParam(name = "userName") final String name) {
    return ResponseEntity.ok(foundationService.findFoundationId(email, name));
  }
}
