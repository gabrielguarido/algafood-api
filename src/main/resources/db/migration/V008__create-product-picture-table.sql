create table product_picture
(
    product_id   bigint       not null auto_increment,
    file_name    varchar(150) not null,
    content_type varchar(80)  not null,
    file_size    int          not null,

    primary key (product_id),

    constraint fk_product_picture foreign key (product_id) references product (id)
) engine = InnoDB
  default charset = utf8;