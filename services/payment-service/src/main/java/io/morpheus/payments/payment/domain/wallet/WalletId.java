package io.morpheus.payments.payment.domain.wallet;

import java.util.Objects;
import java.util.UUID;

/**
 * Strongly typed identifier for a Wallet aggregate.
 *
 * <p>Using a dedicated value object prevents accidental mixing of identifiers belonging to
 * different aggregates and provides a single place for validation.
 */
public record WalletId(UUID value) {

  public WalletId {
    Objects.requireNonNull(value, "value must not be null");
  }

  public static WalletId generate() {
    return new WalletId(UUID.randomUUID());
  }

  public static WalletId from(final UUID value) {
    return new WalletId(value);
  }

  @Override
  public String toString() {
    return value.toString();
  }
}
