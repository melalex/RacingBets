CREATE SCHEMA `horse_racing` DEFAULT CHARACTER SET utf8;

CREATE TABLE `horse_racing`.`jockey` (
  `id` INT UNSIGNED NOT NULL AUTO_INCREMENT,
  `first_name` VARCHAR(45) NOT NULL,
  `last_name` VARCHAR(45) NOT NULL,
  `birthday` DATE,
  PRIMARY KEY (`id`),
  UNIQUE INDEX `id_UNIQUE` (`id` ASC));
  
CREATE TABLE `horse_racing`.`owner` (
  `id` INT UNSIGNED NOT NULL AUTO_INCREMENT,
  `first_name` VARCHAR(45) NOT NULL,
  `last_name` VARCHAR(45) NOT NULL,
  `birthday` DATE,
  PRIMARY KEY (`id`),
  UNIQUE INDEX `id_UNIQUE` (`id` ASC));

CREATE TABLE `horse_racing`.`trainer` (
  `id` INT UNSIGNED NOT NULL AUTO_INCREMENT,
  `first_name` VARCHAR(45) NOT NULL,
  `last_name` VARCHAR(45) NOT NULL,
  `birthday` DATE,
  PRIMARY KEY (`id`),
  UNIQUE INDEX `id_UNIQUE` (`id` ASC));

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
  UNIQUE INDEX `id_UNIQUE` (`id` ASC),
  FOREIGN KEY (trainer_id) REFERENCES trainer(id),
  FOREIGN KEY (owner_id) REFERENCES owner(id),
  FOREIGN KEY (sire_id) REFERENCES horse(id),
  FOREIGN KEY (dam_id) REFERENCES horse(id));

CREATE TABLE `horse_racing`.`racecourse` (
  `id` INT UNSIGNED NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(45) NOT NULL,
  `latitude` DOUBLE NOT NULL,
  `longitude` DOUBLE NOT NULL,
  `contact` VARCHAR(45) NOT NULL,
  `clerk` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE INDEX `id_UNIQUE` (`id` ASC));

CREATE TABLE `horse_racing`.`application_user` (
  `id` INT UNSIGNED NOT NULL AUTO_INCREMENT,
  `login` VARCHAR(45) NOT NULL,
  `password` VARCHAR(45) NOT NULL,
  `first_name` VARCHAR(45) NOT NULL,
  `last_name` VARCHAR(45) NOT NULL,
  `email` VARCHAR(45) NOT NULL,
  `is_email_confirmed` BOOL NOT NULL,
  `balance` DECIMAL(12, 2) UNSIGNED NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE INDEX `id_UNIQUE` (`id` ASC));

CREATE TABLE `horse_racing`.`role` (
  `id` INT UNSIGNED NOT NULL AUTO_INCREMENT,
  `application_user_id` INT UNSIGNED NOT NULL,
  `name` ENUM('Handicapper', 'Bookmaker', 'Admin') NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE INDEX `id_UNIQUE` (`id` ASC),
  FOREIGN KEY (application_user_id) REFERENCES application_user(id) ON DELETE CASCADE);

CREATE TABLE `horse_racing`.`race` (
  `id` INT UNSIGNED NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(45) NOT NULL,
  `status` ENUM('scheduled', 'riding', 'finished', 'rejected') NOT NULL,
  `commission` DOUBLE UNSIGNED NOT NULL,
  `min_bet` DECIMAL(12, 2) UNSIGNED NOT NULL,
  `racecourse_id` INT UNSIGNED NOT NULL,
  `start_date_time` TIMESTAMP NOT NULL,
  `going` ENUM('Hard', 'Firm', 'Good to firm', 'Good', 'Good to soft', 'Soft', 'Heavy', 'Fast', 'Standard to fast', 'Standard', 'Standard to slow', 'Slow') NULL,
  `race_type` ENUM('flat', 'jump', 'harness') NOT NULL,
  `race_class` INT(2) UNSIGNED NOT NULL,
  `min_age` INT(2) UNSIGNED NOT NULL,
  `min_rating` INT(5) UNSIGNED NOT NULL,
  `max_rating` INT(5) UNSIGNED NOT NULL,
  `distance` FLOAT UNSIGNED NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE INDEX `id_UNIQUE` (`id` ASC),
  FOREIGN KEY (racecourse_id) REFERENCES racecourse(id));

CREATE TABLE `horse_racing`.`prize` (
  `id` INT UNSIGNED NOT NULL AUTO_INCREMENT,
  `race_id` INT UNSIGNED NOT NULL,
  `prize_size` DECIMAL(12, 2) UNSIGNED NOT NULL,
  `place` INT(2) UNSIGNED NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE INDEX `id_UNIQUE` (`id` ASC),
  FOREIGN KEY (race_id) REFERENCES race(id) ON DELETE CASCADE);

CREATE TABLE `horse_racing`.`participant` (
  `id` INT UNSIGNED NOT NULL AUTO_INCREMENT,
  `number` INT NOT NULL,
  `horse_id` INT UNSIGNED NOT NULL,
  `race_id` INT UNSIGNED NOT NULL,
  `jockey_id` INT UNSIGNED NOT NULL,
  `trainer_id` INT UNSIGNED NOT NULL,
  `carried_weight` FLOAT UNSIGNED NULL,
  `topspeed` INT(3) UNSIGNED NULL,
  `official_rating` INT(5) UNSIGNED NULL,
  `odds` DOUBLE UNSIGNED NULL,
  `place` INT(2) UNSIGNED NULL,
  PRIMARY KEY (`id`),
  UNIQUE INDEX `id_UNIQUE` (`id` ASC),
  FOREIGN KEY (horse_id) REFERENCES horse(id),
  FOREIGN KEY (race_id) REFERENCES race(id) ON DELETE CASCADE,
  FOREIGN KEY (jockey_id) REFERENCES jockey(id),
  FOREIGN KEY (trainer_id) REFERENCES trainer(id));

