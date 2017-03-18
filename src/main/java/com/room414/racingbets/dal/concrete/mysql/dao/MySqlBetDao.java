package com.room414.racingbets.dal.concrete.mysql.dao;

import com.room414.racingbets.dal.abstraction.dao.BetDao;
import com.room414.racingbets.dal.abstraction.exception.DalException;
import com.room414.racingbets.dal.concrete.mysql.infrastructure.MySqlMapHelper;
import com.room414.racingbets.dal.concrete.mysql.infrastructure.MySqlSharedExecutor;
import com.room414.racingbets.dal.domain.builders.BetBuilder;
import com.room414.racingbets.dal.domain.entities.Bet;
import com.room414.racingbets.dal.domain.entities.Odds;
import com.room414.racingbets.dal.domain.entities.Participant;
import org.intellij.lang.annotations.Language;

import java.math.BigDecimal;
import java.sql.*;
import java.util.*;
import java.util.stream.Collectors;

import static com.room414.racingbets.dal.concrete.mysql.infrastructure.MySqlDaoHelper.*;

/**
 * Implementation of BetDao that uses JDBC as data source.
 *
 * @see BetDao
 * @author Alexander Melashchenko
 * @version 1.0 28 Feb 2017
 */
public class MySqlBetDao implements BetDao {
    private static final String TABLE_NAME = "bet";
    private static final String CALCULATING_ERROR_MESSAGE = "Exception during calculating odds for bet ";

    private Connection connection;
    private MySqlSharedExecutor<Bet> executor;

    MySqlBetDao(Connection connection) {
        this.connection = connection;
        this.executor = new MySqlSharedExecutor<>(
                connection,
                statement -> getResultWithArray(statement, this::mapBets),
                statement -> getResultListWithArray(statement, this::mapBets)
        );
    }

    private List<Bet> mapBets(ResultSet resultSet) throws SQLException {
        Map<Long, BetBuilder> builderById = new HashMap<>();

        final String idColumnName = "bet.id";
        final String raceIdColumnName = "bet.race_id";
        final String betSizeColumnName = "bet.bet_size";
        final String betTypeColumnName = "bet.bet_type";
        final String betStatusColumnName = "bet.status";
        final String placeColumnName = "bet_participant.place";

        BetBuilder builder;
        long id;

        while (resultSet.next()) {
            id = resultSet.getLong(idColumnName);
            builder = builderById.get(id);
            if (builder == null) {
                builder = Bet.builder()
                        .setId(resultSet.getLong(idColumnName))
                        .setRaceId(resultSet.getLong(raceIdColumnName))
                        .setUser(MySqlMapHelper.mapApplicationUser(resultSet))
                        .setBetSize(resultSet.getBigDecimal(betSizeColumnName))
                        .setBetType(resultSet.getString(betTypeColumnName))
                        .setBetStatus(resultSet.getString(betStatusColumnName));
                builderById.put(id, builder);
            }
            builder.setParticipant(resultSet.getInt(placeColumnName), MySqlMapHelper.mapParticipant(resultSet));
        }

        return builderById
                .values()
                .stream()
                .map(BetBuilder::build)
                .collect(Collectors.toList());
    }

    private void createBet(Bet entity) {
        @Language("MySQL")
        final String sqlStatement =
                "INSERT INTO bet " +
                "   (application_user_id, status, bet_size, bet_type, race_id) " +
                "VALUES (?, ?, ?, ?, ?)";

        executor.create(
                sqlStatement,
                entity::setId,
                entity.getUser().getId(),
                entity.getBetStatus().getName(),
                entity.getBetSize(),
                entity.getBetType().getName(),
                entity.getRaceId()
        );
    }

    private void createBetParticipant(Bet entity) {
        final String sqlStatement =
                "INSERT bet_participant " +
                "   (bet_id, participant_id, place) " +
                "VALUES (?, ?, ?)";

        try(PreparedStatement statement = connection.prepareStatement(sqlStatement)) {
            Map<Integer, Participant> participants = entity.getParticipants();

            for (Integer place : participants.keySet()) {
                statement.setLong(1, entity.getId());
                statement.setLong(2, participants.get(place).getId());
                statement.setInt(3, place);

                statement.addBatch();
            }

            statement.executeBatch();
        } catch (SQLException e) {
            String message = "Exception during adding bet_participant faze while creating bet "
                    + entity.toString();
            throw new DalException(message, e);
        }
    }

    @Override
    public void create(Bet entity) {
        createBet(entity);
        createBetParticipant(entity);
    }

    @Override
    public List<Bet> findByUserId(long id, long offset, long limit) {
        @Language("MySQL")
        final String sqlStatement =
                "SELECT * FROM (" +
                "   SELECT * FROM bet " +
                "   WHERE application_user_id = ? " +
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
                "   ON participant.horse_id = horse.id " +
                "INNER JOIN trainer AS horse_trainer " +
                "   ON horse.trainer_id = horse_trainer.id " +
                "INNER JOIN owner AS horse_owner " +
                "   ON horse.owner_id = horse_owner.id ";

        return executor.findByForeignKey(sqlStatement, id, offset, limit);
    }

