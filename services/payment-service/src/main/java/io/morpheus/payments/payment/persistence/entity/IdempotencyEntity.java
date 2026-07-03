package io.morpheus.payments.payment.persistence.entity;

import io.morpheus.payments.payment.persistence.audit.AuditableEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import java.util.UUID;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "idempotency_keys", uniqueConstraints = { @UniqueConstraint(columnNames = "idempotencyKey") })
@Getter
@Setter
@NoArgsConstructor
public class IdempotencyEntity extends AuditableEntity
{

	@Id
	private UUID id;

	private String idempotencyKey;

	private UUID transferId;
}
