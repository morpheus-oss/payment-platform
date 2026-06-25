package io.morpheus.payments.payment.wallet;

import java.math.BigDecimal;
import java.util.UUID;

public record WalletResponse(
        UUID id,
        String ownerId,
        BigDecimal balance
) { }
