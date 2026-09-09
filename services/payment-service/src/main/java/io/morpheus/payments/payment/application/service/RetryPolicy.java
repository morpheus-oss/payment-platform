package io.morpheus.payments.payment.application.service;

import org.springframework.stereotype.Component;

import java.time.Duration;

@Component
public class RetryPolicy {

    private static final int MAX_RETRIES = 3;

    public boolean shouldRetry(final int retryCount) {
        return retryCount < MAX_RETRIES;
    }

    public Duration delayFor(final int retryCount) {
        return switch (retryCount) {
            case 1 -> Duration.ofSeconds(30);
            case 2 -> Duration.ofSeconds(60);
            case 3 -> Duration.ofSeconds(300);
            default -> throw new IllegalArgumentException(
                "Unsupported retry count: " + retryCount);
        };
    }

}
