package io.morpheus.payments.payment.domain.shared;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Currency;
import java.util.Objects;

/**
 * Immutable monetary value.
 *
 * <p>Money is represented using {@link BigDecimal} to avoid precision loss.
 * The scale is normalized to the default fraction digits of the currency.
 */
public record Money(BigDecimal amount, Currency currency) implements Comparable<Money> {

    public Money {
        Objects.requireNonNull(amount, "amount must not be null");
        Objects.requireNonNull(currency, "currency must not be null");

        if (amount.scale() > currency.getDefaultFractionDigits()) {
            throw new IllegalArgumentException(
                    "Amount exceeds supported fraction digits for currency "
                            + currency.getCurrencyCode());
        }

        amount = amount.setScale(
                currency.getDefaultFractionDigits(),
                RoundingMode.UNNECESSARY);
    }

    public static Money zero(final Currency currency) {
        return new Money(BigDecimal.ZERO, currency);
    }

    public Money add(final Money other) {
        requireSameCurrency(other);
        return new Money(amount.add(other.amount), currency);
    }

    public Money subtract(final Money other) {
        requireSameCurrency(other);
        return new Money(amount.subtract(other.amount), currency);
    }

    public boolean isNegative() {
        return amount.signum() < 0;
    }

    public boolean isZero() {
        return amount.signum() == 0;
    }

    public boolean isPositive() {
        return amount.signum() > 0;
    }

    @Override
    public int compareTo(final Money other) {
        requireSameCurrency(other);
        return amount.compareTo(other.amount);
    }

    private void requireSameCurrency(final Money other) {
        Objects.requireNonNull(other, "other must not be null");

        if (!currency.equals(other.currency)) {
            throw new IllegalArgumentException(
                            "Currency mismatch: " +
                            currency.getCurrencyCode() + " vs " +
                            other.currency.getCurrencyCode());
        }
    }

    @Override
    public String toString() {
        return currency.getCurrencyCode() + " " + amount;
    }

}