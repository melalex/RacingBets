package com.room414.racingbets.dal.infrastructure;

import com.room414.racingbets.dal.abstraction.entities.Horse;
import com.room414.racingbets.dal.domain.builders.RaceBuilder;
import com.room414.racingbets.dal.domain.entities.*;
import com.room414.racingbets.dal.domain.enums.BetStatus;
import com.room414.racingbets.dal.domain.enums.BetType;
import com.room414.racingbets.dal.domain.enums.Role;
import com.room414.racingbets.dal.domain.proxies.HorseLazyLoadProxy;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.text.ParseException;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static com.room414.racingbets.dal.infrastructure.TestHelper.sqlDateFromString;

/**
 * @author melalex
 * @version 1.0 09 Mar 2017
 */
public class EntityStorage {
    private static EntityStorage ourInstance = create();

    private Map<Long, ApplicationUser> applicationUserMap = new LinkedHashMap<>();
    private Map<Long, Bet> betMap = new LinkedHashMap<>();
    private Map<Long, Horse> horseMap = new LinkedHashMap<>();
    private Map<Long, Participant> participantMap = new LinkedHashMap<>();
    private Map<Long, Owner> ownerMap = new LinkedHashMap<>();
    private Map<Long, Trainer> trainerMap = new LinkedHashMap<>();
    private Map<Long, Jockey> jockeyMap = new LinkedHashMap<>();
    private Map<Long, Racecourse> racecourseMap = new LinkedHashMap<>();
    private Map<Long, Race> raceMap = new LinkedHashMap<>();

    public static EntityStorage getInstance() {
        return ourInstance;
    }

    private EntityStorage() {
    }

    private static EntityStorage create() {
        EntityStorage newStorage = new EntityStorage();

        try {
            newStorage.initOwnerMap();
            newStorage.initJockeyMap();
            newStorage.initTrainerMap();
            newStorage.initHorseMap();
            newStorage.initRacecourseMap();
            newStorage.initParticipantMap();
            newStorage.initRaceMap();
            newStorage.initApplicationUserMap();
            newStorage.initBetMap();
        } catch (ParseException e) {
            throw new RuntimeException("IMPOSSIBLE", e);
        }

        return newStorage;
    }

    private void initOwnerMap() throws ParseException {
        ownerMap.put(
                1L,
                Owner.builder()
                        .setId(1)
                        .setFirstName("Ruby")
                        .setSecondName("Nichols")
                        .setBirthday(sqlDateFromString("1982-04-21"))
                        .build()
        );
        ownerMap.put(
                2L,
                Owner.builder()
                        .setId(2)
                        .setFirstName("Nichols")
                        .setSecondName("Ruby")
                        .setBirthday(sqlDateFromString("1962-05-19"))
                        .build()
        );
        ownerMap.put(
                3L,
                Owner.builder()
                        .setId(3)
                        .setFirstName("Doris")
                        .setSecondName("Franklin")
                        .setBirthday(sqlDateFromString("1984-03-16"))
                        .build()
        );
        ownerMap.put(
                4L,
                Owner.builder()
                        .setId(4)
                        .setFirstName("Thomas")
                        .setSecondName("West")
                        .setBirthday(sqlDateFromString("1980-01-19"))
                        .build()
        );
        ownerMap.put(
                5L,
                Owner.builder()
                        .setId(5)
                        .setFirstName("Alex")
                        .setSecondName("Strutynski")
                        .setBirthday(sqlDateFromString("1980-04-21"))
                        .build()
        );
        ownerMap.put(
                6L,
                Owner.builder()
                        .setId(6)
                        .setFirstName("Vova")
                        .setSecondName("Bog")
                        .setBirthday(null)
                        .build()
        );
    }

