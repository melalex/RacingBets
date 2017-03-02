package com.room414.racingbets.dal.concrete.jdbc.dao;

import com.room414.racingbets.dal.abstraction.dao.ParticipantDao;
import com.room414.racingbets.dal.abstraction.exception.DalException;
import com.room414.racingbets.dal.domain.entities.Participant;
import com.room414.racingbets.dal.abstraction.infrastructure.Pair;

import java.sql.Connection;
import java.sql.Timestamp;
import java.util.List;

/**
 * Implementation of ParticipantDao that uses JDBC as data source.
 *
 * @see ParticipantDao
 * @author Alexander Melashchenko
 * @version 1.0 28 Feb 2017
 */
public class JdbcParticipantDao implements ParticipantDao {
    Connection connection;

    JdbcParticipantDao(Connection connection) {
        this.connection = connection;
    }


    @Override
    public void create(Participant entity) throws DalException {

    }

    @Override
    public Participant find(Long id) {
        return null;
    }

    @Override
    public List<Participant> findAll() throws DalException {
        return null;
    }

    @Override
    public List<Participant> findAll(long offset, long limit) {
        return null;
    }

    @Override
    public long count() {
        return 0;
    }

    @Override
    public long update(Participant entity) {
        return 0;
    }

    @Override
    public boolean delete(Long id) {
        return false;
    }

    @Override
    public List<Pair<Participant, Timestamp>> findByHorseId(long id, long offset, long limit) {
        return null;
    }

    @Override
    public long findByHorseIdCount(long id) {
        return 0;
    }

    @Override
    public List<Pair<Participant, Timestamp>> findByOwnerId(long id, long offset, long limit) {
        return null;
    }

    @Override
    public long findByOwnerIdCount(long id) {
        return 0;
    }

    @Override
    public List<Pair<Participant, Timestamp>> findByJockeyId(long id, long offset, long limit) {
        return null;
    }

    @Override
    public long findByJockeyIdCount(long id) {
        return 0;
    }

    @Override
    public List<Pair<Participant, Timestamp>> findByTrainerId(long id, long offset, long limit) {
        return null;
    }

    @Override
    public long findByTrainerIdCount(long id) {
        return 0;
    }
}
