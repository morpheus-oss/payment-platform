package io.morpheus.payments.events.cloudevents;

import io.cloudevents.CloudEvent;
import io.morpheus.payments.events.types.MoneyTransferredEvent;

public interface CloudEventFactory {

    CloudEvent create(MoneyTransferredEvent event);

}
