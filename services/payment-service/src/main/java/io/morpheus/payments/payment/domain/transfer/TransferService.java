package io.morpheus.payments.payment.domain.transfer;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.morpheus.payments.events.envelope.EventTypes;
import io.morpheus.payments.events.types.MoneyTransferredEvent;
import io.morpheus.payments.payment.application.port.out.WalletPersistencePort;
import io.morpheus.payments.payment.application.result.TransferResult;
import io.morpheus.payments.payment.domain.outbox.OutboxStatus;
import io.morpheus.payments.payment.domain.shared.Money;
import io.morpheus.payments.payment.domain.wallet.Wallet;
import io.morpheus.payments.payment.exception.domain.InsufficientFundsException;
import io.morpheus.payments.payment.persistence.entity.OutboxEventEntity;
import io.morpheus.payments.payment.persistence.entity.TransferEntity;
import io.morpheus.payments.payment.persistence.repository.OutboxEventRepository;
import io.morpheus.payments.payment.persistence.repository.TransferRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class TransferService
{

    private static final Logger logger = LoggerFactory.getLogger(TransferService.class);

    private final WalletPersistencePort walletPersistencePort;

    private final TransferRepository transferRepository;
    private final OutboxEventRepository outboxRepository;

    private final ObjectMapper objectMapper;

    @Transactional
    public TransferResult transfer(
        final Wallet source,
        final Wallet destination,
        final TransferCommand domainCommand) {

        if (!source.hasSufficientFunds(domainCommand.amount().amount())) {
            throw new InsufficientFundsException();
        }

        Money money = domainCommand.amount();

        source.withdraw(money);
        destination.deposit(money);

        walletPersistencePort.save(source);
        walletPersistencePort.save(destination);

        TransferEntity transfer = createTransfer(domainCommand);

        transferRepository.save(transfer);

        try {
            createOutboxEvent(transfer);
        }
        catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }

        return new TransferResult(transfer.getId());
    }

    private TransferEntity createTransfer(final TransferCommand command) {

        TransferEntity transfer = new TransferEntity();
        transfer.setId(UUID.randomUUID());
        transfer.setSourceWalletId(command.sourceWalletId().value());
        transfer.setDestinationWalletId(command.destinationWalletId().value());
        transfer.setCurrencyCode(command.amount().currency().getCurrencyCode());
        transfer.setAmount(command.amount().amount());
        transfer.setStatus(TransferStatus.COMPLETED);
        transfer.setCreatedAt(Instant.now());

        return transfer;
    }

    private void createOutboxEvent(final TransferEntity transfer) throws JsonProcessingException {

        MoneyTransferredEvent event = new MoneyTransferredEvent(transfer.getId(),
                                                                transfer.getSourceWalletId(),
                                                                transfer.getDestinationWalletId(),
                                                                transfer.getCurrencyCode(),
                                                                transfer.getAmount(),
                                                                Instant.now());

        String payload = objectMapper.writeValueAsString(event);

        OutboxEventEntity outbox = new OutboxEventEntity();
        outbox.setId(UUID.randomUUID());
        outbox.setAggregateId(transfer.getId());
        outbox.setEventType(EventTypes.MONEY_TRANSFERRED);
        outbox.setPayload(payload);
        outbox.setStatus(OutboxStatus.PENDING);
        outbox.setRetryCount(0);

        outboxRepository.save(outbox);
    }

}
