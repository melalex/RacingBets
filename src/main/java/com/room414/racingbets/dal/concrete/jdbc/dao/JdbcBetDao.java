package com.room414.racingbets.dal.concrete.jdbc.dao;

import com.room414.racingbets.dal.abstraction.dao.BetDao;
import com.room414.racingbets.dal.abstraction.exception.DalException;
import com.room414.racingbets.dal.concrete.jdbc.infrastructure.JdbcCrudExecutor;
import com.room414.racingbets.dal.concrete.jdbc.infrastructure.JdbcMapHelper;
import com.room414.racingbets.dal.domain.entities.Bet;
import com.room414.racingbets.dal.domain.entities.Odds;
import com.room414.racingbets.dal.domain.entities.Participant;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

import static com.room414.racingbets.dal.concrete.jdbc.infrastructure.JdbcDaoHelper.createEntity;
import static com.room414.racingbets.dal.concrete.jdbc.infrastructure.JdbcDaoHelper.defaultErrorMessage;

/**
 * Implementation of BetDao that uses JDBC as data source.
 *
 * @see BetDao
 * @author Alexander Melashchenko
 * @version 1.0 28 Feb 2017
 */
public class JdbcBetDao implements BetDao {
    public static final String TABLE_NAME = "bet";

    private Connection connection;
    private JdbcCrudExecutor<Bet> executor;

    JdbcBetDao(Connection connection) {
        this.connection = connection;
        this.executor = new JdbcCrudExecutor<>(connection, JdbcMapHelper::mapBet);
    }

    private void createBet(Bet entity) throws DalException {
        final String sqlStatement =
                "INSERT INTO bet " +
                "   (application_user_id, status, bet_size, bet_type, race_id) " +
                "VALUES (?, ?, ?, ?, ?)";

        try(PreparedStatement statement = connection.prepareStatement(sqlStatement)) {
            statement.setLong(1, entity.getUser().getId());
            statement.setString(2, entity.getBetStatus().getName());
            statement.setBigDecimal(3, entity.getBetSize());
            statement.setString(4, entity.getBetType().getName());
            statement.setLong(5, entity.getRaceId());

            createEntity(statement, entity::setId);
        } catch (SQLException e) {
            String message = defaultErrorMessage(
                    sqlStatement,
                    entity.getUser().getId(),
                    entity.getBetStatus().getName(),
                    entity.getBetSize(),
                    entity.getBetType().getName()
            );
            throw new DalException(message, e);
        }
    }

    private void createBetParticipant(Bet entity) throws DalException {
        final String sqlStatement =
                "INSERT bet_participant " +
                "   (bet_id, participant_id, place) " +
                "VALUES (?, ?, ?)";

        try(PreparedStatement statement = connection.prepareStatement(sqlStatement)) {
            List<Participant> participants = entity.getParticipants();

            for (int i = 0; i < entity.getParticipants().size(); i++) {
                statement.setLong(1, entity.getId());
                statement.setLong(2, participants.get(i).getId());
                statement.setInt(3, i + 1);

                statement.addBatch();
            }

            statement.executeBatch();
        } catch (SQLException e) {
            String message = defaultErrorMessage(
                    sqlStatement,
                    entity.getUser().getId(),
                    entity.getBetStatus().getName(),
                    entity.getBetSize(),
                    entity.getBetType().getName()
            );
            throw new DalException(message, e);
        }
    }

    @Override
    public void create(Bet entity) throws DalException {
        createBet(entity);
        createBetParticipant(entity);
    }

    @Override
    public List<Bet> findByUserId(long id, long offset, long limit) throws DalException {
        //language=MySQL
        final String sqlStatement =
                "SELECT * FROM (" +
                "   SELECT * FROM bet " +
                "   WHERE application_user_id = ?" +
                ") AS bet  " +
                "INNER JOIN application_user " +
                "   ON bet.application_user_id = application_user.id " +
                "LEFT OUTER JOIN bet_participant ON bet_participant.bet_id = bet.id " +
                "INNER JOIN participant " +
                "   ON bet_participant.participant_id = participant.id " +
                "INNER JOIN jockey " +
                "   ON participant.jockey_id = jockey.id " +
                "INNER JOIN trainer " +
                "   ON participant.trainer_id = trainer.id " +
                "INNER JOIN horse " +
                "   ON participant.horse_id = horse.id";

        return executor.findByForeignKey(sqlStatement, id, offset, limit);
    }

