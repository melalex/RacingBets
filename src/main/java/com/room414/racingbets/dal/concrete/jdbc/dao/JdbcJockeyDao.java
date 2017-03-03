package com.room414.racingbets.dal.concrete.jdbc.dao;

import com.room414.racingbets.dal.abstraction.dao.JockeyDao;
import com.room414.racingbets.dal.concrete.jdbc.base.JdbcPersonDao;
import com.room414.racingbets.dal.domain.entities.Country;
import com.room414.racingbets.dal.domain.entities.Jockey;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Implementation of JockeyDao that uses JDBC as data source.
 *
 * @see JockeyDao
 * @author Alexander Melashchenko
 * @version 1.0 28 Feb 2017
 */
public class JdbcJockeyDao extends JdbcPersonDao<Jockey> implements JockeyDao {
    private static final String TABLE_NAME = "jockey";

    private final String COUNTRY_ID_COLUMN = "country.id";
    private final String COUNTRY_NAME_COLUMN = "country.name";
    private final String COUNTRY_CODE_COLUMN = "country.code";

    private final String PERSON_ID_COLUMN = "jockey.id";
    private final String PERSON_FIRST_NAME_COLUMN = "jockey.first_name";
    private final String PERSON_LAST_NAME_COLUMN = "jockey.last_name";
    private final String PERSON_BIRTHDAY_COLUMN = "jockey.birthday";


    JdbcJockeyDao(Connection connection) {
        this.connection = connection;
    }


    @Override
    protected String getTableName() {
        return TABLE_NAME;
    }

    @Override
    protected Jockey mapResultSet(ResultSet resultSet) throws SQLException {
        Country country = new Country();
        country.setId(resultSet.getLong(COUNTRY_ID_COLUMN));
        country.setName(resultSet.getString(COUNTRY_NAME_COLUMN));
        country.setCode(resultSet.getString(COUNTRY_CODE_COLUMN));

        return Jockey.builder()
                .setId(resultSet.getLong(PERSON_ID_COLUMN))
                .setFirstName(resultSet.getString(PERSON_FIRST_NAME_COLUMN))
                .setSecondName(resultSet.getString(PERSON_LAST_NAME_COLUMN))
                .setBirthday(resultSet.getDate(PERSON_BIRTHDAY_COLUMN))
                .setCountry(country)
                .build();
    }
}
