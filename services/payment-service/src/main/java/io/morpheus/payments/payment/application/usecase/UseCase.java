package io.morpheus.payments.payment.application.usecase;

import io.morpheus.payments.payment.exception.ResourceNotFoundException;

/**
 * Generic contract for all application use cases.
 *
 * @param <C> command type
 * @param <R> result type
 */
@FunctionalInterface
public interface UseCase<C, R> {

    R execute(C command) throws ResourceNotFoundException;
}
