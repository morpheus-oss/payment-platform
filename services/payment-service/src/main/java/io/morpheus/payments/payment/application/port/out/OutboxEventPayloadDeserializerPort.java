package io.morpheus.payments.payment.application.port.out;

import io.morpheus.payments.events.envelope.EventType;
import io.morpheus.payments.events.types.MoneyTransferredEvent;

public interface OutboxEventPayloadDeserializerPort extends OutPort {

    MoneyTransferredEvent deserialize(EventType eventType, String payload);
}
