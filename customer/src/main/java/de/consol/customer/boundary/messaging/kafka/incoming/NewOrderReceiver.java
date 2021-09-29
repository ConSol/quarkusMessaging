package de.consol.customer.boundary.messaging.kafka.incoming;

import de.consol.customer.OrderService;
import de.consol.customer.boundary.request.CreateOrderRequest;
import io.smallrye.reactive.messaging.annotations.Blocking;
import javax.enterprise.context.ApplicationScoped;
import javax.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.eclipse.microprofile.reactive.messaging.Incoming;

@ApplicationScoped
@RequiredArgsConstructor
@Slf4j
public class NewOrderReceiver {

  private final OrderService orderService;

  @Incoming("kafka-new-order-channel")
  @Blocking
  @Transactional
  void receiveGroup(CreateOrderRequest request) {
    log.info("Received newly created order: {}", request);
    try {
      orderService.createOrder(request);
    } catch (Exception e) {
      log.error("Error:", e);
    }
  }
}
