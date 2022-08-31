INSERT INTO orders (user_id, user_name, station_id, station_name, status)
VALUES (1, 'Vasiliy', 1, 'Авто Доктор', 'заброннирован'),
       (1, 'Vasiliy', 2, 'Мир Машин', 'заброннирован');


INSERT INTO order_positions (order_id, service_id, service_name, price)
VALUES (1, 1, 'Шиномонтаж', 1500),
       (1, 2, 'ТехОбслуживание', 2000),
       (1, 3, 'Кузовные работы', 2500),
       (2, 4, 'Эвакуатор', 1200),
       (2, 5, 'Слесарные работы', 1700);



-- INSERT INTO history (order_number, owner_id,station_id, service_id, status)
-- VALUES (1, 1, 2, 3, 'выполнен');
