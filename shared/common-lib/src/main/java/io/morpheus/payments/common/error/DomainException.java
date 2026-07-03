package io.morpheus.payments.common.error;

/**
 * Base class for all business rule violations.
 * <p>
 * Domain exceptions represent failures caused by business invariants, validation rules or domain policies. They are
 * deterministic and should not depend on external systems.
 */
public abstract class DomainException extends PlatformException
{

	protected DomainException(final ErrorCode errorCode)
	{
		super(errorCode);
	}

	protected DomainException(final ErrorCode errorCode, final Throwable cause)
	{
		super(errorCode, cause);
	}
}
