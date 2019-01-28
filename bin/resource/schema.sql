SET FOREIGN_KEY_CHECKS = 0;

DROP TABLE IF EXISTS members CASCADE;
DROP TABLE IF EXISTS member_rating CASCADE;
DROP TABLE IF EXISTS theaters CASCADE;
DROP TABLE IF EXISTS seats CASCADE;
DROP TABLE IF EXISTS movies CASCADE;
DROP TABLE IF EXISTS schedules CASCADE;
DROP TABLE IF EXISTS booked_seats CASCADE;
DROP TABLE IF EXISTS tickets CASCADE;
DROP TABLE IF EXISTS ranks CASCADE;
DROP TABLE IF EXISTS orders CASCADE;
DROP TABLE IF EXISTS order_ticket CASCADE;


CREATE TABLE IF NOT EXISTS members (
    member_id INT UNSIGNED NOT NULL AUTO_INCREMENT,
    member_name VARCHAR(20) NOT NULL,
    password VARCHAR(20),
    name VARCHAR(30) NOT NULL,
    role ENUM('ADMIN', 'MANAGER', 'USER') NOT NULL DEFAULT 'USER',
    enabled TINYINT(1) NOT NULL DEFAULT 1,
    created_date TIMESTAMP DEFAULT NOW(),
    updated_date TIMESTAMP DEFAULT NOW(),
    CONSTRAINT pk_member PRIMARY KEY (member_id),
    CONSTRAINT uk_member UNIQUE (member_name)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;



CREATE TABLE IF NOT EXISTS theaters (
    theater_id INT UNSIGNED NOT NULL AUTO_INCREMENT,
    floor TINYINT UNSIGNED NOT NULL,
    CONSTRAINT pk_theater PRIMARY KEY (theater_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;



CREATE TABLE IF NOT EXISTS seats (
    theater_id INT UNSIGNED NOT NULL,
    seat_id INT UNSIGNED NOT NULL,
    CONSTRAINT pk_seat PRIMARY KEY (theater_id, seat_id),
    CONSTRAINT fk_seat_theater FOREIGN KEY (theater_id)
        REFERENCES theaters (theater_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;



CREATE TABLE IF NOT EXISTS movies (
    movie_id INT UNSIGNED NOT NULL AUTO_INCREMENT,
    title VARCHAR(50) NOT NULL,
    director VARCHAR(30) NOT NULL,
    content LONGTEXT NOT NULL,
    actor VARCHAR(255) NOT NULL,
    created_date TIMESTAMP DEFAULT NOW(),
    updated_date TIMESTAMP DEFAULT NOW(),
    CONSTRAINT pk_movie PRIMARY KEY (movie_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;



CREATE TABLE IF NOT EXISTS schedules (
    schedule_id INT UNSIGNED NOT NULL AUTO_INCREMENT,
    movie_id INT UNSIGNED NOT NULL,
    theater_id INT UNSIGNED NOT NULL,
    reserved_date DATE NOT NULL,
    start_time TIME NOT NULL,
    end_time TIME NOT NULL,
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
    CONSTRAINT fk_booked_seat_seat FOREIGN KEY (theater_id, seat_id)
        REFERENCES seats (theater_id, seat_id),
    CONSTRAINT fk_booked_seat_schedule FOREIGN KEY (schedule_id)
        REFERENCES schedules (schedule_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


CREATE TABLE IF NOT EXISTS tickets (
    ticket_id INT UNSIGNED NOT NULL AUTO_INCREMENT,
    schedule_id INT UNSIGNED NOT NULL,
    theater_id INT UNSIGNED NOT NULL,
    seat_id INT UNSIGNED NOT NULL,
    rank_id INT UNSIGNED NOT NULL DEFAULT 1,
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
    rank_name VARCHAR(10) NOT NULL,
    price MEDIUMINT UNSIGNED NOT NULL,
    CONSTRAINT pk_rank PRIMARY KEY (rank_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


CREATE TABLE IF NOT EXISTS orders (
    order_id INT UNSIGNED NOT NULL AUTO_INCREMENT,
    member_id INT UNSIGNED NOT NULL,
    total_price INT UNSIGNED NOT NULL,
    order_date TIMESTAMP DEFAULT NOW(),
    CONSTRAINT pk_order PRIMARY KEY (order_id),
    CONSTRAINT fk_order_member FOREIGN KEY (member_id)
		REFERENCES members (member_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


CREATE TABLE IF NOT EXISTS order_ticket (
    order_id INT UNSIGNED NOT NULL,
    ticket_id INT UNSIGNED NOT NULL,
    CONSTRAINT pk_order_ticket PRIMARY KEY (order_id, ticket_id),
    CONSTRAINT fk_order_ticket_order FOREIGN KEY (order_id)
		REFERENCES orders (order_id),
	CONSTRAINT fk_order_ticket_ticket FOREIGN KEY (ticket_id)
		REFERENCES tickets (ticket_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;



SET FOREIGN_KEY_CHECKS = 1;