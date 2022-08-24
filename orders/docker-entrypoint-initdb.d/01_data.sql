INSERT INTO orders (owner_id, station_id, service_id, status)
VALUES (1, 2, 3, 'заброннирован');

INSERT INTO history (order_number, owner_id,station_id, service_id, status)
VALUES (1, 1, 2, 3, 'выполнен');