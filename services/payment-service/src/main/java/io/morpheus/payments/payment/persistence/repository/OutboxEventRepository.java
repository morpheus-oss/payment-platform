package io.morpheus.payments.payment.persistence.repository;

import io.morpheus.payments.payment.persistence.entity.OutboxEventEntity;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface OutboxEventRepository extends JpaRepository<OutboxEventEntity, String>
{
	@Query(value = """
			    SELECT * FROM outbox_events
			        WHERE status = 'PENDING'
			        ORDER BY created_at
			        LIMIT :batchSize
			        FOR UPDATE SKIP LOCKED
			""", nativeQuery = true)
	List<OutboxEventEntity> lockBatch(@Param("batchSize") int batchSize);

}
