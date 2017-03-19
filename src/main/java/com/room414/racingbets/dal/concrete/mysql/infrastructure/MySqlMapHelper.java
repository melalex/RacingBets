package com.room414.racingbets.dal.concrete.mysql.infrastructure;

import com.room414.racingbets.dal.domain.entities.*;
import com.room414.racingbets.dal.domain.enums.Role;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Class that stores common methods for entities mapping (prevents code duplication).
 *
 * @author Alexander Melashchenko
 * @version 1.0 03 Mar 2017
 */
public class MySqlMapHelper {

    private MySqlMapHelper() {

    }

    static int mapCount(ResultSet resultSet) throws SQLException {
        final String COUNT_COLUMN = "count";

        return resultSet.getInt(COUNT_COLUMN);
    }

    public static Jockey mapJockey(ResultSet resultSet) throws SQLException {
        final String defaultNamespace = "jockey";

        return mapJockey(defaultNamespace, resultSet);
    }

    public static Owner mapOwner(ResultSet resultSet) throws SQLException {
        final String defaultNamespace = "owner";

        return mapOwner(resultSet, defaultNamespace);
    }

    public static Trainer mapTrainer(ResultSet resultSet) throws SQLException {
        final String defaultNamespace = "trainer";

        return mapTrainer(resultSet, defaultNamespace);
    }

    public static Racecourse mapRacecourse(ResultSet resultSet) throws SQLException {
        final String defaultNamespace = "racecourse";

        return mapRacecourse(resultSet, defaultNamespace);
    }

    public static ApplicationUser mapApplicationUser(ResultSet resultSet) throws SQLException {
        final String defaultNamespace = "application_user";

        return mapApplicationUser(resultSet, defaultNamespace);
    }

    public static Horse mapHorse(ResultSet resultSet) throws SQLException {
        final String defaultNamespace = "horse";

        return mapHorse(resultSet, defaultNamespace);
    }

    public static Participant mapParticipant(ResultSet resultSet) throws SQLException {
        final String defaultNamespace = "participant";

        return mapParticipant(resultSet, defaultNamespace);
    }

    private static Jockey mapJockey(String namespace, ResultSet resultSet) throws SQLException {
        final String personIdColumn = namespace + ".id";
        final String personFirstNameColumn = namespace + ".first_name";
        final String personLastNameColumn = namespace + ".last_name";
        final String personBirthdayColumn = namespace + ".birthday";

        return Jockey.builder()
                .setId(resultSet.getLong(personIdColumn))
                .setFirstName(resultSet.getString(personFirstNameColumn))
                .setSecondName(resultSet.getString(personLastNameColumn))
                .setBirthday(resultSet.getDate(personBirthdayColumn))
                .build();

    }

    private static Owner mapOwner(ResultSet resultSet, String namespace) throws SQLException {
        final String personIdColumn = namespace + ".id";
        final String personFirstNameColumn = namespace + ".first_name";
        final String personLastNameColumn = namespace + ".last_name";
        final String personBirthdayColumn = namespace + ".birthday";

        return Owner.builder()
                .setId(resultSet.getLong(personIdColumn))
                .setFirstName(resultSet.getString(personFirstNameColumn))
                .setSecondName(resultSet.getString(personLastNameColumn))
                .setBirthday(resultSet.getDate(personBirthdayColumn))
                .build();
    }

    private static Trainer mapTrainer(ResultSet resultSet, String namespace) throws SQLException {
        final String personIdColumn = namespace + ".id";
        final String personFirstNameColumn = namespace + ".first_name";
        final String personLastNameColumn = namespace + ".last_name";
        final String personBirthdayColumn = namespace + ".birthday";

        return Trainer.builder()
                .setId(resultSet.getLong(personIdColumn))
                .setFirstName(resultSet.getString(personFirstNameColumn))
                .setSecondName(resultSet.getString(personLastNameColumn))
                .setBirthday(resultSet.getDate(personBirthdayColumn))
                .build();
    }

