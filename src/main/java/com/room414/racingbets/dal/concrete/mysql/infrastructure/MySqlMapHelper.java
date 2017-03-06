package com.room414.racingbets.dal.concrete.mysql.infrastructure;

import com.room414.racingbets.dal.abstraction.dao.HorseDao;
import com.room414.racingbets.dal.abstraction.entities.Horse;
import com.room414.racingbets.dal.domain.entities.*;
import com.room414.racingbets.dal.domain.proxies.HorseLazyLoadProxy;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @author Alexander Melashchenko
 * @version 1.0 03 Mar 2017
 */
public class MySqlMapHelper {

    private MySqlMapHelper() {

    }

    public static long mapCount(ResultSet resultSet) throws SQLException {
        final String COUNT_COLUMN = "count";

        return resultSet.getLong(COUNT_COLUMN);
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
                .build();
    }

    public static Racecourse mapRacecourse(ResultSet resultSet) throws SQLException {
        // TODO: is should be upper?
        final String RACECOURSE_ID_COLUMN = "racecourse.id";
        final String RACECOURSE_NAME_COLUMN = "racecourse.name";
        final String RACECOURSE_LATITUDE_COLUMN = "racecourse.latitude";
        final String RACECOURSE_LONGITUDE_COLUMN = "racecourse.longitude";
        final String RACECOURSE_CONTACT_COLUMN = "racecourse.contact";
        final String RACECOURSE_CLERK_COLUMN = "racecourse.clerk";

        return Racecourse.builder()
                .setId(resultSet.getLong(RACECOURSE_ID_COLUMN))
                .setName(resultSet.getString(RACECOURSE_NAME_COLUMN))
                .setLatitude(resultSet.getDouble(RACECOURSE_LATITUDE_COLUMN))
                .setLongitude(resultSet.getDouble(RACECOURSE_LONGITUDE_COLUMN))
                .setClerk(resultSet.getString(RACECOURSE_CLERK_COLUMN))
                .setContact(resultSet.getString(RACECOURSE_CONTACT_COLUMN))
                .build();
    }

    public static ApplicationUser mapApplicationUser(ResultSet resultSet) throws SQLException {
        // TODO: implementation
        return null;
    }

    public static Horse mapHorse(ResultSet resultSet, HorseDao horseDao) throws SQLException {
        final String idColumnName = "horse.id";
        final String nameColumnName = "horse.name";
        final String birthdayColumnName = "horse.birthday";
        final String genderColumnName = "horse.gender";
        final String sirColumnName = "horse.sir_id";
        final String damColumnName = "horse.dam_id";

        return Horse
                .builder()
                .setId(resultSet.getLong(idColumnName))
                .setName(resultSet.getString(nameColumnName))
                .setBirthday(resultSet.getDate(birthdayColumnName))
                .setGender(resultSet.getString(genderColumnName))
                .setSir(new HorseLazyLoadProxy(resultSet.getLong(sirColumnName), horseDao))
                .setDam(new HorseLazyLoadProxy(resultSet.getLong(damColumnName), horseDao))
                .setOwner(MySqlMapHelper.mapOwner(resultSet))
                .setTrainer(MySqlMapHelper.mapTrainer(resultSet))
                .build();
    }

    public static Participant mapParticipant(ResultSet resultSet, HorseDao horseDao) throws SQLException {
        final String idColumnName = "participant.id";
        final String numberColumnName = "participant.number";
        final String carriedWeightColumnName = "participant.carried_weight";
        final String topSpeedColumnName = "participant.top_speed";
        final String officialRatingColumnName = "participant.official_rating";
        final String oddsColumnName = "participant.odds";
        final String placeColumnName = "participant.place";

        return Participant
                .builder()
                .setId(resultSet.getLong(idColumnName))
                .setNumber(resultSet.getInt(numberColumnName))
                .setHorse(mapHorse(resultSet, horseDao))
                .setCarriedWeight(resultSet.getFloat(carriedWeightColumnName))
                .setTopSpeed(resultSet.getInt(topSpeedColumnName))
                .setOfficialRating(resultSet.getInt(officialRatingColumnName))
                .setOdds(resultSet.getDouble(oddsColumnName))
                .setJockey(mapJockey(resultSet))
                .setTrainer(mapTrainer(resultSet))
                .setPlace(resultSet.getInt(placeColumnName))
                .build();
    }
}