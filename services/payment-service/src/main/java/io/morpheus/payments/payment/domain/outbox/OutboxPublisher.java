package io.morpheus.payments.payment.domain.outbox;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.morpheus.payments.payment.messaging.publisher.CloudEventPublisher;
import io.morpheus.payments.payment.persistence.entity.OutboxEventEntity;
import io.morpheus.payments.payment.persistence.repository.OutboxEventRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import tools.jackson.databind.ObjectMapper;

@Component
@RequiredArgsConstructor
@Slf4j
public class OutboxPublisher    {

	private static final int BATCH_SIZE = 100;

	private final OutboxEventRepository repository;
	private final ObjectMapper objectMapper;
	private final CloudEventPublisher publisher;

	@Scheduled(fixedDelayString = "5000")
	@Transactional
	public void publish() {

		List<OutboxEventEntity> events = repository.lockBatch(BATCH_SIZE);

		for (OutboxEventEntity event : events)  {

			try
			{
				publishEvent(event);
				event.setStatus(OutboxStatus.PUBLISHED);

			} catch (Exception ex) {
				event.setStatus(OutboxStatus.FAILED);
				event.setRetryCount(event.getRetryCount() + 1);

				log.error("Failed publishing event {}", event.getId(), ex);
			}
		}
	}

	private void publishEvent(OutboxEventEntity event)  {

    }
}
