CREATE TABLE IF NOT EXISTS images
(
    id bigint NOT NULL AUTO_INCREMENT,
    postId bigint NOT NULL,
    url varchar(255) NOT NULL DEFAULT '',
    PRIMARY KEY (id)
);