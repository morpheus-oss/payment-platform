package io.morpheus.payments.payment.exception.domain;

import io.morpheus.payments.common.error.DomainException;
import io.morpheus.payments.payment.error.PaymentErrorCode;

/** Thrown when a debit operation would result in a negative wallet balance. */
public final class InsufficientFundsException extends DomainException {

  public InsufficientFundsException() {
    super(PaymentErrorCode.INSUFFICIENT_FUNDS);
  }

  public InsufficientFundsException(final Throwable cause) {
    super(PaymentErrorCode.INSUFFICIENT_FUNDS, cause);
  }
}
