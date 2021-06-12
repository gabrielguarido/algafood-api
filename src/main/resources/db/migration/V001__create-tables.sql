create table category
(
    id   bigint      not null auto_increment,
    type varchar(60) not null,
    primary key (id)
) engine = InnoDB
  default charset = utf8;

create table city
(
    id       bigint      not null auto_increment,
    name     varchar(60) not null,
    state_id bigint      not null,
    primary key (id)
) engine = InnoDB
  default charset = utf8;

create table payment_method
(
    id     bigint      not null auto_increment,
    method varchar(60) not null,
    primary key (id)
) engine = InnoDB
  default charset = utf8;

create table permission
(
    id   bigint      not null auto_increment,
    role varchar(60) not null,

    primary key (id)
) engine = InnoDB
  default charset = utf8;

create table product
(
    id            bigint         not null auto_increment,
    active        bit            not null,
    description   varchar(60)    not null,
    name          varchar(60)    not null,
    price         decimal(19, 2) not null,
    restaurant_id bigint         not null,

    primary key (id)
) engine = InnoDB
  default charset = utf8;

create table profile
(
    id   bigint      not null auto_increment,
    name varchar(60) not null,
    primary key (id)
) engine = InnoDB
  default charset = utf8;

create table profile_permission
(
    profile_id    bigint not null,
    permission_id bigint not null
) engine = InnoDB
  default charset = utf8;

create table restaurant
(
    id                 bigint         not null auto_increment,
    address            varchar(60),
    address_complement varchar(60),
    address_number     varchar(60),
    address_province   varchar(60),
    address_zip_code   varchar(60),
    created            datetime(6)    not null,
    name               varchar(60)    not null,
    shipping_tax       decimal(19, 2) not null,
    updated            datetime(6)    not null,
    address_city_id    bigint,
    category_id        bigint         not null,
    primary key (id)
) engine = InnoDB
  default charset = utf8;

create table restaurant_payment_method
(
    restaurant_id     bigint not null,
    payment_method_id bigint not null
) engine = InnoDB
  default charset = utf8;

create table state
(
    id   bigint      not null auto_increment,
    name varchar(60) not null,
    primary key (id)
) engine = InnoDB
  default charset = utf8;

create table user
(
    id       bigint      not null auto_increment,
    created  datetime(6) not null,
    email    varchar(60) not null,
    name     varchar(60) not null,
    password varchar(60) not null,
    primary key (id)
) engine = InnoDB
  default charset = utf8;

create table user_profile
(
    user_id    bigint not null,
    profile_id bigint not null
) engine = InnoDB
  default charset = utf8;

alter table city
    add constraint fk_city_state foreign key (state_id) references state (id);

alter table product
    add constraint fk_product_restaurant foreign key (restaurant_id) references restaurant (id);

alter table profile_permission
    add constraint fk_profile_permission_permission foreign key (permission_id) references permission (id);

alter table profile_permission
    add constraint fk_profile_permission_profile foreign key (profile_id) references profile (id);

alter table restaurant
    add constraint fk_restaurant_address_city foreign key (address_city_id) references city (id);

alter table restaurant
    add constraint fk_restaurant_category foreign key (category_id) references category (id);

alter table restaurant_payment_method
    add constraint fk_restaurant_payment_method_payment_method foreign key (payment_method_id) references payment_method (id);

alter table restaurant_payment_method
    add constraint fk_restaurant_payment_method_restaurant foreign key (restaurant_id) references restaurant (id);

alter table user_profile
    add constraint fk_user_profile_profile foreign key (profile_id) references profile (id);

alter table user_profile
    add constraint fk_user_profile_user foreign key (user_id) references user (id);
