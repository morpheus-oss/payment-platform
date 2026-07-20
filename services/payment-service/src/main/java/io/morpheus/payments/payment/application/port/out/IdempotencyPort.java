package io.morpheus.payments.payment.application.port.out;

import java.util.Optional;
import java.util.UUID;

public interface IdempotencyPort extends OutPort{

    Optional<UUID> findTransferId(String idempotencyKey);

    void save(String idempotencyKey, UUID transferId);

}
