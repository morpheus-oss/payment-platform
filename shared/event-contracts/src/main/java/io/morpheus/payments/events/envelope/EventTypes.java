package io.morpheus.payments.events.envelope;

public interface EventTypes {

    public static final String MONEY_TRANSFERRED    = "MoneyTransferred";
    public static final String FRAUD_DETECTED       = "FraudDetected";
    public static final String FRAUD_CLEARED        = "FraudCleared";
}