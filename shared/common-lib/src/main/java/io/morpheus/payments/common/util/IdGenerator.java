package io.morpheus.payments.common.util;

import java.util.UUID;

public final class IdGenerator {

    public static UUID generate() {
        return UUID.randomUUID();
    }
}