package io.morpheus.payments.payment.messaging;

import io.morpheus.payments.events.cloudevents.CloudEventPublisher;
import io.morpheus.payments.events.cloudevents.CloudEventSerializer;
import io.morpheus.payments.payment.messaging.config.MessagingProperties;
import io.morpheus.payments.payment.messaging.publisher.RabbitCloudEventPublisher;
import org.junit.jupiter.api.Test;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class MessagingConfigurationTest {

    @Autowired
    private CloudEventPublisher cloudEventPublisher;

    @Autowired
    private RabbitCloudEventPublisher rabbitCloudEventPublisher;

    @Autowired
    private CloudEventSerializer cloudEventSerializer;

    @Autowired
    private MessagingProperties messagingProperties;

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Test
    void shouldWireMessagingInfrastructure() {

        assertThat(cloudEventPublisher).isSameAs(rabbitCloudEventPublisher);

        assertThat(cloudEventSerializer).isNotNull();

        assertThat(messagingProperties).isNotNull();

        assertThat(rabbitTemplate).isNotNull();
    }

    @Test
    void shouldLoadConfiguredMessagingProperties() {

        assertThat(messagingProperties.getExchange()).isEqualTo("payment.exchange");

        assertThat(messagingProperties.getRoutingKey()).isEqualTo("MoneyTransferred");
    }

}