    private void initTrainerMap() throws ParseException {
        trainerMap.put(
                1L,
                Trainer.builder()
                        .setId(1)
                        .setFirstName("Ruby")
                        .setSecondName("Nichols")
                        .setBirthday(sqlDateFromString("1982-04-21"))
                        .build()
        );
        trainerMap.put(
                2L,
                Trainer.builder()
                        .setId(2)
                        .setFirstName("Nichols")
                        .setSecondName("Ruby")
                        .setBirthday(sqlDateFromString("1962-05-19"))
                        .build()
        );
        trainerMap.put(
                3L,
                Trainer.builder()
                        .setId(3)
                        .setFirstName("Doris")
                        .setSecondName("Franklin")
                        .setBirthday(sqlDateFromString("1984-03-16"))
                        .build()
        );
        trainerMap.put(
                4L,
                Trainer.builder()
                        .setId(4)
                        .setFirstName("Thomas")
                        .setSecondName("West")
                        .setBirthday(sqlDateFromString("1980-01-19"))
                        .build()
        );
        trainerMap.put(
                5L,
                Trainer.builder()
                        .setId(5)
                        .setFirstName("Alex")
                        .setSecondName("Strutynski")
                        .setBirthday(sqlDateFromString("1980-04-21"))
                        .build()
        );
        trainerMap.put(
                6L,
                Trainer.builder()
                        .setId(6)
                        .setFirstName("Vova")
                        .setSecondName("Bog")
                        .setBirthday(null)
                        .build()
        );
    }

    private void initJockeyMap() throws ParseException {
        jockeyMap.put(
                1L,
                Jockey.builder()
                        .setId(1)
                        .setFirstName("Ruby")
                        .setSecondName("Nichols")
                        .setBirthday(sqlDateFromString("1982-04-21"))
                        .build()
        );
        jockeyMap.put(
                2L,
                Jockey.builder()
                        .setId(2)
                        .setFirstName("Nichols")
                        .setSecondName("Ruby")
                        .setBirthday(sqlDateFromString("1962-05-19"))
                        .build()
        );
        jockeyMap.put(
                3L,
                Jockey.builder()
                        .setId(3)
                        .setFirstName("Doris")
                        .setSecondName("Franklin")
                        .setBirthday(sqlDateFromString("1984-03-16"))
                        .build()
        );
        jockeyMap.put(
                4L,
                Jockey.builder()
                        .setId(4)
                        .setFirstName("Thomas")
                        .setSecondName("West")
                        .setBirthday(sqlDateFromString("1980-01-19"))
                        .build()
        );
        jockeyMap.put(
                5L,
                Jockey.builder()
                        .setId(5)
                        .setFirstName("Alex")
                        .setSecondName("Strutynski")
                        .setBirthday(sqlDateFromString("1980-04-21"))
                        .build()
        );
        jockeyMap.put(
                6L,
                Jockey.builder()
                        .setId(6)
                        .setFirstName("Vova")
                        .setSecondName("Bog")
                        .setBirthday(null)
                        .build()
        );
    }

