package io.morpheus.payments.payment.persistence.repository;

import io.morpheus.payments.payment.persistence.entity.TransferEntity;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TransferRepository extends JpaRepository<TransferEntity, UUID> {}
