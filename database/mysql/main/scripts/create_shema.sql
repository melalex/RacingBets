CREATE SCHEMA `horse_racing`
  DEFAULT CHARACTER SET utf8;

CREATE TABLE `horse_racing`.`jockey` (
  `id`         INT UNSIGNED NOT NULL AUTO_INCREMENT,
  `first_name` VARCHAR(45)  NOT NULL,
  `last_name`  VARCHAR(45)  NOT NULL,
  `birthday`   DATE,
  PRIMARY KEY (`id`),
  UNIQUE INDEX `id_UNIQUE` (`id` ASC)
);

CREATE TABLE `horse_racing`.`owner` (
  `id`         INT UNSIGNED NOT NULL AUTO_INCREMENT,
  `first_name` VARCHAR(45)  NOT NULL,
  `last_name`  VARCHAR(45)  NOT NULL,
  `birthday`   DATE,
  PRIMARY KEY (`id`),
  UNIQUE INDEX `id_UNIQUE` (`id` ASC)
);

CREATE TABLE `horse_racing`.`trainer` (
  `id`         INT UNSIGNED NOT NULL AUTO_INCREMENT,
  `first_name` VARCHAR(45)  NOT NULL,
  `last_name`  VARCHAR(45)  NOT NULL,
  `birthday`   DATE,
  PRIMARY KEY (`id`),
  UNIQUE INDEX `id_UNIQUE` (`id` ASC)
);

CREATE TABLE `horse_racing`.`horse` (
  `id`         INT UNSIGNED              NOT NULL AUTO_INCREMENT,
  `name`       VARCHAR(45)               NOT NULL,
  `trainer_id` INT UNSIGNED              NOT NULL,
  `owner_id`   INT UNSIGNED              NOT NULL,
  `birthday`   DATE                      NOT NULL,
  `gender`     ENUM ('mare', 'stallion') NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE INDEX `id_UNIQUE` (`id` ASC),
  FOREIGN KEY (trainer_id) REFERENCES trainer (id),
  FOREIGN KEY (owner_id) REFERENCES owner (id)
);

CREATE TABLE `horse_racing`.`racecourse` (
  `id`        INT UNSIGNED NOT NULL AUTO_INCREMENT,
  `name`      VARCHAR(45)  NOT NULL,
  `latitude`  DOUBLE       NOT NULL,
  `longitude` DOUBLE       NOT NULL,
  `contact`   VARCHAR(45)  NOT NULL,
  `clerk`     VARCHAR(45)  NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE INDEX `id_UNIQUE` (`id` ASC)
);

CREATE TABLE `horse_racing`.`application_user` (
  `id`                 INT UNSIGNED            NOT NULL AUTO_INCREMENT,
  `login`              VARCHAR(45)             NOT NULL,
  `password_hash`      CHAR(128)               NOT NULL,
  `salt`               VARCHAR(45)             NOT NULL,
  `first_name`         VARCHAR(45)             NOT NULL,
  `last_name`          VARCHAR(45)             NOT NULL,
  `email`              VARCHAR(45)             NOT NULL,
  `is_email_confirmed` BOOL                    NOT NULL,
  `balance`            DECIMAL(12, 2) UNSIGNED NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE INDEX `id_UNIQUE` (`id` ASC),
  UNIQUE INDEX `login_UNIQUE` (`login` ASC),
  UNIQUE INDEX `email_UNIQUE` (`email` ASC)
);

CREATE TABLE `horse_racing`.`role` (
  `id`                  INT UNSIGNED                               NOT NULL AUTO_INCREMENT,
  `application_user_id` INT UNSIGNED                               NOT NULL,
  `name`                ENUM ('Handicapper', 'Bookmaker', 'Admin') NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE INDEX `id_UNIQUE` (`id` ASC),
  FOREIGN KEY (application_user_id) REFERENCES application_user (id)
    ON DELETE CASCADE
);