    @Override
    public long findByUserIdCount(long id) {
        final String columnName = "application_user_id";

        return executor.findByForeignKeyCount(TABLE_NAME, columnName, id);
    }

    /**
     * UserDto role will be set to Handicapper
     *
     * @see com.room414.racingbets.dal.domain.enums.Role#HANDICAPPER
     */
    @Override
    public Bet find(Long id) {
        @Language("MySQL")
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
                "   ON participant.horse_id = horse.id " +
                "INNER JOIN trainer AS horse_trainer " +
                "   ON horse.trainer_id = horse_trainer.id " +
                "INNER JOIN owner AS horse_owner " +
                "   ON horse.owner_id = horse_owner.id ";

        return executor.find(id, sqlStatement);
    }

    @Override
    public List<Bet> findAll() {
        @Language("MySQL")
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
                "   ON participant.horse_id = horse.id " +
                "INNER JOIN trainer AS horse_trainer " +
                "   ON horse.trainer_id = horse_trainer.id " +
                "INNER JOIN owner AS horse_owner " +
                "   ON horse.owner_id = horse_owner.id ";

        return executor.findAll(sqlStatement);
    }

    @Override
    public List<Bet> findByRaceId(long id, long offset, long limit) {
        @Language("MySQL")
        final String sqlStatement =
                "SELECT * FROM (" +
                "   SELECT * FROM bet " +
                "   WHERE race_id = ? " +
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
                "   ON participant.horse_id = horse.id " +
                "INNER JOIN trainer AS horse_trainer " +
                "   ON horse.trainer_id = horse_trainer.id " +
                "INNER JOIN owner AS horse_owner " +
                "   ON horse.owner_id = horse_owner.id ";

        return executor.findByForeignKey(sqlStatement, id, offset, limit);
    }

    @Override
    public List<Bet> findAll(long offset, long limit) {
        @Language("MySQL")
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
                "   ON participant.horse_id = horse.id " +
                "INNER JOIN trainer AS horse_trainer " +
                "   ON horse.trainer_id = horse_trainer.id " +
                "INNER JOIN owner AS horse_owner " +
                "   ON horse.owner_id = horse_owner.id ";

        return executor.findAll(sqlStatement, limit, offset);
    }

    @Override
    public long findByRaceIdCount(long id) {
        final String columnName = "race_id";

        return executor.findByForeignKeyCount(TABLE_NAME, columnName, id);
    }

    /**
     * ParticipantDto list will not be updated
     */
    @Override
    public long update(List<Bet> bets) {
        final String sqlStatement =
                "UPDATE bet " +
                "SET application_user_id = ?, status = ?, bet_size = ?, bet_type = ?, race_id = ? " +
                "WHERE id = ?";

        try(PreparedStatement statement = connection.prepareStatement(sqlStatement)) {
            for (Bet bet : bets) {
                statement.setLong(1, bet.getUser().getId());
                statement.setString(2, bet.getBetStatus().getName());
                statement.setBigDecimal(3, bet.getBetSize());
                statement.setString(4, bet.getBetType().getName());
                statement.setLong(5, bet.getRaceId());
                statement.setLong(6, bet.getId());

                statement.addBatch();
            }

            return Arrays.stream(statement.executeBatch()).sum();
        } catch (SQLException e) {
            String message = "Exception during bets updating";
            throw new DalException(message, e);
        }
    }

    @Override
    public long count() {
        return executor.count(TABLE_NAME);
    }

    @Override
    public long update(Bet entity) {
        @Language("MySQL")
        final String sqlStatement =
                "UPDATE bet " +
                "SET application_user_id = ?, status = ?, bet_size = ?, bet_type = ?, race_id = ? " +
                "WHERE id = ?";

        return executor.executeUpdateQuery(
                sqlStatement,
                entity.getUser().getId(),
                entity.getBetStatus().getName(),
                entity.getBetSize(),
                entity.getBetType().getName(),
                entity.getId()
        );
    }

    @Override
    public boolean delete(Long id) {
        return executor.delete(TABLE_NAME, id);
    }

    @Override
    public Odds getOdds(Bet bet) {
        switch (bet.getBetType()) {
            case SHOW:
                return getShowOdds(bet);
            case PLACE:
                return getPlaceOdds(bet);
            case WIN:
                return getWinOdds(bet);
            case QUINELLA:
                return getQuinellaOdds(bet);
            case EXACTA:
                return getExactaOdds(bet);
            case TRIFECTA:
                return getTrifectaOdds(bet);
            case SUPERFECTA:
                return getSuperfectaOdds(bet);
            default:
                throw new DalException("IMPOSSIBLE!!!");
        }
    }

    private Odds mapOdds(CallableStatement statement) throws SQLException {
        BigDecimal prizePool = statement.getBigDecimal("prize_pool");
        BigDecimal eventPool = statement.getBigDecimal("event_pool");
        double commission = statement.getDouble("race_commission");

        return new Odds(prizePool, eventPool, commission);
    }

    private Odds getShowOdds(Bet bet) {
        @Language("MySQL")
        final String call = "{ CALL get_odds_for_show(?, ?, ?, ?, ?) }";

        return getOddsOneParticipant(bet, call);
    }

    private Odds getPlaceOdds(Bet bet) {
        @Language("MySQL")
        final String call = "{ CALL get_odds_for_place(?, ?, ?, ?, ?) }";

        return getOddsOneParticipant(bet, call);
    }

    private Odds getWinOdds(Bet bet) {
        @Language("MySQL")
        final String call = "{ CALL get_odds_for_win(?, ?, ?, ?, ?) }";

        return getOddsOneParticipant(bet, call);
    }

    private Odds getOddsOneParticipant(Bet bet, String call) {
        try(CallableStatement statement = connection.prepareCall(call)) {
            statement.setLong(1, bet.getRaceId());
            statement.setLong(2, bet.getParticipantByPlace(1).getId());
            statement.registerOutParameter(3, Types.DECIMAL);
            statement.registerOutParameter(4, Types.DECIMAL);
            statement.registerOutParameter(5, Types.DOUBLE);

            if (statement.executeUpdate() > 0) {
                return mapOdds(statement);
            } else {
                throw new SQLException("ResultSet is empty");
            }
        } catch (SQLException e) {
            String message = CALCULATING_ERROR_MESSAGE + bet;
            throw new DalException(message, e);
        }
    }

    private Odds getQuinellaOdds(Bet bet) {
        @Language("MySQL")
        final String call = "{ CALL get_odds_for_quinella(?, ?, ?, ?, ?, ?) }";

        return getOddsTwoParticipant(bet, call);
    }

    private Odds getExactaOdds(Bet bet) {
        @Language("MySQL")
        final String call = "{ CALL get_odds_for_exacta(?, ?, ?, ?, ?, ?) }";

        return getOddsTwoParticipant(bet, call);
    }

    private Odds getOddsTwoParticipant(Bet bet, String call) {
        try(CallableStatement statement = connection.prepareCall(call)) {
            statement.setLong(1, bet.getRaceId());

            statement.setLong(2, bet.getParticipantByPlace(1).getId());
            statement.setLong(3, bet.getParticipantByPlace(2).getId());

            statement.registerOutParameter(4, Types.DECIMAL);
            statement.registerOutParameter(5, Types.DECIMAL);
            statement.registerOutParameter(6, Types.DOUBLE);

            if (statement.executeUpdate() > 0) {
                return mapOdds(statement);
            } else {
                throw new SQLException("ResultSet is empty");
            }
        } catch (SQLException e) {
            String message = CALCULATING_ERROR_MESSAGE + bet;
            throw new DalException(message, e);
        }
    }


    private Odds getTrifectaOdds(Bet bet) {
        @Language("MySQL")
        final String call = "{ CALL get_odds_for_trifecta(?, ?, ?, ?, ?, ?, ?) }";

        try(CallableStatement statement = connection.prepareCall(call)) {
            statement.setLong(1, bet.getRaceId());

            statement.setLong(2, bet.getParticipantByPlace(1).getId());
            statement.setLong(3, bet.getParticipantByPlace(2).getId());
            statement.setLong(4, bet.getParticipantByPlace(3).getId());

            statement.registerOutParameter(5, Types.DECIMAL);
            statement.registerOutParameter(6, Types.DECIMAL);
            statement.registerOutParameter(7, Types.DOUBLE);

            if (statement.executeUpdate() > 0) {
                return mapOdds(statement);
            } else {
                throw new SQLException("ResultSet is empty");
            }
        } catch (SQLException e) {
            String message = CALCULATING_ERROR_MESSAGE + bet;
            throw new DalException(message, e);
        }
    }

    private Odds getSuperfectaOdds(Bet bet) {
        @Language("MySQL")
        final String call = "{ CALL get_odds_for_superfecta(?, ?, ?, ?, ?, ?, ?, ?) }";

        try(CallableStatement statement = connection.prepareCall(call)) {
            statement.setLong(1, bet.getRaceId());

            statement.setLong(2, bet.getParticipantByPlace(1).getId());
            statement.setLong(3, bet.getParticipantByPlace(2).getId());
            statement.setLong(4, bet.getParticipantByPlace(3).getId());
            statement.setLong(5, bet.getParticipantByPlace(4).getId());

            statement.registerOutParameter(6, Types.DECIMAL);
            statement.registerOutParameter(7, Types.DECIMAL);
            statement.registerOutParameter(8, Types.DOUBLE);

            if (statement.executeUpdate() > 0) {
                return mapOdds(statement);
            } else {
                throw new SQLException("ResultSet is empty");
            }
        } catch (SQLException e) {
            String message = CALCULATING_ERROR_MESSAGE + bet;
            throw new DalException(message, e);
        }
    }
}
