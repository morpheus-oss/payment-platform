package io.morpheus.payments.common.error;

/**
 * High-level classification of platform errors.
 *
 * <p>Categories describe the nature of a failure rather than its business meaning. They enable
 * consistent logging, metrics, alerting and HTTP response mapping across all services.
 */
public enum ErrorCategory {

  /** Business rule or domain invariant violation. */
  DOMAIN,

  /** Invalid client input or request. */
  VALIDATION,

  /** Authentication or authorization failure. */
  SECURITY,

  /**
   * Failure caused by external infrastructure such as databases, message brokers or remote
   * services.
   */
  INFRASTRUCTURE,

  /** Unexpected platform failure. */
  INTERNAL
}
