package io.morpheus.payments.payment.domain.wallet;

import java.math.BigDecimal;
import java.util.UUID;

public record WalletResponse(
        UUID id,
        String ownerId,
        BigDecimal balance
) { }
