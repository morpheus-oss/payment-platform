package io.morpheus.payments.payment.persistence.adapter;

import io.morpheus.payments.events.envelope.EventType;
import io.morpheus.payments.payment.application.result.OutboxEvent;
import io.morpheus.payments.payment.domain.outbox.OutboxStatus;
import io.morpheus.payments.payment.persistence.entity.OutboxEventEntity;
import io.morpheus.payments.payment.persistence.mapper.OutboxMapper;
import io.morpheus.payments.payment.persistence.repository.OutboxEventRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class OutboxPersistenceAdapterTest {

    @Mock
    private OutboxEventRepository outboxEventRepository;

    @Mock
    private OutboxMapper outboxMapper;

    @Test
    void shouldClaimAndMapPublishableOutboxEvents() {
        final UUID id = UUID.randomUUID();
        final UUID aggregateId = UUID.randomUUID();

        final OutboxEventEntity entity = new OutboxEventEntity();
        entity.setId(id);
        entity.setAggregateId(aggregateId);
        entity.setEventType(EventType.MONEY_TRANSFERRED);
        entity.setPayload("{\"transactionId\":\"" + aggregateId + "\"}");

        when(outboxEventRepository.lockBatch(10)).thenReturn(List.of(entity));

        final OutboxPersistenceAdapter adapter = new OutboxPersistenceAdapter(outboxEventRepository, outboxMapper);
        final List<OutboxEvent> result = adapter.claimPublishableBatch(10);

        assertThat(result).hasSize(1);

        assertThat(result.getFirst().id()).isEqualTo(id);

        assertThat(result.getFirst().aggregateId()).isEqualTo(aggregateId);

        assertThat(result.getFirst().eventType()).isEqualTo(EventType.MONEY_TRANSFERRED);

        assertThat(result.getFirst().payload()).isEqualTo(entity.getPayload());

        when(outboxEventRepository.lockPublishableBatch(eq(10), any(Instant.class)))
            .thenReturn(List.of(entity));
    }

    @Test
    void shouldReturnEmptyListWhenNoPublishableEventsExist() {

        when(outboxEventRepository.lockBatch(10)).thenReturn(List.of());

        final OutboxPersistenceAdapter adapter = new OutboxPersistenceAdapter(outboxEventRepository, outboxMapper);
        final List<OutboxEvent> result = adapter.claimPublishableBatch(10);

        assertThat(result).isEmpty();

        verify(outboxEventRepository).lockBatch(10);
    }

    @Test
    void shouldMarkEventAsPublished() {
        final UUID eventId = UUID.randomUUID();
        final OutboxEventEntity entity = processingEvent(eventId);

        when(outboxEventRepository.findById(eventId))
            .thenReturn(Optional.of(entity));

        final Instant publishedAt = Instant.now();

        final OutboxPersistenceAdapter adapter = new OutboxPersistenceAdapter(outboxEventRepository, outboxMapper);
        adapter.markPublished(eventId, publishedAt);

        assertThat(entity.getStatus())
            .isEqualTo(OutboxStatus.PUBLISHED);

        assertThat(entity.getUpdatedAt())
            .isEqualTo(publishedAt);

        verify(outboxEventRepository).save(entity);
    }

    @Test
    void shouldScheduleRetry() {
        final UUID eventId = UUID.randomUUID();
        final OutboxEventEntity entity = processingEvent(eventId);
        entity.setRetryCount(1);

        when(outboxEventRepository.findById(eventId))
            .thenReturn(Optional.of(entity));

        final Instant nextRetryAt =
            Instant.now().plusSeconds(60);

        final OutboxPersistenceAdapter adapter = new OutboxPersistenceAdapter(outboxEventRepository, outboxMapper);
        adapter.scheduleRetry(eventId, nextRetryAt);

        assertThat(entity.getStatus())
            .isEqualTo(OutboxStatus.PENDING);

        assertThat(entity.getRetryCount())
            .isEqualTo(2);

        assertThat(entity.getNextRetryAt())
            .isEqualTo(nextRetryAt);

        verify(outboxEventRepository).save(entity);
    }

    @Test
    void shouldMarkEventAsFailed() {
        final UUID eventId = UUID.randomUUID();
        final OutboxEventEntity entity = processingEvent(eventId);

        when(outboxEventRepository.findById(eventId))
            .thenReturn(Optional.of(entity));

        final OutboxPersistenceAdapter adapter = new OutboxPersistenceAdapter(outboxEventRepository, outboxMapper);
        adapter.markFailed(eventId);

        assertThat(entity.getStatus())
            .isEqualTo(OutboxStatus.FAILED);

        verify(outboxEventRepository).save(entity);
    }

    @Test
    void shouldFailWhenEventDoesNotExist() {
        final UUID eventId = UUID.randomUUID();

        when(outboxEventRepository.findById(eventId))
            .thenReturn(Optional.empty());

        final OutboxPersistenceAdapter adapter = new OutboxPersistenceAdapter(outboxEventRepository, outboxMapper);
        assertThatThrownBy(
            () -> adapter.markPublished(
                eventId,
                Instant.now()))
            .isInstanceOf(IllegalStateException.class);
    }

    private OutboxEventEntity processingEvent(UUID eventId) {
        final UUID id = UUID.randomUUID();
        final UUID aggregateId = UUID.randomUUID();

        final OutboxEventEntity entity = new OutboxEventEntity();
        entity.setId(id);
        entity.setAggregateId(aggregateId);
        entity.setEventType(EventType.MONEY_TRANSFERRED);
        entity.setPayload("{}");
        entity.setRetryCount(2);
        entity.setStatus(OutboxStatus.PROCESSING);

        return entity;
    }

}
