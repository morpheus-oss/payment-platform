package io.morpheus.payments.common.exception;

import io.morpheus.payments.common.error.ErrorCode;

public class InsufficientFundsException extends BusinessException {

    public InsufficientFundsException(String message) {
        super(ErrorCode.INSUFFICIENT_FUNDS, message);
    }
}