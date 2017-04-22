CREATE SCHEMA `horse_racing`
  DEFAULT CHARACTER SET utf8;

USE horse_racing;

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

DELIMITER //

CREATE PROCEDURE horse_racing.fix_race_result(
  IN id_of_race   INT UNSIGNED,
  IN participant1 INT UNSIGNED,
  IN participant2 INT UNSIGNED,
  IN participant3 INT UNSIGNED,
  IN participant4 INT UNSIGNED
)
  BEGIN
    DECLARE type_of_bet ENUM ('Show', 'Place', 'Win', 'Quinella', 'Exacta', 'Trifecta', 'Superfecta');
    SET type_of_bet = 'Win';

    UPDATE bet
      LEFT JOIN bet_participant
        ON bet.id = bet_participant.bet_id
    SET bet.status = IF(
        bet_participant.participant_id = participant1,
        'win',
        'lose'
    )
    WHERE bet.bet_type = type_of_bet AND
          bet.race_id = id_of_race;

    SET type_of_bet = 'Place';

    UPDATE bet
      LEFT JOIN bet_participant
        ON bet.id = bet_participant.bet_id
    SET bet.status = IF(
        bet_participant.participant_id = participant1 OR
        bet_participant.participant_id = participant2,
        'win',
        'lose'
    )
    WHERE bet.bet_type = type_of_bet AND
          bet.race_id = id_of_race;

    SET type_of_bet = 'Show';

    UPDATE bet
      LEFT JOIN bet_participant
        ON bet.id = bet_participant.bet_id
    SET bet.status = IF(
        bet_participant.participant_id = participant1 OR
        bet_participant.participant_id = participant2 OR
        bet_participant.participant_id = participant3,
        'win',
        'lose'
    )
    WHERE bet.bet_type = type_of_bet AND
          bet.race_id = id_of_race;

    SET type_of_bet = 'Quinella';

    UPDATE bet
    SET bet.status = IF(
        EXISTS(SELECT *
               FROM (SELECT bet.id
                     FROM bet AS in_bet
                       LEFT JOIN bet_participant
                         ON bet_participant.bet_id = in_bet.id
                     WHERE bet_participant.participant_id IN (participant1, participant2)
                     GROUP BY in_bet.id
                     HAVING COUNT(*) = 2
                    ) AS winners
               WHERE winners.id = bet.id),
        'win',
        'lose'
    )
    WHERE bet.bet_type = type_of_bet AND
          bet.race_id = id_of_race;

    SET type_of_bet = 'Exacta';

    UPDATE bet
    SET bet.status = IF(
        EXISTS(SELECT *
               FROM (SELECT bet.id
                     FROM (
                            SELECT *
                            FROM bet
                            WHERE bet.bet_type = type_of_bet AND
                                  bet.race_id = id_of_race
                          ) AS bet
                       LEFT JOIN bet_participant
                         ON bet_participant.bet_id = bet.id
                     WHERE (bet_participant.participant_id = participant1 AND bet_participant.place = 1) OR
                           (bet_participant.participant_id = participant2 AND bet_participant.place = 2)
                     GROUP BY bet.id
                     HAVING COUNT(*) = 2
                    ) AS winners
               WHERE winners.id = bet.id),
        'win',
        'lose'
    )
    WHERE bet.bet_type = type_of_bet AND
          bet.race_id = id_of_race;


    SET type_of_bet = 'Trifecta';

    UPDATE bet
    SET bet.status = IF(
        EXISTS(SELECT *
               FROM (SELECT bet.id
                     FROM (
                            SELECT *
                            FROM bet
                            WHERE bet.bet_type = type_of_bet AND
                                  bet.race_id = id_of_race
                          ) AS bet
                       LEFT JOIN bet_participant
                         ON bet_participant.bet_id = bet.id
                     WHERE (bet_participant.participant_id = participant1 AND bet_participant.place = 1) OR
                           (bet_participant.participant_id = participant2 AND bet_participant.place = 2) OR
                           (bet_participant.participant_id = participant3 AND bet_participant.place = 3)
                     GROUP BY bet.id
                     HAVING COUNT(*) = 3
                    ) AS winners
               WHERE winners.id = bet.id),
        'win',
        'lose'
    )
    WHERE bet.bet_type = type_of_bet AND
          bet.race_id = id_of_race;

    SET type_of_bet = 'Superfecta';

    UPDATE bet
    SET bet.status = IF(
        EXISTS(SELECT *
               FROM (SELECT bet.id
                     FROM (
                            SELECT *
                            FROM bet
                            WHERE bet.bet_type = type_of_bet AND
                                  bet.race_id = id_of_race
                          ) AS bet
                       LEFT JOIN bet_participant
                         ON bet_participant.bet_id = bet.id
                     WHERE bet_participant.participant_id IN (participant1, participant2, participant3, participant4)
                     GROUP BY bet.id
                     HAVING COUNT(*) = 4
                    ) AS winners
               WHERE winners.id = bet.id),
        'win',
        'lose'
    )
    WHERE bet.bet_type = type_of_bet AND
          bet.race_id = id_of_race;

  END;
