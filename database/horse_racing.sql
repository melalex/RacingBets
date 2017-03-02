CREATE SCHEMA `horse_racing` DEFAULT CHARACTER SET utf8;

CREATE TABLE `horse_racing`.`horse` (
  `id` INT UNSIGNED NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(45) NOT NULL,
  `trainer_id` INT UNSIGNED NOT NULL,
  `owner_id` INT UNSIGNED NOT NULL,
  `birthday` DATE NOT NULL,
  `gender` ENUM('mare', 'stallion') NOT NULL,
  `sire_id` INT UNSIGNED NULL,
  `dam_id` INT UNSIGNED NULL,
  PRIMARY KEY (`id`),
  UNIQUE INDEX `id_UNIQUE` (`id` ASC));
  
CREATE TABLE `horse_racing`.`jockey` (
  `id` INT UNSIGNED NOT NULL AUTO_INCREMENT,
  `first_name` VARCHAR(45) NOT NULL,
  `last_name` VARCHAR(45) NOT NULL,
  `birthday` DATE,
  `country_id` INT UNSIGNED,
  PRIMARY KEY (`id`),
  UNIQUE INDEX `id_UNIQUE` (`id` ASC));
  
CREATE TABLE `horse_racing`.`owner` (
  `id` INT UNSIGNED NOT NULL AUTO_INCREMENT,
  `first_name` VARCHAR(45) NOT NULL,
  `last_name` VARCHAR(45) NOT NULL,
  `birthday` DATE,
  `country_id` INT UNSIGNED,
  PRIMARY KEY (`id`),
  UNIQUE INDEX `id_UNIQUE` (`id` ASC));

CREATE TABLE `horse_racing`.`trainer` (
  `id` INT UNSIGNED NOT NULL AUTO_INCREMENT,
  `first_name` VARCHAR(45) NOT NULL,
  `last_name` VARCHAR(45) NOT NULL,
  `birthday` DATE,
  `country_id` INT UNSIGNED,
  UNIQUE INDEX `id_UNIQUE` (`id` ASC));

CREATE TABLE `horse_racing`.`race` (
  `id` INT UNSIGNED NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(45) NOT NULL,
  `status` ENUM('scheduled', 'riding', 'finished', 'rejected') NOT NULL,
  `racecourse_id` INT UNSIGNED NOT NULL,
  `start_date_time` TIMESTAMP NOT NULL,
  `going_id` INT UNSIGNED NOT NULL,
  `race_type` ENUM('flat', 'jump', 'harness') NOT NULL,
  `race_class` INT(2) UNSIGNED NOT NULL,
  `min_age` INT(2) UNSIGNED NOT NULL,
  `min_rating` INT(5) UNSIGNED NOT NULL,
  `max_rating` INT(5) UNSIGNED NOT NULL,
  `distance` FLOAT UNSIGNED NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE INDEX `id_UNIQUE` (`id` ASC));

CREATE TABLE `horse_racing`.`prize` (
  `id` INT UNSIGNED NOT NULL AUTO_INCREMENT,
  `race_id` INT UNSIGNED NOT NULL,
  `price_size` DECIMAL(12, 2) UNSIGNED NOT NULL,
  `place` INT(2) UNSIGNED NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE INDEX `id_UNIQUE` (`id` ASC));

CREATE TABLE `horse_racing`.`racecourse` (
  `id` INT UNSIGNED NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(45) NOT NULL,
  `country_id` INT UNSIGNED NOT NULL,
  `latitude` DOUBLE NOT NULL,
  `longitude` DOUBLE NOT NULL,
  `contact` VARCHAR(45) NOT NULL,
  `clerk` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE INDEX `id_UNIQUE` (`id` ASC));

CREATE TABLE `horse_racing`.`country` (
  `id` INT UNSIGNED NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(45) NOT NULL,
  `code` VARCHAR(3) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE INDEX `id_UNIQUE` (`id` ASC));

CREATE TABLE `horse_racing`.`going` (
  `id` INT UNSIGNED NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE INDEX `id_UNIQUE` (`id` ASC),
  UNIQUE INDEX `name_UNIQUE` (`name` ASC));
  
