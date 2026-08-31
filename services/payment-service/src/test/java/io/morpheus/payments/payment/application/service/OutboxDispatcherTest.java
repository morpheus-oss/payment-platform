package io.morpheus.payments.payment.application.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.cloudevents.CloudEvent;
import io.morpheus.payments.events.cloudevents.CloudEventFactory;
import io.morpheus.payments.events.cloudevents.CloudEventPublisher;
import io.morpheus.payments.events.envelope.EventType;
import io.morpheus.payments.events.types.MoneyTransferredEvent;
import io.morpheus.payments.payment.application.port.out.OutboxDispatchPort;
import io.morpheus.payments.payment.application.port.out.OutboxEventPayloadDeserializerPort;
import io.morpheus.payments.payment.application.result.OutboxEvent;
import io.morpheus.payments.payment.persistence.adapter.OutboxEventPayloadDeserializerAdapter;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InOrder;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class OutboxDispatcherTest {

    @Mock
    private OutboxDispatchPort outboxDispatchPort;

    @Mock
    private OutboxEventPayloadDeserializerPort outboxEventPayloadDeserializerPort;

    @Mock
    private CloudEventFactory cloudEventFactory;

    @Mock
    private CloudEventPublisher cloudEventPublisher;

    @Mock
    private OutboxEvent outboxEvent;

    @Mock
    private MoneyTransferredEvent event;

    @Mock
    private CloudEvent cloudEvent;

    @Test
    void shouldReconstructAndPublishOutboxEvent() {

        when(outboxDispatchPort.claimPublishableBatch(10))
            .thenReturn(List.of(outboxEvent));

        when(outboxEventPayloadDeserializerPort.deserialize(outboxEvent.eventType(), outboxEvent.payload()))
            .thenReturn(event);

        when(cloudEventFactory.create(event))
            .thenReturn(cloudEvent);

        final OutboxDispatcher dispatcher = new OutboxDispatcher(outboxDispatchPort, outboxEventPayloadDeserializerPort,
            cloudEventFactory, cloudEventPublisher);

        dispatcher.dispatch(10);

        final InOrder inOrder = inOrder(outboxDispatchPort, outboxEventPayloadDeserializerPort, cloudEventFactory,
            cloudEventPublisher);

        inOrder.verify(outboxDispatchPort)
            .claimPublishableBatch(10);

        inOrder.verify(outboxEventPayloadDeserializerPort)
            .deserialize(outboxEvent.eventType(), outboxEvent.payload());

        inOrder.verify(cloudEventFactory)
            .create(event);

        inOrder.verify(cloudEventPublisher)
            .publish(cloudEvent);

    }

    @Test
    void shouldNotPublishWhenNoEventsAreAvailable() {

        when(outboxDispatchPort.claimPublishableBatch(10))
            .thenReturn(List.of());

        final OutboxDispatcher dispatcher = new OutboxDispatcher(outboxDispatchPort, outboxEventPayloadDeserializerPort,
            cloudEventFactory, cloudEventPublisher);

        dispatcher.dispatch(10);

        verify(outboxDispatchPort)
            .claimPublishableBatch(10);

        verifyNoInteractions(outboxEventPayloadDeserializerPort,
            cloudEventFactory,
            cloudEventPublisher);
    }

    @Test
    void shouldNotPublishWhenEventReconstructionFails() {

        final RuntimeException failure = new IllegalStateException("Invalid outbox payload");

        when(outboxDispatchPort.claimPublishableBatch(10))
            .thenReturn(List.of(outboxEvent));

        when(outboxEventPayloadDeserializerPort.deserialize(outboxEvent.eventType(), outboxEvent.payload()))
            .thenThrow(failure);

        final OutboxDispatcher dispatcher = new OutboxDispatcher(outboxDispatchPort,
            outboxEventPayloadDeserializerPort,
            cloudEventFactory,
            cloudEventPublisher);

        org.assertj.core.api.Assertions.assertThatThrownBy(() -> dispatcher.dispatch(10))
            .isSameAs(failure);

        verifyNoInteractions(cloudEventFactory, cloudEventPublisher);
    }

    @Test
    void shouldPropagatePublicationFailure() {
        final RuntimeException failure =
            new IllegalStateException("RabbitMQ unavailable");

        when(outboxDispatchPort.claimPublishableBatch(10))
            .thenReturn(List.of(outboxEvent));

        when(outboxEventPayloadDeserializerPort.deserialize(outboxEvent.eventType(), outboxEvent.payload()))
            .thenReturn(event);

        when(cloudEventFactory.create(event))
            .thenReturn(cloudEvent);

        org.mockito.Mockito.doThrow(failure)
            .when(cloudEventPublisher)
            .publish(cloudEvent);

        final OutboxDispatcher dispatcher = new OutboxDispatcher(outboxDispatchPort,
            outboxEventPayloadDeserializerPort,
            cloudEventFactory,
            cloudEventPublisher);

        org.assertj.core.api.Assertions.assertThatThrownBy(() -> dispatcher.dispatch(10))
            .isSameAs(failure);
    }

    @Test
    void shouldDeserializeMoneyTransferredEvent() {

        final ObjectMapper objectMapper = new ObjectMapper();
        final OutboxEventPayloadDeserializerAdapter adapter = new OutboxEventPayloadDeserializerAdapter(objectMapper);

        final String payload = "{\"transactionId\":\"" + UUID.randomUUID() + "\"}";
        final MoneyTransferredEvent result = adapter.deserialize(EventType.MONEY_TRANSFERRED, payload);

        assertThat(result).isNotNull();
    }

}
