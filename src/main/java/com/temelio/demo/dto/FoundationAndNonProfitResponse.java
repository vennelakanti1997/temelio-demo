package com.temelio.demo.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.UUID;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FoundationAndNonProfitResponse {
  private UUID id;
  private String email;
  private String address;
  private String name;

  @JsonProperty("isFoundation")
  private boolean foundation;

  @JsonProperty("isActive")
  private boolean active;

  /**
   * @param id
   * @param email
   * @param address
   * @param name
   */
  public FoundationAndNonProfitResponse(
      final UUID id,
      final String email,
      final String address,
      final String name,
      final boolean active) {
    this.id = id;
    this.email = email;
    this.address = address;
    this.name = name;
    this.active = active;
  }

  /**
   * @param id
   * @param foundation
   */
  public FoundationAndNonProfitResponse(final UUID id, final boolean foundation) {
    this.id = id;
    this.foundation = foundation;
  }
}
