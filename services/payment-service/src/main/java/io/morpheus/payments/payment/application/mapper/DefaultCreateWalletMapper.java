package io.morpheus.payments.payment.application.mapper;

import io.morpheus.payments.payment.application.command.CreateWalletCommand;
import io.morpheus.payments.payment.application.result.CreateWalletResult;
import io.morpheus.payments.payment.domain.wallet.Wallet;
import io.morpheus.payments.payment.model.response.WalletResponse;

import java.math.BigDecimal;

public class DefaultCreateWalletMapper implements CreateWalletMapper {

    @Override
    public CreateWalletCommand toCommand(Wallet request) {

        return new CreateWalletCommand(
            request.ownerId(),
            request.balance().currency(),
            request.balance().amount());
    }

    @Override
    public WalletResponse toResponse(CreateWalletResult result) {

        return new WalletResponse(
            result.walletId(),
            result.ownerId(),
            result.currency(),
            BigDecimal.valueOf(result.balance()));
    }
}
