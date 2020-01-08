create table companies
(
    id         bigserial    not null
        constraint companies_pk
            primary key,
    name       varchar(100) not null,
    address    varchar(250),
    created_at timestamp    not null,
    created_by varchar(100) not null,
    updated_at timestamp    not null,
    updated_by varchar(100)
);

alter table companies
    owner to qjyxkwvbttpctw;

create unique index companies_company_id_uindex
    on companies (id);

create table users
(
    id            bigserial    not null
        constraint users_pk
            primary key,
    company_id    bigint
        constraint company_id
            references companies
            on delete cascade,
    first_name    varchar(50)  not null,
    last_name     varchar(50)  not null,
    username      varchar(100) not null,
    password      varchar(100) not null,
    phone_number  bigint       not null,
    email_address varchar(250) not null,
    role          varchar(50)  not null,
    created_at    timestamp    not null,
    created_by    varchar(100) not null,
    updated_at    timestamp    not null,
    updated_by    varchar(100) not null
);

alter table users
    owner to qjyxkwvbttpctw;

create unique index users_email_address_uindex
    on users (email_address);

create unique index users_id_uindex
    on users (id);

create unique index users_phone_number_uindex
    on users (phone_number);

create table locations
(
    id           bigserial    not null
        constraint locations_pk
            primary key,
    company_id   bigint       not null
        constraint company_id
            references companies
            on delete cascade,
    name         varchar(150) not null,
    address      varchar(250),
    phone_number bigint,
    created_at   timestamp    not null,
    created_by   varchar(100),
    updated_at   timestamp    not null,
    updated_by   varchar(100) not null
);

alter table locations
    owner to qjyxkwvbttpctw;

create unique index locations_id_uindex
    on locations (id);

create table displays
(
    id                     bigserial    not null
        constraint displays_pk
            primary key,
    location_id            bigint       not null
        constraint location_id
            references locations
            on delete cascade,
    company_id             bigint       not null
        constraint company_id
            references companies
            on delete cascade,
    name                   varchar(100) not null,
    description            text,
    transition_time_millis bigint       not null,
    orientation            varchar(100) not null,
    created_at             timestamp    not null,
    created_by             varchar(100) not null,
    updated_at             timestamp    not null,
    updated_by             varchar(100) not null
);

alter table displays
    owner to qjyxkwvbttpctw;

create unique index displays_id_uindex
    on displays (id);

create table slides
(
    id          bigserial    not null
        constraint slides_pk
            primary key,
    display_id  bigint       not null
        constraint display_id
            references displays
            on delete cascade,
    location_id bigint       not null
        constraint location_id
            references locations
            on delete cascade,
    company_id  bigint       not null
        constraint company_id
            references companies
            on delete cascade,
    name        varchar(150) not null,
    type        varchar(100) not null,
    image_url   text,
    created_at  timestamp    not null,
    created_by  varchar(100) not null,
    updated_at  timestamp    not null,
    updated_by  varchar(100) not null
);

alter table slides
    owner to qjyxkwvbttpctw;

create unique index slides_id_uindex
    on slides (id);

