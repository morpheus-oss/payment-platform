package io.morpheus.payments.payment.domain.transfer;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;

public record TransferCompleted(UUID transactionId,
                                UUID sourceWalletId,
                                UUID destinationWalletId,
                                String currencyCode,
                                BigDecimal amount,
                                Instant occurredAt) { }
