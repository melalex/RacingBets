package com.room414.racingbets.dal.domain.entities;

import com.room414.racingbets.dal.domain.builders.BetBuilder;
import com.room414.racingbets.dal.domain.enums.BetStatus;
import com.room414.racingbets.dal.domain.enums.BetType;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * Class that represents a bet of handicapper.
 * To createUnitOfWorkFactory instances of Bet is recommended to use the BetBuilder
 *
 * @see BetBuilder
 * @author Alexander Melashchenko
 * @version 1.0 23 Feb 2017
 */
public class Bet implements Serializable {
    private static final long serialVersionUID = -5253938704091728335L;

    private long id;
    // TODO: make proxy
    private long raceId;
    private ApplicationUser user;
    private BigDecimal betSize;
    private BetType betType;
    private BetStatus betStatus;
    private List<Participant> participants;

    public Bet() {
    }

    public static BetBuilder builder() {
        return new BetBuilder();
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getRaceId() {
        return raceId;
    }

    public void setRaceId(long raceId) {
        this.raceId = raceId;
    }

    public ApplicationUser getUser() {
        return user;
    }

    public void setUser(ApplicationUser user) {
        this.user = user;
    }

    public BigDecimal getBetSize() {
        return betSize;
    }

    public void setBetSize(BigDecimal betSize) {
        this.betSize = betSize;
    }

    public BetType getBetType() {
        return betType;
    }

    public void setBetType(BetType betType) {
        this.betType = betType;
    }

    public BetStatus getBetStatus() {
        return betStatus;
    }

    public void setBetStatus(BetStatus betStatus) {
        this.betStatus = betStatus;
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

        Bet bet = (Bet) o;

        if (id != bet.id) {
            return false;
        }

        if (raceId != bet.raceId) {
            return false;
        }

        if (user != null ? !user.equals(bet.user) : bet.user != null) {
            return false;
        }

        if (betSize != null ? !betSize.equals(bet.betSize) : bet.betSize != null) {
            return false;
        }

        if (betType != bet.betType) {
            return false;
        }

        if (betStatus != bet.betStatus) {
            return false;
        }

        if (participants != null ? !participants.equals(bet.participants) : bet.participants != null) {
            return false;
        }

        return true;
    }

    @Override
    public int hashCode() {
        int result = (int) (id ^ (id >>> 32));

        result = 31 * result + (int) (raceId ^ (raceId >>> 32));
        result = 31 * result + (user != null ? user.hashCode() : 0);
        result = 31 * result + (betSize != null ? betSize.hashCode() : 0);
        result = 31 * result + (betType != null ? betType.hashCode() : 0);
        result = 31 * result + (betStatus != null ? betStatus.hashCode() : 0);
        result = 31 * result + (participants != null ? participants.hashCode() : 0);

        return result;
    }

    @Override
    public String toString() {
        return "Bet{" +
                "id=" + id +
                ", raceId=" + raceId +
                ", user=" + user +
                ", betSize=" + betSize +
                ", betType=" + betType +
                ", betStatus=" + betStatus +
                ", participants=" + participants +
                '}';
    }
}
