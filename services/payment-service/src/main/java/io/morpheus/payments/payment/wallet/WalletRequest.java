package io.morpheus.payments.payment.wallet;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;

public record WalletRequest(
        @NotBlank String ownerId,
        @Positive BigDecimal initialBalance
) { }
