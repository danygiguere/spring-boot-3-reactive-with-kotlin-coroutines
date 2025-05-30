CREATE TABLE IF NOT EXISTS users
(
     id bigint NOT NULL AUTO_INCREMENT,
     username varchar(255) NOT NULL DEFAULT '',
     email varchar(255) NOT NULL DEFAULT '',
     phoneNumber varchar(255) NOT NULL DEFAULT '',
     createdAt datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
     updatedAt datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
     PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS posts
(
     id bigint NOT NULL AUTO_INCREMENT,
     userId bigint NOT NULL,
     title varchar(255) NOT NULL DEFAULT '',
     description varchar(1000) NOT NULL DEFAULT '',
     createdAt datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
     updatedAt datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
     PRIMARY KEY (id),
     FOREIGN KEY (userId)
     REFERENCES users(id)
     ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS images
(
    id bigint NOT NULL AUTO_INCREMENT,
    postId bigint NOT NULL,
    url varchar(255) NOT NULL DEFAULT '',
    PRIMARY KEY (id),
    FOREIGN KEY (postId)
    REFERENCES posts(id)
    ON DELETE CASCADE
);