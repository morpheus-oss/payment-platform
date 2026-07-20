package io.morpheus.payments.payment.application.usecase;

import io.morpheus.payments.payment.application.command.CreateWalletCommand;
import io.morpheus.payments.payment.application.port.out.WalletPersistencePort;
import io.morpheus.payments.payment.application.result.CreateWalletResult;
import io.morpheus.payments.payment.domain.shared.Money;
import io.morpheus.payments.payment.domain.wallet.Wallet;
import io.morpheus.payments.payment.domain.wallet.WalletId;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.Currency;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CreateWalletUseCaseTest {

    @Mock
    private WalletPersistencePort walletPersistencePort;

    @InjectMocks
    private DefaultCreateWalletUseCase useCase;

    @Test
    void shouldPersistWallet() {
        String ownerId = "abcdefg@xyz.com";

        Wallet wallet = Wallet.from(WalletId.generate(), ownerId, Money.zero(Currency.getInstance("INR")));

        when(walletPersistencePort.save(any())).thenReturn(wallet);

        CreateWalletCommand command = new CreateWalletCommand(
                                                ownerId,
                                                wallet.balance().currency(),
                                                BigDecimal.ZERO);
        CreateWalletResult result = useCase.execute(command);

        verify(walletPersistencePort).save(any());

        assertThat(result.walletId()).isEqualTo(wallet.id().value());
        assertThat(result.ownerId()).isEqualTo(wallet.ownerId());
        assertThat(result.balance()).isEqualByComparingTo(wallet.balance().amount().doubleValue());
    }
}
