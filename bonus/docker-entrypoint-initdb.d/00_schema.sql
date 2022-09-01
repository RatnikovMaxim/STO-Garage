CREATE TABLE bonuses
(
    id BIGSERIAL PRIMARY KEY,
    client_id BIGINT NOT NULL,
    client_name TEXT NOT NULL,
    order_id BIGINT NOT NULL,
    bonus_sum BIGINT,
    created TIMESTAMPTZ NOT NULL DEFAULT current_timestamp,
    valid_to TIMESTAMPTZ
)