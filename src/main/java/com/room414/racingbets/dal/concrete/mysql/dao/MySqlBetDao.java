package com.room414.racingbets.dal.concrete.mysql.dao;

import com.room414.racingbets.dal.abstraction.dao.BetDao;
import com.room414.racingbets.dal.abstraction.exception.DalException;
import com.room414.racingbets.dal.concrete.mysql.infrastructure.MySqlMapHelper;
import com.room414.racingbets.dal.concrete.mysql.infrastructure.MySqlSimpleExecutor;
import com.room414.racingbets.dal.domain.builders.BetBuilder;
import com.room414.racingbets.dal.domain.entities.Bet;
import com.room414.racingbets.dal.domain.entities.Odds;
import com.room414.racingbets.dal.domain.entities.Participant;

import java.math.BigDecimal;
import java.sql.*;
import java.util.*;
import java.util.stream.Collectors;

import static com.room414.racingbets.dal.concrete.mysql.infrastructure.MySqlDaoHelper.createEntity;
import static com.room414.racingbets.dal.concrete.mysql.infrastructure.MySqlDaoHelper.defaultErrorMessage;

/**
 * Implementation of BetDao that uses JDBC as data source.
 *
 * @see BetDao
 * @author Alexander Melashchenko
 * @version 1.0 28 Feb 2017
 */
public class MySqlBetDao implements BetDao {
    private static final String TABLE_NAME = "bet";

    private Connection connection;
    private MySqlSimpleExecutor executor;

    MySqlBetDao(Connection connection) {
        this.connection = connection;
        this.executor = new MySqlSimpleExecutor(connection);
    }

    private List<Bet> mapBets(PreparedStatement statement) throws SQLException {
        try(ResultSet resultSet = statement.executeQuery()) {
            Map<Long, BetBuilder> builderById = new HashMap<>();

            final String idColumnName = "bet.id";
            final String raceIdColumnName = "bet.race_id";
            final String betSizeColumnName = "bet.bet_size";
            final String betTypeColumnName = "bet.bet_type";
            final String betStatusColumnName = "bet.bet_status";

            BetBuilder builder;
            long id;
            int place = 1;

            while (resultSet.next()) {
                id = resultSet.getLong(idColumnName);
                builder = builderById.get(id);
                if (builder == null) {
                    place = 1;
                    builder = Bet.builder()
                            .setId(resultSet.getLong(idColumnName))
                            .setRaceId(resultSet.getLong(raceIdColumnName))
                            .setUser(MySqlMapHelper.mapApplicationUser(resultSet))
                            .setBetSize(resultSet.getBigDecimal(betSizeColumnName))
                            .setBetType(resultSet.getString(betTypeColumnName))
                            .setBetStatus(resultSet.getString(betStatusColumnName));
                    builderById.put(id, builder);
                }
                builder.setParticipant(place, MySqlMapHelper.mapParticipant(resultSet));
            }

            return builderById
                    .values()
                    .stream()
                    .map(BetBuilder::build)
                    .collect(Collectors.toList());
        }
    }

    private Bet mapBet(PreparedStatement statement) throws SQLException {
        return mapBets(statement).get(0);
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
            String message = "Exception during adding bet_participant faze while creating bet "
                    + entity.toString();
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
                "INNER JOIN owner " +
                "   ON horse.owner_id = owner.id";

        return findByForeignKey(sqlStatement, id, offset, limit);
    }

    @Override
    public long findByUserIdCount(long id) throws DalException {
        final String columnName = "application_user_id";

        return executor.findByForeignKeyCount(TABLE_NAME, columnName, id);
    }

    @Override
    // TODO: comment about user role
    public Bet find(Long id) throws DalException {
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
                "INNER JOIN owner " +
                "   ON horse.owner_id = owner.id";

        try(PreparedStatement statement = connection.prepareStatement(sqlStatement)) {
            statement.setLong(1, id);

            return mapBet(statement);
        } catch (SQLException e) {
            String message = defaultErrorMessage(sqlStatement, id);
            throw new DalException(message, e);
        }
    }

