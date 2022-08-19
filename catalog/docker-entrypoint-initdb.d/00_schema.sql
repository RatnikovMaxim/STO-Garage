CREATE TABLE catalogs_sto (
    id BIGSERIAL PRIMARY KEY,
    name TEXT NOT NULL,
    address TEXT NOT NULL,
    operating_mode TEXT NOT NULL,
    services TEXT NOT NULL,
    telephone TEXT NOT NULL,
    photo TEXT NOT NULL,
    removed BOOLEAN     NOT NULL DEFAULT FALSE,
    created timestamptz NOT NULL DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE services (
    id BIGSERIAL PRIMARY KEY,
    sto_id BIGINT NOT NULL REFERENCES catalogs_sto,
    name_service TEXT NOT NULL,
    created timestamptz NOT NULL DEFAULT CURRENT_TIMESTAMP
);

