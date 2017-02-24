package com.room414.racingbets.dal.domain.entities;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @author Alexander Melashchenko
 * @version 1.0 23 Feb 2017
 */
public class Race implements Serializable {
    private static final long serialVersionUID = 8351694393075721386L;

    private int id;
    private String name;
    private Racecourse racecourse;
    /**
     * Race's start date and time.
     */
    // TODO: change to SQL DATETIME
    private Timestamp start;
    private Weather weather;
    private TrackCondition trackCondition;
    private RaceType raceType;

    // TODO: add comment
    private int raceClass;
    private int minAge;
    private int maxAge;
    // TODO: rename to rating in db
    private int minRating;
    private int maxRating;

    private float distance;
    private String verdict;

    // TODO: change db representation
    private List<BigDecimal> prices;

    // TODO: add comment
    // TODO: valueOf
    public enum RaceType {
        FLAT {
            @Override
            public String toString() {
                return "flat";
            }
        },
        JUMP {
            @Override
            public String toString() {
                return "jump";
            }
        },
        HARNESS {
            @Override
            public String toString() {
                return "harness";
            }
        },
    }

    public Race() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
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

    public Weather getWeather() {
        return weather;
    }

    public void setWeather(Weather weather) {
        this.weather = weather;
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

    public void setRaceClass(int raceClass) {
        this.raceClass = raceClass;
    }

    public int getMinAge() {
        return minAge;
    }

    public void setMinAge(int minAge) {
        this.minAge = minAge;
    }

    public int getMaxAge() {
        return maxAge;
    }

    public void setMaxAge(int maxAge) {
        this.maxAge = maxAge;
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

    public String getVerdict() {
        return verdict;
    }

    public void setVerdict(String verdict) {
        this.verdict = verdict;
    }

    public List<BigDecimal> getPrices() {
        List<BigDecimal> result = new ArrayList<>();
        Collections.copy(result, prices);
        return result;
    }

    public void setPrices(List<BigDecimal> prices) {
        Collections.copy(this.prices, prices);
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

        if (raceClass != race.raceClass) {
            return false;
        }

        if (minAge != race.minAge) {
            return false;
        }

        if (maxAge != race.maxAge) {
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

        if (weather != null ? !weather.equals(race.weather) : race.weather != null) {
            return false;
        }

        if (trackCondition != null ? !trackCondition.equals(race.trackCondition) : race.trackCondition != null) {
            return false;
        }

        if (raceType != race.raceType) {
            return false;
        }

        if (verdict != null ? !verdict.equals(race.verdict) : race.verdict != null) {
            return false;
        }

        if (prices != null ? !prices.equals(race.prices) : race.prices != null) {
            return false;
        }

        return true;
    }

    @Override
    public int hashCode() {
        int result = id;

        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (racecourse != null ? racecourse.hashCode() : 0);
        result = 31 * result + (start != null ? start.hashCode() : 0);
        result = 31 * result + (weather != null ? weather.hashCode() : 0);
        result = 31 * result + (trackCondition != null ? trackCondition.hashCode() : 0);
        result = 31 * result + (raceType != null ? raceType.hashCode() : 0);
        result = 31 * result + raceClass;
        result = 31 * result + minAge;
        result = 31 * result + maxAge;
        result = 31 * result + minRating;
        result = 31 * result + maxRating;
        result = 31 * result + (distance != +0.0f ? Float.floatToIntBits(distance) : 0);
        result = 31 * result + (verdict != null ? verdict.hashCode() : 0);
        result = 31 * result + (prices != null ? prices.hashCode() : 0);

        return result;
    }

    @Override
    public String toString() {
        return "Race{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", racecourse=" + racecourse +
                ", start=" + start +
                ", weather=" + weather +
                ", trackCondition=" + trackCondition +
                ", raceType=" + raceType +
                ", raceClass=" + raceClass +
                ", minAge=" + minAge +
                ", maxAge=" + maxAge +
                ", minRating=" + minRating +
                ", maxRating=" + maxRating +
                ", distance=" + distance +
                ", verdict='" + verdict + '\'' +
                ", prices=" + prices +
                '}';
    }
}
