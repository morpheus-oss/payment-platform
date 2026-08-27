package io.morpheus.payments.events.cloudevents;

import io.cloudevents.CloudEvent;

public interface CloudEventPublisher {

    void publish(CloudEvent event);
}