//

CREATE PROCEDURE horse_racing.get_odds_for_win(
  IN  id_of_race      INT UNSIGNED,
  IN  participant     INT UNSIGNED,
  OUT prize_pool      DECIMAL(12, 2),
  OUT event_pool      DECIMAL(12, 2),
  OUT race_commission DOUBLE
)
  BEGIN
    DECLARE type_of_bet ENUM ('Show', 'Place', 'Win', 'Quinella', 'Exacta', 'Trifecta', 'Superfecta');
    SET type_of_bet = 'Win';

    SELECT SUM(bet_size)
    INTO prize_pool
    FROM bet
    WHERE bet.bet_type = type_of_bet AND bet.race_id = id_of_race;

    SELECT race.commission
    INTO race_commission
    FROM race
    WHERE race.id = id_of_race;

    SET event_pool = (
      SELECT SUM(bet_size)
      FROM (
             SELECT *
             FROM bet
             WHERE bet.bet_type = type_of_bet AND
                   bet.race_id = id_of_race
           ) AS bet
        LEFT JOIN bet_participant
          ON bet_participant.bet_id = bet.id
      WHERE bet_participant.participant_id = participant AND
            bet_participant.place = 1
    );
  END;
//

CREATE PROCEDURE horse_racing.get_odds_for_place(
  IN  id_of_race      INT UNSIGNED,
  IN  participant     INT UNSIGNED,
  OUT prize_pool      DECIMAL(12, 2),
  OUT event_pool      DECIMAL(12, 2),
  OUT race_commission DOUBLE
)
  BEGIN
    DECLARE type_of_bet ENUM ('Show', 'Place', 'Win', 'Quinella', 'Exacta', 'Trifecta', 'Superfecta');
    SET type_of_bet = 'Place';

    SELECT SUM(bet_size)
    INTO prize_pool
    FROM bet
    WHERE bet.bet_type = type_of_bet AND bet.race_id = id_of_race;

    SELECT race.commission
    INTO race_commission
    FROM race
    WHERE race.id = id_of_race;

    SET event_pool = (
      SELECT (SUM(bet_size) / 2)
      FROM (
             SELECT *
             FROM bet
             WHERE bet.bet_type = type_of_bet AND
                   bet.race_id = id_of_race
           ) AS bet
        LEFT JOIN bet_participant
          ON bet_participant.bet_id = bet.id
      WHERE bet_participant.participant_id = participant AND
            (bet_participant.place = 1 OR bet_participant.place = 2)
    );
  END;
//

CREATE PROCEDURE horse_racing.get_odds_for_show(
  IN  id_of_race      INT UNSIGNED,
  IN  participant     INT UNSIGNED,
  OUT prize_pool      DECIMAL(12, 2),
  OUT event_pool      DECIMAL(12, 2),
  OUT race_commission DOUBLE
)
  BEGIN
    DECLARE type_of_bet ENUM ('Show', 'Place', 'Win', 'Quinella', 'Exacta', 'Trifecta', 'Superfecta');
    SET type_of_bet = 'Show';

    SELECT SUM(bet_size)
    INTO prize_pool
    FROM bet
    WHERE bet.bet_type = type_of_bet AND bet.race_id = id_of_race;

    SELECT race.commission
    INTO race_commission
    FROM race
    WHERE race.id = id_of_race;

    SET event_pool = (
      SELECT (SUM(bet_size) / 3)
      FROM (
             SELECT *
             FROM bet
             WHERE bet.bet_type = type_of_bet AND
                   bet.race_id = id_of_race
           ) AS bet
        LEFT JOIN bet_participant
          ON bet_participant.bet_id = bet.id
      WHERE bet_participant.participant_id = participant AND
            (bet_participant.place = 1 OR bet_participant.place = 2 OR bet_participant.place = 3)
    );
  END;
//

