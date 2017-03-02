package com.room414.racingbets.dal.domain.builders;

import com.room414.racingbets.dal.domain.entities.ApplicationUser;
import com.room414.racingbets.dal.domain.entities.Bet;
import com.room414.racingbets.dal.domain.entities.Participant;
import com.room414.racingbets.dal.domain.enums.BetStatus;
import com.room414.racingbets.dal.domain.enums.BetType;
import com.room414.racingbets.dal.domain.infrastructure.BuildHelper;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * Simplify creating Bet instance using builder pattern.
 *
 * @see Bet
 * @author Alexander Melashchenko
 * @version 1.0 27 Feb 2017
 */
public class BetBuilder {
    private long id;
    private ApplicationUser user;
    private BigDecimal betSize;
    private BetType betType;
    private BetStatus betStatus;
    private List<Participant> participants;

    private List<Participant> getParticipants() {
        if (participants == null) {
            participants = new ArrayList<>();
        }
        return participants;
    }

    public BetBuilder setId(long id) {
        this.id = id;
        return this;
    }

    public BetBuilder setUser(ApplicationUser user) {
        this.user = user;
        return this;
    }

    public BetBuilder setUserById(int id) {
        this.user = new ApplicationUser();
        this.user.setId(id);
        return this;
    }

    public BetBuilder setBetSize(BigDecimal betSize) {
        this.betSize = betSize;
        return this;
    }

    public BetBuilder setBetType(BetType betType) {
        this.betType = betType;
        return this;
    }

    public BetBuilder setBetStatus(BetStatus betStatus) {
        this.betStatus = betStatus;
        return this;
    }

    public BetBuilder setParticipants(List<Participant> participants) {
        if (participants != null) {
            this.participants = new ArrayList<>(participants);
        } else {
            this.participants = null;
        }
        return this;
    }

    public BetBuilder setParticipantsByIds(List<Integer> Ids) {
        this.participants = BuildHelper.mapIdsToParticipants(Ids);
        return this;
    }

    public BetBuilder addParticipant(Participant participant) {
        getParticipants().add(participant);
        return this;
    }

    public BetBuilder addParticipantById(int id) {
        Participant participant = new Participant();
        participant.setId(id);
        getParticipants().add(participant);
        return this;
    }

    public Bet build() {
        Bet bet = new Bet();

        bet.setId(id);
        bet.setUser(user);
        bet.setBetSize(betSize);
        bet.setBetType(betType);
        bet.setBetStatus(betStatus);
        bet.setParticipants(participants);

        return bet;
    }
}