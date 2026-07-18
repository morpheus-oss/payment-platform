package io.morpheus.payments.payment.application.usecase;


import io.morpheus.payments.payment.application.command.TransferMoneyCommand;
import io.morpheus.payments.payment.exception.ResourceNotFoundException;
import io.morpheus.payments.payment.model.response.TransferResponse;

public interface TransferUseCase extends UseCase<TransferMoneyCommand, TransferResponse> {
    TransferResponse execute(TransferMoneyCommand command) throws ResourceNotFoundException;
}
