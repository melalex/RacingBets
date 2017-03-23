package com.room414.racingbets.dal.domain.entities;

import com.room414.racingbets.dal.domain.base.BaseRace;
import com.room414.racingbets.dal.domain.builders.RaceBuilder;

import java.math.BigDecimal;
import java.util.*;

import static com.room414.racingbets.dal.domain.infrastructure.EntityHelper.compareMaps;

/**
 * Class that represent race.
 * To create instances of Race is recommended to use the RaceBuilder.
 *
 * @see RaceBuilder
 * @author Alexander Melashchenko
 * @version 1.0 23 Feb 2017
 */
public class Race extends BaseRace {
    private static final long serialVersionUID = 8351694393075721386L;

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

    public Participant getParticipant(int place) {
        return participants.get(place);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }

        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        if (!super.equals(o)) {
            return false;
        }

        Race race = (Race) o;

        if (participants != null ? !participants.equals(race.participants) : race.participants != null) {
            return false;
        }

        if (prizes != null ? !compareMaps(prizes, race.prizes, Comparator.naturalOrder()) : race.prizes != null) {
            return false;
        }

        return true;
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();

        result = 31 * result + (participants != null ? participants.hashCode() : 0);
        result = 31 * result + (prizes != null ? prizes.hashCode() : 0);

        return result;
    }

    @Override
    public String toString() {
        return "Race{" +
                "id=" + getId() +
                ", name='" + getName() + '\'' +
                ", racecourse=" + getRacecourse() +
                ", start=" + getStart() +
                ", minBet=" + getMinBet() +
                ", commission=" + getCommission() +
                ", trackCondition=" + getTrackCondition() +
                ", raceType=" + getRaceType() +
                ", raceStatus=" + getRaceStatus() +
                ", raceClass=" + getRaceClass() +
                ", minAge=" + getMinAge() +
                ", minRating=" + getMinRating() +
                ", maxRating=" + getMaxRating() +
                ", distance=" + getDistance() +
                ", participants=" + participants +
                ", prizes=" + prizes +
                '}';
    }
}
