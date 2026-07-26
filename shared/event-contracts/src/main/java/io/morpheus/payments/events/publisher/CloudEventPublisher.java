package io.morpheus.payments.events.publisher;

import io.cloudevents.CloudEvent;

public interface CloudEventPublisher {

    void publish(String routingKey, CloudEvent event);
}
