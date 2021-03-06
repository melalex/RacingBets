package com.room414.racingbets.bll.dto.entities;

import com.room414.racingbets.dal.domain.enums.BetStatus;
import com.room414.racingbets.dal.domain.enums.BetType;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

/**
 * DTO for Bet class
 *
 * @see com.room414.racingbets.dal.domain.entities.Bet
 * @author Alexander Melashchenko
 * @version 1.0 18 Mar 2017
 */
public class BetDto implements Serializable {
    private static final long serialVersionUID = -5253938704091728335L;

    private long id;
    private long raceId;
    private UserDto user;
    private BigDecimal betSize;
    private BetType betType;
    private BetStatus betStatus;
    private Map<Integer, ParticipantDto> participants;

    public BetDto() {
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

    public UserDto getUser() {
        return user;
    }

    public void setUser(UserDto user) {
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

    public Map<Integer, ParticipantDto> getParticipants() {
        if (participants != null) {
            return new HashMap<>(participants);
        } else {
            return new HashMap<>();
        }
    }

    public void setParticipants(Map<Integer, ParticipantDto> participants) {
        if (participants != null) {
            this.participants = new HashMap<>(participants);
        } else {
            this.participants = null;
        }
    }

    // TODO: delete this method
    public void setParticipant(int place, ParticipantDto participant) {
        participants.put(place, participant);
    }

    public ParticipantDto getParticipantByPlace(int place) {
        if (participants != null) {
            return participants.get(place);
        } else {
            return null;
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

        BetDto bet = (BetDto) o;

        if (id != bet.id) {
            return false;
        }

        if (raceId != bet.raceId) {
            return false;
        }

        if (user != null ? !user.equals(bet.user) : bet.user != null) {
            return false;
        }

        if (betSize != null ? betSize.compareTo(bet.betSize) != 0 : bet.betSize != null) {
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
        long temp;
        int result = (int) (id ^ (id >>> 32));

        result = 31 * result + (int) (raceId ^ (raceId >>> 32));
        result = 31 * result + (user != null ? user.hashCode() : 0);

        temp = betSize != null ? Double.doubleToLongBits(betSize.doubleValue()) : 0;
        result = 31 * result + (int) (temp ^ (temp >>> 32));

        result = 31 * result + (betSize != null ? betSize.hashCode() : 0);
        result = 31 * result + (betType != null ? betType.hashCode() : 0);
        result = 31 * result + (betStatus != null ? betStatus.hashCode() : 0);
        result = 31 * result + (participants != null ? participants.hashCode() : 0);

        return result;
    }

    @Override
    public String toString() {
        return "BetDto{" +
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
