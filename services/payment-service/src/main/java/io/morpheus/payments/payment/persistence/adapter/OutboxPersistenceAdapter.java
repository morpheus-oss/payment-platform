package io.morpheus.payments.payment.persistence.adapter;

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
import java.util.UUID;

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
        entities.forEach(entity -> entity.markProcessing(now));

        outboxEventRepository.saveAll(entities);

        return entities.stream()
                    .map(outboxMapper::toApplicationEvent)
                    .toList();
    }

    @Override
    @Transactional
    public void markPublished(final UUID eventId, final Instant publishedAt) {
        final OutboxEventEntity entity = findById(eventId);
        entity.markPublished(publishedAt);

        outboxEventRepository.save(entity);
    }

    @Override
    @Transactional
    public void scheduleRetry(final UUID eventId, final Instant nextRetryAt) {
        final OutboxEventEntity entity = findById(eventId);
        entity.scheduleRetry(nextRetryAt);

        outboxEventRepository.save(entity);
    }

    @Override
    @Transactional
    public void markFailed(final UUID eventId) {
        final OutboxEventEntity entity = findById(eventId);
        entity.markFailed();

        outboxEventRepository.save(entity);
    }

    private OutboxEventEntity findById(final UUID eventId) {
        return outboxEventRepository.findById(eventId)
                                    .orElseThrow(() -> new IllegalStateException("Outbox event not found: " + eventId));
    }


}
