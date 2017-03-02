package com.room414.racingbets.dal.concrete.jdbc.dao;

import com.room414.racingbets.dal.abstraction.dao.BetDao;
import com.room414.racingbets.dal.abstraction.exception.DalException;
import com.room414.racingbets.dal.domain.entities.Bet;
import com.room414.racingbets.dal.domain.entities.Odds;
import com.room414.racingbets.dal.domain.enums.BetStatus;

import java.sql.Connection;
import java.util.List;

/**
 * Implementation of BetDao that uses JDBC as data source.
 *
 * @see BetDao
 * @author Alexander Melashchenko
 * @version 1.0 28 Feb 2017
 */
public class JdbcBetDao implements BetDao {
    private Connection connection;

    JdbcBetDao(Connection connection) {
        this.connection = connection;
    }

    @Override
    public void create(Bet entity) throws DalException {

    }

    @Override
    public List<Bet> findByUserId(long id, long offset, long limit) {
        return null;
    }

    @Override
    public long findByUserIdCount(long id) {
        return 0;
    }

    @Override
    public Bet find(Long id) {
        return null;
    }

    @Override
    public List<Bet> findAll() throws DalException {
        return null;
    }

    @Override
    public List<Bet> findByRaceId(long id, long offset, long limit) {
        return null;
    }

    @Override
    public List<Bet> findAll(long offset, long limit) {
        return null;
    }

    @Override
    public long findByRaceIdCount(long id) {
        return 0;
    }

    @Override
    public long count() {
        return 0;
    }

    @Override
    public void updateStatus(long id, BetStatus status) {

    }

    @Override
    public long update(Bet entity) {
        return 0;
    }

    @Override
    public void updateStatus(long[] places) {

    }

    @Override
    public boolean delete(Long id) {
        return false;
    }

    @Override
    public Odds getOdds(Bet bet) {
        return null;
    }
}
