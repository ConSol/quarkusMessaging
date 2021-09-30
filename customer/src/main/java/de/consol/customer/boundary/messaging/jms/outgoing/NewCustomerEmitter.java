package de.consol.customer.boundary.messaging.jms.outgoing;

import de.consol.customer.boundary.response.CustomerResponse;

import io.jaegertracing.internal.JaegerSpanContext;
import io.opentracing.References;
import io.opentracing.Span;
import io.opentracing.Tracer;
import io.opentracing.tag.Tags;
import io.smallrye.mutiny.Uni;
import io.smallrye.reactive.messaging.amqp.OutgoingAmqpMetadata;
import javax.enterprise.context.ApplicationScoped;
import lombok.extern.slf4j.Slf4j;
import org.eclipse.microprofile.reactive.messaging.Channel;
import org.eclipse.microprofile.reactive.messaging.Emitter;
import org.eclipse.microprofile.reactive.messaging.Message;

@ApplicationScoped
@Slf4j
public class NewCustomerEmitter {

  private static final String DESTINATION = "jms-new-customer-channel";

  private final Emitter<CustomerResponse> emitter;
  private final Tracer tracer;

  public NewCustomerEmitter(
      @Channel("jms-new-customer-channel") Emitter<CustomerResponse> emitter,
      Tracer tracer) {
    this.emitter = emitter;
    this.tracer = tracer;
  }

  public void emit(CustomerResponse response) {
    log.info("Sending new customer: {}", response);
    final Span sendSpan = createAndStartSendSpan();
    // @formatter:off
    Uni.createFrom().item(response)
        .map(Message::of)
        .onItem()
        .transform(msg -> addUberTraceId(sendSpan, msg))
        .invoke(emitter::send)
        .subscribe()
        .asCompletionStage()
        .thenRun(sendSpan::finish);
    // @formatter:on
  }

  private Span createAndStartSendSpan() {
    return tracer.buildSpan("To_" + DESTINATION)
        .addReference(References.CHILD_OF, tracer.activeSpan().context())
        .withTag(Tags.SPAN_KIND.getKey(), Tags.SPAN_KIND_PRODUCER)
        .withTag(Tags.COMPONENT.getKey(), "java-amqp")
        .withTag(Tags.MESSAGE_BUS_DESTINATION.getKey(), DESTINATION)
        .withTag(Tags.PEER_SERVICE.getKey(), "jms")
        .start();
  }


  private Message<CustomerResponse> addUberTraceId(Span sendSpan, Message<CustomerResponse> msg) {
    return msg.addMetadata(OutgoingAmqpMetadata.builder()
        .withCorrelationId(extractUberTraceId(sendSpan))
        .build());
  }

  private String extractUberTraceId(Span span) {
    final JaegerSpanContext context = ((JaegerSpanContext) span.context());
    return String.format(
        "%s:%s:%s:%s",
        context.getTraceId(),
        Long.toHexString(context.getSpanId()),
        Long.toHexString(context.getParentId()),
        Integer.toHexString(context.getFlags()));
  }
}
