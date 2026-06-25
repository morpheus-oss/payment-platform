package io.morpheus.payments.payment.persistence.repository;

import io.morpheus.payments.payment.persistence.entity.WalletEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface WalletRepository extends JpaRepository<WalletEntity, UUID> {
}
