package com.room414.racingbets.dal.domain.entities;

import com.room414.racingbets.dal.domain.enums.BetType;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

/**
 * Class that represents a bet of handicapper.
 * To create instances of Bet is recommended to use the BetBuilder
 *
 * @see com.room414.racingbets.dal.domain.builders.BetBuilder
 * @author Alexander Melashchenko
 * @version 1.0 23 Feb 2017
 */
public class Bet implements Serializable {
    private static final long serialVersionUID = -5253938704091728335L;

    private int id;
    private ApplicationUser user;
    private BigDecimal betSize;
    private BetType betType;
    private List<Participant> participants;

    public Bet() {
    }

    public Bet(int id, ApplicationUser user, BigDecimal betSize, BetType betType, List<Participant> participants) {
        this.id = id;
        this.user = user;
        this.betSize = betSize;
        this.betType = betType;
        this.participants = participants;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public List<Participant> getParticipants() {
        return participants.subList(0, participants.size() - 1);
    }

    public void setParticipants(List<Participant> participants) {
        this.participants = participants.subList(0, participants.size() - 1);
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

        if (user != null ? !user.equals(bet.user) : bet.user != null) {
            return false;
        }

        if (betSize != null ? !betSize.equals(bet.betSize) : bet.betSize != null) {
            return false;
        }

        if (betType != bet.betType) {
            return false;
        }

        if (participants != null ? !participants.equals(bet.participants) : bet.participants != null) {
            return false;
        }

        return true;
    }

    @Override
    public int hashCode() {
        int result = id;

        result = 31 * result + (user != null ? user.hashCode() : 0);
        result = 31 * result + (betSize != null ? betSize.hashCode() : 0);
        result = 31 * result + (betType != null ? betType.hashCode() : 0);
        result = 31 * result + (participants != null ? participants.hashCode() : 0);

        return result;
    }

    @Override
    public String toString() {
        return "Bet{" +
                "id=" + id +
                ", user=" + user +
                ", betSize=" + betSize +
                ", betType=" + betType +
                ", participants=" + participants +
                '}';
    }
}
