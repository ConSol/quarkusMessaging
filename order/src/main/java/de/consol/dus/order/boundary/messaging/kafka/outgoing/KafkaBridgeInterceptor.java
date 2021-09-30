package de.consol.dus.order.boundary.messaging.kafka.outgoing;

import io.opentracing.Tracer;
import io.opentracing.contrib.kafka.TracingKafkaUtils;
import io.smallrye.reactive.messaging.kafka.api.OutgoingKafkaRecordMetadata;
import java.util.Objects;
import java.util.Optional;
import javax.annotation.Priority;
import javax.interceptor.AroundInvoke;
import javax.interceptor.Interceptor;
import javax.interceptor.InvocationContext;
import javax.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.apache.kafka.common.header.internals.RecordHeaders;
import org.eclipse.microprofile.reactive.messaging.Message;

@KafkaBridge
@Interceptor
@Priority(Interceptor.Priority.APPLICATION)
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
@RequiredArgsConstructor
public class KafkaBridgeInterceptor {
  Tracer tracer;

  @AroundInvoke
  Object enhanceMessageWithSpan(@NotNull InvocationContext context) throws Exception {
    final Object[] parameters = Objects.requireNonNull(context).getParameters();
    for (int index = 0; index < parameters.length; ++index) {
      final int lambdaIndex = index;
      toMessage(parameters[lambdaIndex])
          .map(this::constructMessageWithTraceHeader)
          .ifPresent(message -> parameters[lambdaIndex] = message);
    }
    return context.proceed();
  }

  @SuppressWarnings("unchecked")
  @NotNull
  private Optional<Message<?>> toMessage(@NotNull Object parameter) {
    return Optional.of(parameter)
        .filter(Message.class::isInstance)
        .map(Message.class::cast);
  }

  @NotNull
  private Message<?> constructMessageWithTraceHeader(@NotNull Message<?> messageParameter) {
    final RecordHeaders headers = new RecordHeaders();
    TracingKafkaUtils.inject(tracer.activeSpan().context(), headers, tracer);
    return Objects.requireNonNull(messageParameter).addMetadata(OutgoingKafkaRecordMetadata.builder()
        .withHeaders(headers)
        .build());
  }
}