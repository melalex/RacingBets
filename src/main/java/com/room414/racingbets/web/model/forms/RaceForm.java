package com.room414.racingbets.web.model.forms;

import com.room414.racingbets.dal.domain.enums.RaceStatus;
import com.room414.racingbets.dal.domain.enums.RaceType;
import com.room414.racingbets.dal.domain.enums.TrackCondition;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @author Alexander Melashchenko
 * @version 1.0 27 Mar 2017
 */
public class RaceForm implements Serializable {
    private static final long serialVersionUID = 4172213633870853172L;

    private long id;
    private String name;
    private long racecourse;
    private Date start;
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

    private List<ParticipantForm> participants;
    private Map<Integer, BigDecimal> prizes;

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

    public long getRacecourse() {
        return racecourse;
    }

    public void setRacecourse(long racecourse) {
        this.racecourse = racecourse;
    }

    public Date getStart() {
        return start;
    }

    public void setStart(Date start) {
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

    public List<ParticipantForm> getParticipants() {
        return participants;
    }

    public void setParticipants(List<ParticipantForm> participants) {
        this.participants = participants;
    }

    public Map<Integer, BigDecimal> getPrizes() {
        return prizes;
    }

    public void setPrizes(Map<Integer, BigDecimal> prizes) {
        this.prizes = prizes;
    }

    public BigDecimal getPrize(int place) {
        return prizes != null ? prizes.get(place) : null;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        RaceForm raceForm = (RaceForm) o;

        if (id != raceForm.id) return false;
        if (racecourse != raceForm.racecourse) return false;
        if (Double.compare(raceForm.commission, commission) != 0) return false;
        if (raceClass != raceForm.raceClass) return false;
        if (minAge != raceForm.minAge) return false;
        if (minRating != raceForm.minRating) return false;
        if (maxRating != raceForm.maxRating) return false;
        if (Float.compare(raceForm.distance, distance) != 0) return false;
        if (name != null ? !name.equals(raceForm.name) : raceForm.name != null) return false;
        if (start != null ? !start.equals(raceForm.start) : raceForm.start != null) return false;
        if (minBet != null ? !minBet.equals(raceForm.minBet) : raceForm.minBet != null) return false;
        if (trackCondition != raceForm.trackCondition) return false;
        if (raceType != raceForm.raceType) return false;
        if (raceStatus != raceForm.raceStatus) return false;
        if (participants != null ? !participants.equals(raceForm.participants) : raceForm.participants != null)
            return false;
        return prizes != null ? prizes.equals(raceForm.prizes) : raceForm.prizes == null;
    }

    @Override
    public int hashCode() {
        int result;
        long temp;
        result = (int) (id ^ (id >>> 32));
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (int) (racecourse ^ (racecourse >>> 32));
        result = 31 * result + (start != null ? start.hashCode() : 0);
        result = 31 * result + (minBet != null ? minBet.hashCode() : 0);
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
        result = 31 * result + (participants != null ? participants.hashCode() : 0);
        result = 31 * result + (prizes != null ? prizes.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "RaceForm{" +
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
                ", participants=" + participants +
                ", prizes=" + prizes +
                '}';
    }
}
