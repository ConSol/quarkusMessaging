package de.consol.dus.order.boundary.request;

import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

@Value
@Jacksonized
@AllArgsConstructor
public class CreateOrderRequest {
  UUID uuid;
  String description;
  String customerEmail;
}
