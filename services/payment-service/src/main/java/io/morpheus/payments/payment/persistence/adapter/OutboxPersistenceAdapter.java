package io.morpheus.payments.payment.persistence.adapter;

import io.morpheus.payments.events.envelope.EventType;
import io.morpheus.payments.payment.application.port.out.OutboxDispatchPort;
import io.morpheus.payments.payment.application.result.OutboxEvent;
import io.morpheus.payments.payment.persistence.entity.OutboxEventEntity;
import io.morpheus.payments.payment.persistence.repository.OutboxEventRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class OutboxPersistenceAdapter implements OutboxDispatchPort {

    private final OutboxEventRepository outboxEventRepository;

    @Override
    public List<OutboxEvent> claimPublishableBatch(final int batchSize) {

        return outboxEventRepository.lockBatch(batchSize)
                                    .stream()
                                    .map(this::toApplicationEvent)
                                    .toList();
    }

    private OutboxEvent toApplicationEvent(final OutboxEventEntity entity) {

        return new OutboxEvent(entity.getId(), entity.getAggregateId(), entity.getEventType(), entity.getPayload());
    }

}
