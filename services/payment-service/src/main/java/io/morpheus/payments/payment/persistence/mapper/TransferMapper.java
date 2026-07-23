package io.morpheus.payments.payment.persistence.mapper;

import io.morpheus.payments.payment.application.result.TransferResult;
import io.morpheus.payments.payment.domain.transfer.TransferCommand;
import io.morpheus.payments.payment.persistence.entity.TransferEntity;
import java.util.UUID;
import org.springframework.stereotype.Component;

@Component
public class TransferMapper     {

    public TransferEntity toEntity(final UUID transferId,
                                   final TransferCommand command)   {

        TransferEntity entity = new TransferEntity();

        entity.setId(transferId);
        entity.setSourceWalletId(command.sourceWalletId().value());
        entity.setDestinationWalletId(command.destinationWalletId().value());
        entity.setAmount(command.amount().amount());
        entity.setCurrencyCode(command.amount().currency().getCurrencyCode());

        return entity;
    }

    public TransferResult toResult(final UUID transferId)   {
        return new TransferResult(transferId);
    }

}
