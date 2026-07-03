package io.morpheus.payments.common.error;

/**
 * Base class for failures originating from infrastructure components.
 * <p>
 * For example database failures, messaging failures, serialization errors and network communication issues.
 * <p>
 * Infrastructure exceptions should never be used to represent business rule violations.
 */
public abstract class InfrastructureException extends PlatformException
{

	protected InfrastructureException(final ErrorCode errorCode)
	{
		super(errorCode);
	}

	protected InfrastructureException(final ErrorCode errorCode, final Throwable cause)
	{
		super(errorCode, cause);
	}
}
