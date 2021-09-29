package de.consol.dus.order.boundary.messaging.kafka.outgoing;

import de.consol.dus.order.boundary.response.OrderResponse;
import javax.enterprise.context.ApplicationScoped;
import lombok.extern.slf4j.Slf4j;
import org.eclipse.microprofile.reactive.messaging.Channel;
import org.eclipse.microprofile.reactive.messaging.Emitter;

@ApplicationScoped
@Slf4j
public class NewOrderEmitter {

  private final Emitter<OrderResponse> emitter;

  public NewOrderEmitter(
      @Channel("kafka-new-order-channel") Emitter<OrderResponse> emitter) {
    this.emitter = emitter;
  }

  public void emit(OrderResponse order) {
    log.info("Sending newly created order: {}", order);
    emitter.send(order);
  }
}

