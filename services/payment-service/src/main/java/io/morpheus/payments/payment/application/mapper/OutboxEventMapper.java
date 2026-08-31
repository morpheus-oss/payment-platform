package io.morpheus.payments.payment.application.mapper;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.morpheus.payments.events.types.MoneyTransferredEvent;
import io.morpheus.payments.payment.application.result.OutboxEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class OutboxEventMapper {

    private final ObjectMapper objectMapper;

    public MoneyTransferredEvent toMoneyTransferredEvent(final OutboxEvent outboxEvent) {
        try {

            return objectMapper.readValue(outboxEvent.payload(), MoneyTransferredEvent.class);
        } catch (final Exception exception) {

            throw new IllegalStateException("Unable to reconstruct outbox event " + outboxEvent.id(), exception);
        }
    }

}
