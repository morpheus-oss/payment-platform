package io.morpheus.payments.payment.application.usecase;

import io.morpheus.payments.payment.application.command.CreateWalletCommand;
import io.morpheus.payments.payment.application.port.out.WalletRepositoryPort;
import io.morpheus.payments.payment.application.result.CreateWalletResult;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class DefaultCreateWalletUseCase implements CreateWalletUseCase {

    private final WalletRepositoryPort walletRepositoryPort;

    @Override
    public CreateWalletResult execute(CreateWalletCommand command) {

        throw new UnsupportedOperationException(
                "Implementation will be migrated from WalletService.");
    }
}
