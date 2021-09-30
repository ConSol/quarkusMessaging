package de.consol.dus.order.boundary.messaging.jms.incoming;

import de.consol.dus.order.CustomerService;
import de.consol.dus.order.boundary.request.CreateCustomerRequest;
import io.jaegertracing.internal.JaegerSpanContext;
import io.opentracing.References;
import io.opentracing.Span;
import io.opentracing.Tracer;
import io.opentracing.tag.Tags;
import io.smallrye.reactive.messaging.amqp.IncomingAmqpMetadata;
import io.smallrye.reactive.messaging.annotations.Blocking;
import java.math.BigInteger;
import java.util.Optional;
import java.util.concurrent.CompletionStage;
import javax.enterprise.context.ApplicationScoped;
import javax.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.eclipse.microprofile.reactive.messaging.Incoming;
import org.eclipse.microprofile.reactive.messaging.Message;

@ApplicationScoped
@RequiredArgsConstructor
@Slf4j
public class NewCustomerReceiver {

  private final CustomerService customerService;
  private final Tracer tracer;

  @Incoming("jms-new-customer-channel")
  @Blocking
  @Transactional
  CompletionStage<Void> receiveColor(Message<CreateCustomerRequest> message) {
    final CreateCustomerRequest request = message.getPayload();
    log.info("Received customer: {}", request);
    final Span receiveSpan = constructAndStartSendSpan(message);
    customerService.createNewCustomer(request);
    receiveSpan.finish();
    return message.ack();
  }

  private Span constructAndStartSendSpan(Message<CreateCustomerRequest> received) {
    final IncomingAmqpMetadata metadata =
        received.getMetadata(IncomingAmqpMetadata.class).orElseThrow();
    final JaegerSpanContext context = createJaegerSpanContextFromMetadata(metadata);
    final String destination = Optional.of(metadata)
        .map(IncomingAmqpMetadata::getAddress)
        .orElse("<UNKNOWN>");
    return buildAndStartReceiveSpan(context, destination);
  }

  private JaegerSpanContext createJaegerSpanContextFromMetadata(IncomingAmqpMetadata metadata) {
    final String uberTraceId = Optional.ofNullable(metadata)
        .map(IncomingAmqpMetadata::getCorrelationId)
        .orElse("");
    final String[] parts = uberTraceId.split(":");
    return new JaegerSpanContext(
        high(parts[0]),
        new BigInteger(parts[0], 16).longValue(),
        new BigInteger(parts[1], 16).longValue(),
        new BigInteger(parts[2], 16).longValue(),
        new BigInteger(parts[3], 16).byteValue());
  }

  private static long high(String hexString) {
    final int hexStringLength = hexString.length();
    return hexStringLength > 16
        ? Long.parseLong(hexString.substring(0, hexStringLength - 16), 16)
        : 0L;
  }

  private Span buildAndStartReceiveSpan(JaegerSpanContext context, String destination) {
    return tracer
        .buildSpan("From_" + destination)
        .withTag(Tags.SPAN_KIND.getKey(), Tags.SPAN_KIND_CONSUMER)
        .withTag(Tags.COMPONENT.getKey(), "java-amqp")
        .withTag(Tags.MESSAGE_BUS_DESTINATION.getKey(), destination)
        .withTag(Tags.PEER_SERVICE.getKey(), "jms")
        .addReference(References.FOLLOWS_FROM, context)
        .start();
  }
}