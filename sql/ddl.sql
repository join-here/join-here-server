create database join_here;
use join_here;

-- Create member Table
CREATE TABLE member
(
    `id`            BIGINT          NOT NULL    AUTO_INCREMENT,
    `username`      VARCHAR(20)     NOT NULL,
    `password`      VARCHAR(255)    NOT NULL,
    `name`          VARCHAR(20)     NOT NULL,
    `birthday`      DATE            NOT NULL,
    `phone`         VARCHAR(20)     NOT NULL,
    `authority`     VARCHAR(20)     NOT NULL    DEFAULT 'ROLE_USER',
    `created_at`    TIMESTAMP       NOT NULL,
    `updated_at`    TIMESTAMP       NOT NULL,
    PRIMARY KEY (id)
);

-- Create refresh_token Table
CREATE TABLE refresh_token
(
    `id`           VARCHAR(20)         NOT NULL,
    `value`         VARCHAR(255)        NOT NULL,
    PRIMARY KEY (id)
);

-- Create club Table
CREATE TABLE club
(
    `id`            BIGINT           NOT NULL    AUTO_INCREMENT,
    `name`          VARCHAR(50)      NOT NULL,
    `category`      CHAR(10)         NOT NULL,
    `area`          CHAR(2)          NOT NULL,
    `image_url`     VARCHAR(2083)    NULL,
    `introduction`  VARCHAR(1000)    NOT NULL,
    `view`          BIGINT           NOT NULL    DEFAULT 0,
    `created_at`    TIMESTAMP        NOT NULL,
    `updated_at`    TIMESTAMP        NOT NULL,
    PRIMARY KEY (id)
);

-- Create announcement Table
CREATE TABLE announcement
(
    `id`           BIGINT           NOT NULL    AUTO_INCREMENT,
    `title`        VARCHAR(50)      NOT NULL,
    `description`  VARCHAR(1000)    NOT NULL,
    `image_url`    VARCHAR(2083)    NULL,
    `start_date`   DATE             NOT NULL,
    `end_date`     DATE             NOT NULL,
    `inform_state` TINYINT(1)       NOT NULL    DEFAULT 0,
    `created_at`   TIMESTAMP        NOT NULL,
    `updated_at`   TIMESTAMP        NOT NULL,
    `club_id`      BIGINT           NOT NULL,
    PRIMARY KEY (id),
    FOREIGN KEY (club_id) references club(id)
);

-- Create belong Table
CREATE TABLE belong
(
    `id`                    BIGINT         NOT NULL    AUTO_INCREMENT,
    `position`              VARCHAR(9)     NOT NULL    DEFAULT 'NORMAL',
    `review`                VARCHAR(1000)  NULL,
    `review_created_at`     TIMESTAMP      NULL,
    `created_at`            TIMESTAMP      NOT NULL,
    `updated_at`            TIMESTAMP      NOT NULL,
    `member_id`             BIGINT         NOT NULL,
    `club_id`               BIGINT         NOT NULL,
    PRIMARY KEY (id),
    FOREIGN KEY (member_id) references member(id),
    FOREIGN KEY (club_id) references club(id)
);

-- Create application Table
CREATE TABLE application
(
    `id`               BIGINT         NOT NULL    AUTO_INCREMENT,
    `pass_state`       VARCHAR(4)     NOT NULL    DEFAULT 'HOLD',
    `created_at`       TIMESTAMP      NOT NULL,
    `updated_at`       TIMESTAMP      NOT NULL,
    `member_id`        BIGINT         NOT NULL,
    `announcement_id`  BIGINT         NOT NULL,
    PRIMARY KEY (id),
    FOREIGN KEY (member_id) references member(id),
    FOREIGN KEY (announcement_id) references announcement(id)
);

-- Create announcement_question Table
CREATE TABLE announcement_question
(
    `id`               BIGINT          NOT NULL    AUTO_INCREMENT,
    `content`          VARCHAR(500)    NOT NULL,
    `created_at`       TIMESTAMP       NOT NULL,
    `updated_at`       TIMESTAMP       NOT NULL,
    `announcement_id`  BIGINT          NOT NULL,
    PRIMARY KEY (id),
    FOREIGN KEY (announcement_id) references announcement(id)
);

-- Create application_answer Table
CREATE TABLE application_answer
(
    `id`                        BIGINT           NOT NULL    AUTO_INCREMENT,
    `content`                   VARCHAR(2000)    NOT NULL,
    `created_at`                TIMESTAMP        NOT NULL,
    `updated_at`                TIMESTAMP        NOT NULL,
    `application_id`            BIGINT           NOT NULL,
    `announcement_question_id`  BIGINT           NOT NULL,
    PRIMARY KEY (id),
    FOREIGN KEY (application_id) references application(id),
    FOREIGN KEY (announcement_question_id) references announcement_question(id)
);

-- Create qna_question Table
CREATE TABLE qna_question
(
    `id`                BIGINT           NOT NULL    AUTO_INCREMENT,
    `content`           VARCHAR(1000)    NOT NULL,
    `created_at`        TIMESTAMP        NOT NULL,
    `updated_at`        TIMESTAMP        NOT NULL,
    `member_id`         BIGINT           NOT NULL,
    `club_id`           BIGINT           NOT NULL,
    PRIMARY KEY (id),
    FOREIGN KEY (member_id) references member(id),
    FOREIGN KEY (club_id) references club(id)
);

-- Create qna_answer Table
CREATE TABLE qna_answer
(
    `id`               BIGINT           NOT NULL    AUTO_INCREMENT,
    `content`          VARCHAR(1000)    NOT NULL,
    `is_manager`       TINYINT(1)       NOT NULL,
    `created_at`       TIMESTAMP        NOT NULL,
    `updated_at`       TIMESTAMP        NOT NULL,
    `member_id`        BIGINT           NOT NULL,
    `qna_question_id`  BIGINT           NOT NULL,
    PRIMARY KEY (id),
    FOREIGN KEY (member_id) references member(id),
    FOREIGN KEY (qna_question_id) references qna_question(id)
);