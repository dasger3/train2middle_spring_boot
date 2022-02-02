drop table if exists auth_user;

create table auth_user
(
    id       int auto_increment primary key,
    username varchar(255) not null,
    password varchar(255) not null
);

INSERT INTO auth_user
VALUES (1, 'user1', '$2a$10$.czObVBn33y9XMaOqMrZ0uxkjQa4tgW8Ro15uiUTGGZc.tCP68sm2');
INSERT INTO auth_user
VALUES (2, 'user2', '$2a$10$QxADfwAOihZYtstswgjj/ebSyQ6OPrSbz6KqBw9rwoYDwok1xxF9G');
