CREATE TABLE catalogs_sto (
    id BIGSERIAL PRIMARY KEY,
    name TEXT NOT NULL,
    address TEXT NOT NULL,
    operating_mode TEXT NOT NULL,
    services TEXT NOT NULL,
    telephone INT NOT NULL,
    photo TEXT NOT NULL,
    removed BOOLEAN     NOT NULL DEFAULT FALSE,
    created timestamptz NOT NULL DEFAULT CURRENT_TIMESTAMP
)