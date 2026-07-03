package io.morpheus.payments.payment.domain.transfer;

import io.morpheus.payments.payment.domain.shared.Money;
import io.morpheus.payments.payment.domain.wallet.WalletId;
import java.util.Objects;

/**
 * Immutable command describing a money transfer request.
 *
 * <p>This command represents business intent and is independent of REST, messaging, persistence or
 * any transport mechanism.
 */
public record TransferCommand(WalletId sourceWalletId, WalletId destinationWalletId, Money amount) {

  public TransferCommand {

    Objects.requireNonNull(sourceWalletId, "sourceWalletId must not be null");
    Objects.requireNonNull(destinationWalletId, "destinationWalletId must not be null");
    Objects.requireNonNull(amount, "amount must not be null");

    if (sourceWalletId.equals(destinationWalletId)) {
      throw new IllegalArgumentException("Source and destination wallets must be different.");
    }

    if (!amount.isPositive()) {
      throw new IllegalArgumentException("Transfer amount must be greater than zero.");
    }
  }
}
