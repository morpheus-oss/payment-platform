CREATE TABLE outbox_events (
    id UUID PRIMARY KEY,
    aggregate_id UUID,
    event_type VARCHAR(255),
    payload TEXT,
    status VARCHAR(50),
    retry_count INTEGER,
    next_retry_at TIMESTAMP,
    created_at TIMESTAMP
);