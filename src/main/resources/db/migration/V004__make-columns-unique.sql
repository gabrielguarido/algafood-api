-- Payment method
alter table payment_method modify method varchar(60) not null unique;

-- Profile name
alter table profile modify name varchar(60) not null unique;

-- State name
alter table state modify name varchar(80) not null unique;

-- Category type
alter table category modify type varchar(60) not null unique;

-- Permission role
alter table permission modify role varchar(100) not null unique;

-- User email
alter table user modify email varchar(255) not null unique;
