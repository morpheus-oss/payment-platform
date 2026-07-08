package io.morpheus.payments.payment.persistence;

import io.morpheus.payments.payment.application.port.out.WalletRepositoryPort;
import io.morpheus.payments.payment.domain.wallet.Wallet;
import io.morpheus.payments.payment.persistence.repository.WalletRepository;

import java.util.Optional;

public class WalletRepositoryAdapter implements WalletRepositoryPort {

    private final WalletRepository repository;

    public WalletRepositoryAdapter(WalletRepository repository) {
        this.repository = repository;
    }

    @Override
    public Wallet save(Wallet wallet) {
        return repository.save(wallet);
    }

    @Override
    public Optional<Wallet> findById(String walletId) {
        return repository.findById(walletId);
    }

    @Override
    public boolean existsById(String walletId) {
        return repository.existsById(walletId);
    }
}