    @Override
    public List<Bet> findAll() throws DalException {
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
                "INNER JOIN owner " +
                "   ON horse.owner_id = owner.id";

        try(PreparedStatement statement = connection.prepareStatement(sqlStatement)) {
            return mapBets(statement);
        } catch (SQLException e) {
            String message = defaultErrorMessage(sqlStatement);
            throw new DalException(message, e);
        }
    }

    @Override
    public List<Bet> findByRaceId(long id, long offset, long limit) throws DalException {
        //language=MySQL
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
                "INNER JOIN owner " +
                "   ON horse.owner_id = owner.id";

        return findByForeignKey(sqlStatement, id, offset, limit);
    }

    private List<Bet> findByForeignKey(String sqlStatement, long id, long offset, long limit) throws DalException {
        try(PreparedStatement statement = connection.prepareStatement(sqlStatement)) {
            statement.setLong(1, id);
            statement.setLong(2, limit);
            statement.setLong(3, offset);

            return mapBets(statement);
        } catch (SQLException e) {
            String message = defaultErrorMessage(sqlStatement, id, limit, offset);
            throw new DalException(message, e);
        }
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
                "   ON participant.horse_id = horse.id " +
                "INNER JOIN owner " +
                "   ON horse.owner_id = owner.id";

        try(PreparedStatement statement = connection.prepareStatement(sqlStatement)) {
            statement.setLong(1, limit);
            statement.setLong(2, offset);

            return mapBets(statement);
        } catch (SQLException e) {
            String message = defaultErrorMessage(sqlStatement, limit, offset);
            throw new DalException(message, e);
        }
    }

    @Override
    public long findByRaceIdCount(long id) throws DalException {
        final String columnName = "race_id";

        return executor.findByForeignKeyCount(TABLE_NAME, columnName, id);
    }

    @Override
    // TODO: add comment about participant array
    public long update(List<Bet> bets) throws DalException {
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
    public long count() throws DalException {
        return executor.count(TABLE_NAME);
    }

    @Override
    public long update(Bet entity) throws DalException {
        final String sqlStatement =
                "UPDATE bet " +
                "SET application_user_id = ?, status = ?, bet_size = ?, bet_type = ?, race_id = ? " +
                "WHERE id = ?";

        try(PreparedStatement statement = connection.prepareStatement(sqlStatement)) {
            statement.setLong(1, entity.getUser().getId());
            statement.setString(2, entity.getBetStatus().getName());
            statement.setBigDecimal(3, entity.getBetSize());
            statement.setString(4, entity.getBetType().getName());
            statement.setLong(5, entity.getRaceId());
            statement.setLong(6, entity.getId());

            return statement.executeUpdate();
        } catch (SQLException e) {
            String message = defaultErrorMessage(
                    sqlStatement,
                    entity.getUser().getId(),
                    entity.getBetStatus().getName(),
                    entity.getBetSize(),
                    entity.getBetType().getName(),
                    entity.getId()
            );
            throw new DalException(message, e);
        }
    }

    @Override
    public boolean delete(Long id) throws DalException {
        return executor.delete(TABLE_NAME, id);
    }

    @Override
    public Odds getOdds(Bet bet) throws DalException {
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
                throw new DalException("IMPOSABLE!!!");
        }
    }

    private Odds mapOdds(ResultSet resultSet) throws SQLException {
        BigDecimal prizePool = resultSet.getBigDecimal("prize_pool");
        BigDecimal eventPool = resultSet.getBigDecimal("event_pool");
        double commission = resultSet.getDouble("commission");

        return new Odds(prizePool, eventPool, commission);
    }

    private Odds getShowOdds(Bet bet) throws DalException {
        //language=MySQL
        final String call = "{ CALL get_odds_show(?, ?, ?, ?, ?) }";

        return getOddsOneParticipant(bet, call);
    }

    private Odds getPlaceOdds(Bet bet) throws DalException {
        //language=MySQL
        final String call = "{ CALL get_odds_place(?, ?, ?, ?, ?) }";

        return getOddsOneParticipant(bet, call);
    }

    private Odds getWinOdds(Bet bet) throws DalException {
        //language=MySQL
        final String call = "{ CALL get_odds_win(?, ?, ?, ?, ?) }";

        return getOddsOneParticipant(bet, call);
    }

    private Odds getOddsOneParticipant(Bet bet, String call) throws DalException {
        try(CallableStatement statement = connection.prepareCall(call)) {
            statement.setLong(1, bet.getRaceId());
            statement.setLong(2, bet.getParticipants().get(0).getId());
            statement.registerOutParameter(3, Types.DECIMAL);
            statement.registerOutParameter(4, Types.DECIMAL);
            statement.registerOutParameter(5, Types.DOUBLE);

            if (statement.execute()) {
                return mapOdds(statement.getResultSet());
            } else {
                throw new SQLException("ResultSet is empty");
            }
        } catch (SQLException e) {
            String message = "Exception during calculating odds for bet with id " + bet;
            throw new DalException(message, e);
        }
    }

    private Odds getQuinellaOdds(Bet bet) throws DalException {
        //language=MySQL
        final String call = "{ CALL get_odds_win(?, ?, ?, ?, ?, ?) }";

        return getOddsTwoParticipant(bet, call);
    }

    private Odds getExactaOdds(Bet bet) throws DalException {
        //language=MySQL
        final String call = "{ CALL get_odds_win(?, ?, ?, ?, ?, ?) }";

        return getOddsTwoParticipant(bet, call);
    }

    private Odds getOddsTwoParticipant(Bet bet, String call) throws DalException {
        try(CallableStatement statement = connection.prepareCall(call)) {
            statement.setLong(1, bet.getRaceId());

            statement.setLong(2, bet.getParticipants().get(0).getId());
            statement.setLong(3, bet.getParticipants().get(1).getId());

            statement.registerOutParameter(4, Types.DECIMAL);
            statement.registerOutParameter(5, Types.DECIMAL);
            statement.registerOutParameter(6, Types.DOUBLE);

            if (statement.execute()) {
                return mapOdds(statement.getResultSet());
            } else {
                throw new SQLException("ResultSet is empty");
            }
        } catch (SQLException e) {
            String message = "Exception during calculating odds for bet with id " + bet;
            throw new DalException(message, e);
        }
    }


    private Odds getTrifectaOdds(Bet bet) throws DalException {
        final String call = "{ CALL get_odds_win(?, ?, ?, ?, ?, ?, ?) }";

        try(CallableStatement statement = connection.prepareCall(call)) {
            statement.setLong(1, bet.getRaceId());

            statement.setLong(2, bet.getParticipants().get(0).getId());
            statement.setLong(3, bet.getParticipants().get(1).getId());
            statement.setLong(4, bet.getParticipants().get(2).getId());

            statement.registerOutParameter(5, Types.DECIMAL);
            statement.registerOutParameter(6, Types.DECIMAL);
            statement.registerOutParameter(7, Types.DOUBLE);

            if (statement.execute()) {
                return mapOdds(statement.getResultSet());
            } else {
                throw new SQLException("ResultSet is empty");
            }
        } catch (SQLException e) {
            String message = "Exception during calculating odds for bet with id " + bet;
            throw new DalException(message, e);
        }
    }

    private Odds getSuperfectaOdds(Bet bet) throws DalException {
        final String call = "{ CALL get_odds_win(?, ?, ?, ?, ?, ?, ?, ?) }";

        try(CallableStatement statement = connection.prepareCall(call)) {
            statement.setLong(1, bet.getRaceId());

            statement.setLong(2, bet.getParticipants().get(0).getId());
            statement.setLong(3, bet.getParticipants().get(1).getId());
            statement.setLong(4, bet.getParticipants().get(2).getId());
            statement.setLong(5, bet.getParticipants().get(2).getId());

            statement.registerOutParameter(6, Types.DECIMAL);
            statement.registerOutParameter(7, Types.DECIMAL);
            statement.registerOutParameter(8, Types.DOUBLE);

            if (statement.execute()) {
                return mapOdds(statement.getResultSet());
            } else {
                throw new SQLException("ResultSet is empty");
            }
        } catch (SQLException e) {
            String message = "Exception during calculating odds for bet with id " + bet;
            throw new DalException(message, e);
        }
    }
}
