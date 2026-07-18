package io.morpheus.payments.payment.application.usecase;

import io.morpheus.payments.payment.application.command.TransferMoneyCommand;
import io.morpheus.payments.payment.application.port.WalletPersistencePort;
import io.morpheus.payments.payment.application.result.TransferResult;
import io.morpheus.payments.payment.domain.shared.Money;
import io.morpheus.payments.payment.domain.transfer.TransferCommand;
import io.morpheus.payments.payment.domain.transfer.TransferService;
import io.morpheus.payments.payment.domain.wallet.Wallet;
import io.morpheus.payments.payment.exception.ResourceNotFoundException;
import io.morpheus.payments.payment.model.response.TransferResponse;
import org.springframework.stereotype.Service;

@Service
public class TransferMoneyUseCase implements TransferUseCase {

    private final TransferService transferService;

    private final WalletPersistencePort walletPersistencePort;

    public TransferMoneyUseCase(
        final TransferService transferService,
        final WalletPersistencePort walletPersistencePort) {

        this.transferService = transferService;
        this.walletPersistencePort = walletPersistencePort;
    }

    @Override
    public TransferResponse execute(final TransferMoneyCommand command) throws ResourceNotFoundException {

        final Wallet sourceWallet = walletPersistencePort.findById(
                                                command.sourceWalletId()).orElseThrow(() ->
                                                    new ResourceNotFoundException("Source wallet not found"));

        final Wallet destinationWallet = walletPersistencePort.findById(
                                                command.destinationWalletId()).orElseThrow(() ->
                                                    new ResourceNotFoundException("Destination wallet not found"));

        final TransferCommand domainCommand = new TransferCommand(
                                                        sourceWallet.id(),
                                                        destinationWallet.id(),
                                                        Money.of(command.amount(), command.currency()));

        TransferResult result =  transferService.transfer(sourceWallet,
                                        destinationWallet,
                                        domainCommand,
                                        command.idempotencyKey());

        return new TransferResponse(result.transactionId());
    }
}
