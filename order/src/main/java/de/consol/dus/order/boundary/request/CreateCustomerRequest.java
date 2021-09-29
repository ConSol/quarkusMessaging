package de.consol.dus.order.boundary.request;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

@Value
@Jacksonized
@AllArgsConstructor
public class CreateCustomerRequest {
  @NotNull @Email
  String email;
}
