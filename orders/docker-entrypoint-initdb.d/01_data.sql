INSERT INTO orders (owner_id, sto_id, service_id, type)
VALUES (1, 2, 3, 'заброннирован');

INSERT INTO history (order_number, owner_id,sto_id, service_id, type)
VALUES (1, 1, 2, 3, 'выполнен');