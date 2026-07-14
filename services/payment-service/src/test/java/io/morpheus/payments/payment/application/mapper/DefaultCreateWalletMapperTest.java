package io.morpheus.payments.payment.application.mapper;

import io.morpheus.payments.payment.application.command.CreateWalletCommand;
import io.morpheus.payments.payment.application.result.CreateWalletResult;
import io.morpheus.payments.payment.domain.shared.Money;
import io.morpheus.payments.payment.domain.wallet.Wallet;
import io.morpheus.payments.payment.domain.wallet.WalletId;
import io.morpheus.payments.payment.model.response.WalletResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.Currency;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

class DefaultCreateWalletMapperTest {

    private CreateWalletMapper mapper;

    @BeforeEach
    void setUp() {
        mapper = new DefaultCreateWalletMapper();
    }

    @Test
    void shouldMapRequestToCommand() {

        UUID walletUuid = UUID.randomUUID();
        String ownerId = "abcdefg@xyz.com";

        Wallet request = Wallet.from(
                                WalletId.from(walletUuid),
                                ownerId,
                                Money.zero(Currency.getInstance("INR")));

        CreateWalletCommand command = mapper.toCommand(request);

        assertThat(command.ownerId()).isEqualTo(ownerId);

        assertThat(command.initialBalance()).isEqualByComparingTo(BigDecimal.valueOf(100));
    }

    @Test
    void shouldMapResultToResponse() {

        UUID walletId = UUID.randomUUID();
        String ownerId = "abcdefg@xyz.com";

        CreateWalletResult result = new CreateWalletResult(
                                                    walletId,
                                                    ownerId,
                                                    "INR",
                                                    100.0d);

        WalletResponse response = mapper.toResponse(result);

        assertThat(response.walletId()).isEqualTo(walletId);

        assertThat(response.ownerId()).isEqualTo(ownerId);

        assertThat(response.balance()).isEqualByComparingTo(BigDecimal.valueOf(100));
    }
}
