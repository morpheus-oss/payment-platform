package io.morpheus.payments.payment.messaging.rabbitmq;

import org.springframework.amqp.core.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitConfig {

    public static final String EXCHANGE = "payment.exchange";

    public static final String PAYMENT_TRANSFERRED = "payment.transferred";
    public static final String FRAUD_QUEUE = "payment.fraud.queue";
    public static final String NOTIFICATION_QUEUE = "payment.notification.queue";

    @Bean
    TopicExchange paymentExchange() {
        return new TopicExchange(EXCHANGE);
    }

    @Bean
    Queue fraudQueue() {
        return QueueBuilder
                    .durable(FRAUD_QUEUE)
                    .build();
    }

    @Bean
    Queue notificationQueue() {
        return QueueBuilder
                    .durable(NOTIFICATION_QUEUE)
                    .build();
    }

    @Bean
    Binding fraudBinding() {

        return BindingBuilder
                    .bind(fraudQueue())
                    .to(paymentExchange())
                    .with(PAYMENT_TRANSFERRED);
    }

    @Bean
    Binding notificationBinding() {

        return BindingBuilder
                    .bind(notificationQueue())
                    .to(paymentExchange())
                    .with(PAYMENT_TRANSFERRED);
    }
}