CREATE TABLE `horse_racing`.`bet` (
  `id` INT UNSIGNED NOT NULL AUTO_INCREMENT,
  `application_user_id` INT UNSIGNED NOT NULL,
  'race_id' INT UNSIGNED NOT NULL,
  `bet_type` ENUM('Show', 'Place', 'Win', 'Quinella', 'Exacta', 'Trifecta', 'Superfecta') NOT NULL,
  `status` ENUM('scheduled', 'win', 'lose', 'rejected') NOT NULL,
  `bet_size` DECIMAL(12, 2) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE INDEX `id_UNIQUE` (`id` ASC),
  FOREIGN KEY (application_user_id) REFERENCES application_user(id) ON DELETE CASCADE,
  FOREIGN KEY (race_id) REFERENCES race(id) ON DELETE CASCADE);

CREATE TABLE `horse_racing`.`bet_participant` (
  `id` INT UNSIGNED NOT NULL AUTO_INCREMENT,
  `bet_id` INT UNSIGNED NOT NULL,
  `participant_id` INT UNSIGNED NOT NULL,
  `place` INT(2) UNSIGNED NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE INDEX `id_UNIQUE` (`id` ASC),
  FOREIGN KEY (bet_id) REFERENCES bet(id) ON DELETE CASCADE,
  FOREIGN KEY (participant_id) REFERENCES participant(id) ON DELETE CASCADE);

CREATE PROCEDURE add_role(IN application_user_id INT UNSIGNED, IN role_name ENUM('Handicapper', 'Bookmaker', 'Admin'))
  BEGIN
    DECLARE is_exists BOOL;
    SET is_exists = EXISTS(
        SELECT * FROM role WHERE role.application_user_id = application_user_id AND role.name = role_name
    );

    CASE
      WHEN is_exists = FALSE THEN
        INSERT INTO role (application_user_id, name) VALUES (application_user_id, role_name);
    END CASE;
  END;

CREATE PROCEDURE get_odds(
  IN bet_id INT UNSIGNED,
  OUT prize_pool DECIMAL(12, 2),
  OUT event_pool DECIMAL(12, 2),
  OUT commission DOUBLE
)
  BEGIN
    DECLARE type ENUM('Show', 'Place', 'Win', 'Quinella', 'Exacta', 'Trifecta', 'Superfecta');
    SET type = (SELECT bet_type FROM bet WHERE id = bet_id);
    SET prize_pool = (SELECT SUM(bet_size) FROM bet WHERE bet.bet_type = type);
    SET commission = (SELECT commission FROM bet INNER JOIN race ON bet.race_id = race.id WHERE bet.id = bet_id);

    CASE
      WHEN type = 'Exacta' OR type = 'Trifecta' THEN
      SET event_pool = (
        SELECT SUM(bet_size)
        FROM (
               SELECT * FROM bet
               WHERE bet.bet_type = type AND
                     bet.race_id = (SELECT bet.race_id FROM bet WHERE bet.id = bet_id)
             ) AS b1
          LEFT JOIN bet_participant
            ON bet_participant.bet_id = bet.id
        WHERE (bet_participant.participant_id, place) IN (
          SELECT participant_id, place
          FROM bet
            LEFT JOIN bet_participant
              ON bet.id = bet_participant.bet_id
          WHERE bet.bet_type = type AND
                bet.race_id = (SELECT bet.race_id FROM bet WHERE bet.id = bet_id)
        )
        GROUP BY bet.id, place
        HAVING COUNT(*) = (
          SELECT COUNT(*)
          FROM bet
            LEFT JOIN bet_participant
              ON bet.id = bet_participant.bet_id
          WHERE bet.bet_type = type AND
                bet.race_id = (SELECT bet.race_id FROM bet WHERE bet.id = bet_id)
        )
      );
      ELSE
        SET event_pool = (
          SELECT SUM(bet_size)
          FROM (
            SELECT * FROM bet
            WHERE bet.bet_type = type AND
                  bet.race_id = (SELECT bet.race_id FROM bet WHERE bet.id = bet_id)
          ) AS b1
            LEFT JOIN bet_participant
              ON bet_participant.bet_id = bet.id
            WHERE bet_participant.participant_id IN (
              SELECT participant_id
              FROM bet
                LEFT JOIN bet_participant
                  ON bet.id = bet_participant.bet_id
              WHERE bet.bet_type = type AND
                    bet.race_id = (SELECT bet.race_id FROM bet WHERE bet.id = bet_id)
            )
          GROUP BY bet.id
          HAVING COUNT(*) = (
            SELECT COUNT(*)
            FROM bet
              LEFT JOIN bet_participant
                ON bet.id = bet_participant.bet_id
            WHERE bet.bet_type = type AND
                  bet.race_id = (SELECT bet.race_id FROM bet WHERE bet.id = bet_id)
          )
        );
    END CASE;
  END;