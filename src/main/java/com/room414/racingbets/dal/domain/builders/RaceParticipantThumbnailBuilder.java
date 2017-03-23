package com.room414.racingbets.dal.domain.builders;

import com.room414.racingbets.dal.domain.entities.Participant;
import com.room414.racingbets.dal.domain.entities.RaceParticipantThumbnail;
import com.room414.racingbets.dal.domain.entities.Racecourse;
import com.room414.racingbets.dal.domain.enums.RaceStatus;
import com.room414.racingbets.dal.domain.enums.RaceType;
import com.room414.racingbets.dal.domain.enums.TrackCondition;

import java.math.BigDecimal;
import java.sql.Timestamp;


/**
 * @author Alexander Melashchenko
 * @version 1.0 23 Mar 2017
 */
public class RaceParticipantThumbnailBuilder {
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
    private Participant participant;

    public RaceParticipantThumbnailBuilder setId(long id) {
        this.id = id;
        return this;
    }

    public RaceParticipantThumbnailBuilder setName(String name) {
        this.name = name;
        return this;
    }

    public RaceParticipantThumbnailBuilder setRacecourse(Racecourse racecourse) {
        this.racecourse = racecourse;
        return this;
    }

    public RaceParticipantThumbnailBuilder setRacecourseById(int id) {
        this.racecourse = new Racecourse();
        racecourse.setId(id);
        return this;
    }

    public RaceParticipantThumbnailBuilder setStart(Timestamp start) {
        this.start = start;
        return this;
    }

    public RaceParticipantThumbnailBuilder setMinBet(BigDecimal minBet) {
        this.minBet = minBet;
        return this;
    }

    public RaceParticipantThumbnailBuilder setCommission(double commission) {
        this.commission = commission;
        return this;
    }

    public RaceParticipantThumbnailBuilder setTrackCondition(TrackCondition trackCondition) {
        this.trackCondition = trackCondition;
        return this;
    }

    public RaceParticipantThumbnailBuilder setTrackCondition(String trackCondition) {
        this.trackCondition = TrackCondition.getTrackCondition(trackCondition);
        return this;
    }

    public RaceParticipantThumbnailBuilder setRaceType(RaceType raceType) {
        this.raceType = raceType;
        return this;
    }

    public RaceParticipantThumbnailBuilder setRaceType(String raceType) {
        this.raceType = RaceType.getRaceType(raceType);
        return this;
    }

    public RaceParticipantThumbnailBuilder setRaceStatus(RaceStatus raceStatus) {
        this.raceStatus = raceStatus;
        return this;
    }

    public RaceParticipantThumbnailBuilder setRaceStatus(String raceStatus) {
        this.raceStatus = RaceStatus.getRaceStatus(raceStatus);
        return this;
    }

    public RaceParticipantThumbnailBuilder setRaceClass(int raceClass) {
        this.raceClass = raceClass;
        return this;
    }

    public RaceParticipantThumbnailBuilder setMinAge(int minAge) {
        this.minAge = minAge;
        return this;
    }

    public RaceParticipantThumbnailBuilder setMinRating(int minRating) {
        this.minRating = minRating;
        return this;
    }

    public RaceParticipantThumbnailBuilder setMaxRating(int maxRating) {
        this.maxRating = maxRating;
        return this;
    }

    public RaceParticipantThumbnailBuilder setDistance(float distance) {
        this.distance = distance;
        return this;
    }

    public RaceParticipantThumbnailBuilder setParticipant(Participant participant) {
        this.participant = participant;
        return this;
    }

    public RaceParticipantThumbnail build() {
        RaceParticipantThumbnail thumbnail = new RaceParticipantThumbnail();

        thumbnail.setId(id);
        thumbnail.setName(name);
        thumbnail.setRacecourse(racecourse);
        thumbnail.setStart(start);
        thumbnail.setMinBet(minBet);
        thumbnail.setCommission(commission);
        thumbnail.setTrackCondition(trackCondition);
        thumbnail.setRaceType(raceType);
        thumbnail.setRaceStatus(raceStatus);
        thumbnail.setRaceClass(raceClass);
        thumbnail.setMinAge(minAge);
        thumbnail.setMinRating(minRating);
        thumbnail.setMaxRating(maxRating);
        thumbnail.setDistance(distance);
        thumbnail.setParticipant(participant);

        return thumbnail;
    }

}
