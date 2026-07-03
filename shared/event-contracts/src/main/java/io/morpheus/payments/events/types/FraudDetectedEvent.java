package io.morpheus.payments.events.types;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

public record FraudDetectedEvent(UUID transactionId, Integer riskScore, List<String> triggeredRules, Instant occurredAt) {
}
