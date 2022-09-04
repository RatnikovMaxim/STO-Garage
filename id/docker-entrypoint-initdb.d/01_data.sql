INSERT INTO users(login, name, password, station_id)
VALUES ('vasya', 'vasily',
        'P+1oXnLMZ8cTgcvRyWIH/5TmCNb2v+OORjVypxlHRE5ar39DHo03k0N3/IS0YHLc9kQc+dW/zPr4GSHS', 1);

INSERT INTO user_roles(user_id, role)
SELECT id, 'ROLE_USER'
FROM users
WHERE login = 'artem';

INSERT INTO user_roles(user_id, role)
SELECT id, 'ROLE_ADMIN'
FROM users
WHERE login = 'vasya';

INSERT INTO user_roles(user_id, role)
SELECT id, 'ROLE_CATALOG'
FROM users
WHERE login = 'artem';

INSERT INTO user_roles(user_id, role)
SELECT id, 'ROLE_PLANNER'
FROM users
WHERE login = 'artem';

INSERT INTO user_roles(user_id, role)
SELECT id, 'ROLE_ORDER'
FROM users
WHERE login = 'vasya';

INSERT INTO user_roles(user_id, role)
SELECT id, 'ROLE_BONUS'
FROM users
WHERE login = 'vasya';

INSERT INTO user_roles(user_id, role)
SELECT id, 'ROLE_CATALOG'
FROM users
WHERE login = 'amir';

INSERT INTO user_roles(user_id, role)
SELECT id, 'ROLE_PLANNER'
FROM users
WHERE login = 'kostya';

INSERT INTO tokens(token, user_id, expire)
SELECT '',
       id,
       CURRENT_TIMESTAMP + interval '30 days'
FROM users
WHERE login = 'vasya';


