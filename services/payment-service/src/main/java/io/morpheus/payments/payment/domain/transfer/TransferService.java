package io.morpheus.payments.payment.domain.transfer;

import io.morpheus.payments.payment.application.port.out.OutboxPublisherPort;
import io.morpheus.payments.payment.application.port.out.TransferPersistencePort;
import io.morpheus.payments.payment.application.port.out.WalletPersistencePort;
import io.morpheus.payments.payment.application.result.TransferResult;
import io.morpheus.payments.payment.domain.shared.Money;
import io.morpheus.payments.payment.domain.wallet.Wallet;
import io.morpheus.payments.payment.exception.domain.InsufficientFundsException;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;

@Service
@RequiredArgsConstructor
public class TransferService    {

    private static final Logger logger = LoggerFactory.getLogger(TransferService.class);

    private final WalletPersistencePort walletPersistencePort;

    private final TransferPersistencePort transferPersistencePort;

    private final OutboxPublisherPort outboxPublisherPort;

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

        outboxPublisherPort.publish(
                        new TransferCompleted(
                                result.transactionId(),
                                domainCommand.sourceWalletId().value(),
                                domainCommand.destinationWalletId().value(),
                                domainCommand.amount().currency().getCurrencyCode(),
                                domainCommand.amount().amount(),
                            Instant.now()));

        return result;
    }


}
