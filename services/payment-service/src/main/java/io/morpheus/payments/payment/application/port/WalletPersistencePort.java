package io.morpheus.payments.payment.application.port;

import io.morpheus.payments.payment.domain.wallet.Wallet;
import java.util.Optional;
import java.util.UUID;

public interface WalletPersistencePort {

  Optional<Wallet> findById(UUID walletId);

  Wallet save(Wallet wallet);
}
