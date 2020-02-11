create table cart (
    cart_id bigint not null auto_increment,
    created_date timestamp,
    modified_date timestamp,
    member_id bigint,
    primary key (cart_id)
)

create table cart_item (
    cart_item_id bigint not null auto_increment,
    count integer not null,
    order_price integer not null,
    cart_id bigint,
    item_id bigint,
    created_date timestamp,
    modified_date timestamp,
    primary key (cart_item_id)
)

create table category (
    category_id bigint not null auto_increment,
    created_date timestamp,
    modified_date timestamp,
    name varchar(255),
    parent_id bigint,
    primary key (category_id)
)

create table delivery (
    delivery_id bigint not null auto_increment,
    created_date timestamp,
    modified_date timestamp,
    city varchar(255),
    street varchar(255),
    zipcode varchar(255),
    status varchar(255),
    primary key (delivery_id)
)

create table item (
    item_id bigint not null auto_increment,
    created_date timestamp,
    modified_date timestamp,
    name varchar(255),
    picture varchar(255),
    price integer not null,
    stock_quantity integer not null,
    category_id bigint,
    primary key (item_id)
)

create table member (
    member_id bigint not null auto_increment,
    created_date timestamp,
    modified_date timestamp,
    email varchar(255) not null,
    name varchar(255) not null,
    password varchar(255),
    role varchar(255) not null,
    primary key (member_id)
)

create table order_item (
    order_item_id bigint not null auto_increment,
    count integer not null,
    order_price integer not null,
    item_id bigint,
    order_id bigint,
    primary key (order_item_id)
)

create table orders (
    order_id bigint not null auto_increment,
    created_date timestamp,
    modified_date timestamp,
    order_date timestamp,
    status varchar(255),
    delivery_id bigint,
    member_id bigint,
    primary key (order_id)
)

create table persistent_logins (
    series varchar(255) not null,
    last_used timestamp not null,
    token varchar(255) not null,
    username varchar(255) not null,
    primary key (series)
)

create table posts (
    id bigint not null auto_increment,
    created_date timestamp,
    modified_date timestamp,
    author varchar(255),
    content TEXT not null,
    title varchar(500) not null,
    primary key (id)
)

CREATE TABLE SPRING_SESSION (
	PRIMARY_ID CHAR(36) NOT NULL,
	SESSION_ID CHAR(36) NOT NULL,
	CREATION_TIME BIGINT NOT NULL,
	LAST_ACCESS_TIME BIGINT NOT NULL,
	MAX_INACTIVE_INTERVAL INT NOT NULL,
	EXPIRY_TIME BIGINT NOT NULL,
	PRINCIPAL_NAME VARCHAR(100),
	CONSTRAINT SPRING_SESSION_PK PRIMARY KEY (PRIMARY_ID)
);

CREATE UNIQUE INDEX SPRING_SESSION_IX1 ON SPRING_SESSION (SESSION_ID);
CREATE INDEX SPRING_SESSION_IX2 ON SPRING_SESSION (EXPIRY_TIME);
CREATE INDEX SPRING_SESSION_IX3 ON SPRING_SESSION (PRINCIPAL_NAME);

CREATE TABLE SPRING_SESSION_ATTRIBUTES (
	SESSION_PRIMARY_ID CHAR(36) NOT NULL,
	ATTRIBUTE_NAME VARCHAR(200) NOT NULL,
	ATTRIBUTE_BYTES BLOB NOT NULL,
	CONSTRAINT SPRING_SESSION_ATTRIBUTES_PK PRIMARY KEY (SESSION_PRIMARY_ID, ATTRIBUTE_NAME),
	CONSTRAINT SPRING_SESSION_ATTRIBUTES_FK FOREIGN KEY (SESSION_PRIMARY_ID) REFERENCES SPRING_SESSION(PRIMARY_ID) ON DELETE CASCADE
);

insert into category(name, created_date, modified_date) values ('clothes', now(), now());
insert into category(name, created_date, modified_date, parent_id) values ('top', now(), now(), 1);
insert into category(name, created_date, modified_date, parent_id) values ('bottom', now(), now(), 1);


insert into item(created_date, modified_date, name, picture, price, stock_quantity, category_id) values (now(), now(), 'top item1', '/images/200x100.png', 10, 100, 2);
insert into item(created_date, modified_date, name, picture, price, stock_quantity, category_id) values (now(), now(), 'top item2', '/images/200x100.png', 20, 200, 2);
insert into item(created_date, modified_date, name, picture, price, stock_quantity, category_id) values (now(), now(), 'top item3', '/images/200x100.png', 30, 300, 2);
insert into item(created_date, modified_date, name, picture, price, stock_quantity, category_id) values (now(), now(), 'top item4', '/images/200x100.png', 15, 999, 2);
insert into item(created_date, modified_date, name, picture, price, stock_quantity, category_id) values (now(), now(), 'top item5', '/images/200x100.png', 25, 999, 2);
insert into item(created_date, modified_date, name, picture, price, stock_quantity, category_id) values (now(), now(), 'top item6', '/images/200x100.png', 35, 999, 2);
insert into item(created_date, modified_date, name, picture, price, stock_quantity, category_id) values (now(), now(), 'bottom item1', '/images/200x100.png', 10, 100, 3);
insert into item(created_date, modified_date, name, picture, price, stock_quantity, category_id) values (now(), now(), 'bottom item2', '/images/200x100.png', 20, 200, 3);
insert into item(created_date, modified_date, name, picture, price, stock_quantity, category_id) values (now(), now(), 'bottom item3', '/images/200x100.png', 30, 300, 3);
insert into item(created_date, modified_date, name, picture, price, stock_quantity, category_id) values (now(), now(), 'bottom item4', '/images/200x100.png', 15, 999, 3);
insert into item(created_date, modified_date, name, picture, price, stock_quantity, category_id) values (now(), now(), 'bottom item5', '/images/200x100.png', 25, 999, 3);
insert into item(created_date, modified_date, name, picture, price, stock_quantity, category_id) values (now(), now(), 'bottom item6', '/images/200x100.png', 35, 999, 3);