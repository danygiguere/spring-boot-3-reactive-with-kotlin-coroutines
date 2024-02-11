CREATE TABLE IF NOT EXISTS users
(
     id bigint NOT NULL AUTO_INCREMENT,
     username varchar(255) NOT NULL DEFAULT '',
     email varchar(255) NOT NULL DEFAULT '',
     phoneNumber varchar(255) NOT NULL DEFAULT '',
     PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS posts
(
     id bigint NOT NULL AUTO_INCREMENT,
     userId bigint NOT NULL,
     title varchar(255) NOT NULL DEFAULT '',
     description varchar(255) NOT NULL DEFAULT '',
     PRIMARY KEY (id)
);