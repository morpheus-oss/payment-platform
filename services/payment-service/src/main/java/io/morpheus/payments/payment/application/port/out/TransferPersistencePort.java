package io.morpheus.payments.payment.application.port.out;

import io.morpheus.payments.payment.domain.transfer.TransferCommand;
import java.util.UUID;

public interface TransferPersistencePort extends OutPort {

    UUID save(TransferCommand command);

}
