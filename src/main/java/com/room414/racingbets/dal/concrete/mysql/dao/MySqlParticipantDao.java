package com.room414.racingbets.dal.concrete.mysql.dao;

import com.room414.racingbets.dal.abstraction.dao.ParticipantDao;
import com.room414.racingbets.dal.abstraction.exception.DalException;
import com.room414.racingbets.dal.concrete.mysql.infrastructure.MySqlSharedExecutor;
import com.room414.racingbets.dal.concrete.mysql.infrastructure.MySqlMapHelper;
import com.room414.racingbets.dal.domain.entities.Participant;
import com.room414.racingbets.dal.abstraction.infrastructure.Pair;
import org.intellij.lang.annotations.Language;

import java.sql.*;
import java.util.List;

import static com.room414.racingbets.dal.concrete.mysql.infrastructure.MySqlDaoHelper.*;

/**
 * Implementation of ParticipantDao that uses JDBC as data source.
 *
 * @see ParticipantDao
 * @author Alexander Melashchenko
 * @version 1.0 28 Feb 2017
 */
public class MySqlParticipantDao implements ParticipantDao {
    private static final String TABLE_NAME = "participant";

    private MySqlSharedExecutor<Participant> executor;
    private MySqlSharedExecutor<Pair<Participant, Timestamp>> foreignExecutor;

    MySqlParticipantDao(Connection connection) {
        this.executor = new MySqlSharedExecutor<>(
                connection,
                statement -> getResult(statement, MySqlMapHelper::mapParticipant),
                statement -> getResultList(statement, MySqlMapHelper::mapParticipant)
        );
        this.foreignExecutor = new MySqlSharedExecutor<>(
                connection,
                statement -> getResult(statement, this::mapWhoAndWhen),
                statement -> getResultList(statement, this::mapWhoAndWhen)
        );
    }


    @Override
    public void create(Participant entity) {
        throw new DalException("It is impossible to createUnitOfWorkFactory a ParticipantDto separate from the RaceDto");
    }

    @Override
    public Participant find(Long id) {
        @Language("MySQL")
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
    public List<Participant> findAll() {
        @Language("MySQL")
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

        return executor.findAll(sqlStatement);
    }

    @Override
    public List<Participant> findAll(int offset, int limit) {
        @Language("MySQL")
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
    public int count() {
        return executor.count(TABLE_NAME);
    }

    /**
     * Don't change participants race
     */
    @Override
    public long update(Participant entity) {
        @Language("MySQL")
        String sqlStatement =
                "UPDATE participant " +
                "SET number = ?, horse_id = ?, carried_weight = ?, topspeed = ?, official_rating = ?, " +
                "   jockey_id = ?, trainer_id = ?, place = ?, odds = ? " +
                "WHERE id = ?";

        return executor.executeUpdateQuery(
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
    }

    @Override
    public boolean delete(Long id) {
        return executor.delete(TABLE_NAME, id);
    }

    private Pair<Participant, Timestamp> mapWhoAndWhen(ResultSet resultSet) throws SQLException {
        final String RACE_START_COLUMN = "race.start_date_time";

        Pair<Participant, Timestamp> result = new Pair<>();

        result.setFirstElement(MySqlMapHelper.mapParticipant(resultSet));
        result.setSecondElement(resultSet.getTimestamp(RACE_START_COLUMN));

        return result;
    }

    @Override
    public List<Pair<Participant, Timestamp>> findByHorseId(long id, int offset, int limit) {
        @Language("MySQL") 
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
    public int findByHorseIdCount(long id) {
        final String columnName = "horse_id";

        return executor.findByForeignKeyCount(TABLE_NAME, columnName, id);
    }

    @Override
    public List<Pair<Participant, Timestamp>> findByOwnerId(long id, int offset, int limit) throws DalException {
        @Language("MySQL") 
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
    public int findByOwnerIdCount(long id) {
        @Language("MySQL")
        final String sqlStatement =
                "SELECT COUNT(*) AS count " +
                "FROM participant " +
                "   INNER JOIN horse " +
                "       ON horse.id = participant.horse_id " +
                "   INNER JOIN owner AS horse_owner " +
                "       ON horse.owner_id = horse_owner.id " +
                "WHERE horse_owner.id = ?";

        return executor.executeCountQuery(sqlStatement, id);
    }

    @Override
    public List<Pair<Participant, Timestamp>> findByJockeyId(long id, int offset, int limit) {
        @Language("MySQL")
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
    public int findByJockeyIdCount(long id) {
        final String columnName = "jockey_id";

        return executor.findByForeignKeyCount(TABLE_NAME, columnName, id);
    }

    @Override
    public List<Pair<Participant, Timestamp>> findByTrainerId(long id, int offset, int limit) {
        @Language("MySQL")
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
    public int findByTrainerIdCount(long id) {
        final String columnName = "trainer_id";

        return executor.findByForeignKeyCount(TABLE_NAME, columnName, id);
    }
}
