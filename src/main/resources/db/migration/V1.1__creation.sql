drop table if exists film;

create table film
(
    id          int auto_increment primary key,
    name        varchar(255) not null,
    description varchar(255) not null,
    genre       varchar(255) not null,
    budget       float not null
);

INSERT INTO film VALUES (1, 'Fight club', 'What is first rule of fight club?', 'thriller', 111.1);
INSERT INTO film VALUES (2, 'The Intern', 'Cool film with Robert De Niro', 'comedy-drama', 222.2);
INSERT INTO film VALUES (3, 'Shutter island', 'Cool film with Leonardo DiCaprio', 'psychological thriller', 333.3);
