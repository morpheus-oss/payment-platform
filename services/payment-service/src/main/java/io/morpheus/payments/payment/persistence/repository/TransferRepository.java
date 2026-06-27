package io.morpheus.payments.payment.persistence.repository;

import io.morpheus.payments.payment.persistence.entity.TransferEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface TransferRepository extends JpaRepository<TransferEntity, UUID> {
}
