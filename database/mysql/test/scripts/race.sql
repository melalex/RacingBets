DELIMITER //

CREATE FUNCTION horse_racing_test.get_races(sql_statement VARCHAR(255))
  RETURNS TEXT
  BEGIN
    RETURN CONCAT('SELECT race.id \'race.id\', race.start_date_time \'race.start_date_time\', race.commission \'race.commission\', race.distance \'race.distance\', race.max_rating \'race.max_rating\', race.min_age \'race.min_age\', race.min_bet \'race.min_bet\', race.min_rating \'race.min_rating\', race.name \'race.name\', race.race_class \'race.race_class\', race.race_type \'race.race_type\', race.status \'race.status\', race.going \'race.going\', racecourse.id \'racecourse.id\', racecourse.name \'racecourse.name\', racecourse.latitude \'racecourse.latitude\', racecourse.longitude \'racecourse.longitude\', racecourse.clerk \'racecourse.clerk\', racecourse.contact \'racecourse.contact\', participant.id \'participant.id\', participant.number \'participant.number\', participant.carried_weight \'participant.carried_weight\', participant.topspeed \'participant.topspeed\', participant.official_rating \'participant.official_rating\', participant.odds \'participant.odds\', participant.place \'participant.place\', trainer.id \'trainer.id\', trainer.first_name \'trainer.first_name\', trainer.last_name \'trainer.last_name\', trainer.birthday \'trainer.birthday\', jockey.id \'jockey.id\', jockey.first_name \'jockey.first_name\', jockey.last_name \'jockey.last_name\', jockey.birthday \'jockey.birthday\', horse.id \'horse.id\', horse.name \'horse.name\', horse.birthday \'horse.birthday\', horse.gender \'horse.gender\', horse.sire_id \'horse.sire_id\', horse.dam_id \'horse.dam_id\', horse_owner.id \'horse_owner.id\', horse_owner.first_name \'horse_owner.first_name\', horse_owner.last_name \'horse_owner.last_name\', horse_owner.birthday \'horse_owner.birthday\', horse_trainer.id \'horse_trainer.id\', horse_trainer.first_name \'horse_trainer.first_name\', horse_trainer.last_name \'horse_trainer.last_name\', horse_trainer.birthday \'horse_trainer.birthday\', NULL AS \'prize.place\', NULL AS \'prize.prize_size\' FROM (', sql_statement, ') AS race INNER JOIN racecourse AS racecourse ON race.racecourse_id = racecourse.id LEFT JOIN participant AS participant ON race.id = participant.race_id INNER JOIN jockey AS jockey ON participant.jockey_id = jockey.id INNER JOIN trainer AS trainer ON participant.trainer_id = trainer.id INNER JOIN horse AS horse ON participant.horse_id = horse.id INNER JOIN owner AS horse_owner ON horse.owner_id = horse_owner.id INNER JOIN trainer AS horse_trainer ON horse.trainer_id = horse_trainer.id UNION SELECT race.id \'race.id\', race.start_date_time \'race.start_date_time\', race.commission \'race.commission\', race.distance \'race.distance\', race.max_rating \'race.max_rating\', race.min_age \'race.min_age\', race.min_bet \'race.min_bet\', race.min_rating \'race.min_rating\', race.name \'race.name\', race.race_class \'race.race_class\', race.race_type \'race.race_type\', race.status \'race.status\', race.going \'race.going\', racecourse.id \'racecourse.id\', racecourse.name \'racecourse.name\', racecourse.latitude \'racecourse.latitude\', racecourse.longitude \'racecourse.longitude\', racecourse.clerk \'racecourse.clerk\', racecourse.contact \'racecourse.contact\', NULL AS \'participant.id\', NULL AS \'participant.number\', NULL AS \'participant.carried_weight\', NULL AS \'participant.topspeed\', NULL AS \'participant.official_rating\', NULL AS \'participant.odds\', NULL AS \'participant.place\', NULL AS \'trainer.id\', NULL AS \'trainer.first_name\', NULL AS \'trainer.last_name\', NULL AS \'trainer.birthday\', NULL AS \'jockey.id\', NULL AS \'jockey.first_name\', NULL AS \'jockey.last_name\', NULL AS \'jockey.birthday\', NULL AS \'horse.id\', NULL AS \'horse.name\', NULL AS \'horse.birthday\', NULL AS \'horse.gender\', NULL AS \'horse.sire_id\', NULL AS \'horse.dam_id\', NULL AS \'horse_owner.id\', NULL AS \'horse_owner.first_name\', NULL AS \'horse_owner.last_name\', NULL AS \'horse_owner.birthday\', NULL AS \'horse_trainer.id\', NULL AS \'horse_trainer.first_name\', NULL AS \'horse_trainer.last_name\', NULL AS \'horse_trainer.birthday\', prize.place AS \'prize.place\', prize.prize_size AS \'prize.prize_size\' FROM (', sql_statement, ') AS race INNER JOIN racecourse AS racecourse ON race.racecourse_id = racecourse.id LEFT JOIN prize AS prize ON prize.race_id = race.id');
  END //

CREATE PROCEDURE find_race_by_id(IN race_id INT UNSIGNED)
  BEGIN
    SET @find_statement = get_races('SELECT * FROM race WHERE race.id = ?');
    PREPARE query_to_execute FROM @find_statement;
    SET @id = race_id;
    EXECUTE query_to_execute
    USING @id, @id;
    DEALLOCATE PREPARE query_to_execute;
  END //

CREATE PROCEDURE find_all_races()
  BEGIN
    SET @find_statement = get_races('SELECT * FROM race');

    PREPARE query_to_execute FROM @find_statement;
    EXECUTE query_to_execute;
    DEALLOCATE PREPARE query_to_execute;
  END //

