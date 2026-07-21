package io.morpheus.payments.payment.application.port.out;

import io.morpheus.payments.payment.domain.transfer.TransferCompleted;

public interface OutboxPublisherPort extends OutPort    {

    void publish(TransferCompleted event);
}
