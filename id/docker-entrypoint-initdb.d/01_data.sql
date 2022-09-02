INSERT INTO users(login, name, password)
VALUES ('vasya','vasily', '$argon2id$v=19$m=4096,t=3,p=1$4Ut3Zm2r+ZMyTg39tOiD0w$zw614+sn91FJtHuRvkOS7gZMXpK9RRDF7o/NToGrb84')
;
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
SELECT id, 'ROLE_MESSAGE'
FROM users
WHERE login = 'vasya';

INSERT INTO tokens(token, user_id, expire)
SELECT
    'rzl00lRt66/pTUrCCYSeRdzRaQKfz9ZZV5q63NIafNy4Wn6YvsekXRHeqIXJst+5nQn+HU1iWE0E/PoZ',
    id,
    CURRENT_TIMESTAMP + interval '1 year'
FROM users
WHERE login = 'vasya';


