package io.morpheus.payments.payment.messaging.publisher;

import io.cloudevents.CloudEvent;
import io.morpheus.payments.events.cloudevents.CloudEventSerializer;
import io.morpheus.payments.events.cloudevents.CloudEventPublisher;
import io.morpheus.payments.payment.messaging.config.MessagingProperties;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class RabbitCloudEventPublisher implements CloudEventPublisher {

    private final RabbitTemplate rabbitTemplate;

    private final CloudEventSerializer cloudEventSerializer;

    private final MessagingProperties messagingProperties;

    @Override
    public void publish(final CloudEvent cloudEvent) {
        byte[] payload = cloudEventSerializer.serialize(cloudEvent);

        rabbitTemplate.convertAndSend(messagingProperties.getExchange(),
                                      messagingProperties.getRoutingKey(),
                                      payload);
    }

}
