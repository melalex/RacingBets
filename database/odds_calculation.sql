CREATE PROCEDURE get_odds_for_win(
  IN id_of_race INT UNSIGNED,
  IN participant INT UNSIGNED,
  OUT prize_pool DECIMAL(12, 2),
  OUT event_pool DECIMAL(12, 2),
  OUT commission DOUBLE
)
  BEGIN
    DECLARE type ENUM('Show', 'Place', 'Win', 'Quinella', 'Exacta', 'Trifecta', 'Superfecta');
    SET type = 'Win';
    SET prize_pool = (SELECT SUM(bet_size) FROM bet WHERE bet.bet_type = type AND bet.race_id = id_of_race);
    SET commission = (SELECT commission FROM race WHERE id = id_of_race);

    SET event_pool = (
      SELECT SUM(bet_size)
      FROM (
             SELECT * FROM bet
             WHERE bet.bet_type = type AND
                   bet.race_id = id_of_race
           ) AS b1
        LEFT JOIN bet_participant
          ON bet_participant.bet_id = bet.id
      WHERE bet_participant.participant_id = participant AND
            bet_participant.place = 1
    );
  END;

CREATE PROCEDURE get_odds_for_place(
  IN id_of_race INT UNSIGNED,
  IN participant INT UNSIGNED,
  OUT prize_pool DECIMAL(12, 2),
  OUT event_pool DECIMAL(12, 2),
  OUT commission DOUBLE
)
  BEGIN
    DECLARE type ENUM('Show', 'Place', 'Win', 'Quinella', 'Exacta', 'Trifecta', 'Superfecta');
    SET type = 'Place';
    SET prize_pool = (SELECT SUM(bet_size) FROM bet WHERE bet.bet_type = type AND bet.race_id = id_of_race);
    SET commission = (SELECT commission FROM race WHERE id = id_of_race);

    SET event_pool = (
      SELECT SUM(bet_size)
      FROM (
             SELECT * FROM bet
             WHERE bet.bet_type = type AND
                   bet.race_id = id_of_race
           ) AS b1
        LEFT JOIN bet_participant
          ON bet_participant.bet_id = bet.id
      WHERE bet_participant.participant_id = participant AND
            (bet_participant.place = 1 OR bet_participant.place = 2)
    );
  END;

CREATE PROCEDURE get_odds_for_show(
  IN id_of_race INT UNSIGNED,
  IN participant INT UNSIGNED,
  OUT prize_pool DECIMAL(12, 2),
  OUT event_pool DECIMAL(12, 2),
  OUT commission DOUBLE
)
  BEGIN
    DECLARE type ENUM('Show', 'Place', 'Win', 'Quinella', 'Exacta', 'Trifecta', 'Superfecta');
    SET type = 'Place';
    SET prize_pool = (SELECT SUM(bet_size) FROM bet WHERE bet.bet_type = type AND bet.race_id = id_of_race);
    SET commission = (SELECT commission FROM race WHERE id = id_of_race);

    SET event_pool = (
      SELECT SUM(bet_size)
      FROM (
             SELECT * FROM bet
             WHERE bet.bet_type = type AND
                   bet.race_id = id_of_race
           ) AS b1
        LEFT JOIN bet_participant
          ON bet_participant.bet_id = bet.id
      WHERE bet_participant.participant_id = participant AND
            (bet_participant.place = 1 OR bet_participant.place = 2 OR bet_participant.place = 3)
    );
  END;

CREATE PROCEDURE get_odds_for_quinella(
  IN id_of_race INT UNSIGNED,
  IN participant1 INT UNSIGNED,
  IN participant2 INT UNSIGNED,
  OUT prize_pool DECIMAL(12, 2),
  OUT event_pool DECIMAL(12, 2),
  OUT commission DOUBLE
)
  BEGIN
    DECLARE type ENUM('Show', 'Place', 'Win', 'Quinella', 'Exacta', 'Trifecta', 'Superfecta');
    SET type = 'Place';
    SET prize_pool = (SELECT SUM(bet_size) FROM bet WHERE bet.bet_type = type AND bet.race_id = id_of_race);
    SET commission = (SELECT commission FROM race WHERE id = id_of_race);

    SET event_pool = (
      SELECT SUM(bet_size)
      FROM (
             SELECT * FROM bet
             WHERE bet.bet_type = type AND
                   bet.race_id = id_of_race
           ) AS b1
        LEFT JOIN bet_participant
          ON bet_participant.bet_id = bet.id
      WHERE bet_participant.participant_id IN(participant1, participant2)
      GROUP BY bet.id
      HAVING COUNT(*) = 2
    );
  END;

CREATE PROCEDURE get_odds_for_exacta(
  IN id_of_race INT UNSIGNED,
  IN participant1 INT UNSIGNED,
  IN participant2 INT UNSIGNED,
  OUT prize_pool DECIMAL(12, 2),
  OUT event_pool DECIMAL(12, 2),
  OUT commission DOUBLE
)
  BEGIN
    DECLARE type ENUM('Show', 'Place', 'Win', 'Quinella', 'Exacta', 'Trifecta', 'Superfecta');
    SET type = 'Place';
    SET prize_pool = (SELECT SUM(bet_size) FROM bet WHERE bet.bet_type = type AND bet.race_id = id_of_race);
    SET commission = (SELECT commission FROM race WHERE id = id_of_race);

    SET event_pool = (
      SELECT SUM(bet_size)
      FROM (
             SELECT * FROM bet
             WHERE bet.bet_type = type AND
                   bet.race_id = id_of_race
           ) AS b1
        LEFT JOIN bet_participant
          ON bet_participant.bet_id = bet.id
      WHERE (bet_participant.participant_id = participant1 AND bet_participant.place = 1) OR
            (bet_participant.participant_id = participant2 AND bet_participant.place = 2)
      GROUP BY bet.id
      HAVING COUNT(*) = 2
    );
  END;

CREATE PROCEDURE get_odds_for_exacta(
  IN id_of_race INT UNSIGNED,
  IN participant1 INT UNSIGNED,
  IN participant2 INT UNSIGNED,
  IN participant3 INT UNSIGNED,
  OUT prize_pool DECIMAL(12, 2),
  OUT event_pool DECIMAL(12, 2),
  OUT commission DOUBLE
)
  BEGIN
    DECLARE type ENUM('Show', 'Place', 'Win', 'Quinella', 'Exacta', 'Trifecta', 'Superfecta');
    SET type = 'Place';
    SET prize_pool = (SELECT SUM(bet_size) FROM bet WHERE bet.bet_type = type AND bet.race_id = id_of_race);
    SET commission = (SELECT commission FROM race WHERE id = id_of_race);

    SET event_pool = (
      SELECT SUM(bet_size)
      FROM (
             SELECT * FROM bet
             WHERE bet.bet_type = type AND
                   bet.race_id = id_of_race
           ) AS b1
        LEFT JOIN bet_participant
          ON bet_participant.bet_id = bet.id
      WHERE (bet_participant.participant_id = participant1 AND bet_participant.place = 1) OR
            (bet_participant.participant_id = participant2 AND bet_participant.place = 2) OR
            (bet_participant.participant_id = participant3 AND bet_participant.place = 3)
      GROUP BY bet.id
      HAVING COUNT(*) = 3
    );
  END;

CREATE PROCEDURE get_odds_for_exacta(
  IN id_of_race INT UNSIGNED,
  IN participant1 INT UNSIGNED,
  IN participant2 INT UNSIGNED,
  IN participant3 INT UNSIGNED,
  IN participant4 INT UNSIGNED,
  OUT prize_pool DECIMAL(12, 2),
  OUT event_pool DECIMAL(12, 2),
  OUT commission DOUBLE
)
  BEGIN
    DECLARE type ENUM('Show', 'Place', 'Win', 'Quinella', 'Exacta', 'Trifecta', 'Superfecta');
    SET type = 'Place';
    SET prize_pool = (SELECT SUM(bet_size) FROM bet WHERE bet.bet_type = type AND bet.race_id = id_of_race);
    SET commission = (SELECT commission FROM race WHERE id = id_of_race);

    SET event_pool = (
      SELECT SUM(bet_size)
      FROM (
             SELECT * FROM bet
             WHERE bet.bet_type = type AND
                   bet.race_id = id_of_race
           ) AS b1
        LEFT JOIN bet_participant
          ON bet_participant.bet_id = bet.id
      WHERE bet_participant.participant_id IN (participant1, participant2, participant3, participant4)
      GROUP BY bet.id
      HAVING COUNT(*) = 4
    );
  END;