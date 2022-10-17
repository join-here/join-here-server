create database join_here;
use join_here;

-- Create member Table
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

-- Create announcement Table
CREATE TABLE announcement
(
    `id`           BIGINT           NOT NULL    AUTO_INCREMENT,
    `title`        VARCHAR(50)      NOT NULL,
    `description`  VARCHAR(1000)    NOT NULL,
    `poster`       BLOB             NULL,
    `start_date`   DATE             NOT NULL,
    `end_date`     DATE             NOT NULL,
    `club_id`      BIGINT           NOT NULL,
    PRIMARY KEY (id),
    FOREIGN KEY (club_id) references club(id)
);

-- Create belong Table
CREATE TABLE belong
(
    `id`         BIGINT         NOT NULL    AUTO_INCREMENT,
    `position`   VARCHAR(3)     NOT NULL,
    `member_id`  VARCHAR(20)    NOT NULL,
    `club_id`    BIGINT         NOT NULL,
    PRIMARY KEY (id),
    FOREIGN KEY (member_id) references member(id),
    FOREIGN KEY (club_id) references club(id)
);

-- Create question Table
CREATE TABLE question
(
    `id`               BIGINT          NOT NULL    AUTO_INCREMENT,
    `announcement_id`  BIGINT          NOT NULL,
    `content`          VARCHAR(500)    NOT NULL,
    PRIMARY KEY (id),
    FOREIGN KEY (announcement_id) references announcement(id)
);

-- Create answer Table
CREATE TABLE answer
(
    `id`           BIGINT           NOT NULL    AUTO_INCREMENT,
    `content`      VARCHAR(2000)    NOT NULL    DEFAULT 'wait',
    `member_id`    VARCHAR(20)      NOT NULL,
    `question_id`  BIGINT           NOT NULL,
    PRIMARY KEY (id),
    FOREIGN KEY (member_id) references member(id),
    FOREIGN KEY (question_id) references question(id)
);

-- Create application Table
CREATE TABLE application
(
    `id`               BIGINT         NOT NULL    AUTO_INCREMENT,
    `pass_state`       VARCHAR(4)     NOT NULL    DEFAULT 'wait',
    `inform_state`     VARCHAR(1)     NOT NULL    DEFAULT 'n',
    `member_id`        VARCHAR(20)    NOT NULL,
    `announcement_id`  BIGINT         NOT NULL,
    PRIMARY KEY (id),
    FOREIGN KEY (member_id) references member(id),
    FOREIGN KEY (announcement_id) references announcement(id)
);