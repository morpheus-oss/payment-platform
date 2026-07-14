package io.morpheus.payments.payment.persistence;

import io.morpheus.payments.payment.application.port.out.WalletRepositoryPort;
import io.morpheus.payments.payment.domain.wallet.Wallet;
import io.morpheus.payments.payment.mapper.WalletMapper;
import io.morpheus.payments.payment.persistence.entity.WalletEntity;
import io.morpheus.payments.payment.persistence.repository.WalletRepository;
import lombok.RequiredArgsConstructor;

import java.util.Optional;
import java.util.UUID;

@RequiredArgsConstructor
public class WalletRepositoryAdapter implements WalletRepositoryPort {

    private final WalletRepository walletRepository;

    private final WalletMapper walletMapper;

    @Override
    public Wallet save(Wallet wallet) {

        WalletEntity entity = walletMapper.toEntity(wallet);
        WalletEntity persisted = walletRepository.save(entity);

        return walletMapper.toDomain(persisted);
    }

    @Override
    public Optional<Wallet> findById(UUID walletId) {

        return walletRepository.findById(walletId)
                               .map(walletMapper::toDomain);
    }

    @Override
    public boolean existsById(UUID walletId) {

        return walletRepository.existsById(walletId);
    }
}
