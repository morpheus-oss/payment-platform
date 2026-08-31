package io.morpheus.payments.payment.persistence.adapter;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.morpheus.payments.events.envelope.EventType;
import io.morpheus.payments.events.types.MoneyTransferredEvent;
import io.morpheus.payments.payment.application.port.out.OutboxEventPayloadDeserializerPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class OutboxEventPayloadDeserializerAdapter implements OutboxEventPayloadDeserializerPort {

    private final ObjectMapper objectMapper;

    @Override
    public MoneyTransferredEvent deserialize(final EventType eventType, final String payload) {

        if (eventType != EventType.MONEY_TRANSFERRED) {
            throw new IllegalArgumentException("Unsupported outbox event type: " + eventType);
        }

        try {

            return objectMapper.readValue(payload, MoneyTransferredEvent.class);
        } catch (final Exception exception) {

            throw new IllegalStateException("Unable to deserialize outbox event payload", exception);
        }
    }

}
