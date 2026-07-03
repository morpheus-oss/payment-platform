package io.morpheus.payments.payment.messaging.cloudevents;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.cloudevents.CloudEvent;
import io.cloudevents.core.builder.CloudEventBuilder;
import io.morpheus.payments.events.envelope.EventSources;
import io.morpheus.payments.events.envelope.EventTypes;
import io.morpheus.payments.events.types.MoneyTransferredEvent;
import java.net.URI;
import java.time.OffsetDateTime;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CloudEventMapper
{

	private final ObjectMapper objectMapper;

	public CloudEvent map(MoneyTransferredEvent event)
	{

		try
		{
			byte[] payload = objectMapper.writeValueAsBytes(event);

			return CloudEventBuilder.v1().withId(event.transactionId().toString()).withType(EventTypes.MONEY_TRANSFERRED)
					.withSource(URI.create(EventSources.PAYMENT_SERVICE)).withTime(OffsetDateTime.now())
					.withDataContentType("application/json").withData(payload).build();

		}
		catch (Exception ex)
		{
			throw new IllegalStateException("Failed creating CloudEvent", ex);
		}
	}
}
