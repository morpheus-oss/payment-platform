package io.morpheus.payments.payment.application.usecase;

import io.morpheus.payments.payment.application.command.CreateWalletCommand;
import io.morpheus.payments.payment.application.port.in.CreateWalletPort;
import io.morpheus.payments.payment.application.result.CreateWalletResult;

public interface CreateWalletUseCase
                            extends CreateWalletPort,
                                    UseCase<CreateWalletCommand, CreateWalletResult> {

    @Override
    default CreateWalletResult createWallet(CreateWalletCommand command) {
        return execute(command);
    }
}