    private void initHorseMap() throws ParseException {
        horseMap.put(
                1L,
                Horse.builder()
                .setId(1)
                .setName("Fixflex")
                .setBirthday(sqlDateFromString("2008-02-22"))
                .setGender("mare")
                .setTrainer(getTrainer(5))
                .setOwner(getOwner(1))
                .build()
        );

        horseMap.put(
                2L,
                Horse.builder()
                .setId(2)
                .setName("Wrapsafe")
                .setBirthday(sqlDateFromString("2005-08-04"))
                .setGender("stallion")
                .setTrainer(getTrainer(4))
                .setOwner(getOwner(2))
                .build()
        );

        horseMap.put(
                3L,
                Horse.builder()
                .setId(3)
                .setName("Prodder")
                .setBirthday(sqlDateFromString("2011-12-26"))
                .setGender("mare")
                .setTrainer(getTrainer(3))
                .setOwner(getOwner(3))
                .setSir(HorseLazyLoadProxy.create(2))
                .setDam(HorseLazyLoadProxy.create(1))
                .build()
        );

        horseMap.put(
                4L,
                Horse.builder()
                .setId(4)
                .setName("Span")
                .setBirthday(sqlDateFromString("2011-03-27"))
                .setGender("stallion")
                .setTrainer(getTrainer(2))
                .setOwner(getOwner(4))
                .setSir(HorseLazyLoadProxy.create(2))
                .setDam(HorseLazyLoadProxy.create(1))
                .build()
        );

        horseMap.put(
                5L,
                Horse.builder()
                .setId(5)
                .setName("Treeflex")
                .setBirthday(sqlDateFromString("2013-05-01"))
                .setGender("mare")
                .setTrainer(getTrainer(1))
                .setOwner(getOwner(5))
                .setSir(HorseLazyLoadProxy.create(3))
                .setDam(HorseLazyLoadProxy.create(4))
                .build()
        );

        horseMap.put(
                6L,
                Horse.builder()
                .setId(6)
                .setName("Alphazap")
                .setBirthday(sqlDateFromString("2000-10-25"))
                .setGender("stallion")
                .setTrainer(getTrainer(4))
                .setOwner(getOwner(2))
                .build()
        );

        horseMap.put(
                7L,
                Horse.builder()
                .setId(7)
                .setName("Aerified")
                .setBirthday(sqlDateFromString("2010-11-27"))
                .setGender("stallion")
                .setTrainer(getTrainer(2))
                .setOwner(getOwner(4))
                .setSir(HorseLazyLoadProxy.create(2))
                .setDam(HorseLazyLoadProxy.create(1))
                .build()
        );

        horseMap.put(
                8L,
                Horse.builder()
                .setId(8)
                .setName("Prob")
                .setBirthday(sqlDateFromString("2014-05-01"))
                .setGender("mare")
                .setTrainer(getTrainer(3))
                .setOwner(getOwner(3))
                .setSir(HorseLazyLoadProxy.create(3))
                .setDam(HorseLazyLoadProxy.create(4))
                .build()
        );
    }

    private void initRacecourseMap() {
        racecourseMap.put(
                1L,
                Racecourse.builder()
                .setId(1)
                .setName("Ronstring")
                .setLatitude(-22.72528)
                .setLongitude(-47.64917)
                .setContact("scook0@hud.gov")
                .setClerk("Stephen Cook")
                .build()
        );

        racecourseMap.put(
                2L,
                Racecourse.builder()
                .setId(2)
                .setName("Fintone")
                .setLatitude(29.95033)
                .setLongitude(121.74293)
                .setContact("ncunningham1@merriam-webster.com")
                .setClerk("Nicole Cunningham")
                .build()
        );

        racecourseMap.put(
                3L,
                Racecourse.builder()
                        .setId(3)
                        .setName("Flowdesk")
                        .setLatitude(-20.26889)
                        .setLongitude(-50.54583)
                        .setContact("ajames2@amazon.co.jp")
                        .setClerk("Annie James")
                        .build()
        );

    }

