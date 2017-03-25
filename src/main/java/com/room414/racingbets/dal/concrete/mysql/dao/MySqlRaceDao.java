package com.room414.racingbets.dal.concrete.mysql.dao;

import com.room414.racingbets.dal.abstraction.dao.RaceDao;
import com.room414.racingbets.dal.abstraction.exception.DalException;
import com.room414.racingbets.dal.concrete.mysql.infrastructure.MySqlMapHelper;
import com.room414.racingbets.dal.concrete.mysql.infrastructure.MySqlSharedExecutor;
import com.room414.racingbets.dal.domain.builders.RaceBuilder;
import com.room414.racingbets.dal.domain.entities.Participant;
import com.room414.racingbets.dal.domain.entities.Race;
import com.room414.racingbets.dal.domain.enums.RaceStatus;
import org.intellij.lang.annotations.Language;

import java.math.BigDecimal;
import java.sql.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;
import java.util.stream.Collectors;

import static com.room414.racingbets.dal.concrete.mysql.infrastructure.MySqlDaoHelper.*;

/**
 * Implementation of RaceDao that uses JDBC as data source.
 *
 * @see RaceDao
 * @author Alexander Melashchenko
 * @version 1.0 28 Feb 2017
 */
public class MySqlRaceDao implements RaceDao {
    private static String TABLE_NAME = "race";


    private Connection connection;
    private MySqlSharedExecutor<Race> executor;

    MySqlRaceDao(Connection connection) {
        this.connection = connection;
        this.executor = new MySqlSharedExecutor<>(
                connection,
                statement -> getResultWithArray(statement, this::mapRaces),
                statement -> getResultListWithArray(statement, this::mapRaces)
        );
    }

    private List<Race> mapRaces(Statement statement) throws SQLException {
        try(ResultSet resultSet = statement.getResultSet()) {
                return mapRaces(resultSet);
        }
    }

    private List<Race> mapRaces(ResultSet resultSet) throws SQLException {
        final String idColumnName = "race.id";
        final String startColumnName = "race.start_date_time";
        final String commissionColumnName = "race.commission";
        final String distanceColumnName = "race.distance";
        final String maxRatingColumnName = "race.max_rating";
        final String minAgeColumnName = "race.min_age";
        final String minBetColumnName = "race.min_bet";
        final String minRatingColumnName = "race.min_rating";
        final String nameColumnName = "race.name";
        final String raceClassColumnName = "race.race_class";
        final String raceTypeColumnName = "race.race_type";
        final String statusColumnName = "race.status";
        final String goingColumnName = "race.going";
        final String prizeColumnName = "prize.prize_size";
        final String placeColumnName = "prize.place";

        Map<Long, RaceBuilder> builderById = new HashMap<>();

        RaceBuilder builder;
        long id;
        Integer place;

        while (resultSet.next()) {
            id = resultSet.getLong(idColumnName);
            builder = builderById.get(id);
            if (builder == null) {
                builder = Race.builder()
                        .setId(id)
                        .setStart(resultSet.getTimestamp(startColumnName))
                        .setCommission(resultSet.getDouble(commissionColumnName))
                        .setDistance(resultSet.getFloat(distanceColumnName))
                        .setMaxRating(resultSet.getInt(maxRatingColumnName))
                        .setMinAge(resultSet.getInt(minAgeColumnName))
                        .setMinBet(resultSet.getBigDecimal(minBetColumnName))
                        .setMinRating(resultSet.getInt(minRatingColumnName))
                        .setName(resultSet.getString(nameColumnName))
                        .setRaceClass(resultSet.getInt(raceClassColumnName))
                        .setRaceType(resultSet.getString(raceTypeColumnName))
                        .setRaceStatus(resultSet.getString(statusColumnName))
                        .setTrackCondition(resultSet.getString(goingColumnName))
                        .setRacecourse(MySqlMapHelper.mapRacecourse(resultSet));
                builderById.put(id, builder);
            }

            place = resultSet.getInt(placeColumnName);

            if (place == 0) {
                builder.addParticipant(MySqlMapHelper.mapParticipant(resultSet));
            } else {
                builder.setPrize(place, resultSet.getBigDecimal(prizeColumnName));
            }
        }

        return builderById
                .values()
                .stream()
                .map(RaceBuilder::build)
                .collect(Collectors.toList());
    }

