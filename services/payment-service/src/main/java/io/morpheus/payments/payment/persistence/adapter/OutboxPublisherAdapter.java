package io.morpheus.payments.payment.persistence.adapter;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.morpheus.payments.events.envelope.EventType;
import io.morpheus.payments.events.types.MoneyTransferredEvent;
import io.morpheus.payments.payment.application.port.out.OutboxPublisherPort;
import io.morpheus.payments.payment.domain.outbox.OutboxStatus;
import io.morpheus.payments.payment.domain.transfer.TransferCompleted;
import io.morpheus.payments.payment.persistence.entity.OutboxEventEntity;
import io.morpheus.payments.payment.persistence.mapper.OutboxMapper;
import io.morpheus.payments.payment.persistence.repository.OutboxEventRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
@RequiredArgsConstructor
public class OutboxPublisherAdapter implements OutboxPublisherPort  {

    private final OutboxEventRepository outboxEventRepository;
    private final ObjectMapper objectMapper;
    private final OutboxMapper outboxMapper;

    @Override
    public void publish(final TransferCompleted event)  {
        try {
            MoneyTransferredEvent moneyTransferredEvent = outboxMapper.toIntegrationEvent(event);
            String payload = objectMapper.writeValueAsString(moneyTransferredEvent);

            OutboxEventEntity outbox = outboxMapper.toEntity(UUID.randomUUID(), payload, event);

            outboxEventRepository.save(outbox);
        }
        catch (JsonProcessingException e)   {
            throw new IllegalStateException("Failed to serialize money transferred event.", e);
        }
    }

}
