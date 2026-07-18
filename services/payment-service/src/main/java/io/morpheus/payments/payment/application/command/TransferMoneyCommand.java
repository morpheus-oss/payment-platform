package io.morpheus.payments.payment.application.command;

import io.morpheus.payments.payment.model.request.TransferRequest;

import java.math.BigDecimal;
import java.util.Currency;
import java.util.Objects;
import java.util.UUID;

/**
 * Application command representing a transfer request.
 *
 * <p>
 * This command belongs to the Application layer.
 * It encapsulates application-specific concerns such as idempotency while
 * remaining independent of transport protocols.
 */
public record TransferMoneyCommand(String idempotencyKey,
                                   UUID sourceWalletId,
                                   UUID destinationWalletId,
                                   Currency currency,
                                   BigDecimal amount) implements Command {

    public TransferMoneyCommand {

        Objects.requireNonNull(idempotencyKey, "idempotencyKey must not be null");
        Objects.requireNonNull(sourceWalletId, "sourceWalletId must not be null");
        Objects.requireNonNull(destinationWalletId, "destinationWalletId must not be null");
        Objects.requireNonNull(amount, "amount must not be null");
    }

    public static TransferMoneyCommand from(final String idempotencyKey,
                                            final TransferRequest request) {

        return new TransferMoneyCommand(idempotencyKey,
                                        request.sourceWalletId(),
                                        request.destinationWalletId(),
                                        Currency.getInstance(request.currencyCode()),
                                        request.amount());
    }
}
