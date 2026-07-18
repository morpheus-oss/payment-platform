package io.morpheus.payments.payment.application.port.in;

import io.morpheus.payments.payment.application.command.CreateWalletCommand;
import io.morpheus.payments.payment.application.result.CreateWalletResult;
import io.morpheus.payments.payment.exception.ResourceNotFoundException;

@FunctionalInterface
public interface CreateWalletPort extends InPort    {

    CreateWalletResult createWallet(CreateWalletCommand command) throws ResourceNotFoundException;
}