CREATE TABLE `horse_racing`.`participant` (
  `id` INT UNSIGNED NOT NULL AUTO_INCREMENT,
  `number` INT NOT NULL,
  `horse_id` INT UNSIGNED NOT NULL,
  `rase_id` INT UNSIGNED NOT NULL,
  `carried_weight` FLOAT UNSIGNED NULL,
  `topspeed` INT(3) UNSIGNED NULL,
  `official_rating` INT(5) UNSIGNED NULL,
  `ods` DOUBLE UNSIGNED NULL,
  `jockey_id` INT UNSIGNED NOT NULL,
  `trainer_id` INT UNSIGNED NOT NULL,
  `place` INT(2) UNSIGNED NULL,
  PRIMARY KEY (`id`),
  UNIQUE INDEX `id_UNIQUE` (`id` ASC));

CREATE TABLE `horse_racing`.`bet` (
  `id` INT UNSIGNED NOT NULL AUTO_INCREMENT,
  `application_user_id` INT UNSIGNED NOT NULL,
  `bet_type_id` INT UNSIGNED NOT NULL,
  `status` ENUM('scheduled', 'win', 'lose', 'rejected') NOT NULL,
  `bet_size` DECIMAL(12, 2) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE INDEX `id_UNIQUE` (`id` ASC));
  
  CREATE TABLE `horse_racing`.`bet_type` (
  `id` INT UNSIGNED NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE INDEX `id_UNIQUE` (`id` ASC));

CREATE TABLE `horse_racing`.`application_user` (
  `id` INT UNSIGNED NOT NULL AUTO_INCREMENT,
  `login` VARCHAR(45) NOT NULL,
  `password` VARCHAR(45) NOT NULL,
  `first_name` VARCHAR(45) NOT NULL,
  `last_name` VARCHAR(45) NOT NULL,
  `email` VARCHAR(45) NOT NULL,
  `is_email_confirmed` TINYINT(1) NOT NULL,
  `balance` DECIMAL(12, 2) UNSIGNED NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE INDEX `id_UNIQUE` (`id` ASC));

CREATE TABLE `horse_racing`.`role` (
  `id` INT UNSIGNED NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE INDEX `id_UNIQUE` (`id` ASC),
  UNIQUE INDEX `name_UNIQUE` (`name` ASC));

CREATE TABLE `horse_racing`.`application_user_role` (
  `id` INT UNSIGNED NOT NULL AUTO_INCREMENT,
  `role_id` INT UNSIGNED NOT NULL,
  `application_user_id` INT UNSIGNED NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE INDEX `id_UNIQUE` (`id` ASC));
  
CREATE TABLE `horse_racing`.`bet_participant` (
  `id` INT UNSIGNED NOT NULL AUTO_INCREMENT,
  `race_id` INT UNSIGNED NOT NULL,
  `bet_id` INT UNSIGNED NOT NULL,
  `participant_id` INT UNSIGNED NOT NULL,
  `place` INT(2) UNSIGNED NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE INDEX `id_UNIQUE` (`id` ASC));
  
ALTER TABLE `horse_racing`.`bet_participant` 
ADD INDEX `bet_participant_race_id_fkey_idx` (`race_id` ASC),
ADD INDEX `bet_participant_bet_id_fkey_idx` (`bet_id` ASC),
ADD INDEX `bet_participant_participant_id_fkey_idx` (`participant_id` ASC);
ALTER TABLE `horse_racing`.`bet_participant` 
ADD CONSTRAINT `bet_participant_race_id_fkey`
  FOREIGN KEY (`race_id`)
  REFERENCES `horse_racing`.`race` (`id`)
  ON DELETE CASCADE
  ON UPDATE NO ACTION,
ADD CONSTRAINT `bet_participant_bet_id_fkey`
  FOREIGN KEY (`bet_id`)
  REFERENCES `horse_racing`.`bet` (`id`)
  ON DELETE NO ACTION
  ON UPDATE NO ACTION,
ADD CONSTRAINT `bet_participant_participant_id_fkey`
  FOREIGN KEY (`participant_id`)
  REFERENCES `horse_racing`.`participant` (`id`)
  ON DELETE NO ACTION
  ON UPDATE NO ACTION;

ALTER TABLE `horse_racing`.`application_user_role` 
ADD INDEX `application_user_role_application_user_id_fkey_idx` (`application_user_id` ASC),
ADD INDEX `application_user_role_role_id_fkey_idx` (`role_id` ASC);
ALTER TABLE `horse_racing`.`application_user_role` 
ADD CONSTRAINT `application_user_role_application_user_id_fkey`
  FOREIGN KEY (`application_user_id`)
  REFERENCES `horse_racing`.`application_user` (`id`)
  ON DELETE NO ACTION
  ON UPDATE NO ACTION,
