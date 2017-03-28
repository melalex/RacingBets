DELIMITER //

CREATE PROCEDURE horse_racing_test.filter_races(
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
    SELECT
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

    FROM (SELECT *
          FROM (SELECT
                  race.id              'id',
                  race.start_date_time 'start_date_time',
                  race.commission      'commission',
                  race.distance        'distance',
                  race.max_rating      'max_rating',
                  race.min_age         'min_age',
                  race.min_bet         'min_bet',
                  race.min_rating      'min_rating',
                  race.name            'name',
                  race.race_class      'race_class',
                  race.race_type       'race_type',
                  race.status          'status',
                  race.going           'going',
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
                AND (p_end IS NULL OR race.start_date_time <= p_end)
          LIMIT p_limit OFFSET p_offset) AS race

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
    UNION
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

    FROM (SELECT *
          FROM (SELECT
                  race.id              'id',
                  race.start_date_time 'start_date_time',
                  race.commission      'commission',
                  race.distance        'distance',
                  race.max_rating      'max_rating',
                  race.min_age         'min_age',
                  race.min_bet         'min_bet',
                  race.min_rating      'min_rating',
                  race.name            'name',
                  race.race_class      'race_class',
                  race.race_type       'race_type',
                  race.status          'status',
                  race.going           'going',
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
                AND (p_end IS NULL OR race.start_date_time <= p_end)
          LIMIT p_limit OFFSET p_offset) AS race
      INNER JOIN racecourse AS racecourse
        ON race.racecourse_id = racecourse.id

      LEFT JOIN prize AS prize
        ON prize.race_id = race.id;
  END //

CREATE PROCEDURE horse_racing_test.count_races(
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