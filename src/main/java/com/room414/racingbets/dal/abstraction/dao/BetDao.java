package com.room414.racingbets.dal.abstraction.dao;

import com.room414.racingbets.dal.domain.entities.Bet;
import com.room414.racingbets.dal.domain.entities.Odds;
import com.room414.racingbets.dal.domain.entities.Race;

import java.util.List;

/**
 * DAO for Bet entity
 *
 * @see Bet
 * @author Alexander Melashchenko
 * @version 1.0 27 Feb 2017
 */
public interface BetDao extends CrudDao<Long, Bet> {
    /**
     * @return bets made by the user with id == id param
     */
    List<Bet> findByUserId(long id, int offset, int limit);

    /**
     * @return number of bets made by the user with id == id param
     */
    int findByUserIdCount(long id);

    /**
     * @return bets make on RaceDto with id == id param
     */
    List<Bet> findByRaceId(long id, int offset, int limit);

    /**
     * @return number of bets make on RaceDto with id == id param
     */
    int findByRaceIdCount(long id);

    /**
     * Update all bets in the list.
     *
     * @return number of updates
     */
    long update(List<Bet> bets);

    /**
     * @return OddsDto of concrete BetDto.
     * @see <a href="https://en.wikipedia.org/wiki/Parimutuel_betting#Example">
     *          OddsDto calculation example
     *      </a>
     */
    Odds getOdds(Bet bet);


    /**
     * Update {@code Bet}s status to {@code BetStatus.WIN} or {@code BetStatus.LOSE} base on race result
     *
     * @param race {@code Race} which bets should be update
     * @see com.room414.racingbets.dal.domain.enums.BetStatus
     */
    void fixRaceResult(Race race);


    /**
     * Change {@code Bet}s status to {@code BetStatus.REJECTED}
     *
     * @param raceId {@code Race} which bets should be update
     * @see com.room414.racingbets.dal.domain.enums.BetStatus#REJECTED
     */
    void rejectBets(long raceId);
}
