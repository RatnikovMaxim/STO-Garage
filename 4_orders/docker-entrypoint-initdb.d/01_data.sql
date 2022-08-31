INSERT INTO orders (user_id, user_name, station_id, station_name, status)
VALUES (1, 'Vasiliy', 2, 'Авто Доктор', 'заброннирован');


INSERT INTO order_positions (order_id, service_id, service_name, price)
VALUES (1, 1, 'Шиномонтаж', 1500);



-- INSERT INTO history (order_number, owner_id,station_id, service_id, status)
-- VALUES (1, 1, 2, 3, 'выполнен');
