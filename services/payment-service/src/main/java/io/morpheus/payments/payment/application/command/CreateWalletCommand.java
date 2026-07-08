package io.morpheus.payments.payment.application.command;

import java.util.Objects;

public record CreateWalletCommand(
    String customerId,
    String currency) implements Command {

    public CreateWalletCommand {
        Objects.requireNonNull(customerId);
        Objects.requireNonNull(currency);
    }
}
