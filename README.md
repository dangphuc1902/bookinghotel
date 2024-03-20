CREATE database booking_hotel;
USE booking_hotel;

CREATE TABLE IF NOT EXISTS roles (
    id INT NOT NULL AUTO_INCREMENT,
    name VARCHAR(225) NOT NULL,
   
    PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS users (
    id INT NOT NULL AUTO_INCREMENT,
    full_name VARCHAR(225) NOT NULL,
    email VARCHAR(225) NOT NULL,
    password VARCHAR(225) NOT NULL,
    role_id INT Not NULL,
   
    PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS room (
    id INT NOT NULL AUTO_INCREMENT,
    room_type VARCHAR(225) NOT NULL,
    room_price DECIMAL(10, 2) NOT NULL,
    image TEXT NOT NULL,
    is_booked BOOLEAN DEFAULT false,
   
    PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS booking (
    id INT NOT NULL AUTO_INCREMENT,
    check_in VARCHAR(225) NOT NULL,
    check_out VARCHAR(225) NOT NULL,
    adults VARCHAR(225) NOT NULL,
    children VARCHAR(225) NOT NULL,
    total_guest VARCHAR(225) NOT NULL,
    confirmation_code VARCHAR(225) NOT NULL,
    user_id INT NOT NULL,
    room_id INT NOT NULL,
   
    PRIMARY KEY (id)
);

ALTER TABLE users ADD CONSTRAINT FK_id_roles_user FOREIGN KEY(role_id) REFERENCES roles(id);
ALTER TABLE booking ADD CONSTRAINT FK_id_user_booking FOREIGN KEY(user_id) REFERENCES users(id);
ALTER TABLE booking ADD CONSTRAINT FK_id_room_booking FOREIGN KEY(room_id) REFERENCES room(id);


INSERT INTO roles( name) VALUES ("ROLE_ADMIN");
INSERT INTO roles( name) VALUES ("ROLE_USER");
