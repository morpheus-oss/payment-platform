package io.morpheus.payments.payment.persistence.repository;

import io.morpheus.payments.payment.persistence.entity.WalletEntity;

import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Spring Data repository for wallet persistence.
 *
 * This repository is an infrastructure component and should only be used by
 * WalletPersistenceAdapter.
 */
@Repository
public interface WalletRepository extends JpaRepository<WalletEntity, UUID> {

    WalletEntity save(WalletEntity entity);
}
