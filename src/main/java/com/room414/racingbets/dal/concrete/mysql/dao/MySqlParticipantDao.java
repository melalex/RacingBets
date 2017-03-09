package com.room414.racingbets.dal.concrete.mysql.dao;

import com.room414.racingbets.dal.abstraction.dao.HorseDao;
import com.room414.racingbets.dal.abstraction.dao.ParticipantDao;
import com.room414.racingbets.dal.abstraction.exception.DalException;
import com.room414.racingbets.dal.concrete.mysql.infrastructure.MySqlCrudExecutor;
import com.room414.racingbets.dal.concrete.mysql.infrastructure.MySqlMapHelper;
import com.room414.racingbets.dal.domain.entities.Participant;
import com.room414.racingbets.dal.abstraction.infrastructure.Pair;

import java.sql.*;
import java.util.List;

import static com.room414.racingbets.dal.concrete.mysql.infrastructure.MySqlDaoHelper.defaultErrorMessage;
import static com.room414.racingbets.dal.concrete.mysql.infrastructure.MySqlDaoHelper.getResult;

/**
 * Implementation of ParticipantDao that uses JDBC as data source.
 *
 * @see ParticipantDao
 * @author Alexander Melashchenko
 * @version 1.0 28 Feb 2017
 */
public class MySqlParticipantDao implements ParticipantDao {
    private static String TABLE_NAME = "participant";

    private Connection connection;
    private MySqlCrudExecutor<Participant> executor;
    private MySqlCrudExecutor<Pair<Participant, Timestamp>> foreignExecutor;
    private HorseDao horseDao;

    MySqlParticipantDao(Connection connection, HorseDao horseDao) {
        this.connection = connection;
        this.horseDao = horseDao;
        this.executor = new MySqlCrudExecutor<>(connection, rs -> MySqlMapHelper.mapParticipant(rs, horseDao));
        this.foreignExecutor = new MySqlCrudExecutor<>(connection, this::mapWhoAndWhen);
    }


    @Override
    public void create(Participant entity) throws DalException {
        throw new DalException("It is impossible to createUnitOfWorkFactory a Participant separate from the Race");
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
                "INNER JOIN trainer AS horse_trainer " +
                "   ON horse.trainer_id = horse_trainer.id " +
                "INNER JOIN owner AS horse_owner " +
                "   ON horse.owner_id = horse_owner.id " +
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
                "   ON participant.horse_id = horse.id " +
                "INNER JOIN trainer AS horse_trainer " +
                "   ON horse.trainer_id = horse_trainer.id " +
                "INNER JOIN owner AS horse_owner " +
                "   ON horse.owner_id = horse_owner.id ";
        ;

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
                "INNER JOIN trainer AS horse_trainer " +
                "   ON horse.trainer_id = horse_trainer.id " +
                "INNER JOIN owner AS horse_owner " +
                "   ON horse.owner_id = horse_owner.id " +
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

        result.setFirstElement(MySqlMapHelper.mapParticipant(resultSet, horseDao));
        result.setSecondElement(resultSet.getTimestamp(RACE_START_COLUMN));

        return result;
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
                "INNER JOIN trainer AS horse_trainer " +
                "   ON horse.trainer_id = horse_trainer.id " +
                "INNER JOIN owner AS horse_owner " +
                "   ON horse.owner_id = horse_owner.id " +
                "WHERE horse.id = ? " +
                "   LIMIT ? OFFSET ?";

        return foreignExecutor.findByForeignKey(sqlStatement, id, offset, limit);
    }

    @Override
    public long findByHorseIdCount(long id) throws DalException {
        final String columnName = "horse_id";

        return executor.findByForeignKeyCount(TABLE_NAME, columnName, id);
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
                "INNER JOIN trainer AS horse_trainer " +
                "   ON horse.trainer_id = horse_trainer.id " +
                "INNER JOIN owner AS horse_owner " +
                "   ON horse.owner_id = horse_owner.id " +
                "WHERE horse_owner.id = ? " +
                "   LIMIT ? OFFSET ?";

        return foreignExecutor.findByForeignKey(sqlStatement, id, offset, limit);
    }

    @Override
    public long findByOwnerIdCount(long id) throws DalException {
        //language=MySQL
        final String sqlStatement =
                "SELECT COUNT(*) AS count " +
                "FROM participant " +
                "   INNER JOIN horse " +
                "       ON horse.id = participant.horse_id " +
                "   INNER JOIN owner AS horse_owner " +
                "       ON horse.owner_id = horse_owner.id " +
                "WHERE horse_owner.id = ?";

        try(PreparedStatement statement = connection.prepareStatement(sqlStatement)) {
            statement.setLong(1, id);
            return getResult(statement, MySqlMapHelper::mapCount);
        } catch (SQLException e) {
            String message = defaultErrorMessage(sqlStatement, id);
            throw new DalException(message, e);
        }
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
                "INNER JOIN trainer AS horse_trainer " +
                "   ON horse.trainer_id = horse_trainer.id " +
                "INNER JOIN owner AS horse_owner " +
                "   ON horse.owner_id = horse_owner.id " +
                "WHERE jockey.id = ? " +
                "   LIMIT ? OFFSET ?";

        return foreignExecutor.findByForeignKey(sqlStatement, id, offset, limit);
    }

    @Override
    public long findByJockeyIdCount(long id) throws DalException {
        final String columnName = "jockey_id";

        return executor.findByForeignKeyCount(TABLE_NAME, columnName, id);
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
                "INNER JOIN trainer AS horse_trainer " +
                "   ON horse.trainer_id = horse_trainer.id " +
                "INNER JOIN owner AS horse_owner " +
                "   ON horse.owner_id = horse_owner.id " +
                "WHERE trainer.id = ? " +
                "   LIMIT ? OFFSET ?";

        return foreignExecutor.findByForeignKey(sqlStatement, id, offset, limit);
    }

    @Override
    public long findByTrainerIdCount(long id) throws DalException {
        final String columnName = "trainer_id";

        return executor.findByForeignKeyCount(TABLE_NAME, columnName, id);
    }
}
