package com.room414.racingbets.dal.concrete.jdbc.dao;

import com.room414.racingbets.dal.abstraction.dao.OwnerDao;
import com.room414.racingbets.dal.concrete.jdbc.base.JdbcPersonDao;
import com.room414.racingbets.dal.domain.entities.Country;
import com.room414.racingbets.dal.domain.entities.Owner;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Implementation of OwnerDao that uses JDBC as data source.
 *
 * @see OwnerDao
 * @author Alexander Melashchenko
 * @version 1.0 28 Feb 2017
 */
public class JdbcOwnerDao extends JdbcPersonDao<Owner> implements OwnerDao {
    private static final String TABLE_NAME = "owner";

    private final String COUNTRY_ID_COLUMN = "country.id";
    private final String COUNTRY_NAME_COLUMN = "country.name";
    private final String COUNTRY_CODE_COLUMN = "country.code";

    private final String PERSON_ID_COLUMN = "owner.id";
    private final String PERSON_FIRST_NAME_COLUMN = "owner.first_name";
    private final String PERSON_LAST_NAME_COLUMN = "owner.last_name";
    private final String PERSON_BIRTHDAY_COLUMN = "owner.birthday";

    JdbcOwnerDao(Connection connection) {
        this.connection = connection;
    }


    @Override
    protected String getTableName() {
        return TABLE_NAME;
    }

    @Override
    protected Owner mapResultSet(ResultSet resultSet) throws SQLException {
        Country country = new Country();
        country.setId(resultSet.getLong(COUNTRY_ID_COLUMN));
        country.setName(resultSet.getString(COUNTRY_NAME_COLUMN));
        country.setCode(resultSet.getString(COUNTRY_CODE_COLUMN));

        return Owner.builder()
                .setId(resultSet.getLong(PERSON_ID_COLUMN))
                .setFirstName(resultSet.getString(PERSON_FIRST_NAME_COLUMN))
                .setSecondName(resultSet.getString(PERSON_LAST_NAME_COLUMN))
                .setBirthday(resultSet.getDate(PERSON_BIRTHDAY_COLUMN))
                .setCountry(country)
                .build();
    }
}
