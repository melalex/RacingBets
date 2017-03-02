package com.room414.racingbets.dal.concrete.jdbc.dao;

import com.room414.racingbets.dal.abstraction.dao.ParticipantDao;
import com.room414.racingbets.dal.domain.entities.Participant;
import com.room414.racingbets.dal.infrastructure.Pair;

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
    public boolean create(Participant entity) {

    }

    @Override
    public Participant find(Integer id) {
        return null;
    }

    @Override
    public List<Participant> findAll(int offset, int limit) {
        return null;
    }

    @Override
    public int count() {
        return 0;
    }

    @Override
    public int update(Participant entity) {

    }

    @Override
    public boolean delete(Integer id) {

    }

    @Override
    public List<Pair<Participant, Timestamp>> findByHorseId(int id, int offset, int limit) {
        return null;
    }

    @Override
    public int findByHorseIdCount(int id) {
        return 0;
    }

    @Override
    public List<Pair<Participant, Timestamp>> findByOwnerId(int id, int offset, int limit) {
        return null;
    }

    @Override
    public int findByOwnerIdCount(int id) {
        return 0;
    }

    @Override
    public List<Pair<Participant, Timestamp>> findByJockeyId(int id, int offset, int limit) {
        return null;
    }

    @Override
    public int findByJockeyIdCount(int id) {
        return 0;
    }

    @Override
    public List<Pair<Participant, Timestamp>> findByTrainerId(int id, int offset, int limit) {
        return null;
    }

    @Override
    public int findByTrainerIdCount(int id) {
        return 0;
    }
}
