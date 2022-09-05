-- member Table Create SQL
CREATE TABLE member
(
    `id`        VARCHAR(20)    NOT NULL,
    `name`      VARCHAR(10)    NOT NULL,
    `password`  VARCHAR(20)    NOT NULL,
    `birthday`  DATE           NOT NULL,
    `phone`     VARCHAR(20)    NOT NULL,
    PRIMARY KEY (id)
);