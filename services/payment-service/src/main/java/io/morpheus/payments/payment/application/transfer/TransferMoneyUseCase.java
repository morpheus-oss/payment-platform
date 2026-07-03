package io.morpheus.payments.payment.application.transfer;

import io.morpheus.payments.payment.domain.transfer.TransferRequest;
import io.morpheus.payments.payment.domain.transfer.TransferResponse;
import io.morpheus.payments.payment.domain.transfer.TransferService;
import io.morpheus.payments.payment.domain.wallet.Wallet;
import org.springframework.stereotype.Service;

@Service
public class TransferMoneyUseCase implements TransferUseCase {

  private final TransferService transferService;

  public TransferMoneyUseCase(final TransferService transferService) {
    this.transferService = transferService;
  }

  @Override
  public TransferResponse execute(final TransferRequest request) {

    TransferResponse existingTransfer = transferService.validateIdempotency(request);
    if (existingTransfer != null) {
      return existingTransfer;
    }

    Wallet sourceWallet = transferService.loadSourceWallet(request);
    Wallet destinationWallet = transferService.loadDestinationWallet(request);

    return transferService.transfer(sourceWallet, destinationWallet, request);
  }
}
