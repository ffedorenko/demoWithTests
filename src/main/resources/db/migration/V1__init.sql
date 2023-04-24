create table passports
(
    id                   serial
        primary key,
    date_of_birthday     date,
    expire_date          date,
    is_deleted           boolean,
    is_free              boolean,
    name                 varchar(255),
    passport_status      varchar(255),
    serial_number        varchar(255),
    previous_passport_id integer
        constraint fkd2nrq8w4vo1clfc7o6m0jyr5i
            references passports
);

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