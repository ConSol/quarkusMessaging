package de.consol.dus.order.boundary.messaging.jms.incoming;

import de.consol.dus.order.CustomerService;
import de.consol.dus.order.boundary.request.CreateCustomerRequest;
import io.smallrye.reactive.messaging.annotations.Blocking;
import java.util.concurrent.CompletionStage;
import javax.enterprise.context.ApplicationScoped;
import javax.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.eclipse.microprofile.reactive.messaging.Incoming;
import org.eclipse.microprofile.reactive.messaging.Message;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@ApplicationScoped
@RequiredArgsConstructor
@Slf4j
public class NewCustomerReceiver {

  private final CustomerService customerService;

  @Incoming("jms-new-customer-channel")
  @Blocking
  @Transactional
  CompletionStage<Void> receiveColor(Message<CreateCustomerRequest> message) {
    final CreateCustomerRequest request = message.getPayload();
    log.info("Received customer: {}", request);
    customerService.createNewCustomer(request);
    return message.ack();
  }
}