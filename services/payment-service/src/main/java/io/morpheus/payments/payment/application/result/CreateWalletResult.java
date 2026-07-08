package io.morpheus.payments.payment.application.result;

import java.util.Objects;

public record CreateWalletResult(String walletId) implements Result {

    public CreateWalletResult {
        Objects.requireNonNull(walletId);
    }
}