    private void initParticipantMap() {
        participantMap.put(
                1L,
                Participant.builder()
                        .setId(1)
                        .setNumber(1)
                        .setHorse(getHorse(1))
                        .setCarriedWeight(50)
                        .setTopSpeed(70)
                        .setOfficialRating(70)
                        .setOdds(2.1)
                        .setJockey(getJockey(1))
                        .setTrainer(getTrainer(1))
                        .setPlace(1)
                        .build()
        );

        participantMap.put(
                2L,
                Participant.builder()
                        .setId(2)
                        .setNumber(2)
                        .setHorse(getHorse(2))
                        .setCarriedWeight(50)
                        .setTopSpeed(70)
                        .setOfficialRating(70)
                        .setOdds(1)
                        .setJockey(getJockey(2))
                        .setTrainer(getTrainer(2))
                        .setPlace(2)
                        .build()
        );

        participantMap.put(
                3L,
                Participant.builder()
                        .setId(3)
                        .setNumber(3)
                        .setHorse(getHorse(3))
                        .setCarriedWeight(50)
                        .setTopSpeed(70)
                        .setOfficialRating(70)
                        .setOdds(0.5)
                        .setJockey(getJockey(3))
                        .setTrainer(getTrainer(3))
                        .setPlace(3)
                        .build()
        );

        participantMap.put(
                4L,
                Participant.builder()
                        .setId(4)
                        .setNumber(1)
                        .setHorse(getHorse(4))
                        .setCarriedWeight(50)
                        .setTopSpeed(70)
                        .setOfficialRating(70)
                        .setOdds(2.1)
                        .setJockey(getJockey(4))
                        .setTrainer(getTrainer(4))
                        .setPlace(1)
                        .build()
        );

        participantMap.put(
                5L,
                Participant.builder()
                        .setId(5)
                        .setNumber(2)
                        .setHorse(getHorse(5))
                        .setCarriedWeight(50)
                        .setTopSpeed(70)
                        .setOfficialRating(70)
                        .setOdds(1)
                        .setJockey(getJockey(5))
                        .setTrainer(getTrainer(5))
                        .setPlace(2)
                        .build()
        );

        participantMap.put(
                6L,
                Participant.builder()
                        .setId(6)
                        .setNumber(3)
                        .setHorse(getHorse(3))
                        .setCarriedWeight(50)
                        .setTopSpeed(70)
                        .setOfficialRating(70)
                        .setOdds(0.5)
                        .setJockey(getJockey(1))
                        .setTrainer(getTrainer(1))
                        .setPlace(3)
                        .build()
        );

        participantMap.put(
                7L,
                Participant.builder()
                        .setId(7)
                        .setNumber(1)
                        .setHorse(getHorse(2))
                        .setJockey(getJockey(2))
                        .setTrainer(getTrainer(2))
                        .build()
        );

        participantMap.put(
                8L,
                Participant.builder()
                        .setId(8)
                        .setNumber(2)
                        .setHorse(getHorse(1))
                        .setJockey(getJockey(3))
                        .setTrainer(getTrainer(3))
                        .build()
        );

        participantMap.put(
                9L,
                Participant.builder()
                        .setId(9)
                        .setNumber(3)
                        .setHorse(getHorse(3))
                        .setJockey(getJockey(4))
                        .setTrainer(getTrainer(4))
                        .build()
        );
    }

    private void initRaceMap() {
        RaceBuilder race1Builder = Race.builder()
                .setId(1)
                .setName("Gembucket")
                .setRaceStatus("finished")
                .setCommission(0.14)
                .setMinBet(BigDecimal.valueOf(2))
                .setRacecourse(getRacecourse(1))
                .setStart(Timestamp.valueOf("2017-03-08 10:32:36"))
                .setTrackCondition("Hard")
                .setRaceType("flat")
                .setRaceClass(1)
                .setMinAge(3)
                .setMinRating(50)
                .setMaxRating(70)
                .setDistance(8.1f);

        race1Builder
                .addParticipant(getParticipant(1))
                .addParticipant(getParticipant(2))
                .addParticipant(getParticipant(3));

        race1Builder
                .setPrize(1, BigDecimal.valueOf(300))
                .setPrize(2, BigDecimal.valueOf(200))
                .setPrize(3, BigDecimal.valueOf(100));

        RaceBuilder race2Builder = Race.builder()
                .setId(2)
                .setName("Ventosanzap")
                .setRaceStatus("riding")
                .setCommission(0.14)
                .setMinBet(BigDecimal.valueOf(2))
                .setRacecourse(getRacecourse(1))
                .setStart(Timestamp.valueOf("2017-03-09 13:44:56"))
                .setTrackCondition("Firm")
                .setRaceType("jump")
                .setRaceClass(2)
                .setMinAge(2)
                .setMinRating(0)
                .setMaxRating(70)
                .setDistance(8.1f);

        race2Builder
                .addParticipant(getParticipant(4))
                .addParticipant(getParticipant(5))
                .addParticipant(getParticipant(6));

        race2Builder
                .setPrize(1, BigDecimal.valueOf(300))
                .setPrize(2, BigDecimal.valueOf(200))
                .setPrize(3, BigDecimal.valueOf(100));

        RaceBuilder race3Builder = Race.builder()
                .setId(3)
                .setName("Duobam")
                .setRaceStatus("scheduled")
                .setCommission(0.14)
                .setMinBet(BigDecimal.valueOf(2))
                .setRacecourse(getRacecourse(2))
                .setStart(Timestamp.valueOf("2017-03-10 12:00:00"))
                .setRaceType("harness")
                .setRaceClass(2)
                .setMinAge(2)
                .setMinRating(0)
                .setMaxRating(60)
                .setDistance(10.1f);

        race3Builder
                .addParticipant(getParticipant(7))
                .addParticipant(getParticipant(8))
                .addParticipant(getParticipant(9));

        race3Builder
                .setPrize(1, BigDecimal.valueOf(300))
                .setPrize(2, BigDecimal.valueOf(200))
                .setPrize(3, BigDecimal.valueOf(100));

        raceMap.put(1L, race1Builder.build());
        raceMap.put(2L, race2Builder.build());
        raceMap.put(3L, race3Builder.build());
    }

