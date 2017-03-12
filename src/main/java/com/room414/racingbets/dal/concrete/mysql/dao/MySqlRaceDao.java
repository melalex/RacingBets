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

    private List<Race> getRaces(String sqlStatement) throws DalException {
        final String call = "{ CALL get_races(?) }";

        try(CallableStatement statement = connection.prepareCall(call)) {
            statement.setString(1, sqlStatement);
            statement.execute();
            return mapRaces(statement);
        } catch (SQLException e) {
            String message = String.format("Exception during getting Races with subquery '%s'", sqlStatement);
            throw new DalException(message, e);
        }
    }

    private Race getRace(String sqlStatement) throws DalException {
        List<Race> result = getRaces(sqlStatement);
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

        try(PreparedStatement statement = connection.prepareStatement(sqlStatement)) {
            for (Participant participant : entity.getParticipants()) {
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
    // TODO: injection
    public Race find(Long id) throws DalException {
        final String sqlStatement = String.format("SELECT * FROM race WHERE id = %d", id);

        return getRace(sqlStatement);
    }

    @Override
    public List<Race> findAll() throws DalException {
        //language=MySQL
        final String sqlStatement = "SELECT * FROM race";

        return getRaces(sqlStatement);
    }

    @Override
    public List<Race> findAll(long offset, long limit) throws DalException {
        final String sqlStatement = String.format("SELECT * FROM race LIMIT %d OFFSET %d", limit, offset);

        return getRaces(sqlStatement);
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


    @Override
    public List<Race> findByRacecourseId(RaceStatus status, long racecourse, long offset, long limit) throws DalException {
        final String sqlStatement = String.format(
                "SELECT * FROM race WHERE status = '%s' AND race.racecourse_id = %d LIMIT %d OFFSET %d",
                status.getName(),
                racecourse,
                limit,
                offset
        );

        return getRaces(sqlStatement);
    }

    @Override
    public long findByRacecourseIdCount(RaceStatus status, long racecourse) throws DalException {
        final String column_name = "race.racecourse_id";

        return executor.findByForeignKeyCount(TABLE_NAME, column_name, racecourse);
    }

    @Override
    public List<Race> findByRacecourse(RaceStatus status, String racecourse, long offset, long limit) throws DalException {
        final String sqlStatement = String.format(
                "SELECT * FROM race " +
                "   INNER JOIN racecourse " +
                "       ON race.racecourse_id = racecourse.id " +
                "WHERE status = '%s' AND racecourse.name LIKE '%s' " +
                "LIMIT %d OFFSET %d",
                status.getName(),
                startsWith(racecourse),
                limit,
                offset
        );

        return getRaces(sqlStatement);
    }

    @Override
    public long findByRacecourseCount(RaceStatus status, String racecourse) throws DalException {
        final String sqlStatement = String.format(
                "SELECT * FROM race " +
                "   INNER JOIN racecourse " +
                "       ON race.racecourse_id = racecourse.id " +
                "WHERE status = '%s' AND racecourse.name LIKE '?'",
                status.getName()
        );

        return executor.findByColumnPartCount(TABLE_NAME, racecourse);
    }

    @Override
    public List<Race> findInTimestampDiapason(RaceStatus status, Timestamp begin, Timestamp end, long offset, long limit) {
        return null;
    }

    @Override
    public long findInTimestampDiapasonCount(RaceStatus status, Timestamp begin, Timestamp end) {
        return 0;
    }

    @Override
    public List<Race> findInTimestampDiapasonOnRacecourse(RaceStatus status, long racecourse, Timestamp begin, Timestamp end, long offset, long limit) {
        return null;
    }

    @Override
    public long findInTimestampDiapasonOnRacecourseCount(RaceStatus status, long racecourse, Timestamp begin, Timestamp end) {
        return 0;
    }

    @Override
    public List<Race> findByNamePart(RaceStatus raceStatus, String namePart, long offset, long limit) throws DalException {
        return null;
    }

    @Override
    public long findByNamePartCount(RaceStatus raceStatus, String namePart) throws DalException {
        return 0;
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
