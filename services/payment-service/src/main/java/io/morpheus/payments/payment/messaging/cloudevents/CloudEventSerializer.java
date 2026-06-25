package io.morpheus.payments.payment.messaging.cloudevents;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.cloudevents.CloudEvent;
import io.cloudevents.core.provider.EventFormatProvider;
import org.springframework.stereotype.Component;

@Component
public class CloudEventSerializer {

    public byte[] serialize(CloudEvent cloudEvent) {

        return EventFormatProvider.getInstance()
                                .resolveFormat(JsonFormat.CONTENT_TYPE)
                                .serialize(cloudEvent);
    }
}