package com.room414.racingbets.dal.domain.entities;

import com.room414.racingbets.dal.domain.builders.RaceParticipantThumbnailBuilder;
import com.room414.racingbets.dal.domain.enums.RaceStatus;
import com.room414.racingbets.dal.domain.enums.RaceType;
import com.room414.racingbets.dal.domain.enums.TrackCondition;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Timestamp;

/**
 * Class that represent thumbnail of participant.
 * To create instances of RaceParticipantThumbnail is recommended to use the RaceParticipantThumbnailBuilder.
 *
 * @see RaceParticipantThumbnailBuilder
 * @author Alexander Melashchenko
 * @version 1.0 23 Mar 2017
 */
public class RaceParticipantThumbnail implements Serializable {
    private static final long serialVersionUID = 2315996788389295233L;

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

    public static RaceParticipantThumbnailBuilder builder() {
        return new RaceParticipantThumbnailBuilder();
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Racecourse getRacecourse() {
        return racecourse;
    }

    public void setRacecourse(Racecourse racecourse) {
        this.racecourse = racecourse;
    }

    public Timestamp getStart() {
        return start;
    }

    public void setStart(Timestamp start) {
        this.start = start;
    }

    public BigDecimal getMinBet() {
        return minBet;
    }

    public void setMinBet(BigDecimal minBet) {
        this.minBet = minBet;
    }

    public double getCommission() {
        return commission;
    }

    public void setCommission(double commission) {
        this.commission = commission;
    }

    public TrackCondition getTrackCondition() {
        return trackCondition;
    }

    public void setTrackCondition(TrackCondition trackCondition) {
        this.trackCondition = trackCondition;
    }

    public RaceType getRaceType() {
        return raceType;
    }

    public void setRaceType(RaceType raceType) {
        this.raceType = raceType;
    }

    public RaceStatus getRaceStatus() {
        return raceStatus;
    }

    public void setRaceStatus(RaceStatus raceStatus) {
        this.raceStatus = raceStatus;
    }

    public int getRaceClass() {
        return raceClass;
    }

    public void setRaceClass(int raceClass) {
        this.raceClass = raceClass;
    }

    public int getMinAge() {
        return minAge;
    }

    public void setMinAge(int minAge) {
        this.minAge = minAge;
    }

    public int getMinRating() {
        return minRating;
    }

    public void setMinRating(int minRating) {
        this.minRating = minRating;
    }

    public int getMaxRating() {
        return maxRating;
    }

    public void setMaxRating(int maxRating) {
        this.maxRating = maxRating;
    }

    public float getDistance() {
        return distance;
    }

    public void setDistance(float distance) {
        this.distance = distance;
    }

    public Participant getParticipant() {
        return participant;
    }

    public void setParticipant(Participant participant) {
        this.participant = participant;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }

        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        RaceParticipantThumbnail that = (RaceParticipantThumbnail) o;

        if (id != that.id) {
            return false;
        }

        if (Double.compare(that.commission, commission) != 0) {
            return false;
        }

        if (raceClass != that.raceClass) {
            return false;
        }

        if (minAge != that.minAge) {
            return false;
        }

        if (minRating != that.minRating) {
            return false;
        }

        if (maxRating != that.maxRating) {
            return false;
        }

        if (Float.compare(that.distance, distance) != 0) {
            return false;
        }

        if (name != null ? !name.equals(that.name) : that.name != null) {
            return false;
        }

        if (racecourse != null ? !racecourse.equals(that.racecourse) : that.racecourse != null) {
            return false;
        }

        if (start != null ? !start.equals(that.start) : that.start != null) {
            return false;
        }

        if (minBet != null ? minBet.compareTo(that.minBet) != 0 : that.minBet != null) {
            return false;
        }

        if (trackCondition != that.trackCondition) {
            return false;
        }

        if (raceType != that.raceType) {
            return false;
        }

        if (raceStatus != that.raceStatus) {
            return false;
        }

        if (participant != null ? !participant.equals(that.participant) : that.participant != null) {
            return false;
        }

        return true;
    }

    @Override
    public int hashCode() {
        int result;
        long temp;

        result = (int) (id ^ (id >>> 32));
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (racecourse != null ? racecourse.hashCode() : 0);
        result = 31 * result + (start != null ? start.hashCode() : 0);

        temp = minBet != null ? Double.doubleToLongBits(minBet.doubleValue()) : 0;
        result = 31 * result + (int) (temp ^ (temp >>> 32));

        temp = Double.doubleToLongBits(commission);
        result = 31 * result + (int) (temp ^ (temp >>> 32));

        result = 31 * result + (trackCondition != null ? trackCondition.hashCode() : 0);
        result = 31 * result + (raceType != null ? raceType.hashCode() : 0);
        result = 31 * result + (raceStatus != null ? raceStatus.hashCode() : 0);
        result = 31 * result + raceClass;
        result = 31 * result + minAge;
        result = 31 * result + minRating;
        result = 31 * result + maxRating;
        result = 31 * result + (distance != +0.0f ? Float.floatToIntBits(distance) : 0);
        result = 31 * result + (participant != null ? participant.hashCode() : 0);

        return result;
    }

    @Override
    public String toString() {
        return "RaceParticipantThumbnail{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", racecourse=" + racecourse +
                ", start=" + start +
                ", minBet=" + minBet +
                ", commission=" + commission +
                ", trackCondition=" + trackCondition +
                ", raceType=" + raceType +
                ", raceStatus=" + raceStatus +
                ", raceClass=" + raceClass +
                ", minAge=" + minAge +
                ", minRating=" + minRating +
                ", maxRating=" + maxRating +
                ", distance=" + distance +
                ", participant=" + participant +
                '}';
    }
}
