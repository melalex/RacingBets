package com.room414.racingbets.web.model.viewmodels;

import com.room414.racingbets.dal.domain.enums.BetType;

import java.math.BigDecimal;
import java.util.Map;

/**
 * @author Alexander Melashchenko
 * @version 1.0 27 Mar 2017
 */
// TODO: config by dozer
public class BetForm {
    private long raceId;
    private long user;
    private BigDecimal betSize;
    private BetType betType;
    private Map<Integer, Long> participants;

    public long getRaceId() {
        return raceId;
    }

    public void setRaceId(long raceId) {
        this.raceId = raceId;
    }

    public long getUser() {
        return user;
    }

    public void setUser(long user) {
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

    public Map<Integer, Long> getParticipants() {
        return participants;
    }

    public void setParticipants(Map<Integer, Long> participants) {
        this.participants = participants;
    }

    @Override
    public String toString() {
        return "BetForm{" +
                ", raceId=" + raceId +
                ", user=" + user +
                ", betSize=" + betSize +
                ", betType=" + betType +
                ", participants=" + participants +
                '}';
    }
}
