package io.morpheus.payments.payment.persistence.mapper;

import io.morpheus.payments.events.envelope.EventType;
import io.morpheus.payments.events.types.MoneyTransferredEvent;
import io.morpheus.payments.payment.domain.outbox.OutboxStatus;
import io.morpheus.payments.payment.domain.transfer.TransferCompleted;
import io.morpheus.payments.payment.persistence.entity.OutboxEventEntity;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.UUID;

@Component
public class OutboxMapper   {

    public MoneyTransferredEvent toIntegrationEvent(final TransferCompleted event) {
        return new MoneyTransferredEvent(event.transactionId(),
                                        event.sourceWalletId(),
                                        event.destinationWalletId(),
                                        event.currencyCode(),
                                        event.amount(),
                                        event.occurredAt());
    }

    public OutboxEventEntity toEntity(final UUID eventId,
                                      final String payload,
                                      final TransferCompleted event) {

        OutboxEventEntity entity = new OutboxEventEntity();

        entity.setId(eventId);
        entity.setAggregateId(event.transactionId());
        entity.setEventType(EventType.MONEY_TRANSFERRED);
        entity.setPayload(payload);
        entity.setStatus(OutboxStatus.PENDING);
        entity.setCreatedAt(Instant.now());

        return entity;
    }

}
