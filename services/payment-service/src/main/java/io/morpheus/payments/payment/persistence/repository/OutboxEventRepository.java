package io.morpheus.payments.payment.persistence.repository;

import io.morpheus.payments.payment.persistence.entity.OutboxEventEntity;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface OutboxEventRepository extends JpaRepository<OutboxEventEntity, UUID>
{
	@Query(value = """
			    SELECT * FROM outbox_events
			        WHERE status = 'PENDING'
			        ORDER BY created_at
			        LIMIT :batchSize
			        FOR UPDATE SKIP LOCKED
			""", nativeQuery = true)
	List<OutboxEventEntity> lockBatch(@Param("batchSize") int batchSize);

    @Query(value = """
            SELECT *
            FROM outbox_events
            WHERE status = 'PENDING'
              AND next_retry_at <= :now
            ORDER BY created_at
            LIMIT :batchSize
            FOR UPDATE SKIP LOCKED
            """, nativeQuery = true)
    List<OutboxEventEntity> lockPublishableBatch(@Param("batchSize") int batchSize, @Param("now") Instant now);


}
