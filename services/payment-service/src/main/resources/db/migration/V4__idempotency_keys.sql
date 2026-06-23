CREATE TABLE idempotency_keys (
    id UUID PRIMARY KEY,
    idempotency_key VARCHAR(255) UNIQUE NOT NULL,
    transfer_id UUID,
    created_at TIMESTAMP NOT NULL
);