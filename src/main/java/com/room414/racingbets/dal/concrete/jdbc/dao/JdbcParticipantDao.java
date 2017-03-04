package com.room414.racingbets.dal.concrete.jdbc.dao;

import com.room414.racingbets.dal.abstraction.dao.ParticipantDao;
import com.room414.racingbets.dal.abstraction.exception.DalException;
import com.room414.racingbets.dal.concrete.jdbc.infrastructure.JdbcCrudExecutor;
import com.room414.racingbets.dal.concrete.jdbc.infrastructure.JdbcMapHelper;
import com.room414.racingbets.dal.domain.entities.Participant;
import com.room414.racingbets.dal.abstraction.infrastructure.Pair;

import java.sql.*;
import java.util.List;

import static com.room414.racingbets.dal.concrete.jdbc.infrastructure.JdbcDaoHelper.defaultErrorMessage;
import static com.room414.racingbets.dal.concrete.jdbc.infrastructure.JdbcDaoHelper.getResult;
import static com.room414.racingbets.dal.concrete.jdbc.infrastructure.JdbcDaoHelper.getResultList;

/**
 * Implementation of ParticipantDao that uses JDBC as data source.
 *
 * @see ParticipantDao
 * @author Alexander Melashchenko
 * @version 1.0 28 Feb 2017
 */
public class JdbcParticipantDao implements ParticipantDao {
    private static String TABLE_NAME = "participant";

    private Connection connection;
    private JdbcCrudExecutor<Participant> executor;

    JdbcParticipantDao(Connection connection) {
        this.connection = connection;
        this.executor = new JdbcCrudExecutor<>(connection, JdbcMapHelper::mapParticipant);
    }


    @Override
    public void create(Participant entity) throws DalException {
        throw new DalException("It is impossible to create a Participant separate from the Race");
    }

    @Override
    public Participant find(Long id) throws DalException {
        //language=MySQL
        final String sqlStatement =
                "SELECT * FROM participant " +
                "INNER JOIN race " +
                "   ON participant.race_id = race.id " +
                "INNER JOIN trainer " +
                "   ON participant.trainer_id = trainer.id " +
                "INNER JOIN jockey " +
                "   ON participant.jockey_id = jockey.id " +
                "INNER JOIN horse " +
                "   ON participant.horse_id = horse.id " +
                "WHERE participant.id = ?";

        return executor.find(id, sqlStatement);
    }

    @Override
    public List<Participant> findAll() throws DalException {
        //language=MySQL
        final String sqlStatement =
                "SELECT * FROM participant " +
                "INNER JOIN race " +
                "   ON participant.race_id = race.id " +
                "INNER JOIN trainer " +
                "   ON participant.trainer_id = trainer.id " +
                "INNER JOIN jockey " +
                "   ON participant.jockey_id = jockey.id " +
                "INNER JOIN horse " +
                "   ON participant.horse_id = horse.id";

        return executor.findAll(sqlStatement);
    }

    @Override
    public List<Participant> findAll(long offset, long limit) throws DalException {
        //language=MySQL
        final String sqlStatement =
                "SELECT * FROM participant " +
                "INNER JOIN race " +
                "   ON participant.race_id = race.id " +
                "INNER JOIN trainer " +
                "   ON participant.trainer_id = trainer.id " +
                "INNER JOIN jockey " +
                "   ON participant.jockey_id = jockey.id " +
                "INNER JOIN horse " +
                "   ON participant.horse_id = horse.id " +
                "LIMIT ? OFFSET ?";

        return executor.findAll(sqlStatement, limit, offset);
    }

    @Override
    public long count() throws DalException {
        return executor.count(TABLE_NAME);
    }

    /**
     * Don't change participants race
     */
    @Override
    public long update(Participant entity) throws DalException {
        String sqlStatement =
                "UPDATE participant " +
                "SET number = ?, horse_id = ?, carried_weight = ?, topspeed = ?, official_rating = ?, " +
                "   jockey_id = ?, trainer_id = ?, place = ?, odds = ? " +
                "WHERE id = ?";

        try(PreparedStatement statement = connection.prepareStatement(sqlStatement)) {
            statement.setInt(1, entity.getNumber());
            statement.setLong(2, entity.getHorse().getId());
            statement.setFloat(3, entity.getCarriedWeight());
            statement.setInt(4, entity.getTopSpeed());
            statement.setInt(5, entity.getOfficialRating());
            statement.setLong(6, entity.getJockey().getId());
            statement.setLong(7, entity.getTrainer().getId());
            statement.setInt(8, entity.getPlace());
            statement.setDouble(9, entity.getOdds());
            statement.setLong(10, entity.getId());

            return statement.executeUpdate();
        } catch (SQLException e) {
            String message = defaultErrorMessage(
                    sqlStatement,
                    entity.getNumber(),
                    entity.getHorse().getId(),
                    entity.getCarriedWeight(),
                    entity.getTopSpeed(),
                    entity.getOfficialRating(),
                    entity.getJockey().getId(),
                    entity.getTrainer().getId(),
                    entity.getPlace(),
                    entity.getOdds(),
                    entity.getId()

            );
            throw new DalException(message, e);
        }
    }

