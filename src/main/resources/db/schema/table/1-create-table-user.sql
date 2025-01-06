create table iam_user (
    id bigserial primary key,
    username varchar(255) not null unique,
    first_name varchar(50) not null,
    last_name varchar(50) not null,
    email varchar(255) not null unique,
    phone varchar(255),
    password varchar not null
);