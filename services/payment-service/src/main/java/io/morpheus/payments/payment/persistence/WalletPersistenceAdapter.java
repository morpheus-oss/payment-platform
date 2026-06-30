package io.morpheus.payments.payment.persistence;

import io.morpheus.payments.payment.application.port.WalletPersistencePort;
import io.morpheus.payments.payment.domain.wallet.WalletRepository;
import io.morpheus.payments.payment.persistence.entity.WalletEntity;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.UUID;

@Component
public class WalletPersistenceAdapter implements WalletPersistencePort {

    private final WalletRepository walletRepository;

    public WalletPersistenceAdapter(final WalletRepository walletRepository) {
        this.walletRepository = walletRepository;
    }

    @Override
    public Optional<WalletEntity> findById(final UUID walletId) {
        return walletRepository.findById(walletId);
    }

    @Override
    public WalletEntity save(final WalletEntity wallet) {
        return walletRepository.save(wallet);
    }
}