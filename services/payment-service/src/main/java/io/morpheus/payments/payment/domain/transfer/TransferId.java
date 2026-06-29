package io.morpheus.payments.payment.domain.transfer;

import java.util.Objects;
import java.util.UUID;

public record TransferId(UUID value) {

    public TransferId {
        Objects.requireNonNull(value, "value must not be null");
    }

    public static TransferId generate() {
        return new TransferId(UUID.randomUUID());
    }

    public static TransferId from(final UUID value) {
        return new TransferId(value);
    }

    @Override
    public String toString() {
        return value.toString();
    }

}