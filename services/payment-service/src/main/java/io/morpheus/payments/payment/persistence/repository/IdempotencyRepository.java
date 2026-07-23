package io.morpheus.payments.payment.persistence.repository;

import io.morpheus.payments.payment.persistence.entity.IdempotencyEntity;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IdempotencyRepository extends JpaRepository<IdempotencyEntity, UUID> {

	Optional<IdempotencyEntity> findById(String idempotencyKey);

}
