INSERT INTO stations(name)
VALUES ('Автосеть Гараж'),
       ('Мир Машин'),
       ('Авто Доктор'),
       ('Причал'),
       ('CarService');


INSERT INTO services(name)
VALUES ('Шиномонтаж'),
       ('ТехОбслуживание'),
       ('Кузовные работы'),
       ('Эвакуатор'),
       ('Слесарные работы');


INSERT INTO station_services(station_id, service_id)
VALUES (1,1),
       (1,2),
       (1,3),
       (1,4),
       (1,5),
       (2,1),
       (2,2),
       (2,3),
       (2,4),
       (2,5),
       (3,3),
       (4,4),
       (4,5);