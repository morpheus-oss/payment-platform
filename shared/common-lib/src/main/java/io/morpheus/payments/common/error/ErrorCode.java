package io.morpheus.payments.common.error;

/**
 * Represents a stable platform error.
 *
 * <p>Error codes are immutable identifiers that remain stable over time and can safely be exposed
 * through APIs, logs and metrics.
 */
public interface ErrorCode {

  String code();

  String message();

  ErrorCategory category();
}
