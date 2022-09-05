INSERT INTO users(login, name, password, station_id)
VALUES ('vasya','vasily',
        '$argon2id$v=19$m=4096,t=3,p=1$4Ut3Zm2r+ZMyTg39tOiD0w$zw614+sn91FJtHuRvkOS7gZMXpK9RRDF7o/NToGrb84', 1)

INSERT INTO user_roles(user_id, role)
SELECT id, 'ROLE_USER'
FROM users
WHERE login = 'vasya';

INSERT INTO user_roles(user_id, role)
SELECT id, 'ROLE_ADMIN'
FROM users
WHERE login = 'vasya';

INSERT INTO user_roles(user_id, role)
SELECT id, 'ROLE_CATALOG'
FROM users
WHERE login = 'vasya';

INSERT INTO user_roles(user_id, role)
SELECT id, 'ROLE_PLANNER'
FROM users
WHERE login = 'vasya';

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