    private void initApplicationUserMap() {
        applicationUserMap.put(
                1L,
                ApplicationUser.builder()
                        .setId(1)
                        .setLogin("pgordon0")
                        .setPassword("kJ182n")
                        .setFirstName("Paula")
                        .setLastName("Gordon")
                        .setEmail("pgordon0@google.ru")
                        .setEmailConfirmed(true)
                        .setBalance(BigDecimal.valueOf(827.32))
                        .addRole(Role.ADMIN)
                        .addRole(Role.BOOKMAKER)
                        .addRole(Role.HANDICAPPER)
                        .build()
        );

        applicationUserMap.put(
                2L,
                ApplicationUser.builder()
                        .setId(2)
                        .setLogin("slawrence1")
                        .setPassword("tBHdVXlvv")
                        .setFirstName("Shirley")
                        .setLastName("Lawrence")
                        .setEmail("slawrence1@geocities.com")
                        .setEmailConfirmed(true)
                        .setBalance(BigDecimal.valueOf(924.23))
                        .addRole(Role.BOOKMAKER)
                        .addRole(Role.HANDICAPPER)
                        .build()
        );

        applicationUserMap.put(
                3L,
                ApplicationUser.builder()
                        .setId(3)
                        .setLogin("hbanks2")
                        .setPassword("RgoMu22lO")
                        .setFirstName("Harold")
                        .setLastName("Banks")
                        .setEmail("hbanks2@adobe.com")
                        .setEmailConfirmed(true)
                        .setBalance(BigDecimal.valueOf(256.71))
                        .addRole(Role.HANDICAPPER)
                        .build()
        );

        applicationUserMap.put(
                4L,
                ApplicationUser.builder()
                        .setId(4)
                        .setLogin("lallen3")
                        .setPassword("WCeXA5")
                        .setFirstName("Lois")
                        .setLastName("Allen")
                        .setEmail("lallen3@virginia.edu")
                        .setEmailConfirmed(true)
                        .setBalance(BigDecimal.valueOf(385.59))
                        .addRole(Role.HANDICAPPER)
                        .build()
        );

        applicationUserMap.put(
                5L,
                ApplicationUser.builder()
                        .setId(5)
                        .setLogin("pandrews4")
                        .setPassword("uRKNaCqj2B")
                        .setFirstName("Paula")
                        .setLastName("Andrews")
                        .setEmail("pandrews4@artisteer.com")
                        .setEmailConfirmed(true)
                        .setBalance(BigDecimal.valueOf(749.38))
                        .addRole(Role.HANDICAPPER)
                        .build()
        );
    }

