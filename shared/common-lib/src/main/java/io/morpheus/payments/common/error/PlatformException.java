package io.morpheus.payments.common.error;

import java.util.Objects;

/**
 * Base exception for the payment platform.
 *
 * <p>All application, domain and infrastructure exceptions should extend this
 * type. The exception carries a strongly typed {@link ErrorCode} that can later
 * be translated into RFC 9457 Problem Details, structured logs and metrics.
 */
public abstract class PlatformException extends RuntimeException {

    private final ErrorCode errorCode;

    private final ErrorContext errorContext;

    protected PlatformException(final ErrorCode errorCode) {
        this(errorCode, ErrorContext.empty(), null);
    }

    protected PlatformException(final ErrorCode errorCode, final Throwable cause) {
        this(errorCode, ErrorContext.empty(), cause);
    }

    protected PlatformException(final ErrorCode errorCode, final ErrorContext errorContext, final Throwable cause) {
        super(errorCode.message(), cause);

        this.errorCode = Objects.requireNonNull(errorCode, "errorCode must not be null");
        this.errorContext = Objects.requireNonNull(errorContext, "context must not be null");
    }

    public final ErrorCode errorCode() {
        return errorCode;
    }

    public final String code() {
        return errorCode.code();
    }

    public final ErrorContext errorContext() {
        return errorContext;
    }

}