    @Override
    public long findByUserIdCount(long id) throws DalException {
        final String columnName = "application_user_id";

        return executor.findByForeignKeyCount(TABLE_NAME, columnName, id);
    }

    @Override
    // TODO: comment about user role
    public Bet find(Long id) throws DalException {
        //language=MySQL
        final String sqlStatement =
                "SELECT * FROM (" +
                "   SELECT * FROM bet " +
                "   WHERE id = ?" +
                ") AS bet  " +
                "INNER JOIN application_user " +
                "   ON bet.application_user_id = application_user.id " +
                "LEFT OUTER JOIN bet_participant ON bet_participant.bet_id = bet.id " +
                "INNER JOIN participant " +
                "   ON bet_participant.participant_id = participant.id " +
                "INNER JOIN jockey " +
                "   ON participant.jockey_id = jockey.id " +
                "INNER JOIN trainer " +
                "   ON participant.trainer_id = trainer.id " +
                "INNER JOIN horse " +
                "   ON participant.horse_id = horse.id";

        return executor.find(id, sqlStatement);
    }

    @Override
    public List<Bet> findAll() throws DalException {
        //language=MySQL
        final String sqlStatement =
                "SELECT * FROM (" +
                "   SELECT * FROM bet " +
                ") AS bet  " +
                "INNER JOIN application_user " +
                "   ON bet.application_user_id = application_user.id " +
                "LEFT OUTER JOIN bet_participant ON bet_participant.bet_id = bet.id " +
                "INNER JOIN participant " +
                "   ON bet_participant.participant_id = participant.id " +
                "INNER JOIN jockey " +
                "   ON participant.jockey_id = jockey.id " +
                "INNER JOIN trainer " +
                "   ON participant.trainer_id = trainer.id " +
                "INNER JOIN horse " +
                "   ON participant.horse_id = horse.id";

        return executor.findAll(sqlStatement);
    }

    @Override
    public List<Bet> findByRaceId(long id, long offset, long limit) throws DalException {
        //language=MySQL
        final String sqlStatement =
                "SELECT * FROM (" +
                "   SELECT * FROM bet " +
                "   WHERE race_id = ?" +
                ") AS bet  " +
                "INNER JOIN application_user " +
                "   ON bet.application_user_id = application_user.id " +
                "LEFT OUTER JOIN bet_participant ON bet_participant.bet_id = bet.id " +
                "INNER JOIN participant " +
                "   ON bet_participant.participant_id = participant.id " +
                "INNER JOIN jockey " +
                "   ON participant.jockey_id = jockey.id " +
                "INNER JOIN trainer " +
                "   ON participant.trainer_id = trainer.id " +
                "INNER JOIN horse " +
                "   ON participant.horse_id = horse.id";

        return executor.findByForeignKey(sqlStatement, id, offset, limit);
    }

    @Override
    public List<Bet> findAll(long offset, long limit) throws DalException {
        //language=MySQL
        final String sqlStatement =
                "SELECT * FROM (" +
                "   SELECT * FROM bet " +
                "   LIMIT ? OFFSET ?" +
                ") AS bet  " +
                "INNER JOIN application_user " +
                "   ON bet.application_user_id = application_user.id " +
                "LEFT OUTER JOIN bet_participant ON bet_participant.bet_id = bet.id " +
                "INNER JOIN participant " +
                "   ON bet_participant.participant_id = participant.id " +
                "INNER JOIN jockey " +
                "   ON participant.jockey_id = jockey.id " +
                "INNER JOIN trainer " +
                "   ON participant.trainer_id = trainer.id " +
                "INNER JOIN horse " +
                "   ON participant.horse_id = horse.id";

        return executor.findAll(sqlStatement, limit, offset);
    }

    @Override
    public long findByRaceIdCount(long id) throws DalException {
        final String columnName = "race_id";

        return executor.findByForeignKeyCount(TABLE_NAME, columnName, id);
    }

    @Override
    public long update(List<Bet> bets) {
        return 0;
    }

    @Override
    public long count() throws DalException {
        return executor.count(TABLE_NAME);
    }

    @Override
    public long update(Bet entity) {
        return 0;
    }

    @Override
    public boolean delete(Long id) throws DalException {
        return executor.delete(TABLE_NAME, id);
    }

    @Override
    public Odds getOdds(Bet bet) {
        return null;
    }
}