    private void initBetMap() {

        // Place

        betMap.put(
                1L,
                Bet.builder()
                        .setId(1)
                        .setRaceId(1)
                        .setUser(getApplicationUser(3))
                        .setBetType(BetType.PLACE)
                        .setBetStatus(BetStatus.SCHEDULED)
                        .setBetSize(BigDecimal.valueOf(100))
                        .setParticipant(1, getParticipant(1))
                        .setParticipant(2, getParticipant(1))
                        .build()
        );

        betMap.put(
                2L,
                Bet.builder()
                        .setId(2)
                        .setRaceId(1)
                        .setUser(getApplicationUser(4))
                        .setBetType(BetType.PLACE)
                        .setBetStatus(BetStatus.SCHEDULED)
                        .setBetSize(BigDecimal.valueOf(200))
                        .setParticipant(1, getParticipant(1))
                        .setParticipant(2, getParticipant(1))
                        .build()
        );

        betMap.put(
                3L,
                Bet.builder()
                        .setId(3)
                        .setRaceId(1)
                        .setUser(getApplicationUser(5))
                        .setBetType(BetType.PLACE)
                        .setBetStatus(BetStatus.SCHEDULED)
                        .setBetSize(BigDecimal.valueOf(300))
                        .setParticipant(1, getParticipant(3))
                        .setParticipant(2, getParticipant(3))
                        .build()
        );

        // Win

        betMap.put(
                4L,
                Bet.builder()
                        .setId(4)
                        .setRaceId(1)
                        .setUser(getApplicationUser(3))
                        .setBetType(BetType.WIN)
                        .setBetStatus(BetStatus.SCHEDULED)
                        .setBetSize(BigDecimal.valueOf(100))
                        .setParticipant(1, getParticipant(1))
                        .build()
        );

        betMap.put(
                5L,
                Bet.builder()
                        .setId(5)
                        .setRaceId(1)
                        .setUser(getApplicationUser(4))
                        .setBetType(BetType.WIN)
                        .setBetStatus(BetStatus.SCHEDULED)
                        .setBetSize(BigDecimal.valueOf(200))
                        .setParticipant(1, getParticipant(1))
                        .build()
        );

        betMap.put(
                6L,
                Bet.builder()
                        .setId(6)
                        .setRaceId(1)
                        .setUser(getApplicationUser(5))
                        .setBetType(BetType.WIN)
                        .setBetStatus(BetStatus.SCHEDULED)
                        .setBetSize(BigDecimal.valueOf(300))
                        .setParticipant(1, getParticipant(3))
                        .build()
        );

        // Qeinella

        betMap.put(
                7L,
                Bet.builder()
                        .setId(7)
                        .setRaceId(1)
                        .setUser(getApplicationUser(3))
                        .setBetType(BetType.QUINELLA)
                        .setBetStatus(BetStatus.SCHEDULED)
                        .setBetSize(BigDecimal.valueOf(100))
                        .setParticipant(1, getParticipant(1))
                        .setParticipant(2, getParticipant(2))
                        .build()
        );

        betMap.put(
                8L,
                Bet.builder()
                        .setId(8)
                        .setRaceId(1)
                        .setUser(getApplicationUser(4))
                        .setBetType(BetType.QUINELLA)
                        .setBetStatus(BetStatus.SCHEDULED)
                        .setBetSize(BigDecimal.valueOf(200))
                        .setParticipant(1, getParticipant(1))
                        .setParticipant(2, getParticipant(2))
                        .build()
        );

        betMap.put(
                9L,
                Bet.builder()
                        .setId(9)
                        .setRaceId(1)
                        .setUser(getApplicationUser(5))
                        .setBetType(BetType.QUINELLA)
                        .setBetStatus(BetStatus.SCHEDULED)
                        .setBetSize(BigDecimal.valueOf(300))
                        .setParticipant(1, getParticipant(3))
                        .setParticipant(2, getParticipant(1))
                        .build()
        );

        // Exacta

        betMap.put(
                10L,
                Bet.builder()
                        .setId(10)
                        .setRaceId(1)
                        .setUser(getApplicationUser(3))
                        .setBetType(BetType.EXACTA)
                        .setBetStatus(BetStatus.SCHEDULED)
                        .setBetSize(BigDecimal.valueOf(100))
                        .setParticipant(1, getParticipant(1))
                        .setParticipant(2, getParticipant(2))
                        .build()
        );

        betMap.put(
                11L,
                Bet.builder()
                        .setId(11)
                        .setRaceId(1)
                        .setUser(getApplicationUser(4))
                        .setBetType(BetType.EXACTA)
                        .setBetStatus(BetStatus.SCHEDULED)
                        .setBetSize(BigDecimal.valueOf(200))
                        .setParticipant(1, getParticipant(1))
                        .setParticipant(2, getParticipant(2))
                        .build()
        );

        betMap.put(
                12L,
                Bet.builder()
                        .setId(12)
                        .setRaceId(1)
                        .setUser(getApplicationUser(5))
                        .setBetType(BetType.EXACTA)
                        .setBetStatus(BetStatus.SCHEDULED)
                        .setBetSize(BigDecimal.valueOf(300))
                        .setParticipant(1, getParticipant(3))
                        .setParticipant(2, getParticipant(1))
                        .build()
        );

        // Trifecta

        betMap.put(
                13L,
                Bet.builder()
                        .setId(13)
                        .setRaceId(1)
                        .setUser(getApplicationUser(3))
                        .setBetType(BetType.TRIFECTA)
                        .setBetStatus(BetStatus.SCHEDULED)
                        .setBetSize(BigDecimal.valueOf(100))
                        .setParticipant(1, getParticipant(1))
                        .setParticipant(2, getParticipant(2))
                        .setParticipant(3, getParticipant(3))
                        .build()
        );

        betMap.put(
                14L,
                Bet.builder()
                        .setId(14)
                        .setRaceId(1)
                        .setUser(getApplicationUser(4))
                        .setBetType(BetType.TRIFECTA)
                        .setBetStatus(BetStatus.SCHEDULED)
                        .setBetSize(BigDecimal.valueOf(200))
                        .setParticipant(1, getParticipant(1))
                        .setParticipant(2, getParticipant(2))
                        .setParticipant(3, getParticipant(3))
                        .build()
        );

        betMap.put(
                15L,
                Bet.builder()
                        .setId(15)
                        .setRaceId(1)
                        .setUser(getApplicationUser(5))
                        .setBetType(BetType.TRIFECTA)
                        .setBetStatus(BetStatus.SCHEDULED)
                        .setBetSize(BigDecimal.valueOf(300))
                        .setParticipant(1, getParticipant(1))
                        .setParticipant(2, getParticipant(2))
                        .setParticipant(3, getParticipant(3))
                        .build()
        );
    }

