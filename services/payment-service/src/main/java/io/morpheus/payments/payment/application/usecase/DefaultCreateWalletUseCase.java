package io.morpheus.payments.payment.application.usecase;

import io.morpheus.payments.payment.application.command.CreateWalletCommand;
import io.morpheus.payments.payment.application.port.out.WalletPersistencePort;
import io.morpheus.payments.payment.application.result.CreateWalletResult;
import io.morpheus.payments.payment.domain.shared.Money;
import io.morpheus.payments.payment.domain.wallet.Wallet;
import io.morpheus.payments.payment.domain.wallet.WalletId;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class DefaultCreateWalletUseCase implements CreateWalletUseCase {

    private final WalletPersistencePort walletPersistencePort;

    @Override
    public CreateWalletResult execute(CreateWalletCommand command) {

        Wallet wallet = Wallet.from(WalletId.generate(),
                                    command.ownerId(),
                                    Money.of(command.initialBalance(), command.currency()));

        Wallet persistedWallet = walletPersistencePort.save(wallet);

        return new CreateWalletResult(
                            persistedWallet.id().value(),
                            persistedWallet.ownerId(),
                            persistedWallet.balance().currency().getCurrencyCode(),
                            persistedWallet.balance().amount().doubleValue());
    }

}
