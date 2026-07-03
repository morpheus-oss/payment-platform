package io.morpheus.payments.payment.messaging.publisher;

import io.cloudevents.CloudEvent;

public interface CloudEventPublisher {

  void publish(String routingKey, CloudEvent event);
}
