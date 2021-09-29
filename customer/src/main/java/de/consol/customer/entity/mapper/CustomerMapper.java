package de.consol.customer.entity.mapper;

import de.consol.customer.boundary.request.CreateCustomerRequest;
import de.consol.customer.boundary.response.CustomerResponse;
import de.consol.customer.entity.CustomerEntity;
import java.time.Instant;
import org.mapstruct.Mapper;

@Mapper(componentModel = "cdi")
public interface CustomerMapper {
  CustomerEntity requestToEntity(CreateCustomerRequest request, Instant created);
  CustomerResponse entityToResponse(CustomerEntity entity);
}
