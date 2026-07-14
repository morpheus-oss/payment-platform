package io.morpheus.payments.payment.model.response;

import java.math.BigDecimal;
import java.util.UUID;

public record WalletResponse(UUID walletId, String ownerId, String currency, BigDecimal balance) {
}
