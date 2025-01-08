create schema if not exists car_showroom;

create table if not exists car_showroom.categories
(
    uuid uuid primary key,
    body varchar(30) not null
);

create table if not exists car_showroom.cars
(
    uuid  uuid primary key,
    model varchar(50),
    make  varchar(50),
    year  int,
    price numeric
);

create table if not exists car_showroom.car_showrooms
(
    uuid    uuid primary key,
    name    varchar(100) not null,
    address varchar(255)
);

create table if not exists car_showroom.clients
(
    uuid              uuid primary key,
    fullName          varchar(100),
    registration_date date
);

create table if not exists car_showroom.reviews
(
    uuid   uuid primary key,
    review text not null,
    rating int
);

alter table car_showroom.reviews
    add car_uuid uuid references car_showroom.cars (uuid);

alter table car_showroom.cars
    add car_showroom_uuid uuid references car_showroom.car_showrooms (uuid);

alter table car_showroom.categories
    add car_uuid uuid references car_showroom.cars (uuid);

alter table car_showroom.reviews
    add client_uuid uuid references car_showroom.clients (uuid);


create table car_showroom.client_cars
(
    client_uuid uuid references car_showroom.clients,
    car_uuid    uuid references car_showroom.cars,
    primary key (client_uuid, car_uuid)
);

create table car_showroom.client_contacts
(
    client_uuid uuid,
    contact     varchar(255)
);
