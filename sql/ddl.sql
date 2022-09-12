create database join_here;
use join_here;

-- Create member Table
drop table member if exists member;
CREATE TABLE member
(
    `id`        VARCHAR(20)    NOT NULL,
    `name`      VARCHAR(10)    NOT NULL,
    `password`  VARCHAR(20)    NOT NULL,
    `birthday`  DATE           NOT NULL,
    `phone`     VARCHAR(20)    NOT NULL,
    PRIMARY KEY (id)
);

-- Create club Table
drop table  if exists club;
CREATE TABLE club
(
    `id`            BIGINT           NOT NULL    AUTO_INCREMENT,
    `name`          VARCHAR(50)      NOT NULL,
    `category`      CHAR(3)          NOT NULL,
    `area`          CHAR(2)          NOT NULL,
    `image`         BLOB            NULL,
    `introduction`  VARCHAR(1000)    NOT NULL,
    `view`          BIGINT           NOT NULL,
    `scrap`         BIGINT           NOT NULL,
    PRIMARY KEY (id)
);