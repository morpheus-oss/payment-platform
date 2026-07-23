package io.morpheus.payments.payment.persistence.adapter;

import io.morpheus.payments.payment.application.port.out.TransferPersistencePort;
import io.morpheus.payments.payment.domain.transfer.TransferCommand;
import io.morpheus.payments.payment.persistence.entity.TransferEntity;
import io.morpheus.payments.payment.persistence.mapper.TransferMapper;
import io.morpheus.payments.payment.persistence.repository.TransferRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
@RequiredArgsConstructor
public class TransferPersistenceAdapter implements TransferPersistencePort  {

    private final TransferRepository transferRepository;
    private final TransferMapper transferMapper;

    @Override
    public UUID save(final TransferCommand command)     {

        UUID transferId = UUID.randomUUID();

        TransferEntity transfer = transferMapper.toEntity(transferId, command);
        transferRepository.save(transfer);

        return transferId;
    }

}
