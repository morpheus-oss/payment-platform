package io.morpheus.payments.payment.persistence.repository;

import io.morpheus.payments.payment.persistence.entity.IdempotencyEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface IdempotencyRepository extends JpaRepository<IdempotencyEntity, UUID> {

    Optional<IdempotencyEntity> findByIdempotencyKey(String idempotencyKey);
}
