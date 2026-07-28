package io.morpheus.payments.events.messaging.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Getter
@Setter
@ConfigurationProperties(prefix = "payment.messaging")
public class MessagingProperties {

    private String exchange;

    private String fraudQueue;

    private String notificationQueue;

    private String routingKey;

}
