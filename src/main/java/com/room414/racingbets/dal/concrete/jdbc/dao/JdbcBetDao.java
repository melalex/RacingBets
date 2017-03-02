package com.room414.racingbets.dal.concrete.jdbc.dao;

import com.room414.racingbets.dal.abstraction.dao.BetDao;
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
    public void create(Bet entity) {

    }

    @Override
    public Bet find(Integer id) {
        return null;
    }

    @Override
    public List<Bet> findAll(int offset, int limit) {
        return null;
    }

    @Override
    public int count() {
        return 0;
    }

    @Override
    public void update(Bet entity) {

    }

    @Override
    public void delete(Integer id) {

    }

    @Override
    public List<Bet> findByUserId(int id, int offset, int limit) {
        return null;
    }

    @Override
    public int findByUserIdCount(int id) {
        return 0;
    }

    @Override
    public List<Bet> findByRaceId(int id, int offset, int limit) {
        return null;
    }

    @Override
    public int findByRaceIdCount(int id) {
        return 0;
    }

    @Override
    public void updateStatus(int id, BetStatus status) {

    }

    @Override
    public void updateStatus(int places) {

    }

    @Override
    public Odds getOdds(Bet bet) {
        return null;
    }
}
