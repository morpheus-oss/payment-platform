package io.morpheus.payments.payment.error;

import io.morpheus.payments.common.error.ErrorCategory;
import io.morpheus.payments.common.error.ErrorCode;

/**
 * Error codes owned by the Payment bounded context.
 *
 * <p>Codes are immutable API contracts and must never be reused or repurposed once published.
 */
public enum PaymentErrorCode implements ErrorCode {
  INSUFFICIENT_FUNDS("PAYMENT-001", "Insufficient funds.", ErrorCategory.DOMAIN),

  WALLET_NOT_FOUND("PAYMENT-002", "Wallet not found.", ErrorCategory.DOMAIN),

  DUPLICATE_TRANSFER("PAYMENT-003", "Transfer has already been processed.", ErrorCategory.DOMAIN);

  private final String code;
  private final String message;
  private final ErrorCategory category;

  PaymentErrorCode(final String code, final String message, final ErrorCategory category) {

    this.code = code;
    this.message = message;
    this.category = category;
  }

  @Override
  public String code() {
    return code;
  }

  @Override
  public String message() {
    return message;
  }

  @Override
  public ErrorCategory category() {
    return category;
  }
}
