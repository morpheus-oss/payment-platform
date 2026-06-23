package io.morpheus.payments.common.problem;

import io.morpheus.payments.common.exception.BusinessException;

import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;

import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(BusinessException.class)
    public ProblemDetail handleBusinessException(BusinessException ex) {

        ProblemDetail problem = ProblemDetails.of(HttpStatus.BAD_REQUEST,
                                                    ex.getErrorCode().name(),
                                                    ex.getMessage());

        problem.setProperty("errorCode", ex.getErrorCode().name());

        return problem;
    }

    @ExceptionHandler(Exception.class)
    public ProblemDetail handleGenericException(Exception ex) {

        return ProblemDetails.of(HttpStatus.INTERNAL_SERVER_ERROR,
                                "INTERNAL_ERROR",
                                ex.getMessage());
    }
}