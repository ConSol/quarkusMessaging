package de.consol.customer.boundary.request;

import java.time.Instant;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

@Value
@Jacksonized
@AllArgsConstructor
public class CreateOrderRequest {
  UUID uuid;
  String customerEmail;
  Instant created;
  int totalInCents;
}
