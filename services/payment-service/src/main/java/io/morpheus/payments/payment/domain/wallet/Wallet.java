package io.morpheus.payments.payment.domain.wallet;

import io.morpheus.payments.payment.domain.shared.Money;
import io.morpheus.payments.payment.exception.domain.InsufficientFundsException;
import jakarta.validation.constraints.Positive;
import java.math.BigDecimal;
import java.util.Objects;

/**
 * Aggregate root representing a payment wallet.
 *
 * <p>The wallet owns its balance and enforces all business rules related to monetary updates.
 */
public final class Wallet {

  private final WalletId id;

  private Money balance;

  private Wallet(final WalletId id, final Money openingBalance) {
    this.id = Objects.requireNonNull(id, "id must not be null");
    this.balance = Objects.requireNonNull(openingBalance, "openingBalance must not be null");
  }

  public static Wallet from(final WalletId id, final Money openingBalance) {
    return new Wallet(id, openingBalance);
  }

  public WalletId id() {
    return id;
  }

  public Money balance() {
    return balance;
  }

  public void deposit(final @Positive Money amount) {
    balance = balance.add(amount);
  }

  public void withdraw(final @Positive BigDecimal amount) {

    if (balance.compareTo(amount) < 0) {
      throw new InsufficientFundsException();
    }

    balance = balance.subtract(amount);
  }
}
