package com.room414.racingbets.dal.concrete.jdbc.dao;

import com.room414.racingbets.dal.abstraction.builders.PersonBuilder;
import com.room414.racingbets.dal.abstraction.dao.TrainerDao;
import com.room414.racingbets.dal.concrete.jdbc.base.JdbcPersonDao;
import com.room414.racingbets.dal.domain.entities.Country;
import com.room414.racingbets.dal.domain.entities.Trainer;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Implementation of TrainerDao that uses JDBC as data source.
 *
 * @see TrainerDao
 * @author Alexander Melashchenko
 * @version 1.0 28 Feb 2017
 */
public class JdbcTrainerDao extends JdbcPersonDao<Trainer> implements TrainerDao {
    private static final String TABLE_NAME = "trainer";

    private final String COUNTRY_ID_COLUMN = "country.id";
    private final String COUNTRY_NAME_COLUMN = "country.name";
    private final String COUNTRY_CODE_COLUMN = "country.code";

    private final String PERSON_ID_COLUMN = "trainer.id";
    private final String PERSON_FIRST_NAME_COLUMN = "trainer.first_name";
    private final String PERSON_LAST_NAME_COLUMN = "trainer.last_name";
    private final String PERSON_BIRTHDAY_COLUMN = "trainer.birthday";


    JdbcTrainerDao(Connection connection) {
        this.connection = connection;
    }

    @Override
    protected String getTableName() {
        return TABLE_NAME;
    }

    @Override
    protected Trainer mapResultSet(ResultSet resultSet) throws SQLException {
        Country country = new Country();
        country.setId(resultSet.getLong(COUNTRY_ID_COLUMN));
        country.setName(resultSet.getString(COUNTRY_NAME_COLUMN));
        country.setCode(resultSet.getString(COUNTRY_CODE_COLUMN));

        return Trainer.builder()
                .setId(resultSet.getLong(PERSON_ID_COLUMN))
                .setFirstName(resultSet.getString(PERSON_FIRST_NAME_COLUMN))
                .setSecondName(resultSet.getString(PERSON_LAST_NAME_COLUMN))
                .setBirthday(resultSet.getDate(PERSON_BIRTHDAY_COLUMN))
                .setCountry(country)
                .build();
    }
}
