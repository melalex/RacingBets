package com.room414.racingbets.dal.concrete.mysql.dao;

import com.room414.racingbets.dal.abstraction.dao.RaceDao;
import com.room414.racingbets.dal.abstraction.exception.DalException;
import com.room414.racingbets.dal.concrete.mysql.infrastructure.MySqlMapHelper;
import com.room414.racingbets.dal.concrete.mysql.infrastructure.MySqlSharedExecutor;
import com.room414.racingbets.dal.domain.entities.Participant;
import com.room414.racingbets.dal.domain.entities.Race;
import com.room414.racingbets.dal.domain.enums.RaceStatus;

import java.math.BigDecimal;
import java.sql.*;
import java.util.List;

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
    private MySqlSharedExecutor executor;

    MySqlRaceDao(Connection connection) {
        this.connection = connection;
        this.executor = new MySqlSharedExecutor<>(
                connection,
                statement -> getResultWithArray(statement, this::mapRace),
                statement -> getResultList(statement, this::mapRace)
        );
    }

    private List<Race> mapRace(ResultSet resultSet) {
        return null;
    }

    private void createRace(Race entity) throws DalException {
        final String sqlStatement =
                "INSERT INTO race " +
                "   (name, status, racecourse_id, start_date_time, min_bet, commission, going, " +
                "   race_type, race_class, min_age, min_rating, max_rating, distance) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

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
        final String sqlStatement = "INSERT INTO participant " +
                "(number, horse_id, race_id, carried_weight, topspeed, " +
                "official_rating, jockey_id, trainer_id, place, odds)  " +
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
            List<BigDecimal> prizes = entity.getPrizes();

            for (int i = 0; i < prizes.size(); i++) {
                statement.setLong(1, entity.getId());
                statement.setBigDecimal(2, prizes.get(i));
                statement.setInt(3, i + 1);

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
        //language=MySQL
        final String sqlStatement =
                "SELECT race.id, race.start_date_time, race.commission, race.distance, race.max_rating, " +
                "   race.min_age, race.min_bet, race.min_rating, race.name, race.race_class, " +
                "   race.race_type, race.status, race.going, racecourse.id, racecourse.name, " +
                "   racecourse.clerk, racecourse.contact " +
                "FROM (" +
                "   SELECT * FROM race " +
                "   INNER JOIN going " +
                "       ON race.going_id = going.id " +
                "   INNER JOIN racecourse " +
                "       ON race.racecourse_id = racecourse.id " +
                "   INNER JOIN country " +
                "       ON racecourse.country_id = country.id  " +
                "   WHERE race.id = ?" +
                ") AS race " +
                "UNION " +
                "SELECT * " +
                "FROM (" +
                "   SELECT * FROM race as race " +
                "   INNER JOIN going " +
                "       ON race.going_id = going.id " +
                "   INNER JOIN racecourse " +
                "       ON race.racecourse_id = racecourse.id " +
                "   WHERE race.id = ?" +
                ") ";

        return null;
    }

    @Override
    public List<Race> findAll() throws DalException {
        //language=MySQL
        final String sqlStatement =
                "SELECT * FROM race " +
                "INNER JOIN going " +
                "   ON race.going_id = going.id " +
                "INNER JOIN racecourse " +
                "   ON race.racecourse_id = racecourse.id " ;

        return null;
    }

    @Override
    public List<Race> findAll(long offset, long limit) throws DalException {
        //language=MySQL
        final String sqlStatement =
                "SELECT * FROM race " +
                "INNER JOIN going " +
                "   ON race.going_id = going.id " +
                "INNER JOIN racecourse " +
                "   ON race.racecourse_id = racecourse.id " +
                "LIMIT ? OFFSET ?";

        return null;
    }

    @Override
    public long count() throws DalException {
        return executor.count(TABLE_NAME);
    }

    @Override
    public long update(Race entity) throws DalException {
        final String sqlStatement =
                "UPDATE race INNER " +
                "JOIN going " +
                "   ON going.name = ? " +
                "SET name = ?, status = ?, racecourse_id = ?, start_date_time = ?, min_bet = ?, " +
                "   commission = ?, going_id = going.id, race_type = ?, race_class = ?, " +
                "   min_age = ?, min_rating = ?, max_rating = ?, distance = ? " +
                "WHERE id = ?";

        try(PreparedStatement statement = connection.prepareStatement(sqlStatement)) {
            statement.setString(1, entity.getTrackCondition().getName());
            statement.setString(2, entity.getName());
            statement.setString(3, entity.getRaceStatus().getName());
            statement.setLong(4, entity.getRacecourse().getId());
            statement.setTimestamp(5, entity.getStart());
            statement.setBigDecimal(6, entity.getMinBet());
            statement.setDouble(7, entity.getCommission());
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
        return null;
    }

    @Override
    public long findByRacecourseIdCount(RaceStatus status, long racecourse) {
        return 0;
    }

    @Override
    public List<Race> findByRacecourse(RaceStatus status, String racecourse, long offset, long limit) throws DalException {
        return null;
    }

    @Override
    public long findByRacecourseCount(RaceStatus status, String racecourse) {
        return 0;
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
