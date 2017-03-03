package com.room414.racingbets.dal.domain.builders;

import com.room414.racingbets.dal.domain.entities.Participant;
import com.room414.racingbets.dal.domain.entities.Race;
import com.room414.racingbets.dal.domain.entities.Racecourse;
import com.room414.racingbets.dal.domain.enums.RaceStatus;
import com.room414.racingbets.dal.domain.enums.RaceType;
import com.room414.racingbets.dal.domain.enums.TrackCondition;
import com.room414.racingbets.dal.domain.infrastructure.BuildHelper;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

/**
 * Simplify creating Race instance using builder pattern.
 *
 * @see Race
 * @author Alexander Melashchenko
 * @version 1.0 28 Feb 2017
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
    private int maxAge;
    private int minRating;
    private int maxRating;
    private float distance;
    private List<Participant> participants;
    private List<BigDecimal> prices;

    private List<Participant> getParticipants() {
        if (participants == null) {
            participants = new ArrayList<>();
        }
        return participants;
    }

    private List<BigDecimal> getPrices() {
        if (prices == null) {
            prices = new ArrayList<>();
        }
        return prices;
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

    public RaceBuilder setRacecourseById(int id) {
        this.racecourse = new Racecourse();
        racecourse.setId(id);
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

    public RaceBuilder setMaxAge(int maxAge) {
        this.maxAge = maxAge;
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

    public RaceBuilder setParticipantsByIds(List<Integer> ids) {
        this.participants = BuildHelper.mapIdsToParticipants(ids);
        return this;
    }

    public RaceBuilder addParticipant(Participant participant) {
        getParticipants().add(participant);
        return this;
    }

    public RaceBuilder addParticipantById(int id) {
        Participant participant = new Participant();
        participant.setId(id);
        getParticipants().add(participant);
        return this;
    }

    public RaceBuilder setPrices(List<BigDecimal> prices) {
        if (prices != null) {
            this.prices = new ArrayList<>(prices);
        } else {
            this.prices = null;
        }
        return this;
    }

    public RaceBuilder setPrice(int place, BigDecimal size) {
        getPrices().set(place, size);
        return this;
    }

    public Race build() {
        Race race = new Race();

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
        race.setMaxAge(maxAge);
        race.setMinRating(minRating);
        race.setMaxRating(maxRating);
        race.setDistance(distance);
        race.setParticipants(getParticipants());
        race.setPrices(getPrices());

        return race;
    }
}