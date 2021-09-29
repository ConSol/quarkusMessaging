package de.consol.customer.boundary.messaging.jms.outgoing;

import de.consol.customer.boundary.response.CustomerResponse;
import javax.enterprise.context.ApplicationScoped;
import lombok.extern.slf4j.Slf4j;
import org.eclipse.microprofile.reactive.messaging.Channel;
import org.eclipse.microprofile.reactive.messaging.Emitter;

@ApplicationScoped
@Slf4j
public class NewCustomerEmitter {
  private final Emitter<CustomerResponse> emitter;

  public NewCustomerEmitter(@Channel("jms-new-customer-channel") Emitter<CustomerResponse> emitter) {
    this.emitter = emitter;
  }

  public void emit(CustomerResponse response) {
    log.info("Sending new customer: {}", response);
    emitter.send(response);
  }
}
