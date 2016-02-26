# --- Created by Slick DDL
# To stop Slick DDL generation, remove this comment and start using Evolutions

# --- !Ups
CREATE TABLE user_status (
    id BIGSERIAL NOT NULL,
    code varchar(99) NOT NULL,
    name VARCHAR(255) NOT NULL,
    PRIMARY KEY(id)
);

CREATE TABLE users (
    id BIGSERIAL NOT NULL,
    name VARCHAR(255) not null,
    surname varchar(255) NOT NULL,
    status_id BIGINT REFERENCES user_status(id),
    date_created TIMESTAMP NOT NULL,
    date_deleted TIMESTAMP,
    PRIMARY KEY(id)
);

INSERT INTO user_status (id, code, name) VALUES (1, 'new', 'New');
INSERT INTO user_status (id, code, name) VALUES (2, 'active', 'Active');
INSERT INTO user_status (id, code, name) VALUES (3, 'active', 'Inactive');

INSERT INTO users (name, surname, status_id, date_created) VALUES ('John', 'Brown', 1, current_timestamp);
INSERT INTO users (name, surname, status_id, date_created) VALUES ('Abrahams', 'Lincoln', 2, current_timestamp);
INSERT INTO users (name, surname, status_id, date_created) VALUES ('Krišjānis', 'Barons', 2, current_timestamp);
INSERT INTO users (name, surname, status_id, date_created) VALUES ('David', 'Beckham', 3, current_timestamp);
INSERT INTO users (name, surname, status_id, date_created) VALUES ('Paris', 'Hilton', 1, current_timestamp);
INSERT INTO users (name, surname, status_id, date_created) VALUES ('Kobe', 'Bryant', 2, current_timestamp);
INSERT INTO users (name, surname, status_id, date_created) VALUES ('David', 'Letterman', 2, current_timestamp);
INSERT INTO users (name, surname, status_id, date_created) VALUES ('Arnold', 'Schwarzenegger', 2, current_timestamp);
INSERT INTO users (name, surname, status_id, date_created) VALUES ('Ingrid', 'Bergman', 2, current_timestamp);
INSERT INTO users (name, surname, status_id, date_created) VALUES ('Bart', 'Simpson', 2, current_timestamp);
INSERT INTO users (name, surname, status_id, date_created) VALUES ('Greta', 'Garbo', 2, current_timestamp);
INSERT INTO users (name, surname, status_id, date_created) VALUES ('Alicia', 'Vikander', 2, current_timestamp);
INSERT INTO users (name, surname, status_id, date_created) VALUES ('Liam', 'Neeson', 2, current_timestamp);
INSERT INTO users (name, surname, status_id, date_created) VALUES ('Renārs', 'Kaupers', 2, current_timestamp);
INSERT INTO users (name, surname, status_id, date_created) VALUES ('Ennio', 'Morricone', 2, current_timestamp);
INSERT INTO users (name, surname, status_id, date_created) VALUES ('Göran', 'Hägglund', 3, current_timestamp);

# --- !Downs

drop table user_status;

drop table users;

