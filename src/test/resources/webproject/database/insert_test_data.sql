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
VALUES ('Prodder', 3, 3, '2011-12-26', 'Mare', 2, 1);

INSERT INTO horse_racing_test.horse
  (name, trainer_id, owner_id, birthday, gender, sire_id, dam_id)
VALUES ('Span', 2, 4, '2011-03-27', 'stallion', 2, 1);

INSERT INTO horse_racing_test.horse
  (name, trainer_id, owner_id, birthday, gender, sire_id, dam_id)
VALUES ('Treeflex', 1, 5, '2013-05-01', 'Mare', 3, 4);

INSERT INTO horse_racing_test.horse
(name, trainer_id, owner_id, birthday, gender, sire_id, dam_id)
VALUES ('Alphazap', 4, 2, '2000-10-25', 'stallion', NULL, NULL);

INSERT INTO horse_racing_test.horse
(name, trainer_id, owner_id, birthday, gender, sire_id, dam_id)
VALUES ('Aerified', 2, 4, '2010-11-27', 'stallion', 2, 1);
