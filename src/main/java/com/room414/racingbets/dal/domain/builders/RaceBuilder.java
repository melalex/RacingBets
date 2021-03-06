package com.room414.racingbets.dal.domain.builders;

import com.room414.racingbets.dal.domain.entities.Participant;
import com.room414.racingbets.dal.domain.entities.Race;
import com.room414.racingbets.dal.domain.entities.Racecourse;
import com.room414.racingbets.dal.domain.enums.RaceStatus;
import com.room414.racingbets.dal.domain.enums.RaceType;
import com.room414.racingbets.dal.domain.enums.TrackCondition;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Simplify creating Race instance using builder pattern.
 *
 * @author Alexander Melashchenko
 * @version 1.0 28 Feb 2017
 * @see Race
 */
public class RaceBuilder {
    private long id;
    private String name;
    private Racecourse racecourse;
    private Timestamp start;
    private BigDecimal minBet;
    private double commission;
    private TrackCondition trackCondition;
    private RaceType raceType;
    private RaceStatus raceStatus;
    private int raceClass;
    private int minAge;
    private int minRating;
    private int maxRating;
    private float distance;
    private List<Participant> participants;
    private Map<Integer, BigDecimal> prizes;

    private List<Participant> getParticipants() {
        if (participants == null) {
            participants = new LinkedList<>();
        }

        return participants;
    }

    private Map<Integer, BigDecimal> getPrizes() {
        if (prizes == null) {
            prizes = new HashMap<>();
        }
        return prizes;
    }

    public RaceBuilder setId(long id) {
        this.id = id;
        return this;
    }

    public RaceBuilder setName(String name) {
        this.name = name;
        return this;
    }

    public RaceBuilder setRacecourse(Racecourse racecourse) {
        this.racecourse = racecourse;
        return this;
    }

    public RaceBuilder setStart(Timestamp start) {
        this.start = start;
        return this;
    }

    public RaceBuilder setMinBet(BigDecimal minBet) {
        this.minBet = minBet;
        return this;
    }

    public RaceBuilder setCommission(double commission) {
        this.commission = commission;
        return this;
    }

    public RaceBuilder setTrackCondition(TrackCondition trackCondition) {
        this.trackCondition = trackCondition;
        return this;
    }

    public RaceBuilder setTrackCondition(String trackCondition) {
        this.trackCondition = TrackCondition.getTrackCondition(trackCondition);
        return this;
    }

    public RaceBuilder setRaceType(RaceType raceType) {
        this.raceType = raceType;
        return this;
    }

    public RaceBuilder setRaceType(String raceType) {
        this.raceType = RaceType.getRaceType(raceType);
        return this;
    }

    public RaceBuilder setRaceStatus(RaceStatus raceStatus) {
        this.raceStatus = raceStatus;
        return this;
    }

    public RaceBuilder setRaceStatus(String raceStatus) {
        this.raceStatus = RaceStatus.getRaceStatus(raceStatus);
        return this;
    }

    public RaceBuilder setRaceClass(int raceClass) {
        this.raceClass = raceClass;
        return this;
    }

    public RaceBuilder setMinAge(int minAge) {
        this.minAge = minAge;
        return this;
    }

    public RaceBuilder setMinRating(int minRating) {
        this.minRating = minRating;
        return this;
    }

    public RaceBuilder setMaxRating(int maxRating) {
        this.maxRating = maxRating;
        return this;
    }

    public RaceBuilder setDistance(float distance) {
        this.distance = distance;
        return this;
    }

    public RaceBuilder setParticipants(List<Participant> participants) {
        if (participants != null) {
            this.participants = new ArrayList<>(participants);
        } else {
            this.participants = null;
        }
        return this;
    }

    public RaceBuilder addParticipant(Participant participant) {
        getParticipants().add(participant);
        return this;
    }

    public RaceBuilder setPrize(int place, BigDecimal size) {
        getPrizes().put(place, size);
        return this;
    }

    public Race build() {
        Race race = new Race();
        List<Participant> sortedParticipants = getParticipants()
                .stream()
                .sorted(Comparator.comparingInt(Participant::getNumber))
                .collect(Collectors.toList());

        race.setId(id);
        race.setName(name);
        race.setRacecourse(racecourse);
        race.setStart(start);
        race.setMinBet(minBet);
        race.setCommission(commission);
        race.setTrackCondition(trackCondition);
        race.setRaceType(raceType);
        race.setRaceStatus(raceStatus);
        race.setRaceClass(raceClass);
        race.setMinAge(minAge);
        race.setMinRating(minRating);
        race.setMaxRating(maxRating);
        race.setDistance(distance);
        race.setParticipants(sortedParticipants);
        race.setPrizes(getPrizes());

        return race;
    }
}