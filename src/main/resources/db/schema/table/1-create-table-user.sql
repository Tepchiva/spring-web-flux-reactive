create table iam_user (
    id bigserial primary key,
    username varchar(255) not null,
    first_name varchar(50) not null,
    last_name varchar(50) not null,
    email varchar(255) not null,
    phone varchar(255),
    salt varchar not null,
    password varchar not null
);