package io.morpheus.payments.payment.application.service;

import io.cloudevents.CloudEvent;
import io.morpheus.payments.events.cloudevents.CloudEventFactory;
import io.morpheus.payments.events.cloudevents.CloudEventPublisher;
import io.morpheus.payments.events.types.MoneyTransferredEvent;
import io.morpheus.payments.payment.application.port.out.OutboxDispatchPort;
import io.morpheus.payments.payment.application.port.out.OutboxEventPayloadDeserializerPort;
import io.morpheus.payments.payment.application.result.OutboxEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;

@Service
@RequiredArgsConstructor
public class OutboxDispatcher {

    private final OutboxDispatchPort outboxDispatchPort;
    private final OutboxEventPayloadDeserializerPort outboxEventPayloadDeserializerPort;
    private final CloudEventFactory cloudEventFactory;
    private final CloudEventPublisher cloudEventPublisher;
    private final RetryPolicy retryPolicy;

    public void dispatch(final int batchSize) {
        final List<OutboxEvent> events = outboxDispatchPort.claimPublishableBatch(batchSize);

        events.forEach(this::dispatch);
    }

    private void dispatch(final OutboxEvent outboxEvent) {
        try {
            final MoneyTransferredEvent event = outboxEventPayloadDeserializerPort.deserialize(
                                                                    outboxEvent.eventType(), outboxEvent.payload());
            final CloudEvent cloudEvent = cloudEventFactory.create(event);

            cloudEventPublisher.publish(cloudEvent);

            outboxDispatchPort.markPublished(
                outboxEvent.id(),
                Instant.now());
        } catch (final Exception exception) {
            handleFailure(outboxEvent);
        }
    }

    private void handleFailure(final OutboxEvent outboxEvent)
    {
        final int nextRetryCount = outboxEvent.retryCount() + 1;

        if (retryPolicy.shouldRetry(nextRetryCount))    {
            final Instant nextRetryAt = Instant.now().plus(retryPolicy.delayFor(nextRetryCount));

            outboxDispatchPort.scheduleRetry(outboxEvent.id(), nextRetryAt);

            return;
        }

        outboxDispatchPort.markFailed(outboxEvent.id());
    }


}
