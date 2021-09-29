package de.consol.customer.boundary.response;

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
  String customerEmail;
  Instant created;
  int totalInCents;
}
