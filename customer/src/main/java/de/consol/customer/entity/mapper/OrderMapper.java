package de.consol.customer.entity.mapper;

import de.consol.customer.boundary.request.CreateOrderRequest;
import de.consol.customer.boundary.response.OrderResponse;
import de.consol.customer.entity.OrderEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "cdi")
public interface OrderMapper {
  OrderEntity requestToEntity(CreateOrderRequest dto);

  @Mapping(source = "customer.email", target = "customerEmail")
  OrderResponse entityToResponse(OrderEntity entity);
}
