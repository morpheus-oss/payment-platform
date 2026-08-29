package io.morpheus.payments.payment.messaging.publisher;

import io.cloudevents.CloudEvent;
import io.morpheus.payments.events.cloudevents.CloudEventSerializer;
import io.morpheus.payments.payment.messaging.config.MessagingProperties;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InOrder;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.amqp.rabbit.core.RabbitTemplate;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class RabbitCloudEventPublisherTest {

    private static final String EXCHANGE = "payment.exchange";

    private static final String ROUTING_KEY = "MoneyTransferred";

    private static final byte[] SERIALIZED_PAYLOAD = "serialized-cloud-event".getBytes();

    @Mock
    private RabbitTemplate rabbitTemplate;

    @Mock
    private CloudEventSerializer cloudEventSerializer;

    @Mock
    private MessagingProperties messagingProperties;

    @Mock
    private CloudEvent cloudEvent;

    private RabbitCloudEventPublisher publisher;

    @BeforeEach
    void setUp() {
        publisher = new RabbitCloudEventPublisher(rabbitTemplate,
                                                cloudEventSerializer,
                                                messagingProperties);

        when(messagingProperties.getExchange())
            .thenReturn(EXCHANGE);

        when(messagingProperties.getRoutingKey())
            .thenReturn(ROUTING_KEY);

        when(cloudEventSerializer.serialize(cloudEvent))
            .thenReturn(SERIALIZED_PAYLOAD);
    }

    @Test
    void shouldSerializeCloudEventBeforePublishing() {
        publisher.publish(cloudEvent);

        final InOrder inOrder = org.mockito.Mockito.inOrder(cloudEventSerializer, rabbitTemplate);

        inOrder.verify(cloudEventSerializer)
            .serialize(cloudEvent);

        inOrder.verify(rabbitTemplate)
            .convertAndSend(EXCHANGE,
                            ROUTING_KEY,
                            SERIALIZED_PAYLOAD);
    }

    @Test
    void shouldPublishUsingConfiguredExchangeAndRoutingKey() {
        publisher.publish(cloudEvent);

        verify(rabbitTemplate)
            .convertAndSend(EXCHANGE,
                            ROUTING_KEY,
                            SERIALIZED_PAYLOAD);
    }

    @Test
    void shouldPublishSerializedPayloadRatherThanCloudEvent() {
        publisher.publish(cloudEvent);

        verify(rabbitTemplate)
            .convertAndSend(EXCHANGE,
                            ROUTING_KEY,
                            SERIALIZED_PAYLOAD);
    }

}