    @Override
    public boolean delete(Long id) throws DalException {
        return executor.delete(TABLE_NAME, id);
    }

    private Pair<Participant, Timestamp> mapWhoAndWhen(ResultSet resultSet) throws SQLException {
        final String RACE_START_COLUMN = "race.start_date_time";

        Pair<Participant, Timestamp> result = new Pair<>();

        result.setFirstElement(JdbcMapHelper.mapParticipant(resultSet));
        result.setSecondElement(resultSet.getTimestamp(RACE_START_COLUMN));

        return result;
    }

    private List<Pair<Participant, Timestamp>> findByForeignKey(String sqlStatement, long key, long offset, long limit) throws DalException {
        try(PreparedStatement statement = connection.prepareStatement(sqlStatement)) {
            statement.setLong(1, key);
            statement.setLong(2, limit);
            statement.setLong(3, offset);

            return getResultList(statement, this::mapWhoAndWhen);
        } catch (SQLException e) {
            String message = defaultErrorMessage(sqlStatement, key);
            throw new DalException(message, e);
        }
    }

    private long findByForeignKeyCount(String columnName, long key) throws DalException {
        final String sqlStatement = "SELECT Count(*) FROM participant WHERE ? = ?";

        try(PreparedStatement statement = connection.prepareStatement(sqlStatement)) {
            statement.setString(1, columnName);
            statement.setLong(2, key);

            return getResult(statement, JdbcMapHelper::mapCount);
        } catch (SQLException e) {
            String message = defaultErrorMessage(sqlStatement, key);
            throw new DalException(message, e);
        }
    }

    @Override
    public List<Pair<Participant, Timestamp>> findByHorseId(long id, long offset, long limit) throws DalException {
        //language=MySQL
        final String sqlStatement =
                "SELECT * FROM participant " +
                "INNER JOIN race " +
                "   ON participant.race_id = race.id " +
                "INNER JOIN trainer " +
                "   ON participant.trainer_id = trainer.id " +
                "INNER JOIN jockey " +
                "   ON participant.jockey_id = jockey.id " +
                "INNER JOIN horse " +
                "   ON participant.horse_id = horse.id " +
                "WHERE horse.id = ? " +
                "   LIMIT ? OFFSET ?";

        return findByForeignKey(sqlStatement, id, offset, limit);
    }

    @Override
    public long findByHorseIdCount(long id) throws DalException {
        final String columnName = "horse_id";

        return findByForeignKeyCount(columnName, id);
    }

    @Override
    public List<Pair<Participant, Timestamp>> findByOwnerId(long id, long offset, long limit) throws DalException {
        //language=MySQL
        final String sqlStatement =
                "SELECT * FROM participant " +
                "INNER JOIN race " +
                "   ON participant.race_id = race.id " +
                "INNER JOIN trainer " +
                "   ON participant.trainer_id = trainer.id " +
                "INNER JOIN jockey " +
                "   ON participant.jockey_id = jockey.id " +
                "INNER JOIN horse " +
                "   ON participant.horse_id = horse.id " +
                "WHERE owner.id = ? " +
                "   LIMIT ? OFFSET ?";

        return findByForeignKey(sqlStatement, id, offset, limit);
    }

    @Override
    public long findByOwnerIdCount(long id) throws DalException {
        final String columnName = "owner_id";

        return findByForeignKeyCount(columnName, id);
    }

    @Override
    public List<Pair<Participant, Timestamp>> findByJockeyId(long id, long offset, long limit) throws DalException {
        //language=MySQL
        final String sqlStatement =
                "SELECT * FROM participant " +
                "INNER JOIN race " +
                "   ON participant.race_id = race.id " +
                "INNER JOIN trainer " +
                "   ON participant.trainer_id = trainer.id " +
                "INNER JOIN jockey " +
                "   ON participant.jockey_id = jockey.id " +
                "INNER JOIN horse " +
                "   ON participant.horse_id = horse.id " +
                "WHERE jockey.id = ? " +
                "   LIMIT ? OFFSET ?";

        return findByForeignKey(sqlStatement, id, offset, limit);
    }

    @Override
    public long findByJockeyIdCount(long id) throws DalException {
        final String columnName = "jockey_id";

        return findByForeignKeyCount(columnName, id);
    }

    @Override
    public List<Pair<Participant, Timestamp>> findByTrainerId(long id, long offset, long limit) throws DalException {
        //language=MySQL
        final String sqlStatement =
                "SELECT * FROM participant " +
                "INNER JOIN race " +
                "   ON participant.race_id = race.id " +
                "INNER JOIN trainer " +
                "   ON participant.trainer_id = trainer.id " +
                "INNER JOIN jockey " +
                "   ON participant.jockey_id = jockey.id " +
                "INNER JOIN horse " +
                "   ON participant.horse_id = horse.id " +
                "WHERE trainer.id = ? " +
                "   LIMIT ? OFFSET ?";

        return findByForeignKey(sqlStatement, id, offset, limit);
    }

    @Override
    public long findByTrainerIdCount(long id) throws DalException {
        final String columnName = "trainer_id";

        return findByForeignKeyCount(columnName, id);
    }
}
