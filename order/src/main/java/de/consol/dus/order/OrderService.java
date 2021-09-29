package de.consol.dus.order;

import de.consol.dus.order.boundary.messaging.kafka.outgoing.NewOrderEmitter;
import de.consol.dus.order.boundary.repository.CustomerRepository;
import de.consol.dus.order.boundary.repository.OrderRepository;
import de.consol.dus.order.boundary.request.CreateOrderRequest;
import de.consol.dus.order.boundary.response.OrderResponse;
import de.consol.dus.order.entity.CustomerEntity;
import de.consol.dus.order.entity.OrderEntity;
import de.consol.dus.order.entity.mapper.OrderMapper;
import java.time.Instant;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;
import javax.enterprise.context.ApplicationScoped;
import javax.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@ApplicationScoped
@RequiredArgsConstructor
public class OrderService {

  private static final Random random = new Random();

  private final CustomerRepository customerRepository;
  private final OrderRepository orderRepository;
  private final OrderMapper orderMapper;
  private final NewOrderEmitter newOrderEmitter;

  public OrderResponse createOrder(CreateOrderRequest request) {
    final CustomerEntity customer = customerRepository.findByEmail(request.getCustomerEmail())
        .orElseThrow(IllegalStateException::new);
    final OrderEntity toSave =
        orderMapper.requestToEntity(request, Instant.now(), random.nextInt(10_00))
            .setCustomer(customer);
    final OrderResponse response = orderMapper.entityToResponse(orderRepository.save(toSave));
    newOrderEmitter.emit(response);
    return response;
  }

  @Transactional
  public List<OrderResponse> findAllOrders() {
    return orderRepository.findAll().stream()
        .map(orderMapper::entityToResponse)
        .collect(Collectors.toList());
  }
}
