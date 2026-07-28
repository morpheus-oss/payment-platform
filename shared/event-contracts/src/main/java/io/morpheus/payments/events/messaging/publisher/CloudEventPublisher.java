package io.morpheus.payments.events.messaging.publisher;

import io.cloudevents.CloudEvent;

public interface CloudEventPublisher {

    void publish(CloudEvent event);
}
