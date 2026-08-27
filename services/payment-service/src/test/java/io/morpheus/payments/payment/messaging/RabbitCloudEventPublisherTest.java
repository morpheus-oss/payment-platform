package io.morpheus.payments.payment.messaging;

import io.cloudevents.CloudEvent;
import io.morpheus.payments.events.cloudevents.CloudEventSerializer;
import io.morpheus.payments.payment.messaging.config.MessagingProperties;
import io.morpheus.payments.payment.messaging.publisher.RabbitCloudEventPublisher;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.amqp.rabbit.core.RabbitTemplate;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class RabbitCloudEventPublisherTest {

    private static final String EXCHANGE = "payment.exchange";

    private static final String ROUTING_KEY = "payment.transfer.completed";

    private static final byte[] SERIALIZED_PAYLOAD = "{\"specversion\":\"1.0\"}".getBytes();

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
        when(messagingProperties.getExchange())
            .thenReturn(EXCHANGE);

        when(messagingProperties.getRoutingKey())
            .thenReturn(ROUTING_KEY);

        when(cloudEventSerializer.serialize(cloudEvent))
            .thenReturn(SERIALIZED_PAYLOAD);

        publisher = new RabbitCloudEventPublisher(rabbitTemplate,
                                                  cloudEventSerializer,
                                                  messagingProperties);
    }

    @Test
    void shouldSerializeCloudEventAndPublishSerializedPayload() {
        publisher.publish(cloudEvent);

        verify(cloudEventSerializer).serialize(cloudEvent);

        verify(rabbitTemplate).convertAndSend(EXCHANGE,
                                            ROUTING_KEY,
                                            SERIALIZED_PAYLOAD);
    }

    @Test
    void shouldPublishUsingConfiguredExchangeAndRoutingKey()
    {
        publisher.publish(cloudEvent);

        verify(rabbitTemplate).convertAndSend(EXCHANGE,
                                            ROUTING_KEY,
                                            SERIALIZED_PAYLOAD);
    }

    @Test
    void shouldSerializeCloudEventBeforePublishing()
    {
        publisher.publish(cloudEvent);

        verify(cloudEventSerializer).serialize(cloudEvent);

        verify(rabbitTemplate).convertAndSend(EXCHANGE,
                                            ROUTING_KEY,
                                            SERIALIZED_PAYLOAD);
    }

}
