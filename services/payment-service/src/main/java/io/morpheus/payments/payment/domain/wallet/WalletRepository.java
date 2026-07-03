package io.morpheus.payments.payment.domain.wallet;

import java.util.Optional;

/**
 * Repository port for loading and storing {@link Wallet} aggregates.
 *
 * <p>This interface belongs to the domain because it represents a business dependency.
 * Infrastructure adapters (JPA, JDBC, etc.) will implement it.
 */
public interface WalletRepository {

  Optional<Wallet> findById(WalletId walletId);

  void save(Wallet wallet);
}
