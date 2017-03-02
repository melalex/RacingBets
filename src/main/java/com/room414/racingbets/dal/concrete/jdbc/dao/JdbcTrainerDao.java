package com.room414.racingbets.dal.concrete.jdbc.dao;

import com.room414.racingbets.dal.abstraction.dao.TrainerDao;
import com.room414.racingbets.dal.domain.entities.Trainer;

import java.sql.Connection;
import java.util.List;

/**
 * Implementation of TrainerDao that uses JDBC as data source.
 *
 * @see TrainerDao
 * @author Alexander Melashchenko
 * @version 1.0 28 Feb 2017
 */
public class JdbcTrainerDao implements TrainerDao {
    Connection connection;

    JdbcTrainerDao(Connection connection) {
        this.connection = connection;
    }


    @Override
    public void create(Trainer entity) {

    }

    @Override
    public Trainer find(Integer id) {
        return null;
    }

    @Override
    public List<Trainer> findAll(int offset, int limit) {
        return null;
    }

    @Override
    public int count() {
        return 0;
    }

    @Override
    public void update(Trainer entity) {

    }

    @Override
    public void delete(Integer id) {

    }

    @Override
    public List<Trainer> findByNamePart(String namePart, int offset, int limit) {
        return null;
    }

    @Override
    public int findByNamePartCount(String namePart) {
        return 0;
    }
}
