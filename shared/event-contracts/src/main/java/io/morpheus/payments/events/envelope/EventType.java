package io.morpheus.payments.events.envelope;

public enum EventType {

	MONEY_TRANSFERRED("MoneyTransferred"),
	FRAUD_DETECTED("FraudDetected"),
	FRAUD_CLEARED("FraudCleared");

    private String eventType;

    EventType(String eventType) {
        this.eventType = eventType;
    }

    public String toString() {
        return eventType;
    }
}
