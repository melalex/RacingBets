package com.room414.racingbets.dal.domain.builders;

import com.room414.racingbets.dal.abstraction.infrastructure.Pair;
import com.room414.racingbets.dal.domain.entities.ApplicationUser;
import com.room414.racingbets.dal.domain.entities.Bet;
import com.room414.racingbets.dal.domain.entities.Participant;
import com.room414.racingbets.dal.domain.enums.BetStatus;
import com.room414.racingbets.dal.domain.enums.BetType;

import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * Simplify creating Bet instance using builder pattern.
 *
 * @see Bet
 * @author Alexander Melashchenko
 * @version 1.0 27 Feb 2017
 */
public class BetBuilder {
    private long id;
    private long raceId;
    private ApplicationUser user;
    private BigDecimal betSize;
    private BetType betType;
    private BetStatus betStatus;
    private Set<Pair<Integer, Participant>> participants;

    private Set<Pair<Integer, Participant>> getParticipants() {
        if (participants == null) {
            participants = new HashSet<>();
        }
        return participants;
    }

    public BetBuilder setId(long id) {
        this.id = id;
        return this;
    }

    public BetBuilder setRaceId(long raceId) {
        this.raceId = raceId;
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

    public BetBuilder setBetType(String betType) {
        this.betType = BetType.getBetType(betType);
        return this;
    }

    public BetBuilder setBetStatus(BetStatus betStatus) {
        this.betStatus = betStatus;
        return this;
    }

    public BetBuilder setBetStatus(String betStatus) {
        this.betStatus = BetStatus.getStatus(betStatus);
        return this;
    }

    public BetBuilder setParticipants(Set<Pair<Integer, Participant>> participants) {
        if (participants != null) {
            this.participants = new HashSet<>(participants);
        } else {
            this.participants = null;
        }
        return this;
    }

    public BetBuilder setParticipantsByIds(List<Long> Ids) {
        this.participants = IntStream.range(0, Ids.size())
                .mapToObj(id -> new Pair<>(id, new Participant(Ids.get(id))))
                .collect(Collectors.toSet());
        return this;
    }

    public BetBuilder setParticipant(int place, Participant participant) {
        getParticipants().add(new Pair<>(place, participant));
        return this;
    }

    public BetBuilder setParticipantById(int place, int id) {
        Participant participant = new Participant(id);
        getParticipants().add(new Pair<>(place, participant));
        return this;
    }

    public Bet build() {
        Bet bet = new Bet();

        bet.setId(id);
        bet.setRaceId(raceId);
        bet.setUser(user);
        bet.setBetSize(betSize);
        bet.setBetType(betType);
        bet.setBetStatus(betStatus);
        bet.setParticipants(participants);

        return bet;
    }
}