create database bookshop;

use bookshop;

create table members
(
	memberid char(16) not null primary key,
	password char(255) not null,
	email char(100) not null,
	name char(60) not null,
	address char(80) not null,
	phone char(30) not null,
	created_at timestamp not null default current_timestamp,
	updated_at timestamp not null default current_timestamp
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

create table books
(
	bookid int unsigned not null auto_increment primary key,
	image char(100),
	author char(80),
	title char(100),
	catname char(60),
	price int not null,
	pub_date date not null,
	description varchar(255),
	created_at timestamp not null default current_timestamp,
	updated_at timestamp not null default current_timestamp
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
alter table books auto_increment = 1000;

create table orders
(
	orderid int unsigned not null auto_increment primary key,
	memberid char(16) not null,
	amount int,
	date date not null,
	order_status char(10),
	ship_name char(60) not null,
	ship_address char(80) not null,
	ship_phone char(30) not null,
	created_at timestamp not null default current_timestamp,
	updated_at timestamp not null default current_timestamp,

	foreign key (memberid) references members(memberid)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

create table order_items
(
	orderid int unsigned not null,
	bookid int unsigned not null,
	item_price int not null,
	quantity tinyint unsigned not null,
	created_at timestamp not null default current_timestamp,
	updated_at timestamp not null default current_timestamp,
	primary key (orderid, bookid),

	foreign key (orderid) references orders(orderid),
	foreign key (bookid) references books(bookid)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

create table accounts
(
	orderid int unsigned not null,
	memberid char(16) not null,
	bank char(50) not null,
	account char(100) not null,
	name char(60) not null,
	created_at timestamp not null default current_timestamp,
	updated_at timestamp not null default current_timestamp,
	primary key (orderid, memberid),

	foreign key (orderid) references orders(orderid),
	foreign key (memberid) references members(memberid)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;