package de.consol.dus.order.entity.mapper;

import de.consol.dus.order.boundary.request.CreateOrderRequest;
import de.consol.dus.order.boundary.response.OrderResponse;
import de.consol.dus.order.entity.OrderEntity;
import java.time.Instant;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "cdi")
public interface OrderMapper {
  OrderEntity requestToEntity(CreateOrderRequest dto, Instant created, int totalInCents);

  @Mapping(source = "customer.email", target = "customerEmail")
  OrderResponse entityToResponse(OrderEntity entity);
}
