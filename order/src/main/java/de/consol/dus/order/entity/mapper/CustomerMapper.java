package de.consol.dus.order.entity.mapper;

import de.consol.dus.order.boundary.request.CreateCustomerRequest;
import de.consol.dus.order.boundary.response.CustomerResponse;
import de.consol.dus.order.entity.CustomerEntity;
import java.time.Instant;
import org.mapstruct.Mapper;

@Mapper(componentModel = "cdi")
public interface CustomerMapper {
  CustomerEntity requestToEntity(CreateCustomerRequest request);
  CustomerResponse entityToResponse(CustomerEntity entity);
}
