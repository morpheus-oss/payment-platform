package io.morpheus.payments.payment.application.usecase;

import io.morpheus.payments.payment.application.command.CreateWalletCommand;
import io.morpheus.payments.payment.application.port.out.WalletRepositoryPort;
import io.morpheus.payments.payment.application.result.CreateWalletResult;
import io.morpheus.payments.payment.domain.shared.Money;
import io.morpheus.payments.payment.domain.wallet.Wallet;
import io.morpheus.payments.payment.domain.wallet.WalletId;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Currency;
import java.util.Locale;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CreateWalletUseCaseTest {

    @Mock
    private WalletRepositoryPort walletRepositoryPort;

    @InjectMocks
    private DefaultCreateWalletUseCase useCase;

    @Test
    void shouldThrowUntilImplementationIsMigrated() {

        CreateWalletUseCase walletUseCase = new DefaultCreateWalletUseCase(walletRepositoryPort);

        assertThrows(UnsupportedOperationException.class,
                    () -> walletUseCase.execute(
                        new CreateWalletCommand(
                            "customer",
                            "INR")));
    }

    @Test
    void shouldPersistWallet() {
        Wallet wallet = Wallet.from(WalletId.generate(), Money.zero(Currency.getInstance(Locale.getDefault())));

        when(walletRepositoryPort.save(any())).thenReturn(wallet);

        CreateWalletCommand command = new CreateWalletCommand("customer", "INR");
        CreateWalletResult result = useCase.execute(command);

        verify(walletRepositoryPort).save(any());

        assertThat(result).isNotNull();
    }
}