CREATE TABLE `horse_racing`.`race` (
  `id`              INT UNSIGNED                                                                                                                                       NOT NULL AUTO_INCREMENT,
  `name`            VARCHAR(45)                                                                                                                                        NOT NULL,
  `status`          ENUM ('scheduled', 'riding', 'finished', 'rejected')                                                                                               NOT NULL,
  `commission`      DOUBLE UNSIGNED                                                                                                                                    NOT NULL,
  `min_bet`         DECIMAL(12, 2) UNSIGNED                                                                                                                            NOT NULL,
  `racecourse_id`   INT UNSIGNED                                                                                                                                       NOT NULL,
  `start_date_time` TIMESTAMP                                                                                                                                                   DEFAULT CURRENT_TIMESTAMP,
  `going`           ENUM ('Hard', 'Firm', 'Good to firm', 'Good', 'Good to soft', 'Soft', 'Heavy', 'Fast', 'Standard to fast', 'Standard', 'Standard to slow', 'Slow') NULL,
  `race_type`       ENUM ('flat', 'jump', 'harness')                                                                                                                   NOT NULL,
  `race_class`      INT(2) UNSIGNED                                                                                                                                    NOT NULL,
  `min_age`         INT(2) UNSIGNED                                                                                                                                    NOT NULL,
  `min_rating`      INT(5) UNSIGNED                                                                                                                                    NOT NULL,
  `max_rating`      INT(5) UNSIGNED                                                                                                                                    NOT NULL,
  `distance`        FLOAT UNSIGNED                                                                                                                                     NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE INDEX `id_UNIQUE` (`id` ASC),
  FOREIGN KEY (racecourse_id) REFERENCES racecourse (id)
);

CREATE TABLE `horse_racing`.`prize` (
  `id`         INT UNSIGNED            NOT NULL AUTO_INCREMENT,
  `race_id`    INT UNSIGNED            NOT NULL,
  `prize_size` DECIMAL(12, 2) UNSIGNED NOT NULL,
  `place`      INT(2) UNSIGNED         NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE INDEX `id_UNIQUE` (`id` ASC),
  FOREIGN KEY (race_id) REFERENCES race (id)
    ON DELETE CASCADE
);

CREATE TABLE `horse_racing`.`participant` (
  `id`              INT UNSIGNED    NOT NULL AUTO_INCREMENT,
  `number`          INT             NOT NULL,
  `horse_id`        INT UNSIGNED    NOT NULL,
  `race_id`         INT UNSIGNED    NOT NULL,
  `jockey_id`       INT UNSIGNED    NOT NULL,
  `trainer_id`      INT UNSIGNED    NOT NULL,
  `carried_weight`  FLOAT UNSIGNED  NULL,
  `topspeed`        INT(3) UNSIGNED NULL,
  `official_rating` INT(5) UNSIGNED NULL,
  `odds`            DOUBLE UNSIGNED NULL,
  `place`           INT(2) UNSIGNED NULL,
  PRIMARY KEY (`id`),
  UNIQUE INDEX `id_UNIQUE` (`id` ASC),
  FOREIGN KEY (horse_id) REFERENCES horse (id),
  FOREIGN KEY (race_id) REFERENCES race (id)
    ON DELETE CASCADE,
  FOREIGN KEY (jockey_id) REFERENCES jockey (id),
  FOREIGN KEY (trainer_id) REFERENCES trainer (id)
);

CREATE TABLE `horse_racing`.`bet` (
  `id`                  INT UNSIGNED                                                                  NOT NULL AUTO_INCREMENT,
  `application_user_id` INT UNSIGNED                                                                  NOT NULL,
  `race_id`             INT UNSIGNED                                                                  NOT NULL,
  `bet_type`            ENUM ('Show', 'Place', 'Win', 'Quinella', 'Exacta', 'Trifecta', 'Superfecta') NOT NULL,
  `status`              ENUM ('scheduled', 'win', 'lose', 'rejected')                                 NOT NULL,
  `bet_size`            DECIMAL(12, 2)                                                                NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE INDEX `id_UNIQUE` (`id` ASC),
  FOREIGN KEY (application_user_id) REFERENCES application_user (id)
    ON DELETE CASCADE,
  FOREIGN KEY (race_id) REFERENCES race (id)
    ON DELETE CASCADE
);

CREATE TABLE `horse_racing`.`bet_participant` (
  `id`             INT UNSIGNED    NOT NULL AUTO_INCREMENT,
  `bet_id`         INT UNSIGNED    NOT NULL,
  `participant_id` INT UNSIGNED    NOT NULL,
  `place`          INT(2) UNSIGNED NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE INDEX `id_UNIQUE` (`id` ASC),
  FOREIGN KEY (bet_id) REFERENCES bet (id)
    ON DELETE CASCADE,
  FOREIGN KEY (participant_id) REFERENCES participant (id)
    ON DELETE CASCADE
);