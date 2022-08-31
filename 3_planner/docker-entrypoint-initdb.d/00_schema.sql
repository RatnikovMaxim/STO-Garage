CREATE TABLE appointments
(
    id                 BIGSERIAL PRIMARY KEY,
    user_id            BIGINT      NOT NULL,
    user_name          TEXT        NOT NULL,
    station_id         BIGINT      NOT NULL,
    station_name       TEXT        NOT NULL,
    time               TIMESTAMPTZ NOT NULL,
    status             TEXT        NOT NULL,
    created            TIMESTAMPTZ NOT NULL DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE appointment_services
(
    id                 BIGSERIAL PRIMARY KEY,
    appointment_id     BIGINT      NOT NUll,
    service_id         BIGINT      NOT NUll,
    service_name       TEXT   NOT NULL
);