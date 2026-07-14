package io.morpheus.payments.payment.application.mapper;

import io.morpheus.payments.payment.application.command.CreateWalletCommand;
import io.morpheus.payments.payment.application.result.CreateWalletResult;
import io.morpheus.payments.payment.domain.wallet.Wallet;
import io.morpheus.payments.payment.model.response.WalletResponse;

/**
 * Maps between REST contracts and application contracts.
 */
public interface CreateWalletMapper {

    CreateWalletCommand toCommand(Wallet request);

    WalletResponse toResponse(CreateWalletResult result);
}
