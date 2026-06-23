CREATE TABLE wallets (
    id UUID PRIMARY KEY,
    owner_id VARCHAR(100) NOT NULL,
    balance NUMERIC(19,2) NOT NULL,
    version BIGINT NOT NULL
);