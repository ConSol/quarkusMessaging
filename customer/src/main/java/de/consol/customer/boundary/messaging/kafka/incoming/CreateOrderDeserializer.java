package de.consol.customer.boundary.messaging.kafka.incoming;

import de.consol.customer.boundary.request.CreateOrderRequest;
import io.quarkus.kafka.client.serialization.ObjectMapperDeserializer;

public class CreateOrderDeserializer extends ObjectMapperDeserializer<CreateOrderRequest> {
  public CreateOrderDeserializer() {
    super(CreateOrderRequest.class);
  }
}