    private static Racecourse mapRacecourse(ResultSet resultSet, String namespace) throws SQLException {
        final String racecourseIdColumn = namespace + ".id";
        final String racecourseNameColumn = namespace + ".name";
        final String racecourseLatitudeColumn = namespace + ".latitude";
        final String racecourseLongitudeColumn = namespace + ".longitude";
        final String racecourseContactColumn = namespace + ".contact";
        final String racecourseClerkColumn = namespace + ".clerk";

        return Racecourse.builder()
                .setId(resultSet.getLong(racecourseIdColumn))
                .setName(resultSet.getString(racecourseNameColumn))
                .setLatitude(resultSet.getDouble(racecourseLatitudeColumn))
                .setLongitude(resultSet.getDouble(racecourseLongitudeColumn))
                .setClerk(resultSet.getString(racecourseClerkColumn))
                .setContact(resultSet.getString(racecourseContactColumn))
                .build();
    }

    private static ApplicationUser mapApplicationUser(ResultSet resultSet, String namespace) throws SQLException {
        final String idColumnName = namespace + ".id";
        final String loginColumnName = namespace + ".login";
        final String firstNameColumnName = namespace + ".first_name";
        final String lastNameColumnName = namespace + ".last_name";
        final String emailColumnName = namespace + ".email";
        final String isEmailConfirmedColumnName = namespace + ".is_email_confirmed";
        final String passwordColumnName = namespace + ".password";
        final String balanceColumnName = namespace + ".balance";

        return ApplicationUser.builder()
                .setId(resultSet.getLong(idColumnName))
                .setLogin(resultSet.getString(loginColumnName))
                .setFirstName(resultSet.getString(firstNameColumnName))
                .setLastName(resultSet.getString(lastNameColumnName))
                .setEmail(resultSet.getString(emailColumnName))
                .setEmailConfirmed(resultSet.getBoolean(isEmailConfirmedColumnName))
                .setPassword(resultSet.getString(passwordColumnName))
                .setBalance(resultSet.getBigDecimal(balanceColumnName))
                .addRole(Role.HANDICAPPER)
                .build();
    }

    private static Horse mapHorse(ResultSet resultSet, String namespace) throws SQLException {
        final String idColumnName = namespace + ".id";
        final String nameColumnName = namespace + ".name";
        final String birthdayColumnName = namespace + ".birthday";
        final String genderColumnName = namespace + ".gender";
        final String sirColumnName = namespace + ".sire_id";
        final String damColumnName = namespace + ".dam_id";

        final String trainerNamespace = "horse_trainer";
        final String ownerNamespace = "horse_owner";

        return Horse
                .builder()
                .setId(resultSet.getLong(idColumnName))
                .setName(resultSet.getString(nameColumnName))
                .setBirthday(resultSet.getDate(birthdayColumnName))
                .setGender(resultSet.getString(genderColumnName))
                .setSir(resultSet.getLong(sirColumnName))
                .setDam(resultSet.getLong(damColumnName))
                .setOwner(mapOwner(resultSet, ownerNamespace))
                .setTrainer(mapTrainer(resultSet, trainerNamespace))
                .build();
    }

    private static Participant mapParticipant(ResultSet resultSet, String namespace) throws SQLException {
        final String idColumnName = namespace + ".id";
        final String numberColumnName = namespace + ".number";
        final String carriedWeightColumnName = namespace + ".carried_weight";
        final String topSpeedColumnName = namespace + ".topspeed";
        final String officialRatingColumnName = namespace + ".official_rating";
        final String oddsColumnName = namespace + ".odds";
        final String placeColumnName = namespace + ".place";

        return Participant
                .builder()
                .setId(resultSet.getLong(idColumnName))
                .setNumber(resultSet.getInt(numberColumnName))
                .setHorse(mapHorse(resultSet))
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