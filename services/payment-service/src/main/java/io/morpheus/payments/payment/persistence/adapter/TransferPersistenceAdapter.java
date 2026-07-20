package io.morpheus.payments.payment.persistence.adapter;

import io.morpheus.payments.payment.application.port.out.TransferPersistencePort;
import io.morpheus.payments.payment.domain.transfer.TransferCommand;
import io.morpheus.payments.payment.domain.transfer.TransferStatus;
import io.morpheus.payments.payment.persistence.entity.TransferEntity;
import io.morpheus.payments.payment.persistence.repository.TransferRepository;
import java.time.Instant;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class TransferPersistenceAdapter implements TransferPersistencePort
{

    private final TransferRepository transferRepository;

    @Override
    public UUID save(final TransferCommand command)
    {
        TransferEntity transfer = new TransferEntity();

        UUID transferId = UUID.randomUUID();

        transfer.setId(transferId);
        transfer.setSourceWalletId(command.sourceWalletId().value());
        transfer.setDestinationWalletId(command.destinationWalletId().value());
        transfer.setCurrencyCode(command.amount().currency().getCurrencyCode());
        transfer.setAmount(command.amount().amount());
        transfer.setStatus(TransferStatus.COMPLETED);
        transfer.setCreatedAt(Instant.now());

        transferRepository.save(transfer);

        return transferId;
    }

}
