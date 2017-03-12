package com.room414.racingbets.dal.concrete.mysql.dao;

import com.room414.racingbets.dal.abstraction.dao.RaceDao;
import com.room414.racingbets.dal.abstraction.exception.DalException;
import com.room414.racingbets.dal.concrete.mysql.infrastructure.MySqlMapHelper;
import com.room414.racingbets.dal.concrete.mysql.infrastructure.MySqlSharedExecutor;
import com.room414.racingbets.dal.domain.builders.RaceBuilder;
import com.room414.racingbets.dal.domain.entities.Participant;
import com.room414.racingbets.dal.domain.entities.Race;
import com.room414.racingbets.dal.domain.enums.RaceStatus;

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
// TODO: ordered
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

    private Race mapRace(Statement statement) throws SQLException {
        List<Race> result = mapRaces(statement);
        if (!result.isEmpty()) {
            return result.get(0);
        } else {
            return null;
        }
    }

    private void createRace(Race entity) throws DalException {
        final String sqlStatement =
                "INSERT INTO race " +
                "   (name, status, racecourse_id, start_date_time, min_bet, commission, going, " +
                "   race_type, race_class, min_age, min_rating, max_rating, distance) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try(PreparedStatement statement = connection.prepareStatement(sqlStatement, Statement.RETURN_GENERATED_KEYS)) {
            statement.setString(1, entity.getName());
            statement.setString(2, entity.getRaceStatus().getName());
            statement.setLong(3, entity.getRacecourse().getId());
            statement.setTimestamp(4, entity.getStart());
            statement.setBigDecimal(5, entity.getMinBet());
            statement.setDouble(6, entity.getCommission());
            statement.setString(7, entity.getTrackCondition().getName());
            statement.setString(8, entity.getRaceType().getName());
            statement.setInt(9, entity.getRaceClass());
            statement.setInt(10, entity.getMinAge());
            statement.setInt(11, entity.getMinRating());
            statement.setInt(12, entity.getMaxRating());
            statement.setFloat(13, entity.getDistance());

            createEntity(statement, entity::setId);
        } catch (SQLException e) {
            String message = defaultErrorMessage(
                    sqlStatement,
                    entity.getName(),
                    entity.getRaceStatus().getName(),
                    entity.getRacecourse().getId(),
                    entity.getStart(),
                    entity.getMinBet(),
                    entity.getCommission(),
                    entity.getRaceType().getName(),
                    entity.getRaceClass(),
                    entity.getMinAge(),
                    entity.getMinRating(),
                    entity.getMaxRating(),
                    entity.getDistance(),
                    entity.getTrackCondition().getName()
            );
            throw new DalException(message, e);
        }
    }

    private void createParticipants(Race entity) throws DalException {
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

    private void createPrizes(Race entity) throws DalException {
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
    public void create(Race entity) throws DalException {
        createRace(entity);
        createParticipants(entity);
        createPrizes(entity);
    }

    @Override
    public Race find(Long id) throws DalException {
        final String call = "{ CALL find_race_by_id(?) }";

        try(CallableStatement statement = connection.prepareCall(call)) {
            statement.setLong(1, id);
            statement.execute();
            return mapRace(statement);
        } catch (SQLException e) {
            String message = callErrorMessage("find_race_by_id", id);
            throw new DalException(message, e);
        }
    }

    @Override
    public List<Race> findAll() throws DalException {
        //language=MySQL
        final String call = "{ CALL find_all_races() }";

        try(CallableStatement statement = connection.prepareCall(call)) {
            statement.execute();
            return mapRaces(statement);
        } catch (SQLException e) {
            String message = callErrorMessage("find_all_races");
            throw new DalException(message, e);
        }
    }

    @Override
    public List<Race> findAll(long offset, long limit) throws DalException {
        //language=MySQL
        final String call = "{ CALL find_all_races_limit_offset(?, ?) }";

        try(CallableStatement statement = connection.prepareCall(call)) {
            statement.setLong(1, limit);
            statement.setLong(2, offset);

            statement.execute();
            return mapRaces(statement);
        } catch (SQLException e) {
            String message = callErrorMessage("find_all_races_limit_offset", limit, offset);
            throw new DalException(message, e);
        }
    }

    @Override
    public long count() throws DalException {
        return executor.count(TABLE_NAME);
    }

    @Override
    public long update(Race entity) throws DalException {
        final String sqlStatement =
                "UPDATE race " +
                "SET name = ?, status = ?, racecourse_id = ?, start_date_time = ?, min_bet = ?, " +
                "   commission = ?, going = ?, race_type = ?, race_class = ?, " +
                "   min_age = ?, min_rating = ?, max_rating = ?, distance = ? " +
                "WHERE id = ?";

        try(PreparedStatement statement = connection.prepareStatement(sqlStatement)) {
            statement.setString(1, entity.getName());
            statement.setString(2, entity.getRaceStatus().getName());
            statement.setLong(3, entity.getRacecourse().getId());
            statement.setTimestamp(4, entity.getStart());
            statement.setBigDecimal(5, entity.getMinBet());
            statement.setDouble(6, entity.getCommission());
            statement.setString(7, entity.getTrackCondition().getName());
            statement.setString(8, entity.getRaceType().getName());
            statement.setInt(9, entity.getRaceClass());
            statement.setInt(10, entity.getMinAge());
            statement.setInt(11, entity.getMinRating());
            statement.setInt(12, entity.getMaxRating());
            statement.setFloat(13, entity.getDistance());
            statement.setLong(14, entity.getId());

            return statement.executeUpdate();
        } catch (SQLException e) {
            String message = defaultErrorMessage(
                    sqlStatement,
                    entity.getName(),
                    entity.getRaceStatus().getName(),
                    entity.getRacecourse().getId(),
                    entity.getStart(),
                    entity.getMinBet(),
                    entity.getCommission(),
                    entity.getRaceType().getName(),
                    entity.getRaceClass(),
                    entity.getMinAge(),
                    entity.getMinRating(),
                    entity.getMaxRating(),
                    entity.getDistance(),
                    entity.getTrackCondition().getName()
            );
            throw new DalException(message, e);
        }
    }

    @Override
    public boolean delete(Long id) throws DalException {
        return executor.delete(TABLE_NAME, id);
    }

    private List<Race> findByColumnPart(String call, RaceStatus status, String part, long offset, long limit) throws DalException {
        try(CallableStatement statement = connection.prepareCall(call)) {
            statement.setString(1, status.getName());
            statement.setString(2, startsWith(part));
            statement.setLong(3, limit);
            statement.setLong(4, offset);

            statement.execute();
            return mapRaces(statement);
        } catch (SQLException e) {
            String message = callErrorMessage(call, status, part, limit, offset);
            throw new DalException(message, e);
        }
    }

    private long findByColumnPartCount(String sqlStatement, RaceStatus status, String part) throws DalException {
        try (PreparedStatement statement = connection.prepareStatement(sqlStatement)) {
            statement.setString(1, status.getName());
            statement.setString(2, startsWith(part));
            statement.execute();
            return getResult(statement, MySqlMapHelper::mapCount);
        } catch (SQLException e) {
            String message = defaultErrorMessage(sqlStatement, status.getName(), part);
            throw new DalException(message, e);
        }
    }

    @Override
    public List<Race> findByRacecourseId(RaceStatus status, long racecourse, long offset, long limit) throws DalException {
        //language=MySQL
        final String call = "{ CALL find_by_racecourse_id(?, ?, ?, ?) }";

        try(CallableStatement statement = connection.prepareCall(call)) {
            statement.setString(1, status.getName());
            statement.setLong(2, racecourse);
            statement.setLong(3, limit);
            statement.setLong(4, offset);

            statement.execute();
            return mapRaces(statement);
        } catch (SQLException e) {
            String message = callErrorMessage("find_by_racecourse_id", status, racecourse, limit, offset);
            throw new DalException(message, e);
        }
    }

    @Override
    public long findByRacecourseIdCount(RaceStatus status, long racecourse) throws DalException {
        final String sqlStatement = "SELECT  COUNT(*) AS count FROM race WHERE status = ? AND racecourse_id = ?";

        try (PreparedStatement statement = connection.prepareStatement(sqlStatement)) {
            statement.setString(1, status.getName());
            statement.setLong(2, racecourse);
            statement.execute();
            return getResult(statement, MySqlMapHelper::mapCount);
        } catch (SQLException e) {
            String message = defaultErrorMessage(sqlStatement, status.getName(), racecourse);
            throw new DalException(message, e);
        }
    }

    @Override
    public List<Race> findByRacecourse(RaceStatus status, String racecourse, long offset, long limit) throws DalException {
        //language=MySQL
        final String call = "{ CALL find_by_racecourse_name(?, ?, ?, ?) }";

        return findByColumnPart(call, status, racecourse, offset, limit);
    }

    @Override
    public long findByRacecourseCount(RaceStatus status, String racecourse) throws DalException {
        //language=MySQL
        String sqlStatement =
                "SELECT  COUNT(*) AS count " +
                "FROM race " +
                "   INNER JOIN racecourse " +
                "       ON race.racecourse_id = racecourse.id " +
                "WHERE status = ? AND racecourse.name LIKE ?";

        return findByColumnPartCount(sqlStatement, status, racecourse);
    }

    @Override
    public List<Race> findInTimestampDiapason(RaceStatus status, Timestamp begin, Timestamp end, long offset, long limit) throws DalException {
        //language=MySQL
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
            String message = callErrorMessage("find_by_racecourse_name", status, begin, end, limit, offset);
            throw new DalException(message, e);
        }
    }

    @Override
    public long findInTimestampDiapasonCount(RaceStatus status, Timestamp begin, Timestamp end) throws DalException {
        final String sqlStatement =
                "SELECT COUNT(*) AS count " +
                "FROM race " +
                "WHERE status = ? AND start_date_time BETWEEN ? AND ?";

        try (PreparedStatement statement = connection.prepareStatement(sqlStatement)) {
            statement.setString(1, status.getName());
            statement.setTimestamp(2, begin);
            statement.setTimestamp(3, end);

            statement.execute();
            return getResult(statement, MySqlMapHelper::mapCount);
        } catch (SQLException e) {
            String message = defaultErrorMessage(sqlStatement, status.getName(), begin, end);
            throw new DalException(message, e);
        }
    }

    @Override
    public List<Race> findInTimestampDiapasonOnRacecourse(RaceStatus status, long racecourse, Timestamp begin, Timestamp end, long offset, long limit) throws DalException {
        //language=MySQL
        final String call = "{ CALL find_in_timestamp_diapason_by_racecourse_id(?, ?, ?, ?, ?, ?) }";

        try(CallableStatement statement = connection.prepareCall(call)) {
            statement.setString(1, status.getName());
            statement.setLong(2, racecourse);
            statement.setTimestamp(3, begin);
            statement.setTimestamp(4, end);
            statement.setLong(5, limit);
            statement.setLong(6, offset);

            statement.execute();
            return mapRaces(statement);
        } catch (SQLException e) {
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
    public long findInTimestampDiapasonOnRacecourseCount(
            RaceStatus status, long racecourse, Timestamp begin, Timestamp end
    ) throws DalException {

        final String sqlStatement =
                "SELECT COUNT(*) AS count " +
                "FROM race " +
                "WHERE status = ? AND racecourse_id = ? AND start_date_time BETWEEN ? AND ?";

        try (PreparedStatement statement = connection.prepareStatement(sqlStatement)) {
            statement.setString(1, status.getName());
            statement.setLong(2, racecourse);
            statement.setTimestamp(3, begin);
            statement.setTimestamp(4, end);

            statement.execute();
            return getResult(statement, MySqlMapHelper::mapCount);
        } catch (SQLException e) {
            String message = defaultErrorMessage(sqlStatement, status.getName(), begin, end);
            throw new DalException(message, e);
        }
    }

    @Override
    public List<Race> findByNamePart(RaceStatus status, String name, long offset, long limit) throws DalException {
        //language=MySQL
        final String call = "{ CALL find_by_name(?, ?, ?, ?) }";

        return findByColumnPart(call, status, name, offset, limit);
    }

    @Override
    public long findByNamePartCount(RaceStatus status, String name) throws DalException {
        //language=MySQL
        String sqlStatement = "SELECT  COUNT(*) AS count FROM race WHERE status = ? AND name LIKE ?";

        return findByColumnPartCount(sqlStatement, status, name);
    }

    @Override
    public boolean updateStatus(long id, RaceStatus status) throws DalException {
        final String sqlStatement = "UPDATE race SET status = ? WHERE id = ?";

        try(PreparedStatement statement = connection.prepareStatement(sqlStatement)) {
            statement.setString(1, status.getName());
            statement.setLong(2, id);

            return statement.executeUpdate() > 0;
        } catch (SQLException e) {
            String message = defaultErrorMessage(sqlStatement, status.getName(), id);
            throw new DalException(message, e);
        }
    }
}
