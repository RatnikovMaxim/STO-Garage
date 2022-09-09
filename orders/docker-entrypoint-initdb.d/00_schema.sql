CREATE TABLE orders
(
    id           BIGSERIAL PRIMARY KEY,
    user_id      BIGINT      NOT NULL /*из users*/,
    user_name    TEXT        NOT NULL,
    station_id   BIGINT      NOT NULL /*из Catalog*/,
    station_name TEXT        NOT NULL,
    status       TEXT        NOT NULL, /* "забронирован", "в работе", "завершён", "отменён" */
    notified     BOOLEAN     NOT NULL DEFAULT FALSE,
    removed      BOOLEAN     NOT NULL DEFAULT FALSE,
    created      TIMESTAMPTZ NOT NULL DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE order_positions
(
    id           BIGSERIAL PRIMARY KEY,
    order_id     BIGINT NOT NULL REFERENCES orders,
    service_id   BIGINT NOT NULL /*из Catalog*/,
    service_name TEXT   NOT NULL,
    price        BIGINT NOT NULL DEFAULT 0
);

CREATE TABLE history
(
    id            BIGSERIAL PRIMARY KEY,
    order_number  BIGINT      NOT NULL REFERENCES orders,
    owner_id      BIGINT      NOT NULL /*из users*/,
    station_id    BIGINT      NOT NULL /*из Catalog*/,
    service_id    BIGINT      NOT NUll /*из services*/,
    status        TEXT        NOT NULL, /* "завершён", "отменён" */
    order_created timestamptz NOT NULL DEFAULT CURRENT_TIMESTAMP
);

