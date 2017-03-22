# jockey table

INSERT INTO horse_racing_test.jockey (first_name, last_name, birthday) VALUES ('Ruby', 'Nichols', '1982-04-21');
INSERT INTO horse_racing_test.jockey (first_name, last_name, birthday) VALUES ('Nichols', 'Ruby', '1962-05-19');
INSERT INTO horse_racing_test.jockey (first_name, last_name, birthday) VALUES ('Doris', 'Franklin', '1984-03-16');
INSERT INTO horse_racing_test.jockey (first_name, last_name, birthday) VALUES ('Thomas', 'West', '1980-01-19');
INSERT INTO horse_racing_test.jockey (first_name, last_name, birthday) VALUES ('Alex', 'Strutynski', '1980-04-21');
INSERT INTO horse_racing_test.jockey (first_name, last_name, birthday) VALUES ('Vova', 'Bog', NULL);

# trainer table

INSERT INTO horse_racing_test.trainer (first_name, last_name, birthday) VALUES ('Ruby', 'Nichols', '1982-04-21');
INSERT INTO horse_racing_test.trainer (first_name, last_name, birthday) VALUES ('Nichols', 'Ruby', '1962-05-19');
INSERT INTO horse_racing_test.trainer (first_name, last_name, birthday) VALUES ('Doris', 'Franklin', '1984-03-16');
INSERT INTO horse_racing_test.trainer (first_name, last_name, birthday) VALUES ('Thomas', 'West', '1980-01-19');
INSERT INTO horse_racing_test.trainer (first_name, last_name, birthday) VALUES ('Alex', 'Strutynski', '1980-04-21');
INSERT INTO horse_racing_test.trainer (first_name, last_name, birthday) VALUES ('Vova', 'Bog', NULL);

# owner table

INSERT INTO horse_racing_test.owner (first_name, last_name, birthday) VALUES ('Ruby', 'Nichols', '1982-04-21');
INSERT INTO horse_racing_test.owner (first_name, last_name, birthday) VALUES ('Nichols', 'Ruby', '1962-05-19');
INSERT INTO horse_racing_test.owner (first_name, last_name, birthday) VALUES ('Doris', 'Franklin', '1984-03-16');
INSERT INTO horse_racing_test.owner (first_name, last_name, birthday) VALUES ('Thomas', 'West', '1980-01-19');
INSERT INTO horse_racing_test.owner (first_name, last_name, birthday) VALUES ('Alex', 'Strutynski', '1980-04-21');
INSERT INTO horse_racing_test.owner (first_name, last_name, birthday) VALUES ('Vova', 'Bog', NULL);

# horse table

INSERT INTO horse_racing_test.horse
(name, trainer_id, owner_id, birthday, gender, sire_id, dam_id)
VALUES ('Fixflex', 5, 1, '2008-02-22', 'mare', NULL, NULL);

INSERT INTO horse_racing_test.horse
(name, trainer_id, owner_id, birthday, gender, sire_id, dam_id)
VALUES ('Wrapsafe', 4, 2, '2005-08-04', 'stallion', NULL, NULL);

INSERT INTO horse_racing_test.horse
(name, trainer_id, owner_id, birthday, gender, sire_id, dam_id)
VALUES ('Prodder', 3, 3, '2011-12-26', 'mare', 2, 1);

INSERT INTO horse_racing_test.horse
(name, trainer_id, owner_id, birthday, gender, sire_id, dam_id)
VALUES ('Span', 2, 4, '2011-03-27', 'stallion', 2, 1);

INSERT INTO horse_racing_test.horse
(name, trainer_id, owner_id, birthday, gender, sire_id, dam_id)
VALUES ('Treeflex', 1, 5, '2013-05-01', 'mare', 3, 4);

INSERT INTO horse_racing_test.horse
(name, trainer_id, owner_id, birthday, gender, sire_id, dam_id)
VALUES ('Alphazap', 4, 2, '2000-10-25', 'stallion', NULL, NULL);

INSERT INTO horse_racing_test.horse
(name, trainer_id, owner_id, birthday, gender, sire_id, dam_id)
VALUES ('Aerified', 2, 4, '2010-11-27', 'stallion', 2, 1);

INSERT INTO horse_racing_test.horse
(name, trainer_id, owner_id, birthday, gender, sire_id, dam_id)
VALUES ('Prob', 3, 3, '2014-05-01', 'mare', 3, 4);

# racecourse table

