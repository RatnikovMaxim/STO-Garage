CREATE TABLE planner
(
    id                 BIGSERIAL PRIMARY KEY,
    owner_id           BIGINT      NOT NULL,
    sto_id             BIGINT      NOT NULL,
    booking_time       TIMESTAMPTZ NOT NULL,
    canceled           BOOLEAN     NOT NULL DEFAULT FALSE,
    created            TIMESTAMPTZ NOT NULL DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE planner_services
(
    id                 BIGSERIAL PRIMARY KEY,
    planner_id         BIGINT      NOT NUll,
    service_id         BIGINT      NOT NUll
);