CREATE PROCEDURE horse_racing.get_odds_for_quinella(
  IN  id_of_race      INT UNSIGNED,
  IN  participant1    INT UNSIGNED,
  IN  participant2    INT UNSIGNED,
  OUT prize_pool      DECIMAL(12, 2),
  OUT event_pool      DECIMAL(12, 2),
  OUT race_commission DOUBLE
)
  BEGIN
    DECLARE type_of_bet ENUM ('Show', 'Place', 'Win', 'Quinella', 'Exacta', 'Trifecta', 'Superfecta');
    SET type_of_bet = 'Quinella';

    SELECT SUM(bet_size)
    INTO prize_pool
    FROM bet
    WHERE bet.bet_type = type_of_bet AND bet.race_id = id_of_race;

    SELECT race.commission
    INTO race_commission
    FROM race
    WHERE race.id = id_of_race;

    SELECT sum(bet_size)
    INTO event_pool
    FROM (SELECT bet.bet_size
          FROM (
                 SELECT *
                 FROM bet
                 WHERE bet.bet_type = type_of_bet AND
                       bet.race_id = id_of_race
               ) AS bet
            LEFT JOIN bet_participant
              ON bet_participant.bet_id = bet.id
          WHERE bet_participant.participant_id IN (participant1, participant2)
          GROUP BY bet.id
          HAVING COUNT(*) = 2
         ) AS bet_sizes;
  END;
//

CREATE PROCEDURE horse_racing.get_odds_for_exacta(
  IN  id_of_race      INT UNSIGNED,
  IN  participant1    INT UNSIGNED,
  IN  participant2    INT UNSIGNED,
  OUT prize_pool      DECIMAL(12, 2),
  OUT event_pool      DECIMAL(12, 2),
  OUT race_commission DOUBLE
)
  BEGIN
    DECLARE type_of_bet ENUM ('Show', 'Place', 'Win', 'Quinella', 'Exacta', 'Trifecta', 'Superfecta');
    SET type_of_bet = 'Exacta';

    SELECT SUM(bet_size)
    INTO prize_pool
    FROM bet
    WHERE bet.bet_type = type_of_bet AND bet.race_id = id_of_race;

    SELECT race.commission
    INTO race_commission
    FROM race
    WHERE race.id = id_of_race;

    SELECT sum(bet_size)
    INTO event_pool
    FROM (SELECT bet.bet_size
          FROM (
                 SELECT *
                 FROM bet
                 WHERE bet.bet_type = type_of_bet AND
                       bet.race_id = id_of_race
               ) AS bet
            LEFT JOIN bet_participant
              ON bet_participant.bet_id = bet.id
          WHERE (bet_participant.participant_id = participant1 AND bet_participant.place = 1) OR
                (bet_participant.participant_id = participant2 AND bet_participant.place = 2)
          GROUP BY bet.id
          HAVING COUNT(*) = 2
         ) AS bet_sizes;
  END;
//

CREATE PROCEDURE horse_racing.get_odds_for_trifecta(
  IN  id_of_race      INT UNSIGNED,
  IN  participant1    INT UNSIGNED,
  IN  participant2    INT UNSIGNED,
  IN  participant3    INT UNSIGNED,
  OUT prize_pool      DECIMAL(12, 2),
  OUT event_pool      DECIMAL(12, 2),
  OUT race_commission DOUBLE
)
  BEGIN
    DECLARE type_of_bet ENUM ('Show', 'Place', 'Win', 'Quinella', 'Exacta', 'Trifecta', 'Superfecta');
    SET type_of_bet = 'Trifecta';

    SELECT SUM(bet_size)
    INTO prize_pool
    FROM bet
    WHERE bet.bet_type = type_of_bet AND bet.race_id = id_of_race;

    SELECT race.commission
    INTO race_commission
    FROM race
    WHERE race.id = id_of_race;

    SELECT sum(bet_size)
    INTO event_pool
    FROM (SELECT bet.bet_size
          FROM (
                 SELECT *
                 FROM bet
                 WHERE bet.bet_type = type_of_bet AND
                       bet.race_id = id_of_race
               ) AS bet
            LEFT JOIN bet_participant
              ON bet_participant.bet_id = bet.id
          WHERE (bet_participant.participant_id = participant1 AND bet_participant.place = 1) OR
                (bet_participant.participant_id = participant2 AND bet_participant.place = 2) OR
                (bet_participant.participant_id = participant3 AND bet_participant.place = 3)
          GROUP BY bet.id
          HAVING COUNT(*) = 3
         ) AS bet_sizes;
  END;
