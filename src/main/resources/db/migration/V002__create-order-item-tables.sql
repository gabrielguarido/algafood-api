create table `order`
(
    id                 bigint         not null auto_increment,
    subtotal           decimal(10, 2) not null,
    delivery_fee       decimal(10, 2) not null,
    total_price        decimal(10, 2) not null,

    restaurant_id      bigint         not null,
    user_client_id     bigint         not null,
    payment_method_id  bigint         not null,

    address_city       bigint(20)     not null,
    address_zip_code   varchar(9)     not null,
    address            varchar(100)   not null,
    address_number     varchar(20)    not null,
    address_complement varchar(60)    null,
    address_province   varchar(60)    not null,

    status             varchar(10)    not null,
    created            datetime       not null,
    confirmation_date  datetime       null,
    cancellation_date  datetime       null,
    delivery_date      datetime       null,

    primary key (id),

    constraint fk_order_restaurant foreign key (restaurant_id) references restaurant (id),
    constraint fk_order_user_client foreign key (user_client_id) references user (id),
    constraint fk_order_payment_method foreign key (payment_method_id) references payment_method (id)
) engine = InnoDB
  default charset = utf8;

create table order_item
(
    id          bigint         not null auto_increment,
    amount      smallint(6)    not null,
    unit_price  decimal(10, 2) not null,
    total_price decimal(10, 2) not null,
    observation varchar(255)   null,
    order_id    bigint         not null,
    product_id  bigint         not null,

    primary key (id),
    unique key uk_order_item_product (order_id, product_id),

    constraint fk_order_item_order foreign key (order_id) references `order` (id),
    constraint fk_order_item_product foreign key (product_id) references product (id)
) engine = InnoDB
  default charset = utf8;