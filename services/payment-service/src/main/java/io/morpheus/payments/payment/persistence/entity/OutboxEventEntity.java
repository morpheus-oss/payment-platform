package io.morpheus.payments.payment.persistence.entity;

import io.morpheus.payments.events.envelope.EventType;
import io.morpheus.payments.payment.domain.outbox.OutboxStatus;
import io.morpheus.payments.payment.persistence.audit.AuditableEntity;
import jakarta.persistence.*;
import java.time.Instant;
import java.util.UUID;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "outbox_events")
@Getter
@Setter
@NoArgsConstructor
public class OutboxEventEntity extends AuditableEntity  {

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

	public void incrementRetryCount()   {
		retryCount++;
	}

	public void markFailed()    {
		status = OutboxStatus.FAILED;
	}
}
