package com.room414.racingbets.dal.infrastructure;

import com.room414.racingbets.dal.domain.builders.*;
import com.room414.racingbets.dal.domain.entities.*;
import com.room414.racingbets.dal.domain.enums.BetStatus;
import com.room414.racingbets.dal.domain.enums.BetType;
import com.room414.racingbets.dal.domain.enums.Role;

import java.math.BigDecimal;
import java.text.ParseException;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static com.room414.racingbets.dal.infrastructure.TestHelper.sqlDateFromString;
import static com.room414.racingbets.dal.infrastructure.TestHelper.sqlTimestampFromString;

/**
 * @author melalex
 * @version 1.0 09 Mar 2017
 */
public class EntityStorage {
    private static EntityStorage ourInstance = create();

    private Map<Long, ApplicationUserBuilder> applicationUserMap = new LinkedHashMap<>();
    private Map<Long, BetBuilder> betMap = new LinkedHashMap<>();
    private Map<Long, HorseBuilder> horseMap = new LinkedHashMap<>();
    private Map<Long, ParticipantBuilder> participantMap = new LinkedHashMap<>();
    private Map<Long, OwnerBuilder> ownerMap = new LinkedHashMap<>();
    private Map<Long, TrainerBuilder> trainerMap = new LinkedHashMap<>();
    private Map<Long, JockeyBuilder> jockeyMap = new LinkedHashMap<>();
    private Map<Long, RacecourseBuilder> racecourseMap = new LinkedHashMap<>();
    private Map<Long, RaceBuilder> raceMap = new LinkedHashMap<>();

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
                ((OwnerBuilder) Owner.builder()
                        .setId(1)
                        .setFirstName("Ruby")
                        .setSecondName("Nichols")
                        .setBirthday(sqlDateFromString("1982-04-21")))
        );
        ownerMap.put(
                2L,
                ((OwnerBuilder) Owner.builder()
                        .setId(2)
                        .setFirstName("Nichols")
                        .setSecondName("Ruby")
                        .setBirthday(sqlDateFromString("1962-05-19")))
        );
        ownerMap.put(
                3L,
                ((OwnerBuilder) Owner.builder()
                        .setId(3)
                        .setFirstName("Doris")
                        .setSecondName("Franklin")
                        .setBirthday(sqlDateFromString("1984-03-16")))
        );
        ownerMap.put(
                4L,
                ((OwnerBuilder) Owner.builder()
                        .setId(4)
                        .setFirstName("Thomas")
                        .setSecondName("West")
                        .setBirthday(sqlDateFromString("1980-01-19")))
        );
        ownerMap.put(
                5L,
                ((OwnerBuilder) Owner.builder()
                        .setId(5)
                        .setFirstName("Alex")
                        .setSecondName("Strutynski")
                        .setBirthday(sqlDateFromString("1980-04-21")))
        );
        ownerMap.put(
                6L,
                ((OwnerBuilder) Owner.builder()
                        .setId(6)
                        .setFirstName("Vova")
                        .setSecondName("Bog")
                        .setBirthday(null))
        );
    }

    private void initTrainerMap() throws ParseException {
        trainerMap.put(
                1L,
                ((TrainerBuilder) Trainer.builder()
                        .setId(1)
                        .setFirstName("Ruby")
                        .setSecondName("Nichols")
                        .setBirthday(sqlDateFromString("1982-04-21")))
        );
        trainerMap.put(
                2L,
                ((TrainerBuilder) Trainer.builder()
                        .setId(2)
                        .setFirstName("Nichols")
                        .setSecondName("Ruby")
                        .setBirthday(sqlDateFromString("1962-05-19")))
        );
        trainerMap.put(
                3L,
                ((TrainerBuilder) Trainer.builder()
                        .setId(3)
                        .setFirstName("Doris")
                        .setSecondName("Franklin")
                        .setBirthday(sqlDateFromString("1984-03-16")))
        );
        trainerMap.put(
                4L,
                ((TrainerBuilder) Trainer.builder()
                        .setId(4)
                        .setFirstName("Thomas")
                        .setSecondName("West")
                        .setBirthday(sqlDateFromString("1980-01-19")))
        );
        trainerMap.put(
                5L,
                ((TrainerBuilder) Trainer.builder()
                        .setId(5)
                        .setFirstName("Alex")
                        .setSecondName("Strutynski")
                        .setBirthday(sqlDateFromString("1980-04-21")))
        );
        trainerMap.put(
                6L,
                ((TrainerBuilder) Trainer.builder()
                        .setId(6)
                        .setFirstName("Vova")
                        .setSecondName("Bog")
                        .setBirthday(null))
        );
    }

    private void initJockeyMap() throws ParseException {
        jockeyMap.put(
                1L,
                ((JockeyBuilder) Jockey.builder()
                        .setId(1)
                        .setFirstName("Ruby")
                        .setSecondName("Nichols")
                        .setBirthday(sqlDateFromString("1982-04-21")))
        );
        jockeyMap.put(
                2L,
                ((JockeyBuilder) Jockey.builder()
                        .setId(2)
                        .setFirstName("Nichols")
                        .setSecondName("Ruby")
                        .setBirthday(sqlDateFromString("1962-05-19")))
        );
        jockeyMap.put(
                3L,
                ((JockeyBuilder) Jockey.builder()
                        .setId(3)
                        .setFirstName("Doris")
                        .setSecondName("Franklin")
                        .setBirthday(sqlDateFromString("1984-03-16")))
        );
        jockeyMap.put(
                4L,
                ((JockeyBuilder) Jockey.builder()
                        .setId(4)
                        .setFirstName("Thomas")
                        .setSecondName("West")
                        .setBirthday(sqlDateFromString("1980-01-19")))
        );
        jockeyMap.put(
                5L,
                ((JockeyBuilder) Jockey.builder()
                        .setId(5)
                        .setFirstName("Alex")
                        .setSecondName("Strutynski")
                        .setBirthday(sqlDateFromString("1980-04-21")))
        );
        jockeyMap.put(
                6L,
                ((JockeyBuilder) Jockey.builder()
                        .setId(6)
                        .setFirstName("Vova")
                        .setSecondName("Bog")
                        .setBirthday(null))
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
        );

        participantMap.put(
                7L,
                Participant.builder()
                        .setId(7)
                        .setNumber(1)
                        .setHorse(getHorse(2))
                        .setJockey(getJockey(2))
                        .setTrainer(getTrainer(2))
        );

        participantMap.put(
                8L,
                Participant.builder()
                        .setId(8)
                        .setNumber(2)
                        .setHorse(getHorse(1))
                        .setJockey(getJockey(3))
                        .setTrainer(getTrainer(3))
        );

        participantMap.put(
                9L,
                Participant.builder()
                        .setId(9)
                        .setNumber(3)
                        .setHorse(getHorse(3))
                        .setJockey(getJockey(4))
                        .setTrainer(getTrainer(4))
        );
    }

    private void initRaceMap() throws ParseException {
        RaceBuilder race1Builder = Race.builder()
                .setId(1)
                .setName("Gembucket")
                .setRaceStatus("finished")
                .setCommission(0.14)
                .setMinBet(BigDecimal.valueOf(2))
                .setRacecourse(getRacecourse(1))
                .setStart(sqlTimestampFromString("2017-03-08 10:32:36"))
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
                .setStart(sqlTimestampFromString("2017-03-09 13:44:56"))
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
                .setStart(sqlTimestampFromString("2017-03-10 12:00:00"))
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

        raceMap.put(1L, race1Builder);
        raceMap.put(2L, race2Builder);
        raceMap.put(3L, race3Builder);
    }

    private void initApplicationUserMap() {
        applicationUserMap.put(
                1L,
                ApplicationUser.builder()
                        .setId(1)
                        .setLogin("pgordon0")
                        .setPassword("kJ182n")
                        .setSalt("salt")
                        .setFirstName("Paula")
                        .setLastName("Gordon")
                        .setEmail("pgordon0@google.ru")
                        .setEmailConfirmed(true)
                        .setBalance(BigDecimal.valueOf(827.32))
                        .addRole(Role.ADMIN)
                        .addRole(Role.BOOKMAKER)
                        .addRole(Role.HANDICAPPER)
        );

        applicationUserMap.put(
                2L,
                ApplicationUser.builder()
                        .setId(2)
                        .setLogin("slawrence1")
                        .setPassword("tBHdVXlvv")
                        .setSalt("salt")
                        .setFirstName("Shirley")
                        .setLastName("Lawrence")
                        .setEmail("slawrence1@geocities.com")
                        .setEmailConfirmed(true)
                        .setBalance(BigDecimal.valueOf(924.23))
                        .addRole(Role.BOOKMAKER)
                        .addRole(Role.HANDICAPPER)
        );

        applicationUserMap.put(
                3L,
                ApplicationUser.builder()
                        .setId(3)
                        .setLogin("hbanks2")
                        .setPassword("RgoMu22lO")
                        .setSalt("salt")
                        .setFirstName("Harold")
                        .setLastName("Banks")
                        .setEmail("hbanks2@adobe.com")
                        .setEmailConfirmed(true)
                        .setBalance(BigDecimal.valueOf(256.71))
                        .addRole(Role.HANDICAPPER)
        );

        applicationUserMap.put(
                4L,
                ApplicationUser.builder()
                        .setId(4)
                        .setLogin("lallen3")
                        .setPassword("WCeXA5")
                        .setSalt("salt")
                        .setFirstName("Lois")
                        .setLastName("Allen")
                        .setEmail("lallen3@virginia.edu")
                        .setEmailConfirmed(true)
                        .setBalance(BigDecimal.valueOf(385.59))
                        .addRole(Role.HANDICAPPER)
        );

        applicationUserMap.put(
                5L,
                ApplicationUser.builder()
                        .setId(5)
                        .setLogin("pandrews4")
                        .setPassword("uRKNaCqj2B")
                        .setSalt("salt")
                        .setFirstName("Paula")
                        .setLastName("Andrews")
                        .setEmail("pandrews4@artisteer.com")
                        .setEmailConfirmed(true)
                        .setBalance(BigDecimal.valueOf(749.38))
                        .addRole(Role.HANDICAPPER)
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
        );
    }

    public Jockey getJockey(long o) {
        return jockeyMap.get(o).build();
    }

    public Owner getOwner(long o) {
        return ownerMap.get(o).build();
    }

    public Trainer getTrainer(long o) {
        return trainerMap.get(o).build();
    }

    public Horse getHorse(long o) {
        return horseMap.get(o).build();
    }

    public Racecourse getRacecourse(long o) {
        return racecourseMap.get(o).build();
    }

    public Participant getParticipant(long o) {
        return participantMap.get(o).build();
    }

    public Race getRace(long o) {
        return raceMap.get(o).build();
    }

    public ApplicationUser getApplicationUser(long o) {
        return applicationUserMap.get(o).build();
    }

    public Bet getBet(long o) {
        return betMap.get(o).build();
    }

    public List<Jockey> getAllJockeys() {
        return jockeyMap.values().stream().map(JockeyBuilder::build).collect(Collectors.toList());
    }

    public List<Owner> getAllOwners() {
        return ownerMap.values().stream().map(OwnerBuilder::build).collect(Collectors.toList());
    }

    public List<Trainer> getAllTrainers() {
        return trainerMap.values().stream().map(TrainerBuilder::build).collect(Collectors.toList());
    }

    public List<Horse> getAllHorses() {
        return horseMap.values().stream().map(HorseBuilder::build).collect(Collectors.toList());
    }

    public List<Racecourse> getAllRacecourses() {
        return racecourseMap.values().stream().map(RacecourseBuilder::build).collect(Collectors.toList());
    }

    public List<Race> getAllRaces() {
        return raceMap.values().stream().map(RaceBuilder::build).collect(Collectors.toList());
    }

    public List<ApplicationUser> getAllApplicationUsers() {
        return applicationUserMap.values().stream().map(ApplicationUserBuilder::build).collect(Collectors.toList());
    }

    public List<Bet> getAllBets() {
        return betMap.values().stream().map(BetBuilder::build).collect(Collectors.toList());
    }
}
