create table cabinets
(
    id         serial
        primary key,
    capacity   integer,
    is_deleted boolean
);

create table users_cabinets
(
    employee_id integer not null
        constraint fkg3uq9dandywli6g3japd5ia26
            references users,
    cabinet_id  integer not null
        constraint fkgx4j90c7lby9iqbh0ei1s7pcf
            references cabinets,
    is_active    boolean,
    primary key (cabinet_id, employee_id)
);