//

CREATE PROCEDURE horse_racing.get_odds_for_superfecta(
  IN  id_of_race      INT UNSIGNED,
  IN  participant1    INT UNSIGNED,
  IN  participant2    INT UNSIGNED,
  IN  participant3    INT UNSIGNED,
  IN  participant4    INT UNSIGNED,
  OUT prize_pool      DECIMAL(12, 2),
  OUT event_pool      DECIMAL(12, 2),
  OUT race_commission DOUBLE
)
  BEGIN
    DECLARE type_of_bet ENUM ('Show', 'Place', 'Win', 'Quinella', 'Exacta', 'Trifecta', 'Superfecta');
    SET type_of_bet = 'Superfecta';

    SELECT SUM(bet_size)
    INTO prize_pool
    FROM bet
    WHERE bet.bet_type = type_of_bet AND bet.race_id = id_of_race;

    SELECT race.commission
    INTO race_commission
    FROM race
    WHERE race.id = id_of_race;

    SELECT sum(bet_size)
    INTO event_pool
    FROM (SELECT bet.bet_size
          FROM (
                 SELECT *
                 FROM bet
                 WHERE bet.bet_type = type_of_bet AND
                       bet.race_id = id_of_race
               ) AS bet
            LEFT JOIN bet_participant
              ON bet_participant.bet_id = bet.id
          WHERE bet_participant.participant_id IN (participant1, participant2, participant3, participant4)
          GROUP BY bet.id
          HAVING COUNT(*) = 4
         ) AS bet_sizes;
  END;
//

CREATE PROCEDURE horse_racing.filter_races(
  IN p_race_status   ENUM ('scheduled', 'riding', 'finished', 'rejected'),
  IN p_id            INT UNSIGNED,
  IN p_racecourse_id INT UNSIGNED,
  IN p_horse_id      INT UNSIGNED,
  IN p_trainer_id    INT UNSIGNED,
  IN p_jockey_id     INT UNSIGNED,
  IN p_name          VARCHAR(45),
  IN p_begin         TIMESTAMP,
  IN p_end           TIMESTAMP,
  IN p_limit         INT UNSIGNED,
  IN p_offset        INT UNSIGNED
)
  BEGIN
    SELECT *
    FROM (
           (SELECT
              race.id                     'race.id',
              race.start_date_time        'race.start_date_time',
              race.commission             'race.commission',
              race.distance               'race.distance',
              race.max_rating             'race.max_rating',
              race.min_age                'race.min_age',
              race.min_bet                'race.min_bet',
              race.min_rating             'race.min_rating',
              race.name                   'race.name',
              race.race_class             'race.race_class',
              race.race_type              'race.race_type',
              race.status                 'race.status',
              race.going                  'race.going',
              racecourse.id               'racecourse.id',
              racecourse.name             'racecourse.name',
              racecourse.latitude         'racecourse.latitude',
              racecourse.longitude        'racecourse.longitude',
              racecourse.clerk            'racecourse.clerk',
              racecourse.contact          'racecourse.contact',
              participant.id              'participant.id',
              participant.number          'participant.number',
              participant.carried_weight  'participant.carried_weight',
              participant.topspeed        'participant.topspeed',
              participant.official_rating 'participant.official_rating',
              participant.odds            'participant.odds',
              participant.place           'participant.place',
              trainer.id                  'trainer.id',
              trainer.first_name          'trainer.first_name',
              trainer.last_name           'trainer.last_name',
              trainer.birthday            'trainer.birthday',
              jockey.id                   'jockey.id',
              jockey.first_name           'jockey.first_name',
              jockey.last_name            'jockey.last_name',
              jockey.birthday             'jockey.birthday',
              horse.id                    'horse.id',
              horse.name                  'horse.name',
              horse.birthday              'horse.birthday',
              horse.gender                'horse.gender',
              horse_owner.id              'horse_owner.id',
              horse_owner.first_name      'horse_owner.first_name',
              horse_owner.last_name       'horse_owner.last_name',
              horse_owner.birthday        'horse_owner.birthday',
              horse_trainer.id            'horse_trainer.id',
              horse_trainer.first_name    'horse_trainer.first_name',
              horse_trainer.last_name     'horse_trainer.last_name',
              horse_trainer.birthday      'horse_trainer.birthday',
              NULL                        'prize.place',
              NULL                        'prize.prize_size'

            FROM race

              INNER JOIN racecourse AS racecourse
                ON race.racecourse_id = racecourse.id
              LEFT JOIN participant AS participant
                ON race.id = participant.race_id
              INNER JOIN jockey AS jockey
                ON participant.jockey_id = jockey.id
              INNER JOIN trainer AS trainer
                ON participant.trainer_id = trainer.id
              INNER JOIN horse AS horse
                ON participant.horse_id = horse.id
              INNER JOIN owner AS horse_owner
                ON horse.owner_id = horse_owner.id
              INNER JOIN trainer AS horse_trainer
                ON horse.trainer_id = horse_trainer.id
           )
           UNION (
             SELECT
               race.id,
               race.start_date_time,
               race.commission,
               race.distance,
               race.max_rating,
               race.min_age,
               race.min_bet,
               race.min_rating,
               race.name,
               race.race_class,
               race.race_type,
               race.status,
               race.going,
               racecourse.id,
               racecourse.name,
               racecourse.latitude,
               racecourse.longitude,
               racecourse.clerk,
               racecourse.contact,
               NULL,
               NULL,
               NULL,
               NULL,
               NULL,
               NULL,
               NULL,
               NULL,
               NULL,
               NULL,
               NULL,
               NULL,
               NULL,
               NULL,
               NULL,
               NULL,
               NULL,
               NULL,
               NULL,
               NULL,
               NULL,
               NULL,
               NULL,
               NULL,
               NULL,
               NULL,
               NULL,
               prize.place,
               prize.prize_size

             FROM race
               INNER JOIN racecourse AS racecourse
                 ON race.racecourse_id = racecourse.id

               LEFT JOIN prize AS prize
                 ON prize.race_id = race.id
           )
         ) AS r
    WHERE `race.id` IN (
      SELECT *
      FROM (
             SELECT race.id
             FROM (SELECT *
                   FROM race
                   WHERE (p_race_status IS NULL OR race.status = p_race_status)
                         AND (p_id IS NULL OR race.id = p_id)
                         AND (p_racecourse_id IS NULL OR race.racecourse_id = p_racecourse_id)
                         AND (p_name IS NULL OR race.name LIKE p_name)
                         AND (p_begin IS NULL OR race.start_date_time >= p_begin)
                         AND (p_end IS NULL OR race.start_date_time <= p_end)) AS race

               LEFT JOIN participant
                 ON participant.race_id = race.id

             WHERE (p_horse_id IS NULL OR participant.horse_id = p_horse_id)
                   AND (p_trainer_id IS NULL OR participant.trainer_id = p_trainer_id)
                   AND (p_jockey_id IS NULL OR participant.jockey_id = p_jockey_id)

             GROUP BY race.id
             ORDER BY race.start_date_time DESC
             LIMIT p_limit OFFSET p_offset
           ) AS t
    )
    ORDER BY `race.start_date_time` DESC;
  END //

CREATE PROCEDURE horse_racing.count_races(
  IN  p_race_status   ENUM ('scheduled', 'riding', 'finished', 'rejected'),
  IN  p_id            INT UNSIGNED,
  IN  p_racecourse_id INT UNSIGNED,
  IN  p_horse_id      INT UNSIGNED,
  IN  p_trainer_id    INT UNSIGNED,
  IN  p_jockey_id     INT UNSIGNED,
  IN  p_name          VARCHAR(45),
  IN  p_begin         TIMESTAMP,
  IN  p_end           TIMESTAMP,
  OUT count           INT UNSIGNED
)
  BEGIN
    SELECT COUNT(*)
    INTO count
    FROM (SELECT
            race.id              'id',
            race.start_date_time 'start_date_time',
            race.name            'name',
            race.status          'status',
            race.racecourse_id   'racecourse_id'

          FROM participant
            INNER JOIN race
              ON participant.race_id = race.id
          WHERE (p_horse_id IS NULL OR participant.horse_id = p_horse_id)
                AND (p_trainer_id IS NULL OR participant.trainer_id = p_trainer_id)
                AND (p_jockey_id IS NULL OR participant.jockey_id = p_jockey_id)
          GROUP BY race.id
         ) AS race
    WHERE (p_race_status IS NULL OR race.status = p_race_status)
          AND (p_id IS NULL OR race.id = p_id)
          AND (p_racecourse_id IS NULL OR race.racecourse_id = p_racecourse_id)
          AND (p_name IS NULL OR race.name LIKE p_name)
          AND (p_begin IS NULL OR race.start_date_time >= p_begin)
          AND (p_end IS NULL OR race.start_date_time <= p_end);
  END //