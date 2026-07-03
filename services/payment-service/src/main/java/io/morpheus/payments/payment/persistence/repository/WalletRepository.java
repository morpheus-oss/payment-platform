package io.morpheus.payments.payment.persistence.repository;

import io.morpheus.payments.payment.persistence.entity.WalletEntity;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WalletRepository extends JpaRepository<WalletEntity, UUID>
{
}