INSERT INTO horse_racing_test.racecourse
(name, latitude, longitude, contact, clerk)
VALUES ('Ronstring', -22.72528, -47.64917, 'scook0@hud.gov', 'Stephen Cook');

INSERT INTO horse_racing_test.racecourse
(name, latitude, longitude, contact, clerk)
VALUES ('Fintone', 29.95033, 121.74293, 'ncunningham1@merriam-webster.com', 'Nicole Cunningham');

INSERT INTO horse_racing_test.racecourse
(name, latitude, longitude, contact, clerk)
VALUES ('Flowdesk', -20.26889, -50.54583, 'ajames2@amazon.co.jp', 'Annie James');

# race table

INSERT INTO horse_racing_test.race
(name, status, commission, min_bet, racecourse_id, start_date_time, going, race_type, race_class, min_age, min_rating, max_rating, distance)
VALUES ('Gembucket', 'finished', 0.14, 2, 1, '2017-03-08 10:32:36', 'Hard', 'flat', 1, 3, 50, 70, 8.1);

INSERT INTO horse_racing_test.race
(name, status, commission, min_bet, racecourse_id, start_date_time, going, race_type, race_class, min_age, min_rating, max_rating, distance)
VALUES ('Ventosanzap', 'riding', 0.14, 2, 1, '2017-03-09 13:44:56', 'Firm', 'jump', 2, 2, 0, 70, 8.1);

INSERT INTO horse_racing_test.race
(name, status, commission, min_bet, racecourse_id, start_date_time, going, race_type, race_class, min_age, min_rating, max_rating, distance)
VALUES ('Duobam', 'scheduled', 0.14, 2, 2, '2017-03-10 12:00:00', NULL, 'harness', 2, 2, 0, 60, 10.1);

# participant table

INSERT INTO horse_racing_test.participant
(number, horse_id, race_id, jockey_id, trainer_id, carried_weight, topspeed, official_rating, odds, place)
VALUES (1, 1, 1, 1, 1, 50, 70, 70, 2.1, 1);

INSERT INTO horse_racing_test.participant
(number, horse_id, race_id, jockey_id, trainer_id, carried_weight, topspeed, official_rating, odds, place)
VALUES (2, 2, 1, 2, 2, 50, 70, 70, 1, 2);

INSERT INTO horse_racing_test.participant
(number, horse_id, race_id, jockey_id, trainer_id, carried_weight, topspeed, official_rating, odds, place)
VALUES (3, 3, 1, 3, 3, 50, 70, 70, 0.5, 3);

INSERT INTO horse_racing_test.participant
(number, horse_id, race_id, jockey_id, trainer_id, carried_weight, topspeed, official_rating, odds, place)
VALUES (1, 4, 2, 4, 4, 50, 70, 70, 2.1, 1);

INSERT INTO horse_racing_test.participant
(number, horse_id, race_id, jockey_id, trainer_id, carried_weight, topspeed, official_rating, odds, place)
VALUES (2, 5, 2, 5, 5, 50, 70, 70, 1, 2);

INSERT INTO horse_racing_test.participant
(number, horse_id, race_id, jockey_id, trainer_id, carried_weight, topspeed, official_rating, odds, place)
VALUES (3, 3, 2, 1, 1, 50, 70, 70, 0.5, 3);

INSERT INTO horse_racing_test.participant
(number, horse_id, race_id, jockey_id, trainer_id, carried_weight, topspeed, official_rating, odds, place)
VALUES (1, 2, 3, 2, 2, NULL, NULL, NULL, NULL, NULL);

INSERT INTO horse_racing_test.participant
(number, horse_id, race_id, jockey_id, trainer_id, carried_weight, topspeed, official_rating, odds, place)
VALUES (2, 1, 3, 3, 3, NULL, NULL, NULL, NULL, NULL);

INSERT INTO horse_racing_test.participant
(number, horse_id, race_id, jockey_id, trainer_id, carried_weight, topspeed, official_rating, odds, place)
VALUES (3, 3, 3, 4, 4, NULL, NULL, NULL, NULL, NULL);

# prize table

INSERT INTO horse_racing_test.prize (race_id, prize_size, place) VALUES (1, 300, 1);
INSERT INTO horse_racing_test.prize (race_id, prize_size, place) VALUES (1, 200, 2);
INSERT INTO horse_racing_test.prize (race_id, prize_size, place) VALUES (1, 100, 3);

INSERT INTO horse_racing_test.prize (race_id, prize_size, place) VALUES (2, 300, 1);
INSERT INTO horse_racing_test.prize (race_id, prize_size, place) VALUES (2, 200, 2);
INSERT INTO horse_racing_test.prize (race_id, prize_size, place) VALUES (2, 100, 3);

