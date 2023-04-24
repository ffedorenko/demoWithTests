create table photos
(
    id          serial
        primary key,
    add_date    date,
    bytes       bytea,
    description varchar(255),
    file_name   varchar(255),
    is_deleted  boolean,
    employee_id integer
        constraint fkbxq293jvxh5d4t6qrndovypyt
            references users
);