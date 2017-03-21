DELIMITER //

CREATE PROCEDURE horse_racing_test.fix_race_result(
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
