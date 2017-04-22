package com.room414.racingbets.dal.concrete.mysql.dao;

import com.room414.racingbets.dal.abstraction.dao.RaceDao;
import com.room414.racingbets.dal.abstraction.exception.DalException;
import com.room414.racingbets.dal.abstraction.exception.InvalidIdException;
import com.room414.racingbets.dal.concrete.mysql.infrastructure.MySqlMapHelper;
import com.room414.racingbets.dal.concrete.mysql.infrastructure.MySqlSharedDelegate;
import com.room414.racingbets.dal.domain.builders.RaceBuilder;
import com.room414.racingbets.dal.domain.entities.FilterParams;
import com.room414.racingbets.dal.domain.entities.Participant;
import com.room414.racingbets.dal.domain.entities.Race;
import com.room414.racingbets.dal.domain.enums.RaceStatus;
import org.intellij.lang.annotations.Language;

import java.math.BigDecimal;
import java.sql.*;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;
import java.util.stream.Collectors;

import static com.room414.racingbets.dal.concrete.mysql.infrastructure.MySqlDaoHelper.*;
import static com.room414.racingbets.dal.concrete.mysql.infrastructure.MySqlSharedDelegate.MYSQL_FOREIGN_KEY_CONSTRAINT;

/**
 * Implementation of RaceDao that uses JDBC as data source.
 *
 * @see RaceDao
 * @author Alexander Melashchenko
 * @version 1.0 28 Feb 2017
 */
public class MySqlRaceDao implements RaceDao {
    private static final String TABLE_NAME = "race";

    private Connection connection;
    private MySqlSharedDelegate<Race> executor;

    MySqlRaceDao(Connection connection) {
        this.connection = connection;
        this.executor = new MySqlSharedDelegate<>(
                connection,
                statement -> getResultWithArray(statement, this::mapRaces),
                statement -> getResultListWithArray(statement, this::mapRaces)
        );
    }

    private List<Race> mapRaces(Statement statement) throws SQLException {
        try (ResultSet resultSet = statement.getResultSet()) {
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

        Map<Long, RaceBuilder> builderById = new LinkedHashMap<>();

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
                entity.getTrackCondition() != null ? entity.getTrackCondition().getName() : null,
                entity.getRaceType() != null ? entity.getRaceType().getName() : null,
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
            if(e.getErrorCode() == MYSQL_FOREIGN_KEY_CONSTRAINT){
                String message = "Invalid foreign key";
                throw new InvalidIdException(message, e);
            }

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

    private List<Race> callFilterProcedure(FilterParams params) {
        @Language("MySQL")
        final String call = "{ CALL filter_races(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?) }";

        try (CallableStatement statement = connection.prepareCall(call)) {
            setNullable(statement, 1, params.getRaceStatus(), Types.VARCHAR);
            setNullable(statement, 2, params.getId(), Types.INTEGER);
            setNullable(statement, 3, params.getRacecourseId(), Types.INTEGER);
            setNullable(statement, 4, params.getHorseId(), Types.INTEGER);
            setNullable(statement, 5, params.getTrainerId(), Types.INTEGER);
            setNullable(statement, 6, params.getJockeyId(), Types.INTEGER);
            setNullable(statement, 7, params.getName(), Types.VARCHAR);
            setNullable(statement, 8, params.getBegin(), Types.TIMESTAMP);
            setNullable(statement, 9, params.getEnd(), Types.TIMESTAMP);
            setNullable(statement, 10, params.getLimit(), Types.INTEGER);
            setNullable(statement, 11, params.getOffset(), Types.INTEGER);

            statement.execute();

            return mapRaces(statement);
        } catch (SQLException e) {
            String message = callErrorMessage("filter_races", params);
            throw new DalException(message, e);
        }
    }

    private void setNullable(PreparedStatement statement, int number, Object value, int type) throws SQLException {
        if (value != null) {
            statement.setObject(number, value);
        } else {
            statement.setNull(number, type);
        }
    }

    @Override
    public Race find(Long id) {
        FilterParams params = new FilterParams();
        params.setId(id);
        params.setOffset(0);
        params.setLimit(1);

        List<Race> result = callFilterProcedure(params);
        if (!result.isEmpty()) {
            return result.get(0);
        } else {
            return null;
        }
    }

    @Override
    public List<Race> findAll() {
        FilterParams params = new FilterParams();
        params.setOffset(0);
        params.setLimit(Integer.MAX_VALUE);

        return callFilterProcedure(params);
    }

    @Override
    public List<Race> findAll(int offset, int limit) {
        FilterParams params = new FilterParams();
        params.setOffset(offset);
        params.setLimit(limit);

        return callFilterProcedure(params);
    }

    @Override
    public List<Race> filter(FilterParams params) {
        return callFilterProcedure(params);
    }

    @Override
    public int count(FilterParams params) {
        @Language("MySQL")
        final String call = "{ CALL count_races(?, ?, ?, ?, ?, ?, ?, ?, ?, ?) }";

        try (CallableStatement statement = connection.prepareCall(call)) {
            setNullable(statement, 1, params.getRaceStatus(), Types.VARCHAR);
            setNullable(statement, 2, params.getId(), Types.INTEGER);
            setNullable(statement, 3, params.getRacecourseId(), Types.INTEGER);
            setNullable(statement, 4, params.getHorseId(), Types.INTEGER);
            setNullable(statement, 5, params.getTrainerId(), Types.INTEGER);
            setNullable(statement, 6, params.getJockeyId(), Types.INTEGER);
            setNullable(statement, 7, params.getName(), Types.VARCHAR);
            setNullable(statement, 8, params.getBegin(), Types.TIMESTAMP);
            setNullable(statement, 9, params.getEnd(), Types.TIMESTAMP);

            statement.registerOutParameter(10, Types.INTEGER);

            statement.execute();

            return statement.getInt("count");
        } catch (SQLException e) {
            String message = callErrorMessage("count_races", params);
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
            if(e.getErrorCode() == MYSQL_FOREIGN_KEY_CONSTRAINT){
                String message = "Invalid foreign key";
                throw new InvalidIdException(message, e);
            }

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

    @Override
    public boolean updateStatus(long id, RaceStatus status) {
        @Language("MySQL")
        final String sqlStatement = "UPDATE race SET status = ? WHERE id = ?";

        return executor.executeUpdateQuery(sqlStatement, status.getName(), id) > 0;
    }
}
