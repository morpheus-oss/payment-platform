package io.morpheus.payments.payment.application.port.out;

import io.morpheus.payments.payment.application.result.OutboxEvent;

import java.util.List;

public interface OutboxDispatchPort extends OutPort {

    List<OutboxEvent> claimPublishableBatch(int batchSize);
}
