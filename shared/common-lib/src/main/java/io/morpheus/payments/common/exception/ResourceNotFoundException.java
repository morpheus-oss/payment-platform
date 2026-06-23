package io.morpheus.payments.common.exception;

import io.morpheus.payments.common.error.ErrorCode;

public class ResourceNotFoundException extends BusinessException {

    public ResourceNotFoundException(String message) {
        super(ErrorCode.WALLET_NOT_FOUND, message);
    }
}