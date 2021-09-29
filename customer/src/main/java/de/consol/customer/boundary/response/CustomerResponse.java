package de.consol.customer.boundary.response;

import lombok.AllArgsConstructor;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

@Value
@Jacksonized
@AllArgsConstructor
public class CustomerResponse {
  String email;
  String firstName;
  String lastName;
}
