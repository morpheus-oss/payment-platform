package io.morpheus.payments.events.types;

import java.time.Instant;
import java.util.UUID;

public record FraudClearedEvent(
        UUID transactionId,
        Instant occurredAt
    ) { }