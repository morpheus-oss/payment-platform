package io.morpheus.payments.events.cloudevents;

import io.cloudevents.CloudEvent;
import io.cloudevents.core.provider.EventFormatProvider;
import io.cloudevents.jackson.JsonFormat;
import org.springframework.stereotype.Component;

@Component
public class CloudEventSerializer {

    public byte[] serialize(CloudEvent cloudEvent) {
        return EventFormatProvider.getInstance().resolveFormat(JsonFormat.CONTENT_TYPE).serialize(cloudEvent);
    }
}
