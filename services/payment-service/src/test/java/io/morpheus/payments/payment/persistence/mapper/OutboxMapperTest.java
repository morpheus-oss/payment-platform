package io.morpheus.payments.payment.persistence.mapper;

import io.morpheus.payments.events.envelope.EventType;
import io.morpheus.payments.payment.application.result.OutboxEvent;
import io.morpheus.payments.payment.domain.outbox.OutboxStatus;
import io.morpheus.payments.payment.persistence.entity.OutboxEventEntity;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.UUID;

@ExtendWith(MockitoExtension.class)
public class OutboxMapperTest {

    @Mock
    private OutboxMapper mapper;

    @Test
    void shouldMapRetryCountToApplicationEvent() {
        final UUID id = UUID.randomUUID();
        final UUID aggregateId = UUID.randomUUID();

        final OutboxEventEntity entity = new OutboxEventEntity();
        entity.setId(id);
        entity.setAggregateId(aggregateId);
        entity.setEventType(EventType.MONEY_TRANSFERRED);
        entity.setPayload("{}");
        entity.setRetryCount(2);
        entity.setStatus(OutboxStatus.PROCESSING);

        final OutboxEvent result = mapper.toApplicationEvent(entity);

        assertThat(result.id()).isEqualTo(id);
        assertThat(result.aggregateId()).isEqualTo(aggregateId);
        assertThat(result.eventType()).isEqualTo(EventType.MONEY_TRANSFERRED);
        assertThat(result.payload()).isEqualTo("{}");
        assertThat(result.retryCount()).isEqualTo(2);
    }
}
