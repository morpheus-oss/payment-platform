package io.morpheus.payments.events.types;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;

public record MoneyTransferredEvent(
        UUID transactionId,
        UUID sourceWalletId,
        UUID destinationWalletId,
        BigDecimal amount,
        Instant occurredAt
    ) { }