package de.consol.customer.boundary.request;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

@Value
@Jacksonized
@AllArgsConstructor
public class CreateCustomerRequest {
  @NotNull @Email
  String email;

  @NotNull @Size(min = 2, max = 64)
  String firstName;

  @NotNull @Size(min = 2, max = 64)
  String lastName;
}
