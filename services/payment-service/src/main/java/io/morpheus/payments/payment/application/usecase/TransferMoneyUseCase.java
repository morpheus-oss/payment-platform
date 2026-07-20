package io.morpheus.payments.payment.application.usecase;

import io.morpheus.payments.payment.application.command.TransferMoneyCommand;
import io.morpheus.payments.payment.application.port.out.IdempotencyPort;
import io.morpheus.payments.payment.application.port.out.WalletPersistencePort;
import io.morpheus.payments.payment.application.result.TransferResult;
import io.morpheus.payments.payment.domain.shared.Money;
import io.morpheus.payments.payment.domain.transfer.TransferCommand;
import io.morpheus.payments.payment.domain.transfer.TransferService;
import io.morpheus.payments.payment.domain.wallet.Wallet;
import io.morpheus.payments.payment.exception.ResourceNotFoundException;
import io.morpheus.payments.payment.model.response.TransferResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class TransferMoneyUseCase implements TransferUseCase {

    private final TransferService transferService;

    private final WalletPersistencePort walletPersistencePort;

    private final IdempotencyPort idempotencyPort;

    @Override
    public TransferResponse execute(final TransferMoneyCommand command) throws ResourceNotFoundException {

        return idempotencyPort.findTransferId(command.idempotencyKey())
                              .map(TransferResponse::new)
                              .orElseGet(() -> {
                                  try {
                                      return executeTransfer(command);
                                  } catch (ResourceNotFoundException e) {
                                      throw new RuntimeException(e);
                                  }
                              });
    }

    private TransferResponse executeTransfer(final TransferMoneyCommand command) throws ResourceNotFoundException {

        final Wallet sourceWallet = walletPersistencePort.findById(
                                        command.sourceWalletId()).orElseThrow(
                                            () -> new ResourceNotFoundException("Source wallet not found"));

        final Wallet destinationWallet = walletPersistencePort.findById(
                                        command.destinationWalletId()).orElseThrow(
                                            () -> new ResourceNotFoundException("Destination wallet not found"));

        final TransferCommand domainCommand = new TransferCommand(sourceWallet.id(),
                                                                  destinationWallet.id(),
                                                                  Money.of(command.amount(), command.currency()));

        TransferResult result =  transferService.transfer(sourceWallet,
                                                          destinationWallet,
                                                          domainCommand);

        idempotencyPort.save(command.idempotencyKey(), result.transactionId());

        return new TransferResponse(result.transactionId());
    }
}
