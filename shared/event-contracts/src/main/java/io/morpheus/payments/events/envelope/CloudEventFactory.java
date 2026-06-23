package io.morpheus.payments.events.envelope;

import io.cloudevents.CloudEvent;
import io.cloudevents.core.builder.CloudEventBuilder;

import java.net.URI;
import java.time.OffsetDateTime;
import java.util.UUID;

public final class CloudEventFactory {

    private CloudEventFactory() {
    }

    public static CloudEvent create(String source, String type, byte[] payload) {

        return CloudEventBuilder.v1()
                                .withId(UUID.randomUUID().toString())
                                .withType(type)
                                .withSource(URI.create(source))
                                .withTime(OffsetDateTime.now())
                                .withData("application/json", payload)
                                .build();
    }
}