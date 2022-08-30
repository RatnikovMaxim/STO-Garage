INSERT INTO stations(name)
VALUES ('Автосеть Гараж'),
       ('Мир Машин'),
       ('Авто Доктор'),
       ('Причал'),
       ('CarService');


INSERT INTO services(name)
VALUES ('Шиномонтаж'),
       ('Техцентр'),
       ('Автосервис'),
       ('Эвакуатор'),
       ('Автомагазин');


INSERT INTO station_services(station_id, service_id)
VALUES (1,1),
       (2,2),
       (3,3),
       (4,4),
       (4,5);