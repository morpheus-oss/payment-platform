package io.morpheus.payments.payment.persistence.entity;

import io.morpheus.payments.events.envelope.EventType;
import io.morpheus.payments.payment.domain.outbox.OutboxStatus;
import org.junit.jupiter.api.Test;

import java.time.Instant;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

public class OutboxEventEntityTest {
    @Test
    void shouldTransitionFromPendingToProcessing() {
        final OutboxEventEntity entity = pendingEvent();
        final Instant startedAt = Instant.now();

        entity.markProcessing(startedAt);

        assertThat(entity.getStatus())
            .isEqualTo(OutboxStatus.PROCESSING);
    }

    @Test
    void shouldMarkEventAsPublished() {
        final OutboxEventEntity entity = processingEvent();
        final Instant publishedAt = Instant.now();

        entity.markPublished(publishedAt);

        assertThat(entity.getStatus())
            .isEqualTo(OutboxStatus.PUBLISHED);

        assertThat(entity.getUpdatedAt())
            .isEqualTo(publishedAt);
    }

    @Test
    void shouldScheduleRetry() {
        final Instant nextRetryAt = Instant.now().plusSeconds(60);

        final OutboxEventEntity entity = processingEvent();
        entity.setRetryCount(1);
        entity.scheduleRetry(nextRetryAt);

        assertThat(entity.getStatus())
            .isEqualTo(OutboxStatus.PENDING);

        assertThat(entity.getRetryCount())
            .isEqualTo(2);

        assertThat(entity.getNextRetryAt())
            .isEqualTo(nextRetryAt);
    }

    @Test
    void shouldMarkEventAsFailed() {
        final OutboxEventEntity entity = processingEvent();

        entity.markFailed();

        assertThat(entity.getStatus())
            .isEqualTo(OutboxStatus.FAILED);
    }

    private OutboxEventEntity pendingEvent() {
        final UUID id = UUID.randomUUID();
        final UUID aggregateId = UUID.randomUUID();

        final OutboxEventEntity entity = new OutboxEventEntity();
        entity.setId(id);
        entity.setAggregateId(aggregateId);
        entity.setEventType(EventType.MONEY_TRANSFERRED);
        entity.setPayload("{}");
        entity.setRetryCount(2);
        entity.setStatus(OutboxStatus.PENDING);

        return entity;
    }

    private OutboxEventEntity processingEvent() {
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
