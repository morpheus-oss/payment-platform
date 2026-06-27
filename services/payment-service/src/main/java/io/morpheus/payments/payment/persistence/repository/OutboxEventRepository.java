package io.morpheus.payments.payment.persistence.repository;

import io.morpheus.payments.payment.persistence.entity.OutboxEventEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface OutboxEventRepository extends JpaRepository<OutboxEventEntity, String> {
    @Query(
            value = """
                SELECT * FROM outbox_events
                    WHERE published = 'PENDING'
                    ORDER BY created_at
                    LIMIT :batchSize
                    FOR UPDATE SKIP LOCKED
            """,
            nativeQuery = true
    )
    List<OutboxEventEntity> lockBatch(@Param("batchSize") int batchSize);

    @Query(
            value = """
                SELECT * FROM outbox_events
                    WHERE(
                        status='PENDING'
                        OR (
                            status='FAILED'
                            AND next_retry_at <= NOW()
                        )
                    )
                    ORDER BY created_at
                    LIMIT :batchSize
                    FOR UPDATE SKIP LOCKED
            """,
            nativeQuery = true)
    List<OutboxEventEntity> lockPublishableBatch(@Param("batchSize") int batchSize);
}
