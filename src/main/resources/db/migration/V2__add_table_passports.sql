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