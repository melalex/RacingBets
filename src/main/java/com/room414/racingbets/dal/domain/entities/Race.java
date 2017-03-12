package com.room414.racingbets.dal.domain.entities;

import com.room414.racingbets.dal.domain.builders.RaceBuilder;
import com.room414.racingbets.dal.domain.enums.RaceStatus;
import com.room414.racingbets.dal.domain.enums.RaceType;
import com.room414.racingbets.dal.domain.enums.TrackCondition;
import com.room414.racingbets.dal.domain.infrastructure.EntityHelper;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.*;

/**
 * Class that represent race.
 * To createUnitOfWorkFactory instances of Race is recommended to use the RaceBuilder.
 *
 * @see RaceBuilder
 * @author Alexander Melashchenko
 * @version 1.0 23 Feb 2017
 */
public class Race implements Serializable {
    private static final long serialVersionUID = 8351694393075721386L;

    private long id;
    private String name;
    private Racecourse racecourse;
    /**
     * Race's start date and time.
     */
    private Timestamp start;
    private BigDecimal minBet;
    /**
     * Bookmaker part from bets
     */
    private double commission;
    /**
     * Description of ground conditions
     */
    private TrackCondition trackCondition;
    private RaceType raceType;
    private RaceStatus raceStatus;

    private int raceClass;

    /**
     * Horse min age.
     */
    private int minAge;

    /**
     * Horse
     *
     * @see Participant#getOfficialRating()
     */
    private int minRating;
    /**
     * Horse max OR.
     *
     * @see Participant#getOfficialRating()
     */
    private int maxRating;

    private float distance;

    private List<Participant> participants;
    /**
     * Map place -> price
     */
    private Map<Integer, BigDecimal> prizes;

    public Race() {
    }

    public static RaceBuilder builder() {
        return new RaceBuilder();
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

    public int getRaceClass() {
        return raceClass;
    }

    public RaceStatus getRaceStatus() {
        return raceStatus;
    }

    public void setRaceStatus(RaceStatus raceStatus) {
        this.raceStatus = raceStatus;
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

    public Map<Integer, BigDecimal> getPrizes() {
        if (prizes != null) {
            return new HashMap<>(prizes);
        } else {
            return new HashMap<>();
        }
    }

    public void setPrizes(Map<Integer, BigDecimal> prizes) {
        if (prizes != null) {
            this.prizes = new HashMap<>(prizes);
        } else {
            this.prizes = null;
        }
    }

    public BigDecimal getPrize(int place) {
        return prizes.get(place);
    }

    public List<Participant> getParticipants() {
        if (participants != null) {
            return new ArrayList<>(participants);
        } else {
            return new ArrayList<>();
        }
    }

    public void setParticipants(List<Participant> participants) {
        if (participants != null) {
            this.participants = new ArrayList<>(participants);
        } else {
            this.participants = null;
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }

        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        Race race = (Race) o;

        if (id != race.id) {
            return false;
        }

        if (Double.compare(race.commission, commission) != 0) {
            return false;
        }

        if (raceClass != race.raceClass) {
            return false;
        }

        if (minAge != race.minAge) {
            return false;
        }

        if (minRating != race.minRating) {
            return false;
        }

        if (maxRating != race.maxRating) {
            return false;
        }

        if (Float.compare(race.distance, distance) != 0) {
            return false;
        }

        if (name != null ? !name.equals(race.name) : race.name != null) {
            return false;
        }

        if (racecourse != null ? !racecourse.equals(race.racecourse) : race.racecourse != null) {
            return false;
        }

        if (start != null ? !start.equals(race.start) : race.start != null) {
            return false;
        }

        if (minBet != null ? minBet.compareTo(race.minBet) != 0 : race.minBet != null) {
            return false;
        }

        if (trackCondition != race.trackCondition) {
            return false;
        }

        if (raceType != race.raceType) {
            return false;
        }

        if (raceStatus != race.raceStatus) {
            return false;
        }

        if (participants != null ? !participants.equals(race.participants) : race.participants != null) {
            return false;
        }

        if (prizes != null ? !EntityHelper.compareMaps(prizes, race.prizes, Comparator.naturalOrder()) : race.prizes != null) {
            return false;
        }

        return true;
    }

    @Override
    // TODO: is correct hashcode?
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
        result = 31 * result + (participants != null ? participants.hashCode() : 0);

        for (Integer place : prizes.keySet()) {
            temp = minBet != null ? Double.doubleToLongBits(prizes.get(place).doubleValue()) : 0;
            result = 31 * result + (int) (temp ^ (temp >>> 32));
        }

        result = 31 * result + (prizes != null ? prizes.hashCode() : 0);

        return result;
    }

    @Override
    public String toString() {
        return "Race{" +
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
