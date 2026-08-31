package io.morpheus.payments.payment.application.service;

import io.morpheus.payments.events.cloudevents.CloudEventFactory;
import io.morpheus.payments.events.cloudevents.CloudEventPublisher;
import io.morpheus.payments.payment.application.port.out.OutboxDispatchPort;
import io.morpheus.payments.payment.application.port.out.OutboxEventPayloadDeserializerPort;
import io.morpheus.payments.payment.application.result.OutboxEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class OutboxDispatcher {

    private final OutboxDispatchPort outboxDispatchPort;

    private final OutboxEventPayloadDeserializerPort outboxEventPayloadDeserializerPort;

    private final CloudEventFactory cloudEventFactory;

    private final CloudEventPublisher cloudEventPublisher;

    public void dispatch(final int batchSize) {

        final List<OutboxEvent> events = outboxDispatchPort.claimPublishableBatch(batchSize);

        events.forEach(this::dispatch);
    }

    private void dispatch(final OutboxEvent outboxEvent) {

        final var event = outboxEventPayloadDeserializerPort.deserialize(outboxEvent.eventType(), outboxEvent.payload());
        final var cloudEvent = cloudEventFactory.create(event);

        cloudEventPublisher.publish(cloudEvent);
    }

}