INSERT INTO horse_racing_test.prize (race_id, prize_size, place) VALUES (3, 300, 1);
INSERT INTO horse_racing_test.prize (race_id, prize_size, place) VALUES (3, 200, 2);
INSERT INTO horse_racing_test.prize (race_id, prize_size, place) VALUES (3, 100, 3);

# application_user table

INSERT INTO horse_racing_test.application_user
(login, password_hash, first_name, last_name, email, is_email_confirmed, balance)
VALUES ('pgordon0', 'kJ182n', 'Paula', 'Gordon', 'pgordon0@google.ru', TRUE, 827.32);

INSERT INTO horse_racing_test.application_user
(login, password_hash, first_name, last_name, email, is_email_confirmed, balance)
VALUES ('slawrence1', 'tBHdVXlvv', 'Shirley', 'Lawrence', 'slawrence1@geocities.com', TRUE, 924.23);

INSERT INTO horse_racing_test.application_user
(login, password_hash, first_name, last_name, email, is_email_confirmed, balance)
VALUES ('hbanks2', 'RgoMu22lO', 'Harold', 'Banks', 'hbanks2@adobe.com', TRUE, 256.71);

INSERT INTO horse_racing_test.application_user
(login, password_hash, first_name, last_name, email, is_email_confirmed, balance)
VALUES ('lallen3', 'WCeXA5', 'Lois', 'Allen', 'lallen3@virginia.edu', TRUE, 385.59);

INSERT INTO horse_racing_test.application_user
(login, password_hash, first_name, last_name, email, is_email_confirmed, balance)
VALUES ('pandrews4', 'uRKNaCqj2B', 'Paula', 'Andrews', 'pandrews4@artisteer.com', TRUE, 749.38);

# role table

INSERT INTO horse_racing_test.role (application_user_id, name) VALUES (1, 'Admin');
INSERT INTO horse_racing_test.role (application_user_id, name) VALUES (1, 'Bookmaker');
INSERT INTO horse_racing_test.role (application_user_id, name) VALUES (1, 'Handicapper');

INSERT INTO horse_racing_test.role (application_user_id, name) VALUES (2, 'Bookmaker');
INSERT INTO horse_racing_test.role (application_user_id, name) VALUES (2, 'Handicapper');

INSERT INTO horse_racing_test.role (application_user_id, name) VALUES (3, 'Handicapper');

INSERT INTO horse_racing_test.role (application_user_id, name) VALUES (4, 'Handicapper');

INSERT INTO horse_racing_test.role (application_user_id, name) VALUES (5, 'Handicapper');

# bet table

# (Place)

INSERT INTO horse_racing_test.bet (application_user_id, race_id, bet_type, status, bet_size)
VALUES (3, 1, 'Place', 'scheduled', 100);

INSERT INTO horse_racing_test.bet (application_user_id, race_id, bet_type, status, bet_size)
VALUES (4, 1, 'Place', 'scheduled', 200);

INSERT INTO horse_racing_test.bet (application_user_id, race_id, bet_type, status, bet_size)
VALUES (5, 1, 'Place', 'scheduled', 300);

# (Win)

INSERT INTO horse_racing_test.bet (application_user_id, race_id, bet_type, status, bet_size)
VALUES (3, 1, 'Win', 'scheduled', 100);

INSERT INTO horse_racing_test.bet (application_user_id, race_id, bet_type, status, bet_size)
VALUES (4, 1, 'Win', 'scheduled', 200);

INSERT INTO horse_racing_test.bet (application_user_id, race_id, bet_type, status, bet_size)
VALUES (5, 1, 'Win', 'scheduled', 300);

# (Qeinella)

INSERT INTO horse_racing_test.bet (application_user_id, race_id, bet_type, status, bet_size)
VALUES (3, 1, 'Quinella', 'scheduled', 100);

INSERT INTO horse_racing_test.bet (application_user_id, race_id, bet_type, status, bet_size)
VALUES (4, 1, 'Quinella', 'scheduled', 200);

INSERT INTO horse_racing_test.bet (application_user_id, race_id, bet_type, status, bet_size)
VALUES (5, 1, 'Quinella', 'scheduled', 300);

# (Exacta)

INSERT INTO horse_racing_test.bet (application_user_id, race_id, bet_type, status, bet_size)
VALUES (3, 1, 'Exacta', 'scheduled', 100);

