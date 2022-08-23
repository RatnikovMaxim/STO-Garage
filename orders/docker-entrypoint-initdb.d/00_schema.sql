CREATE TABLE orders
(
    id         BIGSERIAL PRIMARY KEY,
    owner_id   BIGINT      NOT NULL /*из users*/,
    sto_id     BIGINT      NOT NULL /*из Catalog*/,
    service_id BIGINT      NOT NUll /*из services*/,
    status       TEXT        NOT NULL, /* "заброннирован", "в работе", "выполнен" */
    removed    BOOLEAN     NOT NULL DEFAULT FALSE,
    created    TIMESTAMPTZ NOT NULL DEFAULT CURRENT_TIMESTAMP
);
CREATE TABLE history
(
    id            BIGSERIAL PRIMARY KEY,
    order_number  BIGINT      NOT NULL REFERENCES orders,
    owner_id      BIGINT      NOT NULL /*из users*/,
    sto_id        BIGINT      NOT NULL /*из Catalog*/,
    service_id    BIGINT      NOT NUll /*из services*/,
    status          TEXT        NOT NULL, /* "выполнен" */
    order_created timestamptz NOT NULL DEFAULT CURRENT_TIMESTAMP
);
