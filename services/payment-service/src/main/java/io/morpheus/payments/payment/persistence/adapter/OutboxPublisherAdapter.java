package io.morpheus.payments.payment.persistence.adapter;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.morpheus.payments.events.envelope.EventTypes;
import io.morpheus.payments.events.types.MoneyTransferredEvent;
import io.morpheus.payments.payment.application.port.out.OutboxPublisherPort;
import io.morpheus.payments.payment.domain.outbox.OutboxStatus;
import io.morpheus.payments.payment.domain.transfer.TransferCompleted;
import io.morpheus.payments.payment.persistence.entity.OutboxEventEntity;
import io.morpheus.payments.payment.persistence.repository.OutboxEventRepository;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class OutboxPublisherAdapter implements OutboxPublisherPort  {

    private final OutboxEventRepository outboxEventRepository;

    private final ObjectMapper objectMapper;

    @Override
    public void publish(final TransferCompleted event)  {
        try {
            MoneyTransferredEvent moneyTransferredEvent = new MoneyTransferredEvent(event.transactionId(),
                                                                                    event.sourceWalletId(),
                                                                                    event.destinationWalletId(),
                                                                                    event.currencyCode(),
                                                                                    event.amount(),
                                                                                    event.occurredAt());

            String payload = objectMapper.writeValueAsString(moneyTransferredEvent);

            OutboxEventEntity outbox = new OutboxEventEntity();
            outbox.setId(UUID.randomUUID());
            outbox.setAggregateId(event.transactionId());
            outbox.setEventType(EventTypes.MONEY_TRANSFERRED);
            outbox.setPayload(payload);
            outbox.setStatus(OutboxStatus.PENDING);
            outbox.setRetryCount(0);

            outboxEventRepository.save(outbox);
        }
        catch (JsonProcessingException e)   {
            throw new IllegalStateException("Failed to serialize money transferred event.", e);
        }
    }

}