ADD CONSTRAINT `application_user_role_role_id_fkey`
  FOREIGN KEY (`role_id`)
  REFERENCES `horse_racing`.`role` (`id`)
  ON DELETE NO ACTION
  ON UPDATE NO ACTION;

ALTER TABLE `horse_racing`.`bet` 
ADD INDEX `application_user_id_fkey_idx` (`application_user_id` ASC),
ADD INDEX `bet_type_id_fkey_idx` (`bet_type_id` ASC);
ALTER TABLE `horse_racing`.`bet` 
ADD CONSTRAINT `application_user_id_fkey`
  FOREIGN KEY (`application_user_id`)
  REFERENCES `horse_racing`.`application_user` (`id`)
  ON DELETE NO ACTION
  ON UPDATE NO ACTION,
ADD CONSTRAINT `bet_type_id_fkey`
  FOREIGN KEY (`bet_type_id`)
  REFERENCES `horse_racing`.`bet_type` (`id`)
  ON DELETE NO ACTION
  ON UPDATE NO ACTION;
  
ALTER TABLE `horse_racing`.`horse`
ADD INDEX `horse_trainer_id_fkey_idx` (`trainer_id` ASC),
ADD INDEX `horse_owner_id_fkey_idx` (`owner_id` ASC),
ADD INDEX `horse_sire_id_fkey_idx` (`sire_id` ASC),
ADD INDEX `horse_dam_id_fkey_idx` (`dam_id` ASC);
ALTER TABLE `horse_racing`.`horse`
ADD CONSTRAINT `horse_trainer_id_fkey`
  FOREIGN KEY (`trainer_id`)
  REFERENCES `horse_racing`.`trainer` (`id`)
  ON DELETE NO ACTION
  ON UPDATE NO ACTION,
ADD CONSTRAINT `horse_owner_id_fkey`
  FOREIGN KEY (`owner_id`)
  REFERENCES `horse_racing`.`owner` (`id`)
  ON DELETE NO ACTION
  ON UPDATE NO ACTION,
ADD CONSTRAINT `horse_sire_id_fkey`
  FOREIGN KEY (`sire_id`)
  REFERENCES `horse_racing`.`horse` (`id`)
  ON DELETE NO ACTION
  ON UPDATE NO ACTION,
ADD CONSTRAINT `horse_dam_id_fkey`
  FOREIGN KEY (`dam_id`)
  REFERENCES `horse_racing`.`horse` (`id`)
  ON DELETE NO ACTION
  ON UPDATE NO ACTION;
  
ALTER TABLE `horse_racing`.`participant` 
ADD INDEX `participant_horse_id_fkey_idx` (`horse_id` ASC),
ADD INDEX `participant_rase_id_fkey_idx` (`rase_id` ASC),
ADD INDEX `participant_jockey_id_fkey_idx` (`jockey_id` ASC),
ADD INDEX `participant_trainer_id_fkey_idx` (`trainer_id` ASC);
ALTER TABLE `horse_racing`.`participant` 
ADD CONSTRAINT `participant_horse_id_fkey`
  FOREIGN KEY (`horse_id`)
  REFERENCES `horse_racing`.`horse` (`id`)
  ON DELETE NO ACTION
  ON UPDATE NO ACTION,
ADD CONSTRAINT `participant_rase_id_fkey`
  FOREIGN KEY (`rase_id`)
  REFERENCES `horse_racing`.`race` (`id`)
  ON DELETE CASCADE
  ON UPDATE NO ACTION,
ADD CONSTRAINT `participant_jockey_id_fkey`
  FOREIGN KEY (`jockey_id`)
  REFERENCES `horse_racing`.`jockey` (`id`)
  ON DELETE NO ACTION
  ON UPDATE NO ACTION,
ADD CONSTRAINT `participant_trainer_id_fkey`
  FOREIGN KEY (`trainer_id`)
  REFERENCES `horse_racing`.`trainer` (`id`)
  ON DELETE NO ACTION
  ON UPDATE NO ACTION;

ALTER TABLE `horse_racing`.`race` 
ADD INDEX `race_racecourse_id_fkey_idx` (`racecourse_id` ASC),
ADD INDEX `race_going_id_fkey_idx` (`going_id` ASC);
ALTER TABLE `horse_racing`.`race` 
ADD CONSTRAINT `race_racecourse_id_fkey`
  FOREIGN KEY (`racecourse_id`)
  REFERENCES `horse_racing`.`racecourse` (`id`)
  ON DELETE NO ACTION
  ON UPDATE NO ACTION,
