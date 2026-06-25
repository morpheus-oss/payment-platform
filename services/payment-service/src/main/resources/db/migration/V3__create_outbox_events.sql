CREATE TABLE outbox_events (
    id UUID PRIMARY KEY,
    aggregate_id UUID NOT NULL ,
    event_type VARCHAR(255) NOT NULL ,
    payload TEXT NOT NULL,
    status VARCHAR(50) NOT NULL,
    retry_count INTEGER NOT NULL ,
    next_retry_at TIMESTAMP NOT NULL,
    created_at TIMESTAMP NOT NULL,
    updated_at TIMESTAMP NOT NULL
);