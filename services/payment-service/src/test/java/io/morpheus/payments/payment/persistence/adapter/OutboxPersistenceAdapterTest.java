package io.morpheus.payments.payment.persistence.adapter;

import io.morpheus.payments.events.envelope.EventType;
import io.morpheus.payments.payment.application.result.OutboxEvent;
import io.morpheus.payments.payment.persistence.entity.OutboxEventEntity;
import io.morpheus.payments.payment.persistence.repository.OutboxEventRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class OutboxPersistenceAdapterTest {

    @Mock
    private OutboxEventRepository outboxEventRepository;

    @Test
    void shouldClaimAndMapPublishableOutboxEvents() {
        final UUID id = UUID.randomUUID();
        final UUID aggregateId = UUID.randomUUID();

        final OutboxEventEntity entity = new OutboxEventEntity();
        entity.setId(id);
        entity.setAggregateId(aggregateId);
        entity.setEventType(EventType.MONEY_TRANSFERRED);
        entity.setPayload("{\"transactionId\":\"" + aggregateId + "\"}");

        when(outboxEventRepository.lockBatch(10)).thenReturn(List.of(entity));

        final OutboxPersistenceAdapter adapter = new OutboxPersistenceAdapter(outboxEventRepository);
        final List<OutboxEvent> result = adapter.claimPublishableBatch(10);

        assertThat(result).hasSize(1);

        assertThat(result.getFirst().id()).isEqualTo(id);

        assertThat(result.getFirst().aggregateId()).isEqualTo(aggregateId);

        assertThat(result.getFirst().eventType()).isEqualTo(EventType.MONEY_TRANSFERRED);

        assertThat(result.getFirst().payload()).isEqualTo(entity.getPayload());

        verify(outboxEventRepository).lockBatch(10);
    }

    @Test
    void shouldReturnEmptyListWhenNoPublishableEventsExist() {

        when(outboxEventRepository.lockBatch(10)).thenReturn(List.of());

        final OutboxPersistenceAdapter adapter = new OutboxPersistenceAdapter(outboxEventRepository);
        final List<OutboxEvent> result = adapter.claimPublishableBatch(10);

        assertThat(result).isEmpty();

        verify(outboxEventRepository).lockBatch(10);
    }

}
