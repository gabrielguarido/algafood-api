alter table `order` add external_key varchar(36) not null unique after id;
update `order` set `order`.external_key = uuid();