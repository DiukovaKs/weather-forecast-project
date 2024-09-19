create table cities (
    id serial primary key,
    city varchar(100),
    latitude varchar(20),
    longitude varchar(20)
);

insert into cities (city, latitude, longitude) values
    ('Wellington', '-41,2866', '174,7756'),
    ('Auckland', '-36,8485', '174,7635'),
    ('Rotorua', '-38,1387', '176,2452'),
    ('Petone', '-41,2283', '174,8702'),
    ('Lower Hutt', '-41,2167', '174,9167'),
    ('Upper Hutt', '-41,1383', '175,0502'),
    ('Novosibirsk', '55,0415', '82,9346'),
    ('Christchurch', '-43,5333', '172,6333');

create table chat_to_city
(
    id      bigint  not null
        constraint chat_to_city_pk
            primary key,
    chat_id integer not null,
    city_id bigint  not null
        constraint chat_to_city_cities_id_fk
            references cities
);

alter table chat_to_city
    owner to test;

create sequence chat_to_city_id_seq;

alter sequence chat_to_city_id_seq owner to test;