CREATE PROCEDURE find_all_races_limit_offset(IN p_limit INT UNSIGNED, IN p_offset INT UNSIGNED)
  BEGIN
    SET @find_statement = get_races('SELECT * FROM race LIMIT ? OFFSET ?');

    PREPARE query_to_execute FROM @find_statement;
    SET @v_limit = p_limit;
    SET @v_offset = p_offset;
    EXECUTE query_to_execute
    USING @v_limit, @v_offset, @v_limit, @v_offset;
    DEALLOCATE PREPARE query_to_execute;
  END //

CREATE PROCEDURE find_by_racecourse_id(
  IN race_status   ENUM ('scheduled', 'riding', 'finished', 'rejected'),
  IN racecourse_id INT UNSIGNED,
  IN p_limit       INT UNSIGNED,
  IN p_offset      INT UNSIGNED
)
  BEGIN
    SET @find_statement = get_races('SELECT * FROM race WHERE race.status = ? AND race.racecourse_id = ? LIMIT ? OFFSET ?');

    PREPARE query_to_execute FROM @find_statement;
    SET @v_race_status = race_status;
    SET @v_racecourse_id = racecourse_id;
    SET @v_limit = p_limit;
    SET @v_offset = p_offset;
    EXECUTE query_to_execute
    USING @v_race_status, @v_racecourse_id, @v_limit, @v_offset, @v_race_status, @v_racecourse_id, @v_limit, @v_offset;
    DEALLOCATE PREPARE query_to_execute;
  END //


CREATE PROCEDURE find_by_racecourse_name(
  IN race_status     ENUM ('scheduled', 'riding', 'finished', 'rejected'),
  IN racecourse_name VARCHAR(45),
  IN p_limit         INT UNSIGNED,
  IN p_offset        INT UNSIGNED
)
  BEGIN
    SET @find_statement = get_races('SELECT race.id, start_date_time, commission, distance, max_rating, min_age, min_bet, min_rating, race.name, race_class, race_type, status, going, racecourse_id FROM race INNER JOIN racecourse ON race.racecourse_id = racecourse.id WHERE status = ? AND racecourse.name LIKE ? LIMIT ? OFFSET ?');

    PREPARE query_to_execute FROM @find_statement;
    SET @v_race_status = race_status;
    SET @v_racecourse_name = racecourse_name;
    SET @v_limit = p_limit;
    SET @v_offset = p_offset;
    EXECUTE query_to_execute
    USING @v_race_status, @v_racecourse_name, @v_limit, @v_offset, @v_race_status, @v_racecourse_name, @v_limit, @v_offset;
    DEALLOCATE PREPARE query_to_execute;
  END //

CREATE PROCEDURE find_in_timestamp_diapason(
  IN race_status ENUM ('scheduled', 'riding', 'finished', 'rejected'),
  IN p_begin     TIMESTAMP,
  IN p_end       TIMESTAMP,
  IN p_limit     INT UNSIGNED,
  IN p_offset    INT UNSIGNED
)
  BEGIN
    SET @find_statement = get_races('SELECT * FROM race WHERE status = ? AND race.start_date_time BETWEEN ? AND ? LIMIT ? OFFSET ?');

    PREPARE query_to_execute FROM @find_statement;
    SET @v_race_status = race_status;
    SET @v_begin = p_begin;
    SET @v_end = p_end;
    SET @v_limit = p_limit;
    SET @v_offset = p_offset;
    EXECUTE query_to_execute
    USING @v_race_status, @v_begin, @v_end, @v_limit, @v_offset, @v_race_status, @v_begin, @v_end, @v_limit, @v_offset;
    DEALLOCATE PREPARE query_to_execute;
  END //

CREATE PROCEDURE find_in_timestamp_diapason_by_racecourse_id(
  IN race_status   ENUM ('scheduled', 'riding', 'finished', 'rejected'),
  IN racecourse_id INT UNSIGNED,
  IN p_begin       TIMESTAMP,
  IN p_end         TIMESTAMP,
  IN p_limit       INT UNSIGNED,
  IN p_offset      INT UNSIGNED
)
  BEGIN
    SET @find_statement = get_races('SELECT * FROM race WHERE status = ? AND race.racecourse_id = ? AND race.start_date_time BETWEEN ? AND ? LIMIT ? OFFSET ?');

    PREPARE query_to_execute FROM @find_statement;
    SET @v_race_status = race_status;
    SET @v_racecourse_id = racecourse_id;
    SET @v_begin = p_begin;
    SET @v_end = p_end;
    SET @v_limit = p_limit;
    SET @v_offset = p_offset;
    EXECUTE query_to_execute
    USING @v_race_status, @v_racecourse_id, @v_begin, @v_end, @v_limit, @v_offset, @v_race_status, @v_racecourse_id, @v_begin, @v_end, @v_limit, @v_offset;
    DEALLOCATE PREPARE query_to_execute;
  END //

CREATE PROCEDURE find_by_name(
  IN race_status ENUM ('scheduled', 'riding', 'finished', 'rejected'),
  IN p_name      VARCHAR(45),
  IN p_limit     INT UNSIGNED,
  IN p_offset    INT UNSIGNED
)
  BEGIN
    SET @find_statement = get_races('SELECT * FROM race WHERE status = ? AND race.name LIKE ? LIMIT ? OFFSET ?');

    PREPARE query_to_execute FROM @find_statement;
    SET @v_race_status = race_status;
    SET @v_name = p_name;
    SET @v_limit = p_limit;
    SET @v_offset = p_offset;
    EXECUTE query_to_execute
    USING @v_race_status, @v_name, @v_limit, @v_offset, @v_race_status, @v_name, @v_limit, @v_offset;
    DEALLOCATE PREPARE query_to_execute;
  END //
