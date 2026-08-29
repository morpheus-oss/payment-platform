package io.morpheus.payments.payment.application.result;

import io.morpheus.payments.events.envelope.EventType;

import java.util.UUID;

public record OutboxEvent(UUID id,
                          UUID aggregateId,
                          EventType eventType,
                          String payload) {
}
