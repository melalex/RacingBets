package com.room414.racingbets.dal.concrete.jdbc.infrastructure;

import com.room414.racingbets.dal.domain.entities.*;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @author Alexander Melashchenko
 * @version 1.0 03 Mar 2017
 */
public class JdbcMapHelper {

    private JdbcMapHelper() {

    }

    public static long mapCount(ResultSet resultSet) throws SQLException {
        final String COUNT_COLUMN = "count";

        return resultSet.getLong(COUNT_COLUMN);
    }

    public static Country mapCountry(ResultSet resultSet) throws SQLException {
        final String COUNTRY_ID_COLUMN = "country.id";
        final String COUNTRY_NAME_COLUMN = "country.name";
        final String COUNTRY_CODE_COLUMN = "country.code";

        long id = resultSet.getLong(COUNTRY_ID_COLUMN);
        String name = resultSet.getString(COUNTRY_NAME_COLUMN);
        String code = resultSet.getString(COUNTRY_CODE_COLUMN);
        return new Country(id, name, code);
    }

    public static Jockey mapJockey(ResultSet resultSet) throws SQLException {
        final String PERSON_ID_COLUMN = "jockey.id";
        final String PERSON_FIRST_NAME_COLUMN = "jockey.first_name";
        final String PERSON_LAST_NAME_COLUMN = "jockey.last_name";
        final String PERSON_BIRTHDAY_COLUMN = "jockey.birthday";

        return Jockey.builder()
                .setId(resultSet.getLong(PERSON_ID_COLUMN))
                .setFirstName(resultSet.getString(PERSON_FIRST_NAME_COLUMN))
                .setSecondName(resultSet.getString(PERSON_LAST_NAME_COLUMN))
                .setBirthday(resultSet.getDate(PERSON_BIRTHDAY_COLUMN))
                .setCountry(mapCountry(resultSet))
                .build();

    }

    public static Owner mapOwner(ResultSet resultSet) throws SQLException {
        final String PERSON_ID_COLUMN = "owner.id";
        final String PERSON_FIRST_NAME_COLUMN = "owner.first_name";
        final String PERSON_LAST_NAME_COLUMN = "owner.last_name";
        final String PERSON_BIRTHDAY_COLUMN = "owner.birthday";

        return Owner.builder()
                .setId(resultSet.getLong(PERSON_ID_COLUMN))
                .setFirstName(resultSet.getString(PERSON_FIRST_NAME_COLUMN))
                .setSecondName(resultSet.getString(PERSON_LAST_NAME_COLUMN))
                .setBirthday(resultSet.getDate(PERSON_BIRTHDAY_COLUMN))
                .setCountry(mapCountry(resultSet))
                .build();
    }

    public static Trainer mapTrainer(ResultSet resultSet) throws SQLException {
        final String PERSON_ID_COLUMN = "trainer.id";
        final String PERSON_FIRST_NAME_COLUMN = "trainer.first_name";
        final String PERSON_LAST_NAME_COLUMN = "trainer.last_name";
        final String PERSON_BIRTHDAY_COLUMN = "trainer.birthday";

        return Trainer.builder()
                .setId(resultSet.getLong(PERSON_ID_COLUMN))
                .setFirstName(resultSet.getString(PERSON_FIRST_NAME_COLUMN))
                .setSecondName(resultSet.getString(PERSON_LAST_NAME_COLUMN))
                .setBirthday(resultSet.getDate(PERSON_BIRTHDAY_COLUMN))
                .setCountry(mapCountry(resultSet))
                .build();
    }

    public static Racecourse mapRacecourse(ResultSet resultSet) throws SQLException {
        final String RACECOURSE_ID_COLUMN = "racecourse.id";
        final String RACECOURSE_NAME_COLUMN = "racecourse.name";
        final String RACECOURSE_LATITUDE_COLUMN = "racecourse.latitude";
        final String RACECOURSE_LONGITUDE_COLUMN = "racecourse.longitude";
        final String RACECOURSE_CONTACT_COLUMN = "racecourse.contact";
        final String RACECOURSE_CLERK_COLUMN = "racecourse.clerk";

        return Racecourse.builder()
                .setId(resultSet.getLong(RACECOURSE_ID_COLUMN))
                .setName(resultSet.getString(RACECOURSE_NAME_COLUMN))
                .setCountry(mapCountry(resultSet))
                .setLatitude(resultSet.getDouble(RACECOURSE_LATITUDE_COLUMN))
                .setLongitude(resultSet.getDouble(RACECOURSE_LONGITUDE_COLUMN))
                .setClerk(resultSet.getString(RACECOURSE_CLERK_COLUMN))
                .setContact(resultSet.getString(RACECOURSE_CONTACT_COLUMN))
                .build();
    }
}