create table users
(
    id               serial
        primary key,
    name             varchar,
    email            varchar,
    country          varchar,
    gender           varchar,
    is_fired         boolean,
    pass_id          integer
        constraint fkn962la30qstmkv3hvsyraf5xd
            references passports,
    date_of_birthday date
);

create unique index users_id_uindex
    on users (id);

create table addresses
(
    id                 bigserial
        primary key,
    address_has_active boolean,
    city               varchar(255),
    country            varchar(255),
    street             varchar(255),
    employee_id        integer
        references users
);