
SET FOREIGN_KEY_CHECKS = 0;
DROP TABLE IF EXISTS members CASCADE;
DROP TABLE IF EXISTS theaters CASCADE;
DROP TABLE IF EXISTS seats CASCADE;
DROP TABLE IF EXISTS movies CASCADE;
DROP TABLE IF EXISTS schedules CASCADE;
DROP TABLE IF EXISTS tickets CASCADE;


CREATE TABLE IF NOT EXISTS members (
    member_id INT UNSIGNED NOT NULL AUTO_INCREMENT,
    member_name VARCHAR(20) NOT NULL,
    password VARCHAR(20),
    name VARCHAR(30) NOT NULL,
    role ENUM('ADMIN', 'MANAGER', 'USER') NOT NULL DEFAULT 'USER',
    created_date TIMESTAMP DEFAULT NOW(),
    updated_date TIMESTAMP DEFAULT NOW(),
    CONSTRAINT pk_member PRIMARY KEY (member_id),
    CONSTRAINT uk_member UNIQUE (member_name)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;



CREATE TABLE IF NOT EXISTS theaters (
    theater_id INT UNSIGNED NOT NULL AUTO_INCREMENT,
    seat_total_number INT,
    CONSTRAINT pk_theater PRIMARY KEY (theater_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;



CREATE TABLE IF NOT EXISTS seats (
    theater_id INT UNSIGNED NOT NULL,
    seat_id INT UNSIGNED NOT NULL,
    CONSTRAINT pk_seat PRIMARY KEY (theater_id , seat_id),
    CONSTRAINT fk_seat_theater FOREIGN KEY (theater_id)
        REFERENCES theaters (theater_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;



CREATE TABLE IF NOT EXISTS movies (
    movie_id INT UNSIGNED NOT NULL AUTO_INCREMENT,
    title VARCHAR(50),
    director VARCHAR(30),
    content LONGTEXT,
    actor VARCHAR(255),
    movie_image varchar(255),
    rating FLOAT(4 , 2),
    rating_count INT,
    member_id INT UNSIGNED,
    created_date TIMESTAMP DEFAULT NOW(),
    updated_date TIMESTAMP DEFAULT NOW(),
    CONSTRAINT pk_movie PRIMARY KEY (movie_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

alter table movies add CONSTRAINT fk_movie_member foreign key (member_id) references members (member_id);

CREATE TABLE IF NOT EXISTS member_rating (
    member_id INT UNSIGNED,
    movie_id INT UNSIGNED,
    rating FLOAT(4 , 2),
    CONSTRAINT pk_member_rating PRIMARY KEY (member_id, movie_id),
    CONSTRAINT fk_member_rating_member FOREIGN KEY (member_id)
		REFERENCES members (member_id),
	CONSTRAINT fk_member_rating_movie FOREIGN KEY (movie_id)
		REFERENCES movies (movie_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;



CREATE TABLE IF NOT EXISTS schedules (
    schedule_id INT UNSIGNED NOT NULL AUTO_INCREMENT,
    schedule_date TIMESTAMP,
    movie_id INT UNSIGNED,
    theater_id INT UNSIGNED,
    CONSTRAINT pk_movie PRIMARY KEY (schedule_id),
    CONSTRAINT fk_schedule_movie FOREIGN KEY (movie_id)
        REFERENCES movies (movie_id),
    CONSTRAINT fk_schedule_theater FOREIGN KEY (theater_id)
        REFERENCES theaters (theater_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


CREATE TABLE IF NOT EXISTS booked_seats (
	seat_id INT UNSIGNED NOT NULL,
    theater_id INT UNSIGNED NOT NULL,
    schedule_id INT UNSIGNED NOT NULL,
    CONSTRAINT pk_booked_seat PRIMARY KEY (seat_id, schedule_id),
    CONSTRAINT fk_booked_seat_seat FOREIGN KEY (theater_id , seat_id)
        REFERENCES seats (theater_id , seat_id),
    CONSTRAINT fk_booked_seat_schedule FOREIGN KEY (schedule_id)
        REFERENCES schedules (schedule_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


CREATE TABLE IF NOT EXISTS tickets (
    ticket_id INT UNSIGNED NOT NULL AUTO_INCREMENT,
    schedule_id INT UNSIGNED,
    theater_id INT UNSIGNED,
    movie_id INT UNSIGNED,
    seat_id INT UNSIGNED,
    rank_id INT UNSIGNED DEFAULT 1,
    created_date TIMESTAMP DEFAULT NOW(),
    CONSTRAINT pk_ticket PRIMARY KEY (ticket_id),
    CONSTRAINT fk_ticket_schedule FOREIGN KEY (schedule_id)
		REFERENCES schedules (schedule_id),
	CONSTRAINT fk_ticket_seat FOREIGN KEY (theater_id, seat_id)
		REFERENCES seats (theater_id, seat_id),
	CONSTRAINT fk_ticket_rank FOREIGN KEY (rank_id)
		REFERENCES ranks (rank_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE IF NOT EXISTS ranks (
    rank_id INT UNSIGNED NOT NULL,
    rank_name varchar(10),
    price INT UNSIGNED,
    CONSTRAINT pk_rank PRIMARY KEY (rank_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


CREATE TABLE IF NOT EXISTS orders (
    order_id INT UNSIGNED NOT NULL AUTO_INCREMENT,
    member_id INT UNSIGNED,
    order_date TIMESTAMP DEFAULT NOW(),
    CONSTRAINT pk_order PRIMARY KEY (order_id),
    CONSTRAINT fk_order_member FOREIGN KEY (member_id)
		REFERENCES members (member_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE IF NOT EXISTS order_ticket (
    order_id INT UNSIGNED,
    ticket_id INT UNSIGNED,
    CONSTRAINT pk_order_ticket PRIMARY KEY (order_id, ticket_id),
    CONSTRAINT fk_order_ticket_order FOREIGN KEY (order_id)
		REFERENCES orders (order_id),
	CONSTRAINT fk_order_ticket_ticket FOREIGN KEY (ticket_id)
		REFERENCES tickets (ticket_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;



SET FOREIGN_KEY_CHECKS = 1;



use bajo;
ALTER TABLE tickets DROP COLUMN price;
ALTER TABLE tickets ADD COLUMN rank_id INT UNSIGNED;

CREATE TABLE IF NOT EXISTS ranks (
    rank_id INT UNSIGNED NOT NULL,
    rank_name varchar(10),
    price INT UNSIGNED,
    CONSTRAINT pk_rank PRIMARY KEY (rank_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

ALTER TABLE tickets ADD CONSTRAINT fk_ticket_rank FOREIGN KEY (rank_id) REFERENCES ranks (rank_id);

insert into ranks (rank_id, rank_name, price) values (1, 'NORMAL', 8000); 