    public Jockey getJockey(long o) {
        return jockeyMap.get(o);
    }

    public Owner getOwner(long o) {
        return ownerMap.get(o);
    }

    public Trainer getTrainer(long o) {
        return trainerMap.get(o);
    }

    public Horse getHorse(long o) {
        return horseMap.get(o);
    }

    public Racecourse getRacecourse(long o) {
        return racecourseMap.get(o);
    }

    public Participant getParticipant(long o) {
        return participantMap.get(o);
    }

    public Race getRace(long o) {
        return raceMap.get(o);
    }

    public ApplicationUser getApplicationUser(long o) {
        return applicationUserMap.get(o);
    }

    public Bet getBet(long o) {
        return betMap.get(o);
    }

    public List<Jockey> getAllJockeys() {
        return jockeyMap.values().stream().collect(Collectors.toList());
    }

    public List<Owner> getAllOwners() {
        return ownerMap.values().stream().collect(Collectors.toList());
    }

    public List<Trainer> getAllTrainers() {
        return trainerMap.values().stream().collect(Collectors.toList());
    }

    public List<Horse> getAllHorses() {
        return horseMap.values().stream().collect(Collectors.toList());
    }

    public List<Racecourse> getAllRacecourses() {
        return racecourseMap.values().stream().collect(Collectors.toList());
    }

    public List<Participant> getAllParticipants() {
        return participantMap.values().stream().collect(Collectors.toList());
    }

    public List<Race> getAllRaces() {
        return raceMap.values().stream().collect(Collectors.toList());
    }

    public List<ApplicationUser> getAllApplicationUsers(long o) {
        return applicationUserMap.values().stream().collect(Collectors.toList());
    }

    public List<Bet> getAllBets() {
        return betMap.values().stream().collect(Collectors.toList());
    }
}
