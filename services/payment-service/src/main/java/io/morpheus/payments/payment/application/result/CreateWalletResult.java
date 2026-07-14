package io.morpheus.payments.payment.application.result;

import java.util.Objects;
import java.util.UUID;

public record CreateWalletResult(
                    UUID walletId,
                    String ownerId,
                    String currency,
                    double balance) implements Result {

    public CreateWalletResult {
        Objects.requireNonNull(walletId);
        Objects.requireNonNull(ownerId);
        Objects.requireNonNull(currency);
    }
}
