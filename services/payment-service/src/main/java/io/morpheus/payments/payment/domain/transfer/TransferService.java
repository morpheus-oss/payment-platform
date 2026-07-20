package io.morpheus.payments.payment.domain.transfer;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.morpheus.payments.events.envelope.EventTypes;
import io.morpheus.payments.events.types.MoneyTransferredEvent;
import io.morpheus.payments.payment.application.port.out.TransferPersistencePort;
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

    private final TransferPersistencePort transferPersistencePort;

    private final OutboxEventRepository outboxRepository;

    private final ObjectMapper objectMapper;

    @Transactional
    public TransferResult transfer(final Wallet source,
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

        TransferResult result = new TransferResult(transferPersistencePort.save(domainCommand));

        try {
            createOutboxEvent(result.transactionId(), domainCommand);
        }
        catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }

        return result;
    }

    private void createOutboxEvent(final UUID txId, final TransferCommand command) throws JsonProcessingException {

        MoneyTransferredEvent event = new MoneyTransferredEvent(txId,
                                                                command.sourceWalletId().value(),
                                                                command.destinationWalletId().value(),
                                                                command.amount().currency().getCurrencyCode(),
                                                                command.amount().amount(),
                                                                Instant.now());

        String payload = objectMapper.writeValueAsString(event);

        OutboxEventEntity outbox = new OutboxEventEntity();
        outbox.setId(UUID.randomUUID());
        outbox.setAggregateId(txId);
        outbox.setEventType(EventTypes.MONEY_TRANSFERRED);
        outbox.setPayload(payload);
        outbox.setStatus(OutboxStatus.PENDING);
        outbox.setRetryCount(0);

        outboxRepository.save(outbox);
    }

}
