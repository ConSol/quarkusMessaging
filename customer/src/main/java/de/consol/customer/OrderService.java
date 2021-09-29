package de.consol.customer;

import de.consol.customer.boundary.repository.CustomerRepository;
import de.consol.customer.boundary.repository.OrderRepository;
import de.consol.customer.boundary.response.OrderResponse;
import de.consol.customer.entity.CustomerEntity;
import de.consol.customer.boundary.request.CreateOrderRequest;
import de.consol.customer.entity.OrderEntity;
import de.consol.customer.entity.mapper.OrderMapper;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import javax.enterprise.context.ApplicationScoped;
import javax.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@ApplicationScoped
@RequiredArgsConstructor
public class OrderService {

  private final CustomerRepository customerRepository;
  private final OrderRepository orderRepository;
  private final OrderMapper orderMapper;

  @Transactional
  public OrderResponse createOrder(CreateOrderRequest request) {
    final CustomerEntity customer = customerRepository.findByEmail(request.getCustomerEmail())
        .orElseThrow(IllegalStateException::new);
    final OrderEntity toSave = orderMapper.requestToEntity(request).setCustomer(customer);
    return orderMapper.entityToResponse(orderRepository.save(toSave));
  }

  @Transactional
  public List<OrderResponse> getOrdersByCustomerEmail(String customerEmail) {
    return customerRepository.findByEmail(customerEmail)
        .map(CustomerEntity::getEmail)
        .map(orderRepository::findByCustomerEmail)
        .orElseGet(Collections::emptyList).stream()
        .map(orderMapper::entityToResponse)
        .collect(Collectors.toList());
  }
}
