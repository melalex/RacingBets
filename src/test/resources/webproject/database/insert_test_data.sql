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