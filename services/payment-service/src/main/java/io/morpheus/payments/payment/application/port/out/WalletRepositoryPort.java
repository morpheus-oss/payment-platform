package io.morpheus.payments.payment.application.port.out;

import io.morpheus.payments.payment.domain.wallet.Wallet;

import java.util.Optional;
import java.util.UUID;

public interface WalletRepositoryPort extends OutPort   {

    Wallet save(Wallet wallet);

    Optional<Wallet> findById(UUID walletId);

    boolean existsById(UUID walletId);
}
