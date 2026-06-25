package io.morpheus.payments.payment.messaging.publisher;

import io.cloudevents.CloudEvent;
import io.morpheus.payments.payment.messaging.rabbitmq.RabbitConfig;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class RabbitCloudEventPublisher implements CloudEventPublisher {

    private final RabbitTemplate rabbitTemplate;

    @Override
    public void publish(String routingKey, CloudEvent event) {

        rabbitTemplate.convertAndSend(
                            RabbitConfig.EXCHANGE,
                            routingKey,
                            event);
    }
}