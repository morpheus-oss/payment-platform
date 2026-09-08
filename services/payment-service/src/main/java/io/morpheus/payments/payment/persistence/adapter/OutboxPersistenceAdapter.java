package io.morpheus.payments.payment.persistence.adapter;

import io.morpheus.payments.events.envelope.EventType;
import io.morpheus.payments.payment.application.port.out.OutboxDispatchPort;
import io.morpheus.payments.payment.application.result.OutboxEvent;
import io.morpheus.payments.payment.persistence.entity.OutboxEventEntity;
import io.morpheus.payments.payment.persistence.mapper.OutboxMapper;
import io.morpheus.payments.payment.persistence.repository.OutboxEventRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.List;

@Component
@RequiredArgsConstructor
public class OutboxPersistenceAdapter implements OutboxDispatchPort {

    private final OutboxEventRepository outboxEventRepository;
    private final OutboxMapper outboxMapper;

    @Override
    @Transactional
    public List<OutboxEvent> claimPublishableBatch(final int batchSize) {

        final Instant now = Instant.now();
        final List<OutboxEventEntity> entities = outboxEventRepository.lockPublishableBatch(batchSize, now);

        entities.forEach(OutboxEventEntity::markProcessing);

        outboxEventRepository.saveAll(entities);

        return entities.stream()
                    .map(outboxMapper::toApplicationEvent)
                    .toList();
    }

}
