create database join_here;
use join_here;

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

-- club Table Create SQL
CREATE TABLE club
(
    `id`            BIGINT           NOT NULL    AUTO_INCREMENT,
    `name`          VARCHAR(50)      NOT NULL,
    `category`      CHAR(3)          NOT NULL,
    `area`          CHAR(2)          NOT NULL,
    `logo`          BLOB             NULL,
    `introduction`  VARCHAR(1000)    NULL,
    `view`          BIGINT           NOT NULL,
    `scrap`         BIGINT           NOT NULL,
    PRIMARY KEY (id)
);