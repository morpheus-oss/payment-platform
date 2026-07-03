package io.morpheus.payments.payment.domain.transfer;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import java.math.BigDecimal;
import java.util.UUID;

public record TransferRequest(@NotNull UUID sourceWalletId, @NotNull UUID destinationWalletId, @Positive BigDecimal amount) {
}
