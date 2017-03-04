package com.room414.racingbets.dal.concrete.jdbc.dao;

import com.room414.racingbets.dal.abstraction.dao.RaceDao;
import com.room414.racingbets.dal.abstraction.exception.DalException;
import com.room414.racingbets.dal.concrete.jdbc.infrastructure.JdbcFindByColumnExecutor;
import com.room414.racingbets.dal.concrete.jdbc.infrastructure.JdbcMapHelper;
import com.room414.racingbets.dal.domain.entities.Race;
import com.room414.racingbets.dal.domain.enums.RaceStatus;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.List;

import static com.room414.racingbets.dal.concrete.jdbc.infrastructure.JdbcDaoHelper.createEntity;
import static com.room414.racingbets.dal.concrete.jdbc.infrastructure.JdbcDaoHelper.defaultErrorMessage;

/**
 * Implementation of RaceDao that uses JDBC as data source.
 *
 * @see RaceDao
 * @author Alexander Melashchenko
 * @version 1.0 28 Feb 2017
 */
// TODO: ordered
public class JdbcRaceDao implements RaceDao {
    private static String TABLE_NAME = "race";

    private Connection connection;
    private JdbcFindByColumnExecutor<Race> executor;

    JdbcRaceDao(Connection connection) {
        this.connection = connection;
        this.executor = new JdbcFindByColumnExecutor<>(connection, JdbcMapHelper::mapRace);
    }

    @Override
    public void create(Race entity) throws DalException {
        final String sqlStatement =
                "INSERT INTO race " +
                "   (name, status, racecourse_id, start_date_time, min_bet, commission, going_id, " +
                "   race_type, race_class, min_age, min_rating, max_rating, distance) " +
                "SELECT ?, ?, ?, ?, ?, ?, going.name, ?, ?, ?, ?, ?, ? FROM going WHERE name = ?";

        try(PreparedStatement statement = connection.prepareStatement(sqlStatement)) {
            statement.setString(1, entity.getName());
            statement.setString(2, entity.getRaceStatus().getName());
            statement.setLong(3, entity.getRacecourse().getId());
            statement.setTimestamp(4, entity.getStart());
            statement.setBigDecimal(5, entity.getMinBet());
            statement.setDouble(6, entity.getCommission());
            statement.setString(7, entity.getRaceType().getName());
            statement.setInt(8, entity.getRaceClass());
            statement.setInt(9, entity.getMinAge());
            statement.setInt(10, entity.getMinRating());
            statement.setInt(11, entity.getMaxRating());
            statement.setFloat(11, entity.getDistance());
            statement.setString(13, entity.getTrackCondition().getName());

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

    @Override
    public Race find(Long id) throws DalException {
        final String sqlStatement =
                "SELECT * FROM (SELECT * FROM race WHERE id = ?) as race " +
                "INNER JOIN going " +
                "   ON race.going_id = going.id " +
                "INNER JOIN racecourse " +
                "   ON race.racecourse_id = racecourse.id LEFT OUTER JOIN participant "

        connection.prepareStatement(sqlStatement)

        return executor.find(id, sqlStatement);
    }

    @Override
    public List<Race> findAll() throws DalException {
        final String sqlStatement =
                "SELECT * FROM race " +
                "INNER JOIN going " +
                "   ON race.going_id = going.id " +
                "INNER JOIN racecourse " +
                "   ON race.racecourse_id = racecourse.id " +

        connection.prepareStatement(sqlStatement)

        return executor.findAll(sqlStatement);
    }

    @Override
    public List<Race> findAll(long offset, long limit) throws DalException {
        final String sqlStatement =
                "SELECT * FROM race " +
                "INNER JOIN going " +
                "   ON race.going_id = going.id " +
                "INNER JOIN racecourse " +
                "   ON race.racecourse_id = racecourse.id " +
                "LIMIT ? OFFSET ?";

connection.prepareStatement(sqlStatement)
        return executor.findAll(sqlStatement, limit, offset);
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
    public List<Race> findScheduledByRacecourse(String racecourse, long offset, long limit) throws DalException {
        final String sqlStatement =
                "SELECT * FROM race " +
                "INNER JOIN going " +
                "   ON race.going_id = going.id " +
                "WHERE race.name LIKE ? " +
                "LIMIT ? OFFSET ?";

connection.prepareStatement(sqlStatement)
        return executor.findByColumnPart(sqlStatement, racecourse, offset, limit);
    }

    @Override
    public long findScheduledByRacecourseCount(long id) {
        return 0;
    }

    @Override
    public List<Race> findFinishedByRacecourse(String racecourse, long offset, long limit) {
        return null;
    }

    @Override
    public long findFinishedByRacecourseCount(long id) {
        return 0;
    }

    @Override
    public List<Race> findScheduledInTimestampDiapason(Timestamp begin, Timestamp end, long offset, long limit) {
        return null;
    }

    @Override
    public long findScheduledInTimestampDiapasonCount(Timestamp begin, Timestamp end) {
        return 0;
    }

    @Override
    public List<Race> findFinishedInTimestampDiapason(Timestamp begin, Timestamp end, long offset, long limit) {
        return null;
    }

    @Override
    public long findFinishedInTimestampDiapasonCount(Timestamp begin, Timestamp end) {
        return 0;
    }

    @Override
    public List<Race> findScheduledInTimestampDiapasonOnRacecourse(String racecourse, Timestamp begin, Timestamp end, long offset, long limit) {
        return null;
    }

    @Override
    public long findScheduledInTimestampDiapasonOnRacecourseCount(String racecourse, Timestamp begin, Timestamp end) {
        return 0;
    }

    @Override
    public List<Race> findFinishedInTimestampDiapasonOnRacecourse(String racecourse, Timestamp begin, Timestamp end, long offset, long limit) {
        return null;
    }

    @Override
    public long findFinishedInTimestampDiapasonOnRacecourseCount(String racecourse, Timestamp begin, Timestamp end) {
        return 0;
    }

    @Override
    public List<Race> findByNamePart(String namePart, long offset, long limit) throws DalException {
        final String sqlStatement =
                "SELECT * FROM race " +
                "INNER JOIN going " +
                "   ON race.going_id = going.id " +
                "WHERE race.name LIKE ? " +
                "LIMIT ? OFFSET ?";
        connection.prepareStatement(sqlStatement)

        return executor.findByColumnPart(sqlStatement, namePart, offset, limit);

    }

    @Override
    public long findByNamePartCount(String namePart) throws DalException {
        final String sqlStatement = "SELECT * FROM race WHERE race.name LIKE ?";

        return executor.findByColumnPartCount(sqlStatement, namePart);
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
