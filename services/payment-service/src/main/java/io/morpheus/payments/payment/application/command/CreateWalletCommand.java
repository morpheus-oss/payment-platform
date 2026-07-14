package io.morpheus.payments.payment.application.command;

import java.math.BigDecimal;
import java.util.Currency;
import java.util.Objects;

public record CreateWalletCommand(
                    String ownerId,
                    Currency currency,
                    BigDecimal initialBalance) implements Command {

    public CreateWalletCommand {
        Objects.requireNonNull(ownerId);
        Objects.requireNonNull(currency);
        Objects.requireNonNull(initialBalance);
    }
}
