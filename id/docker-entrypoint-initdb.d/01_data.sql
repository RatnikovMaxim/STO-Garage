INSERT INTO users(login, name, password, station_id)
VALUES ('vasya', 'vasily',
        '$argon2id$v=19$m=4096,t=3,p=1$4Ut3Zm2r+ZMyTg39tOiD0w$zw614+sn91FJtHuRvkOS7gZMXpK9RRDF7o/NToGrb84', 0);

INSERT INTO users(login, name, password, station_id)
VALUES ('catalog', 'srvcatalog',
        '$argon2id$v=19$m=4096,t=3,p=1$tGQY9ig6XElABSQQiCLHuw$RhmtCzuc6RfkCOXGOmswug0a33wA/yx+5CHJdlrx0A4', 0),
       ('planner', 'srvplanner',
        '$argon2id$v=19$m=4096,t=3,p=1$HZ0mfABQEiVNg9J+bZ2hqg$agoZY85SmslLd1OxuC+1jyeo+uD7E5ZxiveGpdGzUHM', 0),
       ('order', 'srvorder',
        '$argon2id$v=19$m=4096,t=3,p=1$HZ0mfABQEiVNg9J+bZ2hqg$agoZY85SmslLd1OxuC+1jyeo+uD7E5ZxiveGpdGzUHM', 0),
       ('message', 'srvmessage',
        '$argon2id$v=19$m=4096,t=3,p=1$eSDC2PXFZrKCrkUCPkS1/w$eSXQJRztwqp8Jaj5PNOvlp1L/3/psUv9Lue/TMFGj5g', 0),
       ('bonus', 'srvbonus',
        '$argon2id$v=19$m=4096,t=3,p=1$CKiiEoYH90+nEQ2kyWo56g$migzxNT7yiqEMYMPJWlepGmyn8IPfhsqFdBhUnG7rfE', 0);


INSERT INTO user_roles(user_id, role)
SELECT id, 'ROLE_ADMIN'
FROM users
WHERE login = 'vasya';

INSERT INTO user_roles(user_id, role)
SELECT id, 'ROLE_USER'
FROM users
WHERE login = 'vasya';



INSERT INTO user_roles(user_id, role)
SELECT id, 'ROLE_ADMIN'
FROM users
WHERE login = 'catalog';

INSERT INTO user_roles(user_id, role)
SELECT id, 'ROLE_ADMIN'
FROM users
WHERE login = 'planner';

INSERT INTO user_roles(user_id, role)
SELECT id, 'ROLE_ADMIN'
FROM users
WHERE login = 'order';

INSERT INTO user_roles(user_id, role)
SELECT id, 'ROLE_ADMIN'
FROM users
WHERE login = 'message';

INSERT INTO user_roles(user_id, role)
SELECT id, 'ROLE_ADMIN'
FROM users
WHERE login = 'bonus';


INSERT INTO tokens(token, user_id, expire)
SELECT '3RzWEHevIsBCEgEezxXOL+iBMcTQOwAEODEajnP8mxHT0Blhag7Cvjaszui6QW1ZXiVXwILIeGjF2PLV',
       id,
       CURRENT_TIMESTAMP + interval '1 year'
FROM users
WHERE login = 'vasya';

INSERT INTO tokens(token, user_id, expire)
SELECT 'EoE1aUFpFeKgrdSV7EemJGSFgV80uKIjyh0Ae/b7n/uLNpT3wiXfQKFsxuIVeNI4fMmagalmIA0bWw2O',
       id,
       CURRENT_TIMESTAMP + interval '1 year'
FROM users
WHERE login = 'catalog';

INSERT INTO tokens(token, user_id, expire)
SELECT '9MjlBV5WGqJjyw8GIIwSa+zOyGPGgnjk7blA8M+3Yj6+3pa/QvhQnNecR5f0THmabYZ9wPgXUr7HDL0p',
       id,
       CURRENT_TIMESTAMP + interval '1 year'
FROM users
WHERE login = 'planner';

INSERT INTO tokens(token, user_id, expire)
SELECT 'HTeKVh6KGB5GalPWjJvgdpqK4RDtByBsYxPO7bUXzXUv2YurhxO8WG0nM+tvUD8qnzkYSLXW807SvVkh',
       id,
       CURRENT_TIMESTAMP + interval '1 year'
FROM users
WHERE login = 'order';

INSERT INTO tokens(token, user_id, expire)
SELECT 'Pqpwan7mrukncea3f+E9aw/1JOsI3VvB7th5plbRAsoIYlz2xUEX/+fdy3y08+i6eX6rd8MfZ3tpT0k7',
       id,
       CURRENT_TIMESTAMP + interval '1 year'
FROM users
WHERE login = 'message';

INSERT INTO tokens(token, user_id, expire)
SELECT 'fUFxBd3m3efUa2UZmRGpCgxUJyL1CZKLiG2AcCifFUMkPKiiHdYTxOXiyapTqTXgnQJlVkus0YaVeE2g',
       id,
       CURRENT_TIMESTAMP + interval '1 year'
FROM users
WHERE login = 'bonus';