    private void createRace(Race entity) {
        @Language("MySQL")
        final String sqlStatement =
                "INSERT INTO race " +
                "   (name, status, racecourse_id, start_date_time, min_bet, commission, going, " +
                "   race_type, race_class, min_age, min_rating, max_rating, distance) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        executor.create(
                sqlStatement,
                entity::setId,
                entity.getName(),
                entity.getRaceStatus().getName(),
                entity.getRacecourse().getId(),
                entity.getStart(),
                entity.getMinBet(),
                entity.getCommission(),
                entity.getTrackCondition().getName(),
                entity.getRaceType().getName(),
                entity.getRaceClass(),
                entity.getMinAge(),
                entity.getMinRating(),
                entity.getMaxRating(),
                entity.getDistance()
        );
    }

    private void createParticipants(Race entity) {
        final String sqlStatement =
                "INSERT INTO participant " +
                "   (number, horse_id, race_id, carried_weight, topspeed, " +
                "   official_rating, jockey_id, trainer_id, place, odds)  " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try(PreparedStatement statement = connection.prepareStatement(sqlStatement, Statement.RETURN_GENERATED_KEYS)) {
            List<Participant> participants = entity.getParticipants();

            for (Participant participant : participants) {
                statement.setInt(1, participant.getNumber());
                statement.setLong(2, participant.getHorse().getId());
                statement.setLong(3, entity.getId());
                statement.setFloat(4, participant.getCarriedWeight());
                statement.setInt(5, participant.getTopSpeed());
                statement.setInt(6, participant.getOfficialRating());
                statement.setLong(7, participant.getJockey().getId());
                statement.setLong(8, participant.getTrainer().getId());
                statement.setInt(9, participant.getPlace());
                statement.setDouble(10, participant.getOdds());

                statement.addBatch();
            }

            statement.executeBatch();

            List<Consumer<Long>> idSetters = participants
                    .stream()
                    .map(p -> (Consumer<Long>) p::setId)
                    .collect(Collectors.toList());

            createEntities(statement, idSetters);
        } catch (SQLException e) {
            String message = "Exception during adding participant faze while creating race " + entity.toString();
            throw new DalException(message, e);
        }

    }

    private void createPrizes(Race entity) {
        final String sqlStatement = "INSERT INTO prize (race_id, prize_size, place) VALUES (?, ?, ?)";

        try(PreparedStatement statement = connection.prepareStatement(sqlStatement)) {
            Map<Integer, BigDecimal> prizes = entity.getPrizes();

            for (Integer place : prizes.keySet()) {
                statement.setLong(1, entity.getId());
                statement.setBigDecimal(2, prizes.get(place));
                statement.setInt(3, place);

                statement.addBatch();
            }

            statement.executeBatch();
        } catch (SQLException e) {
            String message = "Exception during adding prizes faze while creating race " + entity.toString();
            throw new DalException(message, e);
        }

    }

    @Override
    public void create(Race entity) {
        createRace(entity);
        createParticipants(entity);
        createPrizes(entity);
    }

    @Override
    public Race find(Long id) {
        @Language("MySQL")
        final String call = "{ CALL find_race_by_id(?) }";

        try {
            return executor.executeFindOneCall(call, id);
        } catch (DalException e) {
            String message = callErrorMessage("find_race_by_id", id);
            throw new DalException(message, e);
        }
    }

    @Override
    public List<Race> findAll() {
        @Language("MySQL")
        final String call = "{ CALL find_all_races() }";

        try {
            return executor.executeFindManyCall(call);
        } catch (DalException e) {
            String message = callErrorMessage("find_all_races");
            throw new DalException(message, e);
        }
    }

    @Override
    public List<Race> findAll(int offset, int limit) {
        @Language("MySQL")
        final String call = "{ CALL find_all_races_limit_offset(?, ?) }";

        try {
            return executor.executeFindManyCall(call, limit, offset);
        } catch (DalException e) {
            String message = callErrorMessage("find_all_races_limit_offset", limit, offset);
            throw new DalException(message, e);
        }
    }

    @Override
    public int count() {
        return executor.count(TABLE_NAME);
    }

    private long updateRace(Race entity) {
        @Language("MySQL")
        final String sqlStatement =
                "UPDATE race " +
                "SET name = ?, status = ?, racecourse_id = ?, start_date_time = ?, min_bet = ?, " +
                "   commission = ?, going = ?, race_type = ?, race_class = ?, " +
                "   min_age = ?, min_rating = ?, max_rating = ?, distance = ? " +
                "WHERE id = ?";

        return executor.executeUpdateQuery(
                sqlStatement,
                entity.getName(),
                entity.getRaceStatus().getName(),
                entity.getRacecourse().getId(),
                entity.getStart(),
                entity.getMinBet(),
                entity.getCommission(),
                entity.getTrackCondition().getName(),
                entity.getRaceType().getName(),
                entity.getRaceClass(),
                entity.getMinAge(),
                entity.getMinRating(),
                entity.getMaxRating(),
                entity.getDistance(),
                entity.getId()
        );
    }

    private void updateParticipants(Race entity) {
        @Language("MySQL")
        String sqlStatement =
                "UPDATE participant " +
                "SET number = ?, horse_id = ?, race_id = ?, carried_weight = ?, topspeed = ?, " +
                "   official_rating = ?, jockey_id = ?, trainer_id = ?, place = ?, odds = ? " +
                "WHERE id = ?";

        try(PreparedStatement statement = connection.prepareStatement(sqlStatement, Statement.RETURN_GENERATED_KEYS)) {
            List<Participant> participants = entity.getParticipants();

            for (Participant participant : participants) {
                statement.setInt(1, participant.getNumber());
                statement.setLong(2, participant.getHorse().getId());
                statement.setLong(3, entity.getId());
                statement.setFloat(4, participant.getCarriedWeight());
                statement.setInt(5, participant.getTopSpeed());
                statement.setInt(6, participant.getOfficialRating());
                statement.setLong(7, participant.getJockey().getId());
                statement.setLong(8, participant.getTrainer().getId());
                statement.setInt(9, participant.getPlace());
                statement.setDouble(10, participant.getOdds());
                statement.setLong(11, participant.getId());

                statement.addBatch();
            }

            statement.executeBatch();
        } catch (SQLException e) {
            String message = "Exception during adding participant faze while creating race " + entity.toString();
            throw new DalException(message, e);
        }
    }

    @Override
    // TODO: need clean up
    // TODO: test cascade update
    public long update(Race entity) {
        long racesAffected = updateRace(entity);
        updateParticipants(entity);

        return racesAffected;
    }

    @Override
    public boolean delete(Long id) {
        return executor.delete(TABLE_NAME, id);
    }

    private List<Race> findByColumnPart(String call, RaceStatus status, String part, long offset, long limit) throws DalException {
        try {
            return executor.executeFindManyCall(call, status.getName(), startsWith(part), limit, offset);
        } catch (DalException e) {
            String message = callErrorMessage(call, status, part, limit, offset);
            throw new DalException(message, e);
        }
    }

    @Override
    public List<Race> findByRacecourseId(RaceStatus status, long racecourse, int offset, int limit) {
        @Language("MySQL")
        final String call = "{ CALL find_by_racecourse_id(?, ?, ?, ?) }";

        try {
            return executor.executeFindManyCall(call, status.getName(), racecourse, limit, offset);
        } catch (DalException e) {
            String message = callErrorMessage("find_by_racecourse_id", status, racecourse, limit, offset);
            throw new DalException(message, e);
        }
    }

    @Override
    public int findByRacecourseIdCount(RaceStatus status, long racecourse) {
        @Language("MySQL")
        final String sqlStatement = "SELECT COUNT(*) AS count FROM race WHERE status = ? AND racecourse_id = ?";

        return executor.executeCountQuery(sqlStatement, status.getName(), racecourse);
    }

    @Override
    public List<Race> findByRacecourse(RaceStatus status, String racecourse, int offset, int limit) {
        @Language("MySQL")
        final String call = "{ CALL find_by_racecourse_name(?, ?, ?, ?) }";

        return findByColumnPart(call, status, racecourse, offset, limit);
    }

    @Override
    public int findByRacecourseCount(RaceStatus status, String racecourse) {
        @Language("MySQL")
        String sqlStatement =
                "SELECT  COUNT(*) AS count " +
                "FROM race " +
                "   INNER JOIN racecourse " +
                "       ON race.racecourse_id = racecourse.id " +
                "WHERE status = ? AND racecourse.name LIKE ?";

        return executor.executeCountQuery(sqlStatement, status.getName(), startsWith(racecourse));
    }

    @Override
    public List<Race> findInTimestampDiapason(RaceStatus status, Timestamp begin, Timestamp end, int offset, int limit) {
        @Language("MySQL")
        final String call = "{ CALL find_in_timestamp_diapason(?, ?, ?, ?, ?) }";

        try(CallableStatement statement = connection.prepareCall(call)) {
            statement.setString(1, status.getName());
            statement.setTimestamp(2, begin);
            statement.setTimestamp(3, end);
            statement.setLong(4, limit);
            statement.setLong(5, offset);

            statement.execute();
            return mapRaces(statement);
        } catch (SQLException e) {
            String message = callErrorMessage(
                    "find_by_racecourse_name",
                    status,
                    begin,
                    end,
                    limit,
                    offset
            );
            throw new DalException(message, e);
        }
    }

    @Override
    public int findInTimestampDiapasonCount(RaceStatus status, Timestamp begin, Timestamp end) {
        @Language("MySQL")
        final String sqlStatement =
                "SELECT COUNT(*) AS count " +
                "FROM race " +
                "WHERE status = ? AND start_date_time BETWEEN ? AND ?";

        return executor.executeCountQuery(sqlStatement, status.getName(), begin, end);
    }

    @Override
    public List<Race> findInTimestampDiapasonOnRacecourse(RaceStatus status, long racecourse, Timestamp begin, Timestamp end, int offset, int limit) {
        @Language("MySQL")
        final String call = "{ CALL find_in_timestamp_diapason_by_racecourse_id(?, ?, ?, ?, ?, ?) }";

        try {
            return executor.executeFindManyCall(call, status.getName(), racecourse, begin, end, limit, offset);
        } catch (DalException e) {
            String message = callErrorMessage(
                    "find_by_racecourse_name",
                    status,
                    racecourse,
                    begin,
                    end,
                    limit,
                    offset
            );
            throw new DalException(message, e);
        }
    }

    @Override
    public int findInTimestampDiapasonOnRacecourseCount(
            RaceStatus status, long racecourse, Timestamp begin, Timestamp end
    ) {
        @Language("MySQL")
        final String sqlStatement =
                "SELECT COUNT(*) AS count " +
                "FROM race " +
                "WHERE status = ? AND racecourse_id = ? AND start_date_time BETWEEN ? AND ?";

        return executor.executeCountQuery(sqlStatement, status.getName(), racecourse, begin, end);
    }

    @Override
    public List<Race> findByNamePart(RaceStatus status, String name, int offset, int limit) {
        @Language("MySQL")
        final String call = "{ CALL find_by_name(?, ?, ?, ?) }";

        return findByColumnPart(call, status, name, offset, limit);
    }

    @Override
    public int findByNamePartCount(RaceStatus status, String name) {
        @Language("MySQL")
        String sqlStatement = "SELECT COUNT(*) AS count FROM race WHERE status = ? AND name LIKE ?";

        return executor.executeCountQuery(sqlStatement, status.getName(), startsWith(name));
    }

    @Override
    public boolean updateStatus(long id, RaceStatus status) {
        @Language("MySQL")
        final String sqlStatement = "UPDATE race SET status = ? WHERE id = ?";

        return executor.executeUpdateQuery(sqlStatement, status.getName(), id) > 0;
    }
}
