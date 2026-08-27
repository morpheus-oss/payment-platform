package io.morpheus.payments.payment.messaging.config;

import org.springframework.amqp.core.*;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties(MessagingProperties.class)
public class RabbitConfig {

    @Bean
    DirectExchange paymentExchange(final MessagingProperties properties) {
        return new DirectExchange(properties.getExchange());
    }

    @Bean
    Queue fraudQueue(final MessagingProperties properties) {
        return QueueBuilder.durable(properties.getFraudQueue()).build();
    }

    @Bean
    Queue notificationQueue(final MessagingProperties properties) {
        return QueueBuilder.durable(properties.getNotificationQueue()).build();
    }

    @Bean
    Binding fraudBinding(final MessagingProperties properties,
                         final TopicExchange paymentExchange,
                         final Queue fraudQueue) {
        return BindingBuilder.bind(fraudQueue)
                             .to(paymentExchange)
                             .with(properties.getRoutingKey());
    }

    @Bean
    Binding notificationBinding(final MessagingProperties properties,
                                final TopicExchange paymentExchange,
                                final Queue notificationQueue) {
        return BindingBuilder.bind(notificationQueue)
                             .to(paymentExchange)
                             .with(properties.getRoutingKey());
    }
}
