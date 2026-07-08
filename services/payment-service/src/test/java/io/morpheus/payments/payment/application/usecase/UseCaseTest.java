package io.morpheus.payments.payment.application.usecase;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

class UseCaseTest {

    @Test
    void executeShouldReturnExpectedResult() {

        UseCase<String, Integer> useCase = String::length;

        assertEquals(5, useCase.execute("Hello"));
    }
}