INSERT INTO horse_racing_test.bet (application_user_id, race_id, bet_type, status, bet_size)
VALUES (4, 1, 'Exacta', 'scheduled', 200);

INSERT INTO horse_racing_test.bet (application_user_id, race_id, bet_type, status, bet_size)
VALUES (5, 1, 'Exacta', 'scheduled', 300);

# (Trifecta)

INSERT INTO horse_racing_test.bet (application_user_id, race_id, bet_type, status, bet_size)
VALUES (3, 1, 'Trifecta', 'scheduled', 100);

INSERT INTO horse_racing_test.bet (application_user_id, race_id, bet_type, status, bet_size)
VALUES (4, 1, 'Trifecta', 'scheduled', 200);

INSERT INTO horse_racing_test.bet (application_user_id, race_id, bet_type, status, bet_size)
VALUES (5, 1, 'Trifecta', 'scheduled', 300);

# bet_participant table

# (Place)

INSERT INTO horse_racing_test.bet_participant (bet_id, participant_id, place) VALUES (1, 1, 1);
INSERT INTO horse_racing_test.bet_participant (bet_id, participant_id, place) VALUES (1, 1, 2);

INSERT INTO horse_racing_test.bet_participant (bet_id, participant_id, place) VALUES (2, 1, 1);
INSERT INTO horse_racing_test.bet_participant (bet_id, participant_id, place) VALUES (2, 1, 2);

INSERT INTO horse_racing_test.bet_participant (bet_id, participant_id, place) VALUES (3, 3, 1);
INSERT INTO horse_racing_test.bet_participant (bet_id, participant_id, place) VALUES (3, 3, 2);

# (Win)

INSERT INTO horse_racing_test.bet_participant (bet_id, participant_id, place) VALUES (4, 1, 1);

INSERT INTO horse_racing_test.bet_participant (bet_id, participant_id, place) VALUES (5, 1, 1);

INSERT INTO horse_racing_test.bet_participant (bet_id, participant_id, place) VALUES (6, 3, 1);

# (Qeinella)

INSERT INTO horse_racing_test.bet_participant (bet_id, participant_id, place) VALUES (7, 1, 1);
INSERT INTO horse_racing_test.bet_participant (bet_id, participant_id, place) VALUES (7, 2, 2);

INSERT INTO horse_racing_test.bet_participant (bet_id, participant_id, place) VALUES (8, 1, 1);
INSERT INTO horse_racing_test.bet_participant (bet_id, participant_id, place) VALUES (8, 2, 2);

INSERT INTO horse_racing_test.bet_participant (bet_id, participant_id, place) VALUES (9, 3, 1);
INSERT INTO horse_racing_test.bet_participant (bet_id, participant_id, place) VALUES (9, 1, 2);

# (Exacta)

INSERT INTO horse_racing_test.bet_participant (bet_id, participant_id, place) VALUES (10, 1, 1);
INSERT INTO horse_racing_test.bet_participant (bet_id, participant_id, place) VALUES (10, 2, 2);

INSERT INTO horse_racing_test.bet_participant (bet_id, participant_id, place) VALUES (11, 1, 1);
INSERT INTO horse_racing_test.bet_participant (bet_id, participant_id, place) VALUES (11, 2, 2);

INSERT INTO horse_racing_test.bet_participant (bet_id, participant_id, place) VALUES (12, 3, 1);
INSERT INTO horse_racing_test.bet_participant (bet_id, participant_id, place) VALUES (12, 1, 2);

# (Trifecta)

INSERT INTO horse_racing_test.bet_participant (bet_id, participant_id, place) VALUES (13, 1, 1);
INSERT INTO horse_racing_test.bet_participant (bet_id, participant_id, place) VALUES (13, 2, 2);
INSERT INTO horse_racing_test.bet_participant (bet_id, participant_id, place) VALUES (13, 3, 3);

INSERT INTO horse_racing_test.bet_participant (bet_id, participant_id, place) VALUES (14, 1, 1);
INSERT INTO horse_racing_test.bet_participant (bet_id, participant_id, place) VALUES (14, 2, 2);
INSERT INTO horse_racing_test.bet_participant (bet_id, participant_id, place) VALUES (14, 3, 3);

INSERT INTO horse_racing_test.bet_participant (bet_id, participant_id, place) VALUES (15, 1, 1);
INSERT INTO horse_racing_test.bet_participant (bet_id, participant_id, place) VALUES (15, 2, 2);
INSERT INTO horse_racing_test.bet_participant (bet_id, participant_id, place) VALUES (15, 3, 3);


