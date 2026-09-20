package io.morpheus.payments.payment.persistence.entity;

import io.morpheus.payments.events.envelope.EventType;
import io.morpheus.payments.payment.domain.outbox.OutboxStatus;
import io.morpheus.payments.payment.persistence.audit.AuditableEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.Instant;
import java.util.UUID;

@Entity
@Table(name = "outbox_events")
@Getter
@Setter
@NoArgsConstructor
public class OutboxEventEntity extends AuditableEntity {

    @Id
    private UUID id;

    private UUID aggregateId;

    @Enumerated(EnumType.STRING)
    private EventType eventType;

    @Lob
    private String payload;

    @Enumerated(EnumType.STRING)
    private OutboxStatus status;

    private Integer retryCount;

    private Instant nextRetryAt;

    private Instant processingStartedAt;

    public void markFailed() {
        status = OutboxStatus.FAILED;
        this.processingStartedAt = null;
    }

    public void markProcessing(final Instant processingStartedAt) {
        this.processingStartedAt = processingStartedAt;
        status = OutboxStatus.PROCESSING;
    }

    public void markPublished(final Instant publishedAt) {
        status = OutboxStatus.PUBLISHED;
        this.processingStartedAt = null;
        this.setUpdatedAt(publishedAt);
    }

    public void scheduleRetry(final Instant nextRetryAt) {
        retryCount++;
        this.nextRetryAt = nextRetryAt;
        this.processingStartedAt = null;
        status = OutboxStatus.PENDING;
    }
}
