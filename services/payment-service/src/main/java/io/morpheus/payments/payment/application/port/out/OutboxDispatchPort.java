package io.morpheus.payments.payment.application.port.out;

import io.morpheus.payments.payment.application.result.OutboxEvent;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

public interface OutboxDispatchPort extends OutPort {

    List<OutboxEvent> claimPublishableBatch(int batchSize);

    void markPublished(UUID eventId, Instant publishedAt);

    void scheduleRetry(UUID eventId, Instant nextRetryAt);

    void markFailed(UUID eventId);

}