ADD CONSTRAINT `race_going_id_fkey`
  FOREIGN KEY (`going_id`)
  REFERENCES `horse_racing`.`going` (`id`)
  ON DELETE NO ACTION
  ON UPDATE NO ACTION;

ALTER TABLE `horse_racing`.`prize`
ADD INDEX `race_price_race_id_fkey_idx` (`race_id` ASC);
ALTER TABLE `horse_racing`.`prize`
ADD CONSTRAINT `race_price_racecourse_id_fkey`
  FOREIGN KEY (`race_id`)
  REFERENCES `horse_racing`.`race` (`id`)
  ON DELETE NO ACTION
  ON UPDATE NO ACTION;

ALTER TABLE `horse_racing`.`racecourse` 
ADD INDEX `racecourse_country_id_fkey_idx` (`country_id` ASC);
ALTER TABLE `horse_racing`.`racecourse` 
ADD CONSTRAINT `racecourse_country_id_fkey`
  FOREIGN KEY (`country_id`)
  REFERENCES `horse_racing`.`country` (`id`)
  ON DELETE NO ACTION
  ON UPDATE NO ACTION;
  
ALTER TABLE `horse_racing`.`jockey` 
ADD INDEX `jockey_country_id_fkey_idx` (`country_id` ASC);
ALTER TABLE `horse_racing`.`jockey` 
ADD CONSTRAINT `jockey_country_id_fkey`
  FOREIGN KEY (`country_id`)
  REFERENCES `horse_racing`.`country` (`id`)
  ON DELETE NO ACTION
  ON UPDATE NO ACTION;
  
ALTER TABLE `horse_racing`.`owner` 
ADD INDEX `owner_country_id_fkey_idx` (`country_id` ASC);
ALTER TABLE `horse_racing`.`owner` 
ADD CONSTRAINT `owner_country_id_fkey`
  FOREIGN KEY (`country_id`)
  REFERENCES `horse_racing`.`country` (`id`)
  ON DELETE NO ACTION
  ON UPDATE NO ACTION;
  
ALTER TABLE `horse_racing`.`trainer` 
ADD INDEX `trainer_country_id_fkey_idx` (`country_id` ASC);
ALTER TABLE `horse_racing`.`trainer` 
ADD CONSTRAINT `trainer_country_id_fkey`
  FOREIGN KEY (`country_id`)
  REFERENCES `horse_racing`.`country` (`id`)
  ON DELETE NO ACTION
  ON UPDATE NO ACTION;

INSERT INTO horse_racing.going (name) VALUES ('Hard');
INSERT INTO horse_racing.going (name) VALUES ('Firm');
INSERT INTO horse_racing.going (name) VALUES ('Good to firm');
INSERT INTO horse_racing.going (name) VALUES ('Good');
INSERT INTO horse_racing.going (name) VALUES ('Good to soft');
INSERT INTO horse_racing.going (name) VALUES ('Soft');
INSERT INTO horse_racing.going (name) VALUES ('Heavy');
INSERT INTO horse_racing.going (name) VALUES ('Fast');
INSERT INTO horse_racing.going (name) VALUES ('Standard to fast');
INSERT INTO horse_racing.going (name) VALUES ('Standard');
INSERT INTO horse_racing.going (name) VALUES ('Standard to slow');
INSERT INTO horse_racing.going (name) VALUES ('Slow');
 
INSERT INTO horse_racing.role (name) VALUES ('Handicapper');
INSERT INTO horse_racing.role (name) VALUES ('Bookmaker');
INSERT INTO horse_racing.role (name) VALUES ('Admin');

INSERT INTO horse_racing.bet_type (name) VALUES ('Show');
INSERT INTO horse_racing.bet_type (name) VALUES ('Place');
INSERT INTO horse_racing.bet_type (name) VALUES ('Win');
INSERT INTO horse_racing.bet_type (name) VALUES ('Quinella');
INSERT INTO horse_racing.bet_type (name) VALUES ('Exacta');
INSERT INTO horse_racing.bet_type (name) VALUES ('Trifecta');
INSERT INTO horse_racing.bet_type (name) VALUES ('Superfecta');
INSERT INTO horse_racing.bet_type (name) VALUES ('Daily Double');
