package com.room414.racingbets.dal.concrete.mysql.dao;

import com.room414.racingbets.dal.abstraction.dao.ParticipantDao;
import com.room414.racingbets.dal.abstraction.exception.DalException;
import com.room414.racingbets.dal.concrete.mysql.infrastructure.MySqlSharedDelegate;
import com.room414.racingbets.dal.concrete.mysql.infrastructure.MySqlMapHelper;
import com.room414.racingbets.dal.domain.entities.Participant;
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

    private MySqlSharedDelegate<Participant> executor;

    MySqlParticipantDao(Connection connection) {
        this.executor = new MySqlSharedDelegate<>(
                connection,
                statement -> getResult(statement, MySqlMapHelper::mapParticipant),
                statement -> getResultList(statement, MySqlMapHelper::mapParticipant)
        );
    }


    @Override
    public void create(Participant entity) {
        throw new DalException("It is impossible to getUnitOfWorkFactory a ParticipantDto separate from the RaceDto");
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
}
