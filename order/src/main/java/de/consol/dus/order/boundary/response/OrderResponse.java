package de.consol.dus.order.boundary.response;

import java.time.Instant;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

@Value
@Jacksonized
@AllArgsConstructor
public class OrderResponse {
  UUID uuid;
  String description;
  String customerEmail;
  Instant created;
  int totalInCents